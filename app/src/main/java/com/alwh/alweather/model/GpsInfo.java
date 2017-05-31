package com.alwh.alweather.model;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ProgressBar;

import com.alwh.alweather.json.weather.Coord;

import java.util.Date;

import static android.content.Context.LOCATION_SERVICE;
import static com.alwh.alweather.helpers.AppRoot.MAX_CITY_COUNT;

/**
 * Created by abelov on 30.05.2017.
 */

public class GpsInfo {
    private Context contex;

    final String TAG = "AlWeather/GpsInfo ";

    public GpsInfo(Context contex) {
        this.contex = contex;
    }

    public Coord getLocation() {
        LocationManager locationManager = (LocationManager) contex.getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission
                (contex, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (contex, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d("Alweathet", "Error read GPS");
        }
        Location location = locationManager.getLastKnownLocation(bestProvider);
        Coord coord = new Coord();
        if (location == null) {
            coord.setLat((double) 0);
            coord.setLon((double) 0);
            return coord;
        }
        coord.setLat(location.getLatitude());
        coord.setLon(location.getLongitude());
        return coord;
    }
}
