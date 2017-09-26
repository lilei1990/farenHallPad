package com.shyms.farendating.home.login.fragment_mail_login;

import android.content.Context;
import android.os.CountDownTimer;

import com.shyms.farendating.home.login.OnLoginListener;
import com.shyms.farendating.model.BaseObject;
import com.shyms.farendating.model.UserInfo;
import com.shyms.farendating.utils.UserManager;

public class MailLoginPresenterImp implements MailLoginPresenter, OnLoginListener {

    private MailLoginView view;
    private MailLoginModelImp modelImp;
    private Context context;
    private TimeCount timeCount;

    public MailLoginPresenterImp(MailLoginView view, Context context) {
        this.view = view;
        this.context = context;
        modelImp = new MailLoginModelImp(this);
        timeCount = new TimeCount(30000, 1000);
    }

    @Override
    public void getEmailCode(String Email, String request) {
        modelImp.getEmailCode(Email, request);
    }

    @Override
    public void getEmailLogin(String Email, String verify_code) {
        modelImp.getEmailLogin(Email, verify_code);
    }

    @Override
    public void onSuccess(BaseObject baseObject) {
        view.showMsg("发送成功");
        view.sendCodeCompleted();
        timeCount.start();
    }

    @Override
    public void onSuccess(UserInfo userInfo) {
        UserManager.getInstance().setUserInfo(userInfo);
        UserManager.getInstance().setLoginStatus(true);
        timeCount.cancel();
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
