package com.webdoc.ibcc.DashBoard.Home.Verification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.ActivityPdfViewBinding;

public class PdfView extends AppCompatActivity {
    String pdfUrl, pdfTitle;
    private ActivityPdfViewBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityPdfViewBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        clickListeners();
        setttingWebView();
    }

    private void clickListeners() {
        layoutBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setttingWebView() {
        Intent intent = getIntent();
        layoutBinding.webView.getSettings().setJavaScriptEnabled(true);
        pdfUrl = intent.getStringExtra("pdfUrl");
        layoutBinding.webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdfUrl);
        pdfTitle = intent.getStringExtra("pdfName");
        layoutBinding.tvTitle.setText(pdfTitle);
    }
}