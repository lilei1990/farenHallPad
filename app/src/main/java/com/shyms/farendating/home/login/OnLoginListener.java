package com.shyms.farendating.home.login;

import com.shyms.farendating.model.BaseObject;
import com.shyms.farendating.model.UserInfo;

public interface OnLoginListener {
    void onSuccess(BaseObject baseObject);
    void onSuccess(UserInfo userInfo);

    void onFailed(String msg);
}
