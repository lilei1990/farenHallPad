package com.shyms.farendating.model;

import java.io.Serializable;

/**
 * Created by hks on 2016/4/25.
 */
public class NDetailImage implements Serializable {
    public String img;       //图片的Base64编码
    public String name;      //文件名
    public String id;       //图片的id
    public String path_ext;      //路径

    public NDetailImage() {
        img = null;
        name = null;
        id = null;
        path_ext = null;
    }

    public String getImg(String Nimg) {
        img = Nimg;
        return img;
    }

    ;

    public String getName(String Name) {
        name = Name;
        return name;
    }

    ;

    public String getId(String Id) {
        id = Id;
        return id;
    }

    ;

    public String getPath_ext(String Path_ext) {
        path_ext = Path_ext;
        return path_ext;
    }

    ;
}
