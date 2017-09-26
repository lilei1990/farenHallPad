package com.shyms.farendating.model;

import java.io.Serializable;

/**
 * 用户信息
 */
public class UserInfo implements Serializable {

    private String code;
    private DataEntity data;
    private String message;
    private String meta;

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getMeta() {
        return meta;
    }

    public static class DataEntity {
        private String name;
        private String lastLoginTime;
        private String sex;
        private String ban;
        private String points;
        private String userId;
        private String username;
        private String avatar;
        private String id_card;
        private String avatarUrl;
        private String registerTime;
        private String badges;
        private String log_verify_code;
        private String gender;
        private String email;

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBan() {
            return ban;
        }

        public void setBan(String ban) {
            this.ban = ban;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getId_card() {
            return id_card;
        }

        public void setId_card(String id_card) {
            this.id_card = id_card;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(String registerTime) {
            this.registerTime = registerTime;
        }

        public String getBadges() {
            return badges;
        }

        public void setBadges(String badges) {
            this.badges = badges;
        }

        public String getLog_verify_code() {
            return log_verify_code;
        }

        public void setLog_verify_code(String log_verify_code) {
            this.log_verify_code = log_verify_code;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "name='" + name + '\'' +
                    ", lastLoginTime='" + lastLoginTime + '\'' +
                    ", sex='" + sex + '\'' +
                    ", ban='" + ban + '\'' +
                    ", points='" + points + '\'' +
                    ", userId='" + userId + '\'' +
                    ", username='" + username + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", id_card='" + id_card + '\'' +
                    ", avatarUrl='" + avatarUrl + '\'' +
                    ", registerTime='" + registerTime + '\'' +
                    ", badges='" + badges + '\'' +
                    ", log_verify_code='" + log_verify_code + '\'' +
                    ", gender='" + gender + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", message=" + message +
                ", meta=" + meta +
                '}';
    }
}
