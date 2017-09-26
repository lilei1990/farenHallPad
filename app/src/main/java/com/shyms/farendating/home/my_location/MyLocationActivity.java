package com.shyms.farendating.home.my_location;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.shyms.faren.R;
import com.shyms.farendating.home.my_handle_affairs.material.MyHandleAffairsActivity;
import com.shyms.farendating.home.my_location.model.BluetoothInfo;
import com.shyms.farendating.utils.UserManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import me.hokas.base.BaseActivity;
import me.hokas.utils.PreferencesUtil;

public class MyLocationActivity extends BaseActivity {
    private ImageView iv_back;
    private TextView tv_content;
    private Button tv_next;
    private Button tv_next2;
    private MapSurfaceView surfaceView;
    public SurfaceHolder surfaceHolder;
    private HttpClient httpclient;
    private HttpGet get;
    private HttpResponse response;
    private String mac;
    TimeCount time;
    private Random random = new Random();
    private Intent intent;
    String windows;
    String quhao;
    String paidui;
    private boolean stopThread = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);
        initView();
        initData();
    }

    @Override
    public void initView() {
        tv_next2 = (Button) findViewById(R.id.tv_next2);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_next = (Button) findViewById(R.id.tv_next);
        surfaceView = (MapSurfaceView) findViewById(R.id.surfaceView);
        Bitmap hallMap = BitmapFactory.decodeResource(getResources(), R.mipmap.f1);
        surfaceView.setBitmap(hallMap);
        surfaceHolder = surfaceView.getHolder();
        surfaceView.setZOrderOnTop(true);
        surfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        intent = getIntent();
        windows = intent.getStringExtra("windows");
//        windows = "16";
        quhao = intent.getStringExtra("quhao");
        paidui = intent.getStringExtra("lineup");
    }


    @Override
    public void initData() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.cancel();
                onBackPressed();

            }
        });
        tv_content.setText("我的位置");
        if (!TextUtils.isEmpty(paidui)) {
            if ("1".equals(paidui) || "2".equals(paidui) || "3".equals(paidui) || "4".equals(paidui)) {
                tv_content.setText("取号");
                tv_next2.setText("缴费出证");
                tv_next2.setVisibility(View.VISIBLE);
                tv_next2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setResult(5);
                        onBackPressed();
                    }
                });
            } else {
                tv_content.setText("取号");
                tv_next2.setVisibility(View.GONE);
            }
        }
        tv_next.setText("我的办事");
        tv_next.setVisibility(View.VISIBLE);
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserManager.getInstance().getLoginStatus()) {
                    GotoActivity(MyHandleAffairsActivity.class);
                } else
                    showToast("您还未登录，请登录后才能操作我的办事。");
            }
        });

        mac = PreferencesUtil.getBluetoothMac().toLowerCase();
        time = new TimeCount(1000, 1000);
        time.start();
    }

    public void chaxun() {
        if (stopThread) {
            new Thread() {
                @Override
                public void run() {
//你要执行的方法
//执行完毕后给handler发送一个空消息
                    try {
                        // 创建DefaultHttpClient对象
//                    String url = "http://19.134.148.18:8080/qpe/getHAIPLocation?tag=22223c08ccad&maxAge=3000";
                        String url = "http://19.134.148.18:8080/qpe/getHAIPLocation?tag=" + mac + "&maxAge=3000";
                        httpclient = new DefaultHttpClient();
                        // 创建一个HttpGet对象
                        get = new HttpGet(url);
                        // 获取HttpResponse对象
                        response = httpclient.execute(get);
                        Message msg = new Message();
                        msg.what = 0;
                        handler.sendMessage(msg);

                    } catch (ClientProtocolException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            }.start();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                //判断是否链接成功
                try {
                    if (response.getStatusLine().getStatusCode() == 200) {
                        if (50 < response.getEntity().getContentLength()) {

                            //实体转换为字符串
                            String content = null;
                            content = EntityUtils.toString(response.getEntity());
                            if (content.length() > 50) {
                                surfaceView.surfaceDestroyed(surfaceHolder);
                                initView();
                                List<BluetoothInfo> baseObject = JSON.parseArray(content, BluetoothInfo.class);
                                for (BluetoothInfo bluetoothInfo : baseObject) {
                                    bluetoothInfo.setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_marka));
                                    bluetoothInfo.setMapX(((float) (54 + bluetoothInfo.getSmoothedPositionX()) / 108));
                                    bluetoothInfo.setMapY(((float) (44 - bluetoothInfo.getSmoothedPositionY()) / 88));
                                    surfaceView.addDaobanInfo(bluetoothInfo);
                                }
                                if ("1".equals(windows)) {
                                    setWindows(-10.885269066267645f, -1.6315779052801582f, R.mipmap.window_01);
                                }
                                if ("2".equals(windows)) {
                                    setWindows(-10.885269066267645f, -1.6315779052801582f, R.mipmap.window_02);
                                }
                                if ("3".equals(windows)) {
                                    setWindows(-14.585269066267645f, -1.6315779052801582f, R.mipmap.window_03);
                                }
                                if ("4".equals(windows)) {
                                    setWindows(-16.532736059950413f, 0.1482742334315933f, R.mipmap.window_04);
                                }
                                if ("5".equals(windows)) {
                                    setWindows(-16.532736059950413f, 0.1482742334315933f, R.mipmap.window_05);
                                }
                                if ("6".equals(windows)) {
                                    setWindows(-16.532736059950413f, 3.2739379332950436f, R.mipmap.window_06);
                                }
                                if ("7".equals(windows)) {
                                    setWindows(-16.532736059950413f, 3.2739379332950436f, R.mipmap.window_07);
                                }
                                if ("8".equals(windows)) {
                                    setWindows(-14.464490906016395f, 6.2739379332950436f, R.mipmap.window_08);
                                }
                                if ("9".equals(windows)) {
                                    setWindows(-14.464490906016395f, 6.2739379332950436f, R.mipmap.window_09);
                                }
                                if ("10".equals(windows)) {
                                    setWindows(-11.132736059950413f, 6.2739379332950436f, R.mipmap.window_10);
                                }
                                if ("11".equals(windows)) {
                                    setWindows(-11.132736059950413f, 6.2739379332950436f, R.mipmap.window_11);
                                }
                                if ("12".equals(windows)) {
                                    setWindows(-8.095582990187418f, 6.2739379332950436f, R.mipmap.window_12);
                                }
                                if ("13".equals(windows)) {
                                    setWindows(-7.46212592663525f, 6.2739379332950436f, R.mipmap.window_13);
                                }
                                if ("14".equals(windows)) {
                                    setWindows(-4.592270913969319f, 6.2739379332950436f, R.mipmap.window_14);
                                }
                                if ("15".equals(windows)) {
                                    setWindows(-4.592270913969319f, 6.2739379332950436f, R.mipmap.window_15);
                                }
                                if ("16".equals(windows)) {
                                    setWindows(-1.1735932688182772f, 6.2739379332950436f, R.mipmap.window_16);
                                }
                                if ("17".equals(windows)) {
                                    setWindows(-1.1735932688182772f, 6.2739379332950436f, R.mipmap.window_17);
                                }
                                if ("18".equals(windows)) {
                                    setWindows(1.974540854588836f, 6.2739379332950436f, R.mipmap.window_18);
                                }
                                if ("19".equals(windows)) {
                                    setWindows(1.974540854588836f, 6.2739379332950436f, R.mipmap.window_19);
                                }
                                if ("20".equals(windows)) {
                                    setWindows(4.761245638458894f, 6.2739379332950436f, R.mipmap.window_20);
                                }
                                if ("21".equals(windows)) {
                                    setWindows(2.761245638458894f, 16.4739379332950436f, R.mipmap.window_01);
                                }
                                if ("22".equals(windows)) {
                                    setWindows(2.761245638458894f, 18.4739379332950436f, R.mipmap.window_02);
                                }
                                if ("23".equals(windows)) {
                                    setWindows(2.761245638458894f, 20.4739379332950436f, R.mipmap.window_03);
                                }
                                if ("24".equals(windows)) {
                                    setWindows(2.761245638458894f, 22.4739379332950436f, R.mipmap.window_04);
                                }
                                baseObject.clear();
                                surfaceView.draw();
                            } else if (content.contains("engine")) {
                                showToast("定位引擎未启动");
                            }
                        }
                        time.start();
                    } else {
                        showToast("网络错误");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            time.cancel();
            chaxun();
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
        }
    }


    @Override
    protected void onDestroy() {
        stopThread = false;
        time.cancel();
        surfaceHolder = null;
        surfaceView = null;
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
        initData();
    }

    @Override
    protected void onPause() {
        time.cancel();
        super.onPause();
    }

    /**
     * 连按两次返回键退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ("quhao".equals(quhao)) {
                time.cancel();
                setResult(4);
                finish();
            } else {
                time.cancel();
                onBackPressed();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setWindows(float x, float y, int img) {

        BluetoothInfo bluetoothInfo = new BluetoothInfo();
        bluetoothInfo.setMapX((54 + x) / 108);
        bluetoothInfo.setMapY((44 - y) / 88);
        bluetoothInfo.setBitmap(BitmapFactory.decodeResource(getResources(), img));
        surfaceView.addDaobanInfo(bluetoothInfo);

    }
}
