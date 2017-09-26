package com.shyms.farendating.home.guide.search;

import com.shyms.farendating.home.guide.model.AffairsInfo;
import com.shyms.farendating.home.guide.recyclerview.MyAdapter;

import java.util.List;

/**
 * Created by hks on 2016/3/18.
 */
public class SearchPresenterImp implements SearchModelImp.OnSearchListener,SearchPresenter {

    private SearchView view;
    private SearchModelImp modelImp;
    private List<AffairsInfo.DataEntity> list;
    private MyAdapter adapter;

    public SearchPresenterImp(SearchView view) {
        this.view = view;
        modelImp = new SearchModelImp(this);
    }



    @Override
    public void onSuccess(AffairsInfo affairsInfo) {
        list.addAll(affairsInfo.getData());
        adapter.notifyDataSetChanged();
        view.complete();
    }

    @Override
    public void onFailed(String msg) {
        view.showMsg(msg);
        view.complete();
    }

    @Override
    public void getSearchEnd(String content) {
        modelImp.getSearchEnd(content);
    }

    @Override
    public void initAdapter(List<AffairsInfo.DataEntity> list, MyAdapter adapter) {
        this.list = list;
        this.adapter = adapter;
    }
}
