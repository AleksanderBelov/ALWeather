package com.alwh.alweather.service;

import com.alwh.alweather.database.SQLiteForecastData;
import com.alwh.alweather.database.SQLiteWeatherData;
import com.alwh.alweather.helpers.AppRoot;
import com.alwh.alweather.json.forecast.JSONForecastData;
import com.alwh.alweather.json.weather.JSONWeatherData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadAllDataFromSite {

    private SQLiteForecastData sqLiteForecastData;
    private SQLiteWeatherData sqLiteWeatherData;

    public SQLiteWeatherData getWeatherFromSite(String sity) {

        AppRoot.getApi().getWeather(sity, "json", "metric", AppRoot.API_KEY).enqueue(new Callback<JSONWeatherData>() {
            @Override
            public void onResponse(Call<JSONWeatherData> call, Response<JSONWeatherData> response) {
                if (!(response.body() == null)) {
                    sqLiteWeatherData = new SQLiteWeatherData(response.body());
                    sqLiteWeatherData.save();
                }
            }

            @Override
            public void onFailure(Call<JSONWeatherData> call, Throwable t) {
            }
        });
        return sqLiteWeatherData;
    }

    public SQLiteForecastData getForecastFromSite(String sity) {
        AppRoot.getApi().getForecast(sity, "json", "metric", AppRoot.API_KEY).enqueue(new Callback<JSONForecastData>() {
            @Override
            public void onResponse(Call<JSONForecastData> call, Response<JSONForecastData> response) {
                if (!(response.body() == null)) {
                    sqLiteForecastData = new SQLiteForecastData(response.body());
                    sqLiteForecastData.save();
                }
            }

            @Override
            public void onFailure(Call<JSONForecastData> call, Throwable t) {
            }
        });
        return sqLiteForecastData;
    }
}