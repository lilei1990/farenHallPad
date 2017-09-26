package com.shyms.farendating.home.guide.details.details_list.style_table;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.shyms.faren.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hokas.base.BaseActivity;

public class StyleTableActivity extends BaseActivity {

    @Bind(R.id.webView)
    WebView webView;

    private String wen, url;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_table);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initData() {
        intent = getIntent();
        wen = intent.getStringExtra("wen");
        url = intent.getStringExtra("url");
        tvTitle.setText(wen);

        webView.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，
                view.loadUrl(url);
                return true;
            }
        });
        webView.setFocusable(false);
    }
}
