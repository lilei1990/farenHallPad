package com.shyms.farendating.home.business_space;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shyms.faren.R;
import com.shyms.farendating.home.business_space.model.InformationList;
import com.shyms.farendating.home.business_space.recyclerview.MyAdapter;
import com.shyms.farendating.utils.MyRecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hokas.base.BaseFragment;

/**
 * 创业空间
 */
public class BusinessSpaceFragment extends BaseFragment implements BusinessSpaceView, SwipeRefreshLayout.OnRefreshListener,
        MyRecyclerViewItemClickListener {


    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private int PAGE_NO = 0;
    private int PAGE_SIZE = 10000;
    private List<InformationList.DataEntity> dataEntityList;
    private MyAdapter adapter;

    private BusinessSpacePresenter presenter;

    private OnBusinessSpaceFragmentListener listener;

    public BusinessSpaceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_business_space, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        dataEntityList = new ArrayList<>();
        adapter = new MyAdapter(dataEntityList);
        recyclerView.setAdapter(adapter);

        presenter = new BusinessSpacePresenter(this);
        presenter.getInformationList(adapter,dataEntityList,PAGE_NO,PAGE_SIZE);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.blue_violet, R.color.green_yellow);
        adapter.setOnItemClickListener(this);
        listener = (OnBusinessSpaceFragmentListener) getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showMsg(String msg) {
        showToast(msg);
    }


    @Override
    public void onCompleted() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        PAGE_NO = 0;
        dataEntityList.clear();
        presenter.getInformationList(adapter,dataEntityList,PAGE_NO,PAGE_SIZE);
    }

    @Override
    public void onItemClick(View view, int position) {
//        Log.i("test","aa"+dataEntityList.get(position).getTitle());
        listener.toDetailsListener(dataEntityList.get(position));
        listener.setTitle(dataEntityList.get(position).getTitle());
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public void onResume() {
        super.onResume();
        listener.setTitle("创业空间");
    }

    public interface OnBusinessSpaceFragmentListener{
        void toDetailsListener(InformationList.DataEntity dataEntity);
        void setTitle(String title);
    }

}
