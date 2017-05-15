package com.alwh.alweather.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alwh.alweather.R;
import com.alwh.alweather.adapters.ForecastAdapter;
import com.alwh.alweather.helpers.AppRoot;
import com.alwh.alweather.model.ControlService;

public class ForecastActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ForecastAdapter fAdapter;
    ControlService controlService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        AppRoot app = (AppRoot) getApplication();
        controlService = app.getControlService();


        mRecyclerView = (RecyclerView) findViewById(R.id.forecast_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        fAdapter = new ForecastAdapter(this, controlService.getForecast(false));
    //   mRecyclerView.setAdapter(fAdapter);
    }
}
