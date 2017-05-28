package com.alwh.alweather.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alwh.alweather.R;
import com.alwh.alweather.database.SQLiteForecastData;
import com.alwh.alweather.helpers.ConvertData;
import com.squareup.picasso.Picasso;


public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private SQLiteForecastData sqLiteForecastData;
    private Context context;

    public ForecastAdapter(Context context, SQLiteForecastData sqLiteForecastData) {
        this.sqLiteForecastData = sqLiteForecastData;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forecast_list, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Picasso.with(context)
 //               .load("http://openweathermap.org/img/w/" + sqLiteForecastData.getForecast().get(position).getWeatherIcon() + ".png")
                .load(ConvertData.getIconWeatherL(sqLiteForecastData.getForecast().get(position).getWeatherIcon(),context))
                .resize(0, 150)
                .into(holder.weatherIcon);

        holder.day.setText(ConvertData.getStandartDate(sqLiteForecastData.getForecast().get(position).getDt()));
        holder.weatherInfo.setText("temp: " + String.valueOf(Math.round(sqLiteForecastData.getForecast().get(position).getTemperature())) + "°");
        holder.pressureInfo.setText("    pressure: " + sqLiteForecastData.getForecast().get(position).getPressure() + "hPa");
        //       holder.humidityInfo.setText("h: " + String.valueOf(Math.round(sqLiteForecastData.getForecast().get(position).getTemperature())) + "%");
    }

    @Override
    public int getItemCount() {
        if (sqLiteForecastData == null) return 0;
        return sqLiteForecastData.getForecast().size();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView weatherIcon;
        TextView day;
        TextView weatherInfo;
        TextView pressureInfo;
        TextView humidityInfo;


        public ViewHolder(View v) {
            super(v);
            weatherIcon = (ImageView) v.findViewById(R.id.weatherIcon);
            day = (TextView) v.findViewById(R.id.day);
            weatherInfo = (TextView) v.findViewById(R.id.weatherInfo);
            pressureInfo = (TextView) v.findViewById(R.id.pressureInfo);
            humidityInfo = (TextView) v.findViewById(R.id.humidityInfo);

        }
    }
}