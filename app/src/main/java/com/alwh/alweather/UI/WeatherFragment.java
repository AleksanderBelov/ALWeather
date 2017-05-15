package com.alwh.alweather.UI;


import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alwh.alweather.R;
import com.alwh.alweather.adapters.DailyListAdapter;
import com.alwh.alweather.database.SQLiteForecastData;
import com.alwh.alweather.database.SQLiteWeatherData;
import com.alwh.alweather.helpers.AppRoot;
import com.alwh.alweather.helpers.ConvertData;
import com.alwh.alweather.model.ControlService;
import com.alwh.alweather.service.AlWeatherService;
import com.squareup.picasso.Picasso;

import static com.alwh.alweather.helpers.AppRoot.QUESTION_TO_SERVECE;
import static com.alwh.alweather.helpers.AppRoot.TRANSFER_NEW_WEATHER;
import static com.alwh.alweather.helpers.AppRoot.TRANSFER_SAVE_FORECAST;
import static com.alwh.alweather.helpers.AppRoot.TRANSFER_SAVE_WEATHER;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {

    final String TAG = "AlWeather/W_Fragment ";

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

        weatherFragmentView = inflater.inflate(R.layout.fragment_weather, container, false);




        Button button = (Button) weatherFragmentView.findViewById(R.id.renew);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d(TAG, "onClickgetRenew" + QUESTION_TO_SERVECE + TRANSFER_NEW_WEATHER);
                getActivity()
                        .startService(new Intent(getActivity(), AlWeatherService.class).
                                putExtra(QUESTION_TO_SERVECE, TRANSFER_NEW_WEATHER));
            }
        });


        initView();

        getActivity()
                .startService(new Intent(getActivity(), AlWeatherService.class).
                        putExtra(QUESTION_TO_SERVECE, TRANSFER_SAVE_WEATHER));
        getActivity()
                .startService(new Intent(getActivity(), AlWeatherService.class).
                        putExtra(QUESTION_TO_SERVECE, TRANSFER_SAVE_FORECAST));
//        initData(false);

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

        recyclerView = (RecyclerView) weatherFragmentView.findViewById(R.id.weather_daily_list);
        recyclerView.setLayoutManager(new GridLayoutManager(weatherFragmentView.getContext(), 5));
        recyclerView.setHasFixedSize(true);
    }

    public void initData(SQLiteWeatherData sqLiteWeatherData) {

        temperatureMain.setText("" + sqLiteWeatherData.getTemperature());
        cityName.setText(sqLiteWeatherData.getCityName() + ", " + sqLiteWeatherData.getCountry());

        currentDate.setText("last change: " + ConvertData.passedMin(sqLiteWeatherData.getDt()) + " min age");

        windMain.setText(sqLiteWeatherData.getWindSpeed() + " km/h");
        humidityMain.setText(sqLiteWeatherData.getHumidity() + "%");

        Picasso.with(getActivity())
                .load("http://openweathermap.org/img/w/" + sqLiteWeatherData.getWeatherIcon() + ".png")
                .resize(0, 220)
                .into(weatherIconMain);

//        dailyListAdapter = new DailyListAdapter(getActivity(), controlService.getForecast(false));
 //       recyclerView.setAdapter(dailyListAdapter);

    }
    public void initDataList(SQLiteForecastData sqLiteForecastData) {



        dailyListAdapter = new DailyListAdapter(weatherFragmentView.getContext(), sqLiteForecastData);
        recyclerView.setAdapter(dailyListAdapter);

    }

}
