package com.shyms.farendating.home.login.fragment_code_login;


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
import com.shyms.farendating.movie.MovieActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hokas.base.BaseFragment;

/**
 * 手机验证码登陆
 */
public class CodeLoginFragment extends BaseFragment implements View.OnClickListener, CodeLoginView {


    @Bind(R.id.editText_username)
    EditText editTextUsername;
    @Bind(R.id.identifying_code)
    EditText identifyingCode;
    @Bind(R.id.btn_send_identifying_code)
    Button btnSendIdentifyingCode;
    @Bind(R.id.checkBox_register_agreement)
    CheckBox checkBoxRegisterAgreement;
    @Bind(R.id.textView_register_agreement)
    TextView textViewRegisterAgreement;
    @Bind(R.id.register_agreement_layout)
    LinearLayout registerAgreementLayout;
    @Bind(R.id.button_register)
    Button buttonRegister;

    private CodeLoginPresenterImp presenterImp;
    private int num = 30;

    public CodeLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_code_login, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        tvTitle.setText("手机登录");
        presenterImp = new CodeLoginPresenterImp(this,getActivity());
        ivBack.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
        btnSendIdentifyingCode.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivBack:
                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case R.id.btn_send_identifying_code:
                presenterImp.getPhoneCode(editTextUsername.getText().toString().trim());
                break;
            case R.id.button_register:
                presenterImp.getCodeLogin(editTextUsername.getText().toString().trim(),identifyingCode.getText().toString().trim());
                break;
        }
    }

    @Override
    public void onCompleted() {
        GotoActivity(MovieActivity.class);
        getActivity().finish();
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
