package com.alwh.alweather.UI;
import android.app.ProgressDialog;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.alwh.alweather.R;
import com.alwh.alweather.adapters.CityListAdapter;
import com.alwh.alweather.database.SQLiteAlWeatherConfig;
import com.alwh.alweather.json.CityList;
import com.alwh.alweather.json.weather.Coord;
import com.alwh.alweather.model.GpsInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gson.stream.JsonReader;

import static com.alwh.alweather.helpers.AppRoot.LOCATION_ERROR_MESSAGE;
import static com.alwh.alweather.helpers.AppRoot.MAX_CITY_COUNT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfigureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigureFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "Configure";
    private AutoCompleteTextView addLocation;
    private DataSourceCity dataSourceCity;
    private static List<CityList> mData;
    private CityListAdapter cityListAdapter;
    private List<CityList> allCityList;
    private ProgressDialog mProgress;
    private SQLiteAlWeatherConfig sqLiteAlWeatherConfig;
    private Spinner spinnerF;
    private Spinner spinnerW;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View configureFragmentView;


    public ConfigureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfigureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfigureFragment newInstance(String param1, String param2) {
        ConfigureFragment fragment = new ConfigureFragment();
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

        configureFragmentView = inflater.inflate(R.layout.fragment_configure, container, false);
        addLocation = (AutoCompleteTextView) configureFragmentView.findViewById(R.id.new_location);
        addLocation.setText("Kiev, UA");
        addLocation.setEnabled(false);

        String[] period = {"5 min", "10 min", "30 min", "1 hour", "2 hour", "manual"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, period);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerW = (Spinner) configureFragmentView.findViewById(R.id.spinnerW);
        spinnerW.setAdapter(adapter);
        spinnerW.setSelection(2);

        spinnerW.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.d(TAG, "Position = " + position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });



        spinnerF = (Spinner) configureFragmentView.findViewById(R.id.spinnerF);
        spinnerF.setAdapter(adapter);
        spinnerF.setPrompt("Title");
        spinnerF.setSelection(2);
        spinnerF.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.d(TAG, "Position = " + position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        initConfig();




        final Button addLocationButton = (Button) configureFragmentView.findViewById(R.id.add_location_button);
        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dataSourceCity = new DataSourceCity();
                dataSourceCity.execute();

                String enteredLocation = addLocation.getText().toString();
                Log.d(TAG,enteredLocation);
                if (TextUtils.isEmpty(enteredLocation)) {
                    Toast.makeText(getActivity(), LOCATION_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
        final Button gpsLocationButton = (Button) configureFragmentView.findViewById(R.id.gps_location_button);
        gpsLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GpsInfo gpsInfo = new GpsInfo(getActivity());
                Coord location = gpsInfo.getLocation();
                addLocation.setText(location.getLat() + "," + location.getLon());
                }
        });

        final Button saveConfigButton = (Button) configureFragmentView.findViewById(R.id.saveConfig);
        gpsLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteAlWeatherConfig sqLiteAlWeatherConfig = new SQLiteAlWeatherConfig(addLocation.getText().toString(), 300000, 3000000, 27.911619, -33.015289);
                sqLiteAlWeatherConfig.setId((long) 1);
                sqLiteAlWeatherConfig.save();

                GpsInfo gpsInfo = new GpsInfo(getActivity());
                Coord location = gpsInfo.getLocation();
                addLocation.setText(location.getLat() + "," + location.getLon());
            }
        });



        return configureFragmentView;

    }

    private class DataSourceCity extends AsyncTask<Void, Integer, List<CityList>> {
        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            mProgress.setProgress(progress[0]);
        }

        @Override
        protected void onPreExecute() {
            mProgress = new ProgressDialog(getActivity());
            mProgress.setTitle("city list");
            mProgress.setMessage("loading...");
            mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgress.setMax(MAX_CITY_COUNT);
            mProgress.show();
        }

        protected void onPostExecute(List<CityList> result) {
            if (null != result) {
                mProgress.dismiss();
                addLocation.setText("");
                addLocation.setEnabled(true);
                mData = allCityList;
                cityListAdapter = new CityListAdapter(getActivity(), R.layout.city_list, mData);
                addLocation.setAdapter(cityListAdapter);
                addLocation.setThreshold(1);
            }
        }

        @Override
        protected List<CityList> doInBackground(Void... voids) {
            //        allCityList = readStream();
            AssetManager mgr = getActivity().getAssets();
            String filename;
            InputStream stream = null;
            JsonReader reader;
            try {
                filename = "city.list.json";
                stream = mgr.open(filename, AssetManager.ACCESS_BUFFER);
            } catch (IOException e) {
                e.printStackTrace();
            }
            allCityList = new ArrayList<>();
            try {
                reader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
                Gson gson = new GsonBuilder().create();
                reader.beginArray();
                int i = 1;
                while (reader.hasNext()) {
                    CityList message = gson.fromJson(reader, CityList.class);
                    allCityList.add(message);
                    publishProgress(++i);
                }
                reader.endArray();
                Log.d(TAG, "sitycount = " + allCityList.size());
                reader.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException ex) {
            }
            Collections.sort(allCityList, new Comparator<CityList>() {
                @Override
                public int compare(CityList listJsonObject, CityList nextListJsonObject) {
                    return listJsonObject.getName().compareToIgnoreCase(nextListJsonObject.getName());
                }
            });
            ArrayList<CityList> list = new ArrayList<CityList>();
            return list;
        }
    }

    private void initConfig(){
        sqLiteAlWeatherConfig = new SQLiteAlWeatherConfig();
        this.sqLiteAlWeatherConfig = SQLiteAlWeatherConfig.findById(SQLiteAlWeatherConfig.class, 1);
        if (this.sqLiteAlWeatherConfig == null) {
            addLocation.setText(sqLiteAlWeatherConfig.getCity());
            spinnerW.setSelection(2,true);
            spinnerW.setSelection(3,true);
        }






    }
}
