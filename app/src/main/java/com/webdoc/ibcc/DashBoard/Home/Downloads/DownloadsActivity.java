package com.webdoc.ibcc.DashBoard.Home.Downloads;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.PDFDownloader.PDFDownloader;
import com.webdoc.ibcc.Permissions.ActivityPermissions;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.ActivityDownloadsBinding;

public class DownloadsActivity extends AppCompatActivity {
    PDFDownloader pdfDownloader;
    String downloadForm;
    public static final int STORAGE_REQUEST_CODE = 5;
    private ActivityDownloadsBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityDownloadsBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        pdfDownloader = new PDFDownloader();
        clickListeners();

    }

    private void clickListeners() {
        layoutBinding.tvEquivalenceForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadForm = Constants.EQUIVALENCE_FORM;

                if (ActivityPermissions.readAndWriteExternalStorage(DownloadsActivity.this, STORAGE_REQUEST_CODE)) {
                    pdfDownloader.downloadPDF(DownloadsActivity.this, Constants.EQUIVALENCE_FORM);
                }
            }
        });

        layoutBinding.tvAttestationForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadForm = Constants.ATTESTATION_FORM;

                if (ActivityPermissions.readAndWriteExternalStorage(DownloadsActivity.this, STORAGE_REQUEST_CODE)) {
                    pdfDownloader.downloadPDF(DownloadsActivity.this, Constants.ATTESTATION_FORM);
                }
            }
        });

        layoutBinding.tvConversionFormula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadForm = Constants.CONVERSION_FORMULA;

                if (ActivityPermissions.readAndWriteExternalStorage(DownloadsActivity.this, STORAGE_REQUEST_CODE)) {
                    pdfDownloader.downloadPDF(DownloadsActivity.this, Constants.CONVERSION_FORMULA);
                }
            }
        });

        layoutBinding.tvImportantResolutions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DownloadsActivity.this, "We have currently no document for this", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == STORAGE_REQUEST_CODE) {
            //you have the permission now.
            switch (downloadForm) {
                case Constants.EQUIVALENCE_FORM:
                    pdfDownloader.downloadPDF(DownloadsActivity.this, Constants.EQUIVALENCE_FORM);
                    break;
                case Constants.ATTESTATION_FORM:
                    pdfDownloader.downloadPDF(DownloadsActivity.this, Constants.ATTESTATION_FORM);
                    break;
                case Constants.CONVERSION_FORMULA:
                    pdfDownloader.downloadPDF(DownloadsActivity.this, Constants.CONVERSION_FORMULA);
                    break;
            }
        }
    }
}