package com.alwh.alweather.service;

import com.alwh.alweather.database.SQLiteWeatherData;

/**
 * Created by Admin on 09.05.2017.
 */

public class PutData {

    public SQLiteWeatherData putWeather(){
        SQLiteWeatherData sqLiteWeatherData = SQLiteWeatherData.findById(SQLiteWeatherData.class,1);
        return sqLiteWeatherData;
    }
}
