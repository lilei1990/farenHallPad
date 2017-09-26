package com.shyms.farendating.home.my_handle_affairs.material;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.actionsheet.ActionSheet;
import com.shyms.faren.R;
import com.shyms.farendating.GlobalConstant;
import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.event.MainEventData;
import com.shyms.farendating.home.my_handle_affairs.material.model.PreviewFragment;
import com.shyms.farendating.model.NDetailImage;
import com.shyms.farendating.model.NSign;
import com.shyms.farendating.utils.ContentCode;
import com.shyms.farendating.utils.LogcatUtil;
import com.shyms.farendating.utils.UserManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.toolsfinal.DeviceUtils;
import de.greenrobot.event.EventBus;
import me.hokas.base.BaseActivity;
import me.hokas.base.BaseListAdapter;
import me.hokas.utils.ImageUtils;
import me.hokas.utils.SPUtil;
import me.hokas.utils.ToastUtil;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;

public class TakePicActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener {

    public final static int SUCCESS_COUNT_PIC = 1104;
    public final int REQUEST_CODE_CAMERA = 1100;
    public final int REQUEST_CODE_GALLERY = 1101;
    public static String PREVIEW_PIC = "preview_img";
    public static String UPLOAD_PIC = "upload_img";
    public final static String DETAIL_OPERATE = "detail_operate";
    @Bind(R.id.iv_add)
    ImageView ivAdd;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_next)
    Button tvNext;
    @Bind(R.id.fl_add_img)
    protected FrameLayout mAddImg;
    @Bind(R.id.grid_online_img)
    protected GridView mGridView;

    private String matters_sertal_number;
    private ShrinkImgAdapter mShrinkAdapter;
    public String detail_id; //材料id
    private String detail_title;  //标题
    private String detail_operate;//状态
    public List<NDetailImage> base64ThumbList;
    private List<String> guidList;
    private boolean isShowDelete;
    private ActionSheet.Builder mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load_material);
        ButterKnife.bind(this);
        initData();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.iv_back)
    public void onClickBack() {
        finish();
    }


    @Override
    public void initData() {
        Intent intent = getIntent();
        matters_sertal_number = intent.getStringExtra(ContentCode.MATTERS_SERTAL_NUMBER);
        detail_id = intent.getStringExtra(ContentCode.MATTERS_LEI_BIE);
        detail_title = intent.getStringExtra(ContentCode.MATTERS_NAME);
        detail_operate = intent.getStringExtra(DETAIL_OPERATE);
        tvContent.setText(detail_title);
        ivAdd.setOnClickListener(this);
        base64ThumbList = new ArrayList<>();
        mShrinkAdapter = new ShrinkImgAdapter(this);
        mGridView.setAdapter(mShrinkAdapter);
        mShrinkAdapter.setDataList(base64ThumbList);
        mGridView.setOnItemClickListener(this);
        mGridView.setOnItemLongClickListener(this);

        if (detail_operate != null && detail_operate.equals(PREVIEW_PIC)) {
            mAddImg.setVisibility(View.GONE);
            tvNext.setVisibility(View.GONE);
        } else {
            mAddImg.setVisibility(View.VISIBLE);
            tvNext.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(getToken())) {
            getServerToken();
        } else {
            refreshOnlinePic(detail_id);
        }

        initActionBar();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add:
                if (isShowDelete) {
                    isShowDelete = false;
                    mShrinkAdapter.notifyDataSetChanged();
                }
                mActionBar.show();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startFragment(position);
    }

    public void startFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(PreviewFragment.POSITION_INFO, position);
        bundle.putString(PreviewFragment.GUID_INFO, guidList.get(position));
        bundle.putString(PreviewFragment.DETAIL_TITLE, detail_id);
        bundle.putString(PreviewFragment.DETAIL_OPERATE, detail_operate);
        PreviewFragment fragment = new PreviewFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fragmnt_right_in, R.anim.fragmnt_left_out);
        fragmentTransaction.add(R.id.container_main_page, fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        isShowDelete = true;
        mShrinkAdapter.setIsShowDelete(isShowDelete);
        return true;
    }

    public void initActionBar() {
        mActionBar = ActionSheet.createBuilder(TakePicActivity.this, getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles("拍照", "从相册中选择")
                .setCancelableOnTouchOutside(true)
                .setListener(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        if (isShowDelete) {
                            isShowDelete = false;
                            mShrinkAdapter.setIsShowDelete(isShowDelete);
                        }
                        switch (index) {
                            case 0:
                                GalleryFinal.openCamera(REQUEST_CODE_CAMERA, new GalleryFinal.OnHanlderResultCallback() {
                                    @Override
                                    public void onHandlerSuccess(int requestCode, List<PhotoInfo> resultList) {
                                        if (resultList != null) {
                                            startUploadPic(resultList);
                                        }
                                    }

                                    @Override
                                    public void onHandlerFailure(int requestCode, String errorMsg) {
                                        ToastUtil.shortShowText(errorMsg);
                                    }
                                });
                                break;
                            case 1:
                                GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, 4,
                                        new GalleryFinal.OnHanlderResultCallback() {
                                            @Override
                                            public void onHandlerSuccess(int requestCode, List<PhotoInfo> resultList) {
                                                if (resultList != null) {
                                                    startUploadPic(resultList);
                                                }
                                            }

                                            @Override
                                            public void onHandlerFailure(int requestCode, String errorMsg) {
                                                ToastUtil.shortShowText(errorMsg);
                                            }
                                        });
                                break;

                            default:
                                break;
                        }
                    }
                });
    }

    @Override
    protected void tokenIsValid() {
        super.tokenIsValid();
        refreshOnlinePic(detail_id);
    }

    public void startUploadPic(List<PhotoInfo> resultList) {
        if (!(resultList.size() > 0)) {
            ToastUtil.shortShowText("请添加图片");
            return;
        }
        mActivityHelper.showProgressDialog("正在上传...");

        if (TextUtils.isEmpty(getToken())) {
            getServerToken();
        } else {
            uploadPic(resultList, detail_id);
        }


    }

    private void uploadPic(List<PhotoInfo> resultList, String detail_id) {
        NetRequest.uploadImage(resultList, detail_id)
                .subscribe(results -> {
                    mActivityHelper.dismissProgressDialog();
                    if (results.code.equals(GlobalConstant.REQUEST_SUCCESS)) {
                        if (results.data.equals("1")) {
                            ToastUtil.shortShowText("图片上传成功");
                        }
                    } else if (results.getCode().equals(GlobalConstant.TOKEN_ERROR)) {
                        getServerToken();
                    } else {
                        LogcatUtil.d("");
                    }

                }, throwable -> {
//                    ToastUtil.shortShowText("请检查网络");
                    mActivityHelper.dismissProgressDialog();
//                    TakePicActivity.this.refreshOnlinePic(detail_id);
                });
    }

    public void refreshOnlinePic(String detail_id) {
        mShrinkAdapter.notifyDataSetChanged();
        mActivityHelper.showProgressDialog("正在刷新");
        NSign nSign = new NSign();
        NetRequest.APIInstance.getShrinkImage(detail_id, UserManager.getInstance().getLastUserInfo().getData().getLog_verify_code(),
                nSign.getTimestamp(), getToken(), nSign.getSign())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results -> {
                    mActivityHelper.dismissProgressDialog();

                    if (results.code.equals(GlobalConstant.REQUEST_SUCCESS)) {
                        tvNext.setText("全部删除");

                        base64ThumbList = results.data.thumbs;
                        getPicGuidList(base64ThumbList);
                        mShrinkAdapter.setDataList(base64ThumbList);
                    } else if (results.getCode().equals(GlobalConstant.TOKEN_ERROR)) {
                        getServerToken();
                    } else {
                        ToastUtil.shortShowText(results.message);
                    }

                }, throwable -> {
                    mActivityHelper.dismissProgressDialog();
                    ToastUtil.shortShowText("网络连接失败");
                });
    }

    public void getPicGuidList(List<NDetailImage> base64ThumbList) {
        if (guidList == null) {
            guidList = new ArrayList<>(base64ThumbList.size());
        } else {
            guidList.clear();
        }
        for (int i = 0; i < base64ThumbList.size(); i++) {
            String guid = base64ThumbList.get(i).name.
                    substring(0, base64ThumbList.get(i).name.lastIndexOf("~"));
            guidList.add(guid);
        }
    }

    public void clearCacheByGuid() {
        for (int i = 0; i < guidList.size(); i++) {
            SPUtil.delete(guidList.get(i));
        }
        guidList.clear();
    }

    public void deleteAllImage() {
        if (isShowDelete) {
            isShowDelete = false;
            mShrinkAdapter.setIsShowDelete(isShowDelete);
        }
        mActivityHelper.showProgressDialog("正在删除...");
        NSign nSign = new NSign();
        NetRequest.APIInstance.deleteAll(detail_id, nSign.getTimestamp(), getToken(), nSign.getSign())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results -> {
                    if (results.code.equals(GlobalConstant.REQUEST_SUCCESS)) {
                        if (results.data.equals(base64ThumbList.size() + "")) {
                            ToastUtil.shortShowText("删除成功");
                            base64ThumbList.clear();
                            mShrinkAdapter.notifyDataSetChanged();
                            clearCacheByGuid();
                        }
                    } else if (results.getCode().equals(GlobalConstant.TOKEN_ERROR)) {
                        getServerToken();
                    } else {
                        ToastUtil.shortShowText(results.message);
                    }
                    mActivityHelper.dismissProgressDialog();
                }, throwable -> {
                    if (throwable instanceof HttpException) {
                        ToastUtil.shortShowText("网络连接异常");
                    } else {
                        ToastUtil.shortShowText("删除失败");
                    }
                    mActivityHelper.dismissProgressDialog();
                });
    }

    public void onEventMainThread(MainEventData event) {

        if (event.id == SUCCESS_COUNT_PIC) {
            ToastUtil.shortShowText(event.position + "张图片上传成功");
            TakePicActivity.this.refreshOnlinePic(detail_id);
        }
    }

    @OnClick(R.id.tv_next)
    public void chooseDeleteAllImg() {
        if (base64ThumbList.size() == 0) {
            ToastUtil.shortShowText("当前没有图片");
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("确定删除所有图片")
                .setMessage("删除之后不可恢复")
                .setNegativeButton("取消", (dialog, which) -> {
                    dialog.dismiss();
                }).setPositiveButton("确定", (dialog, which) -> {
            deleteAllImage();
            dialog.dismiss();
        }).create().show();
    }

    public void deleteSingleImg(String guid, int position) {
        mActivityHelper.showProgressDialog("正在删除");
        NSign nSign = new NSign();
        NetRequest.APIInstance.deleteOne(detail_id, guid, nSign.getTimestamp(), getToken(), nSign.getSign())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results -> {
                    if (results.code.equals(GlobalConstant.REQUEST_SUCCESS)) {
                        if (results.data.equals("1")) {
                            ToastUtil.shortShowText("图片已删除");
                            SPUtil.delete(guid);
                            base64ThumbList.remove(position);
                            guidList.remove(position);
                            mShrinkAdapter.notifyDataSetChanged();
                            EventBus.getDefault().post(new MainEventData(PreviewFragment.DELETE_SUCCESS));

                        } else {
                            ToastUtil.shortShowText("删除失败");
                        }
                    } else if (results.getCode().equals(GlobalConstant.TOKEN_ERROR)) {
                        getServerToken();
                    }
                    mActivityHelper.dismissProgressDialog();
                }, throwable -> {
                    if (throwable instanceof HttpException) {
                        ToastUtil.shortShowText("网络异常");
                    } else {
                        ToastUtil.shortShowText("请求失败");
                    }
                    mActivityHelper.dismissProgressDialog();
                });
    }

    class ShrinkImgAdapter extends BaseListAdapter<NDetailImage> {
        private int mScreenWidth;
        private boolean isShowDelete;//根据这个变量来判断是否显示删除图标，true是显示，false是不显示

        public ShrinkImgAdapter(Activity activity) {
            super(activity);
            this.mScreenWidth = DeviceUtils.getScreenPix(activity).widthPixels;
        }

        public void setDataList(List<NDetailImage> dataList) {
            if (dataList != null) {
                mDataList = dataList;
            }
            notifyDataSetChanged();
        }

        public void setIsShowDelete(boolean isShowDelete) {
            this.isShowDelete = isShowDelete;
            notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.item_shrink_image_list, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.ivPhoto.setImageBitmap(ImageUtils.stringToBitmap(mDataList.get(position).img));

            setHeight(convertView);
            holder.mRLdeleteView.setVisibility(isShowDelete ? View.VISIBLE : View.GONE);//设置删除按钮是否显示
            if (holder.mRLdeleteView.getVisibility() == View.VISIBLE) {
                holder.mRLdeleteView.setOnClickListener(view1 -> {
                    deleteSingleImg(guidList.get(position), position);
                });
            }
            return convertView;
        }

        private void setHeight(final View convertView) {
            int height = mScreenWidth / 3 - 25;
            convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        }

        class ViewHolder {
            @Bind(R.id.iv_photo)
            ImageView ivPhoto;
            @Bind(R.id.rl_delete_markView)
            RelativeLayout mRLdeleteView;

            ViewHolder(View parent) {
                ButterKnife.bind(this, parent);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isShowDelete) {
                isShowDelete = false;
                mShrinkAdapter.setIsShowDelete(isShowDelete);
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
