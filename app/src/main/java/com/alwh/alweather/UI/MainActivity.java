package com.alwh.alweather.UI;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;
import com.alwh.alweather.R;
import com.alwh.alweather.database.SQLiteForecastData;
import com.alwh.alweather.database.SQLiteWeatherData;
import com.alwh.alweather.model.MessageEvent;
import com.alwh.alweather.model.SwipeDetector;
import com.alwh.alweather.service.AlWeatherService;
import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import static com.alwh.alweather.helpers.AppRoot.FORECAST;
import static com.alwh.alweather.helpers.AppRoot.NEW_WEATHER;
import static com.alwh.alweather.helpers.AppRoot.QUESTION_TO_SERVECE;
import static com.alwh.alweather.helpers.AppRoot.TRANSFER_NEW_WEATHER;
import static com.alwh.alweather.helpers.AppRoot.TRANSFER_SAVE_FORECAST;
import static com.alwh.alweather.helpers.AppRoot.TRANSFER_SAVE_WEATHER;
import static com.alwh.alweather.helpers.AppRoot.WEATHER;

public class MainActivity extends Activity {

    private WeatherFragment weatherFragment;
    private ForecastFragment forecastFragment;
    private ConfigureFragment configureFragment;
    private BroadcastReceiver br;
    private Context context;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        startService(new Intent(this, AlWeatherService.class).
                putExtra(QUESTION_TO_SERVECE, TRANSFER_SAVE_WEATHER));
        startService(new Intent(this, AlWeatherService.class).
                putExtra(QUESTION_TO_SERVECE, TRANSFER_SAVE_FORECAST));
        weatherFragment = new WeatherFragment();
        forecastFragment = new ForecastFragment();
        configureFragment = new ConfigureFragment();
        gestureDetector = initGestureDetector();
        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                int key = intent.getIntExtra("key", 0);
                switch (key) {
                    case 0:
                        break;
                    case 1:

                        EventBus.getDefault().post(
                                new MessageEvent(
                                        (SQLiteWeatherData) Parcels.unwrap(intent.getParcelableExtra(WEATHER))));
                        break;
                    case 3:
                        EventBus.getDefault().post(
                                new MessageEvent(
                                        (SQLiteForecastData) Parcels.unwrap(intent.getParcelableExtra(FORECAST))));
                }
            }
        };
        IntentFilter intFilt = new IntentFilter(NEW_WEATHER);
        registerReceiver(br, intFilt);
        showFragment(weatherFragment);
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = MainActivity.this.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction
                .replace(R.id.frame_conteiner, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);
        return gestureDetector.onTouchEvent(event);
    }

    private GestureDetector initGestureDetector() {
        return new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            private SwipeDetector detector = new SwipeDetector();

            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {
                try {
                    if (detector.isSwipeDown(e1, e2, velocityY)) {
                        downSwipe();
                    } else if (detector.isSwipeUp(e1, e2, velocityY)) {
                        downSwipe();
                    } else if (detector.isSwipeLeft(e1, e2, velocityX)) {
                        leftSwipe();
                    } else if (detector.isSwipeRight(e1, e2, velocityX)) {
                        rightSwipe();
                    }
                } catch (Exception e) {
                }
                return false;
            }
        });
    }

    private void rightSwipe() {
        Fragment f = getFragmentManager().findFragmentById(R.id.frame_conteiner);
        if (f instanceof ForecastFragment) {
            showFragment(weatherFragment);
        }
        if (f instanceof WeatherFragment)
            showFragment(configureFragment);
    }

    private void leftSwipe() {
        Fragment f = getFragmentManager().findFragmentById(R.id.frame_conteiner);
        if (f instanceof WeatherFragment) {
            showFragment(forecastFragment);
        }
        if (f instanceof ConfigureFragment)
            showFragment(weatherFragment);
    }

    private void downSwipe() {
        Fragment f = getFragmentManager().findFragmentById(R.id.frame_conteiner);
        if (!(f instanceof ForecastFragment)) {
            Toast.makeText(getApplicationContext(), "update", Toast.LENGTH_SHORT).show();
            startService(new Intent(context, AlWeatherService.class).
                    putExtra(QUESTION_TO_SERVECE, TRANSFER_NEW_WEATHER));
        }
    }

    @Override
    public void onBackPressed() {
        showFragment(weatherFragment);
    }
}