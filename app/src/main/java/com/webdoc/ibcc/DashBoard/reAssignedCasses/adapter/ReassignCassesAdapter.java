package com.webdoc.ibcc.DashBoard.reAssignedCasses.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.webdoc.ItemClickListeners;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.ReassignedCaseDetail;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.ViewDetailsResult.Detail;

import java.util.ArrayList;

public class ReassignCassesAdapter extends RecyclerView.Adapter<ReassignCassesAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<ReassignedCaseDetail> arrayList = new ArrayList<>();
    private ItemClickListeners itemClickListeners;

    public ReassignCassesAdapter(Context mContext, ArrayList<ReassignedCaseDetail> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ReassignCassesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reassign_casses_rescycler_layout, parent, false);
        return new ReassignCassesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReassignCassesAdapter.ViewHolder holder, final int position) {
        holder.tvCaseID.setText(arrayList.get(position).getCaseId());

        holder.clMain.setOnClickListener(view -> {
            if (itemClickListeners != null) {
                itemClickListeners.onClick(view, position, "");
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setItemClickListeners(ItemClickListeners itemClickListeners) {
        this.itemClickListeners = itemClickListeners;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCaseID;
        ConstraintLayout clMain;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCaseID = itemView.findViewById(R.id.tv_case_id);
            clMain = itemView.findViewById(R.id.clMain);
        }
    }
}

