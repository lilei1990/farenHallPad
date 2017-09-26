package com.shyms.farendating.home.guide.details;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shyms.faren.R;
import com.shyms.farendating.home.guide.details.details_list.MattersListActivity;
import com.shyms.farendating.utils.ContentCode;
import com.shyms.farendating.utils.LogcatUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.hokas.base.BaseActivity;

/**
 * 办事指南列表条目详情
 */
public class MattersDetailsActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_next)
    Button tvNext;
    @Bind(R.id.web_view)
    WebView mWebView;
    private String mtid;
    private String number;
    private String mattersName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matters_details);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        mattersName = intent.getStringExtra(ContentCode.MATTERS_NAME);
        mtid = intent.getStringExtra(ContentCode.MATTERS_ID);
        number = intent.getStringExtra(ContentCode.MATTERS_NUMBER);
        LogcatUtil.d("MATTERS_ID", "mtid=" + mtid);
        tvContent.setText(mattersName);
        tvNext.setVisibility(View.VISIBLE);
        tvNext.setText("材料列表");
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
//        http://59.39.58.131/UploadFiles/html/JK038.html
        mWebView.loadUrl("http://59.39.58.131/UploadFiles/html/" + number + ".html");

    }

    @OnClick(R.id.tv_next)
    public void onClickNext() {
        Intent intent = new Intent(MattersDetailsActivity.this, MattersListActivity.class);
        intent.putExtra(ContentCode.MATTERS_ID, mtid);
        intent.putExtra(ContentCode.MATTERS_NUMBER, number);
        intent.putExtra(ContentCode.MATTERS_NAME, mattersName);
        startActivity(intent);
    }

    @OnClick(R.id.iv_back)
    public void onClickBack() {
        finish();
    }

}
