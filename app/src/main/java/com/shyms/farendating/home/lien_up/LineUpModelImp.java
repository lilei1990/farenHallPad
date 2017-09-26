package com.shyms.farendating.home.lien_up;

import android.util.Log;

import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.home.lien_up.model.QueryLineUpInfo;
import com.shyms.farendating.home.lien_up.model.UserGiveUp;
import com.shyms.farendating.home.lien_up.model.UserQueryLineUp;
import com.shyms.farendating.home.lien_up.model.UserTakeNumber;
import com.shyms.farendating.home.lien_up.model.WindowsLoginInfo;
import com.shyms.farendating.home.lien_up.model.WindowsSystemInfo;
import com.shyms.farendating.utils.ContentCode;
import com.shyms.farendating.utils.LogcatUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LineUpModelImp implements LineUpModel {
    private OnLineUpListener listener;

    public LineUpModelImp(OnLineUpListener listener) {
        this.listener = listener;
    }

    @Override
    public void getWindowSystem(String username, String password) {
        LogcatUtil.d("WICK", "开始网络请求getWindowSystem");
        NetRequest.APIInstance2.getWindowSystem(username, password).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(windowsLoginInfo -> {
                    Log.d("LineUpModelImp", windowsLoginInfo.toString());
                    if ("0".equals(windowsLoginInfo.getCode()))
                        listener.onSuccess(windowsLoginInfo);
                    else
                        listener.onFailure(windowsLoginInfo.getMessage().toString());
                }, throwable -> {
                    listener.onFailure("服务器连接超时,请重试！");
                });
    }

    @Override
    public void getSystem(String siteId) {
        NetRequest.APIInstance2.getSystem(siteId).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(windowsSystemInfo -> {
                    Log.d("LineUpModelImp", windowsSystemInfo.toString());
                    if ("0".equals(windowsSystemInfo.getCode()))
                        listener.onSuccess(windowsSystemInfo);
                    else
                        listener.onFailure(windowsSystemInfo.getMessage().toString());
                }, throwable -> {
                    listener.onFailure("服务器连接超时,请重试！");
                });
    }

    @Override
    public void getCheckQueue(String system) {

        NetRequest.APIInstance2.getCheckQueue(system).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(queryLineUpInfo -> {
                    Log.d("LineUpModelImp", queryLineUpInfo.toString());
                    if ("0".equals(queryLineUpInfo.getCode())) {
                        listener.onSuccess(queryLineUpInfo);
                    } else {
                        listener.onFailure(queryLineUpInfo.getMessage().toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        listener.onFailure("服务器连接超时,请重试！");
                    }
                });
    }

    @Override
    public void getUserGet(String mobile, String system) {
        NetRequest.APIInstance2.getUserGet(mobile, system)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userTakeNumber -> {
                    Log.d("LineUpModelImp", userTakeNumber.toString());
                    if ("0".equals(userTakeNumber.getCode())) {
                        listener.onSuccess(userTakeNumber, system);
                    } else {
                        listener.onFailure(userTakeNumber.getMessage().toString());
                    }
                }, throwable -> {
                    listener.onFailure("服务器连接超时,请重试！");
                });
    }

    public void getUserAppointAum(String system, String mobile, String uid) {
        NetRequest.APIInstance2.getUserAppoint(ContentCode.log_verify_code, system, mobile, uid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UserTakeNumber>() {
                    @Override
                    public void call(UserTakeNumber results) {
                        if ("0".equals(results.getCode())) {
                            listener.onSuccess(results, system);
                        } else {
                            listener.onFailure(results.getMessage().toString());
                        }
                    }
                }, throwable -> {
                    listener.onFailure("服务器连接超时,请重试！");
                });
    }

    @Override
    public void getGiveUp(String mobile, String system) {
        NetRequest.APIInstance2.getGiveUp(mobile, system)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userGiveUp -> {
                    if ("0".equals(userGiveUp.getCode())) {
                        listener.onSuccess(userGiveUp, system);
                    } else {
                        listener.onFailure(userGiveUp.getMessage().toString());
                    }
                }, throwable -> {
                    listener.onFailure("服务器连接超时,请重试！");
                });
    }

    @Override
    public void getUserStatus(String queue_id, String system) {
        //TODO   频繁查询用户状态
        NetRequest.APIInstance2.getUserStatus(queue_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userQueryLineUp -> {
                    if ("0".equals(userQueryLineUp.getCode())) {
                        listener.onSuccess(userQueryLineUp, system);
                    } else {
                        listener.onFailure(userQueryLineUp.getMessage().toString());
                    }
                }, throwable -> {
                    LogcatUtil.d("服务器连接超时,请重试！");
                });
    }

    public interface OnLineUpListener {
        void onSuccess(QueryLineUpInfo queryLineUpInfo);

        void onSuccess(UserGiveUp userGiveUp, String system);

        void onSuccess(UserQueryLineUp userQueryLineUp, String queue_id);

        void onSuccess(WindowsSystemInfo windowsSystemInfo);

        void onSuccess(WindowsLoginInfo windowsLoginInfo);

        void onSuccess(UserTakeNumber userTakeNumber, String system);

        void onFailure(String msg);
    }
}
