package com.webdoc.ibcc.Payment.PaymentMethods.JsBankAccount.ViewModel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Payment.PaymentMethods.JsBankAccount.ResponseModels.JsGenerateOtpResponse.JsGenerateOtpResponse;
import com.webdoc.ibcc.Retrofit.jsonPlaceHolderApi;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JsAccountGenerateOtpViewModel extends AndroidViewModel {

    private final MutableLiveData<JsGenerateOtpResponse> MLD_JsAccounGenerateOtpApi = new MutableLiveData<>();

    public JsAccountGenerateOtpViewModel(@NonNull Application application) {
        super(application);


    }

    public LiveData<JsGenerateOtpResponse> LD_JsAccounGenerateOtpApi() {
        return MLD_JsAccounGenerateOtpApi;
    }

   /* public void generateOtp(String accountNumber, String cnic) {

        JsAccountGenrateOtpReqModel reqModel = new JsAccountGenrateOtpReqModel();
        reqModel.setMTI("0200");
        reqModel.setProcessingCode("915000");
        reqModel.setTransmissionDatetime("1228164540");
        reqModel.setSystemsTraceAuditNumber("111261");
        reqModel.setTimeLocalTransaction("164540");
        reqModel.setDateLocalTransaction("0115");
        reqModel.setMerchantType("0111");
        reqModel.setCNIC("9030401000953");
        reqModel.setAccountIdentification1("0000445312");
        reqModel.setTransactionType("916000");
        reqModel.setTransactionDescription("OTP.20201228164540111261");


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
                .baseUrl("https://sit-mdw.jsbl.com:8443/uatpaymentinquiry/otpservice1/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) // Set HttpClient to be used by Retrofit
                .build();
        String token = "1@#$%^7";
        jsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);
        Call<JsGenerateOtpResponse> call1 = jsonPlaceHolderApi.GenerateOtp(token, "application/json", reqModel);

        call1.enqueue(new Callback<JsGenerateOtpResponse>() {
            @Override
            public void onResponse(Call<JsGenerateOtpResponse> call, Response<JsGenerateOtpResponse> response) {


                if (!response.isSuccessful()) {
                    Toast.makeText(Global.applicationContext, response.body().toString(), Toast.LENGTH_SHORT).show();
                    return;
                }

                JsGenerateOtpResponse jsGenerateOtpResponse = response.body();
                Global.jsAccountGenerateApp = jsGenerateOtpResponse;

                //    MLD_JsPaymentInquiryApi.postValue(debitInquiryResult);
            }

            @Override
            public void onFailure(Call<JsGenerateOtpResponse> call, Throwable t) {
                Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });


    }*/

    public void newGenerateOtpFunc() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        MediaType mediaType = MediaType.parse("application/json");
        OkHttpClient client = new OkHttpClient();
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sit-mdw.jsbl.com:8443/uatpaymentinquiry/otpservice1/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) // Set HttpClient to be used by Retrofit
                .build();

        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"MTI\": \"0200\",\r\n    \"ProcessingCode\": \"915000\",\r\n    \"TransmissionDatetime\": \"1228164540\",\r\n    \"SystemsTraceAuditNumber\": \"111261\",\r\n    \"TimeLocalTransaction\": \"164540\",\r\n    \"DateLocalTransaction\": \"0115\",\r\n    \"MerchantType\": \"0111\",\r\n    \"CNIC\": \"9030401000953\",\r\n    \"AccountIdentification1\": \"0000445312\",\r\n    \"TransactionType\": \"916000\",\r\n    \"TransactionDescription\": \"OTP.20201228164540111261\"\r\n}");


        String token = "Basic Og==";
        jsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);
        Call<JsGenerateOtpResponse> call1 = jsonPlaceHolderApi.GenerateOtp(token, "application/json", body);
        call1.enqueue(new Callback<JsGenerateOtpResponse>() {
            @Override
            public void onResponse(Call<JsGenerateOtpResponse> call, Response<JsGenerateOtpResponse> response) {

                JsGenerateOtpResponse jsGenerateOtpResponse = response.body();
                Global.jsAccountGenerateOtp = jsGenerateOtpResponse;

                if (!response.isSuccessful()) {
                    Toast.makeText(Global.applicationContext, response.body().toString(), Toast.LENGTH_SHORT).show();
                    return;
                }

                MLD_JsAccounGenerateOtpApi.postValue(jsGenerateOtpResponse);


            }

            @Override
            public void onFailure(Call<JsGenerateOtpResponse> call, Throwable t) {
                Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                Log.i("kh", t.getMessage());
                call.cancel();
            }
        });


    }
}
