package me.hokas.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shyms.faren.R;
import com.shyms.farendating.GlobalConstant;
import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.helper.ActivityHelper;
import com.shyms.farendating.utils.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.hokas.base.i.Init;
import me.hokas.utils.SPUtil;
import me.hokas.utils.ToastUtil;

/**
 * 基类Fragment
 * Created by hokas on 2015/10/15.
 */
public class BaseFragment extends Fragment implements Init {
    protected ImageView ivBack;
    protected TextView tvTitle;
    protected TextView tvBtn;
    protected TextView tvTitleLeft;
    protected ImageView ivBtn;
    protected Activity mActivity;
    protected Context mContext;
    protected ActivityHelper mActivityHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityHelper = new ActivityHelper(mActivity);
    }

    @Override
    public void initView() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
        mContext = getContext();
    }

    @Override
    public void initView(View view) {
        ivBack = (ImageView) view.findViewById(R.id.ivBack);
        tvBtn = (TextView) view.findViewById(R.id.tvBtn);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitleLeft = (TextView) view.findViewById(R.id.tvTitleLeft);
        ivBtn = (ImageView) view.findViewById(R.id.ivBtn);
    }

    @Override
    public void initData() {
    }


    protected void GotoActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(getActivity(), tarActivity);
        startActivity(intent);
    }

    protected void GotoActivity(Class<? extends Activity> tarActivity, int index) {
        Intent intent = new Intent(getActivity(), tarActivity);
        intent.putExtra("CurrentItem",index);
        startActivity(intent);
    }

    protected void GotoActivity(Class<? extends Activity> tarActivity, String string) {
        Intent intent = new Intent(getActivity(), tarActivity);
        intent.putExtra("content",string);
        startActivity(intent);
    }

    //获取时间
    protected String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }
    protected void showToast(String msg){
        ToastUtil.showToast(BaseApplication.getInstance(),msg, Toast.LENGTH_SHORT);
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