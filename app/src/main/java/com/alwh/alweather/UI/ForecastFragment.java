package com.alwh.alweather.UI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alwh.alweather.R;
import com.alwh.alweather.adapters.ForecastAdapter;
import com.alwh.alweather.database.SQLiteForecastData;
import com.alwh.alweather.database.SQLiteWeatherData;
import com.alwh.alweather.service.AlWeatherService;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import org.parceler.Parcels;

import static com.alwh.alweather.helpers.AppRoot.FORECAST;
import static com.alwh.alweather.helpers.AppRoot.NEW_WEATHER;
import static com.alwh.alweather.helpers.AppRoot.QUESTION_TO_SERVECE;
import static com.alwh.alweather.helpers.AppRoot.TRANSFER_SAVE_FORECAST;
import static com.alwh.alweather.helpers.AppRoot.WEATHER;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link .} interface
 * to handle interaction events.
 * Use the {@link ForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForecastFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ForecastAdapter forecastAdapter;
    View forecastFragmentView;
    Context _context;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ForecastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForecastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForecastFragment newInstance(String param1, String param2) {
        ForecastFragment fragment = new ForecastFragment();
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

        getActivity()
                .startService(new Intent(getActivity(), AlWeatherService.class).
                        putExtra(QUESTION_TO_SERVECE, TRANSFER_SAVE_FORECAST));

        BroadcastReceiver br;
        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                int key = intent.getIntExtra("key", 0);
                switch (key){
                    case 0: Log.d("", "Error get key from intent");
                        break;
                    case 1:
                        break;
                    case 3: initDataList((SQLiteForecastData) Parcels.unwrap(intent.getParcelableExtra(FORECAST)));
                        break;
                }
            }
        };

        IntentFilter intFilt = new IntentFilter(NEW_WEATHER);
        _context.registerReceiver(br, intFilt);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        _context = context;
    }

    public static ForecastFragment newInstance(String text) {

        ForecastFragment f = new ForecastFragment();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        forecastFragmentView =  inflater.inflate(R.layout.fragment_forecast, container, false);

        getActivity()
                .startService(new Intent(getActivity(), AlWeatherService.class).
                        putExtra(QUESTION_TO_SERVECE, TRANSFER_SAVE_FORECAST));

        mRecyclerView = (RecyclerView) forecastFragmentView.findViewById(R.id.forecast_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(forecastFragmentView.getContext()));
        mRecyclerView.setHasFixedSize(true);




        return forecastFragmentView;

    }
    public void initDataList(SQLiteForecastData sqLiteForecastData) {


      //  forecastAdapter = new ForecastAdapter(forecastFragmentView.getContext(),sqLiteForecastData);
       // mRecyclerView.setAdapter(forecastAdapter);

    }
}
