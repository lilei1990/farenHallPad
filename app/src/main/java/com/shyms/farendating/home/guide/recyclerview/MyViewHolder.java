package com.shyms.farendating.home.guide.recyclerview;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.TextView;

import com.shyms.faren.R;
import com.shyms.farendating.utils.MyRecyclerViewItemClickListener;


public class MyViewHolder extends ViewHolder implements OnClickListener,OnLongClickListener {

	private MyRecyclerViewItemClickListener mListener;
	public TextView tv_bian_hao;
	public TextView tv_name;
	public TextView tv_bu_men;

	public MyViewHolder(View itemView ,MyRecyclerViewItemClickListener listener) {
		super(itemView);
		tv_bian_hao = (TextView) itemView.findViewById(R.id.tv_bian_hao);
		tv_bu_men = (TextView) itemView.findViewById(R.id.tv_bu_men);
		tv_name = (TextView) itemView.findViewById(R.id.tv_name);
		this.mListener = listener;
		itemView.setOnClickListener(this);
		itemView.setOnLongClickListener(this);
	}


	/**
	 * 点击监听
	 */
	@Override
	public void onClick(View v) {
		if (mListener != null) {
			mListener.onItemClick(v, getPosition());
		}
	}

	/**
	 * 长按监听
	 */
	@Override
	public boolean onLongClick(View v) {
        if (mListener != null) {
            mListener.onItemLongClick(v, getPosition());
        }
		return true;
	}

}
