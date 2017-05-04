package com.alwh.alweather.helpers;

import android.app.Application;

import retrofit2.Retrofit;

/**
 * Created by abelov on 04.05.2017.
 */

public class AppRoot extends Application {
    static String API_KEY = "dda74e030c23ee9017cf9a8f897f19cf";

    private Retrofit retrofit;
}
