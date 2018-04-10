package com.limefamily.recommend.model;

/**
 * Created by liuhao on 2018/4/10.
 */

public class Income {
    private int count;
    private int amount;

    public Income() {
    }

    public Income(int count, int amount) {
        this.count = count;
        this.amount = amount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Income{" +
                "count=" + count +
                ", amount=" + amount +
                '}';
    }
}
