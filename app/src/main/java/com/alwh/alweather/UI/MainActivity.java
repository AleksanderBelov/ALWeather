package com.alwh.alweather.UI;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.util.Log;
import com.alwh.alweather.R;

import com.alwh.alweather.database.SQLiteForecastData;
import com.alwh.alweather.database.SQLiteWeatherData;
import com.alwh.alweather.service.AlWeatherService;
import org.parceler.Parcels;

import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;


import static com.alwh.alweather.helpers.AppRoot.FORECAST;
import static com.alwh.alweather.helpers.AppRoot.NEW_WEATHER;
import static com.alwh.alweather.helpers.AppRoot.WEATHER;


public class MainActivity extends FragmentActivity{


    final String TAG = "AlWeather/MActivity ";
    WeatherFragment weatherFragment;
    ForecastFragment forecastFragment;
    FragmentTransaction fragmentTransaction;
    Intent intent;
    BroadcastReceiver br;


    float x;
    float y;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, AlWeatherService.class);
        startService(intent);
        Log.d(TAG, "service start");

        weatherFragment = new WeatherFragment();
        forecastFragment = new ForecastFragment();




        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_conteiner, weatherFragment);
        fragmentTransaction.commit();

  //        fragmentTransaction = getFragmentManager().beginTransaction();
  //        fragmentTransaction.replace(R.id.frame_conteiner, forecastFragment);
  //        fragmentTransaction.commit();

        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                int key = intent.getIntExtra("key", 0);
                switch (key){
                    case 0: Log.d(TAG, "Error get key from intent");
                        break;
                    case 1:
                        weatherFragment.initData((SQLiteWeatherData) Parcels.unwrap(intent.getParcelableExtra(WEATHER)));
                        break;
                    case 3: SQLiteForecastData sqLiteForecastData = Parcels.unwrap(intent.getParcelableExtra(FORECAST));
                        weatherFragment.initDataList(sqLiteForecastData);
                        forecastFragment.initDataList(sqLiteForecastData);
                        break;
                }
            }
        };
        IntentFilter intFilt = new IntentFilter(NEW_WEATHER);
        registerReceiver(br, intFilt);
    }
    public void onClick(View v){


        // transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);

        if(weatherFragment.isVisible()){
            fragmentTransaction.replace(R.id.frame_conteiner, forecastFragment);
        }else{
            fragmentTransaction.replace(R.id.frame_conteiner, weatherFragment);
        }
        fragmentTransaction.commit();
    }
}





