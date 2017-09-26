package com.shyms.farendating.home.guide;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.shyms.faren.R;
import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.home.guide.details.MattersDetailsActivity;
import com.shyms.farendating.home.guide.model.AffairsInfo;
import com.shyms.farendating.home.guide.recyclerview.MyGuideAdapter;
import com.shyms.farendating.home.guide.search.SearchActivity;
import com.shyms.farendating.home.my_handle_affairs.material.model.NDepartment;
import com.shyms.farendating.utils.ContentCode;
import com.shyms.farendating.utils.LogcatUtil;
import com.shyms.farendating.utils.MyRecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hokas.base.BaseFragment;
import me.hokas.utils.ToastUtil;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 办事指南
 */
public class GuideFragment extends BaseFragment implements GuideView, SwipeRefreshLayout.OnRefreshListener,
        MyRecyclerViewItemClickListener, AdapterView.OnItemClickListener, View.OnTouchListener, TextView.OnEditorActionListener, View.OnClickListener {

    @Bind(R.id.list)
    ListView listView;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.ll_search)
    LinearLayout llSearch;
    @Bind(R.id.iv_search)
    ImageView ivSearch;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    private int perpage = 10000, page = 0;
    private String depid = "25";
    private List<AffairsInfo.DataEntity> list;
    private MyGuideAdapter adapter;
    private List<String> departmentNameList;
    private List<String> departmentCodeList;
    private int[] child_text_id = new int[]{
            25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 41, 42, 43, 44, 45, 46, 47};

    private String[] child_text_array = new String[]{
            "禅城区经济和科技促进局", "禅城区工商行政管理局", "禅城区城市综合管理局", "禅城区民政局"
            , "佛山市公安局禅城分局", "禅城区国土城建和水务局", "禅城区发展规划和统计局", "禅城区交通运输局"
            , "佛山市公安消防支队禅城区大队", "禅城区国家税务局", "禅城区质量技术监督局", "佛山市气象局", "禅城区教育局"
            , "禅城区人力资源和社会保障局", "禅城区卫生和计划生育局", "禅城区环境保护局", "禅城区食品药品" +
            "监督管理局（禅城区市场监督管理局）", "禅城区安全生产监督管理局", "禅城区地方税务局",
            "禅城区供电局", "禅城区文化体育局", "佛山海关驻禅城办事处"
    };

    private GuidePresenter presenter;

    public GuideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guide, container, false);
        ButterKnife.bind(this, view);
        departmentNameList = new ArrayList<>();
        departmentCodeList = new ArrayList<>();
        getAllDepartment();
        initData();
        return view;
    }

    private void getAllDepartment() {
        NetRequest.APIInstance.getDicDepartment()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results -> {
                    if (results.code.equals("0")) {
                        setupData(results.data);
                    }
                }, throwable -> {
                });
    }

    private void setupData(List<NDepartment> departmentList) {
        for (NDepartment department : departmentList) {
            departmentCodeList.add(department.code);
            departmentNameList.add(department.name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_expandable_list_item_1, departmentNameList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void initData() {

        presenter = new GuidePresenter(this);
        list = new ArrayList<>();
        adapter = new MyGuideAdapter(list);

        initRecyclerViewLayout();

        recyclerView.setAdapter(adapter);

        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(R.color.blue_violet, R.color.green_yellow);
        adapter.setOnItemClickListener(this);

        presenter.getAffairGuideList(adapter, list, depid);

        etSearch.setOnTouchListener(this);
        etSearch.setOnEditorActionListener(this);
        ivSearch.setOnClickListener(this);
    }

    private void initRecyclerViewLayout() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        page = 0;
        list.clear();
        LogcatUtil.d("GuideFragment", "depid=" + depid);
        presenter.getAffairGuideList(adapter, list, depid);
    }

    @Override
    public void onItemClick(View view, int position) {
        LogcatUtil.d("GuideFragment", "position:" + position);

        Intent intent = new Intent(getActivity(), MattersDetailsActivity.class);
        if (list.get(position)!=null&&list.get(position).getMtid() != null) {
            intent.putExtra(ContentCode.MATTERS_ID, (list.get(position).getMtid()));
        }
        intent.putExtra(ContentCode.MATTERS_NUMBER, (list.get(position).getNumber()));
        intent.putExtra(ContentCode.MATTERS_NAME, list.get(position).getName());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }


    @Override
    public void showMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void onCompleted() {
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        list.clear();
        if (departmentCodeList.size() == 0) {
            ToastUtil.shortShowText("数据加载失败，请稍后重试");
            return;
        }
        depid = departmentCodeList.get(position);
        presenter.getAffairGuideList(adapter, list, depid);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        llSearch.setVisibility(View.GONE);
        ivSearch.setVisibility(View.VISIBLE);
        return false;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            GotoActivity(SearchActivity.class, etSearch.getText().toString());
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        GotoActivity(SearchActivity.class, etSearch.getText().toString());
    }
}
