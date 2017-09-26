package me.hokas.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shyms.faren.R;
import com.shyms.farendating.GlobalConstant;
import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.helper.ActivityHelper;
import com.shyms.farendating.utils.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import me.hokas.base.i.Init;
import me.hokas.utils.SPUtil;
import me.hokas.utils.ToastUtil;

/**
 * 基类 Activity
 */
public class BaseActivity extends AppCompatActivity implements Init {
    protected String TAG;
    private List<Activity> activityList = new LinkedList<>();
    protected ImageView ivBack;
    protected TextView tvTitle;
    protected TextView tvBtn;
    protected TextView tvTitleLeft;
    protected ImageView ivBtn;
    protected ActivityHelper mActivityHelper;

    // DisplayMetrics 类提供了一种关于显示的通用信息，如显示大小，分辨率和字体
    protected List<AsyncTask<Void, Void, Boolean>> myAsyncTasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addActivity(this);
        mActivityHelper = new ActivityHelper(this);
        TAG = this.getClass().getSimpleName();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 初始化
     */
    @Override
    public void initView(){
        ivBack = (ImageView) findViewById(R.id.ivBack);
        tvBtn = (TextView) findViewById(R.id.tvBtn);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitleLeft = (TextView) findViewById(R.id.tvTitleLeft);
        ivBtn = (ImageView) findViewById(R.id.ivBtn);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void initView(View view) {

    }

    /**
     * 初始化数据
     */
    @Override
    public void initData(){

    }

    /**
     * 如果输入法打开则关闭，如果没打开则打开
     */
    public void showSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 输入完成后隐藏键盘
     */
    public void hideSoftInput(View v) {
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /**
     * 添加Activity到容器中
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 移除Activity从容器中.
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityList.remove(activity);
        }
    }

    /**
     * 遍历所有Activity并finish.
     */
    public void clearAllActivity() {
        for (Activity activity : activityList) {
            if (activity != null) {
                activity.finish();
            }
        }
    }


    /**
     * 关闭全部Activity
     */
    public void exitSystem() {
        clearAllActivity();
        // 清空数据的操作
        System.exit(0);
    }


    /**
     * 添加异步任务到数组中
     *
     * @param asyncTask
     */
    public void putAsyncTask(AsyncTask<Void, Void, Boolean> asyncTask) {
        myAsyncTasks.add(asyncTask.execute());
    }
    protected void showToast(String msg){
        ToastUtil.showToast(BaseApplication.getInstance(),msg, Toast.LENGTH_SHORT);
    }

//    protected void showLog(String msg) {
//        Logger.show(TAG, msg);
//    }

    protected void GotoActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }
    protected void GotoActivity(Class<? extends Activity> tarActivity, int index) {
        Intent intent = new Intent(this, tarActivity);
        intent.putExtra("index",index);
        startActivity(intent);
    }
    protected void GotoActivity(Class<? extends Activity> tarActivity, String str) {
        Intent intent = new Intent(this, tarActivity);
        intent.putExtra("str",str);
        startActivity(intent);
    }
    //获取时间
    protected String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取token
     */
    protected void getServerToken() {
        String password = Util.getMD5("zw@shyms");
        NetRequest.getToken("appaccount", password)
                .subscribe(result -> {
                    if (result.getCode().equals(GlobalConstant.REQUEST_SUCCESS)) {
                        SPUtil.put(GlobalConstant.USER_TOKEN_VALID, result.getData());
                        tokenIsValid();
                    } else {
                        ToastUtil.shortShowText(result.getMessage());
                    }
                }, throwable -> {
                    ToastUtil.shortShowText("获取认证失败，请重新登录");
                });
    }

    protected void tokenIsValid() {
    }


    public String getToken() {
        return (String) SPUtil.get(GlobalConstant.USER_TOKEN_VALID, "");
    }
}
