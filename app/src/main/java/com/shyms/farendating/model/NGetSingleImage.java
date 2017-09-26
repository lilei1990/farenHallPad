package com.shyms.farendating.model;

import java.io.Serializable;

/**
 * Created by hks on 2016/4/26.
 */

public class NGetSingleImage implements Serializable {
    public String image;
    public int width;
    public int height;
    public String GuId;
    public String Type;

    public void setImage(String image) {
        this.image = image;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setGuId(String guId) {
        GuId = guId;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getGuId() {
        return GuId;
    }

    public String getType() {
        return Type;
    }


}
