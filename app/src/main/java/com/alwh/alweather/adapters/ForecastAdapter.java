package com.alwh.alweather.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
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

import static com.alwh.alweather.helpers.ConvertData.getTextWeather;

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

        holder.cardView.setCardBackgroundColor(128);
        Picasso.with(context)
                .load(ConvertData.getIconWeatherL(sqLiteForecastData.getForecast().get(position).getWeatherIcon(), context))
                .resize(0, 150)
                .into(holder.weatherIcon);
        holder.day.setText(ConvertData.getStandartDate(sqLiteForecastData.getForecast().get(position).getDt()));
        holder.dayTemp.setText("    temp: " + String.valueOf(Math.round(sqLiteForecastData.getForecast().get(position).getTemperature())) + "°");
        holder.weatherInfo.setText(getTextWeather(sqLiteForecastData.getForecast().get(position).getWeatherID(), context));
        holder.pressureInfo.setText("    pressure: " + sqLiteForecastData.getForecast().get(position).getPressure() + " hPa");
        holder.windDegInfo.setText("wind (deg): " + (int) sqLiteForecastData.getForecast().get(position).getWindDeg() + "°");
        holder.windSpeedInfo.setText("    wind (speed): " + sqLiteForecastData.getForecast().get(position).getWindSpeed() + " km/s");
    }

    @Override
    public int getItemCount() {
        if (sqLiteForecastData == null) return 0;
        return sqLiteForecastData.getForecast().size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView weatherIcon;
        private TextView day;
        private TextView weatherInfo;
        private TextView pressureInfo;
        private TextView humidityInfo;
        private TextView dayTemp;
        private TextView windDegInfo;
        private TextView windSpeedInfo;
        private CardView cardView;


        public ViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.card_view);
            weatherIcon = (ImageView) v.findViewById(R.id.weatherIcon);
            day = (TextView) v.findViewById(R.id.day);
            dayTemp = (TextView) v.findViewById(R.id.dayTemp);
            weatherInfo = (TextView) v.findViewById(R.id.weatherInfo);
            pressureInfo = (TextView) v.findViewById(R.id.pressureInfo);
            humidityInfo = (TextView) v.findViewById(R.id.humidityInfo);
            windDegInfo = (TextView) v.findViewById(R.id.wind_deg);
            windSpeedInfo = (TextView) v.findViewById(R.id.wind_speed);
        }
    }
}