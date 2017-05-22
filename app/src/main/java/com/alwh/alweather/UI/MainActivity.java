package com.alwh.alweather.UI;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.util.Log;
import com.alwh.alweather.R;
import com.alwh.alweather.database.SQLiteForecastData;
import com.alwh.alweather.database.SQLiteWeatherData;
import com.alwh.alweather.model.MessageEvent;
import com.alwh.alweather.model.SelectShow;
import com.alwh.alweather.service.AlWeatherService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.parceler.Parcels;


import static com.alwh.alweather.helpers.AppRoot.FORECAST;
import static com.alwh.alweather.helpers.AppRoot.NEW_WEATHER;
import static com.alwh.alweather.helpers.AppRoot.QUESTION_TO_SERVECE;
import static com.alwh.alweather.helpers.AppRoot.TRANSFER_SAVE_FORECAST;
import static com.alwh.alweather.helpers.AppRoot.TRANSFER_SAVE_WEATHER;
import static com.alwh.alweather.helpers.AppRoot.WEATHER;


public class MainActivity extends Activity{


    final String TAG = "AlWeather/MActivity ";
    WeatherFragment weatherFragment;
    ForecastFragment forecastFragment;
    FragmentTransaction fragmentTransaction;
    Intent intent;
    BroadcastReceiver br;
    SQLiteForecastData sqLiteForecastData;
    SQLiteWeatherData sqliteWeatherData;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


startService(new Intent(this, AlWeatherService.class).
                        putExtra(QUESTION_TO_SERVECE, TRANSFER_SAVE_WEATHER));


startService(new Intent(this, AlWeatherService.class).
                        putExtra(QUESTION_TO_SERVECE, TRANSFER_SAVE_FORECAST));

        Log.d(TAG, "service start");
        weatherFragment = new WeatherFragment();
        forecastFragment = new ForecastFragment();

        EventBus.getDefault().register(this);




        showFragment(weatherFragment);



        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                int key = intent.getIntExtra("key", 0);
                switch (key){
                    case 0: Log.d(TAG, "Error get key from intent");
                        break;
                    case 1:

                        EventBus.getDefault().post(
                                new MessageEvent(
                                        (SQLiteWeatherData) Parcels.unwrap(intent.getParcelableExtra(WEATHER))));
                        break;
                    case 3:
                        EventBus.getDefault().post(
                                new MessageEvent(
                                        (SQLiteForecastData) Parcels.unwrap(intent.getParcelableExtra(FORECAST))));
                }
            }
        };
        IntentFilter intFilt = new IntentFilter(NEW_WEATHER);
        registerReceiver(br, intFilt);
    }

    @Subscribe
    public void onEvent(SelectShow event){

        if(event.selectShow == 2){

            showFragment(forecastFragment);
            EventBus.getDefault().post(new MessageEvent(sqLiteForecastData));

        }else{
            showFragment(weatherFragment);
        }
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = MainActivity.this.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
     //   fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
        fragmentTransaction
                .replace(R.id.frame_conteiner, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}





