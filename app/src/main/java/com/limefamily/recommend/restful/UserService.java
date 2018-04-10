package com.limefamily.recommend.restful;

import com.limefamily.recommend.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by liuhao on 2018/4/10.
 */

public interface UserService {

    @POST("/user/view")
    @FormUrlEncoded
    Call<User> view(@Header("Authorization") String authorization,@Field("id") int id);
}