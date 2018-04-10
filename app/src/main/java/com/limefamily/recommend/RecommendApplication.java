package com.limefamily.recommend;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @author liuhao
 * @date 2018/3/28
 */

public class RecommendApplication extends Application {

    private static Retrofit retrofit;

    private final String API_HOST = "http://api.recommend.com";

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
                .client(generateClient())
                .build();
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    /**
     * 添加用于测试的公共请求头
     * @return
     */
    private OkHttpClient generateClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Host", API_HOST)
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();
        return httpClient;
    }
}
