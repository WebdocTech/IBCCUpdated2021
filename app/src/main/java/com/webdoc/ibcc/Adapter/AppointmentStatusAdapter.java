package com.webdoc.ibcc.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.ViewDetailsResult.Detail;

public class AppointmentStatusAdapter extends RecyclerView.Adapter<AppointmentStatusAdapter.ViewHolder> {
    Activity context;

    public AppointmentStatusAdapter(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AppointmentStatusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doc_receipt_item, parent, false);
        return new AppointmentStatusAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AppointmentStatusAdapter.ViewHolder holder, final int position) {
        Detail item =Global.viewDetailsResponse.getResult().getDetails().get(position);

        for (int i=0; i<Global.viewDetailsResponse.getResult().getDetails().size();i++ ){
            String certificate = item.getDocument().get(i).getCertificateId();
            String board = item.getDocument().get(i).getBoardId();
            String program = item.getDocument().get(i).getProgramId();


            String cert_name = null;
            for (int k = 0; k < Global.pdfResponse.getResult().getCerftificates().size(); k++) {
                if (certificate.equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(k).getId().toString())) {
                    cert_name = Global.pdfResponse.getResult().getCerftificates().get(k).getName();
                    break;
                }
            }

            String prog_name = null;
            for (int l = 0; l < Global.pdfResponse.getResult().getCerftificates().size(); l++) {
                if (program.equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().get(l).getId().toString())) {
                    prog_name = Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().get(l).getName();
                    break;
                }
            }
            String board_name = null;
            for (int m = 0; m < Global.pdfResponse.getResult().getBoards().size(); m++) {
                if (board.equalsIgnoreCase(Global.pdfResponse.getResult().getBoards().get(m).getId().toString())) {
                    board_name = Global.pdfResponse.getResult().getBoards().get(m).getName();
                    break;
                }
            }

            holder.tv_title.setText(cert_name);
            holder.tv_board.setText(board_name);
            holder.tv_program.setText(prog_name);

            for (int j = 0; j <Global.viewDetailsResponse.getResult().getDetails().size() ; j++) {
                String documentType = item.getDocument().get(i).getDocumentDetails().get(j).getOriginalIncluded();
                String copies = item.getDocument().get(i).getDocumentDetails().get(j).getNoOfCopies();
                String docCertificate =item.getDocument().get(i).getDocumentDetails().get(j).getCertificateType();

                holder.tv_document_Type.setText(docCertificate);
                holder.tv_copies.setText("(No. of copies: " + copies + ")");
            }
        }


    }//onBindView

    @Override
    public int getItemCount() {
        if (Global.viewDetailsResponse.getResult().getDetails().size() > 0) {
           // return Global.viewDetailsResponse.getResult().getDetails().size();

            return 1;
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_board, tv_program, tv_document_Type, tv_copies;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_board = itemView.findViewById(R.id.tv_board);
            tv_program = itemView.findViewById(R.id.tv_program);
            tv_document_Type = itemView.findViewById(R.id.tv_document_Type);
            tv_copies = itemView.findViewById(R.id.tv_copies);
        }
    }
}

