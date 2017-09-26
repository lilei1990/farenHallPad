package com.shyms.farendating.home.guide;

import android.util.Log;

import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.home.guide.model.AffairsInfo;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by hks on 2016/3/16.
 */
public class GuideModel implements GuideListener {

    @Override
    public void getAffairGuideList(String depid, final OnGuideListener listener) {
        NetRequest.APIInstance.getAffairGuideList(depid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(affairsInfo -> {
                    if ("0".equals(affairsInfo.getCode())) {
                        Log.i("test", affairsInfo.getData().size() + "----");
                        listener.onSuccess(affairsInfo);
                    } else {

                        listener.onFailed(affairsInfo.getMessage().toString());
                    }
                    Log.i("test", "请求失败3333");
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        listener.onFailed("网络连接失败");
                        Log.d("GuideModel", throwable.toString());
                    }
                });
    }



    public interface OnGuideListener {
        void onSuccess(AffairsInfo affairsInfo);

        void onFailed(String msg);
    }
}
