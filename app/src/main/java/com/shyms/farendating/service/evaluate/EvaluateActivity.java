package com.shyms.farendating.service.evaluate;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shyms.faren.R;
import com.shyms.farendating.http.AsyncHttpRequest;
import com.shyms.farendating.utils.UserManager;


public class EvaluateActivity extends Dialog {

    private TextView tv_title;
    private ImageView imageView5;
    private TextView tv_color;
    public TextView evaluateName;
    private LinearLayout ll_fcmy;
    private LinearLayout ll_my;
    private LinearLayout ll_yb;
    private LinearLayout ll_bmy;
    private LinearLayout linearLayout;
    private OnEvaluteDialogListener evaluteDialogListener;
    private String name;

//    public EvaluateActivity( ){
//        super();
//    }

    public EvaluateActivity(Context context,String name) {
        super(context);
        this.name = name;
    }

    public EvaluateActivity(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected EvaluateActivity(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_evaluate);
        initViews();
        ll_fcmy.setOnClickListener(new ClickListener());
        ll_my.setOnClickListener(new ClickListener());
        ll_yb.setOnClickListener(new ClickListener());
        ll_bmy.setOnClickListener(new ClickListener());

    }


    private void initViews() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        imageView5 = (ImageView) findViewById(R.id.imageView5);
        tv_color = (TextView) findViewById(R.id.tv_color);
        evaluateName = (TextView) findViewById(R.id.tv_evaluate);
        ll_fcmy = (LinearLayout) findViewById(R.id.ll_fcmy);
        ll_my = (LinearLayout) findViewById(R.id.ll_my);
        ll_yb = (LinearLayout) findViewById(R.id.ll_yb);
        ll_bmy = (LinearLayout) findViewById(R.id.ll_bmy);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        evaluateName.setText(name);
    }

    //定义回调事件，用于dialog的点击事件
    public interface OnEvaluteDialogListener {

        void ll_fcmy();

        void ll_my();

        void ll_yb();

        void ll_bmy();
    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_fcmy:
                    evaluteDialogListener.ll_fcmy();
                    break;
                case R.id.ll_my:
                    evaluteDialogListener.ll_my();
                    break;
                case R.id.ll_yb:
                    evaluteDialogListener.ll_yb();
                    break;
                case R.id.ll_bmy:
                    evaluteDialogListener.ll_bmy();
                    break;
            }
            EvaluateActivity.this.dismiss();


        }
    }

    public void setClickListener(OnEvaluteDialogListener evaluteDialogListener) {
        this.evaluteDialogListener = evaluteDialogListener;
    }

//    public void setEvaluateName(String serviceName) {
//        this.name = serviceName;
//        evaluateName.setText(name);
//    }

}
