package me.hokas.utils;

import android.content.SharedPreferences;

import me.hokas.base.BaseApplication;


/**
 * 获取参数工具
 *
 * @author hokas
 */
public class PreferencesUtil {

    private static final String PREFERENCES_NAME = "heTang";
    private static final String KEY_NETWORK_WIFI = "networkWifi";
    private static final String KEY_SESSION = "session";
    private static final String KEY_USER_INFO = "userInfo";
    private static final String KEY_AUTO_LOGIN = "isAutoLogin";
    private static final String KEY_LOGIN_STATUS = "isLoginSuccess";
    public static final String KEY_LOGIN_NAME = "loginName";
    public static final String KEY_LOGIN_PSW = "loginPsw";
    public static final String KEY_SALT = "salt";
    public static final String KEY_POLICY_ITEM_STATUS = "itemStatus";
    public static final String KEY_BLUETOOTH = "isBluetooth";
    public static final String KEY_BLUETOOTHS = "isBluetoothS";
    public static final String KEY_IS_LOGIN = "isLogin";
    public static final String KEY_SERVICE_ADDRESS = "service_address";

    private static SharedPreferences getDefaultSharedPreferences() {
        BaseApplication application = BaseApplication.getInstance();
        SharedPreferences sp;
        sp = application.getSharedPreferences(PREFERENCES_NAME, 0);
        return sp;
    }


    /**
     * 获取是否Wifi下显示图片
     */
    public static boolean getNetWorkWifi() {
        SharedPreferences sp = getDefaultSharedPreferences();
        return sp.getBoolean(KEY_NETWORK_WIFI, true);
    }

    /**
     * 设置是否Wifi下显示图片
     */
    public static void setNetWorkWifi(boolean netWorkWifi) {
        SharedPreferences sp = getDefaultSharedPreferences();
        sp.edit().putBoolean(KEY_NETWORK_WIFI, netWorkWifi).apply();
    }

    /**
     * 获取session
     */
    public static String getSession() {
        SharedPreferences sp = getDefaultSharedPreferences();
        return sp.getString(KEY_SESSION, "");
    }

    /**
     * 设置session
     */
    public static void setSession(String session) {
        SharedPreferences sp = getDefaultSharedPreferences();
        sp.edit().putString(KEY_SESSION, session).apply();
    }

    /**
     * 获取用户信息
     */
    public static String getUserInfo() {
        SharedPreferences sp = getDefaultSharedPreferences();
        return sp.getString(KEY_USER_INFO, null);
    }

    /**
     * 保存用户信息
     */
    public static void setUserInfo(String userInfo) {
        SharedPreferences sp = getDefaultSharedPreferences();
        sp.edit().putString(KEY_USER_INFO, userInfo).apply();
    }

    /**
     * 删除用户信息
     */
    public static boolean removeUserInfo(String key) {
        SharedPreferences sp = getDefaultSharedPreferences();
        return sp.edit().remove(key).commit();
    }

    /**
     * 保存自动登录状态
     */
    public static void setAutoLoginStatus(boolean isAutoLogin) {
        SharedPreferences sp = getDefaultSharedPreferences();
        sp.edit().putBoolean(KEY_AUTO_LOGIN, isAutoLogin).apply();
    }

    /**
     * 获取自动登录状态
     */
    public static boolean getAutoLoginStatus() {
        SharedPreferences sp = getDefaultSharedPreferences();
        return sp.getBoolean(KEY_AUTO_LOGIN, false);
    }

    /**
     * 保存自动登录状态
     */
    public static void setLoginStatus(boolean isLoginSuccess) {
        SharedPreferences sp = getDefaultSharedPreferences();
        sp.edit().putBoolean(KEY_LOGIN_STATUS, isLoginSuccess).apply();
    }

    /**
     * 获取自动登录状态
     */
    public static boolean getLoginStatus() {
        SharedPreferences sp = getDefaultSharedPreferences();
        return sp.getBoolean(KEY_LOGIN_STATUS, false);
    }

    /**
     * 获取字符串数据
     */
    public static String getDataString(String key) {
        SharedPreferences sp = getDefaultSharedPreferences();
        return sp.getString(key, null);
    }

    /**
     * 保存字符串数据
     */
    public static void setDataString(String key, String data) {
        SharedPreferences sp = getDefaultSharedPreferences();
        sp.edit().putString(key, data).apply();
    }

    /**
     * 删除字符串信息
     */
    public static boolean removeDataString(String key) {
        SharedPreferences sp = getDefaultSharedPreferences();
        return sp.edit().remove(key).commit();
    }

    /**
     * 获取Boolean数据
     */
    public static boolean getDataBoolean(String key) {
        SharedPreferences sp = getDefaultSharedPreferences();
        return sp.getBoolean(key, false);
    }

    /**
     * 保存Boolean数据
     */
    public static void setDataBoolean(String key, boolean status) {
        SharedPreferences sp = getDefaultSharedPreferences();
        sp.edit().putBoolean(key, status).apply();
    }

    /**
     * 保存蓝牙mac
     */
    public static void setBluetoothMac(String mac) {
        SharedPreferences sp = getDefaultSharedPreferences();
        sp.edit().putString(KEY_BLUETOOTH, mac).apply();
    }

    /**
     * 获取蓝牙Mac
     */
    public static String getBluetoothMac() {
        SharedPreferences sp = getDefaultSharedPreferences();
        return sp.getString(KEY_BLUETOOTH, null);
    }

    /**
     * 获取服务器地址
     */
    public static String getServiceAddress() {
        SharedPreferences sp = getDefaultSharedPreferences();
        return sp.getString(KEY_SERVICE_ADDRESS, "19.134.148.17");
    }

    /**
     * 设置服务器地址
     */
    public static void setServiceAddress(String key) {
        SharedPreferences sp = getDefaultSharedPreferences();
        sp.edit().putString(KEY_SERVICE_ADDRESS, key).apply();
    }

    /**
     * 设置蓝牙打印机是否连接
     */
    public static void setBluetooth(boolean flag) {
        SharedPreferences sp = getDefaultSharedPreferences();
        sp.edit().putBoolean(KEY_BLUETOOTHS, flag).apply();
    }

    /**
     * 获取蓝牙打印机连接
     */
    public static boolean getBluetooth() {
        SharedPreferences sp = getDefaultSharedPreferences();
        return sp.getBoolean(KEY_BLUETOOTHS, false);
    }

    //是否登陆
    public static void setIsLogin(boolean flag) {
        SharedPreferences sp = getDefaultSharedPreferences();
        sp.edit().putBoolean(KEY_IS_LOGIN, flag).apply();
    }

    //获取是否登陆
    public static boolean getIsLogin() {
        SharedPreferences sp = getDefaultSharedPreferences();
        return sp.getBoolean(KEY_IS_LOGIN, false);
    }
}
