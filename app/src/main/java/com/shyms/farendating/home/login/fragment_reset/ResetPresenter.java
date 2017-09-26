package com.shyms.farendating.home.login.fragment_reset;


public interface ResetPresenter {
    void getResetCode(String username);
    void getResetPassword(boolean flag,String confirmPwd,String username, String password, String verify_code);
}
