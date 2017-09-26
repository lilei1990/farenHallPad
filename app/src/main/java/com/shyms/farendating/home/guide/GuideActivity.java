package com.shyms.farendating.home.guide;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;


import com.shyms.faren.R;

import me.hokas.base.BaseActivity;

/**
 * 办事指南
 */
public class GuideActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        tvTitle.setText("办事指南");
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        GuideFragment guideFragment = (GuideFragment) manager.findFragmentByTag("guideFragment");
        if (guideFragment == null) {
            guideFragment = new GuideFragment();
        }
        ft.replace(R.id.fragment, guideFragment, "guideFragment");
        ft.commit();
    }
}
