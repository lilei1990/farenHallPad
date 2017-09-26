package com.shyms.farendating.home.user_info;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shyms.faren.R;
import com.shyms.farendating.home.login.fragment_reset.ResetPresenterImp;
import com.shyms.farendating.home.login.fragment_reset.ResetView;
import com.shyms.farendating.movie.MovieActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hokas.base.BaseFragment;

public class MyResetFragment extends BaseFragment implements View.OnClickListener, ResetView {


    @Bind(R.id.editText_username)
    EditText editTextUsername;
    @Bind(R.id.identifying_code)
    EditText identifyingCode;
    @Bind(R.id.btn_send_identifying_code)
    Button btnSendIdentifyingCode;
    @Bind(R.id.editText_password)
    EditText editTextPassword;
    @Bind(R.id.editText_confirm_password)
    EditText editTextConfirmPassword;
    @Bind(R.id.checkBox_register_agreement)
    CheckBox checkBoxRegisterAgreement;
    @Bind(R.id.textView_register_agreement)
    TextView textViewRegisterAgreement;
    @Bind(R.id.register_agreement_layout)
    LinearLayout registerAgreementLayout;
    @Bind(R.id.button_register)
    Button buttonRegister;

    private ResetPresenterImp presenterImp;
    private int num = 30;
    public MyResetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_reset, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    @Override
    public void initData() {
        btnSendIdentifyingCode.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
        presenterImp = new ResetPresenterImp(this,getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_identifying_code:
                presenterImp.getResetCode(editTextUsername.getText().toString().trim());
                break;
            case R.id.button_register:
                presenterImp.getResetPassword(checkBoxRegisterAgreement.isChecked(), editTextConfirmPassword.getText().toString().trim(),
                        editTextUsername.getText().toString().trim(), editTextPassword.getText().toString().trim(),
                        identifyingCode.getText().toString().trim());
                break;
        }
    }

    @Override
    public void onCompleted() {
        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void sendCodeCompleted() {
        btnSendIdentifyingCode.setBackgroundResource(R.mipmap.btn_green);
        btnSendIdentifyingCode.setClickable(false);
    }

    @Override
    public void showMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void onFinish() {
        btnSendIdentifyingCode.setClickable(true);
        btnSendIdentifyingCode.setBackgroundResource(R.mipmap.btn_bg);
        btnSendIdentifyingCode.setText("发送短信验证码");
        num = 30;
    }

    @Override
    public void onTick() {
        btnSendIdentifyingCode.setText("（" + num-- + "）后可再次发送");
    }
}
