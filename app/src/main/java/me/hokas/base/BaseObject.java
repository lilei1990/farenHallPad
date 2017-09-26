package me.hokas.base;

/**
 * 基础对象
 * Created by Administrator on 2015/7/3.
 */
public class BaseObject {

    private String code;
    private String data;
    private String meta;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseObject{" +
                "code='" + code + '\'' +
                ", data='" + data + '\'' +
                ", meta='" + meta + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
