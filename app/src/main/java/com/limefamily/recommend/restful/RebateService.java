package com.limefamily.recommend.restful;

import com.limefamily.recommend.model.Income;
import com.limefamily.recommend.model.Rebate;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by liuhao on 2018/4/10.
 */

public interface RebateService {

    @GET("/rebate/income")
    Call<Income> income(@Header("Authorization") String authorization,@Query("status") int status, @Query("type") int type);

    Call<List<Rebate>> rebateList(@Header("Authorization") String authorization,@Query("status") int status,@Query("type") int type);
}
