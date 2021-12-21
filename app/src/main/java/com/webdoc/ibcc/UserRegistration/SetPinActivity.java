package com.webdoc.ibcc.UserRegistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.webdoc.ibcc.CompleteProfile.CompleteBasicInfo;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.ActivitySetPinBinding;

public class SetPinActivity extends AppCompatActivity {
    private String str_setPassword, str_confirmPassword;
    private ActivitySetPinBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivitySetPinBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        clickListeners();

    }

    private void clickListeners() {
        layoutBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Pin = layoutBinding.edtSetPin.getText().toString() + layoutBinding.edtConfirmPin.getText().toString();

                if (Pin.isEmpty() || Pin.length() < 8) {
                    Toast.makeText(getBaseContext(), "Please set pin", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    PinMatch();
                }
            }
        });
    }

    public void PinMatch(){
        str_setPassword = layoutBinding.edtSetPin.getText().toString().trim();
        str_confirmPassword = layoutBinding.edtConfirmPin.getText().toString().trim();

        if (str_setPassword.equals(str_confirmPassword)) {
            Global.registerUser.setPassword(str_confirmPassword);

            Global.utils.showSuccessSnakeBar(SetPinActivity.this, "Your Password set successfully.");
            Intent intent = new Intent(SetPinActivity.this, CompleteBasicInfo.class);
            startActivity(intent);
        } else {
            Toast.makeText(getBaseContext(), "Pin mismatch", Toast.LENGTH_LONG).show();
        }
    }

}