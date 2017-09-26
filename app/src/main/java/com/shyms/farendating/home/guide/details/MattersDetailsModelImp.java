package com.shyms.farendating.home.guide.details;

import android.util.Log;

import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.home.guide.model.MattersDetailsInfo;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MattersDetailsModelImp implements MattersDetailsModel {

    @Override
    public void getAffairGuideDetail(String mtid, final OnMattersDetailsListener listener) {
        NetRequest.APIInstance.getAffairGuideDetail(mtid).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Action1<MattersDetailsInfo>() {
                    @Override
                    public void call(MattersDetailsInfo mattersDetailsInfo) {
                        if("0".equals(mattersDetailsInfo.getCode())){
                            listener.onSuccess(mattersDetailsInfo);
                        }else
                            listener.onFailed(mattersDetailsInfo.getMessage().toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        listener.onFailed("网络连接失败");
                        Log.d("MattersDetailsModelImp", throwable.toString());
                    }
                });
    }

    public interface OnMattersDetailsListener {
        void onSuccess(MattersDetailsInfo mattersDetailsInfo);

        void onFailed(String msg);
    }
}
