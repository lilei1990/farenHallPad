package com.shyms.farendating.home.login.fragment_login;

import android.util.Log;

import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.home.login.OnLoginListener;
import com.shyms.farendating.model.UserInfo;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by hks on 2016/3/21.
 */
public class LoginModelImp implements LoginModel {
    private OnLoginListener listener;

    public LoginModelImp(OnLoginListener listener) {
        this.listener = listener;
    }


    @Override
    public void getQRLogin(final String username, String verify_code) {
        NetRequest.APIInstance.getQRLogin(username, verify_code).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UserInfo>() {
                    @Override
                    public void call(UserInfo userInfo) {
                        Log.d("LoginModelImp", userInfo.toString());
                        if ("0".equals(userInfo.getCode()))
                            listener.onSuccess(userInfo);
                        else
                            listener.onFailed(userInfo.getMessage());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("LoginModelImp", throwable.toString());
                        listener.onFailed("服务器连接超时,请重试！");
                    }
                });
    }

    @Override
    public void login(String username, String password) {

//        retrofitLogin(username, password);



    }

    private void retrofitLogin(String username, String password) {
        NetRequest.APIInstance.login()
                .subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UserInfo>() {
                    @Override
                    public void call(UserInfo userInfo) {
                        Log.d("WICK", "进入登陆毁掉");
                        if ("0".equals(userInfo.getCode())) {
                            listener.onSuccess(userInfo);
                        } else {
                            listener.onFailed(userInfo.getMessage());
                        }
                    }
                }, throwable -> {
                    Log.d("login", throwable.toString());
                    listener.onFailed("服务器连接超时,请重试！");
                });
    }


}
