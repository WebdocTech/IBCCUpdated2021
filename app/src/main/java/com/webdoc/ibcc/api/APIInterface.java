package com.webdoc.ibcc.api;

import com.google.gson.JsonObject;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.DocumentSelection.apimodels.DocumentsModel;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.DetailsEquivalenceNewModel;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.ReassignedCaseDetailsModels.ReassignedCaseDetailsModel;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.ReassignedCaseModel;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.editReassignCaseModels.EditReassignCaseModel;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.submitReassignCaseModels.SubmitReassignCaseModel;
import com.webdoc.ibcc.Payment.PaymentMethods.StripePayment.responseModel.DollerRateResponseModel;
import com.webdoc.ibcc.ResponseModels.AddDocumentDetailsResult.AddDocumentDetailsResult;
import com.webdoc.ibcc.ResponseModels.AddEducationResult.AddEducationResult;
import com.webdoc.ibcc.ResponseModels.AddQualificationEQ.AddQualificationEQ;
import com.webdoc.ibcc.ResponseModels.CancelAppointmentResult.CancelAppointmentResult;
import com.webdoc.ibcc.ResponseModels.CheckRegistrationResult.CheckRegistrationResult;
import com.webdoc.ibcc.ResponseModels.DeleteEducationResult.DeleteEducationResult;
import com.webdoc.ibcc.ResponseModels.EditEducationResult.EditEducationResult;
import com.webdoc.ibcc.ResponseModels.GetAreasByCity.GetAreasByCity;
import com.webdoc.ibcc.ResponseModels.GetCityListByService.GetCityListByService;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.GetDetailsEquivalence;
import com.webdoc.ibcc.ResponseModels.IncompleteDetailsEQ.IncompleteDetailsEQ;
import com.webdoc.ibcc.ResponseModels.IntiateCase.IntiateCase;
import com.webdoc.ibcc.ResponseModels.PdfResult.PdfResult;
import com.webdoc.ibcc.ResponseModels.RemoveQualificationEQ.RemoveQualificationEQ;
import com.webdoc.ibcc.ResponseModels.SaveBooking.SaveBooking;
import com.webdoc.ibcc.ResponseModels.SaveCourierDetials.SaveCourierDetials;
import com.webdoc.ibcc.ResponseModels.SaveCourierDetialsEQ.SaveCourierDetialsEQ;
import com.webdoc.ibcc.ResponseModels.SaveDocumentDetailsEQ.SaveDocumentDetailsEQ;
import com.webdoc.ibcc.ResponseModels.Step1Result.Step1Result;
import com.webdoc.ibcc.ResponseModels.UpdateProfileResult.UpdateProfileResult;
import com.webdoc.ibcc.ResponseModels.UserLoginResult.UserLoginResult;
import com.webdoc.ibcc.ResponseModels.UserRegisterResult.UserRegisterResult;
import com.webdoc.ibcc.ResponseModels.ViewAppointmentsEQ.ViewAppointmentsEQ;
import com.webdoc.ibcc.ResponseModels.ViewDetailsResult.ViewDetailsResult;
import com.webdoc.ibcc.ResponseModels.ViewIncompleteAppointmentEQ.ViewIncompleteAppointmentEQ;
import com.webdoc.ibcc.ResponseModels.ViewIncompleteDetailsResult.ViewIncompleteDetails;
import com.webdoc.ibcc.ResponseModels.ViewIncompleteResult.ViewIncompleteResult;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("Pdf")
    Call<PdfResult> callPDF();

    @POST("api/latest.json?app_id=fb516545580f47859cadb37203688a08")
    Call<DollerRateResponseModel> callDollorRate();

    @POST("Equivalence/GetDetailsEquivalence")
    Call<GetDetailsEquivalence> callDetailsEquivalence(@Body JsonObject mojb);

    @POST("Equivalence/GetDetailsEquivalenceNew")
    Call<DetailsEquivalenceNewModel> callDetailsEquivalenceNew(@Body JsonObject mojb);

    @POST("Login/UserLogin")
    Call<UserLoginResult> callUserLogin(@Body JsonObject mojb);

    @POST("Login/CheckRegistration")
    Call<CheckRegistrationResult> callCheckUser(@Body JsonObject mojb);

    @POST("Login/UserRegister")
    Call<UserRegisterResult> callUserRegistration(@Body JsonObject mojb);

    @POST("Login/UpdateProfile")
    Call<UpdateProfileResult> callUpdateProfile(@Body JsonObject mojb);

    @POST("Steps/ViewIncomplete")
    Call<ViewIncompleteResult> callViewIncomplete(@Body JsonObject mojb);

    @POST("Equivalence/ViewIncompleteAppointment")
    Call<ViewIncompleteAppointmentEQ> callViewIncompleteEQ(@Body JsonObject mojb);

    @POST("Steps/CancelAppointment")
    Call<CancelAppointmentResult> callCancelAppointment(@Body JsonObject mojb);

    @POST("Steps/ViewIncompleteDetails")
    Call<ViewIncompleteDetails> callViewIncompleteDetails(@Body JsonObject mojb);

    @POST("Steps/ViewDetails")
    Call<ViewDetailsResult> callViewDetails(@Body JsonObject mojb);

    @POST("Equivalence/IncompleteDetailsEQ")
    Call<IncompleteDetailsEQ> callViewIncompleteDetailsEQ(@Body JsonObject mojb);

    @POST("Equivalence/ViewAppointmentsEQ")
    Call<ViewAppointmentsEQ> callViewAppointmentEQ(@Body JsonObject mojb);

    @POST("Equivalence/SaveDocumentDetails")
    Call<SaveDocumentDetailsEQ> callSaveDocumentsDetails(@Body DocumentsModel mojb);

    @POST("Equivalence/RemoveQualification")
    Call<RemoveQualificationEQ> callRemoveQualification(@Body JsonObject mojb);

    @POST("Equivalence/IntiateCase")
    Call<IntiateCase> callEquivalenceIntiateCase(@Body JsonObject mojb);

    @POST("Equivalence/SaveCourierDetialsEQ")
    Call<SaveCourierDetialsEQ> callSaveCourierDetailsEQ(@Body JsonObject mojb);

    @POST("Steps/SaveCourierDetials")
    Call<SaveCourierDetials> callSaveCourierDetails(@Body JsonObject mojb);

    @POST("Steps/AddDocumentDetails")
    Call<AddDocumentDetailsResult> callAddDocumentDetails(@Body JSONObject mojb);

    @POST("Steps/EditEducation")
    Call<EditEducationResult> callEditEducation(@Body JsonObject mojb);

    @POST("Steps/DeleteEducation")
    Call<DeleteEducationResult> callDeleteEducation(@Body JsonObject mojb);

    @POST("Steps/AddEducation")
    Call<AddEducationResult> callAddEducation(@Body JsonObject mojb);

    @POST("Steps/Step1")
    Call<Step1Result> callStep1(@Body JsonObject mojb);

    //New Apis:
    @POST("Equivalence/ReassignedCases")
    Call<ReassignedCaseModel> callReassignedCassesApi(@Body JsonObject mojb);

    @POST("Equivalence/ReassignedCaseDetails")
    Call<ReassignedCaseDetailsModel> callReassignedCaseDetailsApi(@Body JsonObject mojb);

    @POST("Equivalence/EditQualificationEQ")
    Call<EditReassignCaseModel> callReassignedCaseEditApi(@Body JsonObject mojb);

    @POST("Equivalence/ReassignedCaseEdit")
    Call<SubmitReassignCaseModel> callReassignedCaseSubmitApi(@Body JsonObject mojb);

    @POST("Equivalence/AddQualificationEQ")
    Call<AddQualificationEQ> callEquivalenceAddQualificationApi(@Body JsonObject jsonObject);

    /*CALL COURIER*/

    @GET("API/CallCourier/GetCityListByService?serviceID=1")
    Call<GetCityListByService> callCityListByService();

    @GET("api/CallCourier/SaveBooking?")
    Call<SaveBooking> callSaveBooking(String string);

    @GET("api/callcourier/GetAreasByCity")
    Call<GetAreasByCity> callGetAreasByCity(@Query("CityID=") String cityID);
}