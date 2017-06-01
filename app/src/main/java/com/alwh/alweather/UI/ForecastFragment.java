package com.alwh.alweather.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alwh.alweather.R;
import com.alwh.alweather.adapters.ForecastAdapter;
import com.alwh.alweather.database.SQLiteForecastData;
import com.alwh.alweather.model.MessageEvent;
import com.alwh.alweather.service.AlWeatherService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static com.alwh.alweather.helpers.AppRoot.QUESTION_TO_SERVECE;
import static com.alwh.alweather.helpers.AppRoot.TRANSFER_SAVE_FORECAST;

public class ForecastFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView mRecyclerView;
    private ForecastAdapter forecastAdapter;
    private View forecastFragmentView;
    private Context _context;


    private String mParam1;
    private String mParam2;

    public ForecastFragment() {
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

        forecastFragmentView = inflater.inflate(R.layout.fragment_forecast, container, false);
        EventBus.getDefault().register(this);
        getActivity()
                .startService(new Intent(getActivity(), AlWeatherService.class).
                        putExtra(QUESTION_TO_SERVECE, TRANSFER_SAVE_FORECAST));
        mRecyclerView = (RecyclerView) forecastFragmentView.findViewById(R.id.forecast_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(forecastFragmentView.getContext()));
        mRecyclerView.setHasFixedSize(true);
        return forecastFragmentView;

    }

    private void initDataList(SQLiteForecastData sqLiteForecastData) {
        forecastAdapter = new ForecastAdapter(_context, sqLiteForecastData);
        mRecyclerView.setAdapter(forecastAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _context = context;
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onEvent(MessageEvent event) {
        if (event.type == 2) {
            initDataList(event.sqLiteForecastData);
        }
    }
}
