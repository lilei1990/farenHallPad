package com.shyms.farendating.home.login.fragment_code_login;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;

import com.shyms.farendating.home.login.OnLoginListener;
import com.shyms.farendating.model.BaseObject;
import com.shyms.farendating.model.UserInfo;
import com.shyms.farendating.utils.UserManager;

/**
 * Created by hks on 2016/3/23.
 */
public class CodeLoginPresenterImp implements CodeLoginPresenter, OnLoginListener {
    private CodeLoginView view;
    private CodeLoginModelImp modelImp;
    private Context context;
    private TimeCount timeCount;

    public CodeLoginPresenterImp(CodeLoginView view,Context context) {
        this.view = view;
        this.context =context;
        modelImp = new CodeLoginModelImp(this);
        timeCount = new TimeCount(30000, 1000);
    }


    @Override
    public void getPhoneCode(String username) {
        if (TextUtils.isEmpty(username) ){
            view.showMsg("请您输入正确的手机号码");
            return;
        }
        if (username.length() !=11) {
            view.showMsg("您输入11位数的手机号码");
            return;
        }
        modelImp.getPhoneCode(username);
    }

    @Override
    public void getCodeLogin(String mobile, String verify_code) {
        modelImp.getCodeLogin(mobile, verify_code);
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
        view.showMsg("绑定成功");
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
