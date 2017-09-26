package com.shyms.farendating.home.lien_up.model;

import java.io.Serializable;

/**
 * Created by Weimin on 6/28/2016.
 */
public class NAppointHistory implements Serializable {

    public final String userid;
    public final String mobile;
    public final String subNum;
    public final String submit_time;
    public final String sub_day;
    public final String type;

    public NAppointHistory() {
        userid=null;
        mobile=null;
        subNum=null;
        submit_time=null;
        sub_day=null;
        type = null;
    }

}
