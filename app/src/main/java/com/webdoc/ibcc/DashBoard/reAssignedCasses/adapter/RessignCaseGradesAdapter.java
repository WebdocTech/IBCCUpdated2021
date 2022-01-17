package com.webdoc.ibcc.DashBoard.reAssignedCasses.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.GradesEQNew;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.ReassignedCaseDetailsModels.QualificationSubjectResponse;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.SubjectsGradeModel;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.EquivalenceGrade;

import java.util.ArrayList;
import java.util.List;

public class RessignCaseGradesAdapter extends ArrayAdapter<GradesEQNew> {
    private Activity context;
    List<GradesEQNew> data = null;
    ArrayList<QualificationSubjectResponse> arrayList;

    public RessignCaseGradesAdapter(Activity context, int resource, List<GradesEQNew> data) {
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

    @Override
    public int getCount() {
        return data.size();
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_item, parent, false);
        }

        GradesEQNew item = data.get(position);
        String Id = String.valueOf(item.getId());
        String Name = item.getName();

        if (item != null) {
            TextView text = (TextView) row.findViewById(R.id.item_value);
            if (text != null) {
                text.setText(Name);
            }
        }

        return row;
    }//init
}
