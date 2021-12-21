package com.webdoc.ibcc.DashBoard.Account.AimsObjective;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.ActivityAimsObjectiveBinding;

public class Aims_ObjectiveActivity extends AppCompatActivity {
    private ActivityAimsObjectiveBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityAimsObjectiveBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        layoutBinding.webView1.getSettings().setJavaScriptEnabled(true);
        layoutBinding.webView1.getSettings().setPluginState(WebSettings.PluginState.ON);
        layoutBinding.webView1.loadUrl("https://ibcc.edu.pk/aims-objectives/");

    }
}