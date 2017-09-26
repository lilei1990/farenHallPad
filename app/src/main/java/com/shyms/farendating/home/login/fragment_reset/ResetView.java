package com.shyms.farendating.home.login.fragment_reset;


public interface ResetView {
    void onCompleted();
    void sendCodeCompleted();
    void showMsg(String msg);
    void onFinish();
    void onTick();
}
