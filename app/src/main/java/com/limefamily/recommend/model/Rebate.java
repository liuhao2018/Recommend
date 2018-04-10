package com.limefamily.recommend.model;

/**
 * Created by liuhao on 2018/4/10.
 */

public class Rebate {
    private int id;
    private int type;
    private int object_id;
    private int because;
    private int amount;
    private int status;
    private int settlement_id;
    private int created_by;
    private int updated_by;
    private String created_at;
    private String updated_at;

    public Rebate() {
    }

    public Rebate(int id, int type, int object_id, int because, int amount, int status, int settlement_id, int created_by, int updated_by, String created_at, String updated_at) {
        this.id = id;
        this.type = type;
        this.object_id = object_id;
        this.because = because;
        this.amount = amount;
        this.status = status;
        this.settlement_id = settlement_id;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getObject_id() {
        return object_id;
    }

    public void setObject_id(int object_id) {
        this.object_id = object_id;
    }

    public int getBecause() {
        return because;
    }

    public void setBecause(int because) {
        this.because = because;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSettlement_id() {
        return settlement_id;
    }

    public void setSettlement_id(int settlement_id) {
        this.settlement_id = settlement_id;
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
        return "Rebate{" +
                "id=" + id +
                ", type=" + type +
                ", object_id=" + object_id +
                ", because=" + because +
                ", amount=" + amount +
                ", status=" + status +
                ", settlement_id=" + settlement_id +
                ", created_by=" + created_by +
                ", updated_by=" + updated_by +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
