package com.shyms.farendating.home.guide.details.details_list.recyclerview;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.shyms.faren.R;
import com.shyms.farendating.utils.LogcatUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hokas.base.BaseListAdapter;
import me.hokas.utils.ImageUtils;

/**
 * Created by Weimin on 4/12/2016.
 */
public class MaterialAdapter extends BaseListAdapter<String> {


    private DownloadManager downManager;

    public MaterialAdapter(Activity activity) {
        super(activity);
        downManager = (DownloadManager) mActivity.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = mLayoutInflater.inflate(R.layout.item_sample_list, viewGroup, false);
        TextView mPosText = (TextView) view.findViewById(R.id.tv_item_pos);
        TextView mContent = (TextView) view.findViewById(R.id.tv_item_content);
        TextView mDescription = (TextView) view.findViewById(R.id.tv_item_description);
        TextView mNum = (TextView) view.findViewById(R.id.tv_material_num);
        Button mEmptyLoad = (Button) view.findViewById(R.id.btn_empty_download);
        Button mSampleLoad = (Button) view.findViewById(R.id.btn_sample_look);
        Button mFanbenLoad = (Button) view.findViewById(R.id.fanben);
        String[] data = mDataList.get(position).split("\\|");

        if ((mDataList.get(position).contains("http://"))) {

            for (int i = 0; i < data.length; i++) {
                LogcatUtil.d("downloadsample", data[i]);
                if ((data[i].startsWith("http://"))) {
                    if (data[i].contains("Sample")) {
                        mSampleLoad.setVisibility(View.VISIBLE);
                    } else if (data[i].contains("Empty") || data[i].contains("blank")) {
                        mEmptyLoad.setVisibility(View.VISIBLE);
                    } else if (data[i].contains("fanben")) {
                        mFanbenLoad.setVisibility(View.VISIBLE);
                    }
                }
            }
            mSampleLoad.setOnClickListener(view1 -> {
                int count = 0;
                for (int i = 0; i < data.length; i++) {
                    int j = i;
                    if ((data[i].contains("Sample"))) {
                        ImageUtils.downloadFile(downManager, data[j], count > 0 ? data[0] + "样表-" + (count + 1) : data[0] + "样表");
                        count++;
                    }
                }
            });
            mEmptyLoad.setOnClickListener(view1 -> {
                int count = 0;
                for (int i = 0; i < data.length; i++) {
                    int j = i;
                    if (data[i].contains("Empty") || data[i].contains("blank")) {
                        ImageUtils.downloadFile(downManager, data[j], count > 0 ? data[0] + "空表-" + (count + 1) : data[0] + "空表");
                        count++;
                    }
                }
            });
            mFanbenLoad.setOnClickListener(view1 -> {
                int count = 0;
                for (int i = 0; i < data.length; i++) {
                    int j = i;
                    if (data[i].contains("fanben")) {
                        ImageUtils.downloadFile(downManager, data[j], count > 0 ? data[0] + "范本-" + (count + 1) : data[0] + "范本");
                        count++;
                    }
                }
            });
        }
        mPosText.setText(position + 1 + ":");
        mContent.setText(data[0]);
        mDescription.setText(data[1]);
        mNum.setText(data[2]);
        return view;
    }

    class ViewHolder {
        @Bind(R.id.tv_item_pos)
        TextView mPosText;
        @Bind(R.id.tv_item_content)
        TextView mContent;
        @Bind(R.id.tv_item_description)
        TextView mDescription;
        @Bind(R.id.tv_material_num)
        TextView mNum;
        @Bind(R.id.btn_empty_download)
        Button mEmptyLoad;

        ViewHolder(View parent) {
            ButterKnife.bind(this, parent);
        }
    }

}
