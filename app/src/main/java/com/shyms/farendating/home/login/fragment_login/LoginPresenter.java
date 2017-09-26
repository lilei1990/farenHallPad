package com.shyms.farendating.home.login.fragment_login;

public interface LoginPresenter {
    void getQRLogin(String username, String verify_code);
    void login(String username, String password);
}
