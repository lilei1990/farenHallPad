package com.shyms.farendating.home.login.fragment_mail_login;

public interface MailLoginView {
    void onCompleted();
    void sendCodeCompleted();
    void showMsg(String msg);
    void onFinish();
    void onTick();
}
