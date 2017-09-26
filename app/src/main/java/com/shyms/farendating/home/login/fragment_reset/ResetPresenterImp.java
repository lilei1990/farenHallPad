package com.shyms.farendating.home.login.fragment_reset;


import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;

import com.shyms.farendating.home.login.OnLoginListener;
import com.shyms.farendating.model.BaseObject;
import com.shyms.farendating.model.UserInfo;
import com.shyms.farendating.utils.UserManager;

public class ResetPresenterImp implements ResetPresenter, OnLoginListener {
    private ResetView view;
    private ResetModelImp modelImp;
    private Context context;
    private TimeCount timeCount;

    public ResetPresenterImp(ResetView view, Context context) {
        this.view = view;
        this.context = context;
        modelImp = new ResetModelImp(this);
        timeCount = new TimeCount(30000, 1000);
    }

    @Override
    public void getResetCode(String username) {
        if (username != null && username.length() < 11) {
            view.showMsg("您输入11位数的手机号码");
            return;
        }
        modelImp.getRestCode(username);
    }

    @Override
    public void getResetPassword(boolean flag, String confirmPwd, String username, String password, String verify_code) {
        if (username != null && username.length() < 11) {
            view.showMsg("您输入11位数的手机号码");
            return;
        }
        if (TextUtils.isEmpty(verify_code)) {
            view.showMsg("验证码不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            view.showMsg("请输入至少6个字符的密码");
            return;
        }
        if (password.length() < 6) {
            view.showMsg("请再次输入密码确认");
            return;
        }
        if (confirmPwd == null || confirmPwd.length() == 0) {
            view.showMsg("请再次输入密码确认");
            return;
        }
        if (confirmPwd.equals(password)) {
            password = confirmPwd;
        } else {
            view.showMsg("您两次输入的密码不一致！");
            return;
        }
        if (!flag) {
            view.showMsg("请勾选——我已经阅读并同意隐私条款");
            return;
        }
        modelImp.getRestPassword(username,password,verify_code);
    }

    @Override
    public void onSuccess(BaseObject baseObject) {
        view.showMsg("发送成功");
        view.sendCodeCompleted();
        timeCount.start();
    }

    @Override
    public void onSuccess(UserInfo userInfo) {
//        UserManager.getInstance(context).setUserInfo(userInfo);
//        UserManager.getInstance(context).setLoginStatus(true);
        timeCount.cancel();
        view.showMsg("修改成功");
        view.onCompleted();
    }

    @Override
    public void onFailed(String msg) {
        view.showMsg(msg);
    }

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            view.onFinish();
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            view.onTick();
        }
    }
}
