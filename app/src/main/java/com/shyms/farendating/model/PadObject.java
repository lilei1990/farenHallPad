package com.shyms.farendating.model;

import java.io.Serializable;

/**
 * Created by Weimin on 6/6/2016.
 */
public class PadObject<T> implements Serializable {
    public final int code;
    public final T data;
    public final String msg;

    public PadObject() {
        code = -1;
        data = null;
        msg = null;
    }

}
