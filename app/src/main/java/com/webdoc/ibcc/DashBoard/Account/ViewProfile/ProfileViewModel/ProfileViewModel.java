package com.webdoc.ibcc.DashBoard.Account.ViewProfile.ProfileViewModel;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.webdoc.ibcc.DashBoard.Account.AccountFrag;
import com.webdoc.ibcc.DashBoard.Account.ViewProfile.ViewProfileActivity;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.ResponseModels.UpdateProfileResult.UpdateProfileResult;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {
    //Mutable LiveData:
    private MutableLiveData<UpdateProfileResult> MLD_UPDATE_PROFILE_RESULT = new MutableLiveData<>();

    //LiveData:
    public LiveData<UpdateProfileResult> getUpdateProfileResult() {
        return MLD_UPDATE_PROFILE_RESULT;
    }

    //Calling Apis:
    public void callUpdateProfileApi(Activity activity, String firstName, String lastName, String dob, String fatherName,
                                     String title, String domicileID, String pAddress, String pCity,
                                     String pProvinceId, String pCountryId, String cAddress, String cCity,
                                     String cProvinceId, String cCountryId, String nationality) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("id", Global.userLoginResponse.getResult().getCustomerProfile().getId());
            params.addProperty("first_name", firstName);
            params.addProperty("last_name", lastName);
            params.addProperty("dob", dob);
            params.addProperty("father_name", fatherName);
            params.addProperty("title", title);
            params.addProperty("domicile", domicileID);
            params.addProperty("p_add", pAddress);
            params.addProperty("p_city", pCity);
            params.addProperty("p_province_id", pProvinceId);
            params.addProperty("p_country_id", pCountryId);
            params.addProperty("c_add", cAddress);
            params.addProperty("c_city", cCity);
            params.addProperty("c_province_id", cProvinceId);
            params.addProperty("c_country_id", cCountryId);
            params.addProperty("passport_siz_image", " ");
            params.addProperty("nationality", nationality);

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<UpdateProfileResult> call = apiInterface.callUpdateProfile(params);

            call.enqueue(new Callback<UpdateProfileResult>() {
                @Override
                public void onResponse(Call<UpdateProfileResult> call, Response<UpdateProfileResult> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        MLD_UPDATE_PROFILE_RESULT.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UpdateProfileResult> call, Throwable t) {
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
