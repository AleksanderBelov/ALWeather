package com.alwh.alweather.database;

import com.orm.SugarRecord;

/**
 * Created by abelov on 10.05.2017.
 */

public class SQLiteAlWeatherConfig extends SugarRecord{
    String city;
    int intervalWeather;
    int intervalForecast;

    public SQLiteAlWeatherConfig() {
    }

    public SQLiteAlWeatherConfig(String city, int intervalWeather) {
        this.city = city;
        this.intervalWeather = intervalWeather;
    }

    public SQLiteAlWeatherConfig(String city) {

        this.city = city;
    }

    public SQLiteAlWeatherConfig(String city, int intervalWeather, int intervalForecast) {
        this.city = city;
        this.intervalWeather = intervalWeather;
        this.intervalForecast = intervalForecast;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getIntervalWeather() {
        return intervalWeather;
    }

    public void setIntervalWeather(int intervalWeather) {
        this.intervalWeather = intervalWeather;
    }

    public int getIntervalForecast() {
        return intervalForecast;
    }

    public void setIntervalForecast(int intervalForecast) {
        this.intervalForecast = intervalForecast;
    }
}
