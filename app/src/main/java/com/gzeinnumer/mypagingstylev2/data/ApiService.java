package com.gzeinnumer.mypagingstylev2.data;

import com.gzeinnumer.mypagingstylev2.base.BaseListResponse;
import com.gzeinnumer.mypagingstylev2.model.ProductResponse;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("product/paging")
    Flowable<Response<BaseListResponse<ProductResponse>>> getProduct(
            @Query("limit") int limit,
            @Query("page") int page,
            @Query("start_date") String start_date,
            @Query("end_date") String end_date
    );
}
