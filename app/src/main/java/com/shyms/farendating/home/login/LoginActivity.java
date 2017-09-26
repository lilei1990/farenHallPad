package com.shyms.farendating.home.login;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.shyms.faren.R;
import com.shyms.farendating.home.login.fragment_code_login.CodeLoginFragment;
import com.shyms.farendating.home.login.fragment_login.LoginFragment;
import com.shyms.farendating.home.login.fragment_login.LoginPresenterImp;
import com.shyms.farendating.home.login.fragment_mail_login.MailLoginFragment;
import com.shyms.farendating.home.login.fragment_register.RegisterFragment;
import com.shyms.farendating.home.login.fragment_reset.ResetFragment;
import com.shyms.farendating.home.login.fragment_sf.SfFragment;

import butterknife.ButterKnife;
import me.hokas.base.BaseActivity;

public class LoginActivity extends BaseActivity implements LoginView {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    public void initData() {
        toLoginFragment();
    }

    @Override
    public void toLoginFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        LoginFragment loginFragment = (LoginFragment) manager.findFragmentByTag("loginFragment");
        if (loginFragment == null) {
            loginFragment = new LoginFragment();
        }
        ft.replace(R.id.fragment, loginFragment, "loginFragment");
        ft.commit();
    }

    @Override
    public void toCodeFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out, R.anim.slide_right_in, R.anim.slide_right_out);
        CodeLoginFragment codeLoginFragment = (CodeLoginFragment) manager.findFragmentByTag("codeLoginFragment");
        if (codeLoginFragment == null) {
            codeLoginFragment = new CodeLoginFragment();
        }
        ft.replace(R.id.fragment, codeLoginFragment, "codeLoginFragment");
        ft.addToBackStack("codeLoginFragment");
        ft.commit();
    }

    @Override
    public void toMailFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out, R.anim.slide_right_in, R.anim.slide_right_out);
        MailLoginFragment mailLoginFragment = (MailLoginFragment) manager.findFragmentByTag("mailLoginFragment");
        if (mailLoginFragment == null) {
            mailLoginFragment = new MailLoginFragment();
        }
        ft.replace(R.id.fragment, mailLoginFragment, "mailLoginFragment");
        ft.addToBackStack("mailLoginFragment");
        ft.commit();
    }

    @Override
    public void toForgetPasswordFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out, R.anim.slide_right_in, R.anim.slide_right_out);
        ResetFragment resetFragment = (ResetFragment) manager.findFragmentByTag("resetFragment");
        if (resetFragment == null) {
            resetFragment = new ResetFragment();
        }
        ft.replace(R.id.fragment, resetFragment, "resetFragment");
        ft.addToBackStack("resetFragment");
        ft.commit();
    }

    @Override
    public void toRegisterFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out, R.anim.slide_right_in, R.anim.slide_right_out);
        RegisterFragment registerFragment = (RegisterFragment) manager.findFragmentByTag("registerFragment");
        if (registerFragment == null) {
            registerFragment = new RegisterFragment();
        }
        ft.replace(R.id.fragment, registerFragment, "registerFragment");
        ft.addToBackStack("registerFragment");
        ft.commit();
    }

    @Override
    public void toSfFragment(String verify_code, String id_card) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out, R.anim.slide_right_in, R.anim.slide_right_out);
        SfFragment sfFragment = (SfFragment) manager.findFragmentByTag("sfFragment");
        if (sfFragment == null) {
            sfFragment = new SfFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString("verify_code",verify_code);
        bundle.putString("id_card",id_card);
        sfFragment.setArguments(bundle);
        ft.replace(R.id.fragment, sfFragment, "sfFragment");
        ft.addToBackStack("sfFragment");
        ft.commit();
    }

    @Override
    public void onCompleted() {

    }

}
