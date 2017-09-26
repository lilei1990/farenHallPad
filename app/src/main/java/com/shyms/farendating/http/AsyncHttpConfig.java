package com.shyms.farendating.http;


/**
 * URL类
 *
 * @author user
 */
public class AsyncHttpConfig {

//    public static final String URL_GET_UPDATES = "http://114.55.65.20/fryms/api/get_app.php";//自动更新   测试
//    public static final String URL = "http://114.55.65.20";  //大厅测试
    public static final String URL_GET_UPDATES = "http://19.134.148.17/fryms/api/get_app.php";//自动更新   正式
    public static final String URL = "http://19.134.148.17";     //正式
//        public static final String URL = "http://" + PreferencesUtil.getServiceAddress();

    public static final String URL_BASE = URL + "/fryms/api/";
    //    public static final String URL_BASE25 = "http://19.134.148.25/fryms/api/";
    public static final String URL_JHY = URL + "/result/";
    //public static final String URL_BASE_WAI = "http://115.28.12.120/fryms/api/";


    /**
     * 用户消息通知
     */
    public static final String URL_GET_USER_NOTICE = URL_JHY + "get_notify_status.php";

    /**
     * 用户消息确定通知
     */
    public static final String URL_GET_USER_ENTER = URL_JHY + "get_notify_status.php";

    /**
     * 用户消息通知列表
     */
    public static final String URL_GET_USER_NOTICE_LIST = URL_JHY + "get_notify_status.php";

    /**
     * 用户评价
     */
    public static final String URL_USER_EVALUATE = URL + "/cailiao/mobile/banshi/evaluation";

    /**
     * 事项历史材料
     */
    public static final String URL_MATTERS_MATERIAL = URL_JHY + "handle_affairs/user_handle_affairs.php";

    /**
     * 上传图片
     */

    public static final String URL_FILE = URL + "/cailiao/api/fileapi/uploadfile";

    /**
     * 获取图片
     */
    public static final String URL_HUOQU = URL_JHY + "get_file_api.php";


    public static final String URL_GET_QR_GENERATE = URL_BASE + "creat_QR_Code.php";//二维码生成

    public static final String URL_GET_SEND_MEIL = URL_BASE + "send_verify_code_E.php";//发送邮箱验证码
    public static final String URL_BINDING_EMAIL = URL_BASE + "register_Email.php";//绑定邮箱

    /**
     * 删除图片
     */
    public static final String URL_DELETE_FILE = URL_JHY + "delete_file.php";

    public static final int WITH_50011 = 50011; // APP自动更新
    public static final int WITH_50000 = 50000; // 新登录接口
    public static final int WITH_60005 = 60005; // 删除图片
    public static final int WITH_60008 = 60008; // 绑定邮箱
    public static final int WITH_60006 = 60006; // 用户评价
    public static final int WITH_60004 = 60004; // 事项历史材料
    public static final int WITH_60002 = 60002; // 用户消息确定通知
    public static final int WITH_60001 = 60001; // 用户消息通知
    public static final int WITH_60003 = 60003; // 用户消息通知列表

    public static final int WITH_30008 = 30008; // 上传图片
    public static final int WITH_30010 = 30010; // 获取类别图片


    public static final int WITH_50009 = 50009; // 生成二维码
    public static final int WITH_50004 = 50004;//邮箱验证码
}
