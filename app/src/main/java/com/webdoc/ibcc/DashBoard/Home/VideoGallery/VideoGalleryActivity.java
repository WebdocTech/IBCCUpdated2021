package com.webdoc.ibcc.DashBoard.Home.VideoGallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.webdoc.ibcc.Adapter.SelectedDocumentAdapter;
import com.webdoc.ibcc.Adapter.VideosAdapter;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.SelectedDocumentModel;
import com.webdoc.ibcc.Model.VideosModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.ActivityVideoGalleryBinding;

public class VideoGalleryActivity extends AppCompatActivity {
    private VideosAdapter videosAdapter;
    private ActivityVideoGalleryBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityVideoGalleryBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());


        if (Global.utils.HaveNetwork(this)) {
            layoutBinding.rvVideos.setVisibility(View.VISIBLE);
            layoutBinding.NoInternetLayout.setVisibility(View.GONE);
        }else{
            layoutBinding.rvVideos.setVisibility(View.GONE);
            layoutBinding.NoInternetLayout.setVisibility(View.VISIBLE);
        }


        Global.videosModel.clear();
        final String[] title = {"IBCC initiatives on COVID 19","Secretary message to students","Automation of IBCC","Secretary IBCC Pakistan Interview"};
        final String[] duration = {"2:26","0:26","0:39","7:10"};
        final String[] date = {"Dec 30, 2020","Dec 30, 2020","Dec 30, 2020","Dec 27, 2020"};
        final String[] url={"https://www.youtube-nocookie.com/embed/djADrmmWspg?rel=0","https://www.youtube-nocookie.com/embed/o3jHXt_ca6c?rel=0",
       "https://www.youtube-nocookie.com/embed/0Nlvi_2M_P8?rel=0","https://www.youtube-nocookie.com/embed/QMz-LdeZVjA?rel=0"};

        for(int i=0; i<title.length; i++) {
            VideosModel model = new  VideosModel(url[i],date[i],duration[i],title[i]);
            Global.videosModel.add(model);
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
        layoutBinding.rvVideos.setLayoutManager(layoutManager);
        layoutBinding.rvVideos.setHasFixedSize(true);
        videosAdapter = new VideosAdapter(this);
        layoutBinding.rvVideos.setAdapter(videosAdapter);
    }
}