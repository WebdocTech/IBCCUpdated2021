package com.webdoc.ibcc.DashBoard.reAssignedCasses.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.Qualification;
import com.webdoc.ibcc.R;


import java.util.List;

public class QualificationReassignAdapter extends ArrayAdapter<Qualification> {
    private Activity context;
    List<Qualification> data = null;

    public QualificationReassignAdapter(Activity context, int resource, List<Qualification> data) {
        super(context, resource, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Ordinary view in Spinner, we use android.R.layout.simple_spinner_item
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // This view starts when we click the spinner.
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_item, parent, false);
        }

        Qualification item = data.get(position);
        String countryId = String.valueOf(item.getId());
        String name = item.getName();
        if (item != null) {
            TextView text = (TextView) row.findViewById(R.id.item_value);
            if (text != null) {
                text.setText(name);
            }
        }
        return row;
    }

}
