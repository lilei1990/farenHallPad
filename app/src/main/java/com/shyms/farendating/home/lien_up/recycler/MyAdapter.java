package com.shyms.farendating.home.lien_up.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shyms.faren.R;
import com.shyms.farendating.home.lien_up.model.WindowsSystemInfo;
import com.shyms.farendating.utils.MyRecyclerViewItemClickListener;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private MyRecyclerViewItemClickListener mListener;
    private List<WindowsSystemInfo.DataEntity> dataEntityList;
    private int[] icons = {R.mipmap.menu01, R.mipmap.menu02, R.mipmap.menu03, R.mipmap.menu04, R.mipmap.menu05};

    public MyAdapter(List<WindowsSystemInfo.DataEntity> dataEntityList) {
        this.dataEntityList = dataEntityList;
    }

    @Override
    public int getItemCount() {
        return dataEntityList != null ? dataEntityList.size() : 0;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvTitle.setText(dataEntityList.get(position).getName());
        holder.imageView.setBackgroundResource(icons[position]);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_line_up_service_point, parent, false);
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
