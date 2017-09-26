package com.shyms.farendating.home.login.fragment_reset;


import android.util.Log;

import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.home.login.OnLoginListener;
import com.shyms.farendating.model.BaseObject;
import com.shyms.farendating.model.UserInfo;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ResetModelImp implements ResetModel {
    private OnLoginListener listener;

    public ResetModelImp(OnLoginListener listener) {
        this.listener = listener;
    }


    @Override
    public void getRestCode(String username) {
        NetRequest.APIInstance.getPhoneCode(username).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseObject>() {
                    @Override
                    public void call(BaseObject baseObject) {
                        if ("0".equals(baseObject.getCode()))
                            listener.onSuccess(baseObject);
                        else
                            listener.onFailed(baseObject.getMessage());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("CodeLoginModelImp", throwable.toString());
                        listener.onFailed("服务器连接超时,请重试！");
                    }
                });
    }

    @Override
    public void getRestPassword(String username, String password, String verify_code) {
        NetRequest.APIInstance.getResetPassword(username, password, verify_code).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UserInfo>() {
                    @Override
                    public void call(UserInfo userInfo) {
                        Log.d("ResetModelImp", userInfo.toString());
                        if ("0".equals(userInfo.getCode()))
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
}
