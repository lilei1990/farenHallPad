package com.shyms.farendating.home.login.fragment_sf;

public interface SfPresenter {
    void getRegisterCodes(String mobile);
    void getSF(String verify_code, String id_card, String mobile, String mobile_code);
}
