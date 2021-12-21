package com.webdoc.ibcc.Payment.PaymentMethods.JazzCash.ViewModel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.Pyament.RequestModel.EquilanceRequestModel;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Payment.PaymentMethods.JazzCash.ResponseModels.JazzCashResponseNew;
import com.webdoc.ibcc.Payment.PaymentMethods.JazzCash.ResponseModels.jazzCashRequestModel;
import com.webdoc.ibcc.Payment.RequestModel.savePaymentInfoRequestModel;
import com.webdoc.ibcc.ResponseModels.SavePaymentInfo.SavePaymentInfo;
import com.webdoc.ibcc.Retrofit.jsonPlaceHolderApi;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JazzCashViewModel extends AndroidViewModel {

    String messageDigest;

    //todo:Mutable Live Data
    private final MutableLiveData<JazzCashResponseNew> MLD_callingJazzCashApi = new MutableLiveData<>();
    private final MutableLiveData<SavePaymentInfo> MLD_save_payment_info = new MutableLiveData<>();
    private final MutableLiveData<String> MLD_show_reciept = new MutableLiveData<>();
    private final MutableLiveData<SavePaymentInfo> MLD_save_payment_info_from_equivalance = new MutableLiveData<>();


    //todo: Live Data
    public LiveData<SavePaymentInfo> LD_savePaymentInfoEquialance() {
        return MLD_save_payment_info_from_equivalance;
    }

    public LiveData<JazzCashResponseNew> LD_callingJazzCash() {
        return MLD_callingJazzCashApi;
    }

    public LiveData<SavePaymentInfo> LD_savePaymentInfo() {
        return MLD_save_payment_info;
    }

    public LiveData<String> LD_showRecptResponse() {
        return MLD_show_reciept;
    }


    //todo : constructor
    public JazzCashViewModel(@NonNull Application application) {
        super(application);
    }


    //todo:Methods
    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0, v; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] hmac(String algorithm, byte[] key, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(message);
    }

    public void CallingJazzCashApi(String mobileNumber, String cnic) {

        String amount = String.valueOf(Global.price);
        String tempref = "T" + getCurrentlyDateTime();
        String SecurityKey = "s52suwc12f";
        String pp_Amount = amount + "00";
        String pp_BankID = "";
        String pp_BillReference = "billRef";
        String pp_CNIC = cnic;
        String pp_Description = "this is transaction";
        String pp_Language = "EN";
        String pp_MerchantID = "MC18403";
        String pp_MobileNumber = mobileNumber;
        String pp_Password = "4tx1430us8";
        String pp_ProductID = "";
        String pp_SubMerchantID = "";
        String pp_TxnCurrency = "PKR";
        String pp_TxnDateTime = getCurrentlyDateTime();
        String pp_TxnExpiryDateTime = getCurrentlyDateTimeNextday();
        String pp_TxnRefNo = tempref;
        String ppmpf_1 = "";
        String ppmpf_2 = "";
        String ppmpf_3 = "";
        String ppmpf_4 = "";
        String ppmpf_5 = "";
        String pp_DiscountedAmount = "";


        String Key = SecurityKey + "&" + pp_Amount + "&" + pp_BillReference + "&" + pp_CNIC + "&" + pp_Description
                + "&" + pp_Language + "&" + pp_MerchantID + "&" + pp_MobileNumber + "&" + pp_Password
                + "&" + pp_TxnCurrency + "&" + pp_TxnDateTime + "&" + pp_TxnExpiryDateTime + "&" + pp_TxnRefNo;


        try {
            generateHashWithHmac256(Key, SecurityKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String pp_SecureHash = messageDigest;

        jazzCashRetrofit2Api(pp_Language, pp_MerchantID, pp_SubMerchantID, pp_Password, pp_DiscountedAmount,
                pp_BankID, pp_ProductID, pp_TxnRefNo, pp_Amount, pp_TxnCurrency
                , pp_TxnDateTime, pp_BillReference, pp_Description,
                pp_TxnExpiryDateTime, pp_SecureHash, ppmpf_1, ppmpf_2, ppmpf_3, ppmpf_4, ppmpf_5, pp_MobileNumber, pp_CNIC);

    }

    public void CallingSavePaymentApiOnJazzCashSucess(String IBCC_amount, String webdoc_amount, String case_id, String amount_paid, String bank, String account_number, String mobile_number, String transection_type, String transaction_reference_number, String transaction_datetime, String user_id, String transactionReferenceNumber, String bankCharges, String courier_amount, String platform) {

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
        requestModelSavePayment.setOrder_id(transactionReferenceNumber);
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
                Log.i("resp", savePaymentInfo.getResult().getResponseCode());

                if (!response.isSuccessful()) {
                    Toast.makeText(Global.applicationContext, response.body().toString(), Toast.LENGTH_SHORT).show();
                    return;
                }

                MLD_save_payment_info.postValue(savePaymentInfo);

            }

            @Override
            public void onFailure(Call<SavePaymentInfo> call, Throwable t) {
                Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                Log.i("kh", t.getMessage());
                call.cancel();
            }
        });


    }

    public void callingSavePaymentForEquilance(String case_id, String amount_paid, String bank, String account_number, String mobile_number, String transection_type, String transaction_reference_number, String transaction_datetime, String user_id, String status, String webdoc_amount, String IBCC_amount, String transactionReferenceNumber, String bankCharges, String courier_amount, String platform) {

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
        requestModel.setOrder_id(transaction_reference_number);
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

    private void jazzCashRetrofit2Api(String pp_language, String pp_merchantID, String pp_subMerchantID, String pp_password, String pp_discountedAmount, String pp_bankID, String pp_productID, String pp_txnRefNo, String pp_amount, String pp_txnCurrency, String pp_txnDateTime, String pp_billReference, String pp_description, String pp_txnExpiryDateTime, String pp_secureHash, String ppmpf_1, String ppmpf_2, String ppmpf_3, String ppmpf_4, String ppmpf_5, String pp_mobileNumber, String pp_cnic) {

        jazzCashRequestModel requestModel = new jazzCashRequestModel();
        requestModel.setPp_Amount(pp_amount);
        requestModel.setPp_Language(pp_language);
        requestModel.setPp_MerchantID(pp_merchantID);
        requestModel.setPp_SubMerchantID(pp_subMerchantID);
        requestModel.setPp_Password(pp_password);
        requestModel.setPp_DiscountedAmount(pp_discountedAmount);
        requestModel.setPp_BankID(pp_bankID);
        requestModel.setPp_ProductID(pp_productID);
        requestModel.setPp_TxnRefNo(pp_txnRefNo);
        requestModel.setPp_Amount(pp_amount);
        requestModel.setPp_TxnCurrency(pp_txnCurrency);
        requestModel.setPp_TxnDateTime(pp_txnDateTime);
        requestModel.setPp_BillReference(pp_billReference);
        requestModel.setPp_Description(pp_description);
        requestModel.setPp_TxnExpiryDateTime(pp_txnExpiryDateTime);
        requestModel.setPp_SecureHash(pp_secureHash);
        requestModel.setPpmpf_1(ppmpf_1);
        requestModel.setPpmpf_2(ppmpf_2);
        requestModel.setPpmpf_3(ppmpf_3);
        requestModel.setPpmpf_4(ppmpf_4);
        requestModel.setPpmpf_5(ppmpf_5);
        requestModel.setPp_MobileNumber(pp_mobileNumber);
        requestModel.setPp_CNIC(pp_cnic);


        // final JazzCashResponseNew jazzCashResponse = new JazzCashResponseNew(pp_language, pp_merchantID, pp_subMerchantID, pp_password, pp_discountedAmount, pp_bankID, pp_productID, pp_txnRefNo, pp_amount, pp_txnCurrency, pp_txnDateTime, pp_billReference, pp_description, pp_txnExpiryDateTime, pp_secureHash, ppmpf_1, ppmpf_2, ppmpf_3, ppmpf_4, ppmpf_5, pp_mobileNumber, pp_cnic);

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
                .baseUrl("https://sandbox.jazzcash.com.pk/ApplicationAPI/API/2.0/Purchase/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) // Set HttpClient to be used by Retrofit
                .build();

        jsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);
        Call<JazzCashResponseNew> call1 = jsonPlaceHolderApi.createJazzCashPayment(requestModel);
        call1.enqueue(new Callback<JazzCashResponseNew>() {
            @Override
            public void onResponse(Call<JazzCashResponseNew> call, Response<JazzCashResponseNew> response) {

                JazzCashResponseNew pAymentResponse = response.body();
                Global.jazzCashResponseNew = pAymentResponse;

                if (!response.isSuccessful()) {
                    Toast.makeText(Global.applicationContext, response.body().toString(), Toast.LENGTH_SHORT).show();
                    return;
                }

                MLD_callingJazzCashApi.postValue(pAymentResponse);

            }

            @Override
            public void onFailure(Call<JazzCashResponseNew> call, Throwable t) {

                Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                Log.i("kh", t.getMessage());
                call.cancel();
            }
        });


    }

    public String getCurrentlyDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date());

    }

    public String getCurrentlyDateTimeNextday() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        String dt = getCurrentlyDateTime();

        Calendar c = Calendar.getInstance();
        try {
            c.setTime(dateFormat.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1);

        return dateFormat.format(c.getTime());

    }

    private void generateHashWithHmac256(String message, String key) {
        try {

            final String hashingAlgorithm = "HmacSHA256"; //or "HmacSHA1", "HmacSHA512"

            byte[] bytes = hmac(hashingAlgorithm, key.getBytes(), message.getBytes());

            messageDigest = bytesToHex(bytes);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

