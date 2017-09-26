package com.shyms.farendating.utils;

/**
 * code 错误代码
 * Created by hokas on 2015/7/4.
 */
public class ContentCode {


    /**
     * 事项流水号
     */
    public static String PREVIEW_PIC = "preview_img";
    public static String MATTERS_SERTAL_NUMBER = "matters_sertal_number";
    public static String WHOLE_RECORDER = "whole_affair_recorder";
    public static int APPOINT_SIGN = -1;

    public static String RING_CALL_ONCE = "ring_once";
    public static String SHOW_DIALOG = "show_dialog";
    public static String REMINDER_DIALOG = "reminder_dialog";

    public static String QUEUE_ID = UserManager.getInstance().getLastUserInfo()== null ? "queue_id" : UserManager.getInstance().getLastUserInfo().getData().getUserId() + "queue_id";

    /**
     * 事项类别
     */
    public static String MATTERS_LEI_BIE = "matters_lei_bie";

    public static final String log_verify_code = "v8Em54eWXYPBE4A";

    /**
     * 事项ID
     */
    public static String MATTERS_ID = "matters_id";

    public static String NUMMERS_ID = "number_id";

    /**
     * 事项名称
     */
    public static String MATTERS_NAME = "matters_name";
    /**
     * 可操作状态
     */
    public static String MATTER_STATE = "matters_state";
    /**
     * 可操作状态
     */
    public static String OPERATE_STATE = "operate_name";

    /**
     * 事项number
     */
    public static String MATTERS_NUMBER = "matters_number";

    /**
     * 类别
     */
    public static String LEI_BIE = "matters_name";


    /**
     * 材料分数
     */
    public static String CAI_LIAO_FEN_SHU = "matters_num";

    /**
     * 材料缓存
     */
    public static String IMAGS_CACHE = "imgs_cache";


    /**
     * 材料条目position
     */
    public static String IMAGS_POSTTION = "imgs_position";

    /**
     * 请求成功
     */
    public static String WHAT_0 = "0";


    /**
     * 退出按钮
     */
    public static int FINISH = 100;

    /**
     * 账号不存在
     */
    public static String WHAT_1001 = "1001";
    /**
     * 账号不可用
     */
    public static String WHAT_1002 = "1002";
    /**
     * 账号或密码错误
     */
    public static String WHAT_1003 = "1003";
    /**
     * 密码错误
     */
    public static String WHAT_1004 = "1004";
    /**
     * 账号未登陆
     */
    public static String WHAT_1005 = "1005";
    /**
     * 用户名已存在
     */
    public static String WHAT_1006 = "1006";
    /**
     * 已对回答投票
     */
    public static String WHAT_1010 = "1010";
    /**
     * 已对政策投票
     */
    public static String WHAT_1011 = "1011";
    /**
     * 已对参考案例投票
     */
    public static String WHAT_1012 = "1012";
    /**
     * 已对资讯投票
     */
    public static String WHAT_1013 = "1013";
    /**
     * 验证码错误
     */
    public static String WHAT_1020 = "1020";
    /**
     * 手机号码错误
     */
    public static String WHAT_1021 = "1021";
    /**
     * 验证码发送频率限制
     */
    public static String WHAT_1022 = "1022";
    /**
     * 事务查询验证错误
     */
    public static String WHAT_1023 = "1023";
    /**
     * 账号已被禁止
     */
    public static String WHAT_1024 = "1024";
    /**
     * 账号已被禁言
     */
    public static String WHAT_1025 = "1025";
    /**
     * 缺少必要参数
     */
    public static String WHAT_2001 = "2001";
    /**
     * 参数值不支持
     */
    public static String WHAT_2002 = "2002";
    /**
     * 网络错误
     */
    public static String WHAT_3001 = "3001";
    /**
     * 数据库错误
     */
    public static String WHAT_9001 = "9001";
    /**
     * 服务器忙
     */
    public static String WHAT_9002 = "9002";
    /**
     * 未知错误
     */
    public static String WHAT_9999 = "9999";
}
