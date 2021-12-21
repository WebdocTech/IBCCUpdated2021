package com.webdoc.ibcc.Payment.PaymentMethods.BankAlfalah.BankAlfalahWallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.webdoc.ibcc.CompleteProfile.SuccessRegistration;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.DocumentCheckList.DocumentChecklisActivity;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.CallCourier_EQ.CallCourier_EQ;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Essentails.Preferences;
import com.webdoc.ibcc.Payment.PaymentMethods.BankAlfalah.BankAlfalahWallet.ViewModel.BankAlfalahWalletViewModel;
import com.webdoc.ibcc.ResponseModels.SavePaymentInfo.SavePaymentInfo;
import com.webdoc.ibcc.databinding.ActivityBankAlfalahWalletBinding;

import java.util.Calendar;
import java.util.Random;

public class BankAlfalahWalletActivity extends AppCompatActivity {
    private ActivityBankAlfalahWalletBinding binding;
    private BankAlfalahWalletViewModel viewModel;
    private int orderid;
    private String case_id, user_id, amount_paid, bank,
            account_number, mobile_number, transection_type,
            transaction_reference_number, transaction_datetime,
            center, IBCC_amount, webdoc_amount, status, userIdEq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBankAlfalahWalletBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        observers();
    }

    private void initViews() {
        Global.applicationContext = this;

        Global.utils.showCustomLoadingDialog(BankAlfalahWalletActivity.this);
        //viewModel class:
        viewModel = ViewModelProviders.of(BankAlfalahWalletActivity.this).get(BankAlfalahWalletViewModel.class);

        Random rand = new Random();
        orderid = rand.nextInt(10000000);

        String url = "https://apnadoctor.webddocsystems.com/payment/credit.php?orderid=" + orderid + "&amount=" + Global.price + "&id=1";
        viewModel.openBankAlfalahWalletWebView(binding.webView, url);

    }

    private void observers() {

        viewModel.LD_open_BankAlfalah_Wallet_webview().observe(this, s -> {

            if (s instanceof String) {

                if (s.equals("Payment Succesfull")) {
                    //case_id = Global.caseId;

                    if (!TextUtils.isEmpty(Global.caseId)) {
                        case_id = Global.caseId;
                    } else {
                        case_id = Global.incompleteCaseId;
                    }

                    user_id = Global.userLoginResponse.getResult().getCustomerProfile().getId();
                    amount_paid = String.valueOf(Global.price);
                    bank = "Bank Alfalah Account Activity";
                    account_number = Preferences.getInstance(BankAlfalahWalletActivity.this).getKeyUserPhone();
                    mobile_number = Global.userLoginResponse.getResult().getCustomerProfile().getMobile();
                    transection_type = "Account";
                    transaction_reference_number = String.valueOf(orderid);
                    transaction_datetime = Calendar.getInstance().getTime().toString();
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

                        viewModel.CallingSavePaymentApiOnBankAlfalahWalletSuccess(IBCC_amount, webdoc_amount, case_id, amount_paid, bank, account_number, mobile_number, transection_type, transaction_reference_number, transaction_datetime, user_id, bankCharges, courier_amount, platform, transaction_reference_number);

                    }


                } else if (s.equals("PAYMENT FAIL!")) {

                    Global.utils.showErrorSnakeBar(BankAlfalahWalletActivity.this, "PAYMENT FAIL!");
                    binding.webView.removeAllViews();
                    binding.webView.destroy();
                    finish();
                    Global.utils.hideCustomLoadingDialog();

                } else if (s.equals("Please wait")) {


                } else if (s.equals("webview Loaded")) {
                    Global.utils.hideCustomLoadingDialog();
                } else {

                }
            }
        });

        viewModel.LD_savePaymentInfo().observe(this, s -> {
            if (s instanceof SavePaymentInfo) {

                if (s.getResult().getResponseCode().equals("0000")) {
                    Global.savePaymentInfo = s;
                    Global.utils.showSuccessSnakeBar(BankAlfalahWalletActivity.this, "PAYMENT SUCESSFUL!");
                    Global.isPaymmentSuccesful = true;
                    binding.webView.removeAllViews();
                    binding.webView.destroy();

                   /* Intent intent = new Intent(BankAlfalahWalletActivity.this, CallCourier.class);
                    intent.putExtra("appointment_method", transection_type);
                    intent.putExtra("trx_id", transaction_reference_number);
                    intent.putExtra("bank_name", bank);
                    intent.putExtra("payment_status", "active");
                    startActivity(intent);*/

                    if (Global.isFromEquivalence) {
                        Intent intent = new Intent(BankAlfalahWalletActivity.this, CallCourier_EQ.class);
                        intent.putExtra("appointment_method", transection_type);
                        intent.putExtra("trx_id", transaction_reference_number);
                        intent.putExtra("bank_name", bank);
                        intent.putExtra("payment_status", "active");
                        finish();
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent(BankAlfalahWalletActivity.this, DocumentChecklisActivity.class);
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


    }


}