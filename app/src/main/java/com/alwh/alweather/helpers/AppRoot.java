package com.alwh.alweather.helpers;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Context;
import com.orm.SugarApp;
import com.orm.SugarContext;

/**
 * Created by abelov on 04.05.2017.
 */

//public class AppRoot extends Application {

public class AppRoot extends Application {


    public static String API_KEY = "dda74e030c23ee9017cf9a8f897f19cf";
    private static OpenweathermapAPI openweathermapAPI;

    private Retrofit retrofit;


    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);

        retrofit = new Retrofit.Builder()
      //          .baseUrl("http://api.openweathermap.org/data/2.5/forecast?q=London&mode=json&APPID=dda74e030c23ee9017cf9a8f897f19cf") //Базовая часть адреса
                .baseUrl("http://api.openweathermap.org/data/2.5/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        openweathermapAPI = retrofit.create(OpenweathermapAPI.class); //Создаем объект, при помощи которого будем выполнять запросы
    }

    public static OpenweathermapAPI getApi() {
        return openweathermapAPI;
    }
}
