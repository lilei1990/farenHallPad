package com.shyms.farendating.home.login.fragment_code_login;

import android.util.Log;

import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.home.login.OnLoginListener;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hks on 2016/3/23.
 */
public class CodeLoginModelImp implements CodeLoginModel {

    private OnLoginListener listener;

    public CodeLoginModelImp(OnLoginListener listener) {
        this.listener = listener;
    }

    @Override
    public void getPhoneCode(String username) {
        NetRequest.APIInstance.getPhoneCode(username).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseObject -> {
                    Log.d("CodeLoginModelImp", "baseObject:" + baseObject.toString());
                    if ("0".equals(baseObject.getCode()))
                        listener.onSuccess(baseObject);
                    else
                        listener.onFailed(baseObject.getMessage());
                }, throwable -> {
                    Log.d("CodeLoginModelImp", throwable.toString());
                    listener.onFailed("服务器连接超时,请重试！");
                });
    }

    @Override
    public void getCodeLogin(String mobile, String verify_code) {
        NetRequest.APIInstance.getCodeLogin(mobile, verify_code).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfo -> {
                    Log.d("CodeLoginModelImp", userInfo.toString());
                    if ("0".equals(userInfo.getCode()))
                        listener.onSuccess(userInfo);
                    else
                        listener.onFailed(userInfo.getMessage());
                }, throwable -> {
                    listener.onFailed("服务器连接超时,请重试！");

                });
    }
}
