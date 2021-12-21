package com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.JsMainActivities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ResponseModel.JsBankAuthApi;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ResponseModel.jsDebitInquiryResult.JsDebitInquiryResult;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ViewModel.JsBankViewModel;
import com.webdoc.ibcc.databinding.ActivityJsBankBinding;

public class JsBankActivity extends AppCompatActivity {
    ActivityJsBankBinding binding;
    JsBankViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJsBankBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        clickListeners();
        observers();
    }

    private void observers() {
        viewModel.LD_JsAuthAPi().observe(this, s -> {

            if (s instanceof JsBankAuthApi) {

                if (Global.authApiResp.getAccessToken() != null) {
                    Global.newToken = s.getAccessToken();
                    Global.utils.hideCustomLoadingDialog();
                }
            }
        });

        viewModel.LD_JsPAymentInquiryApi().observe(this, s -> {

            if (s != null) {
                if (s.getDebitInqResponse().getResponseCode().equals("00")) {
                    Intent intent = new Intent(JsBankActivity.this, EnterOtpActivity.class);
                    startActivity(intent);
                    Global.utils.hideCustomLoadingDialog();
                } else if (s.getDebitInqResponse().getResponseCode().equals("98")) {
                    Global.utils.hideCustomLoadingDialog();
                    Toast.makeText(this, "Merchant type missing or invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews() {
        Global.applicationContext = this;
        //todo:connecting View Model with activity
        viewModel = ViewModelProviders.of(this).get(JsBankViewModel.class);
        Global.utils.showCustomLoadingDialog(JsBankActivity.this);
        viewModel.JSBankAuthApi();
    }

    private void clickListeners() {
        binding.etAccountNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (binding.etAccountNo.getText().length() == 11) {
                    Global.utils.hideKeyboard(JsBankActivity.this);
                    binding.btnNext.setVisibility(View.VISIBLE);
                } else {
                    binding.ccEnterCnic.setVisibility(View.GONE);
                }
            }


            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MobileNo = binding.etAccountNo.getText().toString();
                Global.JS_Wallet_Account_Number = MobileNo;
                viewModel.jsPaymentInquiryApi(MobileNo);
            }
        });
    }
}