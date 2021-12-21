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
import com.webdoc.ibcc.ResponseModels.PdfResult.Pdf;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;

public class EquivalenceAdapter extends RecyclerView.Adapter< EquivalenceAdapter.ViewHolder> {
    Activity context;
    VolleyRequestController volleyRequestController;

    public  EquivalenceAdapter (Activity context) {
        this.context = context;
        volleyRequestController=new VolleyRequestController(context);
    }

    @NonNull
    @Override
    public  EquivalenceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.equivalence_item, parent, false);
        return new  EquivalenceAdapter .ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EquivalenceAdapter.ViewHolder holder, final int position) {
        final Pdf item = Global.pdfResponse.getResult().getPdf().get(position);
        final String title = item.getName();
        final String date = item.getDate();
        final String pdf = item.getPdfUrl();

        holder.tv_name.setText(title);

        if (position % 2 == 1) {
            holder.tv_name.setBackgroundResource(R.drawable.border_rectangle_white);
        } else {
            holder.tv_name.setBackgroundResource(R.drawable.border_rectangle_gray);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.utils.showCustomLoadingDialog(context);
                volleyRequestController.Pdf();
            }
        });
    }


    @Override
    public int getItemCount() {
        return Global.pdfResponse.getResult().getPdf().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }
}
