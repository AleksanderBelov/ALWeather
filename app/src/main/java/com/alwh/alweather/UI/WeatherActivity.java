package com.alwh.alweather.UI;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.alwh.alweather.R;
import com.alwh.alweather.model.ControlService;

public class WeatherActivity extends AppCompatActivity {

    ControlService controlService;
    TextView temperatureMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        controlService = new ControlService(this);

        temperatureMain = (TextView)findViewById(R.id.temperatureMain);
        temperatureMain.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/dsdigib.ttf"));


    //    Typeface digit_font = Typeface.createFromAsset(getAssets(),getString(R.string.digit_font));

    //    TextView temperatureMain = (TextView)findViewById(R.id.temperatureMain);
    //    temperatureMain.setTypeface(digit_font);




    }

    protected void onResume() {
        super.onResume();
        controlService.bindAlWeatherService();
        temperatureMain.setText("" + controlService.getWeather(false).getTemperature());
    }

    @Override
    protected void onPause() {
        super.onPause();
        controlService.unbindAlWeatherService();
    }


}
