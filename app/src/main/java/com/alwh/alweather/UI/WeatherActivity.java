package com.alwh.alweather.UI;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alwh.alweather.R;
import com.alwh.alweather.adapters.DailyListAdapter;
import com.alwh.alweather.database.SQLiteWeatherData;
import com.alwh.alweather.helpers.AppRoot;
import com.alwh.alweather.helpers.ConvertData;
import com.alwh.alweather.model.ControlService;
import com.squareup.picasso.Picasso;


public class WeatherActivity extends AppCompatActivity {

    ControlService controlService;
    TextView temperatureMain;
    TextView cityName;
    TextView currentDate;
    TextView windMain;
    TextView humidityMain;
    ImageView weatherIconMain;
    DailyListAdapter dailyListAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


        AppRoot app = (AppRoot) getApplication();
        controlService = app.getControlService();


    }

    protected void onResume() {
        super.onResume();

        initView();
        initData(false);


    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    public void onClickgetRenew(View v) {
        initData(true);
    }

    public void onClickgetForecast(View v) {
        Intent startActivityIntent = new Intent(this, ForecastActivity.class);
        startActivity(startActivityIntent);
        ;
    }


    public void initView() {
        temperatureMain = (TextView) findViewById(R.id.temperatureMain);
        temperatureMain.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/dsdigib.ttf"));

        cityName = (TextView) findViewById(R.id.cityName);
        cityName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/dsdigib.ttf"));

        currentDate = (TextView) findViewById(R.id.currentDate);
        //     currentDate.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/dsdigib.ttf"));

        temperatureMain = (TextView) findViewById(R.id.temperatureMain);
        temperatureMain.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/dsdigib.ttf"));

        windMain = (TextView) findViewById(R.id.windMain);
        humidityMain = (TextView) findViewById(R.id.humidityMain);
        weatherIconMain = (ImageView) findViewById(R.id.weatherIconMain);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(WeatherActivity.this, 5);
        recyclerView = (RecyclerView) findViewById(R.id.weather_daily_list);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
    }

    public void initData(boolean renew) {
        SQLiteWeatherData sqLiteWeatherData;
        sqLiteWeatherData = controlService.getWeather(renew);
        temperatureMain.setText("" + sqLiteWeatherData.getTemperature());
        cityName.setText(sqLiteWeatherData.getCityName() + ", " + sqLiteWeatherData.getCountry());

        currentDate.setText("last change: " + ConvertData.passedMin(sqLiteWeatherData.getDt()) + " min age");

        windMain.setText(sqLiteWeatherData.getWindSpeed() + " km/h");
        humidityMain.setText(sqLiteWeatherData.getHumidity() + "%");

        Picasso.with(this)
                .load("http://openweathermap.org/img/w/" + sqLiteWeatherData.getWeatherIcon() + ".png")
                .resize(0, 220)
                .into(weatherIconMain);

        dailyListAdapter = new DailyListAdapter(WeatherActivity.this, controlService.getForecast(false));
        recyclerView.setAdapter(dailyListAdapter);

    }
}
