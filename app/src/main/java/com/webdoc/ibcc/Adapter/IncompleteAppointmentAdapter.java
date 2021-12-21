package com.webdoc.ibcc.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webdoc.ItemClickListeners;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.PdfResult.Center;
import com.webdoc.ibcc.ResponseModels.ViewIncompleteResult.IncompleteResultDetail;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class IncompleteAppointmentAdapter extends RecyclerView.Adapter<IncompleteAppointmentAdapter.ViewHolder> {
    Activity context;
    VolleyRequestController volleyRequestController;
    private ItemClickListeners itemClickListeners;

    public IncompleteAppointmentAdapter(Activity context) {
        this.context = context;
        this.volleyRequestController= new VolleyRequestController(context);
    }

    @NonNull
    @Override
    public IncompleteAppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.incomplete_app_item, parent, false);
        return new IncompleteAppointmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final IncompleteAppointmentAdapter.ViewHolder holder, final int position) {
        IncompleteResultDetail item = Global.viewIncompleteResponse.getResult().getIncompleteResultDetails().get(position);
        final String caseId = item.getCaseId();
        final String caseStatus = item.getCaseStatus();

        holder.tv_id.setText(caseId);
        holder.tv_status.setText(caseStatus);

        holder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Global.incompleteCaseId = caseId;
                Global.caseId = caseId;
                Global.utils.showCustomLoadingDialog(context);
                //volleyRequestController.ViewIncompleteDetails(caseId);

                Global.attestationGenerateAppCenter = new Center();
                for (int i = 0; i < Global.pdfResponse.getResult().getCenters().size(); i++) {
                    if(Global.pdfResponse.getResult().getCenters().get(i).getName().equalsIgnoreCase(item.getCenter_id())) {
                        Global.attestationGenerateAppCenter.setCallCourierId(Global.pdfResponse.getResult().getCenters().get(i).getCallCourierId());
                        Global.attestationGenerateAppCenter.setPhoneNumber(Global.pdfResponse.getResult().getCenters().get(i).getPhoneNumber());
                        Global.attestationGenerateAppCenter.setAddress(Global.pdfResponse.getResult().getCenters().get(i).getAddress());
                        break;
                    }
                }

                volleyRequestController.ViewIncompleteDetails(caseId);*/
                if (itemClickListeners != null) {
                    itemClickListeners.onClick(view, position, "mEdit");
                }

            }
        });

        holder.iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setContentText("Do you want to remove your record?")
                        .setConfirmText("Yes")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                Global.cancelIncompleteApptPosition = position;
                                sDialog.dismissWithAnimation();

                                Global.utils.showCustomLoadingDialog(context);
                                volleyRequestController.CancelAppointment(caseId);
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
                    itemClickListeners.onClick(view, position, "mCancel");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if(Global.viewIncompleteResponse.getResult()!=null) {
            if (Global.viewIncompleteResponse.getResult().getIncompleteResultDetails().size() > 0) {
                return Global.viewIncompleteResponse.getResult().getIncompleteResultDetails().size();
            } else {
                return 0;
            }
        }
        return 0;
    }

    public void setItemClickListeners(ItemClickListeners itemClickListeners){
        this.itemClickListeners = itemClickListeners;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_id, tv_status;
        public ImageView iv_edit, iv_cancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_AppID);
            tv_status = itemView.findViewById(R.id.tv_AppStatus);
            iv_edit = itemView.findViewById(R.id.iv_edit);
            iv_cancel = itemView.findViewById(R.id.iv_cancel);
        }
    }

}
