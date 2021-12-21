package com.webdoc.ibcc.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.AddQualification.AddQualification;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.EquivalenceFileModel;
import com.webdoc.ibcc.R;

public class SelectedTravellingDocumentAdapter extends RecyclerView.Adapter<SelectedTravellingDocumentAdapter.ViewHolder> {
    Activity context;

    public SelectedTravellingDocumentAdapter(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_files_item, parent, false);

        int width = AddQualification.rv_files.getWidth();
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = (int)(width * 0.8);
        view.setLayoutParams(params);

        return new SelectedTravellingDocumentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SelectedTravellingDocumentAdapter.ViewHolder holder, final int position) {
        EquivalenceFileModel subjectItem = Global.selectedFilesListTraveling.get(position);

        holder.iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.selectedFilesListTraveling.remove(position);
                notifyDataSetChanged();

                if(Global.selectedFilesListTraveling.size() == 0) {
                    AddQualification.tv_uploadHint.setVisibility(View.VISIBLE);
                }
            }
        });

        if(subjectItem.getFileType().equalsIgnoreCase("pdf")) {
            holder.iv_file.setPadding(30,30,30,30);
            holder.iv_file.setImageResource(R.drawable.pdf);
            holder.iv_file.setBackgroundColor(context.getResources().getColor(R.color.lightergrayColor));
        } else {
            holder.iv_file.setImageURI(subjectItem.getUri());
        }

        holder.tv_fileName.setText(subjectItem.getFileName());
        //holder.tv_subjectName.setText(subjectItem.getName());

    }

    @Override
    public int getItemCount() {
        return Global.selectedFilesListTraveling.size();
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
