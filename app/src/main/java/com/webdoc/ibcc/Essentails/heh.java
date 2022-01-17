package com.webdoc.ibcc.Essentails;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.webdoc.ibcc.R;

import java.io.File;

public class heh extends AppCompatActivity {
    Button button5;
    private static final int PICK_PDF_FILE = 2;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heh);
        button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int version = getAndroidVersion();

                if (Build.VERSION.SDK_INT >= 30) {
                    //only api 21 above
                    Toast.makeText(heh.this, "Unable to select files in Android version 11", Toast.LENGTH_SHORT).show();

                } else {
                    //only api 21 down
                    openFile();
                }


            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (requestCode == PICK_PDF_FILE
                && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                //  file = new File(FileUitls.getRealPath(uri));


                String filename = uri.getPath();
                filename = filename.substring(filename.lastIndexOf("/") + 1);
                Toast.makeText(heh.this, filename, Toast.LENGTH_SHORT).show();

                // Perform operations on the document using its URI.
            }
        }
    }

    private void openFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.

        startActivityForResult(intent, PICK_PDF_FILE);
    }

    public int getAndroidVersion() {
        String release = Build.VERSION.RELEASE;
        int sdkVersion = Build.VERSION.SDK_INT;
        return sdkVersion;
    }

}