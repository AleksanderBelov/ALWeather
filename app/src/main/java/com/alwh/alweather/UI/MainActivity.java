package com.alwh.alweather.UI;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.FragmentTransaction;

import android.view.View;
import android.widget.TextView;


import com.alwh.alweather.R;
import com.alwh.alweather.helpers.AppRoot;
import com.alwh.alweather.json.forecast.Weather;
import com.alwh.alweather.model.ControlService;

public class MainActivity extends AppCompatActivity {


    final String TAG = "AlWeather/MActivity: ";
   WeatherFragment weatherFragment;
   ForecastFragment forecastFragment;

    FragmentTransaction fragmentTransaction;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherFragment = new WeatherFragment();
        forecastFragment = new ForecastFragment();

        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_conteiner, weatherFragment);
        fragmentTransaction.commit();

        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_conteiner, forecastFragment);
        fragmentTransaction.commit();






        //       Intent startActivityIntent = new Intent(MainActivity.this, WeatherActivity.class);
 //       startActivity(startActivityIntent);
 //       MainActivity.this.finish();


    }
}
