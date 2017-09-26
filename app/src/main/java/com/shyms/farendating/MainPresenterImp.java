package com.shyms.farendating;

import android.content.Context;

import com.shyms.farendating.model.UserInfo;
import com.shyms.farendating.utils.UserManager;

public class MainPresenterImp implements MainPresenter, MainModelImp.onMainListener {
    private MainView view;
    private MainModelImp modelImp;
    private Context context;

    public MainPresenterImp(MainView view, Context context) {
        this.view = view;
        this.context = context;
        modelImp = new MainModelImp(this);
    }

    @Override
    public void autoLogin(String str) {
        modelImp.autoLogin(str);
    }

    @Override
    public void onSuccess(UserInfo userInfo) {
        UserManager.getInstance().setUserInfo(userInfo);
        UserManager.getInstance().setLoginStatus(true);
        view.onLogin();
    }

    @Override
    public void onFailed(String msg) {
        view.showMsg(msg);
    }
}
