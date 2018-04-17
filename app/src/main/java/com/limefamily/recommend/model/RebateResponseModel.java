package com.limefamily.recommend.model;

/**
 * Created by liuhao on 2018/4/17.
 */

public class RebateResponseModel {
    private String name;
    private int type;
    private String status;
    private int amount;

    public RebateResponseModel() {
    }

    public RebateResponseModel(String name, int type, String status, int amount) {
        this.name = name;
        this.type = type;
        this.status = status;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "RebateResponseModel{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", status='" + status + '\'' +
                ", amount=" + amount +
                '}';
    }
}
