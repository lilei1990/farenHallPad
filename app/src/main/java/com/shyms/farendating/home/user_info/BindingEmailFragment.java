package com.shyms.farendating.home.user_info;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.shyms.faren.R;
import com.shyms.farendating.http.AsyncHttpCallBack;
import com.shyms.farendating.http.AsyncHttpConfig;
import com.shyms.farendating.http.AsyncHttpRequest;
import com.shyms.farendating.utils.ContentCode;
import com.shyms.farendating.utils.UserManager;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hokas.base.BaseFragment;
import me.hokas.base.BaseObject;

public class BindingEmailFragment extends BaseFragment implements AsyncHttpCallBack {


    @Bind(R.id.editText_username)
    EditText editTextUsername;
    @Bind(R.id.identifying_codeode)
    EditText identifyingCodeode;
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

    private TimeCount timeCount;
    private int num = 30;

    public BindingEmailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_binding_email, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    @Override
    public void initData() {
        timeCount = new TimeCount(31000, 1000);
        btnSendIdentifyingCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送验证码
                AsyncHttpRequest.getEsond(editTextUsername.getText().toString().trim(), "0", BindingEmailFragment.this);
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //邮箱绑定
                AsyncHttpRequest.getBindingEmail(editTextUsername.getText().toString().trim(),
                        identifyingCodeode.getText().toString().trim(),
                        UserManager.getInstance().getLastUserInfo().getData()
                                .getLog_verify_code(),BindingEmailFragment.this);
            }
        });
    }

    @Override
    public void onSuccess(int what, int statusCode, JSONObject response) {
        BaseObject baseObject = JSON.parseObject(response.toString(), BaseObject.class);
        String code = baseObject.getCode();
        if (AsyncHttpConfig.WITH_50004 == what) {
            if ("0".equals(code)) {
                showToast("发送成功");
                btnSendIdentifyingCode.setBackgroundResource(R.mipmap.btn_green);
                btnSendIdentifyingCode.setClickable(false);
                timeCount.start();
            } else {
                showToast(baseObject.getMessage());
            }
        }
        if (AsyncHttpConfig.WITH_60008 == what) {
            if (ContentCode.WHAT_0.equals(code)) {
                showToast("邮箱绑定成功");
                timeCount.cancel();
            } else {
                showToast(baseObject.getMessage());
            }
        }
    }

    @Override
    public void onFailure(int what, int statusCode, JSONObject errorResponse) {

    }

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            btnSendIdentifyingCode.setClickable(true);
            btnSendIdentifyingCode.setBackgroundResource(R.mipmap.btn_bg);
            btnSendIdentifyingCode.setText("发送邮箱验证码");
            num = 30;
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            btnSendIdentifyingCode.setText("（" + num-- + "）后可再次发送");
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
