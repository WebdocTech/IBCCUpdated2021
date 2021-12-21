package com.webdoc.ibcc.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.InstructionsModel;
import com.webdoc.ibcc.R;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsAdapter.ViewHolder> {
    Activity context;

    public InstructionsAdapter(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public InstructionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instructions_item, parent, false);
        return new InstructionsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final InstructionsAdapter.ViewHolder holder, final int position) {
        final InstructionsModel myListData = Global.instructionsModels.get(position);
        final String id = myListData.getCount();
        final String details = myListData.getDetails();

        holder.tv_id.setText(id);
        holder.tv_detail.setText(details);
    }

    @Override
    public int getItemCount() {
        return Global.instructionsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_id, tv_detail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_detail = itemView.findViewById(R.id.tv_detail);
        }
    }

}
