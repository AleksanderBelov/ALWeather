package com.alwh.alweather.model;

import com.alwh.alweather.database.SQLiteForecastData;
import com.alwh.alweather.database.SQLiteWeatherData;

/**
 * Created by abelov on 22.05.2017.
 */

public class MessageEvent {
    public SQLiteWeatherData sqLiteWeatherData;
    public SQLiteForecastData sqLiteForecastData;
    public int type;

    public MessageEvent(SQLiteWeatherData sqLiteWeatherData, SQLiteForecastData sqLiteForecastData) {
        this.sqLiteWeatherData = sqLiteWeatherData;
        this.sqLiteForecastData = sqLiteForecastData;
    }
    public MessageEvent(SQLiteWeatherData sqLiteWeatherData) {
        this.sqLiteWeatherData = sqLiteWeatherData;
        this.type = 1;
    }

    public MessageEvent(SQLiteForecastData sqLiteForecastData) {
        this.sqLiteForecastData = sqLiteForecastData;
        this.type = 2;
    }
}
