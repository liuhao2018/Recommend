package com.limefamily.recommend.model;

import java.util.List;

/**
 * Created by liuhao on 2018/4/10.
 */

public class Company {
    private int id;
    private int parent_id;
    private String name;
    private String description;
    private String serial_number;
    private String tel;
    private String business_license;
    private int address_id;
    private int owner;
    private int type;
    private String remark;
    private List<String> ext_attr;
    private int created_by;
    private int updated_by;
    private String created_at;
    private String updated_at;

    public Company() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBusiness_license() {
        return business_license;
    }

    public void setBusiness_license(String business_license) {
        this.business_license = business_license;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getExt_attr() {
        return ext_attr;
    }

    public void setExt_attr(List<String> ext_attr) {
        this.ext_attr = ext_attr;
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

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", parent_id=" + parent_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", serial_number='" + serial_number + '\'' +
                ", tel='" + tel + '\'' +
                ", business_license='" + business_license + '\'' +
                ", address_id=" + address_id +
                ", owner=" + owner +
                ", type=" + type +
                ", remark='" + remark + '\'' +
                ", ext_attr=" + ext_attr +
                ", created_by=" + created_by +
                ", updated_by=" + updated_by +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
