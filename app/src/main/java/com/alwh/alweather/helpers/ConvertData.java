package com.alwh.alweather.helpers;

import android.content.Context;

import com.alwh.alweather.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ConvertData {
    public static String passedMin(Date oldDate) {
        Date now = new Date();
        return ("" + ((now.getTime() - oldDate.getTime()) / 1000) / 60);
    }

    public static String getLongDate(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm z");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        return sdf.format(date);
    }

    public static String getDay(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        return sdf.format(date);
    }

    public static String getTime(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        return sdf.format(date);
    }

    public static String getStandartDate(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        return sdf.format(date);
    }


    public static int getIconWeatherL(String wIcon, Context context) {
        String uri = "@drawable/l" + wIcon;
        int icon = context.getResources().getIdentifier(uri, null, context.getPackageName());

        return icon;
    }

    public static int getWeatherInfo(String wInfo, Context context) {
        String uri = "@string/w" + wInfo;
        int info = context.getResources().getIdentifier(uri, null, context.getPackageName());

        return info;
    }

    public static String getTextWeather(int weatherID, Context context) {
        String s = context.getResources()
                .getString(context
                        .getResources()
                        .getIdentifier("w" + weatherID, "string", context.getPackageName()));
        return s;
    }

    public static String getTextWeather(String key, Context context) {
        String s = context.getResources()
                .getString(context
                        .getResources()
                        .getIdentifier(key, "string", context.getPackageName()));
        return s;
    }

    public static String getDeg(double deg, Context context) {
        String result = null;
        String n = context.getString(R.string.nord);
        String s = context.getString(R.string.south);
        String e = context.getString(R.string.east);
        String w = context.getString(R.string.west);
        if ((337.5 <= deg) || (deg <= 22.5)) {
            result = "\u2b07" + " (" + n + ")";
        }
        if ((22.5 <= deg) && (deg <= 67.5)) {
            result = "\u2199" + " (" + n + e + ")";
        }
        if ((67.5 <= deg) && (deg <= 112.5)) {
            result = "\u2b05"  + " (" + e + ")";
        }
        if ((112.5 <= deg) && (deg <= 157.5)) {
            result = "\u2196"  + " (" + s + e + ")";
        }
        if ((157.5 <= deg) && (deg <= 202.5)) {
            result = "\u2b06"  + " (" + s + ")";
        }
        if ((202.5 <= deg) && (deg <= 247.5)) {
            result = "\u2197"  + " (" + s + w + ")";
        }
        if ((247.5 <= deg) && (deg <= 295.5)) {
            result = "\u27a1" + " (" + w + ")";
        }
        if ((295.5 <= deg) && (deg <= 337.5)) {
            result = "\u2198"  + " (" + n + w +")";
        }
        return result;
    }

    public static String getPressure(double pressure, String unit){
        if (unit.equals("hPa")) {
        return "" + (int)pressure;
        } else return "" + (int)(pressure*0.750062);

    }
}
