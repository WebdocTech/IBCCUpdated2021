package com.webdoc.ibcc.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.AttestationApplyActivity;
import com.webdoc.ibcc.Model.CategoriesModel;
import com.webdoc.ibcc.R;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    Activity context;

    public CategoriesAdapter(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_categories_item, parent, false);
        return new CategoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoriesAdapter.ViewHolder holder, final int position) {
        final CategoriesModel myListData = Global.categoryTitle.get(position);
        final String name = myListData.getText();

        holder.title.setText(name);

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (name)
                {
                    case "Equivalence":
                        break;
                    case "Attestation":
                        Intent intent = new Intent(context, AttestationApplyActivity.class);
                        context.startActivity(intent);
                        break;
                    case "Verification":
                        break;
                    case "DownLoads":
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Global.categoryTitle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
        }
    }

}
