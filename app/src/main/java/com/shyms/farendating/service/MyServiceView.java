package com.shyms.farendating.service;

import com.shyms.farendating.service.model.UserNoticeInfo;

public interface MyServiceView {
    void onCompleted(UserNoticeInfo userNoticeInfo);
    void showMsg(String msg);
}
