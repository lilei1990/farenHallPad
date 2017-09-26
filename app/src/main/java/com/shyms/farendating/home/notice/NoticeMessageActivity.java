package com.shyms.farendating.home.notice;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.shyms.faren.R;
import com.shyms.farendating.http.AsyncHttpCallBack;
import com.shyms.farendating.http.AsyncHttpConfig;
import com.shyms.farendating.http.AsyncHttpRequest;
import com.shyms.farendating.service.model.UserNoticeInfo;
import com.shyms.farendating.utils.UserManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.hokas.base.BaseActivity;
import me.hokas.base.BaseObject;

public class NoticeMessageActivity extends BaseActivity implements AsyncHttpCallBack {

    @Bind(R.id.listView)
    ListView listView;
    private MessageAdapter adapter;
    private List<UserNoticeInfo.DataEntity> dataEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_message);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initData() {
        tvTitle.setText("我的通知");
        dataEntities = new ArrayList<>();
        adapter = new MessageAdapter(dataEntities);
        listView.setAdapter(adapter);
        AsyncHttpRequest.getUserNoticeList(UserManager.getInstance().getLastUserInfo().getData().getUserId(), "100", this);
    }

    @Override
    public void onSuccess(int what, int statusCode, JSONObject response) {
        final BaseObject baseObject = JSON.parseObject(response.toString(), BaseObject.class);
        String code = baseObject.getCode();
        if (AsyncHttpConfig.WITH_60003 == what) {
            if (!"0".equals(baseObject.getData())) {
                UserNoticeInfo userNoticeInfo = JSON.parseObject(response.toString(), UserNoticeInfo.class);
                dataEntities.addAll(userNoticeInfo.getData());
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onFailure(int what, int statusCode, JSONObject errorResponse) {

    }

    class MessageAdapter extends BaseAdapter {
        private List<UserNoticeInfo.DataEntity> dataEntityList;

        public MessageAdapter(List<UserNoticeInfo.DataEntity> dataEntityList) {
            this.dataEntityList = dataEntityList;
        }

        @Override
        public int getCount() {
            return dataEntityList != null ? dataEntityList.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return dataEntityList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Cache cache;
            if (convertView == null) {
                convertView = LayoutInflater.from(NoticeMessageActivity.this).inflate(R.layout.list_item_message, null);
                cache = new Cache();
                cache.title = (TextView) convertView.findViewById(R.id.tv_title);
                cache.tongguo = (TextView) convertView.findViewById(R.id.tv_tongguo);
                cache.jindu = (TextView) convertView.findViewById(R.id.tv_jindu);
                cache.nextjindu = (TextView) convertView.findViewById(R.id.tv_next_jindu);
                cache.liyou = (TextView) convertView.findViewById(R.id.tv_liyou);
                cache.window = (TextView) convertView.findViewById(R.id.tv_windows);
                cache.tv_sfcz = (TextView) convertView.findViewById(R.id.tv_sfcz);
                cache.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
                convertView.setTag(cache);
            } else {
                cache = (Cache) convertView.getTag();
            }
            cache.title.setText(dataEntityList.get(position).getMatterName());
            if ("pass".equals(dataEntityList.get(position).getCourseResult()))
                cache.tongguo.setText("通过");
            else
                cache.tongguo.setText("不通过");

            if ("yes".equals(dataEntityList.get(position).getIsPrint()))
                cache.tv_sfcz.setText("是");
            else
                cache.tv_sfcz.setText("否");
            cache.jindu.setText(dataEntityList.get(position).getCourseName());
            cache.nextjindu.setText(dataEntityList.get(position).getNextCourse());
            cache.window.setText(dataEntityList.get(position).getWindowNumber());
            cache.tv_number.setText(dataEntityList.get(position).getFlowNumber());
            if (!TextUtils.isEmpty(dataEntityList.get(position).getMopassReason())) {
                cache.liyou.setText(dataEntityList.get(position).getMopassReason());
            }
            return convertView;
        }

        class Cache {
            TextView title, tongguo, jindu, nextjindu, liyou, window, tv_sfcz,tv_number;
        }
    }
}
