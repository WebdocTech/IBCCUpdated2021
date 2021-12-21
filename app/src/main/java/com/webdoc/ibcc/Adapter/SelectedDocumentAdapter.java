package com.webdoc.ibcc.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.DocumentDetailModel;
import com.webdoc.ibcc.Model.DocumentSelectionModel;
import com.webdoc.ibcc.Model.SelectedDocumentModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.AddEducationResult.EducationDetail;

public class SelectedDocumentAdapter extends RecyclerView.Adapter<SelectedDocumentAdapter.ViewHolder> {
    Activity context;

    public SelectedDocumentAdapter(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SelectedDocumentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_document_item, parent, false);
        return new SelectedDocumentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SelectedDocumentAdapter.ViewHolder holder, final int position) {
        DocumentDetailModel item = Global.DMList.get(position);
        String certificate = item.getCertificateType();        //cert+ pro
        String  documentType= item.getDocumentType();
        String  copies= item.getTotalCopies();
        int amount =item.getAmount();
        String title= item.getTitleCert();
        String  prog= item.getTitleProg();
        String board= item.getTitleBoard();

        if (documentType != null) {
            holder.tv_title.setText(title+" - " + documentType + " (Copies: "+ copies +") ");
        }
        if (documentType.equalsIgnoreCase("Please Select")) {
            holder.tv_title.setText(title+ " -  "+ " (Copies: "+ copies +") ");
        }

        holder.tv_amount.setText(String.valueOf(amount));


    }//onBindView

    @Override
    public int getItemCount() {
            if (Global.DMList.size() > 0) {
                return Global.DMList.size();
            } else {
                return 0;
            }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_amount = itemView.findViewById(R.id.tv_amount);
        }
    }

}

