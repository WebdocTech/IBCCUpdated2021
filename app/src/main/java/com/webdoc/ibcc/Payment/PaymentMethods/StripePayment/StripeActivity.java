package com.webdoc.ibcc.Payment.PaymentMethods.StripePayment;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Essentails.Preferences;
import com.webdoc.ibcc.Payment.PaymentMethods.StripePayment.ViewModel.StripeViewModel;
import com.webdoc.ibcc.ResponseModels.SavePaymentInfo.SavePaymentInfo;
import com.webdoc.ibcc.databinding.ActivityStripeBinding;

public class StripeActivity extends AppCompatActivity {

    ActivityStripeBinding binding;
    StripeViewModel viewModel;
    Preferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        observers();
    }

    private void observers() {
        viewModel.LD_savePaymentInfoEquialance().observe(this, s -> {

            if (s instanceof SavePaymentInfo) {

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
    }

    private void openWebView() {
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        binding.webView.loadUrl("   https://ibccportal.webddocsystems.com/InternationalPayment/" +
                "internationalpayment.php?totalprice=120&caseid=12");
    }

    private void initViews() {
        binding = ActivityStripeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferences = new Preferences(StripeActivity.this);
        viewModel = ViewModelProviders.of(this).get(StripeViewModel.class);
        viewModel.callSavePaymentEQApi(preferences.getKeyUserPhone());
    }
}