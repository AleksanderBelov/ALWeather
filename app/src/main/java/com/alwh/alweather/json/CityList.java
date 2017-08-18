package com.alwh.alweather.json;

import com.alwh.alweather.json.weather.Coord;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abelov on 29.05.2017.
 */

public class CityList {
    @SerializedName("id")
    private String _id;
    @SerializedName("name")
    private String name;
    @SerializedName("country")
    private String country;
    @SerializedName("coord")
    private Coord coord;
    public CityList(String _id, String name, String country, Coord coord) {
        this._id = _id;
        this.name = name;
        this.country = country;
        this.coord = coord;
    }
    public CityList() {
    }
    public String get_id() {
        return _id;
    }
    public String getName() {
        return name;
    }
    public String getCountry() {
        return country;
    }
    public Coord getCoord() {
        return coord;
    }
}
