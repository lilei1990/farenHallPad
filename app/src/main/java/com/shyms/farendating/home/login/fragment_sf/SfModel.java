package com.shyms.farendating.home.login.fragment_sf;

/**
 * Created by hks on 2016/3/21.
 */
public interface SfModel {
    void getRegisterCodes(String mobile);
    void getSF(String verify_code, String id_card, String mobile, String mobile_code);
}
