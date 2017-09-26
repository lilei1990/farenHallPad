package com.shyms.farendating.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.shyms.farendating.home.lien_up.model.UserQueryLineUp;
import com.shyms.farendating.http.AsyncHttpCallBack;
import com.shyms.farendating.http.AsyncHttpConfig;
import com.shyms.farendating.http.AsyncHttpRequest;
import com.shyms.farendating.service.evaluate.EvaluateActivity;
import com.shyms.farendating.service.model.UserNoticeInfo;
import com.shyms.farendating.utils.ContentCode;
import com.shyms.farendating.utils.LogcatUtil;
import com.shyms.farendating.utils.MyCustomDialog;
import com.shyms.farendating.utils.MyHintDialog;
import com.shyms.farendating.utils.UserManager;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.hokas.base.BaseObject;
import me.hokas.utils.SPUtil;
import me.hokas.utils.ToastUtil;


/**
 * 后台服务
 * Created by hokas on 2015/7/29.
 */
public class MyService extends Service implements AsyncHttpCallBack {

    public static final String LINE_UP_REMINDER = "com.windows";
    public static final String LINE_UP_QUERY = "line_up_query";
    private TimeCount timeCount;
    private Receiver receiver;
    private MyHintDialog dialog;
    private int num = 0, count;
    private EvaluateActivity evaluateActivity;
    private final int EVALUATE_SUCCESS = 100;
    private final int EVALUATE_FAILURE = 101;
    private MyCustomDialog noticeDialog;
    private List<String> queueIDList;

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("service", "OnBind");
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("service", "onCreate");
        super.onCreate();
        queueIDList = new ArrayList<>();
        timeCount = new TimeCount(2000, 1000);
        timeCount.start();
        receiver = new Receiver();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LINE_UP_REMINDER);
        registerReceiver(receiver, intentFilter);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onSuccess(int what, int statusCode, JSONObject response) {

        final BaseObject baseObject = JSON.parseObject(response.toString(), BaseObject.class);
        String code = baseObject.getCode();
        if (AsyncHttpConfig.WITH_60001 == what && UserManager.getInstance().getLoginStatus()) {
            if ("0".equals(code)) {
                if (!baseObject.getData().equals("[]")) {
                    UserNoticeInfo userNoticeInfo = JSON.parseObject(response.toString(), UserNoticeInfo.class);
                    count = userNoticeInfo.getData().size();
                    for (final UserNoticeInfo.DataEntity dataEntity : userNoticeInfo.getData()) {
                        String pass;
                        if ("pass".equals(dataEntity.getCourseResult())) {
                            pass = "通过";
                        } else {
                            pass = "不通过";
                        }

                        if ("一口收件".equals(dataEntity.getCourseName())) {
                            Intent intent1 = new Intent();
                            intent1.setAction("com.lineup");
                            intent1.putExtra("pass", "yksl");
                            sendBroadcast(intent1);
                        }
                        if ("缴费出证".equals(dataEntity.getCourseName())) {
                            Intent intent1 = new Intent();
                            intent1.setAction("com.lineup");
                            intent1.putExtra("pass", "sfcz");
                            sendBroadcast(intent1);
                        }

                        if (noticeDialog == null || (noticeDialog != null && !noticeDialog.isShowing())) {
                            noticeDialog = new MyCustomDialog(this, "通知", "    事项名称：" + dataEntity.getMatterName() + "\n    流程：" + dataEntity.getCourseName() + "     状态：" + pass + "\n    详细信息在我的通知中。");
                            noticeDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                            noticeDialog.setCancelable(false);
                            noticeDialog.show();
                            noticeDialog.setClicklistener(new MyCustomDialog.OnCustomDialogListener() {
                                @Override
                                public void back() {
//                                    num++;
                                    AsyncHttpRequest.getUserEnter(UserManager.getInstance().getLastUserInfo().getData().getUserId(),
                                            dataEntity.getMtId(), MyService.this);
                                }

                                @Override
                                public void btnEnd() {
                                }
                            });
                        }
                    }
                }
            }
        }
        if (AsyncHttpConfig.WITH_60002 == what) {
            if ("0".equals(code)) {
                if ("0".equals(baseObject.getData())) {
                }
            }
        }
        if (AsyncHttpConfig.WITH_60006 == what) {
            if ("0".equals(code)) {
                if ("1".equals(baseObject.getData())) {
                    handler.sendEmptyMessage(EVALUATE_SUCCESS);
                } else {
                    handler.sendEmptyMessage(EVALUATE_FAILURE);
                }
            }
        }
    }

    private android.os.Handler handler = new android.os.Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (EVALUATE_SUCCESS == msg.what) {
                Toast.makeText(getApplicationContext(), "评价成功", Toast.LENGTH_SHORT).show();
            }
            if (EVALUATE_FAILURE == msg.what) {
                Toast.makeText(getApplicationContext(), "评价失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onFailure(int what, int statusCode, JSONObject errorResponse) {

    }


    public class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, final Intent intent) {
            if (LINE_UP_REMINDER.equals(intent.getAction())) {
                final String win = intent.getStringExtra("windows");
                String call = intent.getStringExtra("call");
                String lineup = intent.getStringExtra("system");
                String status = intent.getStringExtra("status");
                String queue_id = intent.getStringExtra("queue_id");
                if (queue_id != null) {
                    queueIDList.add(queue_id);
                }
                if (dialog == null || (dialog != null && !dialog.isShowing())) {
                    if (status.equals("2")) {

                        if ("5".equals(lineup) || "10".equals(lineup)) {
                            dialog = new MyHintDialog(MyService.this, "提示", "请到" + "[" + win + "]" + "号窗口取证，" +
                                    " 您已被呼叫" + "[" + call + "]" + "次");
                        } else if ("1".equals(lineup) || "6".equals(lineup)) {
                            dialog = new MyHintDialog(MyService.this, "提示",
                                    "请到" + "[" + win + "]" + "号窗口交件，" + " 您已被呼叫" + "[" + call + "]" + "次");
                        } else {
                            dialog = new MyHintDialog(MyService.this, "提示",
                                    "请到" + "[" + win + "]" + "号窗口办理，"
                                            + " 您已被呼叫" + "[" + call + "]" + "次");
                        }
                        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                        dialog.setCancelable(false);
                        dialog.show();
                        dialog.btn_end.setVisibility(View.VISIBLE);
                        lineUp(win, lineup, queue_id);
                    } else if (status.equals("3")){
                        boolean flag = (boolean) SPUtil.get(ContentCode.SHOW_DIALOG + queue_id, true);
                        if (flag) {
                            dialog = new MyHintDialog(MyService.this, "提示",
                                    "请前往" + "[" + win + "]" + "号窗口，"
                                            + " 您的业务已经开始受理");
                            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                            dialog.setCancelable(false);
                            dialog.show();
                            dialog.btn_end.setVisibility(View.VISIBLE);
                            lineUp(win, lineup, queue_id);
                        } else {
                            Intent intent1 = new Intent();
                            intent1.setAction("com.zhendong1");
                            sendBroadcast(intent1);
                        }
                    }
                }
            }
        }
    }

    /**
     * 弃号
     */
    private void giveUpNumber() {
        dialog.setClickListener(new MyHintDialog.OnCustomDialogListener() {
            @Override
            public void back() {
                Intent intent1 = new Intent();
                intent1.setAction("com.zhendong1");
                sendBroadcast(intent1);
            }

            @Override
            public void btnEnd() {
            }
        });
    }

    /**
     * 排队
     */
    private void lineUp(final String win, final String lineup, String queue_id) {
        dialog.setClickListener(new MyHintDialog.OnCustomDialogListener() {
            @Override
            public void back() {
                SPUtil.put(ContentCode.SHOW_DIALOG + queue_id, false);
                Intent intent1 = new Intent();
                intent1.setAction("com.zhendong");
                intent1.putExtra("windows", win);
                intent1.putExtra("lineup", lineup);
                sendBroadcast(intent1);
            }

            @Override
            public void btnEnd() {
                SPUtil.put(ContentCode.SHOW_DIALOG + queue_id, false);
                Intent intent1 = new Intent();
                intent1.setAction("com.zhendong1");
                sendBroadcast(intent1);
            }
        });
    }


    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();

        if (queueIDList.size() > 0) {
            for (String id : queueIDList) {
                SPUtil.delete(ContentCode.SHOW_DIALOG + id);
            }
        }
    }

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            timeCount.cancel();
            if (UserManager.getInstance().getLoginStatus()) {
                if (!TextUtils.isEmpty(UserManager.getInstance().getLastUserInfo().getData().getUserId()))
                    AsyncHttpRequest.getUserNotice(UserManager.getInstance().getLastUserInfo().getData().getUserId(), MyService.this);
            }
            timeCount.start();

        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            Log.d("service", "登录状态=" + UserManager.getInstance().getLoginStatus());
        }

    }


}
