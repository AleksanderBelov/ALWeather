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
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.TimeZone;


public class DailyListAdapter extends RecyclerView.Adapter<DailyListAdapter.ViewHolder> {
    private SQLiteForecastData sqLiteForecastData;
    protected Context context;

    public DailyListAdapter(Context context, SQLiteForecastData sqLiteForecastData) {
        this.sqLiteForecastData = sqLiteForecastData;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View View = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_daily_list, parent, false);
        return new ViewHolder(View);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SimpleDateFormat sdf = new SimpleDateFormat("E");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        holder.dayOfWeek.setText(sdf.format(sqLiteForecastData.getForecast().get(position).getDt()));

        sdf = new SimpleDateFormat("HH:");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        String tt = sdf.format(sqLiteForecastData.getForecast().get(position).getDt()) + "00";
        holder.time.setText(tt);


        Picasso.with(context)
                .load("http://openweathermap.org/img/w/" + sqLiteForecastData.getForecast().get(position).getWeatherIcon() + ".png")
                .into(holder.weatherIcon);

        holder.weatherResult.setText(String.valueOf(Math.round(sqLiteForecastData.getForecast().get(position).getTemperature())) + "°");
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView dayOfWeek;
        ImageView weatherIcon;
        TextView weatherResult;
        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            dayOfWeek = (TextView) itemView.findViewById(R.id.day_of_week);
            weatherIcon = (ImageView) itemView.findViewById(R.id.weather_icon);
            weatherResult = (TextView) itemView.findViewById(R.id.weather_result);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}


