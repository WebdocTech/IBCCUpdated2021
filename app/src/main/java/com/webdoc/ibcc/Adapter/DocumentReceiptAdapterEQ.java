package com.webdoc.ibcc.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.AddQualificationModel;
import com.webdoc.ibcc.Model.DocumentDetailModel;
import com.webdoc.ibcc.R;

import javax.microedition.khronos.opengles.GL;

public class DocumentReceiptAdapterEQ extends RecyclerView.Adapter<DocumentReceiptAdapterEQ.ViewHolder> {
    Activity context;

    public DocumentReceiptAdapterEQ(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DocumentReceiptAdapterEQ.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doc_receipt_item, parent, false);
        return new DocumentReceiptAdapterEQ.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DocumentReceiptAdapterEQ.ViewHolder holder, final int position) {
        AddQualificationModel item = Global.equivalenceQualificationList.get(position);
        String country = item.getCountry().getName();
        String body = item.getExaminingBody().getName();
        String qualification = item.getQualification().getName();
        String group = item.getGroup().getName();
        String purpose = item.getPurposeOfEquivalence();
        String subjects = "";
        for (int i = 0; i < Global.equivalenceSubjectList.size(); i++) {
            subjects += Global.equivalenceSubjectList.get(i).getName() + " , ";
        }


        holder.tv_subjs.setText(subjects);
        holder.tv_country.setText(country);
        holder.tv_body.setText(body);
        holder.tv_qualification.setText(qualification);
        holder.tv_group.setText(group);
        holder.tv_purpose.setText(purpose);

    }//onBindView

    @Override
    public int getItemCount() {
        if (Global.equivalenceQualificationList != null) {
            if (Global.equivalenceQualificationList.size() > 0) {
                return Global.equivalenceQualificationList.size();
            } else {
                return 0;
            }
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_country, tv_body, tv_qualification, tv_group, tv_purpose, tv_subjs;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_country = itemView.findViewById(R.id.tv_title);
            tv_body = itemView.findViewById(R.id.tv_board);
            tv_qualification = itemView.findViewById(R.id.tv_program);
            tv_group = itemView.findViewById(R.id.tv_document_Type);
            tv_purpose = itemView.findViewById(R.id.tv_copies);
            tv_subjs = itemView.findViewById(R.id.tv_subjs);
        }
    }

}
