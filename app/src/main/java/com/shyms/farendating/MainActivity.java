package com.shyms.farendating;


import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.alibaba.fastjson.JSON;
import com.shyms.faren.R;
import com.shyms.farendating.bluetooth.BluetoothManager;
import com.shyms.farendating.bluetooth.BluetoothUtils;
import com.shyms.farendating.home.HomeAndLineUpFragment;
import com.shyms.farendating.home.HomeFragment;
import com.shyms.farendating.home.advertising.AdvertisingActivity;
import com.shyms.farendating.home.business_space.BusinessSpaceActivity;
import com.shyms.farendating.home.guide.GuideActivity;
import com.shyms.farendating.home.introduce.HallIntroduceActivity;
import com.shyms.farendating.home.lien_up.LineUpFragment;
import com.shyms.farendating.http.AsyncHttp;
import com.shyms.farendating.http.AsyncHttpCallBack;
import com.shyms.farendating.http.AsyncHttpConfig;
import com.shyms.farendating.http.AsyncHttpRequest;
import com.shyms.farendating.http.api.NewMyLocationActivity;
import com.shyms.farendating.service.MyService;
import com.shyms.farendating.utils.LogcatUtil;
import com.shyms.farendating.utils.UpdateAppManager;
import com.shyms.farendating.utils.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hokas.base.BaseActivity;
import me.hokas.base.BaseApplication;
import me.hokas.base.BaseObject;
import me.hokas.utils.PreferencesUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener, MainView, AsyncHttpCallBack {

    @Bind(R.id.fragment)
    FrameLayout fragment;
    @Bind(R.id.footer_radio_home)
    RadioButton footerRadioHome;
    @Bind(R.id.footer_radio_introduce)
    RadioButton footerRadioIntroduce;
    @Bind(R.id.footer_radio_my_location)
    RadioButton footerRadioMyLocation;
    @Bind(R.id.footer_radio_business_space)
    RadioButton footerRadioBusinessSpace;
    @Bind(R.id.footer_radio_advertising)
    RadioButton footerRadioAdvertising;
    @Bind(R.id.footer_radio_guide)
    RadioButton footerRadioGuide;
    private String mac = "";
    private BluetoothManager bluetoothManager;
    private Intent intent;
    HomeAndLineUpFragment homeAndLineUpFragment;

    private MainPresenterImp presenterImp;
    private UpdateAppManager updateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        footerRadioAdvertising.setOnClickListener(this);
        footerRadioBusinessSpace.setOnClickListener(this);
        footerRadioIntroduce.setOnClickListener(this);
        footerRadioMyLocation.setOnClickListener(this);
        footerRadioHome.setOnClickListener(this);
        footerRadioGuide.setOnClickListener(this);
        upDateVersion();
        saveBluetoothMac(); //注释这句话就不能启动service
//        toHomeFragment();
        List<Fragment> list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new LineUpFragment());
        homeAndLineUpFragment = new HomeAndLineUpFragment(getSupportFragmentManager(), list, R.id.fragment);
        presenterImp = new MainPresenterImp(this, this);
    }


    //App自动更新
    public void upDateVersion() {
        AsyncHttpRequest.getUpdate(UpdateAppManager.getVersionCode(this), this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.footer_radio_advertising: //广告
                GotoActivity(AdvertisingActivity.class);
                break;
            case R.id.footer_radio_business_space:  //创业空间
                GotoActivity(BusinessSpaceActivity.class);
                break;
            case R.id.footer_radio_introduce:   //大厅介绍
                GotoActivity(HallIntroduceActivity.class);
                break;
            case R.id.footer_radio_my_location:     //我的位置
                GotoActivity(NewMyLocationActivity.class);
                break;
            case R.id.footer_radio_guide:        //办事指南
                GotoActivity(GuideActivity.class);
                break;
            case R.id.footer_radio_home:        //首页
                toHomeFragment();
                break;
            default:
        }
    }

    /**
     * 保存蓝牙mac
     */
    public void saveBluetoothMac() {
        if (!BluetoothUtils.isOpenBluetooth()) {
            Intent enBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enBT, 2);
        } else {
            getMacNumber();
        }
    }

    //获取蓝牙mac
    public void getMacNumber() {
        String macs[] = BluetoothUtils.getBuletoothMac().split(":");
        for (String mac1 : macs) {
            mac = mac + mac1;
        }
        intent = new Intent(this, MyService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
        PreferencesUtil.setBluetoothMac(mac);
        bluetoothManager = new BluetoothManager(BaseApplication.getInstance());
        bluetoothManager.toggleDFPacketAdv();
//        LogcatUtil.d("MainActivity", mac);
    }

    private ServiceConnection conn = new ServiceConnection() {
        /** 获取服务对象时的操作 */
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        /** 无法获取到服务对象时的操作 */
        public void onServiceDisconnected(ComponentName name) {
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            getMacNumber();
        }
    }

    @Override
    public void toHomeFragment() {
        homeAndLineUpFragment.onCheckedChanged(0);
    }

    @Override
    public void toLineUpFragment() {
        homeAndLineUpFragment.onCheckedChanged(1);
    }

    @Override
    public void showMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void onLogin() {
        Intent intent = new Intent();
        intent.setAction("ZiDong");
        sendBroadcast(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogcatUtil.d("MainActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogcatUtil.d("MainActivity", "PreferencesUtil.getIsLogin():" + PreferencesUtil.getIsLogin());
        if (PreferencesUtil.getIsLogin()) {
            if (UserManager.getInstance().getLoginStatus()) {
                if (!TextUtils.isEmpty(UserManager.getInstance().getLastUserInfo().getData().getLog_verify_code()))
                    presenterImp.autoLogin(UserManager.getInstance().getLastUserInfo().getData().getLog_verify_code());
            }
        }
    }

    /**
     * 连按两次返回键退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 标识是否点击过一次back退出
    private boolean mIsExit = false;
    final static int TIME_TO_EXIT = 2000;

    private Handler mExitHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mIsExit = false;
        }
    };

    /**
     * 实现点击两次退出程序
     */
    private void exit() {
        if (mIsExit) {
            logout();
            bluetoothManager.stopDataPacketAdv();
            unbindService(conn);
            stopService(intent);
            exitSystem();
        } else {
            mIsExit = true;
            showMsg("再按一次返回键关闭应用");
            // 两秒内不点击back则重置mIsExit
            mExitHandler.sendEmptyMessageDelayed(0, TIME_TO_EXIT);
        }

    }

    // 注销登录
    private void logout() {
        if (UserManager.getInstance().getLoginStatus()) {
            UserManager.getInstance().logoutUserInfo();
        }
    }

    @Override
    public void onSuccess(int what, int statusCode, JSONObject response) {
        final BaseObject baseObject = JSON.parseObject(response.toString(), BaseObject.class);
        String code = baseObject.getCode();
        if (AsyncHttpConfig.WITH_50011 == what) {
            if (0 == Integer.parseInt(code)) {
                try {
                    String versionCode = response.getJSONObject("data").getString("versionCode");
                    if (Integer.parseInt(versionCode) > UpdateAppManager.getVersionCode(this)) {
                        updateManager = new UpdateAppManager(this);
                        updateManager.seturl(response.getJSONObject("data").getString("url"));
                        updateManager.checkUpdateInfo(response.getJSONObject("data").getString("versionName"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onFailure(int what, int statusCode, JSONObject errorResponse) {

    }

    @Override
    protected void onDestroy() {
        if (AsyncHttp.emptyReference()) {
            AsyncHttp.cancelAllRequests();
            AsyncHttp.recycle();
            System.gc();
        }
        super.onDestroy();
    }
}
