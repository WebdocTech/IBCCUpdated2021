package com.webdoc.ibcc.Payment.PaymentMethods.EasyPaisaCreditDebit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.DocumentCheckList.DocumentChecklisActivity;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.CallCourier_EQ.CallCourier_EQ;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.SavePaymentInfo.SavePaymentInfo;
import com.webdoc.ibcc.databinding.ActivityWebviewBinding;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class WebviewActivity extends AppCompatActivity {
    private ActivityWebviewBinding layoutBinding;
    private EpWebviewViewModel viewModel;
    private Intent intent;
    private String url, phoneNo, email, finalUrl, orderId;
    private String case_id, user_id, amount_paid, bank, account_number,
            mobile_number, transection_type, transaction_reference_number,
            transaction_datetime, IBCC_amount, webdoc_amount, status,
            courierAmount, bankCharges, userIdEq, sixDigitNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        layoutBinding = ActivityWebviewBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        initViews();
        observers();
    }

    private void observers() {
        viewModel.LD_open_webview().observe(this, s -> {
            //userId,Courier amount ,bank charges,webdoc amount ,
            if (s instanceof String) {

                if (s.equals("Payment Succesfull")) {
                    mobile_number = Global.userLoginResponse.getResult().getCustomerProfile().getMobile();
                    user_id = Global.userLoginResponse.getResult().getCustomerProfile().getId();
                    amount_paid = String.valueOf(Global.price);
                    bank = "EasyPaisaCreditDebit";
                    account_number = mobile_number;
                    transection_type = "Debit/Credit";
                    transaction_reference_number = Global.EasyPaisaCreditDebitOrderId;
                    transaction_datetime = getCurrentlyDateTimenew();
                    IBCC_amount = String.valueOf(Global.ibccAmount);
                    webdoc_amount = String.valueOf(Global.webdocAmount);
                    status = "Success";
                    case_id = Global.caseId;
                    courierAmount = "200";
                    bankCharges = String.valueOf(Global.bankChargeEQ);
                    userIdEq = String.valueOf(Global.equivalenceInitiateCaseResponse.getResult().getIntiateCaseResponseDetails().getCustomerId());
                    String platform = "Android";
                    String order_id = Global.order_id;
                    //   String bankCharges = Global.bankChargesForReceipt;


                    if (Global.isFromEquivalence) {
                        viewModel.callingSavePaymentForEquilance(case_id, amount_paid, bank, account_number, mobile_number, transection_type, transaction_reference_number, transaction_datetime, userIdEq, status, webdoc_amount, IBCC_amount, courierAmount, bankCharges, order_id, platform);

                    } else {

                        bankCharges = Global.bankChargesForReceipt;
                        viewModel.CallingSavePaymentApiOnEasyPaisaSuccess(IBCC_amount, webdoc_amount, case_id, amount_paid, bank, account_number, mobile_number, transection_type, transaction_reference_number, transaction_datetime, user_id, status, transaction_reference_number, bankCharges, courierAmount, platform);

                    }

            /*        Toast.makeText(this, "Payment success!", Toast.LENGTH_SHORT).show();
                    Global.utils.showSuccessSnakeBar(EpCreditDebitWebViewActivity.this, "PAYMENT SUCCESS!");
                    binding.webView.removeAllViews();
                    binding.webView.destroy();
                    finish();
                    Global.utils.hideCustomLoadingDialog();*/

                } else if (s.equals("Payment Fail")) {

                    Global.utils.showErrorSnakeBar(WebviewActivity.this, "PAYMENT FAIL!");
                    layoutBinding.webView.removeAllViews();
                    layoutBinding.webView.destroy();
                    finish();
                    Global.utils.hideCustomLoadingDialog();
                }
            }
        });


        viewModel.LD_savePaymentInfo().observe(this, s -> {

            if (s instanceof SavePaymentInfo) {

                if (s.getResult().getResponseCode().equals("0000")) {
                    Toast.makeText(this, "Payment success!", Toast.LENGTH_SHORT).show();
                    Global.utils.showSuccessSnakeBar(WebviewActivity.this, "PAYMENT SUCCESS!");
                    layoutBinding.webView.removeAllViews();
                    layoutBinding.webView.destroy();
                    Global.utils.hideCustomLoadingDialog();
                    Global.savePaymentInfo = s;
                    Toast.makeText(this, "Payment Succesfull", Toast.LENGTH_SHORT).show();

                   /* Intent intent = new Intent(WebviewActivity.this, DocumentChecklisActivity.class);
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
                        Intent intent = new Intent(WebviewActivity.this, CallCourier_EQ.class);
                        intent.putExtra("appointment_method", transection_type);
                        intent.putExtra("trx_id", transaction_reference_number);
                        intent.putExtra("bank_name", bank);
                        intent.putExtra("payment_status", "Pending");
                        finish();
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent(WebviewActivity.this, DocumentChecklisActivity.class);
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

                    Global.utils.showSuccessSnakeBar(WebviewActivity.this, "Success");

                    new SweetAlertDialog(WebviewActivity.this, SweetAlertDialog.SUCCESS_TYPE)

                            .setContentText("Your Transaction Number " + transaction_reference_number + " is generated. please pay on any near Easypaisa shop against this transection number")
                            .setConfirmText("Yes")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();

                                    /*Intent intent = new Intent(WebviewActivity.this, CallCourier_EQ.class);
                                    intent.putExtra("appointment_method", transection_type);
                                    intent.putExtra("trx_id", transaction_reference_number);
                                    intent.putExtra("bank_name", bank);
                                    intent.putExtra("payment_status", "Pending");
                                    startActivity(intent);*/

                                    if (Global.isFromEquivalence) {
                                        Intent intent = new Intent(WebviewActivity.this, CallCourier_EQ.class);
                                        intent.putExtra("appointment_method", transection_type);
                                        intent.putExtra("trx_id", transaction_reference_number);
                                        intent.putExtra("bank_name", bank);
                                        intent.putExtra("payment_status", "Pending");
                                        finish();
                                        startActivity(intent);

                                    } else {
                                        Intent intent = new Intent(WebviewActivity.this, DocumentChecklisActivity.class);
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

    public void initViews() {
        //connecting View Model with activityyu
        viewModel = ViewModelProviders.of(WebviewActivity.this).get(EpWebviewViewModel.class);

        Global.applicationContext = WebviewActivity.this;

        intent = getIntent();
        phoneNo = intent.getStringExtra("mobileNumber");
        email = intent.getStringExtra("email");
        orderId = getCurrentlyDateTime();
        Global.order_id = orderId;
        Global.EasyPaisaCreditDebitOrderId = orderId;
        String amount = String.valueOf(Global.price);
        String EmailEncoded = null;
        try {
            EmailEncoded = URLEncoder.encode(email, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        finalUrl = "https://portal.webdoc.com.pk/transection/Easypaisa/cc.php?email=" + EmailEncoded + "&cellNumber=" + phoneNo + "&amount=" + amount + "&orderId=" + orderId;

        viewModel.openEasyPaisacreditDebitWebView(layoutBinding.webView, finalUrl);
    }

    public String getCurrentlyDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date());

    }

    public String getCurrentlyDateTimenew() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date());

    }

}