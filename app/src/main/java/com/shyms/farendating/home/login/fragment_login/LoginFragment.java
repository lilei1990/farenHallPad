package com.shyms.farendating.home.login.fragment_login;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.shyms.faren.R;
import com.shyms.farendating.home.login.LoginView;
import com.shyms.farendating.home.login.fragment_sf.AlertDialogS;
import com.shyms.farendating.home.login.zxing.client.android.CaptureActivity;
import com.shyms.farendating.http.AsyncHttpCallBack;
import com.shyms.farendating.http.AsyncHttpConfig;
import com.shyms.farendating.http.AsyncHttpRequest;
import com.shyms.farendating.model.UserInfo;
import com.shyms.farendating.movie.MovieActivity;
import com.shyms.farendating.utils.GlobalConstant;
import com.shyms.farendating.utils.UpdateAppManager;
import com.shyms.farendating.utils.UserManager;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hokas.base.BaseFragment;
import me.hokas.base.BaseObject;
import me.hokas.utils.PreferencesUtil;
import me.hokas.utils.ToastUtil;

/**
 * 用户密码登陆
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener, LoginViews, AsyncHttpCallBack {

    @Bind(R.id.sign_on)
    TextView signOn;
    @Bind(R.id.user_name)
    EditText userName;
    @Bind(R.id.qr_sign_in)
    ImageView qrSignIn;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.tv_a)
    TextView tvA;
    @Bind(R.id.tv_mail)
    TextView tvMail;
    @Bind(R.id.forget_password)
    TextView forgetPassword;
    @Bind(R.id.sign_in)
    Button signIn;
    @Bind(R.id.tv_yms)
    TextView tvYms;

    private LoginView loginView;
    private String login_username, login_password;
    private LoginPresenterImp presenterImp;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }


    @Override
    public void initData() {
        loginView = (LoginView) getActivity();
        tvYms.setText("佛山市禅城区政府版权所有   V" + UpdateAppManager.getVersionName(getActivity()));

        tvA.setOnClickListener(this);
        tvMail.setOnClickListener(this);
        qrSignIn.setOnClickListener(this);
        signOn.setOnClickListener(this);
        signIn.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        presenterImp = new LoginPresenterImp(this, getActivity());
    }

    @Override
    public void onSuccess(int what, int statusCode, JSONObject response) {
        BaseObject baseObject = JSON.parseObject(response.toString(), BaseObject.class);
        if (AsyncHttpConfig.WITH_50000 == what) {
            mActivityHelper.dismissProgressDialog();
            if ("0".equals(baseObject.getCode())) {
                UserInfo userInfo = JSON.parseObject(response.toString(), UserInfo.class);
                ToastUtil.shortShowText("登录成功");
                UserManager.getInstance().setUserInfo(userInfo);
                UserManager.getInstance().setLoginStatus(true);
                PreferencesUtil.setIsLogin(true);//设置登录标志位  登录成功后切换登陆后的UI
                PreferencesUtil.setLoginStatus(true);
                onCompleted();
            } else if (GlobalConstant.TOKEN_ERROR.equals(baseObject.getCode())) {
                getServerToken();
            } else {
                showToast(baseObject.getMessage());
            }
        }
    }

    @Override
    public void onFailure(int what, int statusCode, JSONObject errorResponse) {
        mActivityHelper.dismissProgressDialog();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_a:
                loginView.toCodeFragment();
                break;
            case R.id.tv_mail:
                loginView.toMailFragment();
                break;
            case R.id.qr_sign_in:
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.sign_on:
                loginView.toRegisterFragment();
                break;
            case R.id.forget_password:
                loginView.toForgetPasswordFragment();
                break;
            case R.id.sign_in:
                if (TextUtils.isEmpty(getToken())) {
                    getServerToken();
                } else {
                    getNewLogin(userName.getText().toString().trim(), password.getText().toString().trim());
                }
                break;
        }
    }

    @Override
    protected void tokenIsValid() {
        super.tokenIsValid();
        getNewLogin(userName.getText().toString().trim(), password.getText().toString().trim());
    }

    private void getNewLogin(String username, String password) {
        if (username == null || TextUtils.isEmpty(username)) {
            ToastUtil.shortShowText("用户名不能为空");
            return;
        }
        if (password == null || TextUtils.isEmpty(password)) {
            ToastUtil.shortShowText("密码不能为空");
            return;
        }
        mActivityHelper.showProgressDialog("正在登录");
        AsyncHttpRequest.getNewLogin(username, password, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == -1) {
                final String code = data.getStringExtra("scan_result");
                Log.d("LoginFragment", code);
                if (code != null && !code.equals("")) {
                    if (code.startsWith("http")) {//网址
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(code);
                        intent.setData(content_url);
                        startActivity(intent);
                    } else if (code.startsWith("log")) {//登入
                        login_username = code.split("&")[1];
                        login_password = code.split("&")[2];
                        final AlertDialogS ad = new AlertDialogS(getActivity());
                        ad.setTitle("禅城区行政服务中心（智慧新城大厅）Pad平板电脑借用协议");
                        ad.setMessage(R.string.padxieyi);
                        ad.setPositiveButton("不同意", new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                ad.dismiss();
                            }
                        });
                        ad.setNegativeButton("同意", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                presenterImp.getQRLogin(login_username, login_password);
                                ad.dismiss();
                            }
                        });

                    } else if (code.startsWith("id")) {
                        String[] aa = code.split("&");
                        loginView.toSfFragment(aa[1], aa[2]);
                    } else {//内容
                        showToast(code);
                    }
                }
            }
        }
    }

    @Override
    public void showMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void onCompleted() {
        UserManager.getInstance().setLoginInfo(userName.getText().toString(), password.getText().toString());
        GotoActivity(MovieActivity.class);
        getActivity().finish();
    }
}
