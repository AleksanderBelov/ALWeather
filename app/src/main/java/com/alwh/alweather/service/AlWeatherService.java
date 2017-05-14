package com.alwh.alweather.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.alwh.alweather.database.SQLiteAlWeatherConfig;
import com.alwh.alweather.database.SQLiteForecastData;
import com.alwh.alweather.database.SQLiteForecastItem;
import com.alwh.alweather.database.SQLiteWeatherData;

import org.parceler.Parcels;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.alwh.alweather.helpers.AppRoot.NEW_WEATHER;

public class AlWeatherService extends Service {

    final String TAG = "AlWeather/Service: ";
    MyBinder binder = new MyBinder();

    Timer timerWeather;
    Timer timerForecast;
    TimerTask tTaskWeather;
    TimerTask tTaskForecast;
    long interval = 10000;
    SQLiteAlWeatherConfig sqLiteAlWeatherConfig;
    LoadAllDataFromSite loadAllDataFromSite = new LoadAllDataFromSite();


    public AlWeatherService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        this.sqLiteAlWeatherConfig = SQLiteAlWeatherConfig.findById(SQLiteAlWeatherConfig.class, 1);
        timerWeather = new Timer();
        timerForecast = new Timer();
        LoadForecastFromSite();
        LoadWeatherFromSite();

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return binder;
  //      return new Binder();
    }
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    public void reloadConfig(){
        this.sqLiteAlWeatherConfig = SQLiteAlWeatherConfig.findById(SQLiteAlWeatherConfig.class, 1);
    }

    void LoadWeatherFromSite() {
        new Thread(new Runnable() {
            public void run() {
                if (tTaskWeather != null) tTaskWeather.cancel();
                if (!(sqLiteAlWeatherConfig.getIntervalWeather() == 0)) {
                tTaskWeather = new TimerTask() {
                        public void run() {
                            Log.d(TAG, "LoadWeatherFromSite");
                            loadAllDataFromSite.getWeatherFromSite(sqLiteAlWeatherConfig.getCity());
                        }
                    };
                timerWeather.schedule(tTaskWeather, 1, sqLiteAlWeatherConfig.getIntervalWeather());
                }
            }
        }).start();
    }

    void LoadForecastFromSite() {
        new Thread(new Runnable() {
            public void run() {
                if (tTaskForecast != null) tTaskForecast.cancel();
                if (!(sqLiteAlWeatherConfig.getIntervalForecast() == 0)) {
                tTaskForecast = new TimerTask() {
                        public void run() {
                            Log.d(TAG, "LoadForecastFromSite");
                            loadAllDataFromSite.getForecastFromSite(sqLiteAlWeatherConfig.getCity());
                        }
                    };
                timerForecast.schedule(tTaskForecast, 1, sqLiteAlWeatherConfig.getIntervalForecast());
                }
            }
        }).start();
    }

    public SQLiteWeatherData TransferWeather(boolean renew) {
        SQLiteWeatherData sqLiteWeatherData;

        if (renew) {
            sqLiteWeatherData =  loadAllDataFromSite.getWeatherFromSite(sqLiteAlWeatherConfig.getCity());
        } else
            sqLiteWeatherData =  SQLiteWeatherData.findById(SQLiteWeatherData.class, SQLiteWeatherData.count(SQLiteWeatherData.class));

        Intent intent = new Intent(NEW_WEATHER);
        intent.putExtra("weather",  Parcels.wrap(sqLiteWeatherData));
        sendBroadcast(intent);

        return sqLiteWeatherData;
    }

    public SQLiteForecastData TransferForecast(boolean renew) {

        loadAllDataFromSite.getForecastFromSite(sqLiteAlWeatherConfig.getCity());

        if (renew) {
            return loadAllDataFromSite.getForecastFromSite(sqLiteAlWeatherConfig.getCity());
        } else
        {
            return (new SQLiteForecastData(SQLiteForecastItem.listAll(SQLiteForecastItem.class)));
        }

    }


    public class MyBinder extends Binder {
        public AlWeatherService getService() {
            return AlWeatherService.this;
        }
    }
}
