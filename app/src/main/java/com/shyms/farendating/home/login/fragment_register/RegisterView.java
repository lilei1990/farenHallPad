package com.shyms.farendating.home.login.fragment_register;

public interface RegisterView {
    void onCompleted();
    void sendCodeCompleted();
    void showMsg(String msg);
    void onFinish();
    void onTick();
}
