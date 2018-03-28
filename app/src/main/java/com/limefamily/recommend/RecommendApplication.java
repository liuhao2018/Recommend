package com.limefamily.recommend;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 *
 * @author liuhao
 * @date 2018/3/28
 */

public class RecommendApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
