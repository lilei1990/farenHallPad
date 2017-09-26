package com.shyms.farendating.home.my_handle_affairs.recyclerview;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shyms.faren.R;
import com.shyms.farendating.utils.MyRecyclerViewItemClickListener;


public class MyViewHolder extends ViewHolder implements OnClickListener,OnLongClickListener {

	private MyRecyclerViewItemClickListener mListener;
	public TextView tv_bian_hao;
	public TextView tv_name;
	public LinearLayout ll_a;
	public TextView tv_name_geti;
	public LinearLayout rl_c;
	public TextView xiang;
	public TextView tv_bu_men;
	public TextView tv_time;
	public RelativeLayout ll_b;
	public ImageView imageView;
	public Button btn_xiugai;
	public Button btn_quhao;
	public MyViewHolder(View itemView ,MyRecyclerViewItemClickListener listener) {
		super(itemView);
		tv_bian_hao = (TextView) itemView.findViewById(R.id.tv_bian_hao);
		tv_name = (TextView) itemView.findViewById(R.id.tv_name);
		ll_a = (LinearLayout) itemView.findViewById(R.id.ll_a);
		tv_name_geti = (TextView) itemView.findViewById(R.id.tv_name_geti);
		rl_c = (LinearLayout) itemView.findViewById(R.id.rl_c);
		xiang = (TextView) itemView.findViewById(R.id.xiang);
		tv_bu_men = (TextView) itemView.findViewById(R.id.tv_bu_men);
		tv_time = (TextView) itemView.findViewById(R.id.tv_time);
		ll_b = (RelativeLayout) itemView.findViewById(R.id.ll_b);
		imageView = (ImageView) itemView.findViewById(R.id.imageView);
		btn_xiugai = (Button) itemView.findViewById(R.id.btn_xiugai);
		btn_quhao = (Button) itemView.findViewById(R.id.btn_quhao);
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
