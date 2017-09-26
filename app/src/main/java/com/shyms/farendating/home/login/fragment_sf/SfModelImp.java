package com.shyms.farendating.home.login.fragment_sf;

import android.util.Log;

import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.home.login.OnLoginListener;
import com.shyms.farendating.model.BaseObject;
import com.shyms.farendating.model.UserInfo;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by hks on 2016/3/21.
 */
public class SfModelImp implements SfModel {
    private OnLoginListener listener;

    public SfModelImp(OnLoginListener listener) {
        this.listener = listener;
    }

    @Override
    public void getRegisterCodes(String mobile) {
        NetRequest.APIInstance.getRegisterCodes(mobile).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseObject>() {
                    @Override
                    public void call(BaseObject baseObject) {
                        Log.d("LoginModelImp", "baseObject:" + baseObject.toString());
                        if ("0".equals(baseObject.getCode()))
                            listener.onSuccess(baseObject);
                        else
                            listener.onFailed(baseObject.getMessage());
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
    public void getSF(String verify_code, String id_card, String mobile, String mobile_code) {
        NetRequest.APIInstance.getSf(verify_code, id_card, mobile, mobile_code).subscribeOn(Schedulers.io()).
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
                           }
                );
    }
}
