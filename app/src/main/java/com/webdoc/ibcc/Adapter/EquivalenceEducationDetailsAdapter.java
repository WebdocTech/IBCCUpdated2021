package com.webdoc.ibcc.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webdoc.ItemClickListeners;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.UpdateQualification.UpdateQualification;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.AddQualificationModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;

public class EquivalenceEducationDetailsAdapter extends RecyclerView.Adapter<EquivalenceEducationDetailsAdapter.ViewHolder> {
    Activity context;
    VolleyRequestController volleyRequestController;
    private ItemClickListeners itemClickListeners;

    public EquivalenceEducationDetailsAdapter(Activity context) {
        this.context = context;
        volleyRequestController = new VolleyRequestController(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.equivalence_education_details_item,
                parent, false);
        return new EquivalenceEducationDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EquivalenceEducationDetailsAdapter.ViewHolder holder, final int position) {
        AddQualificationModel qualificationModel = Global.equivalenceQualificationList.get(position);

        holder.tv_country.setText(qualificationModel.getCountry().getName());
        holder.tv_examiningBody.setText(qualificationModel.getExaminingBody().getName());
        holder.tv_qualification.setText(qualificationModel.getQualification().getName());
        holder.tv_group.setText(qualificationModel.getGroup().getName());
        holder.tv_session.setText(qualificationModel.getSession());
        holder.tv_poe.setText(qualificationModel.getPurposeOfEquivalence());
        holder.tv_gradingSystem.setText(qualificationModel.getGradingSystem().getName());
        holder.iv_edit.setVisibility(View.GONE);

            holder.iv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UpdateQualification.class);
                    Global.selectedQualificationIndex = position;
                    context.startActivity(intent);
                }
            });

        holder.iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setContentText("Do you want to remove this record?")
                        .setConfirmText("Yes")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                Global.deleteEquQualificationPosition = position;
                                sDialog.dismissWithAnimation();

                                Global.utils.showCustomLoadingDialog(context);
                                volleyRequestController.removeQualification(
                                        Global.deleteParams.get(position).getDocId(),
                                        Global.deleteParams.get(position).getCaseId());

                                if (Global.equivalenceQualificationList.size() > 0) {
                                    EquivalenceEducationDetailsFragment.No_EducationRecord.setVisibility(View.GONE);
                                } else {
                                    EquivalenceEducationDetailsFragment.No_EducationRecord.setVisibility(View.VISIBLE);
                                }
                            }
                        })
                        .setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();*/

                if (itemClickListeners != null) {
                    itemClickListeners.onClick(view, position, "mRemove");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Global.equivalenceQualificationList.size();
    }

    public void setItemClickListeners(ItemClickListeners itemClickListeners) {
        this.itemClickListeners = itemClickListeners;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_country, tv_examiningBody, tv_qualification, tv_group, tv_session, tv_poe, tv_gradingSystem;
        ImageView iv_edit, iv_remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_country = itemView.findViewById(R.id.tv_countryValue);
            tv_examiningBody = itemView.findViewById(R.id.tv_examiningBodyValue);
            tv_qualification = itemView.findViewById(R.id.tv_qualificationValue);
            tv_group = itemView.findViewById(R.id.tv_groupValue);
            tv_session = itemView.findViewById(R.id.tv_sessionValue);
            tv_poe = itemView.findViewById(R.id.tv_poeValue);
            tv_gradingSystem = itemView.findViewById(R.id.tv_gradingSystemValue);
            iv_edit = itemView.findViewById(R.id.iv_edit);
            iv_remove = itemView.findViewById(R.id.iv_remove);
        }
    }
}
