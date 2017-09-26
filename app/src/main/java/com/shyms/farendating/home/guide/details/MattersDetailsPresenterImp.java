package com.shyms.farendating.home.guide.details;

import com.shyms.farendating.home.guide.model.MattersDetailsInfo;

/**
 * Created by hks on 2016/3/16.
 */
public class MattersDetailsPresenterImp {
    private MattersDetailsView view;
    private MattersDetailsModelImp model;

    public MattersDetailsPresenterImp(MattersDetailsView view) {
        this.view = view;
        model = new MattersDetailsModelImp();
    }

    public void getAffairGuideDetail(String mtid){
        model.getAffairGuideDetail(mtid, new MattersDetailsModelImp.OnMattersDetailsListener() {
            @Override
            public void onSuccess(MattersDetailsInfo mattersDetailsInfo) {
                view.showContent(mattersDetailsInfo.getData().getContent());
            }

            @Override
            public void onFailed(String msg) {
                view.showMsg(msg);
            }
        });
    }
}
