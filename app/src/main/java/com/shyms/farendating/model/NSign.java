package com.shyms.farendating.model;


import com.shyms.farendating.utils.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by hks on 3/6/2017.
 */

public class NSign {

    private String sign;
    private String timestamp;
    private String companySigns = "chancheng123!@#";
    ;

    public NSign() {
        timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        sign = Util.getMD5(companySigns + timestamp).toLowerCase();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


}
