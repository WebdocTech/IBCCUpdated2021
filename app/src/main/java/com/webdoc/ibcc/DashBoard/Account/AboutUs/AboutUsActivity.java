package com.webdoc.ibcc.DashBoard.Account.AboutUs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.ActivityAboutUsBinding;

public class AboutUsActivity extends AppCompatActivity {
    private ActivityAboutUsBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityAboutUsBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        layoutBinding.webView1.getSettings().setJavaScriptEnabled(true);
        layoutBinding.webView1.getSettings().setPluginState(WebSettings.PluginState.ON);
        layoutBinding.webView1.loadUrl("https://ibcc.edu.pk/about-ibcc/");

    }
}