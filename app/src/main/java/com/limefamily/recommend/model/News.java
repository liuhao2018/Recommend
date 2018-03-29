package com.limefamily.recommend.model;

/**
 * Created by liuhao on 2018/3/29.
 */

public class News {

    private String cover;
    private String title;
    private String date;

    public News() {
    }

    public News(String cover, String title, String date) {
        this.cover = cover;
        this.title = title;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
