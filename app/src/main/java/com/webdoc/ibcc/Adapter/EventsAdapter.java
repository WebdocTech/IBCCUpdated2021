package com.webdoc.ibcc.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.webdoc.ibcc.DashBoard.Home.EventsGallery.GalleryImageShow;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.EventsModel;
import com.webdoc.ibcc.R;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    Activity context;

    public EventsAdapter(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_item, parent, false);
        return new EventsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventsAdapter.ViewHolder holder, final int position) {
        EventsModel item = Global.eventsModels.get(position);
        String title = item.getTitle();
        int image = item.getImages();

        holder.tv_title.setText(title);
        //holder.iv_event.setImageResource(image);

        Picasso.get()
                .load(image)
                .placeholder(R.color.gray)
                .into(holder.iv_event);

        holder.eventCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (title) {
                    case "IPEMC Meeting":
                        break;
                    case "Federal Minister of FE&PT Visited IBCC":
                        break;
                    case "The Counsellor for Education, Republic of Turkey, based in Islamabad":
                        break;
                    case "IBCC 168th forum meeting":
                        break;
                    case "165th Meeting of Equivalence Committee":
                        break;
                }

                Intent intent = new Intent(context, GalleryImageShow.class);
                Global.selectedImageTitle = title;
                Global.selectedImage = image;
                context.startActivity(intent);
            }
        });

    }//onBindView

    @Override
    public int getItemCount() {
        return Global.eventsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        CardView eventCard;
        ImageView iv_event;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            eventCard = itemView.findViewById(R.id.eventCard);
            iv_event = itemView.findViewById(R.id.iv_event);
        }
    }

}
