package com.limefamily.recommend.restful;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by liuhao on 2018/4/10.
 */

public interface SiteService {

    @GET("/site/")
    Call<List> index(@Header("Authorization")String Authorization);
}
