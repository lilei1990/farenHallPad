package com.shyms.farendating.home.guide.details.details_list.recyclerview;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.shyms.faren.R;
import com.shyms.farendating.utils.MyRecyclerViewItemClickListener;


public class MyViewHolder extends ViewHolder implements OnClickListener,OnLongClickListener {

	private MyRecyclerViewItemClickListener mListener;
	public TextView tv_matters_name,tv_num,tv_type,tv_style,tv_kong;
	public Button btn_style,btn_kong;

	public MyViewHolder(View itemView ,MyRecyclerViewItemClickListener listener) {
		super(itemView);
		tv_matters_name = (TextView) itemView.findViewById(R.id.tv_matters_name);
		tv_num = (TextView) itemView.findViewById(R.id.tv_num);
		tv_type = (TextView) itemView.findViewById(R.id.tv_type);
		tv_style = (TextView) itemView.findViewById(R.id.tv_style);
		tv_kong = (TextView) itemView.findViewById(R.id.tv_kong);
		btn_style = (Button) itemView.findViewById(R.id.btn_style);
		btn_kong = (Button) itemView.findViewById(R.id.btn_kong);
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
