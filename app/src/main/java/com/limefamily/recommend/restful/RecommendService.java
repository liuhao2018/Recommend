package com.limefamily.recommend.restful;

import com.limefamily.recommend.model.Attendant;
import com.limefamily.recommend.model.Customer;
import com.limefamily.recommend.model.Status;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by liuhao on 2018/4/10.
 */

public interface RecommendService {
    @POST("/recommend/create-customer")
    Call<Customer> createCustomer(@Header("Authorization") String authorization,@Body Customer customer);

    @POST("/recommend/create-attendant")
    Call<Attendant> createAttendant(@Header("Authorization") String authorization,@Body Attendant attendant);

    @POST("/recommend/update-customer")
    Call<Customer> updateCustomer(@Header("Authorization") String authorization,@Body Customer customer);

    @POST("/recommend/update-attendant")
    Call<Attendant> updateAttendant(@Header("Authorization") String authorization,@Body Attendant attendant);

    @GET("/recommend/customer-list")
    Call<List<Customer>> customerList(@Header("Authorization") String authorization,@Query("page") int page,@Query("page_size") int page_size,@Query("status") int status,@Query("from_time") String from_time);

    @GET("/recommend/attendant-list")
    Call<List<Attendant>> attendantList(@Header("Authorization") String authorization,@Query("page") int page,@Query("page_size") int page_size,@Query("status") int status,@Query("from_time") String from_time);

    @GET("/recommend/status-list")
    Call<List<Status>> statusList(@Header("Authorization") String authorization,@Query("type") int type);

}
