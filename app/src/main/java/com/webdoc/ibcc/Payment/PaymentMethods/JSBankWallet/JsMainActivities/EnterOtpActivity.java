package com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.JsMainActivities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.DocumentCheckList.DocumentChecklisActivity;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.CallCourier_EQ.CallCourier_EQ;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ResponseModel.jsDebitPaymentResponse.JsDebitPaymentResponse;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ViewModel.EnterOtpViewModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.SavePaymentInfo.SavePaymentInfo;
import com.webdoc.ibcc.databinding.ActivityEnterOtpBinding;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EnterOtpActivity extends AppCompatActivity {
    private ActivityEnterOtpBinding binding;
    private EnterOtpViewModel viewModel;
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
        binding = ActivityEnterOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //connecting View Model with activity
        viewModel = ViewModelProviders.of(this).get(EnterOtpViewModel.class);

        ClickListeners();
        Observers();
    }

    private void Observers() {

        viewModel.LD_JsPaymentFinal().observe(this, s -> {

            if (s != null) {
                if (s.getDebitPaymentResponse().getResponseCode().equals("00")) {

                    if (!TextUtils.isEmpty(Global.caseId)) {
                        case_id = Global.caseId;
                    } else {
                        case_id = Global.incompleteCaseId;
                    }
                    user_id = Global.userLoginResponse.getResult().getCustomerProfile().getId();
                    amount_paid = String.valueOf(Global.price);
                    bank = "JsBank";
                    account_number = Global.JS_Wallet_Account_Number;
                    mobile_number = Global.userLoginResponse.getResult().getCustomerProfile().getMobile();
                    transection_type = "Wallet";
                    transaction_reference_number = s.getDebitPaymentResponse().getTransactioId();
                    transaction_datetime = s.getDebitPaymentResponse().getDateTime();
                    center = Global.center;
                    IBCC_amount = String.valueOf(Global.ibccAmount);
                    webdoc_amount = String.valueOf(Global.webdocAmount);
                    status = "Success";
                    userIdEq = String.valueOf(Global.userLoginResponse.getResult().getCustomerProfile().getId());
                    String bankCharges = Global.bankChargesForReceipt;
                    String courier_amount = "200";
                    String platform = "Android";

                    if (Global.isFromEquivalence) {

                        viewModel.callingSavePaymentForEquilance(case_id, amount_paid, bank, account_number, mobile_number, transection_type, transaction_reference_number, transaction_datetime, userIdEq, status, webdoc_amount, IBCC_amount, transaction_reference_number, bankCharges, courier_amount, platform);
                    } else {

                        viewModel.CallingSavePaymentApiOnEasyPaisaSuccess(IBCC_amount, webdoc_amount, case_id, amount_paid, bank, account_number, mobile_number, transection_type, transaction_reference_number, transaction_datetime, user_id, transaction_reference_number, bankCharges, courier_amount, platform);

                    }


                    Global.utils.hideCustomLoadingDialog();
                } else {
                    Global.utils.hideCustomLoadingDialog();
                    Toast.makeText(this, "Merchant type missing or invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.LD_savePaymentInfo().observe(this, s -> {

            if (s instanceof SavePaymentInfo) {

                if (s.getResult().getResponseCode().equals("0000")) {
                    Global.savePaymentInfo = s;
                    Global.utils.hideCustomLoadingDialog();

                    /*Intent intent = new Intent(EnterOtpActivity.this, DocumentChecklisActivity.class);
                    intent.putExtra("appointment_method", transection_type);
                    intent.putExtra("trx_id", transaction_reference_number);
                    intent.putExtra("bank_name", bank);
                    intent.putExtra("payment_status", "active");
                    startActivity(intent);*/

                    if (Global.isFromEquivalence) {
                        Intent intent = new Intent(EnterOtpActivity.this, CallCourier_EQ.class);
                        intent.putExtra("appointment_method", transection_type);
                        intent.putExtra("trx_id", transaction_reference_number);
                        intent.putExtra("bank_name", bank);
                        intent.putExtra("payment_status", "active");
                        finish();
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent(EnterOtpActivity.this, DocumentChecklisActivity.class);
                        intent.putExtra("appointment_method", transection_type);
                        intent.putExtra("trx_id", transaction_reference_number);
                        intent.putExtra("bank_name", bank);
                        intent.putExtra("payment_status", "active");
                        finish();
                        startActivity(intent);
                    }

                }
            }
        });

        viewModel.LD_savePaymentInfoEquialance().observe(this, s -> {

            if (s instanceof SavePaymentInfo) {

                if (s.getResult().getResponseCode().equals("0000")) {
                    Global.savePaymentInfo = s;
                    Global.utils.hideCustomLoadingDialog();

                    Global.utils.showSuccessSnakeBar(EnterOtpActivity.this, "Success");

                    new SweetAlertDialog(EnterOtpActivity.this, SweetAlertDialog.SUCCESS_TYPE)

                            .setContentText("Your Transaction Number " + transaction_reference_number + " is generated. please save ")
                            .setConfirmText("Yes")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();

                                    /*Intent intent = new Intent(EnterOtpActivity.this, CallCourier_EQ.class);
                                    intent.putExtra("appointment_method", transection_type);
                                    intent.putExtra("trx_id", transaction_reference_number);
                                    intent.putExtra("bank_name", bank);
                                    intent.putExtra("payment_status", "Pending");
                                    startActivity(intent);*/

                                    if (Global.isFromEquivalence) {
                                        Intent intent = new Intent(EnterOtpActivity.this, CallCourier_EQ.class);
                                        intent.putExtra("appointment_method", transection_type);
                                        intent.putExtra("trx_id", transaction_reference_number);
                                        intent.putExtra("bank_name", bank);
                                        intent.putExtra("payment_status", "Pending");
                                        finish();
                                        startActivity(intent);

                                    } else {
                                        Intent intent = new Intent(EnterOtpActivity.this, DocumentChecklisActivity.class);
                                        intent.putExtra("appointment_method", transection_type);
                                        intent.putExtra("trx_id", transaction_reference_number);
                                        intent.putExtra("bank_name", bank);
                                        intent.putExtra("payment_status", "Pending");
                                        finish();
                                        startActivity(intent);
                                    }

                                }
                            })
                            .show();


                } else {
                    Global.utils.hideCustomLoadingDialog();
                    Toast.makeText(this, "resp fail ", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                    Global.utils.hideKeyboard(EnterOtpActivity.this);
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
                if (charSequence.length() > 5) {
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