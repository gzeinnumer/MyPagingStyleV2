package com.gzeinnumer.mypagingstylev2.data;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
//    public static String base_url = "https://demo-laravel.gzeinnumer.com/api/";
    public static String base_url = "http://192.168.1.10:8000/api/";

    private static Retrofit setInit() {
        TokenInterceptor tokenInterceptor = new TokenInterceptor();
        OkHttpClient httpClient = tokenInterceptor.getClient();

        return new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build();
    }

    public static ApiService getInstance() {
        return setInit().create(ApiService.class);
    }
}
