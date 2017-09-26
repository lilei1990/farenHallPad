package com.shyms.farendating.home.my_handle_affairs.material.model;

import java.io.Serializable;

/**
 * Created by hks on 2016/4/18.
 * <p>
 * 事项历史材料类
 */
public class NMatterMaterial implements Serializable {
    //    "id": "120339",
//            "name": "施工单位《工商营业执照》及其法人代表身份证",
//            "count": "1",
//            "class": "图片材料",
//            "nature": "验原件收复印件",
//            "is_upload": "1",
//            "is_complete": "1",
//            "is_need_upload": "1",
//            "liu_name": "FS03",
//            "bumen_result": "1",
//            "only_number": "GS1509150002"
    public final String id;    //材料ID
    public final String name;  //材料名称
    public final String count;    //材料所需要的分数
    //    public String Class;  //材料类型
    public final String nature;    //材料性质
    public final String is_upload;  //是否上传
    public final String is_complete;    //是否完备
    public final String num;            //材料分数
    public final String only_number;    //流水单号
    public final String is_need_upload;    //流水单号
    public final String bumen_result;    //流水单号
    public final String liu_name;    //流水单号



    public NMatterMaterial() {
        id = null;
        name = null;
        count = null;
        nature = null;
        is_upload = null;
        is_complete = null;
        num = null;
        only_number = null;
        is_need_upload = null;
        bumen_result = null;
        liu_name = null;
    }
}
