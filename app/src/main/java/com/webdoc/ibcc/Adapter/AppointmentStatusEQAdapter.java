package com.webdoc.ibcc.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.ViewAppointmentsEQ.ViewAppointmentsEQD;
import com.webdoc.ibcc.ResponseModels.ViewAppointmentsEQ.ViewAppointmentsEQDetail;

public class AppointmentStatusEQAdapter extends RecyclerView.Adapter<AppointmentStatusEQAdapter.ViewHolder> {
    Activity context;

    public AppointmentStatusEQAdapter(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AppointmentStatusEQAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doc_receipt_item, parent, false);
        return new AppointmentStatusEQAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AppointmentStatusEQAdapter.ViewHolder holder, final int position) {
        ViewAppointmentsEQDetail item = Global.selectedAppointEQ.getViewAppointmentsEQDetails().get(position);
        String country = item.getCountry();
        String title = item.getTitle();
        String eqOfEquivalence = item.getEquivalenceofQualification();
        String group = item.getGroup();
        String examiningBody = item.getExaminingBody();
        /*String subjects = item.getSubjects();*/

        String subjects = "";
        for (int i = 0; i < Global.selectedAppointEQ.getViewAppointmentsEQDetails().size(); i++) {
            subjects += Global.selectedAppointEQ.getViewAppointmentsEQDetails().get(i).getSubjects() + " , ";
        }


        holder.tv_subjs.setText(subjects);
        holder.tv_title.setText(country);
        holder.tv_board.setText(title);
        holder.tv_program.setText(eqOfEquivalence);
        holder.tv_document_Type.setText(group);
        holder.tv_copies.setText(examiningBody);

    }//onBindView

    @Override
    public int getItemCount() {
        if (Global.selectedAppointEQ.getViewAppointmentsEQDetails().size() > 0) {
            return Global.selectedAppointEQ.getViewAppointmentsEQDetails().size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_board, tv_program, tv_document_Type, tv_copies, tv_subjs;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_board = itemView.findViewById(R.id.tv_board);
            tv_program = itemView.findViewById(R.id.tv_program);
            tv_document_Type = itemView.findViewById(R.id.tv_document_Type);
            tv_copies = itemView.findViewById(R.id.tv_copies);
            tv_subjs = itemView.findViewById(R.id.tv_subjs);
        }
    }
}
