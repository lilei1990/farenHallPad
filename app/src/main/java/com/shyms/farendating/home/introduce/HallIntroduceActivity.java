package com.shyms.farendating.home.introduce;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.shyms.faren.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.hokas.base.BaseActivity;

public class HallIntroduceActivity extends BaseActivity {

    @Bind(R.id.tvBtn)
    protected TextView mRightTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        ButterKnife.bind(this);
        initView();
        tvTitle.setText("大厅介绍");
        tvBtn.setText("二楼营商环境");

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        IntroduceFragment introduceFragment = (IntroduceFragment) manager.findFragmentByTag("introduceFragment");
        if (introduceFragment == null) {
            introduceFragment = new IntroduceFragment();
        }
        ft.replace(R.id.fragment, introduceFragment, "introduceFragment");
        ft.commit();
    }

    @OnClick(R.id.tvBtn)
    public void onClickInfo() {
        GotoActivity(BusinessIntroduceActivity.class);
    }

}
