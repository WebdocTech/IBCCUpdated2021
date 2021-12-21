package com.webdoc.ibcc.DashBoard.Home.EventsGallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.webdoc.ibcc.Adapter.EventImageShowAdapter;
import com.webdoc.ibcc.Adapter.EventsAdapter;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.EventImageShowModel;
import com.webdoc.ibcc.Model.EventsModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.ActivityGalleryImageShowBinding;

import java.util.List;

public class GalleryImageShow extends AppCompatActivity {
    ImageView iv_back;
    public static ImageView iv_ImageShow;
    TextView tv_title;
    RecyclerView rv_images;
    EventImageShowAdapter eventImageShowAdapter;
    EventImageShowModel model;
    private ActivityGalleryImageShowBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityGalleryImageShowBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        //Statics:
        iv_ImageShow = layoutBinding.ivImageShow;

        layoutBinding.ivImageShow.setImageResource(Global.selectedImage);

        settingDataInArrayList();
        setAdapter();
        clickListeners();

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
                LinearLayoutManager.HORIZONTAL, false);
        layoutBinding.rvImages.setLayoutManager(layoutManager);
        layoutBinding.rvImages.setHasFixedSize(true);
        eventImageShowAdapter = new EventImageShowAdapter(this);
        layoutBinding.rvImages.setAdapter(eventImageShowAdapter);
    }

    public void settingDataInArrayList() {
        Global.eventImageShowModels.clear();
        switch (Global.selectedImageTitle) {
            case "IPEMC Meeting":
                final int[] IpemcList = {R.drawable.ipemc_meeting_1};
                for (int i = 0; i < IpemcList.length; i++) {
                    model = new EventImageShowModel(IpemcList[i]);
                    Global.eventImageShowModels.add(model);
                }
                break;

            case "Federal Minister of FE&PT Visited IBCC":
                final int[] list2 = {R.drawable.federal_minister_3, R.drawable.federal_minister_2, R.drawable.federal_minister_1, R.drawable.federal_minister_4, R.drawable.federal_minister_5,
                        R.drawable.federal_minister_6, R.drawable.federal_minister_7, R.drawable.federal_minister_8, R.drawable.federal_minister_9};
                for (int i = 0; i < list2.length; i++) {
                    model = new EventImageShowModel(list2[i]);
                    Global.eventImageShowModels.add(model);
                }
                break;

            case "The Counsellor for Education, Republic of Turkey, based in Islamabad":
                final int[] list3 = {R.drawable.counseller_1, R.drawable.counseller_2};
                for (int i = 0; i < list3.length; i++) {
                    model = new EventImageShowModel(list3[i]);
                    Global.eventImageShowModels.add(model);
                }
                break;

            case "IBCC 168th forum meeting":
                final int[] list4 = {R.drawable.sixtyeight_1, R.drawable.sixtyeight_2, R.drawable.sixtyeight_3, R.drawable.sixtyeight_4, R.drawable.sixtyeight_5,
                        R.drawable.sixtyeight_6, R.drawable.sixtyeight_7, R.drawable.sixtyeight_8};
                for (int i = 0; i < list4.length; i++) {
                    model = new EventImageShowModel(list4[i]);
                    Global.eventImageShowModels.add(model);
                }
                break;

            case "165th Meeting of Equivalence Committee":
                final int[] list5 = {R.drawable.sixtyfive_meeting_2, R.drawable.sixtyfive_meeting_1, R.drawable.sixtyfive_meeting_3};
                for (int i = 0; i < list5.length; i++) {
                    model = new EventImageShowModel(list5[i]);
                    Global.eventImageShowModels.add(model);
                }
                break;
        }
    }
}