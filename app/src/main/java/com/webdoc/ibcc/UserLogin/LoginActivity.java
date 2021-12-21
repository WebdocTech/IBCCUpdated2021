package com.webdoc.ibcc.UserLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.webdoc.ibcc.DashBoard.Dashboard;
import com.webdoc.ibcc.DataModel.LoginUser;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Essentails.Preferences;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.PdfResult.PdfResult;
import com.webdoc.ibcc.ResponseModels.UserLoginResult.UserLoginResult;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.SplashScreen;
import com.webdoc.ibcc.UserRegistration.RegistrationSharedViewModel.RegistrationSharedViewModel;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity /*implements VolleyListener*/ {
    static boolean isEmail = true;
    private VolleyRequestController volleyRequestController;
    private String email, password, type, cnic;
    private ActivityLoginBinding layoutBinding;
    private RegistrationSharedViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        viewModel = ViewModelProviders.of(this).get(RegistrationSharedViewModel.class);

        //volleyRequestController = new VolleyRequestController(this);
        Global.loginType = "Email";
        observers();
       clickListeners();
    }

    private void observers() {
        viewModel.getUserLoginLiveData().observe(this, response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equalsIgnoreCase(Constants.IBCC_SUCCESS_CODE)) {
                    Global.userLoginResponse = response;

                    if (type.equalsIgnoreCase("Email")) {
                        Preferences.getInstance(LoginActivity.this).setKeyUserEmail(email);
                    } else {
                        Preferences.getInstance(LoginActivity.this).setKeyUserCnic(cnic);
                    }

                    Preferences.getInstance(LoginActivity.this).setKeyUserLoginType(type);
                    Preferences.getInstance(LoginActivity.this).setKeyUserPin(password);
                    Preferences.getInstance(LoginActivity.this).setKeyUserFName(response.getResult().getCustomerProfile().getFirstName());
                    Preferences.getInstance(LoginActivity.this).setKeyUserLName(response.getResult().getCustomerProfile().getLastName());
                    Preferences.getInstance(LoginActivity.this).setKeyIsLogin(true);

                    //callPDFApi(LoginActivity.this);
                    Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);

                } else {
                    Global.utils.showErrorSnakeBar(this, response.getMessage());
                }
            }
        });
    }

    private void clickListeners() {
        layoutBinding.changeLoginMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    layoutBinding.etCnic.setVisibility(View.INVISIBLE);
                    layoutBinding.etCnic.setText("");
                    layoutBinding.etEmail.setVisibility(View.VISIBLE);
                    layoutBinding.etEmail.requestFocus();
                    layoutBinding.tvSwitch.setText("Email");
                    isEmail = true;

                    Global.loginType = "Email";
                } else {
                    // The toggle is disabled
                    isEmail = false;
                    layoutBinding.etEmail.setVisibility(View.INVISIBLE);
                    layoutBinding.etEmail.setText("");
                    layoutBinding.etCnic.setVisibility(View.VISIBLE);
                    layoutBinding.etCnic.requestFocus();
                    layoutBinding.tvSwitch.setText("CNIC");

                    Global.loginType = "Cnic";
                }
            }
        });

        layoutBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmail) {
                    if (TextUtils.isEmpty(layoutBinding.etEmail.getText().toString())) {
                        layoutBinding.etEmail.setError("Please enter your email");
                        layoutBinding.etEmail.requestFocus();
                        return;
                    }

                    String getEmailId = layoutBinding.etEmail.getText().toString();
                    if (!isEmailValid(getEmailId)) {    // Check if email id is valid or not
                        layoutBinding.etEmail.setError("Your Email Id is Invalid");
                        layoutBinding.etEmail.requestFocus();
                        return;
                    }

                } else {
                    if (TextUtils.isEmpty(layoutBinding.etCnic.getText().toString())) {
                        layoutBinding.etCnic.setError("Please enter your cnic");
                        layoutBinding.etCnic.requestFocus();
                        return;
                    }
                    if (layoutBinding.etCnic.getText().toString().length() < 11) {
                        layoutBinding.etCnic.setError("enter valid cnic number");
                        layoutBinding.etCnic.requestFocus();
                        return;
                    }
                }

                if (TextUtils.isEmpty(layoutBinding.etPassword.getText().toString())) {
                    layoutBinding.etPassword.setError("enter your password");
                    layoutBinding.etPassword.requestFocus();
                    return;
                }

                email = layoutBinding.etEmail.getText().toString();
                password = layoutBinding.etPassword.getText().toString();
                cnic = layoutBinding.etCnic.getText().toString();
                type = Global.loginType;

                if (isEmail) {
                    Global.loginUser.setEmail(email);
                } else {
                    Global.loginUser.setCnic(cnic);
                }

                Global.loginUser.setType(type);
                Global.loginUser.setPassword(password);

                //Global.utils.showCustomLoadingDialog(LoginActivity.this);
                //volleyRequestController.UserLogin(Global.loginUser);
                //callUserLoginApi(LoginActivity.this, Global.loginUser);
                viewModel.callUserLoginApi(LoginActivity.this, Global.loginUser);
            }
        });
    }


    /*@Override
    public void getRequestResponse(JSONObject response, String requestType) throws JSONException {
        Gson gson = new Gson();

        if (requestType.equals(Constants.USERLOGIN)) {
            UserLoginResult userLoginResult = gson.fromJson(response.toString(), UserLoginResult.class);

            if (userLoginResult.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                Global.userLoginResponse = userLoginResult;

                if (type.equalsIgnoreCase("Email")) {
                    preferences.setKeyUserEmail(email);
                } else {
                    preferences.setKeyUserCnic(cnic);
                }

                preferences.setKeyUserLoginType(type);
                preferences.setKeyUserPin(password);
                preferences.setKeyUserFName(userLoginResult.getResult().getCustomerProfile().getFirstName());
                preferences.setKeyUserLName(userLoginResult.getResult().getCustomerProfile().getLastName());
                preferences.setKeyIsLogin(true);

                volleyRequestController.Pdf();
                // Toast.makeText(this, userLoginResult.getResult().getResponseMessage(), Toast.LENGTH_SHORT).show();

            } else {
                Global.utils.hideCustomLoadingDialog();
                Global.utils.showErrorSnakeBar(this, userLoginResult.getResult().getResponseMessage());
            }
        }


        if (requestType.equals(Constants.PDF)) {
            PdfResult pdfResult = gson.fromJson(response.toString(), PdfResult.class);

            if (pdfResult.getMessage().equalsIgnoreCase(Constants.IBCC_SUCCESS_MESSAGE)) {
                Global.pdfResponse = pdfResult;
                Global.utils.hideCustomLoadingDialog();

                Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else {
                Global.utils.hideCustomLoadingDialog();
                Toast.makeText(this, pdfResult.getMessage(), Toast.LENGTH_LONG).show();
            }
        }


    }*/

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
                        if (response.body().getResult().getResponseCode().equalsIgnoreCase(Constants.IBCC_SUCCESS_CODE)) {
                            Global.userLoginResponse = response.body();

                            if (type.equalsIgnoreCase("Email")) {
                                Preferences.getInstance(LoginActivity.this).setKeyUserEmail(email);
                            } else {
                                Preferences.getInstance(LoginActivity.this).setKeyUserCnic(cnic);
                            }

                            Preferences.getInstance(LoginActivity.this).setKeyUserLoginType(type);
                            Preferences.getInstance(LoginActivity.this).setKeyUserPin(password);
                            Preferences.getInstance(LoginActivity.this).setKeyUserFName(response.body().getResult().getCustomerProfile().getFirstName());
                            Preferences.getInstance(LoginActivity.this).setKeyUserLName(response.body().getResult().getCustomerProfile().getLastName());
                            Preferences.getInstance(LoginActivity.this).setKeyIsLogin(true);

                            //callPDFApi(LoginActivity.this);
                            Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);

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

                            Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);

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

}