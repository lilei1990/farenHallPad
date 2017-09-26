package com.shyms.farendating.home.login.fragment_code_login;

/**
 * Created by hks on 2016/3/23.
 */
public interface CodeLoginView {
    void onCompleted();
    void sendCodeCompleted();
    void showMsg(String msg);
    void onFinish();
    void onTick();
}
