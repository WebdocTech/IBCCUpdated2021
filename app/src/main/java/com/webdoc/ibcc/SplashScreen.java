package com.webdoc.ibcc;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.JsonObject;
import com.webdoc.ibcc.DashBoard.Dashboard;
import com.webdoc.ibcc.DataModel.LoginUser;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Essentails.Preferences;
import com.webdoc.ibcc.Payment.PaymentMethods.BankAlfalah.BankAlfalhCreditDebit.BankAlfalahCreditDebitActivity;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.GetDetailsEquivalence;
import com.webdoc.ibcc.ResponseModels.PdfResult.PdfResult;
import com.webdoc.ibcc.ResponseModels.UserLoginResult.UserLoginResult;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.UserRegistration.RegistrationSharedViewModel.RegistrationSharedViewModel;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.ActivitySplashScreenBinding;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity /*implements VolleyListener*/ {
    VolleyRequestController volleyRequestController;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private RegistrationSharedViewModel viwModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashScreenBinding layoutBinding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        viwModel = ViewModelProviders.of(this).get(RegistrationSharedViewModel.class);

        callingLogin();
        observers();

    }

    private void observers() {
        viwModel.getUserLoginLiveData().observe(this, response -> {
            if (response != null) {
                if (response.getMessage().equalsIgnoreCase(Constants.IBCC_SUCCESS_MESSAGE)) {
                    Global.userLoginResponse = response;
                    Intent intent = new Intent(SplashScreen.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                } else {
                    Global.utils.showErrorSnakeBar(this, response.getMessage());
                }
            }
        });
    }

    private void callingLogin() {
        if (Preferences.getInstance(SplashScreen.this).getKeyIsLogin()) {
            if (Preferences.getInstance(SplashScreen.this).getKeyUserLoginType()
                    .equalsIgnoreCase("Email")) {
                Global.loginUser.setEmail(Preferences.getInstance(SplashScreen.this).getKeyUserEmail());
            } else {
                Global.loginUser.setCnic(Preferences.getInstance(SplashScreen.this).getKeyUserCnic());
            }
            Global.loginUser.setPassword(Preferences.getInstance(SplashScreen.this).getKeyUserPin());
            Global.loginUser.setType(Preferences.getInstance(SplashScreen.this).getKeyUserLoginType());
            //callUserLoginApi(SplashScreen.this, Global.loginUser);
            viwModel.callUserLoginApi(this, Global.loginUser);

        } else {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}