package com.limefamily.recommend.model;

import java.util.List;

/**
 * Created by liuhao on 2018/4/10.
 */

public class User {
    private int id;
    private String serial_number;
    private String name;
    private int sex;
    private String id_number;
    private String mobile;
    private String email;
    private String avatar;
    private int address_id;
    private int active;
    private String remark;
    private int created_by;
    private int updated_by;
    private String created_at;
    private String updated_at;
    private List<String> ext_attr;
    private String token;

    public User() {
    }

    public User(int id, String serial_number, String name, int sex, String id_number, String mobile, String email, String avatar, int address_id, int active, String remark, int created_by, int updated_by, String created_at, String updated_at, List<String> ext_attr,String token) {
        this.id = id;
        this.serial_number = serial_number;
        this.name = name;
        this.sex = sex;
        this.id_number = id_number;
        this.mobile = mobile;
        this.email = email;
        this.avatar = avatar;
        this.address_id = address_id;
        this.active = active;
        this.remark = remark;
        this.created_by = created_by;
        this.updated_by = updated_by;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.ext_attr = ext_attr;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public int getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(int updated_by) {
        this.updated_by = updated_by;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public List<String> getExt_attr() {
        return ext_attr;
    }

    public void setExt_attr(List<String> ext_attr) {
        this.ext_attr = ext_attr;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", serial_number='" + serial_number + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", id_number='" + id_number + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", address_id=" + address_id +
                ", active=" + active +
                ", remark='" + remark + '\'' +
                ", created_by=" + created_by +
                ", updated_by=" + updated_by +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", ext_attr=" + ext_attr +
                ", token='" + token + '\'' +
                '}';
    }
}
