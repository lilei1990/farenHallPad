package com.shyms.farendating.home.lien_up.model;

import java.util.List;

/**
 * 查询排队人数
 * Created by hks on 2016/3/25.
 */
public class QueryLineUpInfo {

    /**
     * code : 0
     * data : {"count":"1","window":[{"win":"1","status":"服务","name":"彭晏伟"},{"win":"5","status":"服务","name":"陈艳华"}]}
     * message : null
     * meta : null
     */

    private String code;
    /**
     * count : 1
     * window : [{"win":"1","status":"服务","name":"彭晏伟"},{"win":"5","status":"服务","name":"陈艳华"}]
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
        private String count;
        /**
         * win : 1
         * status : 服务
         * name : 彭晏伟
         */

        private List<WindowEntity> window;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<WindowEntity> getWindow() {
            return window;
        }

        public void setWindow(List<WindowEntity> window) {
            this.window = window;
        }

        public static class WindowEntity {
            private String win;
            private String status;
            private String name;

            public String getWin() {
                return win;
            }

            public void setWin(String win) {
                this.win = win;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public String toString() {
                return "WindowEntity{" +
                        "win='" + win + '\'' +
                        ", status='" + status + '\'' +
                        ", name='" + name + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "count='" + count + '\'' +
                    ", window=" + window +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "QueryLineUpInfo{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", message=" + message +
                ", meta=" + meta +
                '}';
    }
}
