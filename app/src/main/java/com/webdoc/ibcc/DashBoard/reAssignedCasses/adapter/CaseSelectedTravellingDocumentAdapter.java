package com.webdoc.ibcc.DashBoard.reAssignedCasses.adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.AddQualification.AddQualification;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.ReassignedCaseDetailsModels.CaseUploadedDocumentResponse;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.ReassignedCaseDetailsModels.CaseUploadedTravDocumentResponse;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.EquivalenceFileModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.AddQualificationEQ.CaseUploadedTravellingDocumentResponse;

import java.util.List;

public class CaseSelectedTravellingDocumentAdapter extends RecyclerView.Adapter<CaseSelectedTravellingDocumentAdapter.ViewHolder> {
    Activity context;
    List<CaseUploadedTravDocumentResponse> arraylist;

    public CaseSelectedTravellingDocumentAdapter(Activity context, List<CaseUploadedTravDocumentResponse> arraylist) {
        this.context = context;
        this.arraylist = arraylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_files_item, parent, false);

        /*int width = AddQualification.rv_files.getWidth();
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = (int)(width * 0.8);
        view.setLayoutParams(params);*/

        return new CaseSelectedTravellingDocumentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CaseSelectedTravellingDocumentAdapter.ViewHolder holder, final int position) {
        //EquivalenceFileModel subjectItem = Global.selectedFilesListTraveling.get(position);

        holder.iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arraylist.remove(position);
                notifyDataSetChanged();

                if(arraylist.size() == 0) {
                    AddQualification.tv_uploadHint.setVisibility(View.VISIBLE);
                }
            }
        });

        if(arraylist.get(position).getIspdf()) {
            holder.iv_file.setPadding(30,30,30,30);
            holder.iv_file.setImageResource(R.drawable.pdf);
            holder.iv_file.setBackgroundColor(context.getResources().getColor(R.color.lightergrayColor));
        } else {
            holder.iv_file.setImageURI(Uri.parse(arraylist.get(position).getImagename()));
        }

        holder.tv_fileName.setText(arraylist.get(position).getImagename());
        //holder.tv_subjectName.setText(subjectItem.getName());

    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_remove, iv_file;
        TextView tv_fileName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_remove = itemView.findViewById(R.id.iv_remove);
            iv_file = itemView.findViewById(R.id.iv_file);
            tv_fileName = itemView.findViewById(R.id.tv_fileName);
        }
    }

}
