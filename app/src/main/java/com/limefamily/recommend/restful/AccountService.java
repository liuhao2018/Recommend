package com.limefamily.recommend.restful;

import com.limefamily.recommend.model.ChangePasswordModel;
import com.limefamily.recommend.model.LoginModel;
import com.limefamily.recommend.model.LoginResponse;
import com.limefamily.recommend.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by liuhao on 2018/4/10.
 */

public interface AccountService {
    @POST("/account/login")
    Call<LoginResponse> login(@Body LoginModel loginModel);

    @POST("/account/change-password")
    Call<Void> changePassword(@Header("Authorization") String authorization,@Body ChangePasswordModel changePasswordModel);
}
