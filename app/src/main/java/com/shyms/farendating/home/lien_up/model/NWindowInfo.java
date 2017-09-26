package com.shyms.farendating.home.lien_up.model;

import java.io.Serializable;

/**
 * Created by Weimin on 4/18/2016.
 */
public class NWindowInfo implements Serializable {

    final public String name;
    final public String description;
    final public String sort;
    final public String prefix;
    final public String system;//
    final public String open;

    public NWindowInfo() {
        name = null;
        description = null;
        sort = null;
        prefix = null;
        system = null;
        open = null;
    }
}
