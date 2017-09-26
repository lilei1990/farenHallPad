package com.shyms.farendating.home.login.fragment_reset;


public interface ResetModel {
    void getRestCode(String username);
    void getRestPassword(String username, String password, String verify_code);
}
