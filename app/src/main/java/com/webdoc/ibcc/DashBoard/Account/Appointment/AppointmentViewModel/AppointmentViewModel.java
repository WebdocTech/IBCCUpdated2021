package com.webdoc.ibcc.DashBoard.Account.Appointment.AppointmentViewModel;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.webdoc.ibcc.DashBoard.Account.Appointment.AttestationAppointment.CompletedAppointment;
import com.webdoc.ibcc.DashBoard.Account.Appointment.EquivalenceAppointment.AppointmentStatusEQActivity;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.AttestationApplyActivity;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.DocumentCheckList.DocumentChecklisActivity;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.ApplyEquivalenceActivity;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.AddQualificationModel;
import com.webdoc.ibcc.Model.DeleteParams;
import com.webdoc.ibcc.Model.DocumentDetailModel;
import com.webdoc.ibcc.Model.DocumentSelectionModel;
import com.webdoc.ibcc.Model.EquivalenceFileModel;
import com.webdoc.ibcc.Model.EquivalenceInitiateCase;
import com.webdoc.ibcc.Model.EquivalenceTravelFileModel;
import com.webdoc.ibcc.Model.UpdatePaymentInfoRequestModel;
import com.webdoc.ibcc.Model.inquiryRequestModel;
import com.webdoc.ibcc.ResponseModels.AddEducationResult.AddEducationResult;
import com.webdoc.ibcc.ResponseModels.AddEducationResult.EducationDetail;
import com.webdoc.ibcc.ResponseModels.AddEducationResult.Result;
import com.webdoc.ibcc.ResponseModels.AddQualificationEQ.QualificationSubjectResponse;
import com.webdoc.ibcc.ResponseModels.CancelAppointmentResult.CancelAppointmentResult;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.Country;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.EquivalenceGrade;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.EquivalenceGradingSystem;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.EquivalenceGroup;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.ExaminingBody;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.Qualification;
import com.webdoc.ibcc.ResponseModels.IncompleteDetailsEQ.Document_EQ;
import com.webdoc.ibcc.ResponseModels.IncompleteDetailsEQ.IncompleteDetailsEQ;
import com.webdoc.ibcc.ResponseModels.IncompleteDetailsEQ.Paymentinfo;
import com.webdoc.ibcc.ResponseModels.SavePaymentInfo.SavePaymentInfo;
import com.webdoc.ibcc.ResponseModels.ViewAppointmentsEQ.ViewAppointmentsEQ;
import com.webdoc.ibcc.ResponseModels.ViewDetailsResult.ViewDetailsResult;
import com.webdoc.ibcc.ResponseModels.ViewIncompleteAppointmentEQ.ViewIncompleteAppointmentEQ;
import com.webdoc.ibcc.ResponseModels.ViewIncompleteDetailsResult.Document;
import com.webdoc.ibcc.ResponseModels.ViewIncompleteDetailsResult.ViewIncompleteDetails;
import com.webdoc.ibcc.ResponseModels.ViewIncompleteResult.ViewIncompleteResult;
import com.webdoc.ibcc.ResponseModels.inquiryResult.InquiryResult;
import com.webdoc.ibcc.ResponseModels.updatePAymentInfoResult.UpdatePAymentInfoResult;
import com.webdoc.ibcc.Retrofit.jsonPlaceHolderApi;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.webdoc.ibcc.DashBoard.Dashboard.BottomLayout;

public class AppointmentViewModel extends ViewModel {
    //Mutable LiveData:
    private MutableLiveData<ViewDetailsResult> MLD_DETAILS_RESULT = new MutableLiveData<>();
    private MutableLiveData<CancelAppointmentResult> MLD_CANCEL_APPOINTMENT = new MutableLiveData<>();
    private MutableLiveData<InquiryResult> MLD_PAYMENT_INQUIRY = new MutableLiveData<>();
    private MutableLiveData<UpdatePAymentInfoResult> MLD_UPDATE_PAYMENT_INFO_EQ = new MutableLiveData<>();
    private MutableLiveData<ViewAppointmentsEQ> MLD_VIEW_APPOINTMENT_INFO_EQ = new MutableLiveData<>();
    private MutableLiveData<ViewIncompleteResult> MLD_VIEW_INCOMPLETE_RESULT = new MutableLiveData<>();
    private MutableLiveData<ViewIncompleteDetails> MLD_VIEW_INCOMPLETE_DETAILS = new MutableLiveData<>();
    private MutableLiveData<ViewIncompleteAppointmentEQ> MLD_VIEW_INCOMPLETE_APPOINTMENT_EQ = new MutableLiveData<>();
    private MutableLiveData<IncompleteDetailsEQ> MLD_VIEW_INCOMPLETE_DETAILS_EQ = new MutableLiveData<>();

    //LiveData:
    public LiveData<ViewDetailsResult> getDetatilsResult() {
        return MLD_DETAILS_RESULT;
    }

    public LiveData<CancelAppointmentResult> getCancelAppointment() {
        return MLD_CANCEL_APPOINTMENT;
    }

    public LiveData<InquiryResult> getPaymentInquiry() {
        return MLD_PAYMENT_INQUIRY;
    }

    public LiveData<UpdatePAymentInfoResult> getUpdatePaymentInfoEQ() {
        return MLD_UPDATE_PAYMENT_INFO_EQ;
    }

    public LiveData<ViewAppointmentsEQ> getViewAppointmentInfoEQ() {
        return MLD_VIEW_APPOINTMENT_INFO_EQ;
    }

    public LiveData<ViewIncompleteResult> getViewIncompleteResult() {
        return MLD_VIEW_INCOMPLETE_RESULT;
    }

    public LiveData<ViewIncompleteDetails> getViewIncompleteDetails() {
        return MLD_VIEW_INCOMPLETE_DETAILS;
    }

    public LiveData<ViewIncompleteAppointmentEQ> getViewIncompleteAppointmentEQ() {
        return MLD_VIEW_INCOMPLETE_APPOINTMENT_EQ;
    }

    public LiveData<IncompleteDetailsEQ> getViewIncompleteDetailsEQ() {
        return MLD_VIEW_INCOMPLETE_DETAILS_EQ;
    }

    //Calling Apis:
    public void callViewDetailsApi(Activity activity) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("user_id", Global.userLoginResponse.getResult()
                    .getCustomerProfile().getId());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<ViewDetailsResult> call = apiInterface.callViewDetails(params);

            call.enqueue(new Callback<ViewDetailsResult>() {
                @Override
                public void onResponse(Call<ViewDetailsResult> call, Response<ViewDetailsResult> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        MLD_DETAILS_RESULT.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ViewDetailsResult> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callCancelAppointmentApi(Activity activity, String caseId) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("case_id", caseId);
            params.addProperty("user_id", Global.userLoginResponse.getResult()
                    .getCustomerProfile().getId());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<CancelAppointmentResult> call = apiInterface.callCancelAppointment(params);

            call.enqueue(new Callback<CancelAppointmentResult>() {
                @Override
                public void onResponse(Call<CancelAppointmentResult> call, Response<CancelAppointmentResult> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        MLD_CANCEL_APPOINTMENT.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CancelAppointmentResult> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Oopps something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callingPaymentInquiryApi(String orderId, String storeId, String accountNum) {

        inquiryRequestModel requestModel = new inquiryRequestModel();
        requestModel.setAccountNum(accountNum);
        requestModel.setOrderId(orderId);
        requestModel.setStoreId(storeId);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient();
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" https://easypay.easypaisa.com.pk/easypay-service/rest/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) // Set HttpClient to be used by Retrofit
                .build();

        jsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);
        Call<InquiryResult> call1 = jsonPlaceHolderApi.inquiryresult(requestModel);

        call1.enqueue(new Callback<InquiryResult>() {
            @Override
            public void onResponse(Call<InquiryResult> call, Response<InquiryResult> response) {
                if (response.isSuccessful()) {
                    MLD_PAYMENT_INQUIRY.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<InquiryResult> call, Throwable t) {
                Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    public void callingUpdatePaymentInfoEQApi(String caseId, String transectionId, String type) {
        UpdatePaymentInfoRequestModel requestModel = new UpdatePaymentInfoRequestModel();
        requestModel.setCase_id(caseId);
        requestModel.setTransection_id(transectionId);
        requestModel.setType(type);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient();
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ibcc.webddocsystems.com/api/Equivalence/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) // Set HttpClient to be used by Retrofit
                .build();

        jsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);
        Call<UpdatePAymentInfoResult> call1 = jsonPlaceHolderApi.UpdatePaymentInfoEQ(requestModel);

        call1.enqueue(new Callback<UpdatePAymentInfoResult>() {
            @Override
            public void onResponse(Call<UpdatePAymentInfoResult> call, Response<UpdatePAymentInfoResult> response) {

                UpdatePAymentInfoResult result = response.body();
                Global.updatePAymentInfoResult = result;
                if (!response.isSuccessful()) {
                    MLD_UPDATE_PAYMENT_INFO_EQ.postValue(response.body());
                    /*Toast.makeText(AppointmentStatusEQActivity.this, response.message(),
                            Toast.LENGTH_SHORT).show();
                    return;*/
                }
            }

            @Override
            public void onFailure(Call<UpdatePAymentInfoResult> call, Throwable t) {
                Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    public void callViewAppointmentEQApi(Activity activity) {
        if (Constants.isInternetConnected(activity)) {
            //Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("email", Global.userLoginResponse.getResult().getCustomerProfile().getEmail());
            params.addProperty("cnic", Global.userLoginResponse.getResult().getCustomerProfile().getCnic());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<ViewAppointmentsEQ> call = apiInterface.callViewAppointmentEQ(params);

            call.enqueue(new Callback<ViewAppointmentsEQ>() {
                @Override
                public void onResponse(Call<ViewAppointmentsEQ> call, Response<ViewAppointmentsEQ> response) {
                    //Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        MLD_VIEW_APPOINTMENT_INFO_EQ.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ViewAppointmentsEQ> call, Throwable t) {
                    //Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callViewIncompleteApi(Activity activity) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("user_id", Global.userLoginResponse.getResult().getCustomerProfile().getId());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<ViewIncompleteResult> call = apiInterface.callViewIncomplete(params);

            call.enqueue(new Callback<ViewIncompleteResult>() {
                @Override
                public void onResponse(Call<ViewIncompleteResult> call, Response<ViewIncompleteResult> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        MLD_VIEW_INCOMPLETE_RESULT.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ViewIncompleteResult> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Oopps something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callViewIncompleteDetailsApi(Activity activity, String caseId) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("case_id", caseId);

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<ViewIncompleteDetails> call = apiInterface.callViewIncompleteDetails(params);

            call.enqueue(new Callback<ViewIncompleteDetails>() {
                @Override
                public void onResponse(Call<ViewIncompleteDetails> call, Response<ViewIncompleteDetails> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        MLD_VIEW_INCOMPLETE_DETAILS.postValue(response.body());
                    } else {
                        Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ViewIncompleteDetails> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callViewIncompleteAppointmentEQApi(Activity activity) {
        if (Constants.isInternetConnected(activity)) {
            //Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("email", Global.userLoginResponse.getResult()
                    .getCustomerProfile().getEmail());
            params.addProperty("cnic", Global.userLoginResponse.getResult()
                    .getCustomerProfile().getCnic());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<ViewIncompleteAppointmentEQ> call = apiInterface.callViewIncompleteEQ(params);

            call.enqueue(new Callback<ViewIncompleteAppointmentEQ>() {
                @Override
                public void onResponse(Call<ViewIncompleteAppointmentEQ> call, Response<ViewIncompleteAppointmentEQ> response) {
                    //Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        MLD_VIEW_INCOMPLETE_APPOINTMENT_EQ.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ViewIncompleteAppointmentEQ> call, Throwable t) {
                    //Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callViewIncompleteDetailsEQApi(Activity activity, String caseID) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("case_id", caseID);

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<IncompleteDetailsEQ> call = apiInterface.callViewIncompleteDetailsEQ(params);

            call.enqueue(new Callback<IncompleteDetailsEQ>() {
                @Override
                public void onResponse(Call<IncompleteDetailsEQ> call, Response<IncompleteDetailsEQ> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        MLD_VIEW_INCOMPLETE_DETAILS_EQ.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<IncompleteDetailsEQ> call, Throwable t) {
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
