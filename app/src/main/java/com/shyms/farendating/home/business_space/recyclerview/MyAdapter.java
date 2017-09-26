package com.shyms.farendating.home.business_space.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shyms.faren.R;
import com.shyms.farendating.home.business_space.model.InformationList;
import com.shyms.farendating.utils.MyRecyclerViewItemClickListener;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private MyRecyclerViewItemClickListener mListener;
    private List<InformationList.DataEntity> dataEntityList;
    private String[] colors ={"#fffff","#66ff55"};

    public MyAdapter(List<InformationList.DataEntity> dataEntityList) {
        this.dataEntityList = dataEntityList;
    }

    @Override
    public int getItemCount() {
        return dataEntityList != null ? dataEntityList.size() : 0;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvTitle.setText(dataEntityList.get(position).getTitle());
        holder.tvPublishTime.setText(dataEntityList.get(position).getPublishTime());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_information, parent, false);
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
