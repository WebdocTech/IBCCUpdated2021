package com.webdoc.ibcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.webdoc.ibcc.CompleteProfile.CompleteAddressActivity;
import com.webdoc.ibcc.CompleteProfile.CompleteBasicInfo;
import com.webdoc.ibcc.CompleteProfile.CompleteProfilePic;
import com.webdoc.ibcc.UserLogin.LoginActivity;
import com.webdoc.ibcc.UserRegistration.SetPinActivity;
import com.webdoc.ibcc.UserRegistration.SignUpActivity;
import com.webdoc.ibcc.databinding.ActivityLoginBinding;
import com.webdoc.ibcc.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        clickListeners();
    }

    private void clickListeners() {
        layoutBinding.btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        layoutBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }
}