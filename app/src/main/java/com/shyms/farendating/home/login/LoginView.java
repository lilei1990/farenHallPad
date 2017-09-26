package com.shyms.farendating.home.login;

/**
 * Created by hks on 2016/3/18.
 */
public interface LoginView {

    void toLoginFragment();
    void toCodeFragment();
    void toMailFragment();
    void toForgetPasswordFragment();
    void toRegisterFragment();
    void toSfFragment(String verify_code,String id_card);

    void onCompleted();

}
