package com.shyms.farendating.home.lien_up;

import com.shyms.farendating.home.lien_up.model.NUserNumber;
import com.shyms.farendating.home.lien_up.model.QueryLineUpInfo;
import com.shyms.farendating.home.lien_up.model.UserQueryLineUp;
import com.shyms.farendating.home.lien_up.model.WindowsSystemInfo;

public interface LineUpView {
    void onCompleted(WindowsSystemInfo windowsSystemInfo);

    void onCompleted(String systemName, QueryLineUpInfo queryLineUpInfo);

    void showMsg(String msg);

    void onCompleted(String serviceName, String windowsName, String windowsNumber, String before, String number);

    void onCompletedIsLogin();

    void showLineUpInfo(boolean flag);
//    android:text="综合服务窗口\n（注册登记类）"
    void onRegister(NUserNumber userQueryLineUp, String queue_id);

    void onEnd(NUserNumber nUserNumber);


//    android:text="综合服务窗口\n(经营许可类)"
    void onBusinessLicense(NUserNumber userQueryLineUp, String queue_id);
//    android:text="综合服务窗口\n(投资建设类)"
    void onInvestConstruct(NUserNumber userQueryLineUp, String queue_id);


//    android:text="工商信息查询"
    void onBusinessInformationQuery(NUserNumber userQueryLineUp, String queue_id);
//    android:text="缴费出证"
    void onFeeCredentials(NUserNumber userQueryLineUp, String queue_id);

    void onRepeatGetNumber(String system);

    void vibratorOne();
}
