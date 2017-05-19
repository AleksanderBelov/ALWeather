package com.alwh.alweather.UI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
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
import com.alwh.alweather.helpers.ConvertData;
import com.alwh.alweather.service.AlWeatherService;
import com.squareup.picasso.Picasso;
import android.app.Fragment;
import org.parceler.Parcels;

import static com.alwh.alweather.helpers.AppRoot.FORECAST;
import static com.alwh.alweather.helpers.AppRoot.NEW_WEATHER;
import static com.alwh.alweather.helpers.AppRoot.QUESTION_TO_SERVECE;
import static com.alwh.alweather.helpers.AppRoot.TRANSFER_NEW_WEATHER;
import static com.alwh.alweather.helpers.AppRoot.TRANSFER_SAVE_FORECAST;
import static com.alwh.alweather.helpers.AppRoot.TRANSFER_SAVE_WEATHER;
import static com.alwh.alweather.helpers.AppRoot.WEATHER;

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
    TextView windMain;
    TextView humidityMain;
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


/*        weatherFragmentView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    Log.d(TAG, "move");
                }
                return true;
            }
        });
*/


        Button button = (Button) weatherFragmentView.findViewById(R.id.renew);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    /*            Log.d(TAG, "onClickgetRenew" + QUESTION_TO_SERVECE + TRANSFER_NEW_WEATHER);
                getActivity()
                        .startService(new Intent(getActivity(), AlWeatherService.class).
                                putExtra(QUESTION_TO_SERVECE, TRANSFER_NEW_WEATHER));
                getActivity().onClick;
*/
                someEventListener.someEvent(2);
            }
        });


        initView();

        getActivity()
                .startService(new Intent(getActivity(), AlWeatherService.class).
                        putExtra(QUESTION_TO_SERVECE, TRANSFER_SAVE_WEATHER));
        getActivity()
                .startService(new Intent(getActivity(), AlWeatherService.class).
                        putExtra(QUESTION_TO_SERVECE, TRANSFER_SAVE_FORECAST));
        //     initData(sqLiteForecastData);


/*        BroadcastReceiver br;
        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                int key = intent.getIntExtra("key", 0);
                switch (key) {
                    case 0:
                        Log.d(TAG, "Error get key from intent");
                        break;
                    case 1:
                        initData((SQLiteWeatherData) Parcels.unwrap(intent.getParcelableExtra(WEATHER)));

                        break;
                    case 3:
                        sqLiteForecastData = Parcels.unwrap(intent.getParcelableExtra(FORECAST));
                 //       initDataList(sqLiteForecastData);
                        //               forecastFragment.initDataList(sqLiteForecastData);
                        break;
                }
            }
        };

        IntentFilter intFilt = new IntentFilter(NEW_WEATHER);
        _context.registerReceiver(br, intFilt);
*/

     /*   Fragment newFragment = new ForecastFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.ll, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();

*/
        return weatherFragmentView;
    }


    public void initView() {
        Log.d(TAG, "initView");
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

        Log.d(TAG, "initData");
        temperatureMain.setText("" + sqLiteWeatherData.getTemperature());
        cityName.setText(sqLiteWeatherData.getCityName() + ", " + sqLiteWeatherData.getCountry());

        currentDate.setText("last change: " + ConvertData.passedMin(sqLiteWeatherData.getDt()) + " min age");

        windMain.setText(sqLiteWeatherData.getWindSpeed() + " km/h");
        humidityMain.setText(sqLiteWeatherData.getHumidity() + "%");

        Picasso.with(getActivity())
                .load("http://openweathermap.org/img/w/" + sqLiteWeatherData.getWeatherIcon() + ".png")
                .resize(0, 220)
                .into(weatherIconMain);

    }

    public void initDataList(SQLiteForecastData sqLiteForecastData) {
        dailyListAdapter = new DailyListAdapter(weatherFragmentView.getContext(), sqLiteForecastData);
        recyclerView.setAdapter(dailyListAdapter);
    }

}
