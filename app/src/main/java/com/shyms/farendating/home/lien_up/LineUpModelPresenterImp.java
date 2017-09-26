package com.shyms.farendating.home.lien_up;

import android.content.Context;
import android.view.View;

import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.home.lien_up.model.NUserNumber;
import com.shyms.farendating.home.lien_up.model.QueryLineUpInfo;
import com.shyms.farendating.home.lien_up.model.UserGiveUp;
import com.shyms.farendating.home.lien_up.model.UserQueryLineUp;
import com.shyms.farendating.home.lien_up.model.UserTakeNumber;
import com.shyms.farendating.home.lien_up.model.WindowsLoginInfo;
import com.shyms.farendating.home.lien_up.model.WindowsSystemInfo;
import com.shyms.farendating.utils.ContentCode;
import com.shyms.farendating.utils.CustomLoadingDialog;
import com.shyms.farendating.utils.GlobalConstant;
import com.shyms.farendating.utils.LogcatUtil;
import com.shyms.farendating.utils.MyHintDialog;
import com.shyms.farendating.utils.MyHintQuHaoDialog;
import com.shyms.farendating.utils.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.hokas.utils.SPUtil;
import me.hokas.utils.ToastUtil;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class LineUpModelPresenterImp implements LineUpModelPresenter, LineUpModelImp.OnLineUpListener {
    private LineUpView view;
    private LineUpModelImp modelImp;
    private Context context;
    private String systemName; //服务窗口区
    private String system;
    private String systemGive;
    private StringBuffer windowsNumber;//服务窗口数
    private String serviceName;  //服务大厅的名字
    private boolean vibrateFlag = false;
    /**
     * 看这定义代码就知道我有多辛苦了
     */
    private CustomLoadingDialog customLoadingDialog;
    private Map<String, String> queueMap;
    private Subscription mSubscription;
    private ArrayList<String> queueIDList;
    private List<NUserNumber> mUserNumberList;
    private MyHintDialog mHintDialog;

    public LineUpModelPresenterImp(LineUpView view, Context context) {
        this.view = view;
        this.context = context;
        modelImp = new LineUpModelImp(this);
        customLoadingDialog = new CustomLoadingDialog(context);
        queueMap = new HashMap<>();
        queueIDList = new ArrayList<>();
        mUserNumberList = new ArrayList<>();
        getUserStatus();
    }


    @Override
    public void getWindowSystem(String username, String password) {
        modelImp.getWindowSystem(username, password);
    }

    @Override
    public void getSystem(String siteId) {
        modelImp.getSystem(siteId);
    }

    @Override
    public void getCheckQueue(String systemName, String system) {
        this.systemName = systemName;
        this.system = system;
        customLoadingDialog.show();
        modelImp.getCheckQueue(system);
    }

    public void onDestroy() {
        if (mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mSubscription = null;
        for (String queueID : queueIDList) {
            SPUtil.delete(ContentCode.RING_CALL_ONCE + queueID);
        }
    }

    public void onResume() {
        intervalQuery();
    }

    @Override
    public void getUserGet(String mobile, String system) {
        modelImp.getUserGet(mobile, system);
    }

    @Override
    public void getGiveUp(final String mobile, final String system, String systemName) {
        this.systemGive = system;

        if (mHintDialog == null) {
            popDialog(system, systemName);
        } else {
            if (!mHintDialog.isShowing()) {
                popDialog(system, systemName);
            }
        }

    }

    private void popDialog(String system, String systemName) {
        mHintDialog = new MyHintDialog(context, "提示",
                "重新取号后，您的【" + systemName + "】排队号将失效，您是否确认继续操作？");
        mHintDialog.show();
        mHintDialog.btn_end.setVisibility(View.VISIBLE);
        mHintDialog.setClickListener(new MyHintDialog.OnCustomDialogListener() {
            @Override
            public void back() {
                LogcatUtil.d("usertakenumber", system);

                getUserStatus(queueMap.get(ContentCode.QUEUE_ID + system), system);
//
            }

            @Override
            public void btnEnd() {

            }
        });
    }

    @Override
    public void getUserStatus(String queue_id, String system) {
        modelImp.getUserStatus(queue_id, system);
    }


    @Override
    public void onSuccess(final QueryLineUpInfo queryLineUpInfo) {
        customLoadingDialog.dismiss();
        final List<QueryLineUpInfo.DataEntity.WindowEntity> windowEntities = new ArrayList<>();
        windowsNumber = new StringBuffer();
        rx.Observable.from(queryLineUpInfo.getData().getWindow()).subscribe(windowEntity -> {
            if ("服务".equals(windowEntity.getStatus())) {
                windowEntities.add(windowEntity);
            }
        });
        for (int i = 0; i < windowEntities.size(); i++) {
            if (i == windowEntities.size() - 1) {
                windowsNumber.append(windowEntities.get(i).getWin());
            } else {
                windowsNumber.append(windowEntities.get(i).getWin()).append(",");
            }
        }
        final MyHintQuHaoDialog dialog = new MyHintQuHaoDialog(context, queryLineUpInfo.getData().getCount() + " 个人",
                "目前正在服务的\"" + systemName + "\"窗口是：", windowsNumber.toString(), ContentCode.APPOINT_SIGN);
        dialog.show();
        dialog.setClickListener(new MyHintQuHaoDialog.OnCustomDialogListener() {
            @Override
            public void back() {
//                customLoadingDialog.show();
                if (ContentCode.APPOINT_SIGN == 1) {
                    String queueID = queueMap.get(ContentCode.QUEUE_ID + system);
                    if (queueID == null) {
                        modelImp.getGiveUp(UserManager.getInstance().getLastUserInfo().getData().getUsername(), system);
                    } else {
                        getUserStatus(queueMap.get(ContentCode.QUEUE_ID + system), system);
                    }
//                    modelImp.getGiveUp(UserManager.getInstance().getLastUserInfo().getData().getUsername(), system);
                } else {
                    modelImp.getUserGet(UserManager.getInstance().getLastUserInfo().getData().getUsername(), system);
                }

            }

            @Override
            public void btnEnd() {

            }
        });
    }

    /**
     * 弃号
     */
    @Override
    public void onSuccess(UserGiveUp userGiveUp, String system) {
        vibrateFlag = true;
//        queueMap.remove(ContentCode.QUEUE_ID + system);
        if (ContentCode.APPOINT_SIGN == 1) {
            modelImp.getUserAppointAum(system, UserManager.getInstance().getLastUserInfo().getData().getUsername()
                    , UserManager.getInstance().getLastUserInfo().getData().getUserId());
        } else {
            getUserGet(UserManager.getInstance().getLastUserInfo().getData().getUsername(), system);
        }
    }

    /**
     * 频繁查询用户回掉
     */
    @Override
    public void onSuccess(UserQueryLineUp userQueryLineUp, String system) {
        if (userQueryLineUp.getData().getStatus().equals("2")
                || userQueryLineUp.getData().getStatus().equals("3")) {
            ToastUtil.shortShowText("您有其他号码正在办理，不能再取号!");
        } else {
            modelImp.getGiveUp(UserManager.getInstance().getLastUserInfo().getData().getUsername(), system);
        }
//
    }

    @Override
    public void onSuccess(WindowsSystemInfo windowsSystemInfo) {
        view.onCompleted(windowsSystemInfo);
    }

    @Override
    public void onSuccess(WindowsLoginInfo windowsLoginInfo) {
        serviceName = windowsLoginInfo.getData().getSite_name();
        getSystem(windowsLoginInfo.getData().getSite_id());
    }

    /**
     * userTakeNumber
     */
    @Override
    public void onSuccess(UserTakeNumber userTakeNumber, String system) {
        customLoadingDialog.dismiss();
        view.vibratorOne();
        view.onCompletedIsLogin();
        if (queueMap.get(ContentCode.QUEUE_ID + system) != null
                && queueMap.get(ContentCode.QUEUE_ID + system).equals(userTakeNumber.getData().getQueue_id())) {
            ToastUtil.shortShowText("您已经有号码了");
            return;
        }
        if (mSubscription.isUnsubscribed()) {
            ToastUtil.shortShowText("请尝试重新登录");
            intervalQuery();
        } else {
            LogcatUtil.d("systemGive", "已经订阅啦");
        }
        queueMap.put(ContentCode.QUEUE_ID + system, userTakeNumber.getData().getQueue_id());
        queueIDList.add(userTakeNumber.getData().getQueue_id());
        getUserStatus();
    }


    @Override
    public void onFailure(String msg) {
        view.showMsg(msg);
        customLoadingDialog.dismiss();

    }

    private void judgeVisiblePos(List<NUserNumber> dataList) {
        Map<String, NUserNumber> oldMap = new HashMap<>();
        Map<String, NUserNumber> newMap = new HashMap<>();
        if (mUserNumberList != null && dataList.size() > 0) {
            for (NUserNumber number : mUserNumberList) {
                oldMap.put(number.system, number);
            }
            for (NUserNumber number : dataList) {
                newMap.put(number.system, number);
            }
            for (String key : oldMap.keySet()) {
                if (oldMap.get(key) != null && newMap.get(key) == null) {
                    LogcatUtil.d("judgeVisiblePos", Integer.parseInt(key) - 1 + "");
                    view.onEnd(oldMap.get(key));
                } else if (oldMap.get(key) != null && newMap.get(key) != null) {
                    if (!oldMap.get(key).queue_id.equals(newMap.get(key).queue_id)) {
                        view.onEnd(oldMap.get(key));
                    }
                }
            }
        } else if (mUserNumberList != null && dataList.size() == 0) {
            for (NUserNumber number : mUserNumberList) {
                view.onEnd(number);
            }
        }
    }

    public void getUserStatus() {

        if (UserManager.getInstance().getLoginStatus()) {
            NetRequest.APIInstance2.queryNewUserStatus(UserManager.getInstance().getLastUserInfo().getData()
                    .getUsername(), GlobalConstant.LOG_VERTIFY_CODE)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(results -> {
                        if (results.code.equals(GlobalConstant.REQUEST_SUCCESS)) {

                            judgeVisiblePos(results.data);

                            if (results.data.size() > 0) {
                                view.showLineUpInfo(true);
                                refreshUIData(results.data);
                            }
                        } else if (results.code.equals("1")) {
                            view.showLineUpInfo(false);
                            judgeVisiblePos(results.data);
                        }
                        mUserNumberList.clear();
                        mUserNumberList.addAll(results.data);
                    }, throwable -> {

                    });

        }
    }

    private void refreshUIData(List<NUserNumber> userNumberList) {
        for (NUserNumber nUserNumber : userNumberList) {
            queueMap.put(ContentCode.QUEUE_ID + Integer.parseInt(nUserNumber.system), nUserNumber.queue_id);
            if ("1".equals(nUserNumber.system)) {
                view.onRegister(nUserNumber, nUserNumber.queue_id);
            }
            if ("2".equals(nUserNumber.system)) {
                view.onBusinessLicense(nUserNumber, nUserNumber.queue_id);
            }
            if ("4".equals(nUserNumber.system)) {
                view.onInvestConstruct(nUserNumber, nUserNumber.queue_id);
            }
            if ("3".equals(nUserNumber.system)) {
                view.onBusinessInformationQuery(nUserNumber, nUserNumber.queue_id);
            }
//            if ("3".equals(nUserNumber.system)) {
//                view.onInvestConstruct(nUserNumber, nUserNumber.queue_id);
//            }
//            if ("4".equals(nUserNumber.system)) {
//                view.onBusinessInformationQuery(nUserNumber, nUserNumber.queue_id);
//            }
            if ("5".equals(nUserNumber.system)) {
                view.onFeeCredentials(nUserNumber, nUserNumber.queue_id);
            }
            if ("1".equals(nUserNumber.status)
                    || "4".equals(nUserNumber.status)
                    || "0".equals(nUserNumber.status)) {
                if (vibrateFlag) {
                    view.vibratorOne();
                    vibrateFlag = false;
                }
            }
        }

    }

    private void intervalQuery() {
        if (UserManager.getInstance().getLoginStatus()) {
            mSubscription = Observable.interval(3000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> {
                        getUserStatus();

                    });
        }
    }
}
