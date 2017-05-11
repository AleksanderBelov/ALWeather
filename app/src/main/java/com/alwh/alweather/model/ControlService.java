package com.alwh.alweather.model;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.alwh.alweather.UI.MainActivity;
import com.alwh.alweather.database.SQLiteForecastData;
import com.alwh.alweather.database.SQLiteWeatherData;
import com.alwh.alweather.service.AlWeatherService;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by Admin on 09.05.2017.
 */

public class ControlService {

    final String TAG = "AlWeather/Control ";

    boolean bound = false;
    ServiceConnection sConn;
    Intent intent;
    AlWeatherService alWeatherService;

    Context context;


    public ControlService(Context context) {

        this.context = context;
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
    }

    public void bindAlWeatherService() {


        this.context.bindService(this.intent, sConn, 0);


    }
    public void unbindAlWeatherService() {

        if (!bound) return;
        context.unbindService(sConn);
        bound = false;

    }
    public SQLiteWeatherData getWeather(boolean renew) { // false: from DB, true: from site (online)
        Log.d(TAG, "bind service + alService");

        return alWeatherService.TransferWeather(renew);

    }

    public SQLiteForecastData getForecast(boolean renew) { // false: from DB, true: from site (online)
        return alWeatherService.TransferForecast(renew);

    }

}
