package com.alwh.alweather.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.alwh.alweather.R;
import com.alwh.alweather.adapters.DailyListAdapter;
import com.alwh.alweather.database.SQLiteForecastData;
import com.alwh.alweather.database.SQLiteWeatherData;
import com.alwh.alweather.helpers.ConvertData;
import com.alwh.alweather.model.MessageEvent;
import com.alwh.alweather.model.SelectShow;
import com.alwh.alweather.model.SwipeDirectionDetector;
import com.alwh.alweather.service.AlWeatherService;
import com.squareup.picasso.Picasso;
import android.app.Fragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static com.alwh.alweather.helpers.AppRoot.PART_DAY;
import static com.alwh.alweather.helpers.AppRoot.QUESTION_TO_SERVECE;
import static com.alwh.alweather.helpers.AppRoot.TRANSFER_SAVE_FORECAST;
import static com.alwh.alweather.helpers.AppRoot.TRANSFER_SAVE_WEATHER;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class WeatherFragment extends Fragment {

    final String TAG = "AlWeather/W_Fragment ";

    TextView temperatureMain;
    TextView cityName;
    TextView currentDate;
    TextView windDeg;
    TextView humidity;
    TextView weatherInformation;
    TextView windSpeed;
    TextView atmosphericPressure;
    TextView sunrise;
    TextView sunset;
    ImageView weatherIconMain;
    DailyListAdapter dailyListAdapter;
    RecyclerView recyclerView;
    View weatherFragmentView;
    Context _context;
    SQLiteWeatherData sqLiteWeatherData;
    SQLiteForecastData sqLiteForecastData;


    // TODO: Rename parameter arguments, choose names that match

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public interface onSomeEventListener {
        public void someEvent(int page);
    }

    onSomeEventListener someEventListener;


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
        Log.d(TAG, "inCreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

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


    public void initView() {
        Log.d(TAG, "initView");
        cityName = (TextView) weatherFragmentView.findViewById(R.id.cityName);
        cityName.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/corkiRegular.ttf"));

        currentDate = (TextView) weatherFragmentView.findViewById(R.id.currentDate);
        currentDate.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/corkiRegular.ttf"));

        temperatureMain = (TextView) weatherFragmentView.findViewById(R.id.temperatureMain);
        temperatureMain.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/corkiRegular.ttf"));

        weatherInformation = (TextView) weatherFragmentView.findViewById(R.id.weatherInformation);
        weatherInformation.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/corkiRegular.ttf"));

        atmosphericPressure = (TextView) weatherFragmentView.findViewById(R.id.atmosphericPressure);
        atmosphericPressure.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/corkiRegular.ttf"));

        windDeg = (TextView) weatherFragmentView.findViewById(R.id.wind_deg);
        windDeg.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/corkiRegular.ttf"));

        windSpeed = (TextView) weatherFragmentView.findViewById(R.id.windSpeed);
        windSpeed.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/corkiRegular.ttf"));

        humidity = (TextView) weatherFragmentView.findViewById(R.id.humidity);
        humidity.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/corkiRegular.ttf"));

        sunrise = (TextView) weatherFragmentView.findViewById(R.id.sunrise);
        sunrise.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/corkiRegular.ttf"));

        sunset = (TextView) weatherFragmentView.findViewById(R.id.sunset);
        sunset.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/corkiRegular.ttf"));
        weatherIconMain = (ImageView) weatherFragmentView.findViewById(R.id.weatherIconMain);

        recyclerView = (RecyclerView) weatherFragmentView.findViewById(R.id.weather_daily_list);
        recyclerView.setLayoutManager(new GridLayoutManager(weatherFragmentView.getContext(), PART_DAY));
        recyclerView.setHasFixedSize(true);
    }


    public void initData(SQLiteWeatherData sqLiteWeatherData) {

        Log.d(TAG, "initData");
        temperatureMain.setText("" + (int)sqLiteWeatherData.getTemperature() + (char)176 + "C");
        cityName.setText(sqLiteWeatherData.getCityName() + ", " + sqLiteWeatherData.getCountry());

        currentDate.setText("last change: " + ConvertData.passedMin(sqLiteWeatherData.getDt()) + " min age");

        Picasso.with(getActivity())
                .load(ConvertData.getIconWeatherL(sqLiteWeatherData.getWeatherIcon(),getActivity()))
//                .load(getResources().getIdentifier("@drawable/l" + sqLiteWeatherData.getWeatherIcon(), null, getActivity().getPackageName()))
       //       .fit()
                //  .resize(0, 220)
                .into(weatherIconMain);


//        weatherInformation.setText(getString(ConvertData.getWeatherInfo(sqLiteWeatherData.getWeatherDescription(),getActivity())) + " ");
        weatherInformation.setText(sqLiteWeatherData.getWeatherDescription());
        atmosphericPressure.setText(getString(R.string.atmospheric_pressure) + " " + sqLiteWeatherData.getPressure());
        windDeg.setText(getString(R.string.wind) + " " + sqLiteWeatherData.getWindDeg());
        windSpeed.setText(getString(R.string.wind_speed) + " " + sqLiteWeatherData.getWindSpeed() + "km/h");
        humidity.setText(getString(R.string.humidity) + " " + sqLiteWeatherData.getHumidity() + "%");
        sunrise.setText(getString(R.string.sunrise) + " " + ConvertData.getTime(sqLiteWeatherData.getSunrise()));
        sunset.setText(getString(R.string.sunset) + " " + ConvertData.getTime(sqLiteWeatherData.getSunset()));
    }

    public void initDataList(SQLiteForecastData sqLiteForecastData) {
        dailyListAdapter = new DailyListAdapter(weatherFragmentView.getContext(), sqLiteForecastData, weatherFragmentView.getWidth());
        recyclerView.setAdapter(dailyListAdapter);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onEvent(MessageEvent event){
        Log.d(TAG, "onEvent");
        if (event.type == 1) {
            initData(event.sqLiteWeatherData);
        }
        if (event.type == 2) {
            initDataList(event.sqLiteForecastData);
        }
    }
}
