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
import com.alwh.alweather.model.ControlService;

public class MainActivity extends AppCompatActivity {

    final String TAG = "AlWeather/MActivity: ";


    TextView city;
    TextView temperature;
    ControlService controlService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city = (TextView) findViewById(R.id.textViewA);
        temperature = (TextView) findViewById(R.id.textViewB);

    //    controlService = new ControlService(this);
        Intent startActivityIntent = new Intent(MainActivity.this, WeatherActivity.class);
        startActivity(startActivityIntent);
        MainActivity.this.finish();


    }

    @Override
    protected void onResume() {
        super.onResume();
//        controlService.bindAlWeatherService();
    }

    @Override
    protected void onPause() {
        super.onPause();
  //      controlService.unbindAlWeatherService();
    }

    public void onClickgetTemperature(View v) {
        boolean renew = false;
        controlService.getForecast(renew);

        city.setText(controlService.getWeather(renew).getCityName());
        temperature.setText("" + controlService.getWeather(renew).getTemperature());
    }
}
