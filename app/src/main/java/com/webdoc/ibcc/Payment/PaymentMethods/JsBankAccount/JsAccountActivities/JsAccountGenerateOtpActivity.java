package com.webdoc.ibcc.Payment.PaymentMethods.JsBankAccount.JsAccountActivities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Payment.PaymentMethods.JsBankAccount.ResponseModels.JsGenerateOtpResponse.JsGenerateOtpResponse;
import com.webdoc.ibcc.Payment.PaymentMethods.JsBankAccount.ViewModel.JsAccountGenerateOtpViewModel;
import com.webdoc.ibcc.databinding.ActivityJsAccountGenerateOtpBinding;

public class JsAccountGenerateOtpActivity extends AppCompatActivity {
    private ActivityJsAccountGenerateOtpBinding binding;
    private JsAccountGenerateOtpViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJsAccountGenerateOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //connecting View Model with activity
        viewModel = ViewModelProviders.of(this).get(JsAccountGenerateOtpViewModel.class);

        clickListeners();
        observers();
    }

    private void clickListeners() {

        binding.etAccountNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (binding.etAccountNo.getText().length() == 11) {
                    Global.utils.hideKeyboard(JsAccountGenerateOtpActivity.this);
                    binding.ccEnterCnic.setVisibility(View.VISIBLE);
                } else {
                    binding.ccEnterCnic.setVisibility(View.GONE);
                }
            }


            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.etCnic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (binding.etCnic.getText().length() == 6) {
                    Global.utils.hideKeyboard(JsAccountGenerateOtpActivity.this);
                    binding.btnNext.setVisibility(View.VISIBLE);
                } else {
                    binding.btnNext.setVisibility(View.GONE);
                }
            }


            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String accountNumber = binding.etAccountNo.getText().toString();
                String cnic = "dfdsfd";
                Global.JSBankAccountNo = accountNumber;
                viewModel.newGenerateOtpFunc();

            }
        });
    }

    private void observers() {
        viewModel.LD_JsAccounGenerateOtpApi().observe(this, s -> {

            if (s instanceof JsGenerateOtpResponse) {

                if (Global.jsAccountGenerateOtp.getResponseCode().equals("00")) {
                    //intent to enter otp activty
                    Intent intent = new Intent(JsAccountGenerateOtpActivity.this, EnterOtpActivityJSAccount.class);
                    startActivity(intent);

                } else {

                }
            }
        });
    }
}