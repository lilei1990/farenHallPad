package com.shyms.farendating.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import me.hokas.utils.SPUtil;

/**
 * Created by hks on 3/13/2017.
 */

public class Util {

    public static String getMD5(String tempStr) {
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(tempStr.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把没一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString().toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 动态生成新的URL 用于加密登录
     * username=12345678901&MD5(password) 用base64加密
     */
    public static String encodeBase64String(String username, String password) {
        String address = "username=" + username + "&password=" + getMD5(password);
        byte[] bytes = address.getBytes();
        return new String(org.apache.commons.codec.binary.Base64.encodeBase64(bytes));
    }

    public static  String getToken() {
        return (String) SPUtil.get(com.shyms.farendating.GlobalConstant.USER_TOKEN_VALID, "");
    }
}
