package com.webdoc.ibcc.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.DocumentCheckListModel;
import com.webdoc.ibcc.Model.DocumentDetailModel;
import com.webdoc.ibcc.Model.DocumentSelectionModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.AddEducationResult.EducationDetail;

public class DocumentCheckListAdapter extends RecyclerView.Adapter<DocumentCheckListAdapter.ViewHolder> {
    Activity context;

    public DocumentCheckListAdapter(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DocumentCheckListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doc_checklist_item, parent, false);
        return new DocumentCheckListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DocumentCheckListAdapter.ViewHolder holder, final int position) {
        DocumentDetailModel item = Global.selDocument.get(Global.selectedDoc).getDetailModelList().get(position);
        String documentType = item.getDocumentType();
        String copies = item.getTotalCopies();
        String title = item.getTitleCert();

        holder.tv_title.setText(title);
        holder.tv_noOfCopies.setText("No.of Copies: " + copies);

        if (documentType != null) {
            holder.tv_document.setText(documentType);
        }if (documentType.equalsIgnoreCase("Please Select")) {
            holder.tv_document.setText("Original");
        }

    }//onBindView

    @Override
    public int getItemCount() {
        if (Global.selDocument != null) {
            if (Global.selDocument.get(Global.selectedDoc).getDetailModelList().size() > 0) {
                return Global.selDocument.get(Global.selectedDoc).getDetailModelList().size();
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_document,tv_noOfCopies;
        EditText edt_ticket;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_document = itemView.findViewById(R.id.tv_document);
            edt_ticket = itemView.findViewById(R.id.edt_ticket);
            tv_noOfCopies=itemView.findViewById(R.id.tv_noOfCopies);
        }
    }

}
