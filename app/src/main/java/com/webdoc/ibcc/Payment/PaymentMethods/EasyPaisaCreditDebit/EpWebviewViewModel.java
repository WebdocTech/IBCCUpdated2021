package com.webdoc.ibcc.Payment.PaymentMethods.EasyPaisaCreditDebit;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.Pyament.RequestModel.EquilanceRequestModel;
import com.webdoc.ibcc.Essentails.Global;
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

public class EpWebviewViewModel extends AndroidViewModel {
    WebView wb;
    private final MutableLiveData<String> MLD_open_webview = new MutableLiveData<>();

    private final MutableLiveData<SavePaymentInfo> MLD_save_payment_info_from_equivalance = new MutableLiveData<>();

    private final MutableLiveData<SavePaymentInfo> MLD_save_payment_info = new MutableLiveData<>();

    //todo: Live Data
    public LiveData<String> LD_open_webview() {
        return MLD_open_webview;
    }

    public LiveData<SavePaymentInfo> LD_savePaymentInfoEquialance() {
        return MLD_save_payment_info_from_equivalance;
    }

    public LiveData<SavePaymentInfo> LD_savePaymentInfo() {
        return MLD_save_payment_info;
    }

    public EpWebviewViewModel(@NonNull Application application) {
        super(application);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    public void openEasyPaisacreditDebitWebView(WebView webView, String finalUrl) {

        this.wb = webView;
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.getSettings().setUseWideViewPort(true);
        wb.getSettings().setDomStorageEnabled(true);
        wb.getSettings().setBuiltInZoomControls(true);
        wb.getSettings().setPluginState(WebSettings.PluginState.ON);
        wb.setWebViewClient(new EpWebviewViewModel.MyWebViewClient());
        wb.loadUrl(finalUrl);
        Log.i("finalurl", finalUrl);

        wb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                Log.d("WebView", "your current url when webpage loading.." + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                Log.d("WebView", "your current url" + url);
                if (url.contains("AUTH_TXN")) {
                    //todo: payment success
                    MLD_open_webview.postValue("Payment Succesfull");
                } else if (url.contains("status")) {
                    //todo: FAilure
                    MLD_open_webview.postValue("Payment Fail");
                }

                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onLoadResource(view, url);
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                System.out.println("when you click on any interlink on webview that time you got url :-" + url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

    }

    public void callingSavePaymentForEquilance(String case_id, String amount_paid, String bank, String account_number, String mobile_number, String transection_type, String transaction_reference_number, String transaction_datetime, String user_id, String status, String webdoc_amount, String IBCC_amount, String courierAmount, String bankCharges, String order_id, String platform) {

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
        requestModel.setStatus(status);
        requestModel.setWebdoc_amount(webdoc_amount);
        requestModel.setIbcC_amount(IBCC_amount);
        requestModel.setCourier_amount(courierAmount);
        requestModel.setBank_charges(bankCharges);
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
                call.cancel();
            }
        });


    }

    public void CallingSavePaymentApiOnEasyPaisaSuccess(String IBCC_amount, String webdoc_amount, String case_id, String amount_paid, String bank, String account_number, String mobile_number, String transection_type, String transaction_reference_number, String transaction_datetime, String user_id, String status, String transactionReferenceNumber, String bankCharges, String courierAmount, String platform) {

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
        requestModelSavePayment.setBank_charges(bankCharges);
        requestModelSavePayment.setPlatform(platform);
        requestModelSavePayment.setCourier_amount(courierAmount);
        requestModelSavePayment.setOrder_id(transaction_reference_number);

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
