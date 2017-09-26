package com.shyms.farendating.home.login.fragment_sf;


import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;

import com.shyms.farendating.home.login.OnLoginListener;
import com.shyms.farendating.model.BaseObject;
import com.shyms.farendating.model.UserInfo;
import com.shyms.farendating.utils.UserManager;

import me.hokas.utils.ToastUtil;

public class SfPresenterImp implements SfPresenter, OnLoginListener {
    private SfView view;
    private SfModelImp modelImp;
    private TimeCount timeCount;
    private Context context;

    public SfPresenterImp(SfView view,Context context) {
        this.view = view;
        this.context = context;
        modelImp = new SfModelImp(this);
        timeCount = new TimeCount(30000, 1000);
    }

    @Override
    public void getRegisterCodes(String mobile) {
        if (TextUtils.isEmpty(mobile) ){
            view.showMsg("请您输入正确的手机号码");
            return;
        }
        if (mobile.length() <11) {
            view.showMsg("您输入11位数的手机号码");
            return;
        }
        modelImp.getRegisterCodes(mobile);
    }

    @Override
    public void getSF(String verify_code, String id_card, String mobile, String mobile_code) {
        modelImp.getSF(verify_code, id_card, mobile, mobile_code);
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
        view.showMsg("绑定成功");
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
