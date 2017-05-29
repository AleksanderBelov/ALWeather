package com.alwh.alweather.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.alwh.alweather.json.CityList;
import com.alwh.alweather.json.CityList;
import com.alwh.alweather.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class CityListAdapter extends ArrayAdapter<CityList> {
    private final String MY_DEBUG_TAG = "ListJsonObjectAdapter";
    private List<CityList> items;
    private List<CityList> itemsAll;
    private List<CityList> suggestions;
    private int viewResourceId;
    public CityListAdapter(Context context, int viewResourceId, List<CityList> items) {
        super(context, viewResourceId, items);
        this.items = items;
        this.itemsAll = new ArrayList<>(this.items);
        this.suggestions = new ArrayList<CityList>();
        this.viewResourceId = viewResourceId;

    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

     //   View v = LayoutInflater.from(parent.getContext()).inflate(R.layout., parent, false);
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        CityList citylist = items.get(position);
        if (citylist != null) {
            TextView listJsonObjectNameLabel = (TextView) v.findViewById(R.id.text_suggestion);
            if (listJsonObjectNameLabel != null) {
                listJsonObjectNameLabel.setText(citylist.getName() + "," + citylist.getCountry());
            }
        }
        return v;
    }
    @Override
    public Filter getFilter() {
        return nameFilter;
    }
    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((CityList)(resultValue)).getName();
            return str;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (null != constraint) {
                if (constraint.length() == 3) {
                    for (CityList cityList : itemsAll) {
                        if (cityList.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            suggestions.add(cityList);
                        }
                    }
                }
                if (constraint.length() >= 3) {
                    List<CityList> newData = new ArrayList<>(suggestions);
                    suggestions.clear();
                    for (CityList newList : newData) {
                        if (newList.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            suggestions.add(newList);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                System.out.println("Counting new " + suggestions.size());
                return filterResults;
            } else {
                suggestions.clear();
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<CityList> filteredList = (List<CityList>) results.values;
            if(results != null && results.count > 0) {
                clear();
                for(Iterator<CityList> data = filteredList.iterator(); data.hasNext();){
                    CityList obtainedJsonObject = data.next();
                    add(obtainedJsonObject);
                }
                notifyDataSetChanged();
            }
        }
    };
}

/**
 * Created by abelov on 29.05.2017.
 */

