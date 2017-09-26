package com.shyms.farendating.home.guide.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shyms.faren.R;
import com.shyms.farendating.home.guide.details.MattersDetailsActivity;
import com.shyms.farendating.home.guide.model.AffairsInfo;
import com.shyms.farendating.home.guide.recyclerview.MyAdapter;
import com.shyms.farendating.utils.ContentCode;
import com.shyms.farendating.utils.MyRecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hokas.base.BaseActivity;

public class SearchActivity extends BaseActivity implements SearchView, MyRecyclerViewItemClickListener {

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

    private String content; //传过来的搜索字段
    private SearchPresenterImp presenterImp;
    private List<AffairsInfo.DataEntity> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initRecyclerViewLayout();
        initView();
        initData();
    }

    @Override
    public void initData() {
        tvTitle.setText("搜索结果");
        content = getIntent().getStringExtra("content");
        etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                llSearch.setVisibility(View.GONE);
                ivSearch.setVisibility(View.VISIBLE);
                return false;
            }
        });

        list = new ArrayList<>();
        MyAdapter adapter = new MyAdapter(list);
        recyclerView.setAdapter(adapter);

        presenterImp = new SearchPresenterImp(this);
        presenterImp.initAdapter(list, adapter);
        presenterImp.getSearchEnd(content);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    hideSoftInput(v);
                    list.clear();
                    content = etSearch.getText().toString();
                    presenterImp.getSearchEnd(content);
                    return true;
                }
                return false;
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput(v);
                list.clear();
                content = etSearch.getText().toString();
                presenterImp.getSearchEnd(content);
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!swipeContainer.isRefreshing())
                    swipeContainer.setRefreshing(true);
                list.clear();
                presenterImp.getSearchEnd(content);

            }
        });
        swipeContainer.setColorSchemeResources(R.color.blue_violet, R.color.green_yellow);
        adapter.setOnItemClickListener(this);
    }

    private void initRecyclerViewLayout() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void showMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void complete() {
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d("SearchActivity", "position:" + position);
        Intent intent = new Intent(this, MattersDetailsActivity.class);
        intent.putExtra(ContentCode.MATTERS_ID, (list.get(position).getMtid()));
        intent.putExtra(ContentCode.MATTERS_NAME, list.get(position).getName());
        intent.putExtra(ContentCode.MATTERS_NUMBER, list.get(position).getNumber());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
