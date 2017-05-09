package com.alwh.alweather.service;

import android.util.Log;

import com.alwh.alweather.database.SQLiteWeatherData;
import com.alwh.alweather.helpers.AppRoot;
import com.alwh.alweather.json.forecast.JSONForecastData;
import com.alwh.alweather.json.weather.JSONWeatherData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 08.05.2017.
 */

public class LoadDate {

    final String TAG = "AlWeather/LoadDate: ";

  //  JSONWeatherData jsonWeatherData;
    JSONForecastData jsonForecastData;
    SQLiteWeatherData sqLiteWeatherData;

    public SQLiteWeatherData getWeatherFromSite() {
  //      jsonWeatherData = new JSONWeatherData();
        jsonForecastData = new JSONForecastData();

        Log.d(TAG,"getWeather");
        AppRoot.getApi().getWeather("Kiev","json","metric",AppRoot.API_KEY).enqueue(new Callback<JSONWeatherData>() {
            @Override
            public void onResponse(Call<JSONWeatherData> call, Response<JSONWeatherData> response) {
                if (!(response.body() == null)) {
                    sqLiteWeatherData = new SQLiteWeatherData(response.body());
                    Log.d(TAG,"save to SQLite");
                    sqLiteWeatherData.save();

                    //         test();
                } else {
                    Log.d(TAG, "response.body() =- NULL");
                }
            }
            @Override
            public void onFailure(Call<JSONWeatherData> call, Throwable t) {
                Log.d(TAG,t.toString());
            }
        });

        return sqLiteWeatherData;

    }

    public void getForecast() {

        Log.d(TAG,"getForecast");
        AppRoot.getApi().getForecast("Kiev","json","metric",AppRoot.API_KEY).enqueue(new Callback<JSONForecastData>() {
            @Override
            public void onResponse(Call<JSONForecastData> call, Response<JSONForecastData> response) {
                if (!(response.body() == null)) {
                    jsonForecastData = response.body();
                } else {
                    Log.d(TAG, "response.body() =- NULL");
                }
            }
            @Override
            public void onFailure(Call<JSONForecastData> call, Throwable t) {
                Log.d(TAG,t.toString());
            }
        });
    }
}
