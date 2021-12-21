package com.webdoc.ibcc.Payment.PaymentMethods.EasyPaisa.ViewModel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.Pyament.RequestModel.EquilanceRequestModel;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Payment.PaymentMethods.EasyPaisa.EasyPaisaActivity;
import com.webdoc.ibcc.Payment.PaymentMethods.EasyPaisa.RequestModel.EasyPaisaRequestModel;
import com.webdoc.ibcc.Payment.PaymentMethods.EasyPaisa.ResoponseModel.easypaisaPAymentResponse.EasypaisaPAymentResponse;
import com.webdoc.ibcc.Payment.RequestModel.savePaymentInfoRequestModel;
import com.webdoc.ibcc.ResponseModels.SavePaymentInfo.SavePaymentInfo;
import com.webdoc.ibcc.Retrofit.jsonPlaceHolderApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.webdoc.ibcc.Payment.PaymentMethods.OTCPayment.ViewModel.OtcViewModel.getRandomNumberString;

public class EasyPaisaViewModel extends AndroidViewModel {

    public static int sixDigitNumber;

    //todo: Mutable Live Data
    private final MutableLiveData<EasypaisaPAymentResponse> MLD_btn_nextClick = new MutableLiveData<>();
    private final MutableLiveData<String> MLD_show_reciept = new MutableLiveData<>();
    private final MutableLiveData<SavePaymentInfo> MLD_save_payment_info = new MutableLiveData<>();
    private final MutableLiveData<SavePaymentInfo> MLD_save_payment_info_from_equivalance = new MutableLiveData<>();

    //todo: Live Data
    public LiveData<SavePaymentInfo> LD_savePaymentInfoEquialance() {
        return MLD_save_payment_info_from_equivalance;
    }

    public LiveData<EasypaisaPAymentResponse> LD_btn_next_click() {
        return MLD_btn_nextClick;
    }

    public LiveData<String> LD_showRecptResponse() {
        return MLD_show_reciept;
    }

    public LiveData<SavePaymentInfo> LD_savePaymentInfo() {
        return MLD_save_payment_info;
    }

    //todo constructor
    public EasyPaisaViewModel(@NonNull Application application) {
        super(application);
    }

    //todo: Methods
    public void btnNextClick(String mobilenumber, String email) {

        String orderId = getRandomNumberString();
        Global.order_id = orderId;
        String storeId = "86961";
        //String transactionAmount = String.valueOf(Global.price);
        String transactionAmount = String.valueOf(1);
        String transactionType = "MA";
        String mobileAccountNo = mobilenumber;
        String emailAddress = email;

        easyPaisaRetrofit2Api(orderId, storeId, transactionAmount, transactionType, mobileAccountNo, emailAddress);
    }

    private void easyPaisaRetrofit2Api(String orderId, String storeId, String transactionAmount, String transactionType, String mobileAccountNo, String emailAddress) {

        Global.utils.showCustomLoadingDialog(Global.applicationContext);

        EasyPaisaRequestModel requestModel = new EasyPaisaRequestModel();
        requestModel.setOrderId(orderId);
        requestModel.setStoreId(storeId);
        requestModel.setTransactionAmount(transactionAmount);
        requestModel.setMobileAccountNo(mobileAccountNo);
        requestModel.setEmailAddress(emailAddress);
        requestModel.setTransactionType(transactionType);

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
        Call<EasypaisaPAymentResponse> call1 = jsonPlaceHolderApi.createpayment(requestModel);

        call1.enqueue(new Callback<EasypaisaPAymentResponse>() {
            @Override
            public void onResponse(Call<EasypaisaPAymentResponse> call, Response<EasypaisaPAymentResponse> response) {
                Global.utils.hideCustomLoadingDialog();

                EasypaisaPAymentResponse pAymentResponse = response.body();
                Global.easypaisaPAymentResponse = pAymentResponse;
                if (!response.isSuccessful()) {
                    Toast.makeText(Global.applicationContext, response.body().toString(), Toast.LENGTH_SHORT).show();
                    return;
                }

                MLD_btn_nextClick.postValue(pAymentResponse);


            }

            @Override
            public void onFailure(Call<EasypaisaPAymentResponse> call, Throwable t) {
                Global.utils.hideCustomLoadingDialog();
                Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    public void CallingSavePaymentApiOnEasyPaisaSuccess(String IBCC_amount, String webdoc_amount, String case_id, String amount_paid, String bank, String account_number, String mobile_number, String transection_type, String transaction_reference_number, String transaction_datetime, String user_id, String bank_charges, String courier_amount, String order_id, String platform) {

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
        requestModelSavePayment.setOrder_id(order_id);
        requestModelSavePayment.setBank_charges(bank_charges);
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

    public void callingSavePaymentForEquilance(String case_id, String amount_paid, String bank, String account_number, String mobile_number, String transection_type, String transaction_reference_number, String transaction_datetime, String user_id, String status, String webdoc_amount, String IBCC_amount, String bank_charges, String courier_amount, String order_id, String platform) {

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

}

