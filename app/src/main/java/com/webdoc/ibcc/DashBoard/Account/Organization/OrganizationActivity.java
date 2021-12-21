package com.webdoc.ibcc.DashBoard.Account.Organization;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.ActivityContactUsBinding;
import com.webdoc.ibcc.databinding.ActivityOrganizationBinding;

public class OrganizationActivity extends AppCompatActivity {
    private ActivityOrganizationBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);
        layoutBinding = ActivityOrganizationBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        layoutBinding.webView1.getSettings().setJavaScriptEnabled(true);
        layoutBinding.webView1.getSettings().setPluginState(WebSettings.PluginState.ON);
        layoutBinding.webView1.loadUrl("https://ibcc.edu.pk/organization/");

    }
}