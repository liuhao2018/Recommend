package com.limefamily.recommend.model;

/**
 * Created by liuhao on 2018/4/10.
 */

public class ChangePasswordModel {
    private String username;
    private String old_password;
    private String new_password;

    public ChangePasswordModel() {
    }

    public ChangePasswordModel(String username, String old_password, String new_password) {
        this.username = username;
        this.old_password = old_password;
        this.new_password = new_password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    @Override
    public String toString() {
        return "ChangePasswordModel{" +
                "username='" + username + '\'' +
                ", old_password='" + old_password + '\'' +
                ", new_password='" + new_password + '\'' +
                '}';
    }
}
