package com.shyms.farendating.home.my_handle_affairs.material;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shyms.faren.R;
import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.home.my_handle_affairs.material.model.NMatterMaterial;
import com.shyms.farendating.home.my_handle_affairs.model.HandleAffairsModel;
import com.shyms.farendating.model.NSign;
import com.shyms.farendating.utils.ContentCode;
import com.shyms.farendating.utils.GlobalConstant;
import com.shyms.farendating.utils.UserManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.hokas.base.BaseActivity;
import me.hokas.utils.ToastUtil;
import rx.android.schedulers.AndroidSchedulers;

public class MaterialRecorderActivity extends BaseActivity {


    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private MyAdapterList adapter;
    private List<NMatterMaterial> materialUpInfoList;
    private String onlyNumber;
    private String name;
    private HandleAffairsModel.DataEntity mAffairRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_up);
        ButterKnife.bind(this);
        initView();
        initData();
        getLatestData();
    }


    @Override
    public void initView() {
        materialUpInfoList = new ArrayList<>();
        onlyNumber = getIntent().getStringExtra(ContentCode.MATTERS_SERTAL_NUMBER);
        mAffairRecord = (HandleAffairsModel.DataEntity) getIntent()
                .getSerializableExtra(ContentCode.WHOLE_RECORDER);
        name = getIntent().getStringExtra(ContentCode.MATTERS_NAME);
    }

    @Override
    public void initData() {
        tvContent.setText(name);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        adapter = new MyAdapterList(materialUpInfoList, mAffairRecord);
        listView.setAdapter(adapter);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (!mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(true);
            }
            materialUpInfoList.clear();
            getLatestData();
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getLatestData();
    }

    public void getLatestData() {
        NSign nSign = new NSign();
        NetRequest.APIInstance.affairMaterial(onlyNumber, UserManager.getInstance().getLastUserInfo().getData().getLog_verify_code(),
                nSign.getTimestamp(), getToken(), nSign.getSign())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (results.code.equals("0")) {
                        materialUpInfoList.clear();
                        materialUpInfoList.addAll(results.data);
                        adapter.notifyDataSetChanged();
                    } else if (results.code.equals(GlobalConstant.TOKEN_ERROR)) {
                        getServerToken();
                    } else {
                        ToastUtil.shortShowText(results.message);
                    }
                }, throwable -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    ToastUtil.shortShowText("网络连接失败，请检查网络设置");
                });
    }

    class MyAdapterList extends BaseAdapter {
        private List<NMatterMaterial> materialUpInfoList;
        private HandleAffairsModel.DataEntity nAffairRecord;
        private String statusText = "";

        public MyAdapterList(List<NMatterMaterial> materialUpInfoList, HandleAffairsModel.DataEntity affairRecord) {
            this.materialUpInfoList = materialUpInfoList;
            this.nAffairRecord = affairRecord;
        }

        @Override
        public int getCount() {
            return materialUpInfoList != null ? materialUpInfoList.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return materialUpInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(MaterialRecorderActivity.this).inflate(R.layout.list_item_up_material, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            NMatterMaterial material = materialUpInfoList.get(position);
            if (nAffairRecord.is_window_apply == null
                    || (nAffairRecord.is_window_apply != null && !nAffairRecord.is_window_apply.equals("1"))) {     //非综窗事项，流水单号GS开头，输出数据为字符类型的'null'
                if (material.is_need_upload != null && material.is_need_upload.equals("1")) {
                    if (material.liu_name.equals("FS01")) {
                        if (material.is_upload != null && material.is_upload.equals("1")) {
//                                输出《修改》按钮
                            setModify(holder);
                        } else {
//                                输出《上传》按钮
                            setUpload(holder);
                        }
                    } else if (nAffairRecord.liu_name.startsWith("FS0101")
                            && (material.bumen_result != null && material.bumen_result.equals("2"))) {      //注：bumen_result审核结果如果为fs0101不会为空
//                            输出《修改》按钮
                        setModify(holder);
                    } else {
//                            输出《查看》按钮
                        setPreview(holder);
                    }
                } else {
                    setNoBtn(holder);
//                        不输出任何按钮
                }
            } else {   //综窗事项，流水单号GH开头
                if (material.is_upload != null && material.is_upload.equals("1")
                        && material.is_need_upload != null && material.is_upload.equals("1")) {
                    setPreview(holder);
                } else {
                    setNoBtn(holder);
//                        不输出任何按钮
                }
            }
//            holder.btn_setting.setText("修改");
//            holder.btn_setting.setBackgroundResource(R.drawable.bg_appointment_scbtn);
            holder.tv_name.setText(materialUpInfoList.get(position).name);
            String preview = statusText;
            holder.btn_setting.setOnClickListener(v -> {
                Intent intent = new Intent(MaterialRecorderActivity.this, TakePicActivity.class);
                intent.putExtra(ContentCode.MATTERS_SERTAL_NUMBER, onlyNumber);
                intent.putExtra(ContentCode.MATTERS_LEI_BIE, materialUpInfoList.get(position).id);
                intent.putExtra(TakePicActivity.DETAIL_OPERATE, preview);
                intent.putExtra(ContentCode.MATTERS_NAME, materialUpInfoList.get(position).name);
                startActivity(intent);
            });
            return convertView;
        }

        private void setUpload(ViewHolder holder) {
            holder.btn_setting.setText("上传");
            holder.btn_setting.setBackgroundResource(R.drawable.bg_appointment_scbtn);
            statusText = TakePicActivity.UPLOAD_PIC;
        }

        private void setNoBtn(ViewHolder holder) {
            holder.btn_setting.setVisibility(View.GONE);
            statusText = null;
        }

        //
        private void setModify(ViewHolder holder) {
            holder.btn_setting.setText("修改");
            holder.btn_setting.setBackgroundResource(R.drawable.bg_appointment_xgbtn);
            statusText = TakePicActivity.UPLOAD_PIC;
        }

        //
        private void setPreview(ViewHolder holder) {

            holder.btn_setting.setText("查看");
            holder.btn_setting.setBackgroundResource(R.drawable.bg_appointment_ckbtn);
            statusText = TakePicActivity.PREVIEW_PIC;
        }

        class ViewHolder {
            private TextView tv_name;
            private ImageView iv_sure;
            private ImageView iv_close;
            private TextView btn_setting;

            ViewHolder(View view) {
                initViews(view);
            }

            private void initViews(View root) {
                tv_name = (TextView) root.findViewById(R.id.tv_name);
                iv_sure = (ImageView) root.findViewById(R.id.iv_sure);
                iv_close = (ImageView) root.findViewById(R.id.iv_close);
                btn_setting = (TextView) root.findViewById(R.id.btn_setting);
            }
        }
    }

    @OnClick(R.id.iv_back)
    public void onBackClick() {
        finish();
    }

}
