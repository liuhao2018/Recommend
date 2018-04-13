package com.limefamily.recommend.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuhao on 2018/4/10.
 */

public class Customer implements Serializable {
    private int id;
    private String name;
    private int sex;
    private String birthday;
    private String mobile;
    private String id_number;
    private String serial_number;
    private int address_id;
    private String contact;
    private String contact_relation;
    private String intention;
    private int introduced_by;
    private String status;
    private String deal_time;
    private String remark;
    private List<String> ext_attr;
    private int created_by;
    private int updated_by;
    private String created_at;
    private String updated_at;

    public Customer() {
    }

    public Customer(String name,int sex,String birthday, String contact,String mobile, String contact_relation, String intention) {
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.mobile = mobile;
        this.contact = contact;
        this.contact_relation = contact_relation;
        this.intention = intention;
    }

    public Customer(int id, String name, int sex, String birthday, String mobile, String id_number, String serial_number, int address_id, String contact, String contact_relation, String intention, int introduced_by, String status, String deal_time, String remark, List<String> ext_attr, int created_by, int updated_by, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.mobile = mobile;
        this.id_number = id_number;
        this.serial_number = serial_number;
        this.address_id = address_id;
        this.contact = contact;
        this.contact_relation = contact_relation;
        this.intention = intention;
        this.introduced_by = introduced_by;
        this.status = status;
        this.deal_time = deal_time;
        this.remark = remark;
        this.ext_attr = ext_attr;
        this.created_by = created_by;
        this.updated_by = updated_by;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContact_relation() {
        return contact_relation;
    }

    public void setContact_relation(String contact_relation) {
        this.contact_relation = contact_relation;
    }

    public String getIntention() {
        return intention;
    }

    public void setIntention(String intention) {
        this.intention = intention;
    }

    public int getIntroduced_by() {
        return introduced_by;
    }

    public void setIntroduced_by(int introduced_by) {
        this.introduced_by = introduced_by;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeal_time() {
        return deal_time;
    }

    public void setDeal_time(String deal_time) {
        this.deal_time = deal_time;
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
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", birthday='" + birthday + '\'' +
                ", mobile='" + mobile + '\'' +
                ", id_number='" + id_number + '\'' +
                ", serial_number='" + serial_number + '\'' +
                ", address_id=" + address_id +
                ", contact='" + contact + '\'' +
                ", contact_relation='" + contact_relation + '\'' +
                ", intention='" + intention + '\'' +
                ", introduced_by=" + introduced_by +
                ", status=" + status +
                ", deal_time='" + deal_time + '\'' +
                ", remark='" + remark + '\'' +
                ", ext_attr=" + ext_attr +
                ", created_by=" + created_by +
                ", updated_by=" + updated_by +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
