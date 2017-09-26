package com.shyms.farendating.http.api;

import android.os.Bundle;
import android.widget.TextView;

import com.shyms.faren.R;

import me.hokas.base.BaseActivity;

public class BugActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_bug);
        TextView textView = (TextView) findViewById(R.id.bugTextView);
        textView.setText(getIntent().getStringExtra("stackTrace"));
    }
}
