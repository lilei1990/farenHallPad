package com.shyms.farendating.home.guide.details.details_list;

import com.shyms.farendating.home.guide.details.details_list.recyclerview.MyAdapter;

import java.util.List;

public interface MattersListPresenter {
    void initAdapter(List<String> list, MyAdapter adapter);
    void getMattersList(String MtId);
}
