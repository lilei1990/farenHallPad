package com.shyms.farendating.home.user_info;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.shyms.faren.R;
import com.shyms.farendating.home.user_info.model.LicenseDemo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hokas.base.BaseFragment;

public class MyLicenseFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    private MyAdapterList adapter;
    private List<LicenseDemo> licenseDemoList;
    public MyLicenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_license, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    @Override
    public void initData() {
        licenseDemoList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            LicenseDemo licenseDemo = new LicenseDemo();
            licenseDemo.setS1("营业执照" + i);
            licenseDemo.setS2("2015-09-02");
            licenseDemo.setS3("工商" + i);
            licenseDemo.setS4("456465465");
            licenseDemo.setS5("2015-10-22");
            licenseDemo.setS6("广东省电子证照中心" + i);
            licenseDemoList.add(licenseDemo);
        }
        adapter = new MyAdapterList(licenseDemoList);
        listView.setAdapter(adapter);
        swipeContainer.setOnRefreshListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        if (!swipeContainer.isRefreshing()) {
            swipeContainer.setRefreshing(true);
        }
        swipeContainer.setRefreshing(false);
        adapter.notifyDataSetChanged();
    }


    class MyAdapterList extends BaseAdapter {

        private List<LicenseDemo> licenseDemoList;

        public MyAdapterList(List<LicenseDemo> licenseDemoList) {
            this.licenseDemoList = licenseDemoList;
        }

        @Override
        public int getCount() {
            return licenseDemoList != null ? licenseDemoList.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return licenseDemoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_my_license, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_license_name.setText(licenseDemoList.get(position).getS1());
            viewHolder.tv_time.setText(licenseDemoList.get(position).getS2());
            viewHolder.tv_department.setText(licenseDemoList.get(position).getS3());
            viewHolder.tv_id_card_number.setText(licenseDemoList.get(position).getS4());
            viewHolder.tv_time_end.setText(licenseDemoList.get(position).getS5());
            viewHolder.tv_license_department.setText(licenseDemoList.get(position).getS6());

            return convertView;
        }

        class ViewHolder {
            private TextView tv_license_name;
            private TextView tv_time;
            private TextView tv_department;
            private TextView tv_id_card_number;
            private TextView tv_time_end;
            private TextView tv_license_department;

            ViewHolder(View view) {
                initViews(view);
            }

            private void initViews(View root) {
                tv_license_name = (TextView) root.findViewById(R.id.tv_license_name);
                tv_time = (TextView) root.findViewById(R.id.tv_time);
                tv_department = (TextView) root.findViewById(R.id.tv_department);
                tv_id_card_number = (TextView) root.findViewById(R.id.tv_id_card_number);
                tv_time_end = (TextView) root.findViewById(R.id.tv_time_end);
                tv_license_department = (TextView) root.findViewById(R.id.tv_license_department);
            }
        }
    }
}
