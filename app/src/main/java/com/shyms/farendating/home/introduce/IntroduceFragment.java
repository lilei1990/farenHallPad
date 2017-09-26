package com.shyms.farendating.home.introduce;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shyms.faren.R;
import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.model.NBriefIntroduction;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.loader.FrescoImageLoader;
import me.hokas.base.BaseFragment;
import me.hokas.utils.ToastUtil;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 大厅介绍
 */
public class IntroduceFragment extends BaseFragment {


    @Bind(R.id.introduce_one)
    SimpleDraweeView mIntroduceOne;
    @Bind(R.id.introduce_two)
    SimpleDraweeView mIntroduceTwo;
    @Bind(R.id.introduce_three)
    SimpleDraweeView mIntroduceThree;
    @Bind(R.id.content)
    TextView mContent;
    private FrescoImageLoader mImageLoader;
    private PopupWindow popupWindow;
    private NBriefIntroduction mNBriefIntroduction;

    public IntroduceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_introduce, container, false);

        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getLatestData();
        mImageLoader = new FrescoImageLoader(mActivity);

    }

    private void getLatestData() {
        NetRequest.APIInstance.getHallIntroduction()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result.code == 1) {
                        adapterData(result.data);
                    }
                }, throwable -> {
                    ToastUtil.shortShowText("请检查网络");
                });
    }

    private void adapterData(NBriefIntroduction briefIntroduction) {
        mNBriefIntroduction = briefIntroduction;
        mIntroduceOne.setImageURI(Uri.parse(NetRequest.URL + briefIntroduction.PicList.get(0).imgPadUrl));
        mIntroduceTwo.setImageURI(Uri.parse(NetRequest.URL + briefIntroduction.PicList.get(1).imgPadUrl));
        mIntroduceThree.setImageURI(Uri.parse(NetRequest.URL + briefIntroduction.PicList.get(2).imgPadUrl));
//        LogcatUtil.d("briefIntroduction", "briefIntroduction.content=" + briefIntroduction.content);
        mContent.setText(briefIntroduction.Content);


    }

    @OnClick({R.id.introduce_one, R.id.introduce_two, R.id.introduce_three})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.introduce_one:
                initPopWindow(view, 0);
                break;
            case R.id.introduce_two:
                initPopWindow(view, 1);
                break;
            case R.id.introduce_three:
                initPopWindow(view, 2);
                break;
        }
    }

    private void initPopWindow(View view, int position) {
        if (mNBriefIntroduction == null) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.pop_window_preview, null);
        int height = mActivity.getWindowManager().getDefaultDisplay().getHeight();
        int width = mActivity.getWindowManager().getDefaultDisplay().getWidth();
        WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
//        params.alpha = 0.7f;
        mActivity.getWindow().setAttributes(params);
        popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(width - 400);
        popupWindow.setHeight(height - 300);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        popupWindow.setFocusable(true);
        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                closePopupWindow();
                return false;
            }
        });
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x90000000);
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
//        popupWindow.setAnimationStyle(R.style.AnimationPreview);
        popupWindow.setOutsideTouchable(true);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) contentView.findViewById(R.id.simple_view);
        simpleDraweeView.setImageURI(Uri.parse(NetRequest.URL + mNBriefIntroduction.PicList.get(position).imgPadUrl));
        popupWindow.setOnDismissListener(() -> closePopupWindow());
        popupWindow.showAtLocation(contentView,
                Gravity.CENTER, 0, 50);
    }

    private void closePopupWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
            WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
            params.alpha = 1f;
            mActivity.getWindow().setAttributes(params);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
