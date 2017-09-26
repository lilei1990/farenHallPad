package com.shyms.farendating.home.guide.search;


import com.shyms.farendating.home.guide.model.AffairsInfo;
import com.shyms.farendating.home.guide.recyclerview.MyAdapter;

import java.util.List;

/**
 * Created by hks on 2016/3/18.
 */
public interface SearchPresenter {

    void getSearchEnd(String content);
    void initAdapter(List<AffairsInfo.DataEntity> list, MyAdapter adapter);
}
