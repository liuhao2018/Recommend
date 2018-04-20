package com.limefamily.recommend.model;

import java.util.List;

/**
 *
 * @author liuhao
 * @date 2018/3/29
 */

public class HomeResponse {
    private List<News> items;

    public HomeResponse() {
    }

    public HomeResponse(List<News> items) {
        this.items = items;
    }

    public List<News> getItems() {
        return items;
    }

    public void setItems(List<News> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "HomeResponse{" +
                "items=" + items +
                '}';
    }
}
