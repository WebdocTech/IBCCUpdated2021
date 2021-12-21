package com.webdoc.ibcc.CompleteProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;
import com.webdoc.ibcc.DashBoard.Dashboard;
import com.webdoc.ibcc.DataModel.LoginUser;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Essentails.Preferences;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.UserLoginResult.UserLoginResult;
import com.webdoc.ibcc.ResponseModels.UserRegisterResult.UserRegisterResult;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.UserRegistration.RegistrationSharedViewModel.RegistrationSharedViewModel;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.ActivitySuccessRegistrationBinding;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuccessRegistration extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Boolean loadingData;
    private ActivitySuccessRegistrationBinding layoutBinding;
    private RegistrationSharedViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_registration);
        layoutBinding = ActivitySuccessRegistrationBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        viewModel = ViewModelProviders.of(this).get(RegistrationSharedViewModel.class);
        //callUserRegistrationApi(this);
        observers();
        callApi();
    }

    private void observers() {
        //user Registeration api response:
        viewModel.getUserRegisterationLiveData().observe(this, response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Global.userRegisterResponse = response;

                    Global.loginUser.setEmail(Global.registerUser.getEmail());
                    Global.loginUser.setCnic(Global.registerUser.getCnic());
                    Global.loginUser.setPassword(Global.registerUser.getPassword());
                    Global.loginUser.setType("Email");

                    //callUserLoginApi(SuccessRegistration.this, Global.loginUser);
                    viewModel.callUserLoginApi(this, Global.loginUser);
                } else {
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(SuccessRegistration.this,
                            SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(response.getResult().getResponseMessage())
                            .setConfirmText("Retry")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                    //callUserRegistrationApi(SuccessRegistration.this);
                                    viewModel.callUserRegistrationApi(SuccessRegistration.this);
                                }
                            });
                    sweetAlertDialog.setCancelable(false);
                    sweetAlertDialog.show();
                }
            }
        });

        //loginApi response:
        viewModel.getUserLoginLiveData().observe(this, response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Global.userLoginResponse = response;

                    mAuth.signInWithEmailAndPassword(Global.registerUser.getPhoneNumber() + "@ibcc.com.pk", Global.registerUser.getPhoneNumber())
                            .addOnCompleteListener(SuccessRegistration.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        DatabaseReference reference = FirebaseDatabase.getInstance()
                                                .getReference("Users").child(Global.registerUser.getPhoneNumber()
                                                        + "@ibcccompk");

                                        HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                        // hashMap.put("Email", Global.registerUser.getPhoneNumber() + "@ibcc.com.pk");
                                        hashMap.put("Email", Global.registerUser.getEmail());
                                        hashMap.put("Password", Global.registerUser.getPassword());
                                        hashMap.put("FirstName", Global.registerUser.getFirstName());
                                        hashMap.put("LastName", Global.registerUser.getLastName());
                                        hashMap.put("FatherName", Global.registerUser.getFatherName());
                                        hashMap.put("Title", Global.registerUser.getTitle());
                                        hashMap.put("Mobile", Global.registerUser.getPhoneNumber());
                                        hashMap.put("CNIC", Global.registerUser.getCnic());
                                        hashMap.put("DOB", Global.registerUser.getDob());
                                        hashMap.put("Domicile", Global.registerUser.getDomicile());
                                        hashMap.put("Phone", Global.registerUser.getPhoneNumber());
                                        hashMap.put("PresentCity", Global.registerUser.getCity());
                                        hashMap.put("PermanentCity", Global.registerUser.getCity());
                                        hashMap.put("PresentAddress", Global.registerUser.getAddress());
                                        hashMap.put("PermanentAddress", Global.registerUser.getAddress());
                                        hashMap.put("PresentCountryId", Global.registerUser.getCountryId());
                                        hashMap.put("PermanentCountryId", Global.registerUser.getCountryId());
                                        hashMap.put("PresentProvinceId", Global.registerUser.getProvinceId());
                                        hashMap.put("PermanentProvinceId", Global.registerUser.getProvinceId());
                                        hashMap.put("Nationality", Global.registerUser.getCountry());
                                        // hashMap.put("ImgUrl", Global.registerUser.getUrl());
                                        hashMap.put("ImgUrl", " ");

                                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Preferences.getInstance(SuccessRegistration.this).setKeyUserPhone(Global.registerUser.getPhoneNumber());
                                                    Preferences.getInstance(SuccessRegistration.this).setKeyUserCnic(Global.registerUser.getCnic());
                                                    Preferences.getInstance(SuccessRegistration.this).setKeyUserPin(Global.registerUser.getPassword());
                                                    Preferences.getInstance(SuccessRegistration.this).setKeyUserFName(Global.registerUser.getFirstName());
                                                    Preferences.getInstance(SuccessRegistration.this).setKeyUserLName(Global.registerUser.getLastName());
                                                    Preferences.getInstance(SuccessRegistration.this).setKeyUserEmail(Global.registerUser.getEmail());
                                                    Preferences.getInstance(SuccessRegistration.this).setKeyUserLoginType("Email");

                                                    loadingData = true;
                                                    registrationCompleted();

                                                } else {
                                                    Toast.makeText(SuccessRegistration.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(SuccessRegistration.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(SuccessRegistration.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Login: " + response.getMessage())
                            .setConfirmText("Retry")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                    //callUserLoginApi(SuccessRegistration.this, Global.loginUser);
                                    viewModel.callUserLoginApi(SuccessRegistration.this, Global.loginUser);
                                }
                            });
                    sweetAlertDialog.setCancelable(false);
                    sweetAlertDialog.show();
                }
            }
        });
    }

    private void callApi() {
        viewModel.callUserRegistrationApi(this);
    }

    public void registrationCompleted() {
        if (loadingData) {
            Preferences.getInstance(this).setKeyIsLogin(true);  //might comment this line

            layoutBinding.SuccessLayout.setVisibility(View.VISIBLE);
            layoutBinding.WaitingLayout.setVisibility(View.GONE);

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(SuccessRegistration.this, Dashboard.class);
                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }, 3000);
        }
    }

    public void callUserRegistrationApi(Activity activity) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("first_name", Global.registerUser.getFirstName());
            params.addProperty("password", Global.registerUser.getPassword());
            params.addProperty("last_name", Global.registerUser.getLastName());
            params.addProperty("dob", Global.registerUser.getDob());
            params.addProperty("father_name", Global.registerUser.getFatherName());
            params.addProperty("cnic", Global.registerUser.getCnic());
            params.addProperty("title", Global.registerUser.getTitle());
            params.addProperty("domicile", Global.registerUser.getDomicile());
            // params.put("birth_place", Global.registerUser.getCountry());
            params.addProperty("p_add", Global.registerUser.getAddress());  //p= permanent address
            params.addProperty("p_city", Global.registerUser.getCity());
            params.addProperty("p_province_id", Global.registerUser.getProvinceId());
            params.addProperty("p_country_id", Global.registerUser.getCountryId());
            params.addProperty("c_add", Global.registerUser.getAddress());  //c= current address
            params.addProperty("c_city", Global.registerUser.getCity());
            params.addProperty("c_province_id", Global.registerUser.getProvinceId());
            params.addProperty("c_country_id", Global.registerUser.getCountryId());
            params.addProperty("phone", Global.registerUser.getPhoneNumber());
            params.addProperty("mobile", Global.registerUser.getPhoneNumber());
            params.addProperty("email", Global.registerUser.getEmail());
            params.addProperty("passport_siz_image", Global.timestamp + ".png");
            params.addProperty("nationality", Global.registerUser.getCountry());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<UserRegisterResult> call = apiInterface.callUserRegistration(params);

            call.enqueue(new Callback<UserRegisterResult>() {
                @Override
                public void onResponse(Call<UserRegisterResult> call, Response<UserRegisterResult> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            Global.userRegisterResponse = response.body();

                            Global.loginUser.setEmail(Global.registerUser.getEmail());
                            Global.loginUser.setCnic(Global.registerUser.getCnic());
                            Global.loginUser.setPassword(Global.registerUser.getPassword());
                            Global.loginUser.setType("Email");

                            callUserLoginApi(SuccessRegistration.this, Global.loginUser);
                        } else {
                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(SuccessRegistration.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(response.body().getResult().getResponseMessage())
                                    .setConfirmText("Retry")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismissWithAnimation();
                                            callUserRegistrationApi(SuccessRegistration.this);
                                        }
                                    });
                            sweetAlertDialog.setCancelable(false);
                            sweetAlertDialog.show();
                        }
                    } else {
                        Toast.makeText(activity, "Something went wrong / Server error !", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserRegisterResult> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Failure, Oopps something went wrong !", Toast.LENGTH_SHORT).show();
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
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            Global.userLoginResponse = response.body();

                            mAuth.signInWithEmailAndPassword(Global.registerUser.getPhoneNumber() + "@ibcc.com.pk", Global.registerUser.getPhoneNumber())
                                    .addOnCompleteListener(SuccessRegistration.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(Global.registerUser.getPhoneNumber() + "@ibcccompk");

                                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                                // hashMap.put("Email", Global.registerUser.getPhoneNumber() + "@ibcc.com.pk");
                                                hashMap.put("Email", Global.registerUser.getEmail());
                                                hashMap.put("Password", Global.registerUser.getPassword());
                                                hashMap.put("FirstName", Global.registerUser.getFirstName());
                                                hashMap.put("LastName", Global.registerUser.getLastName());
                                                hashMap.put("FatherName", Global.registerUser.getFatherName());
                                                hashMap.put("Title", Global.registerUser.getTitle());
                                                hashMap.put("Mobile", Global.registerUser.getPhoneNumber());
                                                hashMap.put("CNIC", Global.registerUser.getCnic());
                                                hashMap.put("DOB", Global.registerUser.getDob());
                                                hashMap.put("Domicile", Global.registerUser.getDomicile());
                                                hashMap.put("Phone", Global.registerUser.getPhoneNumber());
                                                hashMap.put("PresentCity", Global.registerUser.getCity());
                                                hashMap.put("PermanentCity", Global.registerUser.getCity());
                                                hashMap.put("PresentAddress", Global.registerUser.getAddress());
                                                hashMap.put("PermanentAddress", Global.registerUser.getAddress());
                                                hashMap.put("PresentCountryId", Global.registerUser.getCountryId());
                                                hashMap.put("PermanentCountryId", Global.registerUser.getCountryId());
                                                hashMap.put("PresentProvinceId", Global.registerUser.getProvinceId());
                                                hashMap.put("PermanentProvinceId", Global.registerUser.getProvinceId());
                                                hashMap.put("Nationality", Global.registerUser.getCountry());
                                                // hashMap.put("ImgUrl", Global.registerUser.getUrl());
                                                hashMap.put("ImgUrl", " ");

                                                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Preferences.getInstance(SuccessRegistration.this).setKeyUserPhone(Global.registerUser.getPhoneNumber());
                                                            Preferences.getInstance(SuccessRegistration.this).setKeyUserCnic(Global.registerUser.getCnic());
                                                            Preferences.getInstance(SuccessRegistration.this).setKeyUserPin(Global.registerUser.getPassword());
                                                            Preferences.getInstance(SuccessRegistration.this).setKeyUserFName(Global.registerUser.getFirstName());
                                                            Preferences.getInstance(SuccessRegistration.this).setKeyUserLName(Global.registerUser.getLastName());
                                                            Preferences.getInstance(SuccessRegistration.this).setKeyUserEmail(Global.registerUser.getEmail());
                                                            Preferences.getInstance(SuccessRegistration.this).setKeyUserLoginType("Email");

                                                            loadingData = true;
                                                            registrationCompleted();

                                                        } else {
                                                            Toast.makeText(SuccessRegistration.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(SuccessRegistration.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        } else {
                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(SuccessRegistration.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Login: " + response.body().getMessage())
                                    .setConfirmText("Retry")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismissWithAnimation();
                                            callUserLoginApi(SuccessRegistration.this, Global.loginUser);
                                        }
                                    });
                            sweetAlertDialog.setCancelable(false);
                            sweetAlertDialog.show();
                        }
                    } else {
                        Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserLoginResult> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Oopps something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

}