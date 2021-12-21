package com.webdoc.ibcc.UserRegistration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hbb20.CountryCodePicker;
import com.webdoc.ibcc.DashBoard.Dashboard;
import com.webdoc.ibcc.DataModel.LoginUser;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.CheckRegistrationResult.CheckRegistrationResult;
import com.webdoc.ibcc.ResponseModels.UserLoginResult.UserLoginResult;
import com.webdoc.ibcc.ServerManager.VolleyListener;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.SplashScreen;
import com.webdoc.ibcc.UserRegistration.RegistrationSharedViewModel.RegistrationSharedViewModel;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.ActivitySignUpBinding;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity /*implements VolleyListener*/ {
    VolleyRequestController volleyRequestController;
    private ActivitySignUpBinding layoutBinding;
    private RegistrationSharedViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        viewModel = ViewModelProviders.of(this).get(RegistrationSharedViewModel.class);

        /* Registering ccp with editText to get fullnumber */
        layoutBinding.ccp.registerCarrierNumberEditText(layoutBinding.edtMobileNo);

        clickListeners();
        observers();
    }

    private void observers() {
        viewModel.getCheckUserLiveData().observe(this, response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Toast.makeText(SignUpActivity.this, "User already exists",
                            Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(SignUpActivity.this,
                            MobileVerificationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
            }
        });
    }

    private void clickListeners() {
        layoutBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(layoutBinding.edtFirstName.getText().toString())) {
                    layoutBinding.edtFirstName.setError("enter your name");
                    layoutBinding.edtFirstName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(layoutBinding.edtLastName.getText().toString())) {
                    layoutBinding.edtLastName.setError("enter your name");
                    layoutBinding.edtLastName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(layoutBinding.edtFatherName.getText().toString())) {
                    layoutBinding.edtFatherName.setError("enter your father name");
                    layoutBinding.edtFatherName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(layoutBinding.edtEmail.getText().toString())) {
                    layoutBinding.edtEmail.setError("enter your email");
                    layoutBinding.edtEmail.requestFocus();
                    return;
                }
                String getEmailId = layoutBinding.edtEmail.getText().toString();
                if (!isEmailValid(getEmailId)) {
                    layoutBinding.edtEmail.setError("Your Email Id is Invalid");
                    layoutBinding.edtEmail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(layoutBinding.edtMobileNo.getText().toString())) {
                    layoutBinding.edtMobileNo.setError("enter your mobile number");
                    layoutBinding.edtMobileNo.requestFocus();
                    return;
                }
                if (layoutBinding.edtMobileNo.getText().toString().length() < 10) {
                    layoutBinding.edtMobileNo.setError("enter a valid mobile number");
                    layoutBinding.edtMobileNo.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(layoutBinding.edtCnic.getText().toString())) {
                    layoutBinding.edtCnic.setError("please enter your CNIC");
                    layoutBinding.edtCnic.requestFocus();
                    return;
                }

                Global.registerUser.setFirstName(layoutBinding.edtFirstName.getText().toString());
                Global.registerUser.setLastName(layoutBinding.edtLastName.getText().toString());
                Global.registerUser.setFatherName(layoutBinding.edtFatherName.getText().toString());
                Global.registerUser.setEmail(layoutBinding.edtEmail.getText().toString());
                Global.registerUser.setPhoneNumber(layoutBinding.ccp.getFullNumberWithPlus());
                Global.registerUser.setCnic(layoutBinding.edtCnic.getText().toString());

                String email = layoutBinding.edtEmail.getText().toString();
                String cnic = layoutBinding.edtCnic.getText().toString();
                String phoneNumber = layoutBinding.ccp.getFullNumberWithPlus();

                //Global.utils.showCustomLoadingDialog(SignUpActivity.this);
                //volleyRequestController.CheckUser(email, cnic);
                //callCheckUser(SignUpActivity.this, email, cnic);
                viewModel.callCheckUser(SignUpActivity.this, email, cnic);
            }
        });
    }


    /*@Override
    public void getRequestResponse(JSONObject response, String requestType) throws JSONException {
        Gson gson = new Gson();

        if (requestType.equals(Constants.CHECKREGISTRATION)) {
            CheckRegistrationResult checkUserResult = gson.fromJson(response.toString(), CheckRegistrationResult.class);

            if (checkUserResult.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                Global.utils.hideCustomLoadingDialog();
                Toast.makeText(this, "User already exists", Toast.LENGTH_LONG).show();
            } else {
                Global.utils.hideCustomLoadingDialog();
                Intent intent = new Intent(SignUpActivity.this, MobileVerificationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        }
    }*/

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void callCheckUser(Activity activity, String email, String cnic) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("email", email);
            params.addProperty("cnic", cnic);

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<CheckRegistrationResult> call = apiInterface.callCheckUser(params);

            call.enqueue(new Callback<CheckRegistrationResult>() {
                @Override
                public void onResponse(Call<CheckRegistrationResult> call, Response<CheckRegistrationResult> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            Toast.makeText(SignUpActivity.this, "User already exists", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(SignUpActivity.this, MobileVerificationActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CheckRegistrationResult> call, Throwable t) {
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