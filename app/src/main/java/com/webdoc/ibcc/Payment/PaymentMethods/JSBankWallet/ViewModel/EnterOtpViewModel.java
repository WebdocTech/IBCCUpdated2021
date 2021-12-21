package com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ViewModel;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.Pyament.RequestModel.EquilanceRequestModel;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.RequestModel.JSthirdApiObjectModel;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.RequestModel.JsThirdAPiReqModel;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ResponseModel.jsDebitPaymentResponse.JsDebitPaymentResponse;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.jsDebitPaymentModel.JsDebitPaymentResponseModel;
import com.webdoc.ibcc.Payment.RequestModel.savePaymentInfoRequestModel;
import com.webdoc.ibcc.ResponseModels.SavePaymentInfo.SavePaymentInfo;
import com.webdoc.ibcc.Retrofit.jsonPlaceHolderApi;

import org.json.JSONException;
import org.json.JSONObject;

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

public class EnterOtpViewModel extends AndroidViewModel {

    public static int sixDigitNumber;

    private final MutableLiveData<SavePaymentInfo> MLD_save_payment_info = new MutableLiveData<>();

    private final MutableLiveData<JsDebitPaymentResponseModel> MLD_JsPaymentFinal = new MutableLiveData<>();

    private final MutableLiveData<SavePaymentInfo> MLD_save_payment_info_from_equivalance = new MutableLiveData<>();

    public LiveData<SavePaymentInfo> LD_savePaymentInfo() {
        return MLD_save_payment_info;
    }


    public LiveData<JsDebitPaymentResponseModel> LD_JsPaymentFinal() {
        return MLD_JsPaymentFinal;
    }

    //todo: Live Data
    public LiveData<SavePaymentInfo> LD_savePaymentInfoEquialance() {
        return MLD_save_payment_info_from_equivalance;
    }

    public EnterOtpViewModel(@NonNull Application application) {
        super(application);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void OnPayNowClick(String pinCode) {

        Global.utils.showCustomLoadingDialog(Global.applicationContext);

        String encryptedPin = Global.utils.enccriptData(pinCode);

        JSONObject params1 = new JSONObject();
        JSONObject params = new JSONObject();

        String processingCode = "DebitPayment";
        String merchantType = "0088";
        String traceNo = getRandomNumberString();
        String companyName = "IBCC";
        String dateTime = getCurrentlyDateTime().toString();
        //String mobileNo = Global.JS_Wallet_Account_Number;
        String mobileNo = "03463564149";
        String channelId = "IBCC";
        String terminalId = "IBCC";
        String productId = "10245249";
        String otpPin = encryptedPin;
        String pinType = "02";
        //String transactionAmount = Global.price;
        String transactionAmount = "100.0";
        String Reserved1 = "";
        String Reserved2 = "";
        String Reserved3 = "";
        String Reserved4 = "";
        String Reserved5 = "";
        String Reserved6 = "";
        String Reserved7 = "";
        String Reserved8 = "";
        String Reserved9 = "";
        String Reserved10 = "";

        try {
            params1.put("PaymentRequest", params);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        callingJsFinalApi(Global.newToken, encryptedPin, processingCode, merchantType, traceNo,
                companyName, dateTime, mobileNo, channelId,
                terminalId, productId, otpPin, pinType, transactionAmount,
                Reserved1, Reserved2, Reserved3, Reserved4, Reserved5, Reserved6,
                Reserved7, Reserved8, Reserved9, Reserved10);
    }

    private void callingJsFinalApi(String newToken, String encryptedPin, String processingCode, String merchantType, String traceNo, String companyName, String dateTime, String mobileNo, String channelId, String terminalId, String productId, String otpPin, String pinType, String transactionAmount, String reserved1, String reserved2, String reserved3, String reserved4, String reserved5, String reserved6, String reserved7, String reserved8, String reserved9, String reserved10) {


        JsonObject Mainparams = new JsonObject();
        JsonObject objectParams = new JsonObject();
        objectParams.addProperty("processingCode", "DebitPayment");
        objectParams.addProperty("merchantType", "0088");
        objectParams.addProperty("traceNo", getRandomNumberString());
        objectParams.addProperty("terminalId", "IBCC");
        objectParams.addProperty("dateTime", getCurrentlyDateTime());
        objectParams.addProperty("mobileNo", "03209527097");
        objectParams.addProperty("companyName", "IBCC");
        objectParams.addProperty("channelId", "IBCC");
        objectParams.addProperty("productId", "10245249");
        objectParams.addProperty("otpPin", otpPin);
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
        Mainparams.add("DebitPaymentRequest", objectParams);

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
                .baseUrl("https://sandbox.jsbl.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) // Set HttpClient to be used by Retrofit
                .build();
        String token = "Bearer " + newToken;
        jsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);
        Call<JsDebitPaymentResponseModel> call1 = jsonPlaceHolderApi.JsPaymentFinal(token, Mainparams);

        call1.enqueue(new Callback<JsDebitPaymentResponseModel>() {
            @Override
            public void onResponse(Call<JsDebitPaymentResponseModel> call, Response<JsDebitPaymentResponseModel> response) {


                if (!response.isSuccessful()) {
                    Toast.makeText(Global.applicationContext, response.body().toString(), Toast.LENGTH_SHORT).show();
                    return;
                }

                JsDebitPaymentResponseModel pAymentResponse = response.body();
                Global.jsPaymentFinal = pAymentResponse;

                MLD_JsPaymentFinal.postValue(pAymentResponse);
            }

            @Override
            public void onFailure(Call<JsDebitPaymentResponseModel> call, Throwable t) {
                Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });


    }


    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        sixDigitNumber = rnd.nextInt(999999);
        // this will convert any number sequence into 6 character.
        return String.format("%06d", sixDigitNumber);
    }

    public String getCurrentlyDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date());

    }

    public static long thirteenRandomDigits() {
        final Random r = new Random();
        return 1_000_000_000L * (r.nextInt(9_000) + 1_000)
                + r.nextInt(1_000_000_000);
    }

    public void callingSavePaymentForEquilance(String case_id, String amount_paid, String bank, String account_number, String mobile_number, String transection_type, String transaction_reference_number, String transaction_datetime, String user_id, String status, String webdoc_amount, String IBCC_amount, String orderID, String bankCharges, String courier_amount, String platform) {

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
        requestModel.setOrder_id(orderID);
        requestModel.setBank_charges(bankCharges);
        requestModel.setCourier_amount(courier_amount);
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

    public void CallingSavePaymentApiOnEasyPaisaSuccess(String IBCC_amount, String webdoc_amount, String case_id, String amount_paid, String bank, String account_number, String mobile_number, String transection_type, String transaction_reference_number, String transaction_datetime, String user_id, String transactionReferenceNumber, String bankCharges, String courier_amount, String platform) {

        savePaymentInfoRequestModel requestModelSavePayment = new savePaymentInfoRequestModel();
        requestModelSavePayment.setCase_id(case_id);
        requestModelSavePayment.setAmount_paid(amount_paid);
        requestModelSavePayment.setBank(bank);
        requestModelSavePayment.setAccount_number(account_number);
        requestModelSavePayment.setMobile_number(mobile_number);
        requestModelSavePayment.setTransection_type(transection_type);
        requestModelSavePayment.setTransaction_reference_number(transaction_reference_number);
        requestModelSavePayment.setTransaction_datetime(transaction_datetime);
        requestModelSavePayment.setUser_id(user_id);
        requestModelSavePayment.setIBCC_amount(IBCC_amount);
        requestModelSavePayment.setWebdoc_amount(webdoc_amount);
        requestModelSavePayment.setStatus("Success");
        requestModelSavePayment.setOrder_id(transaction_reference_number);
        requestModelSavePayment.setBank_charges(bankCharges);
        requestModelSavePayment.setCourier_amount(courier_amount);
        requestModelSavePayment.setPlatform(platform);

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
                .baseUrl("https://ibcc.webddocsystems.com/api/Steps/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) // Set HttpClient to be used by Retrofit
                .build();

        jsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);
        Call<SavePaymentInfo> call1 = jsonPlaceHolderApi.savePaymentInfo(requestModelSavePayment);
        call1.enqueue(new Callback<SavePaymentInfo>() {
            @Override
            public void onResponse(Call<SavePaymentInfo> call, Response<SavePaymentInfo> response) {

                SavePaymentInfo savePaymentInfo = response.body();

                if (!response.isSuccessful()) {
                    Toast.makeText(Global.applicationContext, response.body().toString(), Toast.LENGTH_SHORT).show();
                    return;
                }

                MLD_save_payment_info.postValue(savePaymentInfo);
            }

            @Override
            public void onFailure(Call<SavePaymentInfo> call, Throwable t) {
                Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });


    }


}

