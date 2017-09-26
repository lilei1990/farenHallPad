package com.shyms.farendating.http.api;


import android.graphics.Bitmap;

import com.shyms.farendating.GlobalConstant;
import com.shyms.farendating.event.MainEventData;
import com.shyms.farendating.home.my_handle_affairs.material.TakePicActivity;
import com.shyms.farendating.model.NGetSingleImage;
import com.shyms.farendating.model.NObject;
import com.shyms.farendating.model.NSign;
import com.shyms.farendating.utils.BitmapUtils;
import com.shyms.farendating.utils.LogcatUtil;
import com.shyms.farendating.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.finalteam.galleryfinal.model.PhotoInfo;
import de.greenrobot.event.EventBus;
import me.hokas.base.BaseApplication;
import me.hokas.utils.ImageUtils;
import me.hokas.utils.SPUtil;
import me.hokas.utils.ToastUtil;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 一门式办事大厅
 * Created by hks on 2016/3/2.
 */
public class NetRequest {
        public static final String URL = "http://19.134.148.17";     //正式
//    public static final String URL = "http://114.55.65.20";  //大厅测试
//    public static final String URL = "http://59.39.58.131/";  //大厅正式2
//    public static final String URL = "http://192.168.2.31";//佛山
//    public static final String URL = "http://19.134.148.36";//大厅测试

    //                public static final String WEB_URL = "http://115.29.9.178:8085";//大厅
    public static final String WEB_URL = "http://19.134.148.12:8081";//大厅正式19.134.148.12:8081

    private static final String LINE_UP = "http://59.39.58.131";//正式环境
    //        private static final String LINE_UP = "http://115.28.12.120";//正式环境
    //            private static final String LINE_UP = "http://19.134.148.15";//测试大厅
//    private static final String LINE_UP = "http://114.55.65.20";//测试环境
//            private static final String LINE_UP = "http://192.168.2.31";//测试佛山
    public static APIClient APIInstance;
    public static APIClient APIInstance2;
    private static Cache cache;
    public static Interceptor mInterceptor = chain -> {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();
        HttpUrl url = originalHttpUrl.newBuilder()
                .build();
        Request.Builder requestBuilder = original.newBuilder()
                .url(url);
        LogcatUtil.d("httpURL", "http=" + url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    };

    static {
        try {
            cache = new Cache(BaseApplication.getInstance().getCacheDir(), 1 * 1024 * 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .cache(cache)
                .connectTimeout(40, TimeUnit.SECONDS)
                .addInterceptor(mInterceptor)
                .addInterceptor(interceptor).build();
        Retrofit.Builder builder = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create());
        APIInstance = builder.baseUrl(URL).build().create(APIClient.class);
        APIInstance2 = builder.baseUrl(LINE_UP).build().create(APIClient.class);
    }

    public static Observable<NObject<String>> uploadImage(String type, String detail_id, String guid, Bitmap bitmap) {
        return Observable.create(new Observable.OnSubscribe<NObject<String>>() {
            @Override
            public void call(Subscriber<? super NObject<String>> subscriber) {
                NSign nSign = new NSign();
                NetRequest.APIInstance.uploadImage(type, detail_id, guid, BitmapUtils.bitmapToString(bitmap),
                        nSign.getTimestamp(), Util.getToken(), nSign.getSign())
                        .subscribe(subscriber::onNext, subscriber::onError);
            }
        }).subscribeOn(Schedulers.io());
    }

    public static Observable<NObject<String>> uploadImage(List<PhotoInfo> photoInfoList, String detail_id) {
        List<Observable<NObject<String>>> observableList = new ArrayList<>(photoInfoList.size());
        String guid;     //图片的全局表示码
        String type;     //图片的类型
        for (int i = 0; i < photoInfoList.size(); i++) {
            guid = System.currentTimeMillis() + i + "";
            type = photoInfoList.get(i).getPhotoPath().substring(
                    photoInfoList.get(i).getPhotoPath().lastIndexOf(".") + 1);
            Bitmap bitmap = null;
            try {
                bitmap = BitmapUtils.getSmallBitmap(photoInfoList.get(i).getPhotoPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            observableList.add(NetRequest.uploadImage(type, detail_id, guid, bitmap));
        }
        return Observable.zip(observableList, args -> {
            NObject<String> correctResult = null;
            NObject<String> failResult = null;
            int count = 0;
            for (Object o : args) {
                if (((NObject<String>) o).code.equals(GlobalConstant.REQUEST_SUCCESS)) {
                    count++;
                    correctResult = (NObject) o;
//                    break;
                } else {
                    failResult = (NObject) o;
                }
            }

            if (count == 0) {
                return failResult;
            } else {
                EventBus.getDefault().post(new MainEventData(TakePicActivity.SUCCESS_COUNT_PIC, count));
                return correctResult;
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }


    public static Observable<Bitmap> covertToBitmap(String id, String guid) {
        NSign nSign = new NSign();
        return NetRequest.APIInstance.getSingleImg(id, guid, nSign.getTimestamp(), Util.getToken(), nSign.getSign())
                .map(new Func1<NObject<NGetSingleImage>, Bitmap>() {
                    Bitmap bitmap = null;

                    @Override
                    public Bitmap call(NObject<NGetSingleImage> result) {
                        if (result.code.equals(GlobalConstant.REQUEST_SUCCESS)) {
                            SPUtil.put(guid, result.data.getImage());
                            bitmap = ImageUtils.stringToBitmap(result.data.image);
                        } else if (result.getCode().equals(GlobalConstant.TOKEN_ERROR)) {
                            getTokenVerify(id, guid);
                        } else {
                            ToastUtil.shortShowText(result.message);
                        }
                        return bitmap;
                    }
                }).subscribeOn(Schedulers.io());
    }

    /**
     * 请求分装token
     */
    public static Observable<NObject<String>> getToken(String username, String secret) {
        return Observable.create((Observable.OnSubscribe<NObject<String>>) subscriber -> NetRequest.APIInstance.getToken(username, secret).subscribe(t -> {
            subscriber.onNext(t);
        }, subscriber::onError)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    private static void getTokenVerify(String id, String guid) {
        String password = Util.getMD5("zw@shyms");
        NetRequest.getToken("appaccount", password)
                .subscribe(result -> {
                    if (result.getCode().equals(GlobalConstant.REQUEST_SUCCESS)) {
                        SPUtil.put(GlobalConstant.USER_TOKEN_VALID, result.getData());
                        covertToBitmap(id, guid);
                    } else {
                        ToastUtil.shortShowText(result.getMessage());
                    }
                }, throwable -> {
                    ToastUtil.shortShowText("获取认证失败，请重新登录");
                });
    }
}
