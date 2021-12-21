package com.webdoc.ibcc.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.webdoc.ibcc.Adapter.Spinner.Equivalence.GradesAdapter;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.AddQualification.AddQualification;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.EquivalenceGrade;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.EquivalenceSubject;

import java.util.ArrayList;
import java.util.List;

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.ViewHolder> {
    Activity context;
    List<EquivalenceSubject> equivalenceSubjectList = null;
    EquivalenceGrade grade;
    List<String> newMarksList = new ArrayList<String>();

    public SubjectsAdapter(Activity context, List<EquivalenceSubject> equivalenceSubjectList) {
        this.context = context;
        this.equivalenceSubjectList = equivalenceSubjectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subjects_item, parent, false);

        int width = AddQualification.rv_subjects.getWidth();
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = (int) (width * 0.8);
        view.setLayoutParams(params);

        return new SubjectsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubjectsAdapter.ViewHolder holder, final int position) {

        EquivalenceSubject subjectItem = equivalenceSubjectList.get(position);

        holder.tv_subjectName.setText(subjectItem.getName());
        holder.tv_subj_name_grades.setText(subjectItem.getName());

        if (Global.equivalenceGradingSystemName.equalsIgnoreCase("Marks")) {

            holder.cc_popup.setVisibility(View.VISIBLE);
            holder.marksLayout.setVisibility(View.VISIBLE);
            holder.mainGradeLayout.setVisibility(View.GONE);
            Global.checkMarks = true;

            holder.btn_add_sub_marks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Global.utils.hideKeyboard(context);


                    String Obtained_Marks = holder.et_marks_obtained.getText().toString();
                    String Total_Marks = holder.et_total_marks.getText().toString();

                    if (!Obtained_Marks.equals("") && !Total_Marks.equals("")) {
                        if (Integer.parseInt(Obtained_Marks) > Integer.parseInt(Total_Marks)) {
                            holder.et_marks_obtained.setError("Obtained Marks must be less than or equalto total marks");
                            holder.et_marks_obtained.requestFocus();
                            Global.utils.hideKeyboard(context);
                        } else {
                            String marks = Obtained_Marks + "/" + Total_Marks;
                            grade = new EquivalenceGrade();
                            grade.setId(subjectItem.getId());
                            grade.setName(marks);
                            Global.selectedGradeList.set(position, grade);
                            holder.btn_add_sub_marks.setText("Added Succesfully");
                            holder.btn_add_sub_marks.setBackground(context.getDrawable(R.drawable.green_button_background));
                        }

                    } else {
                        Toast.makeText(context, "enter both values", Toast.LENGTH_SHORT).show();
                    }


                }
            });


        } else {
            holder.marksLayout.setVisibility(View.GONE);
            holder.mainGradeLayout.setVisibility(View.VISIBLE);
            Global.checkMarks = false;
        }


        //SPINNER GRADE
        GradesAdapter gradesAdapter = new GradesAdapter(context, R.layout.spinner_item, Global.equivalenceGradeList);
        holder.spinner_grades.setAdapter(gradesAdapter);
        holder.spinner_grades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id) {
                grade = Global.equivalenceGradeList.get(pos);
                Global.selectedGradeList.set(position, grade);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });
    }

    @Override
    public int getItemCount() {
        return equivalenceSubjectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_subjectName, tv_subj_name_grades;
        Spinner spinner_grades;
        ConstraintLayout cc_popup;
        Button btn_add_sub_marks;

        public ConstraintLayout marksLayout, mainGradeLayout;
        EditText et_marks_obtained, et_total_marks;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_subjectName = itemView.findViewById(R.id.tv_subjectName);
            spinner_grades = itemView.findViewById(R.id.spinner_grades);
            marksLayout = itemView.findViewById(R.id.marksLayout);
            mainGradeLayout = itemView.findViewById(R.id.mainGradeLayout);
            et_marks_obtained = itemView.findViewById(R.id.et_marks_obtained);
            et_total_marks = itemView.findViewById(R.id.et_total_marks);
            cc_popup = itemView.findViewById(R.id.cc_popup);
            btn_add_sub_marks = itemView.findViewById(R.id.btn_add_sub_marks);
            tv_subj_name_grades = itemView.findViewById(R.id.tv_subj_name_grades);
        }
    }

}
