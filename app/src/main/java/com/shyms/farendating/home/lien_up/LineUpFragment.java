package com.shyms.farendating.home.lien_up;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.shyms.faren.R;
import com.shyms.farendating.MainView;
import com.shyms.farendating.home.lien_up.model.NUserNumber;
import com.shyms.farendating.home.lien_up.model.QueryLineUpInfo;
import com.shyms.farendating.home.lien_up.model.WindowsSystemInfo;
import com.shyms.farendating.home.lien_up.recycler.MyAdapter;
import com.shyms.farendating.home.my_handle_affairs.material.MyHandleAffairsActivity;
import com.shyms.farendating.model.UserInfo;
import com.shyms.farendating.http.api.NewMyLocationActivity;
import com.shyms.farendating.service.MyService;
import com.shyms.farendating.utils.ContentCode;
import com.shyms.farendating.utils.LogcatUtil;
import com.shyms.farendating.utils.MyRecyclerViewItemClickListener;
import com.shyms.farendating.utils.UserManager;
import com.shyms.farendating.utils.VibratorUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.hokas.base.BaseFragment;
import me.hokas.utils.SPUtil;
import me.hokas.utils.ToastUtil;

/**
 * 排队叫号
 */
public class LineUpFragment extends BaseFragment implements View.OnClickListener, LineUpView,
        MyRecyclerViewItemClickListener {
    @Bind(R.id.tv01)
    TextView tv01;
    @Bind(R.id.rlBtn01)
    RelativeLayout rlBtn01;
    @Bind(R.id.tv02)
    TextView tv02;
    @Bind(R.id.rlBtn02)
    RelativeLayout rlBtn02;
    @Bind(R.id.tv03)
    TextView tv03;
    @Bind(R.id.rlBtn03)
    RelativeLayout rlBtn03;
    @Bind(R.id.tv04)
    TextView tv04;
    @Bind(R.id.rlBtn04)
    RelativeLayout rlBtn04;
    @Bind(R.id.tv05)
    TextView tv05;
    @Bind(R.id.rlBtn05)
    RelativeLayout rlBtn05;
    @Bind(R.id.rlBtn06)
    RelativeLayout rlBtn06;
    @Bind(R.id.llLineUp)
    LinearLayout llLineUp;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.tvHint)
    TextView tvHint;
    @Bind(R.id.tvYwpLineUpName)
    TextView tvYwpLineUpName;
    @Bind(R.id.tvYwpLineUpNumber)
    TextView tvYwpLineUpNumber;
    @Bind(R.id.llYwpLineUp)
    LinearLayout llYwpLineUp;
    @Bind(R.id.tvYwpWait)
    TextView tvYwpWait;
    @Bind(R.id.tvYwpWaitNumber)
    TextView tvYwpWaitNumber;
    @Bind(R.id.llYwpWait)
    LinearLayout llYwpWait;
    @Bind(R.id.tvYwpTakeNumber)
    TextView tvYwpTakeNumber;
    @Bind(R.id.rlYwp)
    RelativeLayout rlYwp;
    @Bind(R.id.tvGcLineUpName)
    TextView tvGcLineUpName;
    @Bind(R.id.tvGcLineUpNumber)
    TextView tvGcLineUpNumber;
    @Bind(R.id.llGcLineUp)
    LinearLayout llGcLineUp;
    @Bind(R.id.tvGcWait)
    TextView tvGcWait;
    @Bind(R.id.tvGcWaitNumber)
    TextView tvGcWaitNumber;
    @Bind(R.id.llGcWait)
    LinearLayout llGcWait;
    @Bind(R.id.tvGcTakeNumber)
    TextView tvGcTakeNumber;
    @Bind(R.id.rlGc)
    RelativeLayout rlGc;
    @Bind(R.id.tvQtLineUpName)
    TextView tvQtLineUpName;
    @Bind(R.id.tvQtLineUpNumber)
    TextView tvQtLineUpNumber;
    @Bind(R.id.llQtLineUp)
    LinearLayout llQtLineUp;
    @Bind(R.id.tvQtWait)
    TextView tvQtWait;
    @Bind(R.id.tvQtWaitNumber)
    TextView tvQtWaitNumber;
    @Bind(R.id.llQtWait)
    LinearLayout llQtWait;
    @Bind(R.id.tvQtTakeNumber)
    TextView tvQtTakeNumber;
    @Bind(R.id.rlQt)
    RelativeLayout rlQt;
    @Bind(R.id.tvGsLineUpName)
    TextView tvGsLineUpName;
    @Bind(R.id.tvGsLineUpNumber)
    TextView tvGsLineUpNumber;
    @Bind(R.id.llGsLineUp)
    LinearLayout llGsLineUp;
    @Bind(R.id.tvGsWait)
    TextView tvGsWait;
    @Bind(R.id.tvGsWaitNumber)
    TextView tvGsWaitNumber;
    @Bind(R.id.llGsWait)
    LinearLayout llGsWait;
    @Bind(R.id.tvGsTakeNumber)
    TextView tvGsTakeNumber;
    @Bind(R.id.rlGs)
    RelativeLayout rlGs;
    @Bind(R.id.tvJfLineUpName)
    TextView tvJfLineUpName;
    @Bind(R.id.tvJfLineUpNumber)
    TextView tvJfLineUpNumber;
    @Bind(R.id.llJfLineUp)
    LinearLayout llJfLineUp;
    @Bind(R.id.tvJfWait)
    TextView tvJfWait;
    @Bind(R.id.tvJfWaitNumber)
    TextView tvJfWaitNumber;
    @Bind(R.id.llJfWait)
    LinearLayout llJfWait;
    @Bind(R.id.tvJfTakeNumber)
    TextView tvJfTakeNumber;
    @Bind(R.id.rlJf)
    RelativeLayout rlJf;
    @Bind(R.id.ll_content_paidui)
    LinearLayout llContentPaidui;
    @Bind(R.id.llMyHandleAffairs)
    LinearLayout llMyHandleAffairs;
    @Bind(R.id.llLineUpInfo)
    LinearLayout llLineUpInfo;
    @Bind(R.id.tv06)
    TextView mAppoint;


    private MainView listener;
    private LineUpModelPresenterImp presenterImp;
    private UserInfo userInfo;
    private WindowsSystemInfo windowsSystemInfo;
    private List<WindowsSystemInfo.DataEntity> list;
    private MyAdapter adapter;
    private String lineup;
    private LineUpReceiver receiver;
    private Ringtone ringtone;

    public LineUpFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_line_up, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        initView(view);
        initData();

        return view;
    }

    void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void initData() {

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        ringtone = RingtoneManager.getRingtone(mContext.getApplicationContext(), notification);

        ringtone.play();
        ringtone.stop();
        receiver = new LineUpReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.zhendong");
        intentFilter.addAction("com.zhendong1");
        intentFilter.addAction("com.lineup");
        getActivity().registerReceiver(receiver, intentFilter);

        listener = (MainView) getActivity();
        tvTitle.setText("排队取号系统");
        tvBtn.setText("预约取号");
        mAppoint.setText("预约取号");
        tvTitle.setTextSize(32);
        rlBtn01.setOnClickListener(this);
        rlBtn02.setOnClickListener(this);
        rlBtn03.setOnClickListener(this);
        rlBtn04.setOnClickListener(this);
        rlBtn05.setOnClickListener(this);
//        rlBtn06.setOnClickListener(this);
        tvYwpTakeNumber.setOnClickListener(this);
        tvGcTakeNumber.setOnClickListener(this);
        tvQtTakeNumber.setOnClickListener(this);
        tvGsTakeNumber.setOnClickListener(this);
        tvJfTakeNumber.setOnClickListener(this);
        llMyHandleAffairs.setOnClickListener(this);
        userInfo = UserManager.getInstance().getLastUserInfo();
        presenterImp = new LineUpModelPresenterImp(this, getActivity());
        presenterImp.getWindowSystem("12345678901", "admin");
        list = new ArrayList<>();
        adapter = new MyAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        presenterImp.onResume();
        mActivityHelper.showProgressDialog("正在加载");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        getActivity().unregisterReceiver(receiver);
        presenterImp.onDestroy();

    }

    @Override
    public void onClick(View v) {
        int position = -1;
        switch (v.getId()) {

            case R.id.rlBtn01:
                position = 0;
                getCheckQueue(position);

                break;
            case R.id.rlBtn02:
                position = 1;
                getCheckQueue(position);
                break;
            case R.id.rlBtn03:
                position = 2;

                getCheckQueue(position);
                break;
            case R.id.rlBtn04:

                position = 3;
                getCheckQueue(position);
                break;
            case R.id.rlBtn05:
                position = 4;
                getCheckQueue(position);
                break;
            case R.id.tvYwpTakeNumber:
                position = 0;
                getGiveUp(position);
                break;
            case R.id.tvGcTakeNumber:
                position = 1;
                getGiveUp(position);
                break;
            case R.id.tvQtTakeNumber:

                position = 2;
                getGiveUp(position);
                break;
            case R.id.tvGsTakeNumber:

                position = 3;
                getGiveUp(position);
                break;
            case R.id.tvJfTakeNumber:

                position = 4;
                getGiveUp(position);
                break;
            case R.id.llMyHandleAffairs:
                GotoActivity(MyHandleAffairsActivity.class);
                break;
        }
    }

    private void getGiveUp(int position) {
        if (windowsSystemInfo == null || windowsSystemInfo.getData() == null) {
            ToastUtil.shortShowText("检查网络");
            return;
        }
        ContentCode.APPOINT_SIGN = -1;
        presenterImp.getGiveUp(userInfo.getData().getUsername(), windowsSystemInfo.getData().get(position).getSystem(),
                windowsSystemInfo.getData().get(position).getName());
    }

    private void getCheckQueue(int position) {
        if (windowsSystemInfo == null || windowsSystemInfo.getData() == null) {
            ToastUtil.shortShowText("检查网络");
            return;
        }
        ContentCode.APPOINT_SIGN = -1;
        presenterImp.getCheckQueue(windowsSystemInfo.getData().get(position).getName(),
                windowsSystemInfo.getData().get(position).getSystem());
    }

    @Override
    public void onRepeatGetNumber(String system) {
        if (ContentCode.APPOINT_SIGN == -1) {
            presenterImp.getCheckQueue(windowsSystemInfo.getData().get(Integer.parseInt(system)).getName(),
                    windowsSystemInfo.getData().get(Integer.parseInt(system)).getSystem());
        }
    }

    @Override
    public void showMsg(String msg) {
        showToast(msg);
        mActivityHelper.dismissProgressDialog();
    }

    @Override
    public void onCompleted(WindowsSystemInfo windowsSystemInfo) {
        this.windowsSystemInfo = windowsSystemInfo;
        mActivityHelper.dismissProgressDialog();


        tv01.setText(windowsSystemInfo.getData().get(0).getName());
        tv02.setText(windowsSystemInfo.getData().get(1).getName());
        tv03.setText(windowsSystemInfo.getData().get(2).getName());
        tv04.setText(windowsSystemInfo.getData().get(3).getName());
        tv05.setText(windowsSystemInfo.getData().get(4).getName());
        list.addAll(windowsSystemInfo.getData());

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCompleted(String systemName, QueryLineUpInfo queryLineUpInfo) {

    }

    @Override
    public void onCompleted(String serviceName, String windowsName, String windowsNumber, String before, String number) {

    }

    @Override
    public void onCompletedIsLogin() {
//        llLineUpInfo.setVisibility(View.VISIBLE);
//        llLineUp.setVisibility(View.GONE);
    }

    private boolean backFlag = true;

    @OnClick(R.id.ivBack)
    public void onClickBack() {
        listener.toHomeFragment();
        if (!backFlag) {
            llLineUpInfo.setVisibility(View.GONE);
            llLineUp.setVisibility(View.VISIBLE);
            ToastUtil.shortShowText("业务办理结束");
        }
    }

    @Override
    public void showLineUpInfo(boolean showInfo) {
        backFlag = showInfo;
        if (showInfo) {
            if (llLineUpInfo.getVisibility() != View.VISIBLE) {
                llLineUpInfo.setVisibility(View.VISIBLE);
                llLineUp.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onRegister(NUserNumber nUserNumber, String queue_id) {
        rlYwp.setVisibility(View.VISIBLE);
        LogcatUtil.d("NUserNumber","id="+nUserNumber.queue_id);
        LogcatUtil.d("NUserNumber","call="+nUserNumber.call);
        if ("1".equals(nUserNumber.status)) {
            tvYwpLineUpName.setText(nUserNumber.system_name + "的排队号：");
            tvYwpLineUpNumber.setText(nUserNumber.number);
            tvYwpWait.setText("您前面的等待人数：");
            tvYwpWaitNumber.setText(nUserNumber.before);
            tvYwpWaitNumber.setVisibility(View.VISIBLE);
        }
        if ("2".equals(nUserNumber.status)) {
            tvYwpLineUpName.setText(nUserNumber.system_name + "呼叫窗口号：");
            tvYwpLineUpNumber.setText(nUserNumber.win);
            tvYwpWait.setText("您已经被呼叫：");
            tvYwpWaitNumber.setVisibility(View.VISIBLE);
            if (!tvYwpWaitNumber.getText().toString().equals(nUserNumber.call)) {
                tvYwpWaitNumber.setText(nUserNumber.call);
                dialog(nUserNumber);
            }
        }
        if ("3".equals(nUserNumber.status)) {
            tvYwpLineUpName.setText(nUserNumber.system_name + "呼叫窗口号：");
            tvYwpLineUpNumber.setText(nUserNumber.win);
            tvYwpWait.setText("您的业务已经开始受理");
            tvYwpWaitNumber.setVisibility(View.INVISIBLE);
            dialog(nUserNumber);
        }

    }

    @Override
    public void onEnd(NUserNumber nUserNumber) {
        vibratorOne();
        ToastUtil.shortShowText("您的 " + nUserNumber.system_name + " 号码" + "[" + nUserNumber.number + "]" + "已过期\n感谢您的支持");
        switch (Integer.parseInt(nUserNumber.system)) {
            case 1:
                tvYwpLineUpName.setText("您的" + nUserNumber.system_name + "号码已失效：");
                tvYwpLineUpNumber.setText(nUserNumber.number);
                tvYwpWait.setText("如有业务需求请重新取号");
                tvYwpWaitNumber.setVisibility(View.INVISIBLE);
                break;
            case 2:
                tvGcLineUpName.setText("您的" + nUserNumber.system_name + "号码已失效：");
                tvGcLineUpNumber.setText(nUserNumber.number);
                tvGcWait.setText("如有业务需求请重新取号");
                tvGcWaitNumber.setVisibility(View.INVISIBLE);

                break;
            case 3:
                tvQtLineUpName.setText("您的" + nUserNumber.system_name + "号码已失效：");
                tvQtLineUpNumber.setText(nUserNumber.number);
                tvQtWait.setText("如有业务需求请重新取号");
                tvQtWaitNumber.setVisibility(View.INVISIBLE);
                break;
            case 4:
                tvGsLineUpName.setText("您的" + nUserNumber.system_name + "号码已失效：");
                tvGsLineUpNumber.setText(nUserNumber.number);
                tvGsWait.setText("如有业务需求请重新取号");
                tvGsWaitNumber.setVisibility(View.INVISIBLE);
                break;
            case 5:
                tvJfLineUpName.setText("您的" + nUserNumber.system_name + "号码已失效：");
                tvJfLineUpNumber.setText(nUserNumber.number);
                tvJfWait.setText("如有业务需求请重新取号");
                tvJfWaitNumber.setVisibility(View.INVISIBLE);
                break;

        }

    }

    @Override
    public void onBusinessLicense(NUserNumber nUserNumber, String queue_id) {
        rlGc.setVisibility(View.VISIBLE);
        if ("1".equals(nUserNumber.status)) {
            tvGcLineUpName.setText(nUserNumber.system_name + "的排队号：");
            tvGcLineUpNumber.setText(nUserNumber.number);
            tvGcWait.setText("您前面的等待人数：");
            tvGcWaitNumber.setText(nUserNumber.before);
            tvGcWaitNumber.setVisibility(View.VISIBLE);
        }
        if ("2".equals(nUserNumber.status)) {
            tvGcLineUpName.setText(nUserNumber.system_name + "呼叫窗口号：");
            tvGcLineUpNumber.setText(nUserNumber.win);
            tvGcWait.setText("您已经被呼叫：");
            tvGcWaitNumber.setVisibility(View.VISIBLE);
            if (!tvGcWaitNumber.getText().toString().equals(nUserNumber.call)) {
                tvGcWaitNumber.setText(nUserNumber.call);
                dialog(nUserNumber);
            }
        }
        if ("3".equals(nUserNumber.status)) {
            tvGcLineUpName.setText(nUserNumber.system_name + "呼叫窗口号：");
            tvGcLineUpNumber.setText(nUserNumber.win);
            tvGcWait.setText("您的业务已经开始受理");
            tvGcWaitNumber.setVisibility(View.INVISIBLE);
            dialog(nUserNumber);
        }
        if ("0".equals(nUserNumber.status)) {
            tvGcLineUpName.setText("您的" + nUserNumber.system_name + "号码已失效：");
            tvGcLineUpNumber.setText(nUserNumber.number);
            tvGcWait.setText("未在规定时间前往办理");
            tvGcWaitNumber.setVisibility(View.GONE);
        }
        if ("4".equals(nUserNumber.status)) {
            tvGcLineUpName.setText("您的" + nUserNumber.system_name + "号码已失效：");
            tvGcLineUpNumber.setText(nUserNumber.number);
            tvGcWait.setText("您的事项已办理");
            tvGcWaitNumber.setText(nUserNumber.call);
            tvGcWaitNumber.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onInvestConstruct(NUserNumber nUserNumber, String queue_id) {
        rlQt.setVisibility(View.VISIBLE);
        if ("1".equals(nUserNumber.status)) {
            tvQtLineUpName.setText("您的" + nUserNumber.system_name + "的排队号：");
            tvQtLineUpNumber.setText(nUserNumber.number);
            tvQtWait.setText("您前面的等待人数：");
            tvQtWaitNumber.setText(nUserNumber.before);
            tvQtWaitNumber.setVisibility(View.VISIBLE);
        }
        if ("2".equals(nUserNumber.status)) {
            tvQtLineUpName.setText(nUserNumber.system_name + "呼叫窗口号：");
            tvQtLineUpNumber.setText(nUserNumber.win);
            tvQtWait.setText("您已经被呼叫：");
            if (!tvQtWaitNumber.getText().toString().equals(nUserNumber.call)) {
                tvQtWaitNumber.setText(nUserNumber.call);
                dialog(nUserNumber);
            }
            tvQtWaitNumber.setVisibility(View.VISIBLE);
        }
        if ("3".equals(nUserNumber.status)) {
            tvQtLineUpName.setText(nUserNumber.system_name + "呼叫窗口号：");
            tvQtLineUpNumber.setText(nUserNumber.win);
            tvQtWait.setText("您的业务开始受理");
            tvQtWaitNumber.setVisibility(View.INVISIBLE);
            dialog(nUserNumber);
        }
        if ("0".equals(nUserNumber.status)) {
            tvQtLineUpName.setText("您的" + nUserNumber.system_name + "号码已失效：");
            tvQtLineUpNumber.setText(nUserNumber.number);
            tvQtWait.setText("未在规定时间前往办理");
            tvQtWaitNumber.setVisibility(View.GONE);
        }
        if ("4".equals(nUserNumber.status)) {
            tvQtLineUpName.setText("您的" + nUserNumber.system_name + "号码已失效：");
            tvQtLineUpNumber.setText(nUserNumber.number);
            tvQtWait.setText("您的事项已办理");
            tvQtWaitNumber.setText(nUserNumber.call);
            tvQtWaitNumber.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onBusinessInformationQuery(NUserNumber nUserNumber, String queue_id) {
        rlGs.setVisibility(View.VISIBLE);
        if ("1".equals(nUserNumber.status)) {
            tvGsLineUpName.setText("您的" + nUserNumber.system_name + "的排队号：");
            tvGsLineUpNumber.setText(nUserNumber.number);
            tvGsWait.setText("您前面的等待人数：");
            tvGsWaitNumber.setText(nUserNumber.before);
            tvGsWaitNumber.setVisibility(View.VISIBLE);
        }
        if ("2".equals(nUserNumber.status)) {
            tvGsLineUpName.setText(nUserNumber.system_name + "呼叫窗口号：");
            tvGsLineUpNumber.setText(nUserNumber.win);
            tvGsWait.setText("您已经被呼叫：");
            if (!tvGsWaitNumber.getText().toString().equals(nUserNumber.call)) {
                tvGsWaitNumber.setText(nUserNumber.call);
                dialog(nUserNumber);
            }
            tvGsWaitNumber.setVisibility(View.VISIBLE);
        }
        if ("3".equals(nUserNumber.status)) {
            tvGsLineUpName.setText(nUserNumber.system_name + "呼叫窗口号：");
            tvGsLineUpNumber.setText(nUserNumber.win);
            tvGsWait.setText("您的业务开始受理");
            tvGsWaitNumber.setVisibility(View.INVISIBLE);
            dialog(nUserNumber);
        }
        if ("0".equals(nUserNumber.status)) {
            tvGsLineUpName.setText("您的" + nUserNumber.system_name + "号码已失效：");
            tvGsLineUpNumber.setText(nUserNumber.number);
            tvGsWait.setText("未在规定时间前往办理");
            tvGsWaitNumber.setVisibility(View.GONE);
        }
        if ("4".equals(nUserNumber.status)) {
            tvGsLineUpName.setText("您的" + nUserNumber.system_name + "号码已失效：");
            tvGsLineUpNumber.setText(nUserNumber.number);
            tvGsWait.setText("您的事项已办理");
            tvGsWaitNumber.setText(nUserNumber.call);
            tvGsWaitNumber.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFeeCredentials(NUserNumber nUserNumber, String queue_id) {
        rlJf.setVisibility(View.VISIBLE);
        if ("1".equals(nUserNumber.status)) {
            tvJfLineUpName.setText("您的" + nUserNumber.system_name + "的排队号：");
            tvJfLineUpNumber.setText(nUserNumber.number);
            tvJfWait.setText("您前面的等待人数：");
            tvJfWaitNumber.setText(nUserNumber.before);
            tvJfWaitNumber.setVisibility(View.VISIBLE);
        }
        if ("2".equals(nUserNumber.status)) {
            tvJfLineUpName.setText(nUserNumber.system_name + "呼叫窗口号：");
            tvJfLineUpNumber.setText(nUserNumber.win);
            tvJfWait.setText("您已经被呼叫：");
            if (!tvJfWaitNumber.getText().toString().equals(nUserNumber.call)) {
                tvJfWaitNumber.setText(nUserNumber.call);
                dialog(nUserNumber);
            }
            tvJfWaitNumber.setVisibility(View.VISIBLE);
        }
        if ("3".equals(nUserNumber.status)) {
            tvJfLineUpName.setText(nUserNumber.system_name + "呼叫窗口号：");
            tvJfLineUpNumber.setText(nUserNumber.win);
            tvJfWait.setText("您的业务开始办理");
            tvJfWaitNumber.setVisibility(View.INVISIBLE);
            dialog(nUserNumber);
        }
        if ("0".equals(nUserNumber.status)) {
            tvJfLineUpName.setText("您的" + nUserNumber.system_name + "号码已失效：");
            tvJfLineUpNumber.setText(nUserNumber.number);
            tvJfWait.setText("未在规定时间前往办理");
            tvJfWaitNumber.setVisibility(View.GONE);
        }
        if ("4".equals(nUserNumber.status)) {
            tvJfLineUpName.setText("您的" + nUserNumber.system_name + "号码已失效：");
            tvJfLineUpNumber.setText(nUserNumber.number);
            tvJfWait.setText("您的事项已办理");
            tvJfWaitNumber.setText(nUserNumber.call);
            tvJfWaitNumber.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void vibratorOne() {
        VibratorUtil.Vibrate(getActivity(), 3000);
        ringtone.play();
    }

    //弹窗

    private void dialog(NUserNumber nUserNumber) {
        LogcatUtil.d("padcall", "dialog 开始");
        long[] pattern = {1000, 1000, 1000, 1000};
        if (nUserNumber.status.equals("3")) {
            boolean ringFlag = (boolean) SPUtil.get(ContentCode.RING_CALL_ONCE + nUserNumber.queue_id, true);
            if (ringFlag) {
                ringtone.play();
                SPUtil.put(ContentCode.RING_CALL_ONCE + nUserNumber.queue_id, false);
            }
        } else {
            ringtone.play();
        }
        VibratorUtil.Vibrate(getActivity(), pattern, true);
        Intent intent = new Intent();
        intent.setAction(MyService.LINE_UP_REMINDER);
        intent.putExtra("windows", nUserNumber.win);
        intent.putExtra("call", nUserNumber.call);
        intent.putExtra("system", nUserNumber.system);
        intent.putExtra("queue_id", nUserNumber.queue_id);
        intent.putExtra("status", nUserNumber.status);

        mContext.sendBroadcast(intent);
        //TODO   intent  加载intent数据
    }

    @Override
    public void onItemClick(View view, int position) {
        ContentCode.APPOINT_SIGN = -1;
        presenterImp.getCheckQueue(windowsSystemInfo.getData().get(position).getName(),
                windowsSystemInfo.getData().get(position).getSystem());
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    public class LineUpReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.zhendong".equals(intent.getAction())) {
                intent.setClass(getActivity(), NewMyLocationActivity.class);
                intent.putExtra("windows", intent.getStringExtra("windows"));
                intent.putExtra("quhao", "quhao");
                intent.putExtra("lineup", intent.getStringExtra("lineup"));
                startActivityForResult(intent, 4);
                VibratorUtil.cancel(getActivity());
            }
            if ("com.zhendong1".equals(intent.getAction())) {
                VibratorUtil.cancel(getActivity());
            }
            if ("com.lineup".equals(intent.getAction())) {
//                String line = intent.getStringExtra("pass");
            }
        }
    }


    @OnClick({R.id.rlBtn06, R.id.tvBtn})
    public void onClickAppoint() {
        List<String> systemList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            systemList.add(list.get(i).getName());
        }
        MaterialDialog materialDialog = new MaterialDialog.Builder(mActivity)
                .titleColor(getResources().getColor(R.color.dark_orange))
                .title("预约取号")
                .items(systemList)
                .onNegative((dialog, which) -> dialog.dismiss())
                .itemsCallback((dialog, itemView, which, text) -> {
                    itemView.setBackgroundColor(Color.parseColor("#673ab7"));
                    switch (which) {
                        case 0:
                            getAppointCheckQueue(which);
                            break;
                        case 1:
                            getAppointCheckQueue(which);
                            break;
                        case 2:
                            getAppointCheckQueue(which);
                            break;
                        case 3:
                            getAppointCheckQueue(which);
                            break;
                        case 4:
                            getAppointCheckQueue(which);
                            break;
                    }
                }).build();
        materialDialog.show();
    }

    private void getAppointCheckQueue(int which) {
        if (windowsSystemInfo == null || windowsSystemInfo.getData() == null) {
            ToastUtil.shortShowText("请检查网络");
            return;
        }
        ContentCode.APPOINT_SIGN = 1;
        presenterImp.getCheckQueue(windowsSystemInfo.getData().get(which).getName(),
                windowsSystemInfo.getData().get(which).getSystem());
    }


}
