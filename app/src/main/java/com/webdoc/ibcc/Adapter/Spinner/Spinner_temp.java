package com.webdoc.ibcc.Adapter.Spinner;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.PdfResult.Program;

import java.util.List;

public class Spinner_temp extends ArrayAdapter<Program> {

    public Spinner_temp(Activity context, List<Program> items) {
        super(context, R.layout.spinner_item, items);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        if (position == 0) {
            return initialSelection(true);
        }
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (position == 0) {
            return initialSelection(false);
        }
        return getCustomView(position, convertView, parent);
    }


    @Override
    public int getCount() {
        return 2;
        //return super.getCount() + 1; // Adjust for initial selection item
    }

    private View initialSelection(boolean dropdown) {
        // Just an example using a simple TextView. Create whatever default view to suit your needs, inflating a separate layout if it's cleaner.
        TextView view = new TextView(getContext());
        view.setText("Select one");
        int spacing = getContext().getResources().getDimensionPixelSize(R.dimen.spacing_smaller);
        view.setPadding(0, spacing, 0, spacing);

        if (dropdown) { // Hidden when the dropdown is opened
            view.setHeight(0);
        }

        return view;
    }


    private View getCustomView(int position, View convertView, ViewGroup parent) {
        // Distinguish "real" spinner items (that can be reused) from initial selection item
        View row = convertView != null && !(convertView instanceof TextView)
                ? convertView : LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);

        position = position - 1; // Adjust for initial selection item

        //Program item = getItem(position);
        Program item = Global.selectedCertificate.getPrograms().get(position);
        String programId = String.valueOf(item.getId());
        String programName = item.getName();

        if (item != null) {
            TextView text = (TextView) row.findViewById(R.id.item_value);
            if (text != null) {
                text.setText(programName);
            }
        }

        return row;
    }
}
