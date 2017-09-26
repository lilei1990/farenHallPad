package com.shyms.farendating.home.business_space;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.shyms.faren.R;
import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.home.business_space.details.BusinessSpaceDetailsFragment;
import com.shyms.farendating.home.business_space.model.InformationList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.hokas.base.BaseActivity;
import rx.android.schedulers.AndroidSchedulers;

public class BusinessSpaceActivity extends BaseActivity {
    @Bind(R.id.tv_content)
    protected TextView mTitle;
    @Bind(R.id.business_introduction)
    protected WebView mWebView;
    @Bind(R.id.tv_next)
    protected Button mBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_space);
        ButterKnife.bind(this);
        ButterKnife.bind(this);
        mTitle.setText("创业空间");
        mBackHome.setText("返回主页");
        mBackHome.setVisibility(View.VISIBLE);
        getBusinessSpace();
//        http://business.chancheng.gov.cn/bulletin_details/12/600.html
//        initView();
//        initData();

    }

    private void getBusinessSpace() {
        NetRequest.APIInstance.getBusinessSpace()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result.code == 1) {
                        setWebUrl(result.data.url);
                    }

                }, throwable -> {
                });

    }

    private void setWebUrl(String url) {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.loadUrl("http://" + url + "/");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @OnClick(R.id.iv_back)
    public void onClickBack() {

        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    @OnClick((R.id.tv_next))
    public void onClickBackHome() {
        finish();
    }

    @Override
    public void initData() {
        super.initData();
        initListFragment();
    }

    private void initListFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        BusinessSpaceFragment businessSpaceFragment = (BusinessSpaceFragment) manager.findFragmentByTag("businessSpaceFragment");
        if (businessSpaceFragment == null) {
            businessSpaceFragment = new BusinessSpaceFragment();
        }
        ft.replace(R.id.fragment, businessSpaceFragment, "businessSpaceFragment");
        ft.commit();
    }

    private void initDetailsFragment(InformationList.DataEntity dataEntity) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out, R.anim.slide_right_in, R.anim.slide_right_out);
        BusinessSpaceDetailsFragment businessSpaceDetailsFragment = (BusinessSpaceDetailsFragment) manager.findFragmentByTag("businessSpaceDetailsFragment");
        if (businessSpaceDetailsFragment == null) {
            businessSpaceDetailsFragment = new BusinessSpaceDetailsFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("information", dataEntity);
        businessSpaceDetailsFragment.setArguments(bundle);
        ft.replace(R.id.fragment, businessSpaceDetailsFragment, "businessSpaceDetailsFragment");
        ft.addToBackStack("businessSpaceFragment");
        ft.commit();

        ivBack.setOnClickListener(v -> {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ivBack.setOnClickListener(v1 -> finish());
        });
    }

    public void toDetailsListener(InformationList.DataEntity dataEntity) {
        initDetailsFragment(dataEntity);
    }

//    public void setTitle(String title) {
//        tvTitle.setText(title);
//    }
}
