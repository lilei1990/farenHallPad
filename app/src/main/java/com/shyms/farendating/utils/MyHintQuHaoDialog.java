package com.shyms.farendating.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shyms.faren.R;


/**
 * 弹窗
 * Created by hokas on 2015/7/21.
 */
public class MyHintQuHaoDialog extends Dialog {

    TextView tvNumber;
    TextView tvSystemNumber;
    TextView tvWindowsNumber;
    TextView tvDialogEnd;
    TextView tvDialogEnter;
    EditText etPhoneNumber;
    LinearLayout llIsLogin;
    private int sign;

    //定义回调事件，用于dialog的点击事件
    public interface OnCustomDialogListener {
        void back();

        void btnEnd();
    }

    private String number, systemNumber, windowsNumber;
    private OnCustomDialogListener customDialogListener;


    public MyHintQuHaoDialog(Context context, String number, String systemNumber, String windowsNumber) {
        super(context, R.style.selectorDialogs);
        this.number = number;
        this.systemNumber = systemNumber;
        this.windowsNumber = windowsNumber;
    }

    public MyHintQuHaoDialog(Context context, String number, String systemNumber, String windowsNumber, int sign) {
        super(context, R.style.selectorDialogs);
        this.number = number;
        this.systemNumber = systemNumber;
        this.windowsNumber = windowsNumber;
        this.sign = sign;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_qu_hao);
        tvNumber = (TextView) findViewById(R.id.tvNumber);
        TextView mTitle = (TextView) findViewById(R.id.tv_dialog_title);
        tvSystemNumber = (TextView) findViewById(R.id.tvSystemNumber);
        tvWindowsNumber = (TextView) findViewById(R.id.tvWindowsNumber);
        tvDialogEnd = (TextView) findViewById(R.id.tv_dialog_end);
        tvDialogEnter = (TextView) findViewById(R.id.tv_dialog_enter);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        llIsLogin = (LinearLayout) findViewById(R.id.llIsLogin);
        if (sign == 1) {
            mTitle.setText("预约取号");
            mTitle.setBackgroundColor(Color.parseColor("#8B008B"));
        }

        tvNumber.setText(number);
        tvSystemNumber.setText(systemNumber);
        tvWindowsNumber.setText(windowsNumber);
        tvDialogEnter.setOnClickListener(new clickListener());
        tvDialogEnd.setOnClickListener(new clickListener());
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_dialog_enter:
                    customDialogListener.back();
                    break;
                case R.id.tv_dialog_end:
                    customDialogListener.btnEnd();
                    break;
            }
            MyHintQuHaoDialog.this.dismiss();
        }
    }

    public void setClickListener(OnCustomDialogListener clickListenerInterface) {
        this.customDialogListener = clickListenerInterface;
    }

    public void setTvSystemNumber(TextView tvSystemNumber) {
        this.tvSystemNumber = tvSystemNumber;
    }

    public void setTvWindowsNumber(TextView tvWindowsNumber) {
        this.tvWindowsNumber = tvWindowsNumber;
    }

    public void setTvNumber(TextView tvNumber) {
        this.tvNumber = tvNumber;
    }

    public EditText getEtPhoneNumber() {
        return etPhoneNumber;
    }

    public void setEtPhoneNumber(EditText etPhoneNumber) {
        this.etPhoneNumber = etPhoneNumber;
    }

    public void setLlIsLoginGone() {
        llIsLogin.setVisibility(View.GONE);
    }

    public void setLlIsLoginVisible() {
        llIsLogin.setVisibility(View.VISIBLE);
    }
}
