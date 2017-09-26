package com.shyms.farendating.home.user_info;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.shyms.faren.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hokas.base.BaseFragment;

public class UserInfoFragment extends BaseFragment implements AdapterView.OnItemClickListener {


    @Bind(R.id.list_view)
    ListView listView;
    private UserInfoView view;

    public UserInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    @Override
    public void initData() {
        this.view = (UserInfoView) getActivity();
        String[] item = {"我的信息", "我的证照", "联系人", "账户安全","二维码","修改密码","绑定邮箱"};
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.settings_list_content, item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.view.setTitle(parent.getAdapter().getItem(position).toString());
        switch (position) {
            case 0:
                this.view.switchMyInfo();
                break;
            case 1:
                this.view.switchMyLicense();
                break;
            case 2:
                this.view.switchContactPerson();
                break;
            case 3:
                this.view.switchAccountSecurity();
                break;
            case 4:
                this.view.switchErWeiMa();
                break;
            case 5:
                this.view.switchReset();
                break;
            case 6:
                this.view.switchBindingEmail();
                break;
        }
    }
}
