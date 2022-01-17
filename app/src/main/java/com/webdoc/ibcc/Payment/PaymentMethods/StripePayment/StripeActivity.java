package com.webdoc.ibcc.Payment.PaymentMethods.StripePayment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.DocumentCheckList.DocumentChecklisActivity;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.CallCourier_EQ.CallCourier_EQ;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Essentails.Preferences;
import com.webdoc.ibcc.Payment.PaymentMethods.JazzCash.JazzCashPaymentActivity;
import com.webdoc.ibcc.Payment.PaymentMethods.StripePayment.ViewModel.StripeViewModel;
import com.webdoc.ibcc.ResponseModels.SavePaymentInfo.SavePaymentInfo;
import com.webdoc.ibcc.databinding.ActivityStripeBinding;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class StripeActivity extends AppCompatActivity {
    private ActivityStripeBinding binding;
    private StripeViewModel viewModel;
    private Preferences preferences;
    private double totalAmountinRupees, sevenDollar, fivePercentAmount, finalAmountinDollor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
        observers();
    }

    private void observers() {
        viewModel.LD_savePaymentInfoEquialance().observe(this, s -> {
            if (s != null) {
                if (s.getResult().getResponseCode().equals("0000")) {
                    Global.savePaymentInfo = s;
                    Global.utils.hideCustomLoadingDialog();
                    openWebView();
                } else {
                    Global.utils.hideCustomLoadingDialog();
                    Toast.makeText(this, "resp fail ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.LD_getDollorRate().observe(this, response -> {
            if (response != null) {
                double priceInDollors = calculateInternationalCharges(response.getRates().getPkr());
                viewModel.callSavePaymentEQApi(preferences.getKeyUserPhone(), priceInDollors, totalAmountinRupees,
                        sevenDollar, fivePercentAmount);
            }
        });
    }

    private void openWebView() {
        String url = "https://ibccportal.webddocsystems.com/InternationalPayment/internationalpayment.php?totalprice=" + finalAmountinDollor + "&caseid=" + Global.caseId;
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setLoadWithOverviewMode(true);
        binding.webView.getSettings().setUseWideViewPort(true);
        binding.webView.getSettings().setBuiltInZoomControls(true);
        binding.webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        binding.webView.setWebViewClient(new MyWebViewClient());
        binding.webView.loadUrl(url);

        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                Log.d("WebView", "your current url when webpage loading.." + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("WebView", "your current url when webpage loading.. finish" + url);
                if (url.contains("https://ibccportal.webddocsystems.com/index.php/InternationalPayment/stripePost")) {
                    //todo: payment success\\
                    Global.isPaymmentSuccesful = true;
                    binding.webView.removeAllViews();
                    binding.webView.destroy();
                    Global.utils.showSuccessSnakeBar(StripeActivity.this, "Success");

                    new SweetAlertDialog(StripeActivity.this, SweetAlertDialog.SUCCESS_TYPE)

                            .setContentText("Payment successful!")
                            .setConfirmText("okay")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    if (Global.isFromEquivalence) {
                                        Intent intent = new Intent(StripeActivity.this, CallCourier_EQ.class);
                                        intent.putExtra("appointment_method", "International Payment");
                                        intent.putExtra("trx_id", "");
                                        intent.putExtra("bank_name", "Stripe Payment");
                                        intent.putExtra("payment_status", "Pending");
                                        finish();
                                        startActivity(intent);

                                    } else {
                                        Intent intent = new Intent(StripeActivity.this, DocumentChecklisActivity.class);
                                        intent.putExtra("appointment_method", "International Payment");
                                        intent.putExtra("trx_id", "");
                                        intent.putExtra("bank_name", "Stripe Payment");
                                        intent.putExtra("payment_status", "Pending");
                                        finish();
                                        startActivity(intent);
                                    }
                                }
                            })
                            .show();
                } /*else {
                    //todo: payment Fail
                    Toast.makeText(StripeActivity.this, "PAYMENT FAIL!", Toast.LENGTH_SHORT).show();
                    binding.webView.removeAllViews();
                    binding.webView.destroy();
                    finish();
                    Global.utils.hideCustomLoadingDialog();
                }*/

                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onLoadResource(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                System.out.println("when you click on any interlink on webview that time you got url :-" + url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

    }

    private void initViews() {
        binding = ActivityStripeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferences = new Preferences(StripeActivity.this);
        viewModel = ViewModelProviders.of(this).get(StripeViewModel.class);

        viewModel.callDollorRateApi(this);
        //viewModel.callSavePaymentEQApi(preferences.getKeyUserPhone());
    }

    //todo : methods
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    public double calculateInternationalCharges(double pkrRate) {
        //200 courier fee
        int documentsTotalFee = Global.documentsTotalFee;
        sevenDollar = pkrRate * 7; //webdoc amount:
        double amount = documentsTotalFee + 200 + sevenDollar;
        fivePercentAmount = amount * 0.05; //bank charges:\
        totalAmountinRupees = amount + fivePercentAmount;
        finalAmountinDollor = totalAmountinRupees / pkrRate;

        return finalAmountinDollor;
    }
}