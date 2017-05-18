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
import java.util.Timer;
import java.util.TimerTask;

import static com.alwh.alweather.helpers.AppRoot.FORECAST;
import static com.alwh.alweather.helpers.AppRoot.NEW_WEATHER;
import static com.alwh.alweather.helpers.AppRoot.TRANSFER_NEW_WEATHER;
import static com.alwh.alweather.helpers.AppRoot.TRANSFER_SAVE_WEATHER;
import static com.alwh.alweather.helpers.AppRoot.TRANSFER_NEW_FORECAST;
import static com.alwh.alweather.helpers.AppRoot.TRANSFER_SAVE_FORECAST;
import static com.alwh.alweather.helpers.AppRoot.QUESTION_TO_SERVECE;
import static com.alwh.alweather.helpers.AppRoot.WEATHER;

public class AlWeatherService extends Service {

    final String TAG = "AlWeather/Service ";
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

    public void reloadConfig() {
        this.sqLiteAlWeatherConfig = SQLiteAlWeatherConfig.findById(SQLiteAlWeatherConfig.class, 1);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        if (!(intent.getExtras() == null)) {

            Log.d(TAG, "get: " + intent.getIntExtra(QUESTION_TO_SERVECE, 0));

            SelectetTransfer(intent.getIntExtra(QUESTION_TO_SERVECE, 0));


        }

        return super.onStartCommand(intent, flags, startId);
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

    public void TransferWeather(boolean renew) {
        SQLiteWeatherData sqLiteWeatherData;

        if (renew) {
            sqLiteWeatherData = loadAllDataFromSite.getWeatherFromSite(sqLiteAlWeatherConfig.getCity());
        } else
            sqLiteWeatherData = SQLiteWeatherData.findById(SQLiteWeatherData.class, SQLiteWeatherData.count(SQLiteWeatherData.class));

        Intent intent = new Intent(NEW_WEATHER);
        intent.putExtra("key", TRANSFER_NEW_WEATHER);
        intent.putExtra(WEATHER, Parcels.wrap(sqLiteWeatherData));
        sendBroadcast(intent);
    }

    public void TransferForecast(boolean renew) {
SQLiteForecastData SQLiteForecastData;

  //      loadAllDataFromSite.getForecastFromSite(sqLiteAlWeatherConfig.getCity());

        if (renew) {
            SQLiteForecastData =  loadAllDataFromSite.getForecastFromSite(sqLiteAlWeatherConfig.getCity());
        } else {
            SQLiteForecastData = new SQLiteForecastData(SQLiteForecastItem.listAll(SQLiteForecastItem.class));
        }
        Intent intent = new Intent(NEW_WEATHER);
        intent.putExtra("key", TRANSFER_NEW_FORECAST);
        intent.putExtra(FORECAST, Parcels.wrap(SQLiteForecastData));
        sendBroadcast(intent);

    }


    public class MyBinder extends Binder {
        public AlWeatherService getService() {
            return AlWeatherService.this;
        }
    }

    public void SelectetTransfer(int key) {
        switch (key) {
            case TRANSFER_NEW_WEATHER:
                TransferWeather(true);
                break;
            case TRANSFER_SAVE_WEATHER:
                TransferWeather(false);
                break;
            case TRANSFER_NEW_FORECAST:
                TransferForecast(true);
                break;
            case TRANSFER_SAVE_FORECAST:
                TransferForecast(false);
                break;

        }
    }
}
