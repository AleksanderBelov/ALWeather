package com.alwh.alweather.UI;


import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alwh.alweather.R;
import com.alwh.alweather.adapters.DailyListAdapter;
import com.alwh.alweather.database.SQLiteWeatherData;
import com.alwh.alweather.helpers.AppRoot;
import com.alwh.alweather.helpers.ConvertData;
import com.alwh.alweather.model.ControlService;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {

    ControlService controlService;
    TextView temperatureMain;
    TextView cityName;
    TextView currentDate;
    TextView windMain;
    TextView humidityMain;
    ImageView weatherIconMain;
    DailyListAdapter dailyListAdapter;
    RecyclerView recyclerView;
    View weatherFragmentView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public WeatherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeatherFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        // Inflate the layout for this fragment
        weatherFragmentView = inflater.inflate(R.layout.fragment_weather, container, false);

     //   AppRoot app = (AppRoot) getActivity().getApplication();
     //   controlService = app.getControlService();

        initView();
        initData(false);

        return weatherFragmentView;
    }

    public void initView() {
        temperatureMain = (TextView) weatherFragmentView.findViewById(R.id.temperatureMain);
        temperatureMain.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/dsdigib.ttf"));

        cityName = (TextView) weatherFragmentView.findViewById(R.id.cityName);
        cityName.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/dsdigib.ttf"));

        currentDate = (TextView) weatherFragmentView.findViewById(R.id.currentDate);
        //     currentDate.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/dsdigib.ttf"));

        temperatureMain = (TextView) weatherFragmentView.findViewById(R.id.temperatureMain);
        temperatureMain.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/dsdigib.ttf"));

        windMain = (TextView) weatherFragmentView.findViewById(R.id.windMain);
        humidityMain = (TextView) weatherFragmentView.findViewById(R.id.humidityMain);
        weatherIconMain = (ImageView) weatherFragmentView.findViewById(R.id.weatherIconMain);

     //   GridLayoutManager gridLayoutManager = new GridLayoutManager();
        recyclerView = (RecyclerView) weatherFragmentView.findViewById(R.id.weather_daily_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),5));
        recyclerView.setHasFixedSize(true);
    }

    public void initData(boolean renew) {
        SQLiteWeatherData sqLiteWeatherData;

        sqLiteWeatherData = controlService.getWeather(renew);
        temperatureMain.setText("" + sqLiteWeatherData.getTemperature());
        cityName.setText(sqLiteWeatherData.getCityName() + ", " + sqLiteWeatherData.getCountry());

        currentDate.setText("last change: " + ConvertData.passedMin(sqLiteWeatherData.getDt()) + " min age");

        windMain.setText(sqLiteWeatherData.getWindSpeed() + " km/h");
        humidityMain.setText(sqLiteWeatherData.getHumidity() + "%");

        Picasso.with(getActivity())
                .load("http://openweathermap.org/img/w/" + sqLiteWeatherData.getWeatherIcon() + ".png")
                .resize(0, 220)
                .into(weatherIconMain);

        dailyListAdapter = new DailyListAdapter(getActivity(), controlService.getForecast(false));
        recyclerView.setAdapter(dailyListAdapter);

    }

}
