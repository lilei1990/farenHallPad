package com.shyms.farendating.home.guide;

import android.util.Log;

import com.shyms.farendating.home.guide.model.AffairsInfo;
import com.shyms.farendating.home.guide.recyclerview.MyAdapter;
import com.shyms.farendating.home.guide.recyclerview.MyGuideAdapter;

import java.util.List;

/**
 * Created by hks on 2016/3/16.
 */
public class GuidePresenter {
    private GuideView view;
    private GuideModel model;

    public GuidePresenter(GuideView view) {
        this.view = view;
        model = new GuideModel();
    }

    /**
     * 办事指南列表
     */
    public void getAffairGuideList(final MyGuideAdapter adapter, final List<AffairsInfo.DataEntity> list,
                                   String depid) {
        model.getAffairGuideList(depid, new GuideModel.OnGuideListener() {
            @Override
            public void onSuccess(AffairsInfo affairsInfo) {
                view.onCompleted();

                list.addAll(affairsInfo.getData());
                Log.i("test", affairsInfo.getData().size() + "wwww");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String msg) {
                view.showMsg(msg);
            }
        });


    }



}
