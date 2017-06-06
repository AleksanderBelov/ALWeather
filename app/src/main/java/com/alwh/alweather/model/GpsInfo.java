package com.alwh.alweather.model;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import com.alwh.alweather.json.weather.Coord;

import static android.content.Context.LOCATION_SERVICE;

public class GpsInfo {

    private Context contex;

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
