package com.shyms.farendating.home.lien_up.model;

/**
 * 放弃号码（窗口、pad）
 * Created by hks on 2016/3/25.
 */
public class UserGiveUp {

    /**
     * code : 0
     * data : 0
     * message : null
     * meta : null
     */

    private String code;
    private int data;
    private Object message;
    private Object meta;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
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

    @Override
    public String toString() {
        return "UserGiveUp{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", message=" + message +
                ", meta=" + meta +
                '}';
    }
}
