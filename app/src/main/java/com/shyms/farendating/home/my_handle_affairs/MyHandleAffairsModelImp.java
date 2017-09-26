package com.shyms.farendating.home.my_handle_affairs;

import android.text.TextUtils;
import android.util.Log;

import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.home.my_handle_affairs.model.HandleAffairsModel;
import com.shyms.farendating.model.NSign;
import com.shyms.farendating.utils.GlobalConstant;
import com.shyms.farendating.utils.UserManager;
import com.shyms.farendating.utils.Util;

import me.hokas.utils.SPUtil;
import me.hokas.utils.ToastUtil;
import rx.android.schedulers.AndroidSchedulers;

public class MyHandleAffairsModelImp implements MyHandleAffairsModel {
    private OnHandleAffairsListener listener;

    public MyHandleAffairsModelImp(OnHandleAffairsListener listener) {
        this.listener = listener;
    }

    @Override
    public void getAffairRecord(String uid) {
        if (TextUtils.isEmpty(Util.getToken())) {
            getServerToken(uid);
        } else {
            affairRecord(uid);
        }
    }

    private void affairRecord(String uid) {
        NSign nSign = new NSign();
        NetRequest.APIInstance.affairRecord(uid, UserManager.getInstance().getLastUserInfo().getData().getLog_verify_code()
                , nSign.getTimestamp(), Util.getToken(), nSign.getSign())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handleAffairsModel -> {
                    Log.d("MyHandleAffairsModelImp", handleAffairsModel.toString());
                    if ("0".equals(handleAffairsModel.getCode())) {
                        listener.onSuccess(handleAffairsModel);
                    } else if (handleAffairsModel.getCode().equals(GlobalConstant.TOKEN_ERROR)) {
                        getServerToken(uid);
                    } else {
                        listener.onFailure(handleAffairsModel.getMessage().toString());
                    }
                }, throwable -> {
                    listener.onFailure("未有办理事项");
                });
    }

    private void getServerToken(String uid) {
        String password = Util.getMD5("zw@shyms");
        NetRequest.getToken("appaccount", password)
                .subscribe(result -> {
                    if (result.getCode().equals(com.shyms.farendating.GlobalConstant.REQUEST_SUCCESS)) {
                        SPUtil.put(com.shyms.farendating.GlobalConstant.USER_TOKEN_VALID, result.getData());
                        affairRecord(uid);
                    } else {
                        ToastUtil.shortShowText(result.getMessage());
                    }
                }, throwable -> {
                    ToastUtil.shortShowText("获取认证失败，请重新登录");
                });
    }

    public interface OnHandleAffairsListener {
        void onSuccess(HandleAffairsModel handleAffairsModel);

        void onFailure(String msg);
    }
}
