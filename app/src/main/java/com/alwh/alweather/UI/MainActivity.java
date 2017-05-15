package com.alwh.alweather.UI;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.app.Fragment;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.alwh.alweather.R;
import com.alwh.alweather.database.SQLiteForecastData;
import com.alwh.alweather.database.SQLiteWeatherData;
import com.alwh.alweather.helpers.AppRoot;
import com.alwh.alweather.json.forecast.Weather;
import com.alwh.alweather.model.ControlService;

import com.alwh.alweather.service.AlWeatherService;

import org.parceler.Parcels;

import static com.alwh.alweather.helpers.AppRoot.FORECAST;
import static com.alwh.alweather.helpers.AppRoot.NEW_WEATHER;
import static com.alwh.alweather.helpers.AppRoot.WEATHER;


public class MainActivity extends AppCompatActivity {


    final String TAG = "AlWeather/MActivity: ";
    ControlService controlService;
    WeatherFragment weatherFragment;
    ForecastFragment forecastFragment;

    FragmentTransaction fragmentTransaction;

    ServiceConnection sConn;
    Intent intent;
    AlWeatherService alWeatherService;
    boolean bound = false;
    BroadcastReceiver br;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, AlWeatherService.class);
        startService(intent);
        Log.d(TAG, "service start");



//        AppRoot app = (AppRoot) this.getApplication();
//        controlService = app.getControlService();

        weatherFragment = new WeatherFragment();
        forecastFragment = new ForecastFragment();

 //       fragmentTransaction = getFragmentManager().beginTransaction();
 //       fragmentTransaction.add(R.id.frame_conteiner, weatherFragment);
 //       fragmentTransaction.commit();

          fragmentTransaction = getFragmentManager().beginTransaction();
          fragmentTransaction.replace(R.id.frame_conteiner, forecastFragment);
          fragmentTransaction.commit();

        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                int key = intent.getIntExtra("key", 0);
                switch (key){
                    case 0: Log.d(TAG, "Error get key from intent");
                        break;
                    case 1:  weatherFragment.initData((SQLiteWeatherData) Parcels.unwrap(intent.getParcelableExtra(WEATHER)));
                        break;
                    case 3: SQLiteForecastData sqLiteForecastData = Parcels.unwrap(intent.getParcelableExtra(FORECAST));
       //                 weatherFragment.initDataList(sqLiteForecastData);
                        forecastFragment.initDataList(sqLiteForecastData);
                        break;
                }
            }
        };

        // создаем фильтр для BroadcastReceiver
        IntentFilter intFilt = new IntentFilter(NEW_WEATHER);
        // регистрируем (включаем) BroadcastReceiver
        registerReceiver(br, intFilt);
    }

    //           Log.d(LOG_TAG, "onReceive: task = " + task + ", status = " + status);

    // Ловим сообщения о старте задач
          /*      if (status == STATUS_START) {
                    switch (task) {
                        case TASK1_CODE:
                            tvTask1.setText("Task1 start");
                            break;
                        case TASK2_CODE:
                            tvTask2.setText("Task2 start");
                            break;
                        case TASK3_CODE:
                            tvTask3.setText("Task3 start");
                            break;

                    }
                }
*/

    //       Intent startActivityIntent = new Intent(MainActivity.this, WeatherActivity.class);
    //       startActivity(startActivityIntent);
    //       MainActivity.this.finish();


}





