package com.webdoc.ibcc.PDFDownloader;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.webdoc.ibcc.Essentails.Constants;

public class PDFDownloader {
    private Uri uri;
    private String subPath;

    public void downloadPDF(Activity activity, String downloadForm) {
        DownloadManager downloadmanager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);

        switch (downloadForm) {
            case Constants.EQUIVALENCE_FORM:
                uri = Uri.parse(Constants.EQUIVALENCE_FORM_URL);
                subPath = "Equivalence Form.pdf";
                break;
            case Constants.ATTESTATION_FORM:
                uri = Uri.parse(Constants.ATTESTATION_FORM_URL);
                subPath = "Attestation Form.pdf";
                break;
            case Constants.CONVERSION_FORMULA:
                uri = Uri.parse(Constants.CONVERSION_FORM_URL);
                subPath = "Conversion Formula.pdf";
                break;
        }

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(subPath);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, subPath);
        downloadmanager.enqueue(request);
    }
}
