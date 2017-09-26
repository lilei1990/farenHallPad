package com.shyms.farendating.http.api;

import com.shyms.farendating.home.business_space.model.InformationList;
import com.shyms.farendating.home.guide.model.AffairsInfo;
import com.shyms.farendating.home.guide.model.MattersDetailsInfo;
import com.shyms.farendating.home.lien_up.model.NUserNumber;
import com.shyms.farendating.home.lien_up.model.QueryLineUpInfo;
import com.shyms.farendating.home.lien_up.model.UserGiveUp;
import com.shyms.farendating.home.lien_up.model.UserQueryLineUp;
import com.shyms.farendating.home.lien_up.model.UserTakeNumber;
import com.shyms.farendating.home.lien_up.model.WindowsLoginInfo;
import com.shyms.farendating.home.lien_up.model.WindowsSystemInfo;
import com.shyms.farendating.home.my_handle_affairs.material.model.NDepartment;
import com.shyms.farendating.home.my_handle_affairs.material.model.NMatterMaterial;
import com.shyms.farendating.home.my_handle_affairs.model.HandleAffairsModel;
import com.shyms.farendating.model.BaseObject;
import com.shyms.farendating.model.NBriefIntroduction;
import com.shyms.farendating.model.NGetSingleImage;
import com.shyms.farendating.model.NMultipleImage;
import com.shyms.farendating.model.NObject;
import com.shyms.farendating.model.NObjectList;
import com.shyms.farendating.model.NUrl;
import com.shyms.farendating.model.PadObject;
import com.shyms.farendating.model.UserInfo;
import com.shyms.farendating.service.model.UserNoticeInfo;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hks on 2016/6/23.
 */
public interface APIClient {

    String URL_BASE = "/fryms/api/";
    String URL_AFFAIR = "/result/";
    String URL_LIEN_UP = "/bs/queue/";

    /**
     * 排队办事
     */
//创业空间
    @GET(URL_BASE + "get_information_list.php")
    Observable<InformationList> getInformationList(@Query("page_no") int page_no, @Query("page_size") int page_size);

    //窗口登录
    @GET(URL_LIEN_UP + "site_login.php")
    Observable<WindowsLoginInfo> getWindowSystem(@Query("username") String username, @Query("password") String password);

    //查询受理点对应窗口类型
    @GET(URL_LIEN_UP + "get_system.php?log_verify_code=v8Em54eWXYPBE4A")
    Observable<WindowsSystemInfo> getSystem(@Query("siteid") String siteId);
    //查询受理点对应窗口类型

    //预约取号
    @GET("/bs/subscribe/get_subscribe.php?log_verify_code=v8Em54eWXYPBE4A")
    Observable<UserTakeNumber> getUserAppoint(@Query("system") String system,
                                              @Query("mobile") String mobile,
                                              @Query("uid") String uid);

    @GET("/bs/subscribe/get_subscribe.php")
    Observable<UserTakeNumber> getUserAppoint(@Query("log_verify_code") String log_verify_code,
                                              @Query("system") String system,
                                              @Query("mobile") String mobile,
                                              @Query("uid") String uid);


    //查询当前排队人数（窗口、pad）onFailure
    @GET(URL_LIEN_UP + "check_queue.php?log_verify_code=v8Em54eWXYPBE4A")
    Observable<QueryLineUpInfo> getCheckQueue(@Query("system") String system);

    //用户取号（pad）
    @GET(URL_LIEN_UP + "user_get.php?log_verify_code=v8Em54eWXYPBE4A")
    Observable<UserTakeNumber> getUserGet(@Query("mobile") String mobile, @Query("system") String system);

    //放弃号码（窗口、pad）
    @GET(URL_LIEN_UP + "giveup.php?log_verify_code=v8Em54eWXYPBE4A")
    Observable<UserGiveUp> getGiveUp(@Query("mobile") String mobile, @Query("system") String system);

    //用户查询排队状态（pad）
    @GET(URL_LIEN_UP + "user_status.php?log_verify_code=v8Em54eWXYPBE4A")
    Observable<UserQueryLineUp> getUserStatus(@Query("queue_id") String queue_id);

    @GET("/bs/queue/user_status.php")
    Observable<NObjectList<NUserNumber>> queryNewUserStatus(@Query("mobile") String mobile,
                                                            @Query("log_verify_code") String log_verify_code);

    //获取所有部门
    @GET("/cailiao/mobile/banshi/getdepartment")
    Observable<NObjectList<NDepartment>> getDicDepartment();

    //办事指南列表
    @GET("cailiao/mobile/banshi/getdepartmentmatter/code/{code}")
    Observable<AffairsInfo> getAffairGuideList(@Query("code") String code);


    @GET("/cailiao/mobile/banshi/getmatter/{number}/")
    Observable<MattersDetailsInfo> getAffairGuideDetail(@Query("number") String number);

    //办事指南列表条目详情  材料列表
//    @GET(URL_BASE + "get_mt_sample.php")
//    Observable<MattersList> getMattersList(@Query("MtId") String MtId);
    @GET("/fryms/api/get_mt_sample.php")
    Observable<NObject<List<String>>> getMattersList(@Query("MtId") String MtId);

    @GET("/fryms/api/get_mt_sample.php")
    Observable<NObject<List<String>>> getMtSample(@Query("MtId") String MtId);

    //搜索 办事指南列表
    @GET("/cailiao/mobile/banshi/matterssearch")
    Observable<AffairsInfo> getSearchEnd(@Query("title") String title);

    //身份证短信验证码
    @GET(URL_BASE + "send_verify_code_B.php")
    Observable<BaseObject> getRegisterCodes(@Query("mobile") String mobile);

    //绑定身份证   注册
    @GET(URL_BASE + "register_Id.php")
    Observable<UserInfo> getSf(@Query("verify_code") String verify_code, @Query("id_card") String id_card,
                               @Query("mobile") String mobile, @Query("mobile_code") String mobile_code);

    //二维码登录
    @GET(URL_BASE + "login_QR.php")
    Observable<UserInfo> getQRLogin(@Query("username") String username, @Query("verify_code") String verify_code);

    //登录
    @FormUrlEncoded
    @POST("/fryms/api/login.php")
    Observable<UserInfo> login(@Field("username") String username, @Field("password") String password);


    //自动登陆
    @GET(URL_BASE + "login_code.php")
    Observable<UserInfo> login(@Query("log_verify_code") String log_verify_code);

    //手机验证码 发送
    @GET(URL_BASE + "user_verify_code.php")
    Observable<BaseObject> getPhoneCode(@Query("username") String username);

    //手机验证码登录
    @GET(URL_BASE + "login_quick.php")
    Observable<UserInfo> getCodeLogin(@Query("mobile") String mobile, @Query("verify_code") String verify_code);

    //发送邮箱验证码
    @GET(URL_BASE + "send_verify_code_E.php")
    Observable<BaseObject> getEmailCode(@Query("Email") String Email, @Query("request") String request);

    //邮箱登录
    @GET(URL_BASE + "login_quick_E.php")
    Observable<UserInfo> getEmailLogin(@Query("Email") String Email, @Query("verify_code") String verify_code);

    //用户注册
    @GET(URL_BASE + "register.php")
    Observable<UserInfo> userRegister(@Query("username") String username, @Query("password") String password
            , @Query("name") String name, @Query("mobile") String mobile
            , @Query("verify_code") String verify_code, @Query("login") int login);

    //用户注册短信验证码
    @GET(URL_BASE + "send_verify_code.php")
    Observable<BaseObject> getRegisterCode(@Query("mobile") String mobile);


    //重置密码
    @GET(URL_BASE + "reset_password.php")
    Observable<UserInfo> getResetPassword(@Query("username") String username, @Query("password") String password
            , @Query("verify_code") String verify_code);

    //用户登入提交数据
    @GET(URL_AFFAIR + "user_login.php")
    Observable<BaseObject> setUserInfo(@Query("uid") String uid, @Query("username") String username);


    //我的办事列表（进度查询）
    @GET(URL_AFFAIR + "get_notify_status.php")
    Observable<UserNoticeInfo> getUserNotice(@Query("uid") String uid);

    //用户求助（pad）
    @GET(URL_AFFAIR + "handle_affairs/save_seekhelp.php")
    Observable<NObject<String>> askForHelp(@Query("uid") String uid);

    /**
     * 用户消息确定通知
     */
    @GET(URL_AFFAIR + "get_notify_status.php")
    Observable<UserNoticeInfo> getUserEnter(@Query("uid") String uid,
                                            @Query("mtid") String mtid);


    /**
     * 用户消息通知
     * get_notify_status.php
     */
//        @GET(URL_AFFAIR+"get_notify_status.php")
//        Observable<UserNoticeInfo> getUserNotice(@Query("uid") String uid);
//

    /**
     * 评价
     * mobile/banshi/evaluation
     */
    @GET(URL_AFFAIR + "mobile/banshi/evaluation")
    Observable<NObject<String>> postUserEvaluate(@Query("flowNumber") String flowNumber,
                                                 @Query("Evaluation") String Evaluation,
                                                 @Query("node") String node);

    /**
     * 用户消息通知列表
     */
    @GET(URL_AFFAIR + "get_notify_status.php")
    Observable<UserNoticeInfo> getUserNoticeList(@Query("uid") String uid,
                                                 @Query("list") String list);

    @POST("/fryms/api/login_C.php")
    Observable<UserInfo> login();

    //我的办事列表（进度查询）
    //获取token
    @GET("/webfryms/api/get_token.php")
    Observable<NObject<String>> getToken(@Query("username") String username,
                                         @Query("secretkey") String secretkey);

    @GET("/webcailiao/api/matter/getBanshiList/{uid}/{log_verify_code}/{timestamp}/{token}/{sign}")
    Observable<HandleAffairsModel> affairRecord(@Query("uid") String uid,
                                                @Query("log_verify_code") String log_verify_code,
                                                @Query("timestamp") String timestamp,
                                                @Query("token") String token,
                                                @Query("sign") String sign);

    //办事材料
    @GET("/webcailiao/api/matter/getCailiaoList/{only_number}/{log_verify_code}/{timestamp}/{token}/{sign}")
    Observable<NObjectList<NMatterMaterial>> affairMaterial(@Query("only_number") String only_number,
                                                            @Query("log_verify_code") String log_verify_code,
                                                            @Query("timestamp") String timestamp,
                                                            @Query("token") String token,
                                                            @Query("sign") String sign);

    @GET("/webcailiao/api/fileapi/getimage/{id}/{log_verify_code}/{timestamp}/{token}/{sign}")
    Observable<NObject<NMultipleImage>> getShrinkImage(@Query("id") String id,
                                                       @Query("log_verify_code") String log_verify_code,
                                                       @Query("timestamp") String timestamp,
                                                       @Query("token") String token,
                                                       @Query("sign") String sign);

    //上传材料
    @FormUrlEncoded
    @POST("/webcailiao/api/fileapi/uploadfile/{Type}/{Id}/{GuId}/{File}/{timestamp}/{token}/{sign}")
    Observable<NObject<String>> uploadImage(@Field("Type") String Type,
                                            @Field("Id") String Id,
                                            @Field("GuId") String GuId,
                                            @Field("File") String File,
                                            @Query("timestamp") String timestamp,
                                            @Query("token") String token,
                                            @Query("sign") String sign);

    //删除图片材料一张
    @GET("/webcailiao/api/fileapi/deletefile/{id}/{guid}/{timestamp}/{token}/{sign}")
    Observable<NObject<String>> deleteOne(@Query("id") String id,
                                          @Query("guid") String guid,
                                          @Query("timestamp") String timestamp,
                                          @Query("token") String token,
                                          @Query("sign") String sign);


    //删除多张图片材料
    @GET("/webcailiao/api/fileapi/deletefiles/{id}/{timestamp}/{token}/{sign}")
    Observable<NObject<String>> deleteAll(@Query("id") String id,
                                          @Query("timestamp") String timestamp,
                                          @Query("token") String token,
                                          @Query("sign") String sign);

    //获取单张图片
    @GET("/webcailiao/api/fileapi/getfile/{id}/{guid}/{timestamp}/{token}/{sign}")
    Observable<NObject<NGetSingleImage>> getSingleImg(@Query("id") String id,
                                                      @Query("guid") String guid,
                                                      @Query("timestamp") String timestamp,
                                                      @Query("token") String token,
                                                      @Query("sign") String sign);


    //大厅介绍
    @GET(NetRequest.WEB_URL + "/Api/MatterManage/GetServiceCenterInfo?id=0")
    Observable<PadObject<NBriefIntroduction>> getHallIntroduction();

    //船业空间
    @GET(NetRequest.WEB_URL + "/Api/MatterManage/GetServiceCenterUrl?flag=true")
    Observable<PadObject<NUrl>> getBusinessSpace();

    //广告
    @GET(NetRequest.WEB_URL + "/Api/MatterManage/GetServiceCenterUrl?flag=false")
    Observable<PadObject<NUrl>> getAdvertisement();

    /**
     * APP自动更新kylpeng
     */
    @GET("/fryms/api/get_app.php")
    Observable<UserNoticeInfo> getUpdate(@Query("ver") String ver);
}
