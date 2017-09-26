package com.shyms.farendating.home.my_handle_affairs.material.model;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shyms.faren.R;
import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.event.MainEventData;
import com.shyms.farendating.home.my_handle_affairs.material.TakePicActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import me.hokas.base.BaseFragment;
import me.hokas.utils.ImageUtils;
import me.hokas.utils.SPUtil;
import me.hokas.utils.ToastUtil;
import photoview.PhotoView;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Weimin on 5/20/2016.
 */
public class PreviewFragment extends BaseFragment {

    public static final String POSITION_INFO = "position_info";
    public static final String GUID_INFO = "image_guid";
    public final static String DETAIL_TITLE = "detail_title";
    public final static String DETAIL_OPERATE = "detail_operate";
    public final static int DELETE_SUCCESS = 1018;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_next)
    Button tvNext;
    @Bind(R.id.photo_view)
    protected PhotoView mPreview;
    private int position;
    private String guid;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_preview, container, false);
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvContent.setText("预览图片");
        position = getArguments().getInt(POSITION_INFO);
        guid = getArguments().getString(GUID_INFO);
        String detail_id = getArguments().getString(DETAIL_TITLE);
        String detail_operate = getArguments().getString(DETAIL_OPERATE);
        if (detail_operate != null && detail_operate.equals(TakePicActivity.PREVIEW_PIC)) {
            tvNext.setVisibility(View.GONE);
        } else {
            tvNext.setText("删除图片");
            tvNext.setVisibility(View.VISIBLE);
        }
        getWholeImage(detail_id, guid);
        EventBus.getDefault().register(this);
    }

    public void getWholeImage(String detail_id, String guid) {
        if (!TextUtils.isEmpty((String) SPUtil.get(guid, ""))) {
            mPreview.setImageBitmap(ImageUtils.stringToBitmap((String) SPUtil.get(guid, "")));
        } else {
            mActivityHelper.showProgressDialog("正在加载");
            NetRequest.covertToBitmap(detail_id, guid)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        if (result != null) {
                            mPreview.setImageBitmap(result);
                        } else {
                            ToastUtil.shortShowText("图片加载失败,请检查当前网络");
                        }
                        mActivityHelper.dismissProgressDialog();
                    }, throwable -> {
                        ToastUtil.shortShowText("图片加载失败,请检查当前网络");
                        mActivityHelper.dismissProgressDialog();
                    });
        }
    }

    @OnClick(R.id.tv_next)
    public void deleteSingleImg() {

        new AlertDialog.Builder(mActivity)
                .setTitle("删除图片")
                .setMessage("您确定删除该图片")
                .setPositiveButton("确定", (dialog, i) -> {
                    dialog.dismiss();
                    ((TakePicActivity) mActivity).deleteSingleImg(guid, position);
                }).setNegativeButton("取消", (dialog, i) -> {
            dialog.dismiss();
        }).create().show();
    }

    public void onEventMainThread(MainEventData event) {

        int id = event.id;
        if (id == DELETE_SUCCESS) {
            mActivity.onBackPressed();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
