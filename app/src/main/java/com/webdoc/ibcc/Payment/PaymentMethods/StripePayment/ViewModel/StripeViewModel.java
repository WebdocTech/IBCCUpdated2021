package com.webdoc.ibcc.Payment.PaymentMethods.StripePayment.ViewModel;

import android.app.Activity;
import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.Pyament.RequestModel.EquilanceRequestModel;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Payment.PaymentMethods.StripePayment.responseModel.DollerRateResponseModel;
import com.webdoc.ibcc.ResponseModels.SavePaymentInfo.SavePaymentInfo;
import com.webdoc.ibcc.Retrofit.jsonPlaceHolderApi;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StripeViewModel extends AndroidViewModel {

    private String case_id, user_id, amount_paid, bank,
            account_number, mobile_number, transection_type,
            transaction_reference_number, transaction_datetime,
            center, IBCC_amount, webdoc_amount, status, userIdEq;

    private final MutableLiveData<SavePaymentInfo> MLD_save_payment_info_from_equivalance = new MutableLiveData<>();

    private final MutableLiveData<DollerRateResponseModel> MLD_dollor_response_model = new MutableLiveData<>();

    //todo: Live Data
    public LiveData<SavePaymentInfo> LD_savePaymentInfoEquialance() {
        return MLD_save_payment_info_from_equivalance;
    }

    public LiveData<DollerRateResponseModel> LD_getDollorRate() {
        return MLD_dollor_response_model;
    }

    public StripeViewModel(@NonNull Application application) {
        super(application);
    }

    public void callSavePaymentEQApi(String keyUserPhone, double pkrRate) {

        if (!TextUtils.isEmpty(Global.caseId)) {
            case_id = Global.caseId;
        } else {
            case_id = Global.incompleteCaseId;
        }
        double ibccAmount = pkrRate * 140;
        double webdocAmount = pkrRate * 20;
        double bankCharges = pkrRate * 15;
        double courierFee = pkrRate * 5;
        double totalAmount = pkrRate * 180;

        user_id = Global.userLoginResponse.getResult().getCustomerProfile().getId();
        amount_paid = String.valueOf(totalAmount);
        bank = "Stripe Payment";
        account_number = Global.userLoginResponse.getResult().getCustomerProfile().getMobile();
        mobile_number = Global.userLoginResponse.getResult().getCustomerProfile().getMobile();
        transection_type = "International Payment";
        transaction_reference_number = "";
        transaction_datetime = "";
        center = Global.center;
        IBCC_amount = String.valueOf(ibccAmount);
        webdoc_amount = String.valueOf(webdocAmount);
        status = "Success";
        String bank_charges = String.valueOf(bankCharges);
        String courier_amount = String.valueOf(courierFee);
        String order_id = "";
        userIdEq = Global.userLoginResponse.getResult().getCustomerProfile().getId();
        String platform = "Android";

        callingSavePaymentForEquilance(case_id, amount_paid, bank, account_number,
                mobile_number, transection_type, transaction_reference_number,
                transaction_datetime, userIdEq, status, webdoc_amount, IBCC_amount,
                bank_charges, courier_amount, order_id, platform);

      /*  if (Global.isFromEquivalence) {
            //call savePayment Api for Equilance
            callingSavePaymentForEquilance(case_id, amount_paid, bank, account_number,
                    mobile_number, transection_type, transaction_reference_number,
                    transaction_datetime, userIdEq, status, webdoc_amount, IBCC_amount,
                    bank_charges, courier_amount, order_id, platform);
        }*/

    }

    private void callingSavePaymentForEquilance(String case_id, String amount_paid, String bank, String account_number, String mobile_number, String transection_type, String transaction_reference_number, String transaction_datetime, String userIdEq, String status, String webdoc_amount, String ibcc_amount, String bank_charges, String courier_amount, String order_id, String platform) {

        EquilanceRequestModel requestModel = new EquilanceRequestModel();
        requestModel.setCase_id(case_id);
        requestModel.setAmount_paid(amount_paid);
        requestModel.setBank(bank);
        requestModel.setAccount_number(account_number);
        requestModel.setMobile_number(mobile_number);
        requestModel.setTransection_type(transection_type);
        requestModel.setTransaction_reference_number(transaction_reference_number);
        requestModel.setTransaction_datetime(transaction_datetime);
        requestModel.setUser_id(String.valueOf(Global.equivalenceInitiateCaseResponse.getResult().getIntiateCaseResponseDetails().getCustomerId()));
        requestModel.setIbcC_amount(IBCC_amount);
        requestModel.setWebdoc_amount(webdoc_amount);
        requestModel.setStatus(status);
        requestModel.setBank_charges(bank_charges);
        requestModel.setCourier_amount(courier_amount);
        requestModel.setOrder_id(order_id);
        requestModel.setPlatform(platform);


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
        Call<SavePaymentInfo> call1 = jsonPlaceHolderApi.savePaymentInfoforEquialance(requestModel);
        call1.enqueue(new Callback<SavePaymentInfo>() {
            @Override
            public void onResponse(Call<SavePaymentInfo> call, Response<SavePaymentInfo> response) {

                SavePaymentInfo savePaymentInfo = response.body();

                if (!response.isSuccessful()) {
                    Toast.makeText(Global.applicationContext, response.body().toString(), Toast.LENGTH_SHORT).show();
                    return;
                }

                MLD_save_payment_info_from_equivalance.postValue(savePaymentInfo);

            }

            @Override
            public void onFailure(Call<SavePaymentInfo> call, Throwable t) {
                Toast.makeText(Global.applicationContext, "onFailure called in save payment api equilance ", Toast.LENGTH_SHORT).show();
                Log.i("kh", t.getMessage());
                call.cancel();
            }
        });


    }

    public void callDollorRateApi(Activity activity) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            APIInterface apiInterface = APIClient.getClient("https://openexchangerates.org/");
            Call<DollerRateResponseModel> call = apiInterface.callDollorRate();

            call.enqueue(new Callback<DollerRateResponseModel>() {
                @Override
                public void onResponse(Call<DollerRateResponseModel> call, Response<DollerRateResponseModel> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        MLD_dollor_response_model.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DollerRateResponseModel> call, Throwable t) {
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
