package com.shyms.farendating.home.my_handle_affairs;

import com.shyms.farendating.home.my_handle_affairs.model.HandleAffairsModel;
import com.shyms.farendating.home.my_handle_affairs.recyclerview.MyAdapter;

import java.util.List;

public class MyHandleAffairsPresenterImp implements MyHandleAffairsPresenter,
        MyHandleAffairsModelImp.OnHandleAffairsListener {
    private MyHandleAffairsView view;
    private MyHandleAffairsModelImp modelImp;
    private MyAdapter adapter;
    private List<HandleAffairsModel.DataEntity> list;

    public MyHandleAffairsPresenterImp(MyHandleAffairsView view, MyAdapter adapter, List<HandleAffairsModel.DataEntity> list) {
        this.view = view;
        this.adapter = adapter;
        this.list = list;
        modelImp = new MyHandleAffairsModelImp(this);
    }


    @Override
    public void getAffairRecord(String uid) {
        modelImp.getAffairRecord(uid);
    }

    @Override
    public void onSuccess(HandleAffairsModel handleAffairsModel) {
        view.onCompleted();
        list.addAll(handleAffairsModel.getData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(String msg) {
        view.showMsg(msg);
    }
}
