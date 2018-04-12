package com.limefamily.recommend.model;

/**
 * Created by liuhao on 2018/4/12.
 */

public class LoginResponse {
    private int id;
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(int id, String token) {
        this.id = id;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "id=" + id +
                ", token='" + token + '\'' +
                '}';
    }
}
