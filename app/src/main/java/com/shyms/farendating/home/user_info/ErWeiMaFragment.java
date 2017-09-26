package com.shyms.farendating.home.user_info;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.shyms.faren.R;
import com.shyms.farendating.home.login.zxing.BarcodeFormat;
import com.shyms.farendating.home.login.zxing.EncodeHintType;
import com.shyms.farendating.home.login.zxing.WriterException;
import com.shyms.farendating.home.login.zxing.common.BitMatrix;
import com.shyms.farendating.home.login.zxing.qrcode.QRCodeWriter;
import com.shyms.farendating.http.AsyncHttpCallBack;
import com.shyms.farendating.http.AsyncHttpConfig;
import com.shyms.farendating.http.AsyncHttpRequest;
import com.shyms.farendating.utils.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hokas.base.BaseFragment;
import me.hokas.base.BaseObject;

/**
 * 用户二维码
 */
public class ErWeiMaFragment extends BaseFragment implements AsyncHttpCallBack {


    @Bind(R.id.sweepIV)
    ImageView sweepIV;
    @Bind(R.id.btn_erweima)
    Button btnErweima;
    private int QR_WIDTH = 300, QR_HEIGHT = 300;
    private int num = 60;
    private TimeCount timeCount;

    public ErWeiMaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_er_wei_ma, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    @Override
    public void initData() {
        timeCount = new TimeCount(61000, 1000);
        btnErweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpRequest.getQRGenerate(UserManager.getInstance().getLastUserInfo().getData().getLog_verify_code(),
                        ErWeiMaFragment.this);
            }
        });
        AsyncHttpRequest.getQRGenerate(UserManager.getInstance().getLastUserInfo().getData().getLog_verify_code(),this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onSuccess(int what, int statusCode, JSONObject response) {
        BaseObject baseObject = JSON.parseObject(response.toString(), BaseObject.class);
        if (AsyncHttpConfig.WITH_50009 == what) {
            if ("0".equals(baseObject.getCode())) {
                try {
                    String username = response.getJSONObject("data").getString("username");
                    String verify_code = response.getJSONObject("data").getString("verify_code");
                    String qrUrl =  "log&" + username + "&" + verify_code;
                    createQRImage(qrUrl);
                    btnErweima.setClickable(false);
                    btnErweima.setBackgroundResource(R.mipmap.btn_green);
                    timeCount.start();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                showToast(baseObject.getMessage());
            }
        }

    }

    @Override
    public void onFailure(int what, int statusCode, JSONObject errorResponse) {

    }

    public void createQRImage(String url) {
        try {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            //显示到一个ImageView上面
            sweepIV.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            num = 60;
            btnErweima.setText("刷新二维码");
            btnErweima.setClickable(true);
            btnErweima.setBackgroundResource(R.mipmap.btn_bg);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            btnErweima.setText("刷新二维码（" + num-- + "）");
        }
    }
}
