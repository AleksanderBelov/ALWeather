package com.alwh.alweather.UI;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.alwh.alweather.R;
import com.alwh.alweather.Test;
import com.alwh.alweather.service.AlWeatherService;

public class MainActivity extends AppCompatActivity {

    final String TAG = "AlWeather/MActivity: ";

    boolean bound = false;
    ServiceConnection sConn;
    Intent intent;
    AlWeatherService alWeatherService;
    long interval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, AlWeatherService.class);

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







     //   Test test = new Test();
     //   test.getCurrentWeather();




    }
    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
        bindService(intent, sConn, 0);
        startService(intent);
    }
}
