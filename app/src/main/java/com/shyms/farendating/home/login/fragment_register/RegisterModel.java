package com.shyms.farendating.home.login.fragment_register;

public interface RegisterModel {
    void userRegister(String username, String password, String name, String mobile, String verify_code, int login);
    void getRegisterCode(String mobile);
}
