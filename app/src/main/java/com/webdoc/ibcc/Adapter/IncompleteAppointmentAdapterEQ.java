package com.webdoc.ibcc.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webdoc.ItemClickListeners;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.PdfResult.Center;
import com.webdoc.ibcc.ResponseModels.ViewIncompleteAppointmentEQ.IncompleteResultDetail;
import com.webdoc.ibcc.ResponseModels.ViewIncompleteAppointmentEQ.ViewIncompleteAppointmentEQ;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class IncompleteAppointmentAdapterEQ extends RecyclerView.Adapter<IncompleteAppointmentAdapterEQ.ViewHolder> {
    Activity context;
    VolleyRequestController volleyRequestController;
    private ItemClickListeners itemClickListeners;

    public IncompleteAppointmentAdapterEQ(Activity context) {
        this.context = context;
        //volleyRequestController =new VolleyRequestController(context);
    }

    @NonNull
    @Override
    public IncompleteAppointmentAdapterEQ.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.incomplete_app_item, parent, false);
        return new IncompleteAppointmentAdapterEQ.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final IncompleteAppointmentAdapterEQ.ViewHolder holder, final int position) {
        IncompleteResultDetail item = Global.viewIncompleteAppointmentEQ.getResult().getIncompleteResultDetails().get(position);
        final String caseId = item.getCaseId();
        final String caseStatus = item.getCaseStatus();
        final String centerId= item.getCenterId();

        holder.tv_id.setText(caseId);
        holder.tv_status.setText(caseStatus);

        holder.iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "later", Toast.LENGTH_SHORT).show();
            }
        });

        holder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Global.caseIdQualificationEQ=caseId;
                Global.caseId = caseId;
                Global.selEqCenter= centerId;

                Global.equivalenceGenerateAppCenter = new Center();
                for (int i = 0; i < Global.pdfResponse.getResult().getCenters().size(); i++) {
                        Global.equivalenceGenerateAppCenter.setCallCourierId(Global.pdfResponse.getResult().getCenters().get(i).getCallCourierId());
                        Global.equivalenceGenerateAppCenter.setPhoneNumber(Global.pdfResponse.getResult().getCenters().get(i).getPhoneNumber());
                        Global.equivalenceGenerateAppCenter.setAddress(Global.pdfResponse.getResult().getCenters().get(i).getAddress());
                        break;
                }*/
                //Global.utils.showCustomLoadingDialog(context);
                //volleyRequestController.incompleteDetailsEQ(caseId);

                if (itemClickListeners != null) {
                    itemClickListeners.onClick(view, position, "mEdit");
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        if(Global.viewIncompleteAppointmentEQ.getResult()!=null) {
            if (Global.viewIncompleteAppointmentEQ.getResult().getIncompleteResultDetails().size() > 0) {
                return Global.viewIncompleteAppointmentEQ.getResult().getIncompleteResultDetails().size();
            } else {
                return 0;
            }
        }
        return 0;
    }

    public void setItemClickListeners(ItemClickListeners itemClickListeners) {
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
