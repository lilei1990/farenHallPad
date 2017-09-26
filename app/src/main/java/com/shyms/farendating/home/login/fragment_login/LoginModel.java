package com.shyms.farendating.home.login.fragment_login;

/**
 * Created by hks on 2016/3/21.
 */
public interface LoginModel {
    void getQRLogin(String username, String verify_code);
    void login(String username, String password);
}
