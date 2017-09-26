package com.shyms.farendating.home.business_space;

import android.util.Log;

import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.home.business_space.model.InformationList;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by hks on 2016/3/11.
 */
public class BusinessSpaceModel implements BusinessSpaceListener {


    @Override
    public void getInformationList(int page_no, int page_size, final OnBusinessSpaceListener listener) {
        NetRequest.APIInstance2.getInformationList(page_no, page_size)
                .subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(informationList -> {
                    Log.d("BusinessSpaceModel", "informationList:" + informationList);
                    if ("0".equals(informationList.getCode())) {
                        listener.onSuccess(informationList);
                    } else {
                        listener.onFailed(informationList.getMessage().toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        listener.onFailed("服务器连接超时,请重试！");
                    }
                });
    }

    public interface OnBusinessSpaceListener {
        void onSuccess(InformationList informationList);

        void onFailed(String msg);
    }

}
