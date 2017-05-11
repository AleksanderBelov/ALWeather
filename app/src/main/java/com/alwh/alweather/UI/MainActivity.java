package com.alwh.alweather.UI;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;


import com.alwh.alweather.R;
import com.alwh.alweather.helpers.AppRoot;
import com.alwh.alweather.model.ControlService;

public class MainActivity extends AppCompatActivity {

    final String TAG = "AlWeather/MActivity: ";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Intent startActivityIntent = new Intent(MainActivity.this, WeatherActivity.class);
        startActivity(startActivityIntent);
        MainActivity.this.finish();


    }
}
