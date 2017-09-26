package com.shyms.farendating.home.my_handle_affairs.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shyms.faren.R;
import com.shyms.farendating.home.my_handle_affairs.material.MaterialRecorderActivity;
import com.shyms.farendating.home.my_handle_affairs.model.HandleAffairsModel;
import com.shyms.farendating.utils.ContentCode;

import java.util.List;

import me.hokas.base.BaseListAdapter;


public class MyAdapter extends BaseListAdapter<HandleAffairsModel.DataEntity> {


    public MyAdapter(Context context, List<HandleAffairsModel.DataEntity> mDataList) {
        super(context, mDataList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View view = mLayoutInflater.inflate(R.layout.list_item_my_handle, viewGroup, false);
        TextView tv_bian_hao = (TextView) view.findViewById(R.id.tv_bian_hao);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        LinearLayout ll_a = (LinearLayout) view.findViewById(R.id.ll_a);
        TextView tv_name_geti = (TextView) view.findViewById(R.id.tv_name_geti);
        LinearLayout rl_c = (LinearLayout) view.findViewById(R.id.rl_c);
        TextView xiang = (TextView) view.findViewById(R.id.xiang);
        TextView tv_bu_men = (TextView) view.findViewById(R.id.tv_bu_men);
        TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
        RelativeLayout ll_b = (RelativeLayout) view.findViewById(R.id.ll_b);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        Button btn_xiugai = (Button) view.findViewById(R.id.btn_xiugai);
        Button btn_quhao = (Button) view.findViewById(R.id.btn_quhao);
        HandleAffairsModel.DataEntity nAffairRecord = mDataList.get(position);
        tv_bian_hao.setText(nAffairRecord.onlynumber);
        tv_bu_men.setText(nAffairRecord.dname);
        tv_name.setText(nAffairRecord.name);
        tv_name_geti.setText(nAffairRecord.company_name);
        tv_time.setText(nAffairRecord.action_date);
        btn_xiugai.setText(nAffairRecord.liu_nametext);
//        String stateName = nAffairRecord.liu_name;
//        String flowState = "";
//        if (!stateName.startsWith("FS01") || stateName.equals("FS0103")
//                || (nAffairRecord.is_window_apply != null && nAffairRecord.is_window_apply.equals("1"))) {
//            mUpdate.setText("查看");
        if (!nAffairRecord.liu_name.startsWith("FS01") || nAffairRecord.liu_name.equals("FS0103")
                || (nAffairRecord.is_window_apply != null && nAffairRecord.is_window_apply.equals("1"))) {
            btn_quhao.setVisibility(View.VISIBLE);
            btn_quhao.setText("查看材料");
            btn_quhao.setBackgroundResource(R.drawable.bg_appointment_ckbtn);
        } else {
            btn_quhao.setVisibility(View.VISIBLE);
            btn_quhao.setText("修改材料");
            btn_quhao.setBackgroundResource(R.drawable.bg_appointment_xgbtn);
        }
        btn_quhao.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, MaterialRecorderActivity.class);

            intent.putExtra(ContentCode.WHOLE_RECORDER, mDataList.get(position));
            intent.putExtra(ContentCode.MATTERS_SERTAL_NUMBER, nAffairRecord.onlynumber);
            intent.putExtra(ContentCode.MATTERS_NAME, nAffairRecord.company_name);
            intent.putExtra(ContentCode.MATTER_STATE, nAffairRecord.liu_name);
            mContext.startActivity(intent);
        });
        return view;
    }


}
