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
    @GET("forecast")
    Call<JSONCurrentWeatherData> getCurrentWeather(@Query("q") String locate, @Query("mode") String mode,@Query("units") String units,@Query("APPID") String APIKey);
//    Call<JSONCurrentWeatherData> getCurrentWeather();

}
