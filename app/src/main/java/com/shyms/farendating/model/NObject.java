package com.shyms.farendating.model;

import java.io.Serializable;

/**
 * Created by Weimin on 6/6/2016.
 */
public class NObject<T> implements Serializable {
    public final String code;
    public final T data;
    public final String meta;
    public final String message;

    public NObject() {
        code = null;
        data = null;
        meta = null;
        message = null;
    }

    public String getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMeta() {
        return meta;
    }

    public String getMessage() {
        return message;
    }
}
