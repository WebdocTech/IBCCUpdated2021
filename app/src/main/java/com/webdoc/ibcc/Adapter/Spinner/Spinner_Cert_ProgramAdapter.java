package com.webdoc.ibcc.Adapter.Spinner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.PdfResult.Program;

import java.util.ArrayList;
import java.util.List;

public class Spinner_Cert_ProgramAdapter extends ArrayAdapter<Program> {
    private Activity context;

    public Spinner_Cert_ProgramAdapter(Activity context, List<Program> data) {
        super(context, R.layout.spinner_item, data);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        if (Global.selectedCertificate.getPrograms().size() > 0) {
            return Global.selectedCertificate.getPrograms().size();
        } else {
            return 0;
        }

    }

    private View initView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_item, parent, false);
        }

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
    }//init
}




