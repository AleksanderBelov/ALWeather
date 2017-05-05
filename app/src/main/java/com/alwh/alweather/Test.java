package com.alwh.alweather;

import android.util.Log;

import com.alwh.alweather.helpers.AppRoot;
import com.alwh.alweather.json.JSONCurrentWeatherData;
import com.orm.SugarRecord;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abelov on 05.05.2017.
 */

public class Test  {


    static String TAG = "getCurrentWeather/Test";

    JSONCurrentWeatherData jsonCurrentWeatherData;

    public void getCurrentWeather() {
        jsonCurrentWeatherData = new JSONCurrentWeatherData();
        Log.d(TAG,"start getCurrentWeather");
        AppRoot.getApi().getCurrentWeather("Kiev","json","metric",AppRoot.API_KEY).enqueue(new Callback<JSONCurrentWeatherData>() {



            @Override
            public void onResponse(Call<JSONCurrentWeatherData> call, Response<JSONCurrentWeatherData> response) {
                if (!(response.body() == null)) {
                    jsonCurrentWeatherData = response.body();
                    test(

                    );
                } else {
                    Log.d(TAG, "response.body() =- NULL");
                }
            }

            @Override
            public void onFailure(Call<JSONCurrentWeatherData> call, Throwable t) {
                Log.d(TAG,t.toString());
            }
        });

    }

    public void test() {
        Log.d(TAG, "sity: " + jsonCurrentWeatherData.getCity().getName());

        TestRecord testRecord = new TestRecord(jsonCurrentWeatherData.getCity().getName(), jsonCurrentWeatherData.getList().get(0).getMain().getTemp());
        testRecord.save();

        Log.d(TAG, "temp: " + jsonCurrentWeatherData.getList().get(0).getMain().getTemp());
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
