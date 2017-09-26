package com.shyms.farendating.home.guide.details.details_list.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shyms.faren.R;
import com.shyms.farendating.home.guide.details.details_list.MattersListActivity;
import com.shyms.farendating.home.guide.details.details_list.style_table.StyleTableActivity;
import com.shyms.farendating.home.guide.model.AffairsInfo;
import com.shyms.farendating.home.guide.model.MattersList;
import com.shyms.farendating.utils.MyRecyclerViewItemClickListener;

import java.util.List;

import me.hokas.utils.ToastUtil;
import okhttp3.Cache;


public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private MyRecyclerViewItemClickListener mListener;
    private List<String> list;
    private Context context;

    public MyAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final String[] strings = list.get(position).split("\\|");
        holder.tv_matters_name.setText(strings[0]);
        holder.tv_num.setText(strings[2]);
        holder.tv_type.setText(strings[1]);
        if("0".equals(strings[3])){
            holder.tv_style.setVisibility(View.VISIBLE);
            holder.btn_style.setVisibility(View.GONE);
        }else{
            holder.tv_style.setVisibility(View.GONE);
            holder.btn_style.setVisibility(View.VISIBLE);
            holder.btn_style.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(strings[3].contains("doc")) {
                        Toast.makeText(context,"当前不支持打开doc格式的文件",Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(context, StyleTableActivity.class);
                        intent.putExtra("wen", strings[0]);
                        intent.putExtra("url", strings[3]);
                        context.startActivity(intent);
                    }
                }
            });
        }
        if("0".equals(strings[4])){
            holder.tv_kong.setVisibility(View.VISIBLE);
            holder.btn_kong.setVisibility(View.GONE);
        }else{
            holder.tv_kong.setVisibility(View.GONE);
            holder.btn_kong.setVisibility(View.VISIBLE);
            holder.btn_kong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(strings[4].contains("doc")) {
                        Toast.makeText(context,"当前不支持打开doc格式的文件",Toast.LENGTH_SHORT).show();
//                    }else {
                        Intent intent = new Intent(context, StyleTableActivity.class);
                        intent.putExtra("wen", strings[0]);
                        intent.putExtra("url", strings[4]);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_matters, parent, false);
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
