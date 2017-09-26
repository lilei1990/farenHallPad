package com.shyms.farendating.home.lien_up.model;

import java.io.Serializable;

/**
 * 用户查询排队状态
 * Created by hks on 2016/3/25.
 */
public class UserQueryLineUp implements Serializable{

    /**
     * code : 0
     * data : {"win":"02","before":0,"number":"0002","status":"1"}
     * message : null
     * meta : null
     */

    private String code;
    /**
     * win : 02
     * before : 0
     * number : 0002
     * status : 1
     */

    private DataEntity data;
    private Object message;
    private Object meta;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
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

    public static class DataEntity {
        private String win;
        private String before;
        private String number;
        private String status;
        private String call;
        private String system;
        private String system_name;

        public String getWin() {
            return win;
        }

        public void setWin(String win) {
            this.win = win;
        }

        public String getBefore() {
            return before;
        }

        public void setBefore(String before) {
            this.before = before;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCall() {
            return call;
        }

        public void setCall(String call) {
            this.call = call;
        }

        public String getSystem() {
            return system;
        }

        public void setSystem(String system) {
            this.system = system;
        }

        public String getSystem_name() {
            return system_name;
        }

        public void setSystem_name(String system_name) {
            this.system_name = system_name;
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "win='" + win + '\'' +
                    ", before='" + before + '\'' +
                    ", number='" + number + '\'' +
                    ", status='" + status + '\'' +
                    ", call='" + call + '\'' +
                    ", system='" + system + '\'' +
                    ", system_name='" + system_name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserQueryLineUp{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", message=" + message +
                ", meta=" + meta +
                '}';
    }
}
