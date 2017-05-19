package com.alwh.alweather.UI;
import android.app.Activity;
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
import com.alwh.alweather.service.AlWeatherService;
import org.parceler.Parcels;

import static com.alwh.alweather.helpers.AppRoot.FORECAST;
import static com.alwh.alweather.helpers.AppRoot.NEW_WEATHER;
import static com.alwh.alweather.helpers.AppRoot.WEATHER;


public class MainActivity extends Activity implements WeatherFragment.onSomeEventListener {


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

        intent = new Intent(this, AlWeatherService.class);
        startService(intent);
        Log.d(TAG, "service start");
        weatherFragment = new WeatherFragment();
        forecastFragment = new ForecastFragment();

        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_conteiner, weatherFragment);
        fragmentTransaction.commit();

     //      fragmentTransaction = getFragmentManager().beginTransaction();
     //      fragmentTransaction.replace(R.id.frame_conteiner, forecastFragment);
     //      fragmentTransaction.commit();

        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                int key = intent.getIntExtra("key", 0);
                switch (key){
                    case 0: Log.d(TAG, "Error get key from intent");
                        break;
                    case 1:
                    //    weatherFragment.initData((SQLiteWeatherData) Parcels.unwrap(intent.getParcelableExtra(WEATHER)));

                        sqliteWeatherData = (SQLiteWeatherData) Parcels.unwrap(intent.getParcelableExtra(WEATHER));
                        break;
                    case 3:
                        sqLiteForecastData = Parcels.unwrap(intent.getParcelableExtra(FORECAST));
                        sqliteWeatherData = (SQLiteWeatherData) Parcels.unwrap(intent.getParcelableExtra(WEATHER));
                     //   weatherFragment.initDataList(sqLiteForecastData);
                     //   forecastFragment.initDataList(sqLiteForecastData);
                     //   break;


                }
                weatherFragment.initDataList(sqLiteForecastData);
                weatherFragment.initData(sqliteWeatherData);

 //               forecastFragment.initDataList(sqLiteForecastData);

            }
        };
        IntentFilter intFilt = new IntentFilter(NEW_WEATHER);
        registerReceiver(br, intFilt);
    }

    public void someEvent(int page) {
        // transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
         //transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);

        if(weatherFragment.isVisible()){
            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_conteiner, forecastFragment);
            fragmentTransaction.commit();
            forecastFragment.initDataList(sqLiteForecastData);
        }else{
            fragmentTransaction.replace(R.id.frame_conteiner, weatherFragment);
            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_conteiner, forecastFragment);
            fragmentTransaction.commit();
        }
    }
}





