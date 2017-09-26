package com.shyms.farendating.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hks on 2016/4/25.
 */
public class NMultipleImage implements Serializable {
    final public List<NDetailImage> thumbs;
    final public List files;

    public NMultipleImage() {
        thumbs = null;
        files = null;
    }
}
