package com.limefamily.recommend.net;

import com.limefamily.recommend.model.HomeResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 *
 * @author liuhao
 * @date 2018/3/29
 */

public interface HomeService {
    @GET("/news")
    Call<HomeResponse> fetchHome();
}
