package com.limefamily.recommend.restful;

import com.limefamily.recommend.model.Income;
import com.limefamily.recommend.model.Rebate;
import com.limefamily.recommend.model.RebateResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by liuhao on 2018/4/10.
 */

public interface RebateService {

    @GET("/rebate/income")
    Call<Income> income(@Header("Authorization") String authorization,@Query("from_time") String from_time);

    @GET("/rebate/rebate-list")
    Call<List<RebateResponseModel>> rebateList(@Header("Authorization") String authorization, @Query("page") int page, @Query("page_size") int page_size, @Query("from_time") String from_time, @Query("type") int type);
}
