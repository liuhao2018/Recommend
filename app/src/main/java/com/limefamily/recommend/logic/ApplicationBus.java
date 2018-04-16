package com.limefamily.recommend.logic;

import com.squareup.otto.Bus;

/**
 * Created by liuhao on 2018/4/16.
 */

public class ApplicationBus extends Bus {

    private static ApplicationBus instance;

    public static ApplicationBus getInstance() {
        if (instance == null) {
            instance = new ApplicationBus();
        }
        return instance;
    }
}
