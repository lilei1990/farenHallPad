package com.shyms.farendating.home.introduce;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.shyms.faren.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.hokas.base.BaseActivity;

public class BusinessIntroduceActivity extends BaseActivity {

    @Bind(R.id.tv_content)
    protected TextView mTitle;
    @Bind(R.id.business_introduction)
    protected WebView mWebView;
    @Bind(R.id.tv_next)
    protected Button mBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_introduce);
        ButterKnife.bind(this);
        mTitle.setText("营商环境");
        mBackHome.setText("返回主页");
        mBackHome.setVisibility(View.VISIBLE);
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
        mWebView.loadUrl("http://business.chancheng.gov.cn/display/523.html");
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

}
