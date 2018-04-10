package com.limefamily.recommend.model;

/**
 * Created by liuhao on 2018/4/10.
 */

public class Status {
    private int id;
    private int type;
    private int object_id;
    private int from;
    private int to;
    private int created_by;
    private String created_at;

    public Status() {
    }

    public Status(int id, int type, int object_id, int from, int to, int created_by, String created_at) {
        this.id = id;
        this.type = type;
        this.object_id = object_id;
        this.from = from;
        this.to = to;
        this.created_by = created_by;
        this.created_at = created_at;
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

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public String getCreated_at() {
        return created_at;
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", type=" + type +
                ", object_id=" + object_id +
                ", from=" + from +
                ", to=" + to +
                ", created_by=" + created_by +
                ", created_at='" + created_at + '\'' +
                '}';
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
