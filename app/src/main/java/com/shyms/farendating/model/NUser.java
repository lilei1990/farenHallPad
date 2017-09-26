package com.shyms.farendating.model;

import java.io.Serializable;

/**
 * Created by Weimin on 4/8/2016.
 */
public class NUser implements Serializable {
    private String name; //用户姓名、昵称
    private String lastLoginTime;//最后登录时间
    private String ban;//权限
    private int points;//积分
    private String userId;//用户ID
    private String username;//用户名
    private String id_card;//身份证号码
    private String badges;//头衔
    private String log_verify_code;//登录用户识别码  string，12小时内有效
    private String avatar;
    private String avatarUrl;
    private String email;
    private String token;
    private String loginAddress;

    public NUser() {
        this.name = null;
        this.lastLoginTime = null;
        this.ban = null;
        this.points = 0;
        this.userId = null;
        this.username = null;
        this.id_card = null;
        this.badges = null;
        this.log_verify_code = null;
        this.avatar = null;
        this.avatarUrl = null;
        this.email = null;
        this.loginAddress = null;
    }

    public void init(UserInfo.DataEntity user) {

//        this.name = user
//        this.lastLoginTime = user.lastLoginTime;
//        this.ban = user.ban;
//        this.points = user.points;
//        this.userId = user.userId;
//        this.username = user.username;
//        this.id_card = user.id_card;
//        this.badges = user.badges;
//        this.log_verify_code = user.log_verify_code;
//        this.avatar = user.avatar;
//        this.avatarUrl = user.avatarUrl;
//        this.email = user.email;
    }

    public void init(String token) {
    this.token=token;
    }
    public void save(String loginAddress) {
    this.loginAddress=loginAddress;
    }

    public String getLoginAddress() {
        return loginAddress;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public String getBan() {
        return ban;
    }

    public int getPoints() {
        return points;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getEmail() {
        return email;
    }


    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getId_card() {
        return id_card;
    }

    public String getBadges() {
        return badges;
    }

    public String getLogVerifyCode() {
        return log_verify_code;
    }

}
