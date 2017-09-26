package com.shyms.farendating.home.lien_up;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shyms.faren.R;
import com.shyms.farendating.home.lien_up.model.NWindowInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hokas.base.BaseActivity;

public class AppointmentActivity extends BaseActivity implements View.OnClickListener {

    @Bind({R.id.rlBtn01, R.id.rlBtn02, R.id.rlBtn03, R.id.rlBtn04, R.id.rlBtn05})
    protected RelativeLayout mRLwindows[];
    @Bind({R.id.tv01, R.id.tv02, R.id.tv03, R.id.tv04, R.id.tv05})
    protected TextView mWinName[];
    private List<NWindowInfo> mWindowInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint);
        ButterKnife.bind(this);
        mWindowInfoList = new ArrayList<>();
        getWIndowInfo();

        for (int i = 0; i < 5; i++) {
            mRLwindows[i].setOnClickListener(this);
        }
    }

    public void getWIndowInfo() {
//
//        NetRequest.APIInstance2.getSystemNew("999", "v8Em54eWXYPBE4A")
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(results -> {
//                    if (results.code.equals("0")) {
//                        mWindowInfoList = results.data;
//                        for (int i = 0; i < mWindowInfoList.size(); i++) {
//                            mWinName[i].setText(mWindowInfoList.get(i).name);
//                        }
//                    } else {
//                        if (results.message != null) {
//                            ToastUtil.shortShowText(results.message);
//                        }
//                    }
//                }, throwable -> {
//                    ToastUtil.shortShowText("刷新失败，请检查当前网络");
//                });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rlBtn01:
                break;
            case R.id.rlBtn02:
                break;
            case R.id.rlBtn03:
                break;
            case R.id.rlBtn04:
                break;
            case R.id.rlBtn05:
                break;


        }

    }
}
