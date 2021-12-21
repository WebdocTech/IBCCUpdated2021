package com.webdoc.ibcc.Payment.PaymentMethods.EasyPaisaCreditDebit;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Payment.PaymentMethods.EasyPaisaCreditDebit.ViewModel.EasyPaisaCreditdebitViewModel;
import com.webdoc.ibcc.databinding.ActivityEasyPaisaCreditDebitBinding;

public class EasyPaisaCreditDebitActivity extends AppCompatActivity {
    private ActivityEasyPaisaCreditDebitBinding binding;
    private EasyPaisaCreditdebitViewModel viewModel;
    private String mobileNumber, email, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEasyPaisaCreditDebitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        clickListeners();
    }

    private void clickListeners() {

        binding.etAccountNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (binding.etAccountNo.getText().length() == 11) {
                    Global.utils.hideKeyboard(EasyPaisaCreditDebitActivity.this);
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

                if (Global.isEmailValid(binding.etCnic.getText().toString())) {
                    Global.utils.hideKeyboard(EasyPaisaCreditDebitActivity.this);
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
            public void onClick(View view) {

                Global.utils.showCustomLoadingDialog(EasyPaisaCreditDebitActivity.this);
                mobileNumber = binding.etAccountNo.getText().toString();
                email = binding.etCnic.getText().toString();
                Intent intent = new Intent(Global.applicationContext, WebviewActivity.class);
                intent.putExtra("mobileNumber", mobileNumber);
                intent.putExtra("email", email);
                startActivity(intent);
            }

        });

    }

    public void initViews() {
        //connecting View Model with activityyu
        viewModel = ViewModelProviders.of(this).get(EasyPaisaCreditdebitViewModel.class);

        Global.applicationContext = EasyPaisaCreditDebitActivity.this;

        binding.tvPkgPrice.setText(Global.price);
    }
}