package com.webdoc.ibcc.Payment.PaymentMethods.JazzCash;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.DocumentCheckList.DocumentChecklisActivity;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.CallCourier_EQ.CallCourier_EQ;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Payment.PaymentMethods.JazzCash.ResponseModels.JazzCashResponseNew;
import com.webdoc.ibcc.Payment.PaymentMethods.JazzCash.ViewModel.JazzCashViewModel;
import com.webdoc.ibcc.ResponseModels.SavePaymentInfo.SavePaymentInfo;
import com.webdoc.ibcc.databinding.ActivityJazzCashPaymentBinding;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class JazzCashPaymentActivity extends AppCompatActivity {
    private ActivityJazzCashPaymentBinding binding;
    private JazzCashViewModel viewModel;
    private String mobileNumber, cnic;
    private String case_id, user_id, amount_paid, bank, account_number,
            mobile_number, transection_type, transaction_reference_number,
            transaction_datetime, center, IBCC_amount, webdoc_amount,
            status, userIdEq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJazzCashPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        clickListeners();
        observer();
    }

    private void observer() {
        viewModel.LD_callingJazzCash().observe(this, s -> {
            if (s instanceof JazzCashResponseNew) {
                if (s.getPpResponseCode().equals("000")) {

                    if (!TextUtils.isEmpty(Global.caseId)) {
                        case_id = Global.caseId;
                    } else {
                        case_id = Global.incompleteCaseId;
                    }

                    user_id = Global.userLoginResponse.getResult().getCustomerProfile().getId();
                    amount_paid = s.getPpAmount();
                    bank = "JazzCashWallet";
                    account_number = mobileNumber;
                    mobile_number = Global.userLoginResponse.getResult().getCustomerProfile().getMobile();
                    transection_type = "Wallet";
                    transaction_reference_number = s.getPpTxnRefNo();
                    transaction_datetime = s.getPpTxnDateTime();
                    center = Global.center;
                    IBCC_amount = String.valueOf(Global.ibccAmount);
                    webdoc_amount = String.valueOf(Global.webdocAmount);
                    status = "Success";
                    userIdEq = String.valueOf(Global.equivalenceInitiateCaseResponse.getResult().getIntiateCaseResponseDetails().getCustomerId());
                    String bankCharges = Global.bankChargesForReceipt;
                    String courier_amount = "200";
                    String platform = "Android";

                    if (Global.isFromEquivalence) {

                        viewModel.callingSavePaymentForEquilance(case_id, amount_paid, bank, account_number, mobile_number, transection_type, transaction_reference_number, transaction_datetime, userIdEq, status, webdoc_amount, IBCC_amount, transaction_reference_number, bankCharges, courier_amount, platform);

                    } else {

                        viewModel.CallingSavePaymentApiOnJazzCashSucess(IBCC_amount, webdoc_amount, case_id, amount_paid, bank, account_number, mobile_number, transection_type, transaction_reference_number, transaction_datetime, user_id, transaction_reference_number, bankCharges, courier_amount, platform);

                    }

                }
            }
        });

        viewModel.LD_savePaymentInfo().observe(this, s -> {
            if (s instanceof SavePaymentInfo) {

                if (s.getResult().getResponseCode().equals("0000")) {
                    Global.utils.hideCustomLoadingDialog();
                    Global.savePaymentInfo = s;


                  /*  Intent intent = new Intent(JazzCashPaymentActivity.this, CallCourier.class);
                    intent.putExtra("appointment_method", transection_type);
                    intent.putExtra("trx_id", transaction_reference_number);
                    intent.putExtra("bank_name", bank);
                    intent.putExtra("payment_status", "active");
                    finish();
                    startActivity(intent);*/

                    if (Global.isFromEquivalence) {

                        if (Global.isIncompleteAppointmentEQ) {
                            Global.incompleteDetailsEQResponse.getResult().getPaymentinfo().setIbccAmount(String.valueOf(Global.ibccAmount));
                            Global.incompleteDetailsEQResponse.getResult().getPaymentinfo().setWebdocAmount(String.valueOf(Global.webdocAmount));
                            Global.incompleteDetailsEQResponse.getResult().getPaymentinfo().setChalanId(String.valueOf(s.getResult().getChallan_id()));
                            Global.incompleteDetailsEQResponse.getResult().getPaymentinfo().setTransactionid(String.valueOf(transaction_reference_number));
                            Global.incompleteDetailsEQResponse.getResult().getPaymentinfo().setBankname(bank);
                            Global.incompleteDetailsEQResponse.getResult().getPaymentinfo().setPaymentstatus("Pending");
                            Global.incompleteDetailsEQResponse.getResult().getPaymentinfo().setAppointmentMethod(String.valueOf(transection_type));
                            if (Global.incompleteDetailsEQResponse.getResult().getStepNumber() != "5")
                                if (Global.secuirtyFeeForReceiptEQ.contains("50")) {
                                    Global.incompleteDetailsEQResponse.getResult().getPaymentinfo().setIsSecurityCheck(true);

                                } else {
                                    Global.incompleteDetailsEQResponse.getResult().getPaymentinfo().setIsSecurityCheck(false);
                                }
                        }
                        Intent intent = new Intent(JazzCashPaymentActivity.this, CallCourier_EQ.class);
                        intent.putExtra("appointment_method", transection_type);
                        intent.putExtra("trx_id", transaction_reference_number);
                        intent.putExtra("bank_name", bank);
                        intent.putExtra("payment_status", "Pending");
                        finish();
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent(JazzCashPaymentActivity.this, DocumentChecklisActivity.class);
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

                    Global.utils.showSuccessSnakeBar(JazzCashPaymentActivity.this, "Success");

                    new SweetAlertDialog(JazzCashPaymentActivity.this, SweetAlertDialog.SUCCESS_TYPE)

                            .setContentText("Your Transaction Number " + transaction_reference_number + " is generated. please pay on any near Easypaisa shop against this transection number")
                            .setConfirmText("Yes")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();

                                    /*Intent intent = new Intent(JazzCashPaymentActivity.this, CallCourier.class);
                                    intent.putExtra("appointment_method", transection_type);
                                    intent.putExtra("trx_id", transaction_reference_number);
                                    intent.putExtra("bank_name", bank);
                                    intent.putExtra("payment_status", "Pending");
                                    startActivity(intent);*/

                                    if (Global.isFromEquivalence) {
                                        Intent intent = new Intent(JazzCashPaymentActivity.this, CallCourier_EQ.class);
                                        intent.putExtra("appointment_method", transection_type);
                                        intent.putExtra("trx_id", transaction_reference_number);
                                        intent.putExtra("bank_name", bank);
                                        intent.putExtra("payment_status", "Pending");
                                        finish();
                                        startActivity(intent);

                                    } else {
                                        Intent intent = new Intent(JazzCashPaymentActivity.this, DocumentChecklisActivity.class);
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

    private void clickListeners() {

        binding.etAccountNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (binding.etAccountNo.getText().length() == 11) {
                    Global.utils.hideKeyboard(JazzCashPaymentActivity.this);
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
                    Global.utils.hideKeyboard(JazzCashPaymentActivity.this);
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
                Global.utils.showCustomLoadingDialog(JazzCashPaymentActivity.this);
                mobileNumber = binding.etAccountNo.getText().toString();
                cnic = binding.etCnic.getText().toString();

                viewModel.CallingJazzCashApi(mobileNumber, cnic);
            }
        });
    }

    private void initViews() {
        Global.applicationContext = JazzCashPaymentActivity.this;

        viewModel = ViewModelProviders.of(JazzCashPaymentActivity.this).get(JazzCashViewModel.class);

        binding.tvAmount.setText(Global.price);
    }
}