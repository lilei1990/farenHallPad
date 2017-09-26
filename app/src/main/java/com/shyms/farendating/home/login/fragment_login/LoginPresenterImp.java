package com.shyms.farendating.home.login.fragment_login;


import android.content.Context;
import android.util.Log;

import com.shyms.farendating.home.login.OnLoginListener;
import com.shyms.farendating.model.BaseObject;
import com.shyms.farendating.model.UserInfo;
import com.shyms.farendating.utils.UserManager;

import me.hokas.utils.PreferencesUtil;

public class LoginPresenterImp implements LoginPresenter, OnLoginListener {
    private LoginViews view;
    private LoginModelImp modelImp;
    private Context context;

    public LoginPresenterImp(LoginViews view,Context context) {
        this.view = view;
        this.context = context;
        modelImp = new LoginModelImp(this);
    }

    @Override
    public void getQRLogin(String username, String verify_code) {
        modelImp.getQRLogin(username,verify_code);
    }

    @Override
    public void login(String username, String password) {
//        modelImp.login(username, password);
    }


    @Override
    public void onSuccess(BaseObject baseObject) {

    }

    @Override
    public void onSuccess(UserInfo userInfo) {
        UserManager.getInstance().setUserInfo(userInfo);
        UserManager.getInstance().setLoginStatus(true);
        Log.d("loginPresenter","登录成功："+UserManager.getInstance().getLoginStatus()+" "+userInfo.toString());
        Log.d("wick","登录成功uid="+UserManager.getInstance().getLastUserInfo().getData().getUserId());
        PreferencesUtil.setIsLogin(true);//设置登录标志位  登录成功后切换登陆后的UI
        PreferencesUtil.setLoginStatus(true);
        view.showMsg("登录成功");
//        view.showMsg("登录成功uid="+UserManager.getInstance().getLastUserInfo().getData().getUserId());
        view.onCompleted();
    }

    @Override
    public void onFailed(String msg) {
        view.showMsg(msg);
    }

}
