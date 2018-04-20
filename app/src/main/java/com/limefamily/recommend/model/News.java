package com.limefamily.recommend.model;

/**
 * Created by liuhao on 2018/3/29.
 */

public class News {
    private int news_id;
    private String news_verify;
    private String news_title;
    private String news_abstract;
    private String news_img;
    private int news_click;
    private long news_online_time;
    private int news_show;
    private long news_pub_time;
    private String news_content;

    public News() {
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public String getNews_verify() {
        return news_verify;
    }

    public void setNews_verify(String news_verify) {
        this.news_verify = news_verify;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_abstract() {
        return news_abstract;
    }

    public void setNews_abstract(String news_abstract) {
        this.news_abstract = news_abstract;
    }

    public String getNews_img() {
        return news_img;
    }

    public void setNews_img(String news_img) {
        this.news_img = news_img;
    }

    public int getNews_click() {
        return news_click;
    }

    public void setNews_click(int news_click) {
        this.news_click = news_click;
    }

    public long getNews_online_time() {
        return news_online_time;
    }

    public void setNews_online_time(long news_online_time) {
        this.news_online_time = news_online_time;
    }

    public int getNews_show() {
        return news_show;
    }

    public void setNews_show(int news_show) {
        this.news_show = news_show;
    }

    public long getNews_pub_time() {
        return news_pub_time;
    }

    public void setNews_pub_time(long news_pub_time) {
        this.news_pub_time = news_pub_time;
    }

    public String getNews_content() {
        return news_content;
    }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }

    @Override
    public String toString() {
        return "News{" +
                "news_id=" + news_id +
                ", news_verify='" + news_verify + '\'' +
                ", news_title='" + news_title + '\'' +
                ", news_abstract='" + news_abstract + '\'' +
                ", news_img='" + news_img + '\'' +
                ", news_click=" + news_click +
                ", news_online_time=" + news_online_time +
                ", news_show=" + news_show +
                ", news_pub_time=" + news_pub_time +
                ", news_content='" + news_content + '\'' +
                '}';
    }
}
