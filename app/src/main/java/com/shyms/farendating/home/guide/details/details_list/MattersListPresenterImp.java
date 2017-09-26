package com.shyms.farendating.home.guide.details.details_list;

import com.shyms.farendating.home.guide.details.details_list.recyclerview.MyAdapter;
import com.shyms.farendating.home.guide.model.MattersList;

import java.util.List;

/**
 * Created by hks on 2016/3/17.
 */
public class MattersListPresenterImp implements MattersListPresenter, MattersListModelImp.OnMattersListModelListener {

    private MattersListView view;
    private MattersListModelImp modelImp;
    private List<String> list;
    private MyAdapter adapter;

    public MattersListPresenterImp(MattersListView view) {
        this.view = view;
        modelImp = new MattersListModelImp(this);
    }

    @Override
    public void initAdapter(List<String> list, MyAdapter adapter) {
            this.adapter = adapter;
            this.list = list;
    }

    @Override
    public void getMattersList(String MtId) {
        modelImp.getMattersList(MtId);
    }

    @Override
    public void onSuccess(List<String> data) {
        list.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(String msg) {
        view.showMsg(msg);
    }
}
