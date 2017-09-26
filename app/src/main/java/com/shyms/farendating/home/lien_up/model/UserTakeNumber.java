package com.shyms.farendating.home.lien_up.model;

/**
 * Created by hks on 2016/3/25.
 */
public class UserTakeNumber {

    /**
     * code : 0
     * data : {"queue_id":"20","mobile":"15012341234","username":"","number":"0001","status":"1","win":"","system":"100","time1":"2016-03-22 10:26:33","time2":"0000-00-00 00:00:00","time3":"0000-00-00 00:00:00","time4":"0000-00-00 00:00:00","before":"1"}
     * message : null
     * meta : null
     */

    private String code;
    /**
     * queue_id : 20
     * mobile : 15012341234
     * username :
     * number : 0001
     * status : 1
     * win :
     * system : 100
     * time1 : 2016-03-22 10:26:33
     * time2 : 0000-00-00 00:00:00
     * time3 : 0000-00-00 00:00:00
     * time4 : 0000-00-00 00:00:00
     * before : 1
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
        private String queue_id;
        private String mobile;
        private String username;
        private String number;
        private String status;
        private String win;
        private String system;
        private String time1;
        private String time2;
        private String time3;
        private String time4;
        private String before;

        public String getQueue_id() {
            return queue_id;
        }

        public void setQueue_id(String queue_id) {
            this.queue_id = queue_id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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

        public String getWin() {
            return win;
        }

        public void setWin(String win) {
            this.win = win;
        }

        public String getSystem() {
            return system;
        }

        public void setSystem(String system) {
            this.system = system;
        }

        public String getTime1() {
            return time1;
        }

        public void setTime1(String time1) {
            this.time1 = time1;
        }

        public String getTime2() {
            return time2;
        }

        public void setTime2(String time2) {
            this.time2 = time2;
        }

        public String getTime3() {
            return time3;
        }

        public void setTime3(String time3) {
            this.time3 = time3;
        }

        public String getTime4() {
            return time4;
        }

        public void setTime4(String time4) {
            this.time4 = time4;
        }

        public String getBefore() {
            return before;
        }

        public void setBefore(String before) {
            this.before = before;
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "queue_id='" + queue_id + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", username='" + username + '\'' +
                    ", number='" + number + '\'' +
                    ", status='" + status + '\'' +
                    ", win='" + win + '\'' +
                    ", system='" + system + '\'' +
                    ", time1='" + time1 + '\'' +
                    ", time2='" + time2 + '\'' +
                    ", time3='" + time3 + '\'' +
                    ", time4='" + time4 + '\'' +
                    ", before='" + before + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserTakeNumber{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", message=" + message +
                ", meta=" + meta +
                '}';
    }
}
