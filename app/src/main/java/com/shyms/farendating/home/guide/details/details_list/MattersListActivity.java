package com.shyms.farendating.home.guide.details.details_list;

import android.os.Bundle;
import android.widget.ListView;

import com.shyms.faren.R;
import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.home.guide.details.details_list.recyclerview.MaterialAdapter;
import com.shyms.farendating.utils.ContentCode;
import com.shyms.farendating.utils.GlobalConstant;
import com.shyms.farendating.utils.LogcatUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hokas.base.BaseActivity;
import me.hokas.utils.ToastUtil;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 材料列表
 */
public class MattersListActivity extends BaseActivity {

    @Bind(R.id.lv_material_list)
    protected ListView mlistView;
    private MaterialAdapter mAdapter;

    private String mattersName;
    private String mtid;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matters_list);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initData() {

        mattersName = getIntent().getStringExtra(ContentCode.MATTERS_NAME);
        mtid = getIntent().getStringExtra(ContentCode.MATTERS_ID);
        number = getIntent().getStringExtra(ContentCode.MATTERS_NUMBER);
        LogcatUtil.d("MATTERS_ID", "mtid=" + mtid);
        tvTitle.setText(mattersName);

        mAdapter = new MaterialAdapter(this);
        mlistView.setAdapter(mAdapter);
        mActivityHelper.showProgressDialog("正在加载...");

        NetRequest.APIInstance.getMtSample(mtid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results -> {
                    mActivityHelper.dismissProgressDialog();
                    if (results.code.equals(GlobalConstant.REQUEST_SUCCESS)) {
                        mAdapter.setDataList(results.data);
                    } else {
                        ToastUtil.shortShowText(results.message);
                    }
                }, throwable -> {
                    mActivityHelper.dismissProgressDialog();
                    ToastUtil.shortShowText("获取数据失败");
                });
    }


}
