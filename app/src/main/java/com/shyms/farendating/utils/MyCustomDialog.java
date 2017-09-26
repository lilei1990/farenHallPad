package com.shyms.farendating.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.shyms.faren.R;


/**
 * 地图自定义弹窗
 * Created by hokas on 2015/7/21.
 */
public class MyCustomDialog extends Dialog {

    public TextView btn_end;

    //定义回调事件，用于dialog的点击事件
    public interface OnCustomDialogListener {
        void back();

        void btnEnd();
    }

    private String name, msg;
    private OnCustomDialogListener customDialogListener;

    public MyCustomDialog(Context context, String name, String msg) {
        super(context);
        this.name = name;
        this.msg = msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_help);
        TextView tv_dialog_title = (TextView) findViewById(R.id.tv_dialog_title);
        TextView tv_dialog_content = (TextView) findViewById(R.id.tv_dialog_content);
        TextView btn = (TextView) findViewById(R.id.tv_dialog_enter);
        tv_dialog_title.setText(name);
        tv_dialog_content.setText(msg);
        btn_end = (TextView) findViewById(R.id.tv_dialog_end);
        btn.setOnClickListener(new clickListener());
        btn_end.setOnClickListener(new clickListener());
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
            MyCustomDialog.this.dismiss();
        }
    }

    public void setClicklistener(OnCustomDialogListener clickListenerInterface) {
        this.customDialogListener = clickListenerInterface;
    }
}
