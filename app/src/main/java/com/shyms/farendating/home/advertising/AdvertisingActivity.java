package com.shyms.farendating.home.advertising;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shyms.faren.R;
import com.shyms.farendating.http.api.NetRequest;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hokas.base.BaseActivity;
import rx.android.schedulers.AndroidSchedulers;

public class AdvertisingActivity extends BaseActivity {

    @Bind(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertising);

        ButterKnife.bind(this);
        initView();
        tvTitle.setText("最新禅城");
        getAdvertisement();


    }

    private void getAdvertisement() {
        NetRequest.APIInstance.getAdvertisement()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result.code == 1) {
                        setupWebUrl(result.data.url);
                    }
                }, throwable -> {

                });

    }

    private void setupWebUrl(String url) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl("http://" + url + "/");
    }

    @Override
    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
