package com.alwh.alweather.service;

import android.util.Log;

import com.alwh.alweather.database.SQLiteAlWeatherConfig;
import com.alwh.alweather.database.SQLiteForecastData;
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

public class LoadAllDataFromSite {

    final String TAG = "LoadAllDataFromSite ";

    SQLiteForecastData sqLiteForecastData;
    SQLiteWeatherData sqLiteWeatherData;

    public SQLiteWeatherData getWeatherFromSite(String sity) {

        Log.d(TAG,"getWeather");
        AppRoot.getApi().getWeather(sity,"json","metric",AppRoot.API_KEY).enqueue(new Callback<JSONWeatherData>() {
            @Override
            public void onResponse(Call<JSONWeatherData> call, Response<JSONWeatherData> response) {
                if (!(response.body() == null)) {
                    sqLiteWeatherData = new SQLiteWeatherData(response.body());
                    Log.d(TAG,"(getWeather) save to SQLite");
                    sqLiteWeatherData.save();
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

    public SQLiteForecastData getForecastFromSite(String sity) {

        Log.d(TAG,"getForecast");
        AppRoot.getApi().getForecast(sity,"json","metric",AppRoot.API_KEY).enqueue(new Callback<JSONForecastData>() {
            @Override
            public void onResponse(Call<JSONForecastData> call, Response<JSONForecastData> response) {
                if (!(response.body() == null)) {
                    sqLiteForecastData = new SQLiteForecastData(response.body());
                    Log.d(TAG,"(getForecast)  save to SQLite");
                    sqLiteForecastData.save();
                } else {
                    Log.d(TAG, "response.body() =- NULL");
                }
            }
            @Override
            public void onFailure(Call<JSONForecastData> call, Throwable t) {
                Log.d(TAG,t.toString());
            }
        });
        return sqLiteForecastData;
    }
}
