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

import com.squareup.picasso.Picasso;
import com.webdoc.ibcc.Adapter.SelectedFilesAdapter;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.AddQualification.AddQualification;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.UpdateQualification.UpdateQualification;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.ReassignedCaseDetailsModels.CaseUploadedDocumentResponse;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.EquivalenceFileModel;
import com.webdoc.ibcc.R;

import java.util.List;

public class CaseSelectedFilesAdapter extends RecyclerView.Adapter<CaseSelectedFilesAdapter.ViewHolder> {
    Activity context;
    List<CaseUploadedDocumentResponse> caseSelectedFilesList;
    List<EquivalenceFileModel> selectedFilesList;

    public CaseSelectedFilesAdapter(Activity context, List<CaseUploadedDocumentResponse> caseSelectedFilesList,
                                    List<EquivalenceFileModel> selectedFilesList) {
        this.context = context;
        this.caseSelectedFilesList = caseSelectedFilesList;
        this.selectedFilesList = selectedFilesList;
    }

    @NonNull
    @Override
    public CaseSelectedFilesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_files_item, parent,
                false);
       /* int width;
        if (Global.widthFromUpdateQualification) {
            width = UpdateQualification.rv_files.getWidth();
        } else {
            width = AddQualification.rv_files.getWidth();
        }

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = (int) (width * 0.8);
        view.setLayoutParams(params);*/

        return new CaseSelectedFilesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CaseSelectedFilesAdapter.ViewHolder holder, final int position) {

        EquivalenceFileModel subjectItem = null;
        if (selectedFilesList != null) {
            subjectItem = Global.selectedFilesList.get(position);
        }

        holder.iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.selectedFilesList.remove(position);
                notifyDataSetChanged();

                if (Global.selectedFilesList.size() == 0) {
                    AddQualification.tv_uploadHint.setVisibility(View.VISIBLE);
                }
            }
        });

        if (selectedFilesList != null) {
            if (subjectItem.getFileType().equalsIgnoreCase("pdf")) {
                holder.iv_file.setPadding(30, 30, 30, 30);
                holder.iv_file.setImageResource(R.drawable.pdf);
                holder.iv_file.setBackgroundColor(context.getResources().getColor(R.color.lightergrayColor));
            } else {
                if (subjectItem.getFileName().contains("http://equivalence.ibcc.edu.pk/")) {
                    Picasso.get().load(subjectItem.getFileName()).into(holder.iv_file);
                } else {
                    holder.iv_file.setImageURI(subjectItem.getUri());
                }
            }
            holder.tv_fileName.setText(subjectItem.getFileName());
        } else {
            if (caseSelectedFilesList.get(position).getIspdf()) {
                holder.iv_file.setPadding(30, 30, 30, 30);
                holder.iv_file.setImageResource(R.drawable.pdf);
                holder.iv_file.setBackgroundColor(context.getResources().getColor(R.color.lightergrayColor));
            } else {
                if (caseSelectedFilesList.get(position).getImagename().contains("http://equivalence.ibcc.edu.pk/")) {
                    Picasso.get().load(Global.caseSelectedFilesList.get(position).getImagename()).into(holder.iv_file);
                } else {
                    holder.iv_file.setImageURI(Uri.parse(caseSelectedFilesList.get(position).getImagename()));
                }
            }
            holder.tv_fileName.setText(caseSelectedFilesList.get(position).getImagename());
        }
    }

    @Override
    public int getItemCount() {
        return caseSelectedFilesList.size();
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