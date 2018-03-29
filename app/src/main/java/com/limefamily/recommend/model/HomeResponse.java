package com.limefamily.recommend.model;

import java.util.List;

/**
 *
 * @author liuhao
 * @date 2018/3/29
 */

public class HomeResponse {
    private List<News> news;
    private List<Hot> hot;

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public List<Hot> getHot() {
        return hot;
    }

    public void setHot(List<Hot> hot) {
        this.hot = hot;
    }
}
