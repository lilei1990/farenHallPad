package com.shyms.farendating.home.login.fragment_register;

import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.home.login.OnLoginListener;
import com.shyms.farendating.model.BaseObject;
import com.shyms.farendating.model.UserInfo;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RegisterModelImp implements RegisterModel {
    private OnLoginListener listener;

    public RegisterModelImp(OnLoginListener listener) {
        this.listener = listener;
    }

    @Override
    public void userRegister(String username, String password, String name,
                             String mobile, String verify_code, int login) {
        NetRequest.APIInstance.userRegister(username, password, name, mobile, verify_code, login)
                .subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UserInfo>() {
                    @Override
                    public void call(UserInfo userInfo) {
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

    @Override
    public void getRegisterCode(String mobile) {
        NetRequest.APIInstance.getRegisterCode(mobile).subscribeOn(Schedulers.io()).
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
                        listener.onFailed("服务器连接超时,请重试！");
                    }
                });
    }
}
