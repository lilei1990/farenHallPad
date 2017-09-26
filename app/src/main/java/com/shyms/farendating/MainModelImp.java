package com.shyms.farendating;

import android.util.Log;

import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.model.UserInfo;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainModelImp implements MainModel{
    private onMainListener listener;

    public MainModelImp(onMainListener listener) {
        this.listener = listener;
    }

    @Override
    public void autoLogin(String str) {
        NetRequest.APIInstance.login(str).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UserInfo>() {
                    @Override
                    public void call(UserInfo userInfo) {
                        Log.d("MainModelImp", userInfo.toString());
                        if("0".equals(userInfo.getCode()))
                            listener.onSuccess(userInfo);
                        else
                            listener.onFailed(userInfo.getMessage());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                                    listener.onFailed("服务器连接超时,请重试！");

                    }
                });
    }

    public interface onMainListener{
        void onSuccess(UserInfo userInfo);

        void onFailed(String msg);
    }
}
