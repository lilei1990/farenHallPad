package com.shyms.farendating.utils;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.shyms.faren.R;

public class CustomLoadingDialog extends AlertDialog {

	private String message;
	private TextView messageText;

	public CustomLoadingDialog(Context context) {
		super(context, R.style.selectorDialogSS);
	}

	public CustomLoadingDialog(Context context, String message) {
		super(context, R.style.selectorDialogSS);
		this.message = message;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_dialog);
		setCanceledOnTouchOutside(false);
		messageText = (TextView) findViewById(R.id.tv_loading);
		if (!TextUtils.isEmpty(message)) {
			messageText.setText(message);
		}
	}
}
