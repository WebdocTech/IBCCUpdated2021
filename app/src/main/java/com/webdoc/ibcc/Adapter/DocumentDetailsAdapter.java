package com.webdoc.ibcc.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.DocumentDetailModel;
import com.webdoc.ibcc.R;

public class DocumentDetailsAdapter extends RecyclerView.Adapter<DocumentDetailsAdapter.ViewHolder> {
    public static ViewHolder holder;
    Activity context;
    int totalAmount;

    public DocumentDetailsAdapter(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DocumentDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.document_detail_item, parent, false);
        return new DocumentDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DocumentDetailsAdapter.ViewHolder holder, final int position) {
        DocumentDetailModel item = Global.DMList.get(position);
        String certificateType = item.getCertificateType();
        String documentType = item.getDocumentType();
        String ticketNo = item.getTicketNo();
        String date = item.getDate();
        String copies = item.getTotalCopies();
        int amount = item.getAmount();
        String title = item.getTitleCert();
        String prog= item.getTitleProg();
        String board= item.getTitleBoard();

        holder.tv_certificate.setText(certificateType);
        holder.tv_noOfCopies.setText(copies + " copies");
        holder.tv_amount.setText(amount + "/ Rs");

        holder.tv_certificateTitle.setText(title+ " - "+ prog);

        //DOCUMENT TYPE
        if (documentType != null) {
            holder.tv_document_Type.setText(documentType);
        }
        if (documentType.equalsIgnoreCase("Please Select")) {
            holder.tv_document_Type.setText("Original");
        }


        if(ticketNo.equalsIgnoreCase("00")) {
            holder.tv_document_Type.setVisibility(View.VISIBLE);
            holder.tv_TicketNo.setVisibility(View.GONE);
        } else {
            holder.tv_document_Type.setVisibility(View.GONE);
            holder.tv_TicketNo.setText("Ticket No: "+ ticketNo);
            holder.tv_TicketNo.setVisibility(View.VISIBLE);
        }

        if(date.equalsIgnoreCase("1970-01-01") || date.equalsIgnoreCase("0001-01-01")) {
            holder.tv_date.setVisibility(View.GONE);
        } else {
            holder.tv_date.setText("Ticket Date: "+date);
            holder.tv_date.setVisibility(View.VISIBLE);
        }

        totalAmount = 0;
       /* if(amount>0) {
            for (int i = 0; i < Global.selDocument.size(); i++) {
                int myamount = Global.selDocument.get(i).getDetailModelList().get(i).getAmount();
                totalAmount += myamount;
            }
            DocumentSelectionFragment.tv_TotalAmount.setText("Amount:  Rs." + String.valueOf(totalAmount));
        }*/


    }//onBindView

    @Override
    public int getItemCount() {
       if (Global.DMList != null) {
           return Global.DMList.size();
       }else {
                return 0;
            }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_certificate, tv_document_Type, tv_noOfCopies, tv_amount, tv_TicketNo, tv_date;
        public TextView tv_certificateTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_certificate = itemView.findViewById(R.id.tv_certificate);
            tv_document_Type = itemView.findViewById(R.id.tv_document_Type);
            tv_noOfCopies = itemView.findViewById(R.id.tv_noOfCopies);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_TicketNo = itemView.findViewById(R.id.tv_TicketNo);
            tv_date = itemView.findViewById(R.id.tv_date);

            tv_certificateTitle = itemView.findViewById(R.id.tv_certificateTitle);
        }
    }

}
