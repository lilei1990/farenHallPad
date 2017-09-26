package com.shyms.farendating.home.login.fragment_code_login;

/**
 * Created by hks on 2016/3/23.
 */
public interface CodeLoginPresenter {
    void getPhoneCode(String username);
    void getCodeLogin(String mobile, String verify_code);
}
