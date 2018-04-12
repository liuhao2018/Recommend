package com.limefamily.recommend.restful;

import com.limefamily.recommend.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by liuhao on 2018/4/10.
 */

public interface UserService {

    @GET("/user/view")
    Call<User> view(@Header("Authorization") String authorization);
}
