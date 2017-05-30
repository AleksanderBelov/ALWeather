package com.alwh.alweather.model;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.Date;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by abelov on 30.05.2017.
 */

public class GpsInfo {
    private Context contex;
    private LocationManager locationManager;
    private StringBuilder sbGPS = new StringBuilder();
    private StringBuilder sbNet = new StringBuilder();
    private boolean isGps = false;
    private boolean isNetork = false;
    private Gps gps;

    final String TAG = "AlWeather/GpsInfo ";


    public GpsInfo(Context contex) {
        this.contex = contex;
        locationManager = (LocationManager) contex.getSystemService(LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 10, 10, this.locationListener);
        if (ActivityCompat.checkSelfPermission(contex, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(contex, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            //   return TODO;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 10, 10, locationListener);
        checkEnabled();

    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();
        }

        @Override
        public void onProviderEnabled(String provider) {
            checkEnabled();
            if (ActivityCompat.checkSelfPermission(contex, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (contex, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                return;
            }
            showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) {
         //       tvStatusGPS.setText("Status: " + String.valueOf(status));
            } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
        //        tvStatusNet.setText("Status: " + String.valueOf(status));
            }
        }
    };

    private void showLocation(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {

       //     tvLocationGPS.setText(formatLocation(location));
            this.gps = new Gps((float)location.getLatitude(), (float)location.getLongitude(), Source.GPS);
            Log.d(TAG, formatLocation(location));
        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            this.gps = new Gps((float)location.getLatitude(), (float)location.getLongitude(), Source.NETWORK);
            Log.d(TAG, formatLocation(location));
      //      tvLocationNet.setText(formatLocation(location));
        }
    }

    private String formatLocation(Location location) {
        if (location == null)
            return "";
        return String.format(
                "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
                location.getLatitude(), location.getLongitude(), new Date(
                        location.getTime()));
    }

    private void checkEnabled() {
isGps =  locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
isNetork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public Gps getGps() {
        return gps;
    }

    public void setGps(Gps gps) {
        this.gps = gps;
    }
}
