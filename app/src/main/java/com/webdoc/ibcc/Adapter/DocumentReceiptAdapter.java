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

public class DocumentReceiptAdapter extends RecyclerView.Adapter<DocumentReceiptAdapter.ViewHolder>{

    Activity context;

    public DocumentReceiptAdapter (Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DocumentReceiptAdapter .ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doc_receipt_item, parent, false);
        return new DocumentReceiptAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DocumentReceiptAdapter .ViewHolder holder, final int position) {
        DocumentDetailModel item = Global.selDocument.get(Global.selectedDoc).getDetailModelList().get(position);
        String certificateType = item.getCertificateType();
        String documentType = item.getDocumentType();
        String ticketNo = item.getTicketNo();
        String date = item.getDate();
        String copies = item.getTotalCopies();
        int amount = item.getAmount();
        String title = item.getTitleCert();
        String prog= item.getTitleProg();
        String board= item.getTitleBoard();

        holder.tv_title.setText(title);
        holder.tv_board.setText(board);
        holder.tv_program.setText(prog);
        holder.tv_document_Type.setText(certificateType);
        holder.tv_copies.setText("(No. of copies: "+ copies +")");

       /* if (documentType != null) {
            holder.tv_document_Type.setText(documentType);
        }
        if (documentType.equalsIgnoreCase("Please Select")) {
            holder.tv_document_Type.setText("Original");
        }*/

    }//onBindView

    @Override
    public int getItemCount() {
        if(Global.selDocument!=null) {
            if (Global.selDocument.get(Global.selectedDoc).getDetailModelList() != null) {
                return Global.selDocument.get(Global.selectedDoc).getDetailModelList().size();
            } else {
                return 0;
            }
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_board,tv_program,tv_document_Type,tv_copies;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_board=itemView.findViewById(R.id.tv_board);
            tv_program=itemView.findViewById(R.id.tv_program);
            tv_document_Type = itemView.findViewById(R.id.tv_document_Type);
            tv_copies=itemView.findViewById(R.id.tv_copies);
        }
    }


}
