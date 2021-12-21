package com.webdoc.ibcc.Payment.PaymentMethods.OTCPayment.ViewModel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.Pyament.RequestModel.EquilanceRequestModel;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Payment.PaymentMethods.OTCPayment.RequestModel.OtcRequestModel;
import com.webdoc.ibcc.Payment.PaymentMethods.OTCPayment.ResponseModels.OtcPaymentResponse;
import com.webdoc.ibcc.Payment.RequestModel.savePaymentInfoRequestModel;
import com.webdoc.ibcc.ResponseModels.SavePaymentInfo.SavePaymentInfo;
import com.webdoc.ibcc.Retrofit.jsonPlaceHolderApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OtcViewModel extends AndroidViewModel {

    public static int sixDigitNumber;

    //todo:Mutable Live Data
    private final MutableLiveData<SavePaymentInfo> MLD_save_payment_info_from_equivalance = new MutableLiveData<>();
    private final MutableLiveData<SavePaymentInfo> MLD_save_payment_info = new MutableLiveData<>();
    private final MutableLiveData<OtcPaymentResponse> MLD_otcPAymentApiCalling = new MutableLiveData<>();

    //todo:Livedata
    public LiveData<SavePaymentInfo> LD_savePaymentInfo_from_attestation() {
        return MLD_save_payment_info;
    }

    public LiveData<SavePaymentInfo> LD_savePaymentInfoEquialance() {
        return MLD_save_payment_info_from_equivalance;
    }

    public LiveData<OtcPaymentResponse> LD_btn_next_click() {
        return MLD_otcPAymentApiCalling;
    }

    //todo:Constructor
    public OtcViewModel(@NonNull Application application) {
        super(application);
    }

    //todo:methods
    public void otcPAymentApiCalling(String mobilenumber, String email) {


        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDateandTimeNew = sdf2.format(new Date());
        Global.eqDateTime = currentDateandTimeNew;

        //todo: adding 1 day in current code
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd HHmmss", Locale.getDefault());
        String currentDateandTime = sdf1.format(new Date());
        //Global.eqDateTime = currentDateandTime;

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HHmmss");
        Date myDate = null;
        try {
            myDate = format.parse(currentDateandTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        myDate = addDays(myDate, 2);
        String newDate = sdf1.format(myDate);  //todo:new date with one day + <<<<

        String orderId = getRandomNumberString();
        Global.order_id = orderId;
        String storeId = "86961";
        String transactionAmount = Global.price;
        String transactionType = "OTC";
        String msisdn = mobilenumber;
        String emailAddress = email;
        String tokenExpiry = newDate;

        OTC_easyPaisaRetrofit2Api(orderId, storeId, transactionAmount, transactionType, msisdn, emailAddress, tokenExpiry);
    }

    private void OTC_easyPaisaRetrofit2Api(String orderId, String storeId, String transactionAmount, String transactionType, String mobileAccountNo, String emailAddress, String tokenExpiry) {

        OtcRequestModel requestModel = new OtcRequestModel();
        requestModel.setOrderId(orderId);
        requestModel.setStoreId(storeId);
        requestModel.setTransactionAmount(transactionAmount);
        requestModel.setmsisdn(mobileAccountNo);
        requestModel.setEmailAddress(emailAddress);
        requestModel.setTransactionType(transactionType);
        requestModel.setTokenExpiry(tokenExpiry);

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
                .baseUrl("https://easypay.easypaisa.com.pk/easypay-service/rest/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) // Set HttpClient to be used by Retrofit
                .build();

        jsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);
        Call<OtcPaymentResponse> call1 = jsonPlaceHolderApi.createOTCpayment(requestModel);
        call1.enqueue(new Callback<OtcPaymentResponse>() {
            @Override
            public void onResponse(Call<OtcPaymentResponse> call, Response<OtcPaymentResponse> response) {

                OtcPaymentResponse pAymentResponse = response.body();
                Global.otcPaymentResponse = pAymentResponse;
                if (!response.isSuccessful()) {
                    Toast.makeText(Global.applicationContext, response.body().toString(), Toast.LENGTH_SHORT).show();
                    return;
                }

                MLD_otcPAymentApiCalling.postValue(pAymentResponse);


            }

            @Override
            public void onFailure(Call<OtcPaymentResponse> call, Throwable t) {
                Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    public void CallingSavePaymentApiOnEasyPaisaSuccess(String order_id, String IBCC_amount, String webdoc_amount, String case_id, String amount_paid, String bank, String account_number, String mobile_number, String transection_type, String transaction_reference_number, String transaction_datetime, String user_id, String status, String bankCharges, String courier_amount, String platform) {

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
        requestModelSavePayment.setStatus(status);
        requestModelSavePayment.setOrder_id(order_id);
        requestModelSavePayment.setCourier_amount(courier_amount);
        requestModelSavePayment.setPlatform(platform);
        requestModelSavePayment.setBank_charges(bankCharges);

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
                Toast.makeText(Global.applicationContext, "onFailure called in save payment api ", Toast.LENGTH_SHORT).show();
                Log.i("kh", t.getMessage());
                call.cancel();
            }
        });


    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        sixDigitNumber = rnd.nextInt(999999);
        // this will convert any number sequence into 6 character.
        return String.format("%06d", sixDigitNumber);
    }

    public void callingSavePaymentForEquilance(String order_id, String case_id, String amount_paid, String bank, String account_number, String mobile_number, String transection_type, String transaction_reference_number, String transaction_datetime, String user_id, String status, String webdoc_amount, String IBCC_amount, String bank_charges, String courier_amount, String platform) {

        EquilanceRequestModel requestModel = new EquilanceRequestModel();
        requestModel.setCase_id(case_id);
        requestModel.setAmount_paid(amount_paid);
        requestModel.setBank(bank);
        requestModel.setAccount_number(account_number);
        requestModel.setMobile_number(mobile_number);
        requestModel.setTransection_type(transection_type);
        requestModel.setTransaction_reference_number(transaction_reference_number);
        requestModel.setTransaction_datetime(transaction_datetime);
        if (Global.isIncompleteAppointmentEQ) {
            requestModel.setUser_id(Global.UserIdForCase);
        } else {
            requestModel.setUser_id(String.valueOf(Global.equivalenceInitiateCaseResponse.getResult().getIntiateCaseResponseDetails().getCustomerId()));
        }
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
}
