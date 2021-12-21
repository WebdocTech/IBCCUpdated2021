package com.webdoc.ibcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Essentails.Utils;
import com.webdoc.ibcc.databinding.ActivityInternetErrorBinding;

public class InternetError extends AppCompatActivity {
    private ActivityInternetErrorBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityInternetErrorBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        layoutBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Settings.ACTION_SETTINGS);
                finish();
                if (Utils.alertDialog != null && Utils.alertDialog.isShowing()) {
                    Global.utils.hideCustomLoadingDialog();
                }
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Utils.alertDialog != null && Utils.alertDialog.isShowing()) {
            Global.utils.hideCustomLoadingDialog();
        }
        finish();
    }
}