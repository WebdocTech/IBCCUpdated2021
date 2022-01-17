package com.webdoc.ibcc.Adapter.Spinner.Equivalence;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.Country;
import com.webdoc.ibcc.R;
//import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.Country;

import java.util.List;

public class CountriesAdapter extends ArrayAdapter<Country> {
    private Activity context;
    List<Country> data = null;

    public CountriesAdapter(Activity context, int resource, List<Country> data) {
        super(context, resource, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { // Ordinary view in Spinner, we use android.R.layout.simple_spinner_item
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) { // This view starts when we click the spinner.
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_item, parent, false);
        }

        Country item = data.get(position);
        String countryId = String.valueOf(item.getId());
        String countryName = item.getName();

        if (item != null) {
            TextView text = (TextView) row.findViewById(R.id.item_value);
            if (text != null) {
                text.setText(countryName);
            }
        }

        return row;
    }//init

}
