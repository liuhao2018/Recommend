package com.limefamily.recommend.logic;

/**
 * Created by liuhao on 2018/4/16.
 */

public class FragmentPageChangeEvent {
    private int position;

    public FragmentPageChangeEvent() {
    }

    public FragmentPageChangeEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
