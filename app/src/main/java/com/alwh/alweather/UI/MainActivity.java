package com.alwh.alweather.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.alwh.alweather.R;
import com.alwh.alweather.Test;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Test test = new Test();

      //  test.getCurrentWeatherExecute();
        test.getCurrentWeather();




    }
}
