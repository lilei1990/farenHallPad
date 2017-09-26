package com.shyms.farendating.home.login.fragment_sf;

/**
 * Created by hks on 2016/3/18.
 */
public interface SfView {

    void onCompleted();
    void sendCodeCompleted();
    void showMsg(String msg);
    void onFinish();
    void onTick();
}
