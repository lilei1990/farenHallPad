package com.shyms.farendating.home.lien_up.model;

import com.google.gson.annotations.SerializedName;

/**
 * 窗口登录信息
 */
public class WindowsLoginInfo {


    /**
     * code : 0
     * data : {"admin_id":"1","username":"12345678901","name":"王明","mobile":"13800123456","last_login_time":"2016-02-23 00:00:00","permission":{"1":15,"2":1,"4":15,"8":15,"16":1},"ban":"1","site_id":"1"}
     * message : null
     * meta : null
     */

    private String code;
    /**
     * admin_id : 1
     * username : 12345678901
     * name : 王明
     * mobile : 13800123456
     * last_login_time : 2016-02-23 00:00:00
     * permission : {"1":15,"2":1,"4":15,"8":15,"16":1}
     * ban : 1
     * site_id : 1
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
        private String admin_id;
        private String username;
        private String name;
        private String mobile;
        private String last_login_time;
        /**
         * 1 : 15
         * 2 : 1
         * 4 : 15
         * 8 : 15
         * 16 : 1
         */

        private PermissionEntity permission;
        private String ban;
        private String site_id;
        private String site_name;

        public String getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(String admin_id) {
            this.admin_id = admin_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }

        public PermissionEntity getPermission() {
            return permission;
        }

        public void setPermission(PermissionEntity permission) {
            this.permission = permission;
        }

        public String getBan() {
            return ban;
        }

        public void setBan(String ban) {
            this.ban = ban;
        }

        public String getSite_id() {
            return site_id;
        }

        public void setSite_id(String site_id) {
            this.site_id = site_id;
        }

        public String getSite_name() {
            return site_name;
        }

        public void setSite_name(String site_name) {
            this.site_name = site_name;
        }

        public static class PermissionEntity {
            @SerializedName("1")
            private int value1;
            @SerializedName("2")
            private int value2;
            @SerializedName("4")
            private int value4;
            @SerializedName("8")
            private int value8;
            @SerializedName("16")
            private int value16;

            public int getValue1() {
                return value1;
            }

            public void setValue1(int value1) {
                this.value1 = value1;
            }

            public int getValue2() {
                return value2;
            }

            public void setValue2(int value2) {
                this.value2 = value2;
            }

            public int getValue4() {
                return value4;
            }

            public void setValue4(int value4) {
                this.value4 = value4;
            }

            public int getValue8() {
                return value8;
            }

            public void setValue8(int value8) {
                this.value8 = value8;
            }

            public int getValue16() {
                return value16;
            }

            public void setValue16(int value16) {
                this.value16 = value16;
            }

            @Override
            public String toString() {
                return "PermissionEntity{" +
                        "value1=" + value1 +
                        ", value2=" + value2 +
                        ", value4=" + value4 +
                        ", value8=" + value8 +
                        ", value16=" + value16 +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "admin_id='" + admin_id + '\'' +
                    ", username='" + username + '\'' +
                    ", name='" + name + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", last_login_time='" + last_login_time + '\'' +
                    ", permission=" + permission +
                    ", ban='" + ban + '\'' +
                    ", site_id='" + site_id + '\'' +
                    ", site_name='" + site_name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WindowsLoginInfo{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", message=" + message +
                ", meta=" + meta +
                '}';
    }
}
