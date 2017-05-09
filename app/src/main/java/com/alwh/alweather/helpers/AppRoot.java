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

import com.alwh.alweather.database.SQLiteWeatherData;
import com.alwh.alweather.service.AlWeatherService;
import com.orm.SugarApp;
import com.orm.SugarContext;

/**
 * Created by abelov on 04.05.2017.
 */

//public class AppRoot extends Application {

public class AppRoot extends Application {


    public static String API_KEY = "dda74e030c23ee9017cf9a8f897f19cf";
    private static OpenweathermapAPI openweathermapAPI;

    final String TAG = "AlWeather/appRoot: ";
    Intent intent;
    boolean bound = false;
    ServiceConnection sConn;
    AlWeatherService alWeatherService;

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

        intent = new Intent(this, AlWeatherService.class);
        startService(intent);

   /*     sConn = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.d(TAG, "onServiceConnected");
                alWeatherService = ((AlWeatherService.MyBinder) binder).getService();
                bound = true;
            }

            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "onServiceDsconnected");
                bound = false;
            }
        };
        bindService(intent, sConn, 0);
 */   }

    public AlWeatherService getService(){
        return alWeatherService;
    }

    public static OpenweathermapAPI getApi() {
        return openweathermapAPI;
    }
}
