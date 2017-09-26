package com.shyms.farendating.home.user_info;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;

import com.shyms.faren.R;
import com.shyms.farendating.home.login.fragment_reset.ResetFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hokas.base.BaseActivity;

public class UserInfoActivity extends BaseActivity implements UserInfoView {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        super.initView();
        tvTitle.setText("我的中心");
    }

    @Override
    public void initData() {
        infoUserInfoFragment();
    }

    @Override
    public void infoUserInfoFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        UserInfoFragment userInfoFragment = (UserInfoFragment) manager.findFragmentByTag("userInfoFragment");
        if (userInfoFragment == null) {
            userInfoFragment = new UserInfoFragment();
        }
        ft.replace(R.id.fragment, userInfoFragment, "userInfoFragment");
        ft.commit();
    }

    @Override
    public void switchMyInfo() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out, R.anim.slide_right_in, R.anim.slide_right_out);
        MyInfoFragment myInfoFragment = (MyInfoFragment) manager.findFragmentByTag("myInfoFragment");
        if (myInfoFragment == null) {
            myInfoFragment = new MyInfoFragment();
        }
        ft.replace(R.id.fragment, myInfoFragment, "myInfoFragment");
        ft.addToBackStack("myInfoFragment");
        ft.commit();
        back();
    }

    @Override
    public void switchMyLicense() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out, R.anim.slide_right_in, R.anim.slide_right_out);
        MyLicenseFragment myLicenseFragment = (MyLicenseFragment) manager.findFragmentByTag("myLicenseFragment");
        if (myLicenseFragment == null) {
            myLicenseFragment = new MyLicenseFragment();
        }
        ft.replace(R.id.fragment, myLicenseFragment, "myLicenseFragment");
        ft.addToBackStack("myLicenseFragment");
        ft.commit();
        back();
    }

    @Override
    public void switchContactPerson() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out, R.anim.slide_right_in, R.anim.slide_right_out);
        ContactPersonFragment contactPersonFragment = (ContactPersonFragment) manager.findFragmentByTag("contactPersonFragment");
        if (contactPersonFragment == null) {
            contactPersonFragment = new ContactPersonFragment();
        }
        ft.replace(R.id.fragment, contactPersonFragment, "contactPersonFragment");
        ft.addToBackStack("contactPersonFragment");
        ft.commit();

        back();
    }

    @Override
    public void switchAccountSecurity() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out, R.anim.slide_right_in, R.anim.slide_right_out);
        AccountSecurityFragment accountSecurityFragment = (AccountSecurityFragment) manager.findFragmentByTag("accountSecurityFragment");
        if (accountSecurityFragment == null) {
            accountSecurityFragment = new AccountSecurityFragment();
        }
        ft.replace(R.id.fragment, accountSecurityFragment, "accountSecurityFragment");
        ft.addToBackStack("accountSecurityFragment");
        ft.commit();

        back();
    }

    @Override
    public void switchErWeiMa() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out, R.anim.slide_right_in, R.anim.slide_right_out);
        ErWeiMaFragment erWeiMaFragment = (ErWeiMaFragment) manager.findFragmentByTag("erWeiMaFragment");
        if (erWeiMaFragment == null) {
            erWeiMaFragment = new ErWeiMaFragment();
        }
        ft.replace(R.id.fragment, erWeiMaFragment, "erWeiMaFragment");
        ft.addToBackStack("erWeiMaFragment");
        ft.commit();

        back();
    }

    @Override
    public void switchReset() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out, R.anim.slide_right_in, R.anim.slide_right_out);
        MyResetFragment myResetFragment = (MyResetFragment) manager.findFragmentByTag("myResetFragment");
        if (myResetFragment == null) {
            myResetFragment = new MyResetFragment();
        }
        ft.replace(R.id.fragment, myResetFragment, "myResetFragment");
        ft.addToBackStack("myResetFragment");
        ft.commit();

        back();
    }

    @Override
    public void switchBindingEmail() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out, R.anim.slide_right_in, R.anim.slide_right_out);
        BindingEmailFragment bindingEmailFragment = (BindingEmailFragment) manager.findFragmentByTag("bindingEmailFragment");
        if (bindingEmailFragment == null) {
            bindingEmailFragment = new BindingEmailFragment();
        }
        ft.replace(R.id.fragment, bindingEmailFragment, "bindingEmailFragment");
        ft.addToBackStack("bindingEmailFragment");
        ft.commit();

        back();
    }

    @Override
    public void setTitle(String title) {
        tvTitle.setText(title);
    }



    private void back() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle("我的中心");
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ivBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        });
    }
}
