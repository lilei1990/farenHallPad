package com.shyms.farendating.home.guide.search;

import android.util.Log;

import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.home.guide.model.AffairsInfo;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by hks on 2016/3/18.
 */
public class SearchModelImp implements SearchModel {

    private OnSearchListener listener;

    public SearchModelImp(OnSearchListener listener) {
        this.listener = listener;
    }

    @Override
    public void getSearchEnd(String content) {
        NetRequest.APIInstance.getSearchEnd(content).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<AffairsInfo>() {
                    @Override
                    public void call(AffairsInfo affairsInfo) {
                        if ("0".equals(affairsInfo.getCode()))
                            listener.onSuccess(affairsInfo);
                        else
                            listener.onFailed(affairsInfo.getMessage().toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        listener.onFailed("您搜索的关键字没有数据！");
                        Log.d("SearchModelImp", throwable.toString());
                    }
                });
    }


    public interface OnSearchListener {
        void onSuccess(AffairsInfo affairsInfo);

        void onFailed(String msg);
    }
}
