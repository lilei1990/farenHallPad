package com.shyms.farendating.home.guide.model;


import java.util.List;

/**
 * 办事指南列表信息
 * Created by 洪开盛 on 2015/7/22.
 */
public class AffairsInfo {


    /**
     * code : 0
     * data : [{"MtId":"1465","Name":"外商投资企业分立","Number":"JK038","section":"禅城区经济和科技促进局","DepId":"25","linkUrl":"/result/handle_affairs/street.php?mtid=1465"},{"MtId":"1466","Name":"外商投资企业合并","Number":"JK040","section":"禅城区经济和科技促进局","DepId":"25","linkUrl":"/result/handle_affairs/street.php?mtid=1466"},{"MtId":"1467","Name":"外商投资企业设立","Number":"JK041","section":"禅城区经济和科技促进局","DepId":"25","linkUrl":"/result/handle_affairs/street.php?mtid=1467"},{"MtId":"1513","Name":"对外承包工程资格（初审）","Number":"JK021","section":"禅城区经济和科技促进局","DepId":"25","linkUrl":"/result/handle_affairs/street.php?mtid=1513"},{"MtId":"1514","Name":"对外劳务合作经营资格核准(初审) ","Number":"JK022","section":"禅城区经济和科技促进局","DepId":"25","linkUrl":"/result/handle_affairs/street.php?mtid=1514"},{"MtId":"1515","Name":"对外贸易经营者登记事项变更","Number":"JK024","section":"禅城区经济和科技促进局","DepId":"25","linkUrl":"/result/handle_affairs/street.php?mtid=1515"},{"MtId":"1516","Name":"机电产品进口许可审核（初审）","Number":"JK025","section":"禅城区经济和科技促进局","DepId":"25","linkUrl":"/result/handle_affairs/street.php?mtid=1516"},{"MtId":"1517","Name":"机电产品自动进口许可审核（初审）","Number":"JK026","section":"禅城区经济和科技促进局","DepId":"25","linkUrl":"/result/handle_affairs/street.php?mtid=1517"},{"MtId":"1518","Name":"境外企业的设立及其变更","Number":"JK027","section":"禅城区经济和科技促进局","DepId":"25","linkUrl":"/result/handle_affairs/street.php?mtid=1518"},{"MtId":"1519","Name":"境外投资特殊企业的设立初审（更名为境外投资特殊企业的设立及其变更）","Number":"JK028","section":"禅城区经济和科技促进局","DepId":"25","linkUrl":"/result/handle_affairs/street.php?mtid=1519"}]
     * message : null
     * meta : null
     */

    private String code;
    private Object message;
    private Object meta;
    /**
     * MtId : 1465
     * Name : 外商投资企业分立
     * Number : JK038
     * section : 禅城区经济和科技促进局
     * DepId : 25
     * linkUrl : /result/handle_affairs/street.php?mtid=1465
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
        //        "departmentname": "禅城区市场监督管理局（食药监）",
//                "shortname": "食药监",
//                "name": "《食品生产许可证》核发",
//                "number": "SY042",
//                "depid": "42",
//                "mtid": "1487",
//                "linkUrl": "/cailiao/mobile/banshi/getmatter/number/SY042"
//        "mtid": "1514",
//                "name": "对外劳务合作经营资格核准(初审)",
//                "number": "JK022",
//                "section": "禅城区经济和科技促进局",
//                "depid": "25",
//                "linkUrl": "/cailiao/mobile/banshi/getmatter/number/JK022"
        private String mtid;
        private String name;
        private String number;
        private String section;
        private String depid;
        private String linkUrl;
        private String departmentname;
        private String shortname;

        public String getMtid() {
            return mtid;
        }

        public String getDepid() {
            return depid;
        }

        public String getDepartmentname() {
            return departmentname;
        }

        public String getShortname() {
            return shortname;
        }



        public String getName() {
            return name;
        }


        public String getNumber() {
            return number;
        }


        public String getSection() {
            return section;
        }


        public String getDepId() {
            return depid;
        }


        public String getLinkUrl() {
            return linkUrl;
        }

    }
}
