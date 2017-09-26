package com.shyms.farendating.utils;


import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.shyms.farendating.model.UserInfo;

import java.util.HashMap;
import java.util.Map;

import me.hokas.base.BaseApplication;
import me.hokas.utils.PasswordUtil;
import me.hokas.utils.PreferencesUtil;

public class UserManager {

    private Context context;
    private static UserManager instance;
    private UserInfo userInfo;
    private String loginAddress;
    private Map<String, String> codeMessageMap;

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager(BaseApplication.getInstance());
        }
        return instance;
    }

    private UserManager(Context context) {
        this.context = context;
    }

    public void setLoginAddress(String loginAddress) {
        this.loginAddress = loginAddress;
    }

    public String getLoginAddress() {
        return loginAddress;
    }

    /**
     * 保存用户信息到本地
     */
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
        PreferencesUtil.setUserInfo(JSON.toJSONString(userInfo));
    }

    /**
     * 设置登录名和密码
     *
     * @param name
     * @param password
     */
    public void setLoginInfo(String name, String password) {
        String key = "110120114";
        PreferencesUtil.setDataString(PreferencesUtil.KEY_LOGIN_NAME, name);
        // 加密密码再存储
        try {
            byte[] salt = PasswordUtil.getSalt();
            // 将salt保存到本地，供获取登录信息时使用
            PreferencesUtil.setDataString(PreferencesUtil.KEY_SALT, new String(salt, "ISO-8859-1"));
            // 加密保存
            String encryptPsw = PasswordUtil.encrypt(password, key, salt);
            PreferencesUtil.setDataString(PreferencesUtil.KEY_LOGIN_PSW, encryptPsw);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取登录名和密码
     *
     * @return
     */
    public Map<String, String> getLoginInfo() {
        String key = "110120114";
        Map<String, String> map = new HashMap<>();
        map.put("username", PreferencesUtil.getDataString(PreferencesUtil.KEY_LOGIN_NAME));

        // 获取保存加密时的salt
        String saltString = PreferencesUtil.getDataString(PreferencesUtil.KEY_SALT);

        // 获取本地加密后的密码
        String encryptPsw = PreferencesUtil.getDataString(PreferencesUtil.KEY_LOGIN_PSW);
        // 解密
        try {
            byte[] salt = saltString.getBytes("ISO-8859-1");
            String password = PasswordUtil.decrypt(encryptPsw, key, salt);
            map.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * 移除账号密码
     */
    public void removeLoginInfo() {
        PreferencesUtil.removeDataString(PreferencesUtil.KEY_LOGIN_NAME);
        PreferencesUtil.removeDataString(PreferencesUtil.KEY_LOGIN_PSW);
    }

    /**
     * 清除本地登录信息
     *
     * @return
     */
    public boolean logoutUserInfo() {
        instance.setAutoLoginStatus(false);
        instance.setLoginStatus(false);
        removeLoginInfo();
        PreferencesUtil.removeDataString(PreferencesUtil.KEY_POLICY_ITEM_STATUS);
        return PreferencesUtil.removeUserInfo("userInfo");
    }

    /**
     * 获取最新用户信息
     *
     * @return
     */
    public UserInfo getLastUserInfo() {
        String str = PreferencesUtil.getUserInfo();
        if (str == null) {
            return null;
        }
        UserInfo userInfo = JSON.parseObject(str, UserInfo.class);
        return userInfo;
    }

    /**
     * 设置登录状态
     */
    public void setLoginStatus(boolean isLoginSuccess) {
        PreferencesUtil.setLoginStatus(isLoginSuccess);
        PreferencesUtil.setIsLogin(isLoginSuccess);
    }

    /**
     * 获取登录状态
     *
     * @return
     */
    public boolean getLoginStatus() {
        return PreferencesUtil.getLoginStatus();
    }

    /**
     * 设置是否勾选了自动登录
     *
     * @param isAutoLogin
     */
    public void setAutoLoginStatus(boolean isAutoLogin) {
        PreferencesUtil.setAutoLoginStatus(isAutoLogin);
    }

    /**
     * 查看是否勾选了自动登录
     *
     * @return
     */
    public boolean getAutoLoginStatus() {
        return PreferencesUtil.getAutoLoginStatus();
    }

}
