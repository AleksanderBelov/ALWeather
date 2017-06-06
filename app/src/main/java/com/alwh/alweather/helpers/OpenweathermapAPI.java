package com.alwh.alweather.helpers;

/**
 * Created by abelov on 04.05.2017.
 */

import com.alwh.alweather.json.forecast.JSONForecastData;
import com.alwh.alweather.json.weather.JSONWeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenweathermapAPI {

    @GET("forecast")
    Call<JSONForecastData> getForecast(@Query("q") String locate, @Query("mode") String mode, @Query("units") String units, @Query("APPID") String APIKey);

    @GET("weather")
    Call<JSONWeatherData> getWeather(@Query("q") String locate, @Query("mode") String mode, @Query("units") String units, @Query("APPID") String APIKey);

}
