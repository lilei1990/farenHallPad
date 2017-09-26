package com.shyms.farendating.home.daoban;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shyms.faren.R;
import com.shyms.farendating.home.my_handle_affairs.material.MyHandleAffairsActivity;
import com.shyms.farendating.utils.UserManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hokas.base.BaseActivity;

public class DaoBanActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_next)
    Button tvNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dao_ban);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvContent.setText("导办");
        tvNext.setText("我的办事");
        tvNext.setVisibility(View.VISIBLE);
        tvNext.setOnClickListener(v -> {
            if (UserManager.getInstance().getLoginStatus()) {
                GotoActivity(MyHandleAffairsActivity.class);
            } else {
                showToast("您还未登录，请登录后再操作");
            }
        });
    }
}
