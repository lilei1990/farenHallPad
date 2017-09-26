package com.shyms.farendating.home.lien_up.model;

import java.io.Serializable;

/**
 * Created by Weimin on 6/28/2016.
 */
public class NGetAppointNum implements Serializable {

    public final String queue_id;// int // 排队ID
    public final String mobile; // int 用户手机号码
    public final String username; // string 姓名
    public final String number;// string 排队号码
    public final String status;// int 状态，0、弃号，1、排队中，2、叫号，3、开始办理，4、办理结束
    public final String win;// int 窗口号码
    public final String system; // string 叫号系统标识
    public final String time1;// string 排队时间
    public final String time2;// string 叫号时间
    public final String time3;// string 开始办理时间
    public final String time4;// string 办理结束时间
    public final String url;// string 二维码url
    public final String before;


    public NGetAppointNum() {
        queue_id = null;// int // 排队ID
        mobile = null; // int 用户手机号码
        username = null; // string 姓名
        number = null;// string 排队号码
        status = null;// int 状态，0、弃号，1、排队中，2、叫号，3、开始办理，4、办理结束
        win = null;// int 窗口号码
        system = null; // string 叫号系统标识
        time1 = null;// string 排队时间
        time2 = null;// string 叫号时间
        time3 = null;// string 开始办理时间
        time4 = null;// string 办理结束时间
        url = null;// string 二维码url
        before = null;
    }


}
