package com.shyms.farendating.home.business_space.details;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shyms.faren.R;
import com.shyms.farendating.home.business_space.model.InformationList;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hokas.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessSpaceDetailsFragment extends BaseFragment {


    @Bind(R.id.ivDetailsBrief)
    ImageView ivDetailsBrief;
    @Bind(R.id.tvDetailsTitle)
    TextView tvDetailsTitle;
    @Bind(R.id.tvDetailsTime)
    TextView tvDetailsTime;
    @Bind(R.id.tvDetailsContent)
    TextView tvDetailsContent;

    public BusinessSpaceDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_business_space_details, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        InformationList.DataEntity dataEntity = (InformationList.DataEntity) bundle.getSerializable("information");
        if(dataEntity!=null) {
            tvDetailsTitle.setText(dataEntity.getTitle());
            tvDetailsTime.setText(dataEntity.getPublishTime());
            tvDetailsContent.setText(dataEntity.getContent()+"");
            if (!"".equals(dataEntity.getBrief())) {
                Glide.with(this).load(dataEntity.getBrief()).error(R.mipmap.ic_launcher).into(ivDetailsBrief);
                ivDetailsBrief.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
