package com.shyms.farendating.home.my_handle_affairs.material;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.shyms.faren.R;
import com.shyms.farendating.home.my_handle_affairs.MyHandleAffairsPresenterImp;
import com.shyms.farendating.home.my_handle_affairs.MyHandleAffairsView;
import com.shyms.farendating.home.my_handle_affairs.model.HandleAffairsModel;
import com.shyms.farendating.home.my_handle_affairs.recyclerview.MyAdapter;
import com.shyms.farendating.utils.UserManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hokas.base.BaseActivity;

/**
 * 我的办事
 */
public class MyHandleAffairsActivity extends BaseActivity implements MyHandleAffairsView,
        SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.ll_wei)
    LinearLayout llWei;
    @Bind(R.id.ll_you)
    LinearLayout llYou;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    private MyAdapter adapter;
    private List<HandleAffairsModel.DataEntity> list;
    private MyHandleAffairsPresenterImp presenterImp;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_handle_affairs);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initData() {
        tvTitle.setText("我的办事");
        list = new ArrayList<>();
        adapter = new MyAdapter(this, list);
        presenterImp = new MyHandleAffairsPresenterImp(this, adapter, list);
        listView.setAdapter(adapter);
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(R.color.blue_violet, R.color.green_yellow);
        uid = UserManager.getInstance().getLastUserInfo().getData().getUserId();
        mActivityHelper.showProgressDialog("正在加载");
        presenterImp.getAffairRecord(uid);
    }

    @Override
    public void showMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void onCompleted() {
        mActivityHelper.dismissProgressDialog();
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        list.clear();
        presenterImp.getAffairRecord(uid);
    }

}
