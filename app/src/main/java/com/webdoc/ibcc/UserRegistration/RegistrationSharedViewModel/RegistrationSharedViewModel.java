package com.webdoc.ibcc.UserRegistration.RegistrationSharedViewModel;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.webdoc.ibcc.CompleteProfile.SuccessRegistration;
import com.webdoc.ibcc.DataModel.LoginUser;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.ResponseModels.CheckRegistrationResult.CheckRegistrationResult;
import com.webdoc.ibcc.ResponseModels.UserLoginResult.UserLoginResult;
import com.webdoc.ibcc.ResponseModels.UserRegisterResult.UserRegisterResult;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationSharedViewModel extends ViewModel {
    //Mutable LiveData:
    private final MutableLiveData<UserLoginResult> MLD_USER_LOGIN = new MutableLiveData<>();
    private final MutableLiveData<CheckRegistrationResult> MLD_CHECT_USER = new MutableLiveData<>();
    private final MutableLiveData<UserRegisterResult> MLD_USER_REGISTERATION = new MutableLiveData<>();

    //LiveData:
    public LiveData<UserLoginResult> getUserLoginLiveData() {
        return MLD_USER_LOGIN;
    }

    public LiveData<CheckRegistrationResult> getCheckUserLiveData() {
        return MLD_CHECT_USER;
    }

    public LiveData<UserRegisterResult> getUserRegisterationLiveData() {
        return MLD_USER_REGISTERATION;
    }

    //Calling Apis:
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
                        MLD_USER_LOGIN.postValue(response.body());
                    } else {
                        Global.utils.showErrorSnakeBar(activity, response.body().getMessage());
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
                        MLD_CHECT_USER.postValue(response.body());
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
                        MLD_USER_REGISTERATION.postValue(response.body());
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
}
