package com.shyms.farendating.service;

import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.service.model.UserNoticeInfo;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MyServiceModelImp implements MyServiceModel{
    private OnServiceListener listener;

    public MyServiceModelImp(OnServiceListener listener) {
        this.listener = listener;
    }

    @Override
    public void userNotice(String uid) {
        NetRequest.APIInstance.getUserNotice(uid).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UserNoticeInfo>() {
                    @Override
                    public void call(UserNoticeInfo userNoticeInfo) {
                        if ("0".equals(userNoticeInfo.getCode()))
                            listener.onSuccess(userNoticeInfo);
                        else
                            listener.onFailure(userNoticeInfo.getMessage());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        listener.onFailure("服务器连接超时,请重试！");
                    }
                });
    }

    public interface OnServiceListener{
        void onSuccess(UserNoticeInfo userNoticeInfo);
        void onFailure(String msg);
    }
}
