package com.shyms.farendating;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shyms.faren.R;

import java.util.concurrent.TimeUnit;

import me.hokas.base.BaseActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Observable.timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                });
    }
}
