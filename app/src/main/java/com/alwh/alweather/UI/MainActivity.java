package com.alwh.alweather.UI;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.alwh.alweather.R;
import com.alwh.alweather.helpers.AppRoot;
import com.alwh.alweather.json.forecast.Weather;
import com.alwh.alweather.model.ControlService;
import com.alwh.alweather.model.Trasfer;
import com.alwh.alweather.service.AlWeatherService;


public class MainActivity extends AppCompatActivity {


    final String TAG = "AlWeather/MActivity: ";
    ControlService controlService;
   WeatherFragment weatherFragment;
   ForecastFragment forecastFragment;

    FragmentTransaction fragmentTransaction;

    ServiceConnection sConn;
    Intent intent;
    AlWeatherService alWeatherService;
    Trasfer trasfer;
    boolean bound = false;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, AlWeatherService.class);
        startService(intent);
        Log.d(TAG, "service start");
        trasfer = new Trasfer();
        sConn = new ServiceConnection() {

            public void onServiceConnected(ComponentName name, IBinder binder) {
                alWeatherService = ((AlWeatherService.MyBinder) binder).getService();
                bound = true;
            }

            public void onServiceDisconnected(ComponentName name) {
                bound = false;
            }
        };


        super.onResume();
        bindService(intent, sConn, 0);
        Log.d(TAG, "service bind (from Main)");
        bound = true;

        unbindService(sConn);
        Log.d(TAG, "service unbind (from Main)");
        bound = false;



//        AppRoot app = (AppRoot) this.getApplication();
//        controlService = app.getControlService();

        weatherFragment = new WeatherFragment();
        forecastFragment = new ForecastFragment();

        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_conteiner, weatherFragment);
        fragmentTransaction.commit();

      //  fragmentTransaction = getFragmentManager().beginTransaction();
      //  fragmentTransaction.replace(R.id.frame_conteiner, forecastFragment);
      //  fragmentTransaction.commit();






        //       Intent startActivityIntent = new Intent(MainActivity.this, WeatherActivity.class);
 //       startActivity(startActivityIntent);
 //       MainActivity.this.finish();


    }

    
    
}
