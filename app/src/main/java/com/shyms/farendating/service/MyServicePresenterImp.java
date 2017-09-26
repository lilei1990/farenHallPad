package com.shyms.farendating.service;

import com.shyms.farendating.service.model.UserNoticeInfo;

public class MyServicePresenterImp implements MyServiceModelImp.OnServiceListener,MyServicePresenter {
    private MyServiceView view;
    private MyServiceModelImp modelImp;

    public MyServicePresenterImp(MyServiceView view) {
        this.view = view;
        modelImp = new MyServiceModelImp(this);
    }

    @Override
    public void onSuccess(UserNoticeInfo userNoticeInfo) {
        view.onCompleted(userNoticeInfo);
    }

    @Override
    public void onFailure(String msg) {
        view.showMsg(msg);
    }

    @Override
    public void getUserNotice(String uid) {
        modelImp.userNotice(uid);
    }
}
