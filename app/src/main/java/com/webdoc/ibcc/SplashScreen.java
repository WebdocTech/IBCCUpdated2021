package com.webdoc.ibcc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProviders;

import com.webdoc.ibcc.DashBoard.Dashboard;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Essentails.Preferences;
import com.webdoc.ibcc.ResponseModels.PdfResult.PdfResult;
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

        callPDFApi();
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

    public void callPDFApi() {
        if (Constants.isInternetConnected(SplashScreen.this)) {
            Global.utils.showCustomLoadingDialog(SplashScreen.this);

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<PdfResult> call = apiInterface.callPDF();

            call.enqueue(new Callback<PdfResult>() {
                @Override
                public void onResponse(Call<PdfResult> call, Response<PdfResult> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {

                        Global.pdfResponse = response.body();
                        callingLogin();

                    } else {
                        Toast.makeText(SplashScreen.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PdfResult> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(SplashScreen.this, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(SplashScreen.this, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

}