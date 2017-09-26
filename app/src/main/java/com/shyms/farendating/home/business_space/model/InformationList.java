package com.shyms.farendating.home.business_space.model;

import java.io.Serializable;
import java.util.List;

/**
 * 创业空间
 * Created by hokas on 2015/7/4.
 */
public class InformationList implements Serializable{

    /**
     * code : 0
     * data : [{"informationId":"5","title":"投资开店推荐14个学校附近的赚钱行业","brief":"http://www.sc115.com/wenku/uploads/allimg/140417/2225495W9-0.jpg","content":null,"publishTime":"2015-08-05 21:52:23","category":null,"viewNum":"51","favoriteNum":"0","voteUp":"0","voteDown":"0","isFavorited":null,"isVoted":null},{"informationId":"4","title":"大学生创业融资的方式主要有哪些","brief":"http://t10.baidu.com/it/u=1698162210,2248975490&fm=76","content":null,"publishTime":"2015-08-05 21:49:11","category":null,"viewNum":"22","favoriteNum":"0","voteUp":"0","voteDown":"0","isFavorited":null,"isVoted":null},{"informationId":"3","title":"创业获取收入的四种模式","brief":"http://static.zixun.9978.cn/Upload/image/20150804/20150804014624_87034.jpg","content":null,"publishTime":"2015-08-05 21:46:14","category":null,"viewNum":"31","favoriteNum":"0","voteUp":"0","voteDown":"0","isFavorited":null,"isVoted":null},{"informationId":"2","title":"创业想法成功的5个步骤","brief":"http://static.zixun.9978.cn/Upload/image/20140228/20140228084847_81085.jpg","content":null,"publishTime":"2015-08-05 21:42:16","category":null,"viewNum":"22","favoriteNum":"0","voteUp":"0","voteDown":"0","isFavorited":null,"isVoted":null},{"informationId":"1","title":"创业者宣言","brief":"http://static.zixun.9978.cn/Upload/image/20140228/20140228084847_81085.jpg","content":null,"publishTime":"2015-08-05 21:03:39","category":null,"viewNum":"20","favoriteNum":"0","voteUp":"0","voteDown":"0","isFavorited":null,"isVoted":null}]
     * message : null
     * meta : null
     */

    private String code;
    private Object message;
    private Object meta;
    /**
     * informationId : 5
     * title : 投资开店推荐14个学校附近的赚钱行业
     * brief : http://www.sc115.com/wenku/uploads/allimg/140417/2225495W9-0.jpg
     * content : null
     * publishTime : 2015-08-05 21:52:23
     * category : null
     * viewNum : 51
     * favoriteNum : 0
     * voteUp : 0
     * voteDown : 0
     * isFavorited : null
     * isVoted : null
     */

    private List<DataEntity> data;

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public void setMeta(Object meta) {
        this.meta = meta;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public Object getMessage() {
        return message;
    }

    public Object getMeta() {
        return meta;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity implements Serializable{
        private String informationId;
        private String title;
        private String brief;
        private Object content;
        private String publishTime;
        private Object category;
        private String viewNum;
        private String favoriteNum;
        private String voteUp;
        private String voteDown;
        private Object isFavorited;
        private Object isVoted;

        public void setInformationId(String informationId) {
            this.informationId = informationId;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public void setContent(Object content) {
            this.content = content;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public void setCategory(Object category) {
            this.category = category;
        }

        public void setViewNum(String viewNum) {
            this.viewNum = viewNum;
        }

        public void setFavoriteNum(String favoriteNum) {
            this.favoriteNum = favoriteNum;
        }

        public void setVoteUp(String voteUp) {
            this.voteUp = voteUp;
        }

        public void setVoteDown(String voteDown) {
            this.voteDown = voteDown;
        }

        public void setIsFavorited(Object isFavorited) {
            this.isFavorited = isFavorited;
        }

        public void setIsVoted(Object isVoted) {
            this.isVoted = isVoted;
        }

        public String getInformationId() {
            return informationId;
        }

        public String getTitle() {
            return title;
        }

        public String getBrief() {
            return brief;
        }

        public Object getContent() {
            return content;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public Object getCategory() {
            return category;
        }

        public String getViewNum() {
            return viewNum;
        }

        public String getFavoriteNum() {
            return favoriteNum;
        }

        public String getVoteUp() {
            return voteUp;
        }

        public String getVoteDown() {
            return voteDown;
        }

        public Object getIsFavorited() {
            return isFavorited;
        }

        public Object getIsVoted() {
            return isVoted;
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "informationId='" + informationId + '\'' +
                    ", title='" + title + '\'' +
                    ", brief='" + brief + '\'' +
                    ", content=" + content +
                    ", publishTime='" + publishTime + '\'' +
                    ", category=" + category +
                    ", viewNum='" + viewNum + '\'' +
                    ", favoriteNum='" + favoriteNum + '\'' +
                    ", voteUp='" + voteUp + '\'' +
                    ", voteDown='" + voteDown + '\'' +
                    ", isFavorited=" + isFavorited +
                    ", isVoted=" + isVoted +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "InformationList{" +
                "code='" + code + '\'' +
                ", message=" + message +
                ", meta=" + meta +
                ", data=" + data +
                '}';
    }
}
