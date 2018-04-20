package com.limefamily.recommend;

import android.app.Application;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.limefamily.recommend.util.SPUtil;

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

    private Retrofit retrofit;
    private static RecommendApplication instance;
    private final String API_HOST = "api.recommend.com";
    public static final String KEY_USER_TOKEN = "key_user_token";
    public static final String RECOMMEND_STORAGE_KIT = "recommend_storage_kit";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fresco.initialize(this);
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .client(generateClient())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static RecommendApplication getInstance() {
        return instance;
    }

    public String getToken() {
        return  SPUtil.getInstance(RECOMMEND_STORAGE_KIT).getString(KEY_USER_TOKEN,"");
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
