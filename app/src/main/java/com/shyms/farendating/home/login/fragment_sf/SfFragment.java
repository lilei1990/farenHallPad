package com.shyms.farendating.home.login.fragment_sf;


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

public class SfFragment extends BaseFragment implements SfView {


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

    private String id_card,verify_code;
    private SfPresenterImp presenterImp;
    private int num = 30;


    public SfFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sf, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        tvTitle.setText("绑定身份证注册");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

    }

    @Override
    public void initData() {
        presenterImp = new SfPresenterImp(this,getActivity());
        Bundle bundle = getArguments();
        id_card = bundle.getString("id_card");
        verify_code = bundle.getString("verify_code");
        btnSendIdentifyingCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterImp.getRegisterCodes(editTextUsername.getText().toString());
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterImp.getSF(verify_code,id_card,editTextUsername.getText().toString(),identifyingCode.getText().toString());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onCompleted() {
//        GotoActivity(MovieActivity.class);
//        getActivity().finish();
        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
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

    @Override
    public void sendCodeCompleted() {
        btnSendIdentifyingCode.setBackgroundResource(R.mipmap.btn_green);
        btnSendIdentifyingCode.setClickable(false);
    }
}
