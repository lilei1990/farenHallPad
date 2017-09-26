package com.shyms.farendating.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Weimin on 6/6/2016.
 */
public class NObjectList<T> implements Serializable {
    public final String code;
    public final List<T> data;
    public final String meta;
    public final String message;

    public NObjectList() {
        code = null;
        data = null;
        meta = null;
        message = null;
    }

}
