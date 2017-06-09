package com.alwh.alweather.UI;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alwh.alweather.R;
import com.alwh.alweather.adapters.DailyListAdapter;
import com.alwh.alweather.database.SQLiteForecastData;
import com.alwh.alweather.database.SQLiteWeatherData;
import com.alwh.alweather.helpers.ConvertData;
import com.alwh.alweather.model.MessageEvent;
import com.alwh.alweather.service.AlWeatherService;
import com.squareup.picasso.Picasso;

import android.app.Fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static com.alwh.alweather.helpers.AppRoot.PART_DAY;
import static com.alwh.alweather.helpers.AppRoot.QUESTION_TO_SERVECE;
import static com.alwh.alweather.helpers.AppRoot.TRANSFER_SAVE_FORECAST;
import static com.alwh.alweather.helpers.AppRoot.TRANSFER_SAVE_WEATHER;
import static com.alwh.alweather.helpers.ConvertData.getDeg;
import static com.alwh.alweather.helpers.ConvertData.getTextWeather;

public class WeatherFragment extends Fragment {

    private TextView temperatureMain;
    private TextView cityName;
    private TextView currentDate;
    private TextView windDeg;
    private TextView humidity;
    private TextView weatherInformation;
    private TextView windSpeed;
    private TextView atmosphericPressure;
    private TextView sunrise;
    private TextView sunset;
    private ImageView weatherIconMain;
    private DailyListAdapter dailyListAdapter;
    private RecyclerView recyclerView;
    private View weatherFragmentView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public WeatherFragment() {
    }

    public static WeatherFragment newInstance(String param1, String param2) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        weatherFragmentView = inflater.inflate(R.layout.fragment_weather, container, false);
        EventBus.getDefault().register(this);
        initView();
        getActivity()
                .startService(new Intent(getActivity(), AlWeatherService.class).
                        putExtra(QUESTION_TO_SERVECE, TRANSFER_SAVE_WEATHER));
        getActivity()
                .startService(new Intent(getActivity(), AlWeatherService.class).
                        putExtra(QUESTION_TO_SERVECE, TRANSFER_SAVE_FORECAST));
        return weatherFragmentView;
    }


    private void initView() {
        cityName = (TextView) weatherFragmentView.findViewById(R.id.cityName);
        //    cityName.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/corkiRegular.ttf"));
        currentDate = (TextView) weatherFragmentView.findViewById(R.id.currentDate);
        //    currentDate.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/corkiRegular.ttf"));
        temperatureMain = (TextView) weatherFragmentView.findViewById(R.id.temperatureMain);
        //    temperatureMain.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/corkiRegular.ttf"));
        weatherInformation = (TextView) weatherFragmentView.findViewById(R.id.weatherInformation);
        //  weatherInformation.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/corkiRegular.ttf"));
        atmosphericPressure = (TextView) weatherFragmentView.findViewById(R.id.atmosphericPressure);
        //  atmosphericPressure.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/corkiRegular.ttf"));
        windDeg = (TextView) weatherFragmentView.findViewById(R.id.wind_deg);
        windSpeed = (TextView) weatherFragmentView.findViewById(R.id.windSpeed);
        humidity = (TextView) weatherFragmentView.findViewById(R.id.humidity);
        sunrise = (TextView) weatherFragmentView.findViewById(R.id.sunrise);
        sunset = (TextView) weatherFragmentView.findViewById(R.id.sunset);
        weatherIconMain = (ImageView) weatherFragmentView.findViewById(R.id.weatherIconMain);
        recyclerView = (RecyclerView) weatherFragmentView.findViewById(R.id.weather_daily_list);
        recyclerView.setLayoutManager(new GridLayoutManager(weatherFragmentView.getContext(), PART_DAY));
        recyclerView.setHasFixedSize(true);
    }

    private void initData(SQLiteWeatherData sqLiteWeatherData) {

        temperatureMain.setText("" + (int) sqLiteWeatherData.getTemperature() + (char) 176 + "C");
        cityName.setText(sqLiteWeatherData.getCityName() + ", " + sqLiteWeatherData.getCountry());
        currentDate.setText(getString(R.string.last_time_begin) + " "
                + ConvertData.passedMin(sqLiteWeatherData.getDt()) + " "
                + getString(R.string.last_time_end));
        Picasso.with(getActivity())
                .load(ConvertData.getIconWeatherL(sqLiteWeatherData.getWeatherIcon(), getActivity()))
                .into(weatherIconMain);
        weatherInformation.setText(getTextWeather(sqLiteWeatherData.getWeatherID(), getActivity()));
        atmosphericPressure.setText(getString(R.string.atmospheric_pressure) + " " +
                ConvertData.getPressure(sqLiteWeatherData.getPressure(), getString(R.string.atmospheric_pressure_unit)) + " " +
                getString(R.string.atmospheric_pressure_unit));
        windDeg.setText(getString(R.string.wind) + " "
                + getDeg(sqLiteWeatherData.getWindDeg(), getActivity()));
        windSpeed.setText(getString(R.string.wind_speed_begin) + " "
                + sqLiteWeatherData.getWindSpeed() + " "
                + getString(R.string.wind_speed_end));
        humidity.setText(getString(R.string.humidity) + " " + sqLiteWeatherData.getHumidity() + "%");
        sunrise.setText(getString(R.string.sunrise) + " " + ConvertData.getTime(sqLiteWeatherData.getSunrise()));
        sunset.setText(getString(R.string.sunset) + " " + ConvertData.getTime(sqLiteWeatherData.getSunset()));
    }

    private void initDataList(SQLiteForecastData sqLiteForecastData) {
        dailyListAdapter = new DailyListAdapter(weatherFragmentView.getContext(), sqLiteForecastData, weatherFragmentView.getWidth());
        recyclerView.setAdapter(dailyListAdapter);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onEvent(MessageEvent event) {

        if (event.type == 1) {
            initData(event.sqLiteWeatherData);
        }
        if (event.type == 2) {
            initDataList(event.sqLiteForecastData);
        }
    }
}
