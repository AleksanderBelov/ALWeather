package com.alwh.alweather.UI;


import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.alwh.alweather.R;
import com.alwh.alweather.adapters.CityListAdapter;
import com.alwh.alweather.json.CityList;
import com.alwh.alweather.json.forecast.City;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.google.gson.stream.JsonReader;

import static com.alwh.alweather.helpers.AppRoot.LOCATION_ERROR_MESSAGE;

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
        // Inflate the layout for this fragment
        configureFragmentView = inflater.inflate(R.layout.fragment_configure, container, false);
        addLocation = (AutoCompleteTextView) configureFragmentView.findViewById(R.id.new_location);

        dataSourceCity = new DataSourceCity();
        dataSourceCity.execute();

        final Button addLocationButton = (Button) configureFragmentView.findViewById(R.id.add_location_button);
        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredLocation = addLocation.getText().toString();
                if (TextUtils.isEmpty(enteredLocation)) {
                    Toast.makeText(getActivity(), LOCATION_ERROR_MESSAGE, Toast.LENGTH_LONG).show();



                    return;
                }
                // add this to the database
/*                int numOfLocationsStored = databaseQuery.countAllStoredLocations();
                Toast.makeText(AddLocationActivity.this, "Total count " + numOfLocationsStored, Toast.LENGTH_LONG).show();
                if(numOfLocationsStored <= 3){
                    databaseQuery.insertNewLocation(enteredLocation);
                }else{
                    Toast.makeText(AddLocationActivity.this, getString(R.string.stored_location), Toast.LENGTH_LONG).show();
                }
                Intent listLocationIntent = new Intent(AddLocationActivity.this, ListLocationActivity.class);
                startActivity(listLocationIntent);
  */
            }
        });


        return configureFragmentView;

    }

    private class DataSourceCity extends AsyncTask<Void, Void, List<CityList>> {
        protected void onProgressUpdate() {
        }

        protected void onPostExecute(List<CityList> result) {
            if (null != result) {
                mData = result;
                cityListAdapter = new CityListAdapter(getActivity(), R.layout.city_list, mData);
                addLocation.setAdapter(cityListAdapter);
                addLocation.setThreshold(1);
            }
        }

        @Override
        protected List<CityList> doInBackground(Void... voids) {
            readStream();
            ArrayList<CityList> list = new ArrayList<CityList>();

            return list;
        }
    }

/*    @Override
    protected void onResume() {
        super.onResume();
        if (null == mData) {
            dataSourceCity = new DataSourceCity();
            dataSourceCity.execute();
        }
    }*/

    @Override
    public void onResume() {
        super.onResume();

        if (null == mData) {
            dataSourceCity = new DataSourceCity();
            dataSourceCity.execute();
        }
    }

    public List<CityList> readStream() {

        AssetManager mgr = getActivity().getAssets();
        String filename = null;
        InputStream stream = null;
        try {
            filename = "city.list.json";
            //     System.out.println("filename : " + filename);
            stream = mgr.open(filename, AssetManager.ACCESS_BUFFER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonReader reader = null;
        List<CityList> messages = new ArrayList<>();
        try {
            reader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
               Gson gson = new GsonBuilder().create();

         reader.beginArray();
            while (reader.hasNext()) {

                CityList message = gson.fromJson(reader, CityList.class);
                messages.add(message);

                Log.d(TAG, message.getName());
            }


            reader.endArray();
            reader.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException ex) {
        }
        Collections.sort(messages, new Comparator<CityList>() {
            @Override
            public int compare(CityList listJsonObject, CityList nextListJsonObject) {
                return listJsonObject.getName().compareToIgnoreCase(nextListJsonObject.getName());
            }
        });

        return messages;

    }


}
