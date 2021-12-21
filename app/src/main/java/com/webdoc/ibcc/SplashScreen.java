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
        verifyStoragePermissions(SplashScreen.this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        viwModel = ViewModelProviders.of(this).get(RegistrationSharedViewModel.class);

        //Global.utils.showCustomLoadingDialog(SplashScreen.this);
        //volleyRequestController = new VolleyRequestController(this);
        //volleyRequestController.Pdf();
        //callPDFApi(this);

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

    /*@Override
    public void getRequestResponse(JSONObject response, String requestType) throws JSONException {
        Gson gson = new Gson();

        if (requestType.equals(Constants.PDF)) {
            PdfResult pdfResult = gson.fromJson(response.toString(), PdfResult.class);

            if (pdfResult.getMessage().equalsIgnoreCase(Constants.IBCC_SUCCESS_MESSAGE)) {
                Global.pdfResponse = pdfResult;
                volleyRequestController.GetDetailsEquivalence();
            } else {
                Global.utils.hideCustomLoadingDialog();
            }

        } else if (requestType.equals(Constants.GETDETAILSEQUIVALENCE)) {
            GetDetailsEquivalence getDetailsEquivalence = gson.fromJson(response.toString(), GetDetailsEquivalence.class);

            if (getDetailsEquivalence.getMessage().equalsIgnoreCase(Constants.IBCC_SUCCESS_MESSAGE)) {
                Global.getDetailsEquivalence = getDetailsEquivalence;

                if (preferences.getKeyIsLogin()) {
                    if (preferences.getKeyUserLoginType().equalsIgnoreCase("Email")) {
                        Global.loginUser.setEmail(preferences.getKeyUserEmail());
                    } else {
                        Global.loginUser.setCnic(preferences.getKeyUserCnic());
                    }

                    Global.loginUser.setPassword(preferences.getKeyUserPin());
                    Global.loginUser.setType(preferences.getKeyUserLoginType());
                    volleyRequestController.UserLogin(Global.loginUser);

                } else {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                Global.utils.hideCustomLoadingDialog();
                Toast.makeText(this, getDetailsEquivalence.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else if (requestType.equals(Constants.USERLOGIN)) {
            UserLoginResult userLoginResult = gson.fromJson(response.toString(), UserLoginResult.class);

            if (userLoginResult.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                Global.userLoginResponse = userLoginResult;

                Global.utils.hideCustomLoadingDialog();
                Intent intent = new Intent(SplashScreen.this, Dashboard.class);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(getApplicationContext(), userLoginResult.getResult().getResponseMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }*/

    public void callPDFApi(Activity activity) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<PdfResult> call = apiInterface.callPDF();

            call.enqueue(new Callback<PdfResult>() {
                @Override
                public void onResponse(Call<PdfResult> call, Response<PdfResult> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getMessage().equalsIgnoreCase(Constants.IBCC_SUCCESS_MESSAGE)) {
                            Global.pdfResponse = response.body();
                            callDetailsEquivalenceApi(SplashScreen.this);
                        } else {
                            Global.utils.showErrorSnakeBar(activity, response.body().getMessage());
                        }
                    } else {
                        Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PdfResult> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callDetailsEquivalenceApi(Activity activity) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject mobj = new JsonObject();

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<GetDetailsEquivalence> call = apiInterface.callDetailsEquivalence(mobj);

            call.enqueue(new Callback<GetDetailsEquivalence>() {
                @Override
                public void onResponse(Call<GetDetailsEquivalence> call, Response<GetDetailsEquivalence> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getMessage().equalsIgnoreCase(Constants.IBCC_SUCCESS_MESSAGE)) {
                            Global.getDetailsEquivalence = response.body();

                            if (Preferences.getInstance(SplashScreen.this).getKeyIsLogin()) {
                                if (Preferences.getInstance(SplashScreen.this).getKeyUserLoginType()
                                        .equalsIgnoreCase("Email")) {
                                    Global.loginUser.setEmail(Preferences.getInstance(SplashScreen.this).getKeyUserEmail());
                                } else {
                                    Global.loginUser.setCnic(Preferences.getInstance(SplashScreen.this).getKeyUserCnic());
                                }
                                Global.loginUser.setPassword(Preferences.getInstance(SplashScreen.this).getKeyUserPin());
                                Global.loginUser.setType(Preferences.getInstance(SplashScreen.this).getKeyUserLoginType());
                                callUserLoginApi(SplashScreen.this, Global.loginUser);

                            } else {
                                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        } else {
                            Global.utils.showErrorSnakeBar(activity, response.body().getMessage());
                        }
                    } else {
                        Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GetDetailsEquivalence> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callUserLoginApi(Activity activity, LoginUser dataModel) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("password", dataModel.getPassword());
            params.addProperty("type", dataModel.getType());

            if (dataModel.getType().equalsIgnoreCase("Email")) {
                params.addProperty("email", dataModel.getEmail());
            } else {
                params.addProperty("email", dataModel.getCnic());
            }

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<UserLoginResult> call = apiInterface.callUserLogin(params);

            call.enqueue(new Callback<UserLoginResult>() {
                @Override
                public void onResponse(Call<UserLoginResult> call, Response<UserLoginResult> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getMessage().equalsIgnoreCase(Constants.IBCC_SUCCESS_MESSAGE)) {
                            Global.userLoginResponse = response.body();

                            Intent intent = new Intent(SplashScreen.this, Dashboard.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Global.utils.showErrorSnakeBar(activity, response.body().getMessage());
                        }
                    } else {
                        Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserLoginResult> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}