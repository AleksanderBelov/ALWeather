package com.alwh.alweather.model;

import android.util.Log;

import com.alwh.alweather.database.SQLiteForecastData;
import com.alwh.alweather.database.SQLiteWeatherData;
import com.alwh.alweather.service.AlWeatherService;

/**
 * Created by Admin on 14.05.2017.
 */

public class Trasfer {
    final String TAG = "AlWeather/Transfer ";

    private AlWeatherService alWeatherService;

    public Trasfer(AlWeatherService alWeatherService) {
        this.alWeatherService = alWeatherService;
    }

    public Trasfer() {
    }

    public SQLiteWeatherData getWeather(boolean renew) { // false: from DB, true: from site (online)
        Log.d(TAG, "getWeather start");
        return this.alWeatherService.TransferWeather(renew);
    }
    public SQLiteWeatherData getWeather(AlWeatherService alWeatherService, boolean renew) { // false: from DB, true: from site (online)
        Log.d(TAG, "getWeather start");
        return alWeatherService.TransferWeather(renew);
    }

    public SQLiteForecastData getForecast(boolean renew) { // false: from DB, true: from site (online)
        return alWeatherService.TransferForecast(renew);

    }
}
