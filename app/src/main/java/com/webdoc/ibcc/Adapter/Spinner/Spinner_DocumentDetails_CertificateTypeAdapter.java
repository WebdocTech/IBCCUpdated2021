package com.webdoc.ibcc.Adapter.Spinner;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.CertificateType;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.PdfResult.Group;

import java.util.List;

public class Spinner_DocumentDetails_CertificateTypeAdapter extends ArrayAdapter<CertificateType> {
    private Activity context;
    List<CertificateType> data = null;

    public Spinner_DocumentDetails_CertificateTypeAdapter(Activity context, int spinner_item, List<CertificateType> data) {
        super(context, R.layout.spinner_item, data);
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

    @Override
    public int getCount() {
        return  data.size();


    }

    private View initView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_item, parent, false);
        }

        CertificateType item = data.get(position);
        String groupId = String.valueOf(item.getId());
        String groupName = item.getName();

        if (item != null) {
            TextView text = (TextView) row.findViewById(R.id.item_value);
            if (text != null) {
                text.setText(groupName);
            }
        }

        return row;
    }//init
}