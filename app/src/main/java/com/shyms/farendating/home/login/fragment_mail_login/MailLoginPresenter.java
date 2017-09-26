package com.shyms.farendating.home.login.fragment_mail_login;

public interface MailLoginPresenter {
    void getEmailCode(String Email, String request);
    void getEmailLogin(String Email,String verify_code);
}
