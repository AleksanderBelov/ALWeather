package com.alwh.alweather.database;

import com.alwh.alweather.json.weather.JSONWeatherData;
import com.orm.SugarRecord;

import org.parceler.Parcel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Parcel
public class SQLiteWeatherData extends SugarRecord {

    private String cityName;
    private String country;
    private double coordLon;
    private double coordLat;
    private int weatherID;
    private String weatherMain;
    private String weatherDescription;
    private String weatherIcon;
    private double temperature;
    private double pressure;
    private int humidity;
    private double windSpeed;
    private double windDeg;
    private int clouds;
    private double rain;
    private double snow;
    private Date dt;
    private Date sunrise;
    private Date sunset;

    public SQLiteWeatherData() {
    }

    public SQLiteWeatherData(JSONWeatherData jsonWeatherData) {
        setCityName(jsonWeatherData.getName());
        setCountry(jsonWeatherData.getSys().getCountry());
        setCoordLon(jsonWeatherData.getCoord().getLon());
        setCoordLat(jsonWeatherData.getCoord().getLat());
        setWeatherMain(jsonWeatherData.getWeather().get(0).getMain());
        setWeatherID(jsonWeatherData.getWeather().get(0).getId());
        setWeatherDescription(jsonWeatherData.getWeather().get(0).getDescription());
        setWeatherIcon(jsonWeatherData.getWeather().get(0).getIcon());
        setTemperature(jsonWeatherData.getMain().getTemp());
        setPressure(jsonWeatherData.getMain().getPressure());
        setHumidity(jsonWeatherData.getMain().getHumidity());
        setWindSpeed(jsonWeatherData.getWind().getSpeed());
        setId((long) 1);


        if (!(jsonWeatherData.getWind().getDeg() == null)) {
            setWindDeg(jsonWeatherData.getWind().getDeg());
        } else {
            setWindDeg(0);
        }

        setClouds(jsonWeatherData.getClouds().getAll());

        if (!(jsonWeatherData.getRain() == null)) {
            setRain(jsonWeatherData.getRain().get3h());
        } else {
            setRain(0);
        }

        if (!(jsonWeatherData.getSnow() == null)) {
            setSnow(jsonWeatherData.getSnow().get3h());
        } else {
            setSnow(0);
        }
        setDt(new Date());
        setSunrise(jsonWeatherData.getSys().getSunrise());
        setSunset(jsonWeatherData.getSys().getSunset());
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public double getCoordLon() {
        return coordLon;
    }

    public void setCoordLon(double coordLon) {
        this.coordLon = coordLon;
    }

    public double getCoordLat() {
        return coordLat;
    }

    public void setCoordLat(double coordLat) {
        this.coordLat = coordLat;
    }

    public String getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public double getTemperature() {
        BigDecimal decimal = new BigDecimal(temperature);
        decimal = decimal.setScale(0, RoundingMode.HALF_UP);
        double result = decimal.doubleValue();
        return result;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(double windDeg) {
        this.windDeg = windDeg;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public double getSnow() {
        return snow;
    }

    public void setSnow(double snow) {
        this.snow = snow;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(int dtUnix) {

        this.dt = unixDateToDate(dtUnix);
    }

    public void setDt(Date date) {

        this.dt = date;
    }

    public Date getSunrise() {
        return sunrise;
    }

    public void setSunrise(int sunriseUnix) {

        this.sunrise = unixDateToDate(sunriseUnix);
    }

    public Date getSunset() {
        return sunset;
    }

    public void setSunset(int sunsetUnix) {

        this.sunset = unixDateToDate(sunsetUnix);
    }

    private Date unixDateToDate(int unixDate) {
        return new Date(unixDate * 1000L);
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    public int getWeatherID() {
        return weatherID;
    }

    public void setWeatherID(int weatherID) {
        this.weatherID = weatherID;
    }
}
