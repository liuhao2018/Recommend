package com.limefamily.recommend.restful;

import com.limefamily.recommend.model.HomeResponse;
import com.limefamily.recommend.model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 * @author liuhao
 * @date 2018/3/29
 */

public interface NewsService {
    @GET("/cusend/web/index.php")
    Call<HomeResponse> fetchLimeNews(@Query("r")String r,@Query("tag_id")int tag_id,@Query("area_id") int area);

    @GET("/cusend/web/index.php")
    Call<News> view(@Query("r") String r,@Query("id") int id);
}
