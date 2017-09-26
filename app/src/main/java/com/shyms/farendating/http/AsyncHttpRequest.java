package com.shyms.farendating.http;

import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.shyms.farendating.http.api.NetRequest;
import com.shyms.farendating.utils.Util;


public class AsyncHttpRequest extends AsyncHttpConfig {

    /**
     * 用户消息确定通知
     */
    public static void getUserEnter(String userId, String mtid, AsyncHttpCallBack asyncHttpCallBack) {
        RequestParams params = new RequestParams();
        params.put("uid", userId);
        params.put("mtid", mtid);
        Log.d("notice","用户消息确定通知uid==="+userId);
        AsyncHttp.get(WITH_60002, AsyncHttpConfig.URL_GET_USER_ENTER, params, asyncHttpCallBack);
    }

    /**
     * 用户消息通知
     */
    public static void getUserNotice(String userId, AsyncHttpCallBack asyncHttpCallBack) {
        RequestParams params = new RequestParams();
        params.put("uid", userId);
        Log.d("notice","用户消息通知uid==="+userId);
        AsyncHttp.get(WITH_60001, AsyncHttpConfig.URL_GET_USER_NOTICE, params, asyncHttpCallBack);
    }

    /**
     * 评价
     */
    public static void postUserEvaluate(String flowNumber, String Evaluation,String node,AsyncHttpCallBack asyncHttpCallBack) {
        RequestParams params = new RequestParams();
        params.put("flowNumber", flowNumber);
        params.put("Evaluation", Evaluation);
        params.put("node", node);
        AsyncHttp.post(WITH_60006, AsyncHttpConfig.URL_USER_EVALUATE, params, asyncHttpCallBack);
    }
    /**
     * 用户消息通知列表
     */
    public static void getUserNoticeList(String userId, String list, AsyncHttpCallBack asyncHttpCallBack) {
        RequestParams params = new RequestParams();
        params.put("uid", userId);
        params.put("list", list);
        AsyncHttp.get(WITH_60003, AsyncHttpConfig.URL_GET_USER_NOTICE_LIST, params, asyncHttpCallBack);
    }

    /**
     * 事项历史材料
     */
    public static void getMattersMaterial(String only_number, AsyncHttpCallBack asyncHttpCallBack) {
        RequestParams params = new RequestParams();
        params.put("only_number", only_number);
        AsyncHttp.get(WITH_60004, AsyncHttpConfig.URL_MATTERS_MATERIAL, params, asyncHttpCallBack);
    }
    /**
     * 上传图片
     */
    public static void postFile(String id, String guid, String file, AsyncHttpCallBack asyncHttpCallBack) {
        RequestParams params = new RequestParams();
        params.put("Type", "jpg");
        params.put("Id", id);
        params.put("GuId", guid);
        params.put("File", file);
        AsyncHttp.post(WITH_30008, AsyncHttpConfig.URL_FILE, params, asyncHttpCallBack);
    }
    /**
     * 获取图片
     */
    public static void getFile(String id, AsyncHttpCallBack asyncHttpCallBack) {
//        RequestParams params = new RequestParams();
//        params.put("only_number ", only_number);
//        params.put("type ", type);
        AsyncHttp.get(WITH_30010, AsyncHttpConfig.URL_HUOQU + "?id=" + id, asyncHttpCallBack);
    }

    //生成二维码
    public static void getQRGenerate(String code,AsyncHttpCallBack asyncHttpCallBack) {
        RequestParams params = new RequestParams();
        params.put("code ", code);
        AsyncHttp.get(WITH_50009, AsyncHttpConfig.URL_GET_QR_GENERATE,params, asyncHttpCallBack);
    }

    //发送邮箱码
    public static void getEsond(String Email, String request, AsyncHttpCallBack asyncHttpCallBack) {
        RequestParams params = new RequestParams();
        params.put("Email", Email);
        params.put("request", request);
        AsyncHttp.get(WITH_50004, AsyncHttpConfig.URL_GET_SEND_MEIL, params, asyncHttpCallBack);
    }
    //邮箱登入
    public static void getBindingEmail(String email, String code,String log_verify_code, AsyncHttpCallBack asyncHttpCallBack) {
        RequestParams params = new RequestParams();
        params.put("Email", email);
        params.put("verify_code", code);
        params.put("log_verify_code", log_verify_code);
        AsyncHttp.get(WITH_60008, AsyncHttpConfig.URL_BINDING_EMAIL, params, asyncHttpCallBack);
    }
    /**
     * 删除图片
     */
    public static void deleteFile(String id, String guid, AsyncHttpCallBack asyncHttpCallBack) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("guid", guid);
//        LogcatUtil.i(params.toString());
        AsyncHttp.get(WITH_60005, AsyncHttpConfig.URL_DELETE_FILE,params, asyncHttpCallBack);
    }

    /**
     * APP自动更新kylpeng
     */
    public static void getUpdate(int ver, AsyncHttpCallBack asyncHttpCallBack) {
//        RequestParams params = new RequestParams();
//        params.put("ver ", ver);
        AsyncHttp.get(WITH_50011, AsyncHttpConfig.URL_GET_UPDATES + "?ver=" + ver, asyncHttpCallBack);
    }
    public static void getNewLogin(String username,String password,AsyncHttpCallBack asyncHttpCallBack) {

        String address = NetRequest.URL+ "/fryms/api/login_C.php?"+ Util.encodeBase64String(username,password);
        AsyncHttp.get(WITH_50000, address, asyncHttpCallBack);
    }
}
