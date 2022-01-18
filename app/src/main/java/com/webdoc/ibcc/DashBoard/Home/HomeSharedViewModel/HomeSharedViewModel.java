package com.webdoc.ibcc.DashBoard.Home.HomeSharedViewModel;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.webdoc.ibcc.Adapter.EducationDetailsAdapter;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.AttestationReceipt;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.CallCourier.CallCourier;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.EducationDetails.EducationDetailsFragment;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.GenerateApp.GenerateAppFragment;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.ApplyEquivalenceActivity;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.CallCourier_EQ.CallCourier_EQ;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.DocumentSelection.DocDetailEQ_Model;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.DocumentSelection.apimodels.DocumentsModel;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.DocumentSelection.apimodels.SaveDocumentDetails;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.EquivalenceEducationDetailsFragment;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.Equivalence_Receipt;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.GenerateApp.EquivalenceGenerateAppFragment;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.DetailsEquivalenceNewModel;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.editReassignCaseModels.EditReassignCaseModel;
import com.webdoc.ibcc.DataModel.EducationDetailModel;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.FileUitls;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.DocumentDetailModel;
import com.webdoc.ibcc.Model.DocumentSelectionModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.AddDocumentDetailsResult.AddDocumentDetailsResult;
import com.webdoc.ibcc.ResponseModels.AddEducationResult.AddEducationResult;
import com.webdoc.ibcc.ResponseModels.AddQualificationEQ.AddQualificationEQ;
import com.webdoc.ibcc.ResponseModels.DeleteEducationResult.DeleteEducationResult;
import com.webdoc.ibcc.ResponseModels.EditEducationResult.EditEducationResult;
import com.webdoc.ibcc.ResponseModels.IntiateCase.IntiateCase;
import com.webdoc.ibcc.ResponseModels.RemoveQualificationEQ.RemoveQualificationEQ;
import com.webdoc.ibcc.ResponseModels.SaveCourierDetials.SaveCourierDetials;
import com.webdoc.ibcc.ResponseModels.SaveCourierDetialsEQ.SaveCourierDetialsEQ;
import com.webdoc.ibcc.ResponseModels.SaveDocumentDetailsEQ.SaveDocumentDetailsEQ;
import com.webdoc.ibcc.ResponseModels.Step1Result.Step1Result;
import com.webdoc.ibcc.ResponseModels.phpfilesResponse.PhpfilesResponse;
import com.webdoc.ibcc.Retrofit.jsonPlaceHolderApi;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import in.gauriinfotech.commons.Commons;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeSharedViewModel extends ViewModel {
    //Mutable LiveData:
    private MutableLiveData<AddDocumentDetailsResult> MLD_ADD_DOCUMENT_DETAILS = new MutableLiveData<>();
    private MutableLiveData<DeleteEducationResult> MLD_DELETE_EDUCTION = new MutableLiveData<>();
    private MutableLiveData<EditEducationResult> MLD_EDIT_EDUCTION = new MutableLiveData<>();
    private MutableLiveData<AddEducationResult> MLD_ADD_EDUCTION = new MutableLiveData<>();
    private MutableLiveData<Step1Result> MLD_STEP1 = new MutableLiveData<>();
    private MutableLiveData<SaveDocumentDetailsEQ> MLD_SAVE_DOCUMENT_DETAILSEQ = new MutableLiveData<>();
    private MutableLiveData<RemoveQualificationEQ> MLD_REMOVE_QUALIFICATIONEQ = new MutableLiveData<>();
    private MutableLiveData<IntiateCase> MLD_EQUIVALENCE_INITIAL_CASE = new MutableLiveData<>();
    private MutableLiveData<PhpfilesResponse> MLD_IMAGE_DOCUMENTS = new MutableLiveData<>();
    private MutableLiveData<PhpfilesResponse> MLD_IMAGE_TRAVELLING = new MutableLiveData<>();
    private MutableLiveData<SaveCourierDetials> MLD_SAVE_COURIER_DETAILS = new MutableLiveData<>();
    private MutableLiveData<SaveCourierDetialsEQ> MLD_SAVE_COURIER_DETAILS_EQ = new MutableLiveData<>();

    private MutableLiveData<DetailsEquivalenceNewModel> MLD_DETAILS_EQUIVALENCE_NEW = new MutableLiveData<>();
    private MutableLiveData<AddQualificationEQ> MLD_EQUIVALENCE_ADD_QUALIFICATION = new MutableLiveData<>();

    //LiveData:
    public LiveData<AddDocumentDetailsResult> getAddDocumentDetails() {
        return MLD_ADD_DOCUMENT_DETAILS;
    }

    public LiveData<DeleteEducationResult> getGetDeleteEduction() {
        return MLD_DELETE_EDUCTION;
    }

    public LiveData<EditEducationResult> getGetEditEduction() {
        return MLD_EDIT_EDUCTION;
    }

    public LiveData<AddEducationResult> getGetAddEduction() {
        return MLD_ADD_EDUCTION;
    }

    public LiveData<Step1Result> getGetStep1() {
        return MLD_STEP1;
    }

    public LiveData<SaveDocumentDetailsEQ> getGetSaveDocumetnDetails() {
        return MLD_SAVE_DOCUMENT_DETAILSEQ;
    }

    public LiveData<RemoveQualificationEQ> getGetRemoveQualificationEQ() {
        return MLD_REMOVE_QUALIFICATIONEQ;
    }

    public LiveData<IntiateCase> getGetEquivalenceInitialCase() {
        return MLD_EQUIVALENCE_INITIAL_CASE;
    }

    public LiveData<PhpfilesResponse> getGetImageDocuments() {
        return MLD_IMAGE_DOCUMENTS;
    }

    public LiveData<PhpfilesResponse> getGetImageTravelling() {
        return MLD_IMAGE_TRAVELLING;
    }

    public LiveData<SaveCourierDetials> getSaveCourierDetails() {
        return MLD_SAVE_COURIER_DETAILS;
    }

    public LiveData<SaveCourierDetialsEQ> getSaveCourierDetailsEQ() {
        return MLD_SAVE_COURIER_DETAILS_EQ;
    }

    public LiveData<DetailsEquivalenceNewModel> getDetailsEQNew() {
        return MLD_DETAILS_EQUIVALENCE_NEW;
    }

    public LiveData<AddQualificationEQ> getEquivalenveAddQualification() {
        return MLD_EQUIVALENCE_ADD_QUALIFICATION;
    }

    //Calling Apis:
    public void callAddDocumentDetailsApi(Activity activity,
                                          List<DocumentSelectionModel> documentSelectionModelList) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JSONObject params = new JSONObject();
            try {
                JSONArray documentArray = new JSONArray();
                for (int i = 0; i < documentSelectionModelList.size(); i++) {
                    JSONObject docItem = new JSONObject();

                    //docItem.put("document_id", documentSelectionModelList.get(i).getDocId());
                    docItem.put("document_id", documentSelectionModelList.get(i).getDocId());
                    docItem.put("case_id", documentSelectionModelList.get(i).getCaseId());
                    docItem.put("totalAmount", documentSelectionModelList.get(i).getTotalAmount());   //all document total amount

                    JSONArray document_detailsArray = new JSONArray();
                    for (DocumentDetailModel item : documentSelectionModelList.get(i).getDetailModelList()) {
                        JSONObject docDetailItem = new JSONObject();
                        docDetailItem.put("certificate_type", item.getCertificateType());
                        docDetailItem.put("original_included", item.getOriginalIncluded());
                        docDetailItem.put("document_type", item.getDocumentType());

                        docDetailItem.put("ticket_number", item.getTicketNo());
                        docDetailItem.put("ticket_date", item.getDate());
                    /*docDetailItem.put("ticket_number", "dfg");
                    docDetailItem.put("ticket_date", "0000-00-0");*/

                        docDetailItem.put("no_of_copies", item.getTotalCopies());
                        docDetailItem.put("amount", String.valueOf(item.getAmount()));     //only item ki amount

                        document_detailsArray.put(docDetailItem);
                    }

                    //params.put("document_details", document_detailsArray);
                    docItem.put("document_details", document_detailsArray);
                    documentArray.put(docItem);
                }

                params.put("document", documentArray);
                System.out.println("my selected documents" + params);

            } catch (JSONException e) {
                System.out.println("Error : Add Document Details " + e.toString());
            }


            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<AddDocumentDetailsResult> call = apiInterface.callAddDocumentDetails(params);

            call.enqueue(new Callback<AddDocumentDetailsResult>() {
                @Override
                public void onResponse(Call<AddDocumentDetailsResult> call, Response<AddDocumentDetailsResult> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        MLD_ADD_DOCUMENT_DETAILS.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AddDocumentDetailsResult> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callDeleteEducationApi(Activity activity, String eDocId, String eCaseId) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("document_id", eDocId);
            params.addProperty("case_id", eCaseId);

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<DeleteEducationResult> call = apiInterface.callDeleteEducation(params);

            call.enqueue(new Callback<DeleteEducationResult>() {
                @Override
                public void onResponse(Call<DeleteEducationResult> call, Response<DeleteEducationResult> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        MLD_DELETE_EDUCTION.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DeleteEducationResult> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callEditEducationApi(Activity activity, String eDocId, String certId, String proId,
                                     String groId, String broId,
                                     String year, String obtainedMarks, String totalMarks, String session,
                                     String rollNo, String eCaseId) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("document_id", eDocId);
            params.addProperty("certificate_id", certId);
            params.addProperty("program_id", proId);
            params.addProperty("group_id", groId);
            params.addProperty("board_id", broId);
            params.addProperty("year", year);
            params.addProperty("marks", obtainedMarks);
            params.addProperty("total", totalMarks);
            params.addProperty("session", session);
            params.addProperty("roll_number", rollNo);
            params.addProperty("case_id", eCaseId);

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<EditEducationResult> call = apiInterface.callEditEducation(params);

            call.enqueue(new Callback<EditEducationResult>() {
                @Override
                public void onResponse(Call<EditEducationResult> call, Response<EditEducationResult> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        MLD_EDIT_EDUCTION.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<EditEducationResult> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callAddEducationApi(Activity activity, EducationDetailModel dataModel) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("certificate_id", dataModel.getCertificate_id());
            params.addProperty("program_id", dataModel.getProgram_id());
            params.addProperty("group_id", dataModel.getGroup_id());
            params.addProperty("board_id", dataModel.getBoard_id());
            params.addProperty("year", dataModel.getPassing_year());
            params.addProperty("marks", dataModel.getMarks_obtained());
            params.addProperty("total", dataModel.getTotal_marks());
            params.addProperty("session", dataModel.getSession());
            params.addProperty("roll_number", dataModel.getRoll_number());
            params.addProperty("case_id", dataModel.getCase_id());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<AddEducationResult> call = apiInterface.callAddEducation(params);

            call.enqueue(new Callback<AddEducationResult>() {
                @Override
                public void onResponse(Call<AddEducationResult> call, Response<AddEducationResult> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        MLD_ADD_EDUCTION.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AddEducationResult> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callStep1Api(Activity activity, String firstName, String lastName, String dob,
                             String fatherName, String cnic, String title, String domicileId, String pAddress,
                             String pCity, String pProvinceId, String pCountryId, String cAddress, String cCity,
                             String cProvinceId, String cCountryId, String phone,
                             String mobileNo, String email) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("id", Global.userLoginResponse.getResult().getCustomerProfile().getId());   //2004
            params.addProperty("first_name", firstName);
            params.addProperty("last_name", lastName);
            params.addProperty("dob", dob);
            params.addProperty("father_name", fatherName);
            params.addProperty("cnic", cnic);
            params.addProperty("title", title);
            params.addProperty("domicile", domicileId);
            params.addProperty("p_add", pAddress);
            params.addProperty("p_city", pCity);
            params.addProperty("p_province_id", pProvinceId);
            params.addProperty("p_country_id", pCountryId);
            params.addProperty("c_add", cAddress);
            params.addProperty("c_city", cCity);
            params.addProperty("c_province_id", cProvinceId);
            params.addProperty("c_country_id", cCountryId);
            params.addProperty("phone", phone);
            params.addProperty("mobile", mobileNo);
            params.addProperty("email", email);
            params.addProperty("passport_siz_image", " ");

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<Step1Result> call = apiInterface.callStep1(params);

            call.enqueue(new Callback<Step1Result>() {
                @Override
                public void onResponse(Call<Step1Result> call, Response<Step1Result> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        MLD_STEP1.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Step1Result> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callsaveDocumentDetailsApi(Activity activity, List<DocDetailEQ_Model> dataModel) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            DocumentsModel documentsModel = new DocumentsModel();
            documentsModel.setCaseId(dataModel.get(0).getCaseID());

            //JSONObject params = new JSONObject();
            //try {
            //params.put("caseId", dataModel.get(0).getCaseID());
            //JSONArray docArray = new JSONArray();

            List<SaveDocumentDetails> list = new ArrayList<>();
            for (int j = 0; j < Global.equivalenceQualificationList.size(); j++) {
                SaveDocumentDetails saveDocumentDetails = new SaveDocumentDetails();
                saveDocumentDetails.setDocId(dataModel.get(j).getDocId());
                saveDocumentDetails.setAmount(dataModel.get(j).getAmount());
                saveDocumentDetails.setAverageMarks(0);
                saveDocumentDetails.setDocumentType(dataModel.get(j).getDocumentType());
                saveDocumentDetails.setEquivalenceOfCertificates(dataModel.get(j).getQofCert());
                saveDocumentDetails.setEquivalenceOfCertificatesType(dataModel.get(j).getqOfCertType());

                saveDocumentDetails.setFinalObtainedMarks(dataModel.get(j).getfObtMarks());
                saveDocumentDetails.setFinalTotalMarks(dataModel.get(j).getfTotalMarks());
                saveDocumentDetails.setObtainedMarks(dataModel.get(j).getObtainedMarks());
                saveDocumentDetails.setTotalMarks(dataModel.get(j).getTotalMarks());
                saveDocumentDetails.setPercentage(dataModel.get(j).getPercentage());

                saveDocumentDetails.setPreviousTicketNumber("");
                saveDocumentDetails.setTicketDate("2021-06-19T06:43:05.343Z");
                saveDocumentDetails.setTicketNumber("");
                saveDocumentDetails.setUrgent(dataModel.get(j).getUrgent());

                list.add(saveDocumentDetails);
            }

            documentsModel.setSaveDocumentDetailsList(list);
            Log.d("testparams", "callsaveDocumentDetailsApi: " + documentsModel);

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<SaveDocumentDetailsEQ> call = apiInterface.callSaveDocumentsDetails(documentsModel);

            call.enqueue(new Callback<SaveDocumentDetailsEQ>() {
                @Override
                public void onResponse(Call<SaveDocumentDetailsEQ> call, Response<SaveDocumentDetailsEQ> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        MLD_SAVE_DOCUMENT_DETAILSEQ.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SaveDocumentDetailsEQ> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callRemoveQualificationApi(Activity activity, int docId, int caseId) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("docId", docId);
            params.addProperty("caseId", caseId);

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<RemoveQualificationEQ> call = apiInterface.callRemoveQualification(params);
            call.enqueue(new Callback<RemoveQualificationEQ>() {
                @Override
                public void onResponse(Call<RemoveQualificationEQ> call, Response<RemoveQualificationEQ> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        MLD_REMOVE_QUALIFICATIONEQ.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RemoveQualificationEQ> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callEquivalenceInitiateCaseApi(Activity activity) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("firstName", Global.equivalenceInitiateCase.getFirstName());
            params.addProperty("lastName", Global.equivalenceInitiateCase.getLastName());
            params.addProperty("dob", Global.equivalenceInitiateCase.getDob());
            params.addProperty("fatherName", Global.equivalenceInitiateCase.getFatherName());
            params.addProperty("cnic", Global.equivalenceInitiateCase.getCnic());
            params.addProperty("title", Global.equivalenceInitiateCase.getUserTitle());
            params.addProperty("domicile", Global.equivalenceInitiateCase.getDomicileID());
            params.addProperty("birthPlace", Global.equivalenceInitiateCase.getPob());
            params.addProperty("pAdd", Global.equivalenceInitiateCase.getpAddress());
            params.addProperty("pCity", Global.equivalenceInitiateCase.getpCity());
            params.addProperty("pProvinceId", Global.equivalenceInitiateCase.getpProvinceId());
            params.addProperty("pCountryId", Global.equivalenceInitiateCase.getpCountryId());
            params.addProperty("cAdd", Global.equivalenceInitiateCase.getcAddress());
            params.addProperty("cCity", Global.equivalenceInitiateCase.getcCity());
            params.addProperty("cProvinceId", Global.equivalenceInitiateCase.getcProvinceId());
            params.addProperty("cCountryId", Global.equivalenceInitiateCase.getcCountryId());
            params.addProperty("phone", Global.equivalenceInitiateCase.getPhone());
            params.addProperty("mobile", Global.equivalenceInitiateCase.getMobile());
            params.addProperty("email", Global.equivalenceInitiateCase.getEmail());
            params.addProperty("passport_siz_image", Global.equivalenceInitiateCase.getPassportSizImage());
            params.addProperty("password", Global.equivalenceInitiateCase.getPassword());
            params.addProperty("nationality", Global.equivalenceInitiateCase.getNationality());
            params.addProperty("presentEmploymentOfParents", Global.equivalenceInitiateCase.getParentsEmployment());
            params.addProperty("fatherCnic", Global.equivalenceInitiateCase.getFatherCnic());
            params.addProperty("parentsPermanentAddress", Global.equivalenceInitiateCase.getFatherAddress());
            params.addProperty("parentsNameOfTheOrganization", Global.equivalenceInitiateCase.getNameOfOrganization());
            params.addProperty("incomAppLink", Global.equivalenceInitiateCase.getIncomAppLink());
            params.addProperty("ThirdParty", Global.equivalenceInitiateCase.getThirdParty());


            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<IntiateCase> call = apiInterface.callEquivalenceIntiateCase(params);

            call.enqueue(new Callback<IntiateCase>() {
                @Override
                public void onResponse(Call<IntiateCase> call, Response<IntiateCase> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        MLD_EQUIVALENCE_INITIAL_CASE.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<IntiateCase> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Oopps something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callImageDocumentApi(Context context) {
        if (Global.utils.isInternerConnected(context)) {

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
                    .baseUrl("https://equivalence.ibcc.edu.pk/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client) // Set HttpClient to be used by Retrofit
                    .build();

            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);

            for (int i = 0; i < Global.phpfiles.size(); i++) {

                File file = new File(FileUitls.getPath(context, Global.phpfiles.get(i)));

                builder.addFormDataPart(
                        "image[]",
                        file.getName(),

                        RequestBody.create(MediaType.parse(context.getContentResolver().getType(Global.phpfiles.get(i))), file)
                );
            }
            RequestBody requestBody = builder.build();
            jsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);
            Call<PhpfilesResponse> call1 = jsonPlaceHolderApi.callImagesFormDataApi(requestBody);

            call1.enqueue(new Callback<PhpfilesResponse>() {
                @Override
                public void onResponse(Call<PhpfilesResponse> call, retrofit2.Response<PhpfilesResponse> response) {
                    Global.utils.hideCustomLoadingDialog();
                    if (response.isSuccessful()) {
                        MLD_IMAGE_DOCUMENTS.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<PhpfilesResponse> call, Throwable t) {
                    Toast.makeText(Global.applicationContext, t.getMessage() + "", Toast.LENGTH_SHORT).show();
                    Global.utils.hideCustomLoadingDialog();
                    call.cancel();
                }
            });
        } else {
            Toast.makeText(context, "Please connect internet connection !", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void callImageTravellingApi(Context context) {

        if (Global.utils.isInternerConnected(context)) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient();
            client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.MINUTES)
                    .addInterceptor(interceptor)
                    .build();
            String url = Constants.TRAVELLING_URL;
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://equivalence.ibcc.edu.pk/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client) // Set HttpClient to be used by Retrofit
                    .build();

            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);

            for (int i = 0; i < Global.phptravellingFiles.size(); i++) {
                File file = new File(FileUitls.getPath(context, Global.phptravellingFiles.get(i)));
                builder.addFormDataPart(
                        "image[]",
                        file.getName(),
                        RequestBody.create(MediaType.parse(context.getContentResolver().getType(Global.phptravellingFiles.get(i))), file)
                );
            }


            RequestBody requestBody = builder.build();
            jsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);
            Call<PhpfilesResponse> call1 = jsonPlaceHolderApi.callImagesTravellingFormDataApi(requestBody);

            call1.enqueue(new Callback<PhpfilesResponse>() {
                @Override
                public void onResponse(Call<PhpfilesResponse> call, retrofit2.Response<PhpfilesResponse> response) {
                    Global.utils.hideCustomLoadingDialog();
                    if (response.isSuccessful()) {
                        MLD_IMAGE_TRAVELLING.postValue(response.body());
                    } else {
                        Toast.makeText(context, "something went wrong / Server error !", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PhpfilesResponse> call, Throwable t) {
                    Toast.makeText(Global.applicationContext, "Failure, something went wrong !", Toast.LENGTH_SHORT).show();
                    Global.utils.hideCustomLoadingDialog();
                    call.cancel();
                }
            });
        } else {
            Toast.makeText(context, "Please connect internet connection !", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void callEquivalenceAddQualificationApi(Context context, JsonObject params) {
        if (Global.utils.isInternerConnected(context)) {

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<AddQualificationEQ> call1 = apiInterface.callEquivalenceAddQualificationApi(params);

            call1.enqueue(new Callback<AddQualificationEQ>() {
                @Override
                public void onResponse(Call<AddQualificationEQ> call, retrofit2.Response<AddQualificationEQ> response) {
                    Global.utils.hideCustomLoadingDialog();
                    if (response.isSuccessful()) {
                        MLD_EQUIVALENCE_ADD_QUALIFICATION.postValue(response.body());
                    } else {
                        Toast.makeText(context, "something went wrong / Server error !", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AddQualificationEQ> call, Throwable t) {
                    Toast.makeText(Global.applicationContext, "Failure, something went wrong !", Toast.LENGTH_SHORT).show();
                    Global.utils.hideCustomLoadingDialog();
                    call.cancel();
                }
            });
        } else {
            Toast.makeText(context, "Please connect internet connection !", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void callSaveCourierDetailsApi(Activity activity) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("center", String.valueOf(Global.center));
            params.addProperty("case_id", Global.caseId);
            params.addProperty("user_id", Global.userLoginResponse.getResult().getCustomerProfile().getId());
            params.addProperty("consignment_id", Global.saveBookingResponse.getCnno());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<SaveCourierDetials> call = apiInterface.callSaveCourierDetails(params);

            call.enqueue(new Callback<SaveCourierDetials>() {
                @Override
                public void onResponse(Call<SaveCourierDetials> call, Response<SaveCourierDetials> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        MLD_SAVE_COURIER_DETAILS.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SaveCourierDetials> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callSaveCourierDetailsEQApi(Activity activity) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("center", String.valueOf(Global.equivalenceGenerateAppCenter.getName()));
            params.addProperty("case_id", Global.caseId);
            params.addProperty("user_id", Global.userLoginResponse.getResult().getCustomerProfile().getId());
            params.addProperty("consignment_id", Global.saveBookingResponse.getCnno());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<SaveCourierDetialsEQ> call = apiInterface.callSaveCourierDetailsEQ(params);

            call.enqueue(new Callback<SaveCourierDetialsEQ>() {
                @Override
                public void onResponse(Call<SaveCourierDetialsEQ> call, Response<SaveCourierDetialsEQ> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        MLD_SAVE_COURIER_DETAILS_EQ.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SaveCourierDetialsEQ> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    //New Details APi:
    public void callDetailsEquivalenceNewApi(Activity activity) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject mobj = new JsonObject();

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<DetailsEquivalenceNewModel> call = apiInterface.callDetailsEquivalenceNew(mobj);

            call.enqueue(new Callback<DetailsEquivalenceNewModel>() {
                @Override
                public void onResponse(Call<DetailsEquivalenceNewModel> call
                        , Response<DetailsEquivalenceNewModel> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        MLD_DETAILS_EQUIVALENCE_NEW.postValue(response.body());
                    } else {
                        Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DetailsEquivalenceNewModel> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }
}
