package com.alwh.alweather.database;

import com.alwh.alweather.json.forecast.JSONForecastData;
import org.parceler.Parcel;
import java.util.ArrayList;
import java.util.List;


@Parcel
public class SQLiteForecastData {
    List<SQLiteForecastItem> forecast;

    public SQLiteForecastData() {
    }

    public SQLiteForecastData(List<SQLiteForecastItem> forecast) {
        this.forecast = forecast;
    }

    public SQLiteForecastData(JSONForecastData jsonForecastData) {

        this.forecast = new ArrayList<>();
        SQLiteForecastItem sqLiteForecastItem = new SQLiteForecastItem();
        for (int i = 0; i < jsonForecastData.getList().size(); i++) {
            sqLiteForecastItem.setCityName(jsonForecastData.getCity().getName());
            sqLiteForecastItem.setCountry(jsonForecastData.getCity().getCountry());
            sqLiteForecastItem.setCoordLon(jsonForecastData.getCity().getCoord().getLon());
            sqLiteForecastItem.setCoordLat(jsonForecastData.getCity().getCoord().getLat());
            sqLiteForecastItem.setWeatherID(jsonForecastData.getList().get(i).getWeather().get(0).getId());
            sqLiteForecastItem.setWeatherMain(jsonForecastData.getList().get(i).getWeather().get(0).getMain());
            sqLiteForecastItem.setWeatherDescription(jsonForecastData.getList().get(i).getWeather().get(0).getDescription());
            sqLiteForecastItem.setWeatherIcon(jsonForecastData.getList().get(i).getWeather().get(0).getIcon());
            sqLiteForecastItem.setTemperature(jsonForecastData.getList().get(i).getMain().getTemp());
            sqLiteForecastItem.setPressure(jsonForecastData.getList().get(i).getMain().getPressure());
            sqLiteForecastItem.setHumidity(jsonForecastData.getList().get(i).getMain().getHumidity());
            sqLiteForecastItem.setWindSpeed(jsonForecastData.getList().get(i).getWind().getSpeed());
            if (!(jsonForecastData.getList().get(i).getWind().getDeg() == null)) {
                sqLiteForecastItem.setWindDeg(jsonForecastData.getList().get(i).getWind().getDeg());
            } else {
                sqLiteForecastItem.setWindDeg(0);
            }
            sqLiteForecastItem.setClouds(jsonForecastData.getList().get(i).getClouds().getAll());
            if (!(jsonForecastData.getList().get(i).getRain() == null)) {
                if (!(jsonForecastData.getList().get(i).getRain().get3h() == null)) {
                    sqLiteForecastItem.setRain(jsonForecastData.getList().get(i).getRain().get3h());
                }
            } else {
                sqLiteForecastItem.setRain(0);
            }
            if (!(jsonForecastData.getList().get(i).getSnow() == null)) {
                if (!(jsonForecastData.getList().get(i).getSnow().get3h() == null)) {
                    sqLiteForecastItem.setSnow(jsonForecastData.getList().get(i).getSnow().get3h());
                }
            } else {
                sqLiteForecastItem.setSnow(0);
            }
            sqLiteForecastItem.setDt(jsonForecastData.getList().get(i).getDt());
            sqLiteForecastItem.setId((long)i);
            sqLiteForecastItem.save();
            forecast.add(sqLiteForecastItem);
        }
    }

    public void save() {

    }

    public List<SQLiteForecastItem> getForecast() {
        return forecast;
    }
}