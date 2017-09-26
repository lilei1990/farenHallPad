package com.shyms.farendating.utils;

import android.view.View;


public interface MyRecyclerViewItemClickListener {
	/**
	 * 点击监听
	 */
	void onItemClick(View view, int position);

	/**
	 * 长按监听
	 */
	void onItemLongClick(View view, int position);


}
