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
import com.webdoc.ibcc.DashBoard.Account.Appointment.AttestationAppointment.AppointmentStatusActivity;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.ViewDetailsResult.Detail;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;

public class CompletedAppointmentAdapter extends RecyclerView.Adapter<CompletedAppointmentAdapter.ViewHolder> {
    Activity context;
    VolleyRequestController volleyRequestController;
    private ItemClickListeners itemClickListeners;

    public CompletedAppointmentAdapter(Activity context) {
        this.context = context;
        this.volleyRequestController = new VolleyRequestController(context);
    }

    @NonNull
    @Override
    public CompletedAppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_appt_item, parent, false);
        return new CompletedAppointmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CompletedAppointmentAdapter.ViewHolder holder, final int position) {
        Detail item = Global.viewDetailsResponse.getResult().getDetails().get(position);
        final String id = item.getCaseId();
        final String method = item.getTransactionType();
        final String consignmentNo= item.getConsignmentNo();
        final String dateTime= item.getTransactionDatetime();
        final String transID= item.getTransactionId();
        final String status= item.getStatus();
        final String city= Global.userLoginResponse.getResult().getCustomerProfile().getcCity();

        //final String c= item.getDocument().get(position).getCertificateId();

        holder.tv_AppID.setText(id);
        holder.tv_AppMethod.setText(method);
        holder.tv_consignmentNo.setText(consignmentNo);
        holder.tv_DateTime.setText(dateTime);
        holder.tv_transactionId.setText(transID);
        holder.tv_status.setText(status);
        holder.tv_city.setText(city);

        //Global.selectedApptStatus= item;
       // Global.apptId= position;

        holder.iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Global.cancelAppointmentPosition = position;
                String caseID = Global.viewDetailsResponse.getResult().getDetails().get(position).getCaseId();
                Global.utils.showCustomLoadingDialog(context);
                volleyRequestController.CancelAppointment(caseID);*/

                if (itemClickListeners != null) {
                    itemClickListeners.onClick(view, position, "mCancel");
                }
            }
        });

        holder.iv_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AppointmentStatusActivity.class);
                Global.selectedAppointAtt= item;
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (Global.viewDetailsResponse.getResult().getDetails().size() > 0) {
            return Global.viewDetailsResponse.getResult().getDetails().size();
        } else {
            return 0;
        }
    }

    public void setItemClickListeners(ItemClickListeners itemClickListeners){
        this.itemClickListeners = itemClickListeners;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_AppID, tv_AppMethod, tv_consignmentNo, tv_DateTime, tv_transactionId, tv_status, tv_city;
        public ImageView iv_status, iv_cancel, iv_save;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_AppID = itemView.findViewById(R.id.tv_AppID);
            tv_AppMethod = itemView.findViewById(R.id.tv_AppMethod);
            tv_consignmentNo = itemView.findViewById(R.id.tv_consignmentNo);
            tv_DateTime =itemView.findViewById(R.id.tv_DateTime);
            tv_transactionId =itemView.findViewById(R.id.tv_transactionId);
            tv_city =itemView.findViewById(R.id.tv_city);
            tv_status=itemView.findViewById(R.id.tv_status);
            iv_status =itemView.findViewById(R.id.iv_status);
            iv_cancel=itemView.findViewById(R.id.iv_cancel);
            iv_save=itemView.findViewById(R.id.iv_save);

        }
    }
}
