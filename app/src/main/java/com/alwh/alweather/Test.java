package com.alwh.alweather;

import android.util.Log;

import com.alwh.alweather.database.SQLiteWeatherData;
import com.alwh.alweather.helpers.AppRoot;

import com.alwh.alweather.json.forecast.JSONForecastData;
import com.alwh.alweather.json.weather.JSONWeatherData;
import com.orm.SugarRecord;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abelov on 05.05.2017.
 */

public class Test  {

    static String TAG = "getWeather/Test";

    JSONWeatherData jsonWeatherData;
    JSONForecastData jsonForecastData;

    public void getCurrentWeather() {
        jsonWeatherData = new JSONWeatherData();
        jsonForecastData = new JSONForecastData();

        Log.d(TAG,"start jsonWeatherData");
        AppRoot.getApi().getWeather("Kiev","json","metric",AppRoot.API_KEY).enqueue(new Callback<JSONWeatherData>() {
            @Override
            public void onResponse(Call<JSONWeatherData> call, Response<JSONWeatherData> response) {
                if (!(response.body() == null)) {
                    SQLiteWeatherData sqLiteWeatherData = new SQLiteWeatherData(response.body());
                    sqLiteWeatherData.save();

           //         test();
                } else {
                    Log.d(TAG, "response.body() =- NULL");
                }
            }
            @Override
            public void onFailure(Call<JSONWeatherData> call, Throwable t) {
                Log.d(TAG,t.toString());
            }
        });


        Log.d(TAG,"start jsonForecastData");
        AppRoot.getApi().getForecast("Kiev","json","metric",AppRoot.API_KEY).enqueue(new Callback<JSONForecastData>() {
            @Override
            public void onResponse(Call<JSONForecastData> call, Response<JSONForecastData> response) {
                if (!(response.body() == null)) {
                    jsonForecastData = response.body();
                    test();
                } else {
                    Log.d(TAG, "response.body() =- NULL");
                }
            }
            @Override
            public void onFailure(Call<JSONForecastData> call, Throwable t) {
                Log.d(TAG,t.toString());
            }
        });
    }

    public void test() {
  //      Log.d(TAG, "sity: " + jsonWeatherData.getName());

     //   TestRecord testRecord = new TestRecord(jsonWeatherData.getCity().getName(), jsonWeatherData.getList().get(0).getMain().getTemp());
     //   testRecord.save();
     //   jsonCurrentWeatherData.save();
      //  SQLiteCurrentWeatherData sqLiteCurrentWeatherData = new SQLiteCurrentWeatherData(jsonWeatherData);
      //  SQLiteWeatherData sqLiteWeatherData = new SQLiteWeatherData(jsonWeatherData);

      //  Log.d(TAG, "temp: SQLiteCurrentWeatherData: " + sqLiteWeatherData.getCityName());
    //    sqLiteWeatherData.save();

        Log.d(TAG, "temp: jsonForecastData (sity): " + jsonForecastData.getCity().getName());
    //    Log.d(TAG, "temp: LoadFromBase (temperature): " + jsonWeatherData.getList().get(0).getMain().getTemp());
    //    Log.d(TAG, "temp: LoadFromBase: " + jsonWeatherData.getList().get(0).getWeather().get(0).getDescription());

    }
}

class TestRecord extends SugarRecord {
    private String sity;
    private double temperature;

    public TestRecord() {
    }

    public TestRecord(String sity, double temperature) {
        this.sity = sity;
        this.temperature = temperature;
    }

    public String getSity() {
        return sity;
    }

    public void setSity(String sity) {
        this.sity = sity;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
