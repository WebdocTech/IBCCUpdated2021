package com.webdoc.ibcc.DashBoard.Home.EventsGallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.webdoc.ibcc.Adapter.EventsAdapter;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.EventsModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.ActivityEventsGalleryBinding;

public class EventsGalleryActivity extends AppCompatActivity {
    EventsAdapter eventsAdapter;
    private ActivityEventsGalleryBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityEventsGalleryBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        Global.eventsModels.clear();
        final String[] title = { "IPEMC Meeting","Federal Minister of FE&PT Visited IBCC", "The Counsellor for Education, Republic of Turkey, based in Islamabad", "IBCC 168th forum meeting", "165th Meeting of Equivalence Committee"};
        final int[] image = {R.drawable.ipemc_meeting_1,R.drawable.federal_minister_3, R.drawable.counseller_1, R.drawable.sixtyeight_1, R.drawable.sixtyfive_meeting_2};

        for (int i = 0; i < title.length; i++) {
            EventsModel model = new EventsModel(title[i], image[i]);
            Global.eventsModels.add(model);
        }

        clickListeners();
        setAdapter();

    }

    private void clickListeners() {
        layoutBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvEvents.setLayoutManager(layoutManager);
        layoutBinding.rvEvents.setHasFixedSize(true);
        eventsAdapter = new EventsAdapter(this);
        layoutBinding.rvEvents.setAdapter(eventsAdapter);
    }
}