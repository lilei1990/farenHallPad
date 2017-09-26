package com.shyms.farendating.home.lien_up.model;

import java.util.List;

/**
 * 窗口服务
 */
public class WindowsSystemInfo {

    /**
     * code : 0
     * data : [{"name":"综合窗口","description":"提供除工程报建外的所有法人行政审批服务","sort":"1","prefix":"A","system":"1"},{"name":"收费窗口","description":"提供收费服务的窗口","sort":"4","prefix":"S","system":"4"}]
     * message : null
     * meta : null
     */

    private String code;
    private Object message;
    private Object meta;
    /**
     * name : 综合窗口
     * description : 提供除工程报建外的所有法人行政审批服务
     * sort : 1
     * prefix : A
     * system : 1
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

    public static class DataEntity {
        private String name;
        private String description;
        private String sort;
        private String prefix;
        private String system;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getSystem() {
            return system;
        }

        public void setSystem(String system) {
            this.system = system;
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", sort='" + sort + '\'' +
                    ", prefix='" + prefix + '\'' +
                    ", system='" + system + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WindowsSystemInfo{" +
                "code='" + code + '\'' +
                ", message=" + message +
                ", meta=" + meta +
                ", data=" + data +
                '}';
    }
}
