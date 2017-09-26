package com.shyms.farendating.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shyms.faren.R;


/**
 * 弹窗
 * Created by hokas on 2015/7/21.
 */
public class MyHintDialog extends Dialog {

    public TextView btn_end;
    public TextView tv_dialog_content;

    //定义回调事件，用于dialog的点击事件
    public interface OnCustomDialogListener {
        void back();

        void btnEnd();
    }

    private String name, msg;
    private OnCustomDialogListener customDialogListener;


    public MyHintDialog(Context context, String name, String msg) {
        super(context, R.style.selectorDialog);
        this.name = name;
        this.msg = msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_new);
        TextView tv_dialog_title = (TextView) findViewById(R.id.tv_dialog_title);
        tv_dialog_content = (TextView) findViewById(R.id.tv_dialog_content);
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
            MyHintDialog.this.dismiss();
        }
    }

    public void setClickListener(OnCustomDialogListener clickListenerInterface) {
        this.customDialogListener = clickListenerInterface;
    }

//    public void setMsg(String msg) {
//        tv_dialog_content.setText(msg);
//    }
}
