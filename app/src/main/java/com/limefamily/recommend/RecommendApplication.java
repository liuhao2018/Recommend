package com.limefamily.recommend;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @author liuhao
 * @date 2018/3/28
 */

public class RecommendApplication extends Application {

    private static Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);

        initRetrofit();
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
