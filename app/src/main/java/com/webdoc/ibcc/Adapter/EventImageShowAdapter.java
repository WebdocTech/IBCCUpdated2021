package com.webdoc.ibcc.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.webdoc.ibcc.DashBoard.Home.EventsGallery.GalleryImageShow;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.EventImageShowModel;
import com.webdoc.ibcc.R;

public class EventImageShowAdapter extends RecyclerView.Adapter<EventImageShowAdapter.ViewHolder> {
    Activity context;

    public EventImageShowAdapter(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public EventImageShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_showimg_item, parent, false);
        return new EventImageShowAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventImageShowAdapter.ViewHolder holder, final int position) {
        EventImageShowModel item = Global.eventImageShowModels.get(position);
        int image = item.getImage();

        Picasso.get()
                .load(image)
                .placeholder(R.color.gray)
                .into(holder.iv_list);

        holder.iv_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GalleryImageShow.iv_ImageShow.setImageResource(image);
            }
        });

    }//onBindView

    @Override
    public int getItemCount() {
        return Global.eventImageShowModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_list;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_list= itemView.findViewById(R.id.iv_list);
        }
    }

}
