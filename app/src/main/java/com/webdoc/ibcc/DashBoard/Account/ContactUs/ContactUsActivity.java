package com.webdoc.ibcc.DashBoard.Account.ContactUs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.ActivityContactUsBinding;

public class ContactUsActivity extends AppCompatActivity {
    private ActivityContactUsBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityContactUsBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        layoutBinding.webView1.getSettings().setJavaScriptEnabled(true);
        layoutBinding.webView1.getSettings().setPluginState(WebSettings.PluginState.ON);
        layoutBinding.webView1.loadUrl("https://ibcc.edu.pk/contact-us/");
    }
}