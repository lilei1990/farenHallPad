package com.shyms.farendating.home.guide.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shyms.faren.R;
import com.shyms.farendating.home.guide.model.AffairsInfo;
import com.shyms.farendating.utils.MyRecyclerViewItemClickListener;

import java.util.List;


public class MyGuideAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private MyRecyclerViewItemClickListener mListener;
    private List<AffairsInfo.DataEntity> list;

    public MyGuideAdapter(List<AffairsInfo.DataEntity> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_bian_hao.setText( list.get(position).getNumber()==null?"暂无事项编号":""+list.get(position).getNumber());
        holder.tv_bu_men.setText(list.get(position).getDepartmentname());
        holder.tv_name.setText(list.get(position).getName()==null?"  暂无事项名称":" "+list.get(position).getName());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_guide, parent, false);
        return new MyViewHolder(itemView, mListener);
    }

    /**
     * 设置Item点击监听
     */
    public void setOnItemClickListener(MyRecyclerViewItemClickListener listener) {
        this.mListener = listener;
    }

    /**
     * 设置Item长安点击监听
     */
    public void setOnItemLongClickListener(MyRecyclerViewItemClickListener listener) {
        this.mListener = listener;
    }
}
