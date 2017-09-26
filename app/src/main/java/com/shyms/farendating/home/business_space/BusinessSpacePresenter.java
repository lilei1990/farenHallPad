package com.shyms.farendating.home.business_space;

import com.shyms.farendating.home.business_space.model.InformationList;
import com.shyms.farendating.home.business_space.recyclerview.MyAdapter;

import java.util.List;

/**
 * Created by hks on 2016/3/11.
 */
public class BusinessSpacePresenter {
    private BusinessSpaceView view;
    private BusinessSpaceModel model;

    public BusinessSpacePresenter(BusinessSpaceView view) {
        this.view = view;
        model= new BusinessSpaceModel();
    }

    public void getInformationList(final MyAdapter adapter, final List<InformationList.DataEntity> dataEntityList,
                                   int page_no, int page_size){
        model.getInformationList(page_no, page_size, new BusinessSpaceModel.OnBusinessSpaceListener() {
            @Override
            public void onSuccess(InformationList informationList) {
                    view.onCompleted();
                    dataEntityList.addAll(informationList.getData());
                    adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailed(String msg) {
                view.showMsg(msg);
            }
        });
    }
}
