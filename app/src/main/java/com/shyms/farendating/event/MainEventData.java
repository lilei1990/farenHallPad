package com.shyms.farendating.event;

/**
 * Created by Weimin on 4/15/2016.
 */
public class MainEventData {

    public String message;
    public int position;
    public int id;

    public MainEventData(int id) {
        this.id = id;
    }

    public MainEventData(int id, int position) {
        this.id = id;
        this.position = position;
    }

}
