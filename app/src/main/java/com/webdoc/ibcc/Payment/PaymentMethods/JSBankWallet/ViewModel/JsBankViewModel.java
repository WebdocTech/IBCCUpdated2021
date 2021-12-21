package com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ViewModel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.RequestModel.AccountInquiryRequest;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.RequestModel.JsReqModel;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ResponseModel.JsBankAuthApi;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ResponseModel.jsDebitInquiryResult.JsDebitInquiryResult;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.jsbankdebitresponsemodel.JsDebitInqueryResponseModel;
import com.webdoc.ibcc.Retrofit.jsonPlaceHolderApi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JsBankViewModel extends AndroidViewModel {

    private final MutableLiveData<JsBankAuthApi> MLD_JsAuthAPi = new MutableLiveData<>();
    private final MutableLiveData<JsDebitInqueryResponseModel> MLD_JsPaymentInquiryApi = new MutableLiveData<>();
    String mobileno;
    String pkgPrice;

    public JsBankViewModel(@NonNull Application application) {
        super(application);
    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int SixDigitnumber = rnd.nextInt(999999);
        // this will convert any number sequence into 6 character.

        return String.format("%06d", SixDigitnumber);
    }

    public LiveData<JsBankAuthApi> LD_JsAuthAPi() {
        return MLD_JsAuthAPi;
    }

    public LiveData<JsDebitInqueryResponseModel> LD_JsPAymentInquiryApi() {
        return MLD_JsPaymentInquiryApi;
    }

    //todo:1st Api for JsBank payment
    public void JSBankAuthApi() {
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
                .baseUrl("https://sandbox.jsbl.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) // Set HttpClient to be used by Retrofit
                .build();

        jsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);
        Call<JsBankAuthApi> call1 = jsonPlaceHolderApi.jsAuthApi();

        call1.enqueue(new Callback<JsBankAuthApi>() {
            @Override
            public void onResponse(Call<JsBankAuthApi> call, Response<JsBankAuthApi> response) {

                JsBankAuthApi authApi = response.body();
                Global.authApiResp = authApi;
                if (!response.isSuccessful()) {
                    Toast.makeText(Global.applicationContext, response.body().toString(), Toast.LENGTH_SHORT).show();
                    return;
                }
                MLD_JsAuthAPi.postValue(authApi);
            }

            @Override
            public void onFailure(Call<JsBankAuthApi> call, Throwable t) {
                Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });


    }

    //todo: 2nd APi for JsBank Payment
    public void jsPaymentInquiryApi(String mobileNo) {

        mobileno = mobileNo;
        getRandomNumberString();

        pkgPrice = "Global.selectedPackage.getPrice()";

        Global.utils.hideKeyboard(Global.applicationContext);
        Global.utils.showCustomLoadingDialog(Global.applicationContext);
        try {
            CallingApi(Global.newToken);
        } catch (Exception ex) {
        }
    }

    private void CallingApi(String newToken) {

        JsReqModel reqModel = new JsReqModel();

        JsonObject Mainparams = new JsonObject();
        JsonObject objectParams = new JsonObject();
        objectParams.addProperty("processingCode", "DebitInquiry");
        objectParams.addProperty("merchantType", "0088");
        objectParams.addProperty("traceNo", getRandomNumberString());
        objectParams.addProperty("terminalId", "IBCC");
        objectParams.addProperty("dateTime", getCurrentlyDateTime());
        objectParams.addProperty("mobileNo", "03209527097");
        objectParams.addProperty("companyName", "IBCC");
        objectParams.addProperty("channelId", "IBCC");
        objectParams.addProperty("productId", "10245249");
        objectParams.addProperty("pinType", "02");
        objectParams.addProperty("transactionAmount", "200.0");
        objectParams.addProperty("reserved1", "");
        objectParams.addProperty("reserved2", "");
        objectParams.addProperty("reserved3", "");
        objectParams.addProperty("reserved4", "");
        objectParams.addProperty("reserved5", "");
        objectParams.addProperty("reserved6", "");
        objectParams.addProperty("reserved7", "");
        objectParams.addProperty("reserved8", "");
        objectParams.addProperty("reserved9", "");
        objectParams.addProperty("reserved10", "");
        Mainparams.add("DebitInqRequest", objectParams);


       //JsonObject reqModel = new JsonObject();
     /*   reqModel.setProcessingCode("DebitInquiry");
        reqModel.setMerchantType("0088");
        reqModel.setTraceNo(getRandomNumberString());
        reqModel.setCompanyName("IBCC");
        reqModel.setDateTime(getCurrentlyDateTime());
        reqModel.setMobileNo("03463564149");//mobileno);
        reqModel.setChannelId("IBCC");
        reqModel.setTerminalId("IBCC");
        reqModel.setProductId("10245249");
        reqModel.setPinType("02");
        reqModel.setTransactionAmount("200.0");
        reqModel.setReserved1("");
        reqModel.setReserved2("");
        reqModel.setReserved3("");
        reqModel.setReserved4("");
        reqModel.setReserved5("");
        reqModel.setReserved6("");
        reqModel.setReserved7("");
        reqModel.setReserved8("");
        reqModel.setReserved9("");
        reqModel.setReserved10("");
*/
      /*  AccountInquiryRequest jsreqmodel1 = new AccountInquiryRequest();
        jsreqmodel1.setJsReqModel(reqModel);*/

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient();
        client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("https://sandbox.jsbl.com/")
                .baseUrl("https://sandbox.jsbl.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) // Set HttpClient to be used by Retrofit
                .build();

        String token = "Bearer " + newToken;
        jsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);
        Call<JsDebitInqueryResponseModel> call1 = jsonPlaceHolderApi.addToPlaylist(token, Mainparams);

        try {
            call1.enqueue(new Callback<JsDebitInqueryResponseModel>() {
                @Override
                public void onResponse(Call<JsDebitInqueryResponseModel> call, Response<JsDebitInqueryResponseModel> response) {


                    if (!response.isSuccessful()) {
                        Toast.makeText(Global.applicationContext, response.body().toString(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    JsDebitInqueryResponseModel debitInquiryResult = response.body();
                    Global.paymentInquiry = debitInquiryResult;

                    MLD_JsPaymentInquiryApi.postValue(debitInquiryResult);
                }

                @Override
                public void onFailure(Call<JsDebitInqueryResponseModel> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            });
        } catch (Exception ex) {
        }
    }

    public String getCurrentlyDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date());

    }
}
