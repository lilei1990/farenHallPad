package com.shyms.farendating.home.login.fragment_register;

public interface RegisterPresenter {
    void userRegister(boolean flag,String confirmPwd,String username, String password, String name, String mobile, String verify_code, int login);
    void getRegisterCode(String mobile);
}
