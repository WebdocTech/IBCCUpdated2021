package com.webdoc.ibcc.Payment.PaymentMethods.EasyPaisa;

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
import com.webdoc.ibcc.Payment.PaymentMethods.EasyPaisa.ResoponseModel.easypaisaPAymentResponse.EasypaisaPAymentResponse;
import com.webdoc.ibcc.Payment.PaymentMethods.EasyPaisa.ViewModel.EasyPaisaViewModel;
import com.webdoc.ibcc.ResponseModels.SavePaymentInfo.SavePaymentInfo;
import com.webdoc.ibcc.databinding.ActivityEasyPaisaBinding;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EasyPaisaActivity extends AppCompatActivity {
    private ActivityEasyPaisaBinding binding;
    private EasyPaisaViewModel viewModel;
    private String mobileNumber, email;
    private String case_id, user_id, amount_paid, bank,
            account_number, mobile_number, transection_type,
            transaction_reference_number, transaction_datetime,
            center, IBCC_amount, webdoc_amount, status, userIdEq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEasyPaisaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        clickListeneres();
        observers();
    }

    public void initViews() {
        //connecting View Model with activity
        viewModel = ViewModelProviders.of(this).get(EasyPaisaViewModel.class);

        Global.applicationContext = EasyPaisaActivity.this;

        binding.tvPkgPrice.setText(String.valueOf(Global.price));
    }

    public void observers() {
        //todo: when next button is click befor easy paisa payment this observer will call====>>>>
        viewModel.LD_btn_next_click().observe(this, s -> {

            if (s instanceof EasypaisaPAymentResponse) {

                if (s.getResponseCode().equals("0000")) {
                    //case_id = Global.caseId;

                    if (!TextUtils.isEmpty(Global.caseId)) {
                        case_id = Global.caseId;
                    } else {
                        case_id = Global.incompleteCaseId;
                    }

                    user_id = Global.userLoginResponse.getResult().getCustomerProfile().getId();
                    amount_paid = String.valueOf(Global.price);
                    bank = "EasyPaisaWallet";
                    account_number = mobileNumber;
                    mobile_number = Global.userLoginResponse.getResult().getCustomerProfile().getMobile();
                    transection_type = "Wallet";
                    transaction_reference_number = s.getTransactionId();
                    transaction_datetime = s.getTransactionDateTime();
                    center = Global.center;
                    IBCC_amount = String.valueOf(Global.ibccAmount);
                    webdoc_amount = String.valueOf(Global.webdocAmount);
                    status = "Success";
                    String bank_charges = Global.bankChargesForReceiptEQ;
                    String courier_amount = "200";
                    String order_id = Global.order_id;
                    userIdEq = Global.userLoginResponse.getResult().getCustomerProfile().getId();
                    String platform = "Android";

                    if (Global.isFromEquivalence) {
                        //call savePayment Api for Equilance
                        viewModel.callingSavePaymentForEquilance(case_id, amount_paid, bank, account_number,
                                mobile_number, transection_type, transaction_reference_number,
                                transaction_datetime, userIdEq, status, webdoc_amount, IBCC_amount,
                                bank_charges, courier_amount, order_id, platform);
                    } else {
                        //call savePayment Api for EasyPaisa
                        viewModel.CallingSavePaymentApiOnEasyPaisaSuccess(IBCC_amount, webdoc_amount,
                                case_id, amount_paid, bank, account_number, mobile_number, transection_type,
                                transaction_reference_number, transaction_datetime, user_id, bank_charges,
                                courier_amount, order_id, platform);
                    }


                } else if (s.getResponseCode().equals("0001")) {
                    Global.utils.showErrorSnakeBar(EasyPaisaActivity.this, "System Error");
                    Global.utils.hideCustomLoadingDialog();
                } else if (s.getResponseCode().equals("0013")) {
                    Global.utils.showErrorSnakeBar(EasyPaisaActivity.this, "Low account balance");
                    Global.utils.hideCustomLoadingDialog();
                } else {
                    Global.utils.showErrorSnakeBar(EasyPaisaActivity.this, "something went wrong");
                    Global.utils.hideCustomLoadingDialog();
                }
            }
        });

        viewModel.LD_showRecptResponse().observe(this, s -> {

            if (s instanceof String) {
                if (s.equals("btn_ok_pressed")) {
                    finish();
                }
            }
        });

        viewModel.LD_savePaymentInfo().observe(this, s -> {

            if (s instanceof SavePaymentInfo) {

                if (s.getResult().getResponseCode().equals("0000")) {
                    Global.savePaymentInfo = s;
                    Global.utils.hideCustomLoadingDialog();


                   /* Intent intent = new Intent(EasyPaisaActivity.this, CallCourier.class);
                    intent.putExtra("appointment_method", transection_type);
                    intent.putExtra("trx_id", transaction_reference_number);
                    intent.putExtra("bank_name", bank);
                    intent.putExtra("payment_status", "active");
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
                        Intent intent = new Intent(EasyPaisaActivity.this, CallCourier_EQ.class);
                        intent.putExtra("appointment_method", transection_type);
                        intent.putExtra("trx_id", transaction_reference_number);
                        intent.putExtra("bank_name", bank);
                        intent.putExtra("payment_status", "Pending");
                        finish();
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent(EasyPaisaActivity.this, DocumentChecklisActivity.class);
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

                    Global.utils.showSuccessSnakeBar(EasyPaisaActivity.this, "Success");

                    new SweetAlertDialog(EasyPaisaActivity.this, SweetAlertDialog.SUCCESS_TYPE)

                            .setContentText("Payment Successful \n Your transaction number is " + transaction_reference_number)
                            .setConfirmText("Yes")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();

                                   /* Intent intent = new Intent(EasyPaisaActivity.this, CallCourier.class);
                                    intent.putExtra("appointment_method", transection_type);
                                    intent.putExtra("trx_id", transaction_reference_number);
                                    intent.putExtra("bank_name", bank);
                                    intent.putExtra("payment_status", "Pending");
                                    startActivity(intent);*/

                                    if (Global.isFromEquivalence) {
                                        Intent intent = new Intent(EasyPaisaActivity.this, CallCourier_EQ.class);
                                        intent.putExtra("appointment_method", transection_type);
                                        intent.putExtra("trx_id", transaction_reference_number);
                                        intent.putExtra("bank_name", bank);
                                        intent.putExtra("payment_status", "Pending");
                                        finish();
                                        startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(EasyPaisaActivity.this, DocumentChecklisActivity.class);
                                        intent.putExtra("appointment_method", transection_type);
                                        intent.putExtra("trx_id", transaction_reference_number);
                                        intent.putExtra("bank_name", bank);
                                        intent.putExtra("payment_status", "Pending");
                                        finish();
                                        startActivity(intent);
                                    }
                                }
                            }).show();
                } else {
                    Global.utils.hideCustomLoadingDialog();
                    Toast.makeText(this, "resp fail ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void clickListeneres() {

        binding.etAccountNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (binding.etAccountNo.getText().length() == 11) {
                    Global.utils.hideKeyboard(EasyPaisaActivity.this);
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
                    Global.utils.hideKeyboard(EasyPaisaActivity.this);
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
                mobileNumber = binding.etAccountNo.getText().toString();
                email = binding.etCnic.getText().toString();

                viewModel.btnNextClick(mobileNumber, email);
            }

        });

    }
}