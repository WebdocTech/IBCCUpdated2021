package com.webdoc.ibcc.DashBoard.DashboardSharedViewModel;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.webdoc.ibcc.DashBoard.Dashboard;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.GetDetailsEquivalence;
import com.webdoc.ibcc.ResponseModels.PdfResult.PdfResult;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardSharedViewModel extends ViewModel {
    //Mutable LiveData:
    private final MutableLiveData<PdfResult> MLD_PDF_RESULT = new MutableLiveData<>();
    private final MutableLiveData<GetDetailsEquivalence> MLD_DETAILS_EQUIVALENCE = new MutableLiveData<>();
    //private final MutableLiveData<UserRegisterResult> MLD_USER_REGISTERATION = new MutableLiveData<>();

    //LiveData:
    public LiveData<PdfResult> getPdfResultLiveData() {
        return MLD_PDF_RESULT;
    }

    public LiveData<GetDetailsEquivalence> getDetailsEquivalenceLiveData() {
        return MLD_DETAILS_EQUIVALENCE;
    }

    //Calling Apis:
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
                        MLD_PDF_RESULT.postValue(response.body());
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
                        MLD_DETAILS_EQUIVALENCE.postValue(response.body());
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
}
