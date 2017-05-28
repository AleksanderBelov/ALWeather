package com.alwh.alweather.adapters;

import android.content.Context;
import android.graphics.Typeface;
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
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static com.alwh.alweather.helpers.AppRoot.PART_DAY;


public class DailyListAdapter extends RecyclerView.Adapter<DailyListAdapter.ViewHolder> {
    private SQLiteForecastData sqLiteForecastData;
    protected Context context;
    private int weather;

    public DailyListAdapter(Context context, SQLiteForecastData sqLiteForecastData, int weather) {
        this.sqLiteForecastData = sqLiteForecastData;
        this.context = context;
        this.weather = weather;
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

//String uri = "@drawable/l" + sqLiteForecastData.getForecast().get(position).getWeatherIcon();
//      int icon = getResources().getIdentifier(uri, null, context.getPackageName());




        int a = holder.weatherIcon.getWidth();

        Picasso.with(context)
                .load(ConvertData.getIconWeatherL(sqLiteForecastData.getForecast().get(position).getWeatherIcon(),context))
                .resize(0, weather/PART_DAY)
                .into(holder.weatherIcon);

        holder.weatherResult.setText(String.valueOf(Math.round(sqLiteForecastData.getForecast().get(position).getTemperature())) + "°");
    }

    @Override
    public int getItemCount() {
        return PART_DAY;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView dayOfWeek;
        ImageView weatherIcon;
        TextView weatherResult;
        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            dayOfWeek = (TextView) itemView.findViewById(R.id.day_of_week);
            dayOfWeek.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/corkiRegular.ttf"));

            weatherIcon = (ImageView) itemView.findViewById(R.id.weather_icon);


            weatherResult = (TextView) itemView.findViewById(R.id.weather_result);
            weatherResult.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/corkiRegular.ttf"));

            time = (TextView) itemView.findViewById(R.id.time);
            time.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/corkiRegular.ttf"));
        }
    }
}


