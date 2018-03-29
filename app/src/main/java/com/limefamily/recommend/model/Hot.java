package com.limefamily.recommend.model;

/**
 * Created by liuhao on 2018/3/29.
 */

public class Hot {
    private String cover;
    private String title;
    private String desc;
    private String date;

    public Hot() {
    }

    public Hot(String cover, String title, String desc, String date) {
        this.cover = cover;
        this.title = title;
        this.desc = desc;
        this.date = date;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
