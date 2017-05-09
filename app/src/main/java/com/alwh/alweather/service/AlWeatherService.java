package com.alwh.alweather.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.alwh.alweather.database.SQLiteWeatherData;

import java.util.Timer;
import java.util.TimerTask;

public class AlWeatherService extends Service {

    final String TAG = "AlWeather/Service: ";
    MyBinder binder = new MyBinder();

    Timer timer;
    TimerTask tTask;
    long interval = 10000;
    LoadDate loadDate = new LoadDate();


    public AlWeatherService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        timer = new Timer();
        LoadWeatherFromSite();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return binder;


    }

    void LoadWeatherFromSite() {
        new Thread(new Runnable() {
            public void run() {
                if (tTask != null) tTask.cancel();
                if (interval > 0) {
                    tTask = new TimerTask() {
                        public void run() {
                            Log.d(TAG, "run");
                            loadDate.getWeatherFromSite();
                        }
                    };
                    timer.schedule(tTask, 1000, interval);
                }
            }
        }).start();
    }

    public SQLiteWeatherData ReadWeatherFromSQLite() {
        return SQLiteWeatherData.findById(SQLiteWeatherData.class, );
    }

    public SQLiteWeatherData ReadActyalWeatherFromSQLite() {

        return loadDate.getWeatherFromSite();
    }

    public class MyBinder extends Binder {
        public AlWeatherService getService() {
            return AlWeatherService.this;
        }
    }
}
