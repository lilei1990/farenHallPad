package com.shyms.farendating.home.user_info;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.shyms.faren.R;

import me.hokas.base.BaseFragment;

public class AccountSecurityFragment extends BaseFragment {


    public AccountSecurityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_security, container, false);
    }

}
