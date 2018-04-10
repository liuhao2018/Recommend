package com.limefamily.recommend.restful;

import com.limefamily.recommend.model.HomeResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 *
 * @author liuhao
 * @date 2018/3/29
 */

public interface NewsService {
    @GET("/news/")
    Call<HomeResponse> fetchHome();
}
