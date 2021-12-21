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

import com.webdoc.ibcc.DashBoard.Account.Appointment.EquivalenceAppointment.AppointmentStatusEQActivity;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.ViewAppointmentsEQ.ViewAppointmentsEQD;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;

public class CompletedApptEQtAdapter extends RecyclerView.Adapter<CompletedApptEQtAdapter.ViewHolder> {
    Activity context;
    VolleyRequestController volleyRequestController;

    public CompletedApptEQtAdapter(Activity context) {
        this.context = context;
        this.volleyRequestController = new VolleyRequestController(context);
    }

    @NonNull
    @Override
    public CompletedApptEQtAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_appt_item, parent, false);
        return new CompletedApptEQtAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CompletedApptEQtAdapter.ViewHolder holder, final int position) {
        ViewAppointmentsEQD item = Global.viewAppointmentsEQResponse.getResult().getViewAppointmentsEQD().get(position);
        final String id = item.getCaseid();
        final String method = item.getPaidthrough();
        final String consignmentNo= item.getConsignmentno();
        final String dateTime= item.getDatetime();
        final String transID= item.getTransactionid();
        final String status= item.getPaymentstatus();
        final String city= Global.userLoginResponse.getResult().getCustomerProfile().getcCity();
        final String orderId = item.getOrderId();
        final String storeID = "86961";
        final String accountNum = "99426801";


        //final String c= item.getDocument().get(position).getCertificateId();

        holder.tv_AppID.setText(id);
        holder.tv_AppMethod.setText(method);
        holder.tv_consignmentNo.setText(consignmentNo);
        holder.tv_DateTime.setText(dateTime);
        holder.tv_transactionId.setText(transID);
        holder.tv_status.setText(status);
        holder.tv_city.setText(city);

        holder.iv_cancel.setVisibility(View.GONE);
        holder.tv_cancelIcon.setVisibility(View.GONE);

       /*  holder.iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.cancelAppointmentPosition = position;

                String caseID = Global.viewDetailsResponse.getResult().getDetails().get(position).getCaseId();

                Global.utils.showCustomLoadingDialog(context);
                volleyRequestController.CancelAppointment(caseID);
            }
        });*/

        holder.iv_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AppointmentStatusEQActivity.class);
                Global.selectedAppointEQ= item;
                intent.putExtra("orderId",orderId);
                intent.putExtra("storeId",storeID);
                intent.putExtra("accountNum",accountNum);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (Global.viewAppointmentsEQResponse.getResult().getViewAppointmentsEQD().size() > 0) {
            return Global.viewAppointmentsEQResponse.getResult().getViewAppointmentsEQD().size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_AppID, tv_AppMethod, tv_consignmentNo, tv_DateTime, tv_transactionId, tv_status, tv_city,
                tv_cancelIcon;
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
            tv_cancelIcon=itemView.findViewById(R.id.tv_cancelIcon);

        }
    }
}
