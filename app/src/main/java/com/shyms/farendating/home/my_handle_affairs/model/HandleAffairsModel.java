package com.shyms.farendating.home.my_handle_affairs.model;

import java.io.Serializable;
import java.util.List;

public class HandleAffairsModel implements Serializable {

    /**
     * code : 0
     * data : [{"onlyNumber":"GS1603140069","sdate":"2016-03-14 09:52:06","Company_Name":"","liu_Name":"FS01","liu_name":"网上申请","Action_date":"2016-03-14 09:52:07","Name":"母婴保健技术服务机构执业许可（变更注册）","dName":"禅城区卫生和计划生育局"},{"onlyNumber":"GS1601070007","sdate":"2016-01-07 09:03:12","Company_Name":"佛山电力","liu_Name":"FS01","liu_name":"网上申请","Action_date":"2016-01-07 09:03:12","Name":"挖掘城市道路审批","dName":"禅城区交通运输局"}]
     * message : null
     * meta : null
     */

    private String code;
    private Object message;
    private Object meta;
    /**
     * onlyNumber : GS1603140069
     * sdate : 2016-03-14 09:52:06
     * Company_Name :
     * liu_Name : FS01
     * liu_name : 网上申请
     * Action_date : 2016-03-14 09:52:07
     * Name : 母婴保健技术服务机构执业许可（变更注册）
     * dName : 禅城区卫生和计划生育局
     */

    private List<DataEntity> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getMeta() {
        return meta;
    }

    public void setMeta(Object meta) {
        this.meta = meta;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HandleAffairsModel{" +
                "code='" + code + '\'' +
                ", message=" + message +
                ", meta=" + meta +
                ", data=" + data +
                '}';
    }

    public static class DataEntity implements Serializable{
        //        final public String onlynumber;       //流水号
//        final public String sdate;    //开始时间
//        final public String company_name;//公司名称
//        final public String liu_name;  //流程名称
//        final public String liu_nametext;  //流程名称
//        final public String action_date; //操作时间
//        final public String name;
//        // 事项名
//        final public String dname;    //所属部门
//        final public String is_window_apply;    //所属部门
        public final String onlynumber;
        public final String sdate;
        public final String company_name;
        public final String liu_name;
        public final String liu_nametext;
        public final String action_date;
        public final String name;
        public final String dname;
        public final String is_window_apply;

        public DataEntity() {
            liu_nametext = null;
            onlynumber = null;
            sdate = null;
            company_name = null;
            liu_name = null;
            action_date = null;
            name = null;
            dname = null;
            is_window_apply = null;
        }



        @Override
        public String toString() {
            return "DataEntity{" +
                    "onlyNumber='" + onlynumber + '\'' +
                    ", sdate='" + sdate + '\'' +
                    ", Company_Name='" + company_name + '\'' +
                    ", liu_Name='" + liu_name + '\'' +
                    ", liu_name='" + liu_nametext + '\'' +
                    ", Action_date='" + action_date + '\'' +
                    ", Name='" + name + '\'' +
                    ", dName='" + dname + '\'' +
                    '}';
        }
    }
}
