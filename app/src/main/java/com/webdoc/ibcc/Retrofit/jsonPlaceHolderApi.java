package com.webdoc.ibcc.Retrofit;

import com.google.gson.JsonObject;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.Pyament.RequestModel.EquilanceRequestModel;
import com.webdoc.ibcc.Model.UpdatePaymentInfoRequestModel;
import com.webdoc.ibcc.Model.inquiryRequestModel;
import com.webdoc.ibcc.Payment.PaymentMethods.EasyPaisa.RequestModel.EasyPaisaRequestModel;
import com.webdoc.ibcc.Payment.PaymentMethods.EasyPaisa.ResoponseModel.easypaisaPAymentResponse.EasypaisaPAymentResponse;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.RequestModel.JsReqModel;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.RequestModel.JsThirdAPiReqModel;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ResponseModel.JsBankAuthApi;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ResponseModel.jsDebitInquiryResult.JsDebitInquiryResult;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ResponseModel.jsDebitPaymentResponse.JsDebitPaymentResponse;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.jsDebitPaymentModel.JsDebitPaymentResponseModel;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.jsbankdebitresponsemodel.JsDebitInqueryResponseModel;
import com.webdoc.ibcc.Payment.PaymentMethods.JazzCash.ResponseModels.JazzCashResponseNew;
import com.webdoc.ibcc.Payment.PaymentMethods.JazzCash.ResponseModels.jazzCashRequestModel;
import com.webdoc.ibcc.Payment.PaymentMethods.JsBankAccount.RequestModel.JsAccountSecondApiReqModel;
import com.webdoc.ibcc.Payment.PaymentMethods.JsBankAccount.ResponseModels.JsGenerateOtpResponse.JsGenerateOtpResponse;
import com.webdoc.ibcc.Payment.PaymentMethods.JsBankAccount.ResponseModels.SecondApiJSAccountResponse.SecondApiJSAccountResponse;
import com.webdoc.ibcc.Payment.PaymentMethods.OTCPayment.RequestModel.OtcRequestModel;
import com.webdoc.ibcc.Payment.PaymentMethods.OTCPayment.ResponseModels.OtcPaymentResponse;
import com.webdoc.ibcc.Payment.RequestModel.savePaymentInfoRequestModel;
import com.webdoc.ibcc.ResponseModels.SavePaymentInfo.SavePaymentInfo;
import com.webdoc.ibcc.ResponseModels.inquiryResult.InquiryResult;
import com.webdoc.ibcc.ResponseModels.phpfilesResponse.PhpfilesResponse;
import com.webdoc.ibcc.ResponseModels.updatePAymentInfoResult.UpdatePAymentInfoResult;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface jsonPlaceHolderApi {
    //todo : EasyPaisa Payment
    @Headers({
            "Credentials:SUJDQzo0ZjlhMmMyN2Q5MDMxZDBlNjRiOTlmOGE1NjZkMTRiZA=="
    })
    @POST("initiate-ma-transaction")
    Call<EasypaisaPAymentResponse> createpayment(@Body EasyPaisaRequestModel pAymentResponse);

    //todo: JazzCash Payment
    @POST("DoMWalletTransaction")
    Call<JazzCashResponseNew> createJazzCashPayment(@Body jazzCashRequestModel jazzCashResponseNew);


    // todo : save payment info
    @POST("AddPaymentInfo")
    Call<SavePaymentInfo> savePaymentInfo(@Body savePaymentInfoRequestModel savePaymentInfoRequestModel);

    //todo : save paymeny for equialance
    @POST("AddPaymentInfoEQ")
    Call<SavePaymentInfo> savePaymentInfoforEquialance(@Body EquilanceRequestModel requestModel);


    //todo:// easypaisa OTC payment
    @Headers({
            "Credentials:SUJDQzo0ZjlhMmMyN2Q5MDMxZDBlNjRiOTlmOGE1NjZkMTRiZA=="
    })
    @POST("initiate-otc-transaction")
    Call<OtcPaymentResponse> createOTCpayment(@Body OtcRequestModel otcRequestModel);


    @Headers({
            "Authorization:Basic dHlIWHBqNGFRSHNYVGkwczBFTlNBc0ZsSTVyU2c3Mnk6UEhVa2dmM0h3MWk0R3NhTg==",
            "Cookie:__cfduid=db120ff1902d91d7c034d3f8f1f91a9f11613366925"
    })
    @GET("esb/oauth-t24?grant_type=client_credentials")
    Call<JsBankAuthApi> jsAuthApi();


    /*  Call<PaymentInquiry> JsPaymentInquirt(JSONObject params1, @Header("Authorization") String token, @Header("Content-Type") String content);*/


    @POST("v2/debitinquiry2-blb2")
    Call<JsDebitInqueryResponseModel>   addToPlaylist(@Header("Authorization") String auth, @Body JsonObject jsReqModel);

    @POST("debitpayment-blb2")
    Call<JsDebitPaymentResponseModel> JsPaymentFinal(@Header("Authorization") String auth, @Body JsonObject object);

    //todo: JazzCash Payment
    @Headers({
            "Credentials:SUJDQzo0ZjlhMmMyN2Q5MDMxZDBlNjRiOTlmOGE1NjZkMTRiZA=="
    })
    @POST("inquire-transaction")
    Call<InquiryResult> inquiryresult(@Body inquiryRequestModel requestModel);


    @POST("UpdatePaymentInfoEQ")
    Call<UpdatePAymentInfoResult> UpdatePaymentInfoEQ(@Body UpdatePaymentInfoRequestModel requestModel);


    @POST("OTPGeneration")
    Call<JsGenerateOtpResponse> GenerateOtp(@Header("Authorization") String auth, @Header("Content-Type") String content_type, @Body RequestBody jsReqModel);

    @POST("OTPVerification")
    Call<SecondApiJSAccountResponse> jsAccountSecondAPi(@Header("Authorization") String auth, @Header("Content-Type") String content_type, @Body JsAccountSecondApiReqModel jsReqModel);

    @POST("upload_documentnew.php")
    Call<PhpfilesResponse> callImagesFormDataApi(@Body RequestBody requestBody);


    @POST("upload_documentary_evidence.php")
    Call<PhpfilesResponse> callImagesTravellingFormDataApi(@Body RequestBody requestBody);


}




