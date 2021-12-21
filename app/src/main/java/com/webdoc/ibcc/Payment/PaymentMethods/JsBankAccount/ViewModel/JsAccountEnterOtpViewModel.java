package com.webdoc.ibcc.Payment.PaymentMethods.JsBankAccount.ViewModel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Payment.PaymentMethods.JsBankAccount.RequestModel.JsAccountSecondApiReqModel;
import com.webdoc.ibcc.Payment.PaymentMethods.JsBankAccount.ResponseModels.SecondApiJSAccountResponse.SecondApiJSAccountResponse;
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

public class JsAccountEnterOtpViewModel extends AndroidViewModel {

    private static int sixDigitNumber;


    private final MutableLiveData<SecondApiJSAccountResponse> MLDsecondApi = new MutableLiveData();

    public LiveData<SecondApiJSAccountResponse> LD_SecondApi() {
        return MLDsecondApi;
    }


    public JsAccountEnterOtpViewModel(@NonNull Application application) {
        super(application);
    }

    public void OnPayNowClick(String pinCode) {

        // Global.utils.showCustomLoadingDialog(Global.applicationContext);

        String encryptedPin = Global.utils.enccriptData(pinCode);
        getRandomNumberString();
        String mti = "0200";
        String ProcessingCode = "916000";
        String TransmissionDatetime = GettransmisionDateTime();
        String SystemsTraceAuditNumber = String.valueOf(sixDigitNumber);
        String TimeLocalTransaction = GetTimeLocalTransaction();
        String DateLocalTransaction = GetDateLocalTransaction();
        String MerchantType = "0111";
        String TransactionType = "916000";
        String CNIC = "5440077719751";
        String AccountIdentification1 = "0001510969";/*encryptedPin*/
        String TransactionDescription = "GetVal." + GetTransactionDescription() + SystemsTraceAuditNumber;
        String PersonalIdentificationNumberData = "4321";

        callingJsAccountSecondApi(mti, ProcessingCode, TransmissionDatetime, SystemsTraceAuditNumber, TimeLocalTransaction,
                DateLocalTransaction, MerchantType, TransactionType, CNIC,
                AccountIdentification1, TransactionDescription, PersonalIdentificationNumberData);
    }


    private void callingJsAccountSecondApi(String mti, String processingCode, String transmissionDatetime, String systemsTraceAuditNumber, String timeLocalTransaction, String dateLocalTransaction, String merchantType, String transactionType, String CNIC, String accountIdentification1, String transactionDescription, String personalIdentificationNumberData) {

        JsAccountSecondApiReqModel reqModel = new JsAccountSecondApiReqModel();
        reqModel.setMti(mti);
        reqModel.setProcessingCode(processingCode);
        reqModel.setTransmissionDatetime(transmissionDatetime);
        reqModel.setSystemsTraceAuditNumber(systemsTraceAuditNumber);
        reqModel.setTimeLocalTransaction(timeLocalTransaction);
        reqModel.setDateLocalTransaction(dateLocalTransaction);
        reqModel.setMerchantType(merchantType);
        reqModel.setTransactionType(transactionType);
        reqModel.setCNIC(CNIC);
        reqModel.setAccountIdentification1(accountIdentification1);
        reqModel.setTransactionDescription(transactionDescription);
        reqModel.setPersonalIdentificationNumberData(personalIdentificationNumberData);

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

        jsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);
        String token = "1@#$%^9";
        Call<SecondApiJSAccountResponse> call1 = jsonPlaceHolderApi.jsAccountSecondAPi(token, "application/json", reqModel);

        call1.enqueue(new Callback<SecondApiJSAccountResponse>() {
            @Override
            public void onResponse(Call<SecondApiJSAccountResponse> call, Response<SecondApiJSAccountResponse> response) {


                if (!response.isSuccessful()) {
                    Toast.makeText(Global.applicationContext, response.body().toString(), Toast.LENGTH_SHORT).show();
                    return;
                }

                SecondApiJSAccountResponse pAymentResponse = response.body();
                Global.secondApiJSAccountResponse = pAymentResponse;

                MLDsecondApi.postValue(pAymentResponse);
            }

            @Override
            public void onFailure(Call<SecondApiJSAccountResponse> call, Throwable t) {
                Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });


    }


    private String GetDateLocalTransaction() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMdd");
        return dateFormat.format(new Date());

    }

    private String GetTimeLocalTransaction() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HHMMSS");
        return dateFormat.format(new Date());

    }

    private String GettransmisionDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHMMSS");
        return dateFormat.format(new Date());

    }

    private String GetTransactionDescription() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYHHMMSS");
        return dateFormat.format(new Date());
    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        sixDigitNumber = rnd.nextInt(999999);
        // this will convert any number sequence into 6 character.
        return String.format("%06d", sixDigitNumber);
    }

}
