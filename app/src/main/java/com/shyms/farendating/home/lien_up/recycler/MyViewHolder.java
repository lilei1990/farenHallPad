package com.shyms.farendating.home.lien_up.recycler;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.shyms.faren.R;
import com.shyms.farendating.utils.MyRecyclerViewItemClickListener;


public class MyViewHolder extends ViewHolder implements OnClickListener,OnLongClickListener {

	private MyRecyclerViewItemClickListener mListener;
	public TextView tvTitle;
	public ImageView imageView;
	public MyViewHolder(View itemView ,MyRecyclerViewItemClickListener listener) {
		super(itemView);
		tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
		imageView = (ImageView) itemView.findViewById(R.id.ivIcon);
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
