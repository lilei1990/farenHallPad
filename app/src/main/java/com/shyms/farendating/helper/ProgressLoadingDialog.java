package com.shyms.farendating.helper;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shyms.faren.R;


public class ProgressLoadingDialog extends AlertDialog {

    private String mMessage;
    private TextView mMessageText;
    private boolean mIndeterminate;
    private boolean mProgressVisiable;
    private ProgressBar mProgress;
    private Context mContext;

    public ProgressLoadingDialog(Context context) {

        super(context, R.style.selectorDialogSS);
        mContext = context;
    }
//    public ProgressLoadingDialog(Context context) {
//
//        super(context);
//        mContext = context;
//    }

    public ProgressLoadingDialog(Context context, String message) {
        super(context, R.style.selectorDialogSS);
        this.mMessage = message;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog);
        setCanceledOnTouchOutside(false);
        mMessageText = (TextView) findViewById(R.id.tv_loading);
        mProgress = (ProgressBar) findViewById(android.R.id.progress);
//        mProgress.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
        setMessageAndView();
//        setIndeterminate(mIndeterminate);
    }

    private void setMessageAndView() {
        if (!TextUtils.isEmpty(mMessage)) {
            mMessageText.setText(mMessage);
        }
//        mProgress.setVisibility(mProgressVisiable ? View.VISIBLE : View.GONE);
    }

    public void setProgressVisiable(boolean progressVisiable) {
        mProgressVisiable = progressVisiable;
    }

    public void setIndeterminate(boolean indeterminate) {
        if (mProgress != null) {
            mProgress.setIndeterminate(indeterminate);
        } else {
            mIndeterminate = indeterminate;
        }
    }
}
