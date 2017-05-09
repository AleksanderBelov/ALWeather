package com.alwh.alweather.model;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.alwh.alweather.UI.MainActivity;
import com.alwh.alweather.database.SQLiteWeatherData;
import com.alwh.alweather.service.AlWeatherService;

/**
 * Created by Admin on 09.05.2017.
 */

public class ControlService {

    final String TAG = "AlWeather/Control ";

    boolean bound = false;
    ServiceConnection sConn;
    Intent intent;
    AlWeatherService alWeatherService;
    long interval;
    Context context;

    public ControlService(Context context) {
        this.context = context;
    }

    public void BindAlWeatherService() {
        intent = new Intent(this.context, AlWeatherService.class);

        sConn = new ServiceConnection() {

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

        context.bindService(this.intent, sConn, 0);

    }

    public SQLiteWeatherData getWeather() {
        Log.d(TAG, "bind service + alService");
        return alWeatherService.ReadWeatherFromSQLite();
    //    return alWeatherService.ReadActyalWeatherFromSQLite();
    }
}
