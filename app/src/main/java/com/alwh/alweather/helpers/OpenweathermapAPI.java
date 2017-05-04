package com.alwh.alweather.helpers;

/**
 * Created by abelov on 04.05.2017.
 */

import com.alwh.alweather.json.JSONCurrentWeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public interface OpenweathermapAPI {

    @GET("addr/{addr}/")
    Call<JSONCurrentWeatherData> getCurrentWeather(@Path("name") String userName, @Query("access_token") String access_token);

}
