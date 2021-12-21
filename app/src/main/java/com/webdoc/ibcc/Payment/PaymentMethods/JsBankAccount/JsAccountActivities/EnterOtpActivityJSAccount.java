package com.webdoc.ibcc.Payment.PaymentMethods.JsBankAccount.JsAccountActivities;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Payment.PaymentMethods.JsBankAccount.ViewModel.JsAccountEnterOtpViewModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.ActivityEnterOtpJSAccountBinding;

public class EnterOtpActivityJSAccount extends AppCompatActivity {
    private ActivityEnterOtpJSAccountBinding binding;
    private JsAccountEnterOtpViewModel viewModel;
    private String pinCode = "";
    private String case_id,
            user_id,
            amount_paid,
            bank,
            account_number,
            mobile_number,
            transection_type,
            transaction_reference_number,
            transaction_datetime,
            center,
            IBCC_amount,
            webdoc_amount,
            status, userIdEq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnterOtpJSAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //connecting View Model with activity
        viewModel = ViewModelProviders.of(this).get(JsAccountEnterOtpViewModel.class);

        ClickListeners();
        //Observers();
    }

    private void ClickListeners() {

        binding.btnPayNow.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                viewModel.OnPayNowClick(pinCode);
            }
        });

        binding.etPin1GetWalletPinFragPaymentMethodPaymentMethod.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                binding.etPin1GetWalletPinFragPaymentMethodPaymentMethod.getText().clear();
                binding.etPin2GetWalletPinFragPaymentMethodPaymentMethod.getText().clear();
                binding.etPin3GetWalletPinFragPaymentMethodPaymentMethod.getText().clear();
                binding.etPin4GetWalletPinFragPaymentMethodPaymentMethod.getText().clear();
                binding.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.getText().toString();

                binding.etPin1GetWalletPinFragPaymentMethodPaymentMethod.requestFocus();
                return false;
            }
        });

        binding.etPin2GetWalletPinFragPaymentMethodPaymentMethod.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                binding.etPin2GetWalletPinFragPaymentMethodPaymentMethod.getText().clear();
                binding.etPin3GetWalletPinFragPaymentMethodPaymentMethod.getText().clear();
                binding.etPin4GetWalletPinFragPaymentMethodPaymentMethod.getText().clear();
                binding.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.getText().toString();

                binding.etPin2GetWalletPinFragPaymentMethodPaymentMethod.requestFocus();

                return false;
            }
        });

        binding.etPin3GetWalletPinFragPaymentMethodPaymentMethod.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                binding.etPin3GetWalletPinFragPaymentMethodPaymentMethod.getText().clear();
                binding.etPin4GetWalletPinFragPaymentMethodPaymentMethod.getText().clear();
                binding.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.getText().toString();
                binding.etPin3GetWalletPinFragPaymentMethodPaymentMethod.requestFocus();

                return false;
            }
        });

        binding.etPin4GetWalletPinFragPaymentMethodPaymentMethod.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                binding.etPin4GetWalletPinFragPaymentMethodPaymentMethod.getText().clear();
                binding.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.getText().toString();
                binding.etPin4GetWalletPinFragPaymentMethodPaymentMethod.requestFocus();

                return false;
            }
        });

        binding.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                binding.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.getText().clear();
                binding.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.requestFocus();

                return false;
            }
        });

        binding.etPin1GetWalletPinFragPaymentMethodPaymentMethod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.etPin2GetWalletPinFragPaymentMethodPaymentMethod.requestFocus();
            }
        });

        binding.etPin2GetWalletPinFragPaymentMethodPaymentMethod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.etPin3GetWalletPinFragPaymentMethodPaymentMethod.requestFocus();
            }
        });

        binding.etPin3GetWalletPinFragPaymentMethodPaymentMethod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.etPin4GetWalletPinFragPaymentMethodPaymentMethod.requestFocus();
            }
        });

        binding.etPin4GetWalletPinFragPaymentMethodPaymentMethod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.requestFocus();
            }
        });

        binding.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.requestFocus();
                pinCode = binding.etPin1GetWalletPinFragPaymentMethodPaymentMethod.getText() + "" + binding.etPin2GetWalletPinFragPaymentMethodPaymentMethod.getText() + "" + binding.etPin3GetWalletPinFragPaymentMethodPaymentMethod.getText() + "" + binding.etPin4GetWalletPinFragPaymentMethodPaymentMethod.getText() + "" + binding.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.getText();

                if (pinCode.length() < 4) {
                    binding.etPin1GetWalletPinFragPaymentMethodPaymentMethod.requestFocus();
                    binding.etPin1GetWalletPinFragPaymentMethodPaymentMethod.getText().clear();
                    binding.etPin2GetWalletPinFragPaymentMethodPaymentMethod.getText().clear();
                    binding.etPin3GetWalletPinFragPaymentMethodPaymentMethod.getText().clear();
                    binding.etPin4GetWalletPinFragPaymentMethodPaymentMethod.getText().clear();
                    binding.etPin5GetWalletPinFragPaymentMethodPaymentMethod5.getText().clear();

                    binding.btnPayNow.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.circle_light_grey));
                    binding.btnPayNow.setEnabled(false);
                    binding.btnPayNow.clearAnimation();
                } else {
                    Global.utils.hideKeyboard(EnterOtpActivityJSAccount.this);
                    ActivatePaynowBtn();
                }
            }
        });

        binding.etOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 4) {
                    ActivatePaynowBtn();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void ActivatePaynowBtn() {
        binding.btnPayNow.setBackground(getApplication().getResources().getDrawable(R.drawable.circle_blue));
        binding.btnPayNow.setEnabled(true);
        binding.btnPayNow.startAnimation(Global.utils.startBlinkAnimation(this));
    }

}