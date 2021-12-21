package com.webdoc.ibcc.Adapter.Spinner;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.PdfResult.Group;
import com.webdoc.ibcc.ResponseModels.PdfResult.Program;

import java.util.List;

public class Spinner_Cert_GroupAdapter extends ArrayAdapter<Group> {
    private Activity context;

    public Spinner_Cert_GroupAdapter(Activity context, List<Group> data) {
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
        //return 1;
        if (Global.selectedCertificate.getGroups().size() > 0) {
            return Global.selectedCertificate.getGroups().size();
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

        Group item = Global.selectedCertificate.getGroups().get(position);
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
