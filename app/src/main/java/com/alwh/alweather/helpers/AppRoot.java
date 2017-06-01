package com.alwh.alweather.helpers;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.alwh.alweather.database.SQLiteAlWeatherConfig;


import com.alwh.alweather.database.SQLiteForecastData;
import com.alwh.alweather.database.SQLiteForecastItem;
import com.alwh.alweather.database.SQLiteWeatherData;
import com.alwh.alweather.service.AlWeatherService;
import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by abelov on 04.05.2017.
 */

//public class AppRoot extends Application {

public class AppRoot extends Application {


    public static String API_KEY = "dda74e030c23ee9017cf9a8f897f19cf";
    public static final String NEW_WEATHER = "com.alwh.alweather.action.NEW_WEATHER";
    public static final String QUESTION_TO_SERVECE = "key";
    public static final String WEATHER = "weather";
    public static final String FORECAST = "forecast";
    public static final String LOCATION_ERROR_MESSAGE = "Input field must be filled";
    public static final int TRANSFER_NEW_WEATHER = 1;
    public static final int TRANSFER_SAVE_WEATHER = 2;
    public static final int TRANSFER_NEW_FORECAST = 3;
    public static final int TRANSFER_SAVE_FORECAST = 4;
    public static final int CHANGE_CITY = 5;
    public static final int PART_DAY = 8;
    public static final int MAX_CITY_COUNT = 22635;

    private static OpenweathermapAPI openweathermapAPI;



    final String TAG = "AlWeather/appRoot: ";
    Intent intent;

    private Retrofit retrofit;


    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        openweathermapAPI = retrofit.create(OpenweathermapAPI.class);

        checkFirstStart();

        intent = new Intent(this, AlWeatherService.class);
        startService(intent);

 }



    public static OpenweathermapAPI getApi() {
        return openweathermapAPI;
    }

    public void checkFirstStart() {

        if ((SQLiteAlWeatherConfig.findById(SQLiteAlWeatherConfig.class, 1)) == null) {
            firstStart();
        }



    }

    public void firstStart() {

        SQLiteAlWeatherConfig sqLiteAlWeatherConfig = new SQLiteAlWeatherConfig("London", 300000, 3000000, 27.911619, -33.015289);
        sqLiteAlWeatherConfig.setId((long) 1);
        sqLiteAlWeatherConfig.save();

        SQLiteWeatherData sqLiteWeatherData = new SQLiteWeatherData();
        sqLiteWeatherData.setCityName("London");
        sqLiteWeatherData.setCountry("GB");
        sqLiteWeatherData.setCoordLon(27.911619);
        sqLiteWeatherData.setCoordLat(-33.015289);
        sqLiteWeatherData.setWeatherMain("rain");
        sqLiteWeatherData.setWeatherID(200);
        sqLiteWeatherData.setWeatherDescription("show");
        sqLiteWeatherData.setWeatherIcon("01d");
        sqLiteWeatherData.setTemperature(12);
        sqLiteWeatherData.setPressure(1020);
        sqLiteWeatherData.setHumidity(65);
        sqLiteWeatherData.setWindSpeed(2);
        sqLiteWeatherData.setId((long)1);
        sqLiteWeatherData.setWindDeg(0);
        sqLiteWeatherData.setRain(0);
        sqLiteWeatherData.setSnow(0);
        sqLiteWeatherData.setDt(new Date());
        sqLiteWeatherData.setSunrise(1232132133);
        sqLiteWeatherData.setSunset(123213213);

        sqLiteWeatherData.setId((long) 1);
        sqLiteWeatherData.save();


        List<SQLiteForecastItem> forecast = new ArrayList<>();

        SQLiteForecastItem sqLiteForecastItem = new SQLiteForecastItem();

        for (int i = 0; i < 9; i++) {
            sqLiteForecastItem.setCityName("London");
            sqLiteForecastItem.setCountry("GB");
            sqLiteForecastItem.setCoordLon(27.911619);
            sqLiteForecastItem.setCoordLat(27.911619);
            sqLiteForecastItem.setWeatherID(200);
            sqLiteForecastItem.setWeatherMain("rain");
            sqLiteForecastItem.setWeatherDescription("show");
            sqLiteForecastItem.setWeatherIcon("01d");
            sqLiteForecastItem.setTemperature(12);
            sqLiteForecastItem.setPressure(1054);
            sqLiteForecastItem.setHumidity(44);
            sqLiteForecastItem.setWindSpeed(2);
            sqLiteForecastItem.setWindDeg(0);
            sqLiteForecastItem.setClouds(0);
            sqLiteForecastItem.setRain(0);
            sqLiteForecastItem.setSnow(0);
            sqLiteForecastItem.setDt(new Date());
            sqLiteForecastItem.setId((long)i);

            sqLiteForecastItem.save();
            forecast.add(sqLiteForecastItem);

        }
        SQLiteForecastData sqLiteForecastData = new SQLiteForecastData(forecast);
        sqLiteForecastData.save();
    }
}
