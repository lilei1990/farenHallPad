package com.shyms.farendating.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shyms.faren.R;
import com.shyms.farendating.MainView;
import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.home.company.CompanyIntroduceActivity;
import com.shyms.farendating.home.daoban.DaoBanActivity;
import com.shyms.farendating.home.login.LoginActivity;
import com.shyms.farendating.home.my_handle_affairs.material.MyHandleAffairsActivity;
import com.shyms.farendating.home.notice.NoticeMessageActivity;
import com.shyms.farendating.home.user_info.UserInfoActivity;
import com.shyms.farendating.model.UserInfo;
import com.shyms.farendating.utils.MyHintDialog;
import com.shyms.farendating.utils.UserManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.hokas.base.BaseFragment;
import me.hokas.utils.PreferencesUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.im)
    ImageView im;
    @Bind(R.id.text_login)
    TextView textLogin;
    @Bind(R.id.tv_notice)
    TextView tvNotice;
    @Bind(R.id.ll_login)
    LinearLayout llLogin;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.ll_name)
    LinearLayout llName;
    @Bind(R.id.tv_01)
    TextView tv01;
    @Bind(R.id.tv_02)
    TextView tv02;
    @Bind(R.id.ll_daoban)
    LinearLayout llDaoban;
    @Bind(R.id.ll_quhao)
    LinearLayout llQuhao;
    @Bind(R.id.ll_wo_de)
    LinearLayout llWoDe;
    @Bind(R.id.rl_main1)
    RelativeLayout rlMain1;
    @Bind(R.id.ll_firm_info)
    LinearLayout llFirmIntroduce;
    private MainView listener;
    private HomeReceiver receiver;
    private UserInfo userInfo;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void initData() {
        receiver = new HomeReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("ZiDong");
        getActivity().registerReceiver(receiver, intentFilter);
        listener = (MainView) getActivity();
        llDaoban.setOnClickListener(this);
        llQuhao.setOnClickListener(this);
        llWoDe.setOnClickListener(this);
        im.setOnClickListener(this);
        tvNotice.setOnClickListener(this);
        textLogin.setOnClickListener(this);
//        userInfo = UserManager.getInstance().getLastUserInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im:
                if (UserManager.getInstance().getLoginStatus()) {
                    MyHintDialog dialog = new MyHintDialog(getActivity(), "提示", "确认退出账号?");
                    dialog.show();
                    dialog.btn_end.setVisibility(View.VISIBLE);
                    dialog.setClickListener(new MyHintDialog.OnCustomDialogListener() {
                        @Override
                        public void back() {
                            UserManager.getInstance().logoutUserInfo();
                            Log.d("homeFragment", "退出登录=" + UserManager.getInstance().getLoginStatus() + " " + PreferencesUtil.getLoginStatus());
                            im.setBackgroundResource(R.mipmap.user_icon);
                            tvUserName.setText("");
                            textLogin.setText("登录");
                            textLogin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    GotoActivity(LoginActivity.class);
                                }
                            });
                            tvNotice.setVisibility(View.GONE);
                            showToast("退出成功");
                        }

                        @Override
                        public void btnEnd() {

                        }
                    });

                }
                break;
            case R.id.tv_notice:
                GotoActivity(NoticeMessageActivity.class);
                break;
            case R.id.ll_daoban:
                GotoActivity(DaoBanActivity.class);
                break;
            case R.id.ll_quhao:
                if (UserManager.getInstance().getLoginStatus()) {
                    listener.toLineUpFragment();
                } else {
                    showToast("您还未登录，请登录后再操作");
                }
                break;
            case R.id.ll_wo_de:
                if (UserManager.getInstance().getLoginStatus()) {
                    GotoActivity(MyHandleAffairsActivity.class);
                } else {
                    showToast("您还未登录，请登录后再操作");
                }
                break;
            case R.id.text_login:
                GotoActivity(LoginActivity.class);
                break;
        }
    }

    public class HomeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("ZiDong".equals(intent.getAction())) {
                //TODO   why is null
                userInfo = UserManager.getInstance().getLastUserInfo();
                if ("男".equals(userInfo.getData().getGender())) {
                    tvUserName.setText(userInfo.getData().getName() + "先生，您好！");
                } else if ("女".equals(userInfo.getData().getGender())) {
                    tvUserName.setText(userInfo.getData().getName() + "女士，您好！");
                } else {
                    tvUserName.setText(userInfo.getData().getName() + "先生/女士，您好！");
                }
                textLogin.setText(userInfo.getData().getUsername());
                tvNotice.setVisibility(View.VISIBLE);
                im.setBackgroundResource(R.mipmap.exit);
                NetRequest.APIInstance.setUserInfo(userInfo.getData().getUserId(), userInfo.getData().getUsername())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(baseObject -> {

                        }, throwable -> {
                            showToast("服务器连接超时,请重试！");
                        });
                textLogin.setOnClickListener(v -> GotoActivity(UserInfoActivity.class));

            }
        }
    }

    @OnClick(R.id.ll_firm_info)
    public void onClickFirmIntroduction() {

        GotoActivity(CompanyIntroduceActivity.class);

    }


}
