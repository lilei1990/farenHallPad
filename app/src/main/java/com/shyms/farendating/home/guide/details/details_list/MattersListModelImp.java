package com.shyms.farendating.home.guide.details.details_list;

import android.util.Log;

import com.shyms.farendating.http.api.NetRequest;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by hks on 2016/3/17.
 */
public class MattersListModelImp implements MattersListModel{

    private OnMattersListModelListener listener;

    public MattersListModelImp(OnMattersListModelListener listener) {
        this.listener = listener;
    }

    @Override
    public void getMattersList(String MtId) {
        NetRequest.APIInstance.getMattersList(MtId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if ("0".equals(result.code)) {
                        listener.onSuccess(result.data);
                    } else {
                        listener.onFailed(result.message.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        listener.onFailed("网络连接失败");
                        Log.d("MattersListModelImp", throwable.toString());
                    }
                });
    }

    public interface OnMattersListModelListener{
        void onSuccess(List<String> data);

        void onFailed(String msg);
    }
}


