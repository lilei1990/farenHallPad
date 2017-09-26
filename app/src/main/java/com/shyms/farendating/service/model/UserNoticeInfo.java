package com.shyms.farendating.service.model;

import java.util.List;

/**
 * 用户消息通知
 * Created by hokas on 2015/8/21.
 */
public class UserNoticeInfo {

    /**
     * message : null
     * data : [{"mtId":"216","matterName":"工商预先核名","isPrint":"yes","courseResult":"pass","status":"0","nextCourse":"一口受理","uId":"24","windowNumber":"13","mopassReason":"","courseName":"预审"},{"mtId":"216","matterName":"工商预先核名","isPrint":"yes","courseResult":"pass","status":"0","nextCourse":"一口受理","uId":"24","windowNumber":"13","mopassReason":"","courseName":"预审"}]
     * code : 0
     * meta : null
     */
    private String message;
    private List<DataEntity> data;
    private String code;
    private String meta;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getMessage() {
        return message;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public String getMeta() {
        return meta;
    }

    public static class DataEntity {
        /**
         * mtId : 216
         * matterName : 工商预先核名
         * isPrint : yes
         * courseResult : pass
         * status : 0
         * nextCourse : 一口受理
         * uId : 24
         * windowNumber : 13
         * mopassReason :
         * courseName : 预审
         */
        private String mtId;
        private String matterName;
        private String isPrint;
        private String courseResult;
        private String status;
        private String nextCourse;
        private String uId;
        private String windowNumber;
        private String mopassReason;
        private String courseName;
        private String flowNumber;

        public String getuId() {
            return uId;
        }

        public void setuId(String uId) {
            this.uId = uId;
        }

        public String getFlowNumber() {
            return flowNumber;
        }

        public void setFlowNumber(String flowNumber) {
            this.flowNumber = flowNumber;
        }

        public void setMtId(String mtId) {
            this.mtId = mtId;
        }

        public void setMatterName(String matterName) {
            this.matterName = matterName;
        }

        public void setIsPrint(String isPrint) {
            this.isPrint = isPrint;
        }

        public void setCourseResult(String courseResult) {
            this.courseResult = courseResult;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setNextCourse(String nextCourse) {
            this.nextCourse = nextCourse;
        }

        public void setUId(String uId) {
            this.uId = uId;
        }

        public void setWindowNumber(String windowNumber) {
            this.windowNumber = windowNumber;
        }

        public void setMopassReason(String mopassReason) {
            this.mopassReason = mopassReason;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getMtId() {
            return mtId;
        }

        public String getMatterName() {
            return matterName;
        }

        public String getIsPrint() {
            return isPrint;
        }

        public String getCourseResult() {
            return courseResult;
        }

        public String getStatus() {
            return status;
        }

        public String getNextCourse() {
            return nextCourse;
        }

        public String getUId() {
            return uId;
        }

        public String getWindowNumber() {
            return windowNumber;
        }

        public String getMopassReason() {
            return mopassReason;
        }

        public String getCourseName() {
            return courseName;
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "mtId='" + mtId + '\'' +
                    ", matterName='" + matterName + '\'' +
                    ", isPrint='" + isPrint + '\'' +
                    ", courseResult='" + courseResult + '\'' +
                    ", status='" + status + '\'' +
                    ", nextCourse='" + nextCourse + '\'' +
                    ", uId='" + uId + '\'' +
                    ", windowNumber='" + windowNumber + '\'' +
                    ", mopassReason='" + mopassReason + '\'' +
                    ", courseName='" + courseName + '\'' +
                    ", flowNumber='" + flowNumber + '\'' +
                    '}';
        }
    }
}
