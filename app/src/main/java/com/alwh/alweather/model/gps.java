package com.alwh.alweather.model;

/**
 * Created by abelov on 30.05.2017.
 */

public class Gps {
    private float latitude;
    private float longitude;
    Source source;


    public Gps() {
    }

    public Gps(float latitude, float longitude, Source source) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.source = source;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @Override
    public String toString() {
        String result;
        result =  String.format("Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
                    getLatitude(), getLongitude());

        return result;
    }
}

enum Source {GPS, NETWORK}
