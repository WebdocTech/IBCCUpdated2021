package com.webdoc.ibcc.DashBoard.Appointment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.webdoc.ItemClickListeners;
import com.webdoc.ibcc.Adapter.IncompleteAppointmentAdapter;
import com.webdoc.ibcc.Adapter.IncompleteAppointmentAdapterEQ;
import com.webdoc.ibcc.DashBoard.Account.Appointment.AppointmentViewModel.AppointmentViewModel;
import com.webdoc.ibcc.DashBoard.Dashboard;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.ApplyEquivalenceActivity;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.Country;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.EquivalenceGradingSystemEQNew;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.ExaminingBody;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.GradesEQNew;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.GroupEQNew;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.Qualification;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.AddQualificationModel;
import com.webdoc.ibcc.Model.DeleteParams;
import com.webdoc.ibcc.Model.EquivalenceFileModel;
import com.webdoc.ibcc.Model.EquivalenceInitiateCase;
import com.webdoc.ibcc.Model.EquivalenceTravelFileModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.AddQualificationEQ.QualificationSubjectResponse;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.EquivalenceGrade;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.EquivalenceGradingSystem;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.EquivalenceGroup;
import com.webdoc.ibcc.ResponseModels.IncompleteDetailsEQ.Document_EQ;
import com.webdoc.ibcc.ResponseModels.IncompleteDetailsEQ.IncompleteDetailsEQ;
import com.webdoc.ibcc.ResponseModels.IncompleteDetailsEQ.Paymentinfo;
import com.webdoc.ibcc.ResponseModels.PdfResult.Center;
import com.webdoc.ibcc.ResponseModels.ViewIncompleteAppointmentEQ.IncompleteResultDetail;
import com.webdoc.ibcc.ResponseModels.ViewIncompleteAppointmentEQ.ViewIncompleteAppointmentEQ;
import com.webdoc.ibcc.ResponseModels.ViewIncompleteResult.ViewIncompleteResult;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.FragIncompleteApptEqBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.webdoc.ibcc.DashBoard.Dashboard.BottomLayout;

public class IncompleteAppt_Eq extends Fragment implements ItemClickListeners {
    private IncompleteAppointmentAdapterEQ incompleteAppointmentAdapterEQ;
    private FragIncompleteApptEqBinding layoutBinding;
    private AppointmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutBinding = FragIncompleteApptEqBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(AppointmentViewModel.class);

        //imported from dashboard:
        BottomLayout.setVisibility(View.VISIBLE);
        //volleyRequestController = new VolleyRequestController(getActivity());
        //volleyRequestController.ViewIncompleteAppointmentEQ();
        //callViewIncompleteAppointmentEQApi(getActivity());
        viewModel.callViewIncompleteAppointmentEQApi(getActivity());
        observers();
        return layoutBinding.getRoot();
    }

    private void observers() {
        viewModel.getViewIncompleteAppointmentEQ().observe(this, response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Global.viewIncompleteAppointmentEQ = response;
                    //Global.utils.hideCustomLoadingDialog();
                    if (Global.viewIncompleteAppointmentEQ.getResult().getIncompleteResultDetails().size() > 0) {
                        layoutBinding.LayoutAppointmentsEQ.setVisibility(View.VISIBLE);
                        layoutBinding.LayoutNoAppointmentsEQ.setVisibility(View.GONE);
                    } else {
                        layoutBinding.LayoutNoAppointmentsEQ.setVisibility(View.VISIBLE);
                        layoutBinding.LayoutAppointmentsEQ.setVisibility(View.GONE);
                    }
                    setAdapter();
                } else {
                    layoutBinding.LayoutNoAppointmentsEQ.setVisibility(View.VISIBLE);
                    layoutBinding.LayoutAppointmentsEQ.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getViewIncompleteDetailsEQ().observe(this, response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Global.incompleteDetailsEQResponse = response;
                    Global.UserIdForCase = response.getResult().getPaymentinfo().getUserId();

                    if (response.getResult().getStepNumber().equals("2")) {
                        Global.isIncompleteAppointmentEQ = true;
                        Global.equivalenceQualificationList.clear();
                        Global.isFromEquivalence = true;

                        for (int i = 0; i < response.getResult().getDocument().size(); i++) {
                            Paymentinfo paymentinfo = new Paymentinfo();

                            Document_EQ item = response.getResult().getDocument().get(i);

                            AddQualificationModel addModel = new AddQualificationModel();
                            EquivalenceGradingSystemEQNew equivalenceGradingSystem = new EquivalenceGradingSystemEQNew();
                            equivalenceGradingSystem.setId(Integer.parseInt(item.getGradingSystemId()));
                            equivalenceGradingSystem.setName(item.getGradingSystemName());
                            //List<GradesEQNew> equivalenceGrades = new ArrayList<>();
                            //equivalenceGradingSystem.setEquivalenceGrade(equivalenceGrades);

                            //todo : adding images without travelling
                            List<EquivalenceFileModel> imagesModelList = new ArrayList<>();
                            for (int y = 0; y < item.getCaseUploadedDocumentResponse().size(); y++) {
                                EquivalenceFileModel imagesModel = new EquivalenceFileModel();
                                imagesModel.setUri(Uri.parse(item.getCaseUploadedDocumentResponse().get(y).getImagename()));
                                imagesModel.setFileName(item.getCaseUploadedDocumentResponse().get(y).getImagename());
                                imagesModel.setFileType(String.valueOf(item.getCaseUploadedDocumentResponse().get(y).getIspdf()));
                                imagesModelList.add(imagesModel);
                            }
                            addModel.setSelectedFilesList(imagesModelList);

                            //todo :adding travelling images in subjs
                            List<EquivalenceTravelFileModel> imagesTravellingModelList = new ArrayList<>();
                            for (int y = 0; y < item.getCaseUploadedTravellingDocumentResponse().size(); y++) {
                                EquivalenceTravelFileModel imagesModel = new EquivalenceTravelFileModel();
                                imagesModel.setUri(Uri.parse(item.getCaseUploadedTravellingDocumentResponse().get(y).getImagename()));
                                imagesModel.setFileName(item.getCaseUploadedTravellingDocumentResponse().get(y).getImagename());
                                imagesModel.setFileType(String.valueOf(item.getCaseUploadedTravellingDocumentResponse().get(y).getIspdf()));
                                imagesTravellingModelList.add(imagesModel);
                            }
                            addModel.setSelectedTravelFilesList(imagesTravellingModelList);

                            //todo : adding gradesss of subjects in list
                            List<QualificationSubjectResponse> gradesEntered = new ArrayList<>();
                            for (int y = 0; y < item.getQualificationSubjectResponse().size(); y++) {
                                QualificationSubjectResponse GradesModel = new QualificationSubjectResponse();
                                GradesModel.setSubjectName(item.getQualificationSubjectResponse().get(y).getSubjectName());
                                GradesModel.setSubjectId(item.getQualificationSubjectResponse().get(y).getSubjectId());
                                GradesModel.setMarksgrades(item.getQualificationSubjectResponse().get(y).getMarksgrades());
                                gradesEntered.add(GradesModel);
                            }
                            addModel.setQualificationSubjectResponseList(gradesEntered);

                            Country countryTemp = new Country();
                            /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                countryTemp = Global.detailsEquivalenceNewModel.getResult().getCountries().stream().filter(y -> y.getId().equals(item.getCountryId())).findFirst().get();
                            }*/
                            for (int j = 0; j < Global.detailsEquivalenceNewModel.getResult().getCountries().size(); j++) {
                                countryTemp = Global.detailsEquivalenceNewModel.getResult().getCountries().get(j);
                            }

                            Qualification qualification = new Qualification();
                            /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                qualification = Global.detailsEquivalenceNewModel.getResult().getQualification().stream().filter(y -> y.getId().equals(item.getQualificationId())).findFirst().get();
                            }*/

                            /*for (int k = 0; k < Global.detailsEquivalenceNewModel.getResult().getQualification().size(); k++) {
                                int str1 = Global.detailsEquivalenceNewModel.getResult().getQualification().get(k).getCountryId();
                                int str2 = Integer.parseInt(item.getQualificationId());
                                if (str1 == str2) {*/
                            qualification = Global.detailsEquivalenceNewModel.getResult().getQualification().get(Integer.parseInt(item.getQualificationId()));
                            //}
                            //}

                            addModel.setQualification(qualification);
                            addModel.setGradingSystem(equivalenceGradingSystem);
                            addModel.setPurposeOfEquivalence(item.getPurposeOfEquivalence());

                            GroupEQNew equivalenceGroup = new GroupEQNew();
                            equivalenceGroup.setName(item.getGroupName());
                            equivalenceGroup.setId(Integer.parseInt(item.getGroupId()));
                            addModel.setGroup(equivalenceGroup);


                            addModel.setExaminationSystem(item.getExaminationSystem());
                            ExaminingBody examiningBody = new ExaminingBody();
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                examiningBody = Global.detailsEquivalenceNewModel.getResult().getExaminingBody().stream().filter(y -> y.getName().equals(item.getExaminingBody())).findFirst().get();
                            }

                            addModel.setExaminingBody(examiningBody);
                            addModel.setCountry(countryTemp);
                            addModel.setSession(item.getSession());

                            List<QualificationSubjectResponse> gradeList = new ArrayList<>();
                            Global.equivalenceQualificationList.add(addModel);

                            EquivalenceInitiateCase equivalenceInitiateCase = new EquivalenceInitiateCase();
                            equivalenceInitiateCase.setcCountryId(item.getCountryId());
                            equivalenceInitiateCase.setEmail(item.getEmail());
                            equivalenceInitiateCase.setPhone(item.getTelNo());
                            equivalenceInitiateCase.setFatherCnic(item.getFatherCnic());
                            equivalenceInitiateCase.setFatherAddress(item.getParentsPermanentAddress());
                            equivalenceInitiateCase.setFatherName(item.getFatherName());
                            equivalenceInitiateCase.setParentsEmployment(item.getPresentEmploymentOfParents());
                            Global.equivalenceInitiateCase = equivalenceInitiateCase;

                            DeleteParams deleteParams = new DeleteParams();
                            deleteParams.setCaseId(item.getCaseId());
                            deleteParams.setDocId(item.getDocId());
                            Global.deleteParams.add(deleteParams);
                        }

                        BottomLayout.setVisibility(View.GONE);
                        Global.stepNumberEQ = "2";

                        Intent intent = new Intent(getActivity(), ApplyEquivalenceActivity.class);
                        startActivity(intent);

                    } else if (response.getResult().getStepNumber().equals("4")) {
                        Global.isIncompleteAppointmentEQ = true;
                        Global.equivalenceQualificationList.clear();
                        Global.isFromEquivalence = true;

                        for (int i = 0; i < response.getResult().getDocument().size(); i++) {
                            Document_EQ item = response.getResult().getDocument().get(i);
                            //Global.documentsTotalFee = Integer.parseInt(item.getDocumentDetailList().getAmount());

                            AddQualificationModel addModel = new AddQualificationModel();
                            EquivalenceGradingSystemEQNew equivalenceGradingSystem = new EquivalenceGradingSystemEQNew();
                            equivalenceGradingSystem.setId(Integer.parseInt(item.getGradingSystemId()));
                            equivalenceGradingSystem.setName(item.getGradingSystemName());
                            //List<EquivalenceGrade> equivalenceGrades = new ArrayList<>();
                            //equivalenceGradingSystem.setEquivalenceGrade(equivalenceGrades);

                            List<EquivalenceFileModel> imagesModelList = new ArrayList<>();
                            for (int y = 0; y < item.getCaseUploadedDocumentResponse().size(); y++) {
                                EquivalenceFileModel imagesModel = new EquivalenceFileModel();
                                imagesModel.setUri(Uri.parse(item.getCaseUploadedDocumentResponse().get(y).getImagename()));
                                imagesModel.setFileName(item.getCaseUploadedDocumentResponse().get(y).getImagename());
                                imagesModel.setFileType(String.valueOf(item.getCaseUploadedDocumentResponse().get(y).getIspdf()));
                                imagesModelList.add(imagesModel);
                            }
                            addModel.setSelectedFilesList(imagesModelList);

                            List<EquivalenceTravelFileModel> imagesTravellingModelList = new ArrayList<>();

                            for (int y = 0; y < item.getCaseUploadedTravellingDocumentResponse().size(); y++) {
                                EquivalenceTravelFileModel imagesModel = new EquivalenceTravelFileModel();
                                imagesModel.setUri(Uri.parse(item.getCaseUploadedTravellingDocumentResponse().get(y).getImagename()));
                                imagesModel.setFileName(item.getCaseUploadedTravellingDocumentResponse().get(y).getImagename());
                                imagesModel.setFileType(String.valueOf(item.getCaseUploadedTravellingDocumentResponse().get(y).getIspdf()));
                                imagesTravellingModelList.add(imagesModel);
                            }
                            addModel.setSelectedTravelFilesList(imagesTravellingModelList);

                            //todo : adding gradesss of subjects in list
                            List<QualificationSubjectResponse> gradesEntered = new ArrayList<>();
                            for (int y = 0; y < item.getQualificationSubjectResponse().size(); y++) {
                                QualificationSubjectResponse GradesModel = new QualificationSubjectResponse();
                                GradesModel.setSubjectName(item.getQualificationSubjectResponse().get(y).getSubjectName());
                                GradesModel.setSubjectId(item.getQualificationSubjectResponse().get(y).getSubjectId());
                                GradesModel.setMarksgrades(item.getQualificationSubjectResponse().get(y).getMarksgrades());
                                gradesEntered.add(GradesModel);
                            }
                            addModel.setQualificationSubjectResponseList(gradesEntered);

                            Country countryTemp = new Country();
                            /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                countryTemp = Global.detailsEquivalenceNewModel.getResult().getCountries().stream().filter(y -> y.getId().equals(item.getCountryId())).findFirst().get();
                            }*/

                            for (int j = 0; j < Global.detailsEquivalenceNewModel.getResult().getCountries().size(); j++) {
                                countryTemp = Global.detailsEquivalenceNewModel.getResult().getCountries().get(j);
                            }

                            Qualification qualification = new Qualification();
                            /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                qualification = Global.detailsEquivalenceNewModel.getResult().getQualification().stream().filter(y -> y.getId().equals(item.getQualificationId())).findFirst().get();
                            }*/
                            qualification = Global.detailsEquivalenceNewModel.getResult().getQualification().get(Integer.parseInt(item.getQualificationId()));

                            addModel.setQualification(qualification);
                            addModel.setGradingSystem(equivalenceGradingSystem);
                            addModel.setPurposeOfEquivalence(item.getPurposeOfEquivalence());


                            GroupEQNew equivalenceGroup = new GroupEQNew();
                            equivalenceGroup.setName(item.getGroupName());
                            equivalenceGroup.setId(Integer.parseInt(item.getGroupId()));
                            addModel.setGroup(equivalenceGroup);

                            addModel.setExaminationSystem(item.getExaminationSystem());
                            ExaminingBody examiningBody = new ExaminingBody();
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                String str = item.getExaminingBody();
                                examiningBody = Global.detailsEquivalenceNewModel.getResult().getExaminingBody().stream().filter(y -> y.getName().equals(item.getExaminingBody())).findFirst().get();
                            }


                            String documenttype = item.getDocumentDetailList().getDocumentType();
                            String isurgent = item.getDocumentDetailList().getUrgent();
                            switch (documenttype) {
                                case "New/First Time":
                                    if (isurgent.equals("true")) {
                                        Global.documentsTotalFee += 6000;
                                        //    Global.documentAmountArray[i] = 6000;
                                    } else {
                                        Global.documentsTotalFee += 3000;
                                        //     Global.documentAmountArray[i] = 3000;
                                    }
                                    break;

                                case "Duplicate":
                                    if (isurgent.equals("true")) {
                                        Global.documentsTotalFee += 12000;
                                        //    Global.documentAmountArray[i] = 12000;
                                    } else {
                                        Global.documentsTotalFee += 6000;
                                        //    Global.documentAmountArray[i] = 6000;
                                    }
                                    break;

                                case "1st Revision":
                                    if (isurgent.equals("true")) {
                                        Global.documentsTotalFee += 12000;
                                        // Global.documentAmountArray[i] = 12000;
                                    } else {
                                        Global.documentsTotalFee += 6000;
                                        //  Global.documentAmountArray[i] = 6000;
                                    }
                                    break;

                                case "2nd Revision":
                                    if (isurgent.equals("true")) {
                                        Global.documentsTotalFee += 18000;
                                        //  Global.documentAmountArray[i] = 18000;
                                    } else {
                                        Global.documentsTotalFee += 9000;
                                        //    Global.documentAmountArray[i] = 9000;
                                    }
                                    break;

                                case "3rd Revision":
                                    if (isurgent.equals("true")) {
                                        Global.documentsTotalFee += 24000;
                                        //    Global.documentAmountArray[i] = 24000;
                                    } else {
                                        Global.documentsTotalFee += 12000;
                                        //     Global.documentAmountArray[i] = 12000;
                                    }
                                    break;
                            }


                            addModel.setExaminingBody(examiningBody);
                            addModel.setCountry(countryTemp);
                            addModel.setSession(item.getSession());

                            List<QualificationSubjectResponse> gradeList = new ArrayList<>();
                            Global.equivalenceQualificationList.add(addModel);

                            EquivalenceInitiateCase equivalenceInitiateCase = new EquivalenceInitiateCase();
                            equivalenceInitiateCase.setcCountryId(item.getCountryId());
                            equivalenceInitiateCase.setEmail(item.getEmail());
                            equivalenceInitiateCase.setPhone(item.getTelNo());
                            equivalenceInitiateCase.setFatherCnic(item.getFatherCnic());
                            equivalenceInitiateCase.setFatherAddress(item.getParentsPermanentAddress());
                            equivalenceInitiateCase.setFatherName(item.getFatherName());
                            equivalenceInitiateCase.setParentsEmployment(item.getPresentEmploymentOfParents());
                            Global.equivalenceInitiateCase = equivalenceInitiateCase;

                            DeleteParams deleteParams = new DeleteParams();
                            deleteParams.setCaseId(item.getCaseId());
                            deleteParams.setDocId(item.getDocId());
                            Global.deleteParams.add(deleteParams);
                        }

                        BottomLayout.setVisibility(View.GONE);
                        Global.stepNumberEQ = "4";
                        Intent intent = new Intent(getActivity(), ApplyEquivalenceActivity.class);
                        startActivity(intent);
                    } else if (response.getResult().getStepNumber().equals("5")) {
                        Global.isIncompleteAppointmentEQ = true;
                        Global.equivalenceQualificationList.clear();
                        Global.isFromEquivalence = true;

                        for (int i = 0; i < response.getResult().getDocument().size(); i++) {
                            Document_EQ item = response.getResult().getDocument().get(i);
                            Global.documentsTotalFee = Integer.parseInt(item.getDocumentDetailList().getAmount());

                            //Global.allDocumentsFeeEQ= item.getDocumentDetailList().getAmount();

                            AddQualificationModel addModel = new AddQualificationModel();
                            EquivalenceGradingSystemEQNew equivalenceGradingSystem = new EquivalenceGradingSystemEQNew();
                            equivalenceGradingSystem.setId(Integer.parseInt(item.getGradingSystemId()));
                            equivalenceGradingSystem.setName(item.getGradingSystemName());

                            //List<EquivalenceGrade> equivalenceGrades = new ArrayList<>();
                            //equivalenceGradingSystem.setEquivalenceGrade(equivalenceGrades);

                            List<EquivalenceFileModel> imagesModelList = new ArrayList<>();
                            for (int y = 0; y < item.getCaseUploadedDocumentResponse().size(); y++) {
                                EquivalenceFileModel imagesModel = new EquivalenceFileModel();
                                imagesModel.setUri(Uri.parse(item.getCaseUploadedDocumentResponse().get(y).getImagename()));
                                imagesModel.setFileName(item.getCaseUploadedDocumentResponse().get(y).getImagename());
                                imagesModel.setFileType(String.valueOf(item.getCaseUploadedDocumentResponse().get(y).getIspdf()));
                                imagesModelList.add(imagesModel);
                            }
                            addModel.setSelectedFilesList(imagesModelList);


                            List<EquivalenceTravelFileModel> imagesTravellingModelList = new ArrayList<>();
                            for (int y = 0; y < item.getCaseUploadedTravellingDocumentResponse().size(); y++) {
                                EquivalenceTravelFileModel imagesModel = new EquivalenceTravelFileModel();
                                imagesModel.setUri(Uri.parse(item.getCaseUploadedTravellingDocumentResponse().get(y).getImagename()));
                                imagesModel.setFileName(item.getCaseUploadedTravellingDocumentResponse().get(y).getImagename());
                                imagesModel.setFileType(String.valueOf(item.getCaseUploadedTravellingDocumentResponse().get(y).getIspdf()));
                                imagesTravellingModelList.add(imagesModel);
                            }
                            addModel.setSelectedTravelFilesList(imagesTravellingModelList);

                            //todo : adding gradesss of subjects in list
                            List<QualificationSubjectResponse> gradesEnteredlist = new ArrayList<>();
                            for (int y = 0; y < item.getQualificationSubjectResponse().size(); y++) {
                                QualificationSubjectResponse GradesModel = new QualificationSubjectResponse();
                                GradesModel.setSubjectName(item.getQualificationSubjectResponse().get(y).getSubjectName());
                                GradesModel.setSubjectId(item.getQualificationSubjectResponse().get(y).getSubjectId());
                                GradesModel.setMarksgrades(item.getQualificationSubjectResponse().get(y).getMarksgrades());
                                gradesEnteredlist.add(GradesModel);
                            }
                            addModel.setQualificationSubjectResponseList(gradesEnteredlist);


                            Country countryTemp = new Country();
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                countryTemp = Global.detailsEquivalenceNewModel.getResult().getCountries().stream().filter(y -> y.getId().equals(item.getCountryId())).findFirst().get();
                            }

                            Qualification qualification = new Qualification();
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                qualification = Global.detailsEquivalenceNewModel.getResult().getQualification().stream().filter(y -> y.getId().equals(item.getQualificationId())).findFirst().get();

                            }

                            addModel.setQualification(qualification);
                            addModel.setGradingSystem(equivalenceGradingSystem);
                            addModel.setPurposeOfEquivalence(item.getPurposeOfEquivalence());

                            GroupEQNew equivalenceGroup = new GroupEQNew();
                            equivalenceGroup.setName(item.getGroupName());
                            equivalenceGroup.setId(Integer.parseInt(item.getGroupId()));
                            addModel.setGroup(equivalenceGroup);

                            addModel.setExaminationSystem(item.getExaminationSystem());
                            ExaminingBody examiningBody = new ExaminingBody();
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                examiningBody = Global.detailsEquivalenceNewModel.getResult().getExaminingBody().stream().filter(y -> y.getName().equals(item.getExaminingBody())).findFirst().get();

                            }
                            addModel.setExaminingBody(examiningBody);
                            addModel.setCountry(countryTemp);
                            addModel.setSession(item.getSession());

                            List<QualificationSubjectResponse> gradeList = new ArrayList<>();
                            Global.equivalenceQualificationList.add(addModel);

                            //Global.caseIdQualificationEQ = item.getCaseId().toString();
                            EquivalenceInitiateCase equivalenceInitiateCase = new EquivalenceInitiateCase();
                            equivalenceInitiateCase.setcCountryId(item.getCountryId());
                            equivalenceInitiateCase.setEmail(item.getEmail());
                            equivalenceInitiateCase.setPhone(item.getTelNo());
                            equivalenceInitiateCase.setFatherCnic(item.getFatherCnic());
                            equivalenceInitiateCase.setFatherAddress(item.getParentsPermanentAddress());
                            equivalenceInitiateCase.setFatherName(item.getFatherName());
                            equivalenceInitiateCase.setParentsEmployment(item.getPresentEmploymentOfParents());
                            Global.equivalenceInitiateCase = equivalenceInitiateCase;

                            DeleteParams deleteParams = new DeleteParams();
                            deleteParams.setCaseId(item.getCaseId());
                            deleteParams.setDocId(item.getDocId());
                            Global.deleteParams.add(deleteParams);

                        }

                        BottomLayout.setVisibility(View.GONE);
                        Global.stepNumberEQ = "5";
                        Intent intent = new Intent(getActivity(), ApplyEquivalenceActivity.class);
                        startActivity(intent);
                    }

                } else {
                    Global.utils.hideCustomLoadingDialog();
                    Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvIncompleteAppointmentsEQ.setLayoutManager(layoutManager);
        layoutBinding.rvIncompleteAppointmentsEQ.setHasFixedSize(true);
        incompleteAppointmentAdapterEQ = new IncompleteAppointmentAdapterEQ(getActivity());
        layoutBinding.rvIncompleteAppointmentsEQ.setAdapter(incompleteAppointmentAdapterEQ);
        incompleteAppointmentAdapterEQ.setItemClickListeners(this);

    }

    public void callViewIncompleteAppointmentEQApi(Activity activity) {
        if (Constants.isInternetConnected(activity)) {
            //Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("email", Global.userLoginResponse.getResult()
                    .getCustomerProfile().getEmail());
            params.addProperty("cnic", Global.userLoginResponse.getResult()
                    .getCustomerProfile().getCnic());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<ViewIncompleteAppointmentEQ> call = apiInterface.callViewIncompleteEQ(params);

            call.enqueue(new Callback<ViewIncompleteAppointmentEQ>() {
                @Override
                public void onResponse(Call<ViewIncompleteAppointmentEQ> call, Response<ViewIncompleteAppointmentEQ> response) {
                    //Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            Global.viewIncompleteAppointmentEQ = response.body();
                            //Global.utils.hideCustomLoadingDialog();

                            if (Global.viewIncompleteAppointmentEQ.getResult().getIncompleteResultDetails().size() > 0) {
                                layoutBinding.LayoutAppointmentsEQ.setVisibility(View.VISIBLE);
                                layoutBinding.LayoutNoAppointmentsEQ.setVisibility(View.GONE);
                            } else {
                                layoutBinding.LayoutNoAppointmentsEQ.setVisibility(View.VISIBLE);
                                layoutBinding.LayoutAppointmentsEQ.setVisibility(View.GONE);
                            }

                            /*incompleteAppointmentAdapterEQ = new IncompleteAppointmentAdapterEQ(getActivity());
                            rv_incompleteAppointmentsEQ.setAdapter(IncompleteAppt_Eq.incompleteAppointmentAdapterEQ);*/

                            //setting adapter:
                            setAdapter();

                        } else {
                            layoutBinding.LayoutNoAppointmentsEQ.setVisibility(View.VISIBLE);
                            layoutBinding.LayoutAppointmentsEQ.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ViewIncompleteAppointmentEQ> call, Throwable t) {
                    //Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callViewIncompleteDetailsEQApi(Activity activity, String caseID) {
        if (Constants.isInternetConnected(activity)) {
            //Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("case_id", caseID);

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<IncompleteDetailsEQ> call = apiInterface.callViewIncompleteDetailsEQ(params);

            call.enqueue(new Callback<IncompleteDetailsEQ>() {
                @Override
                public void onResponse(Call<IncompleteDetailsEQ> call, Response<IncompleteDetailsEQ> response) {
                    //Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            Global.incompleteDetailsEQResponse = response.body();
                            Global.utils.hideCustomLoadingDialog();
                            Global.UserIdForCase = response.body().getResult().getPaymentinfo().getUserId();

                            if (response.body().getResult().getStepNumber().equals("2")) {
                                Global.isIncompleteAppointmentEQ = true;
                                Global.equivalenceQualificationList.clear();
                                Global.isFromEquivalence = true;

                                for (int i = 0; i < response.body().getResult().getDocument().size(); i++) {
                                    Paymentinfo paymentinfo = new Paymentinfo();

                                    Document_EQ item = response.body().getResult().getDocument().get(i);

                                    AddQualificationModel addModel = new AddQualificationModel();
                                    EquivalenceGradingSystemEQNew equivalenceGradingSystem = new EquivalenceGradingSystemEQNew();
                                    equivalenceGradingSystem.setId(Integer.parseInt(item.getGradingSystemId()));
                                    equivalenceGradingSystem.setName(item.getGradingSystemName());
                                    //List<EquivalenceGrade> equivalenceGrades = new ArrayList<>();
                                    //equivalenceGradingSystem.setEquivalenceGrade(equivalenceGrades);

                                    //todo : adding images without travelling
                                    List<EquivalenceFileModel> imagesModelList = new ArrayList<>();
                                    for (int y = 0; y < item.getCaseUploadedDocumentResponse().size(); y++) {
                                        EquivalenceFileModel imagesModel = new EquivalenceFileModel();
                                        imagesModel.setUri(Uri.parse(item.getCaseUploadedDocumentResponse().get(y).getImagename()));
                                        imagesModel.setFileName(item.getCaseUploadedDocumentResponse().get(y).getImagename());
                                        imagesModel.setFileType(String.valueOf(item.getCaseUploadedDocumentResponse().get(y).getIspdf()));
                                        imagesModelList.add(imagesModel);
                                    }
                                    addModel.setSelectedFilesList(imagesModelList);

                                    //todo :adding travelling images in subjs
                                    List<EquivalenceTravelFileModel> imagesTravellingModelList = new ArrayList<>();
                                    for (int y = 0; y < item.getCaseUploadedTravellingDocumentResponse().size(); y++) {
                                        EquivalenceTravelFileModel imagesModel = new EquivalenceTravelFileModel();
                                        imagesModel.setUri(Uri.parse(item.getCaseUploadedTravellingDocumentResponse().get(y).getImagename()));
                                        imagesModel.setFileName(item.getCaseUploadedTravellingDocumentResponse().get(y).getImagename());
                                        imagesModel.setFileType(String.valueOf(item.getCaseUploadedTravellingDocumentResponse().get(y).getIspdf()));
                                        imagesTravellingModelList.add(imagesModel);
                                    }
                                    addModel.setSelectedTravelFilesList(imagesTravellingModelList);

                                    //todo : adding gradesss of subjects in list
                                    List<QualificationSubjectResponse> gradesEntered = new ArrayList<>();
                                    for (int y = 0; y < item.getQualificationSubjectResponse().size(); y++) {
                                        QualificationSubjectResponse GradesModel = new QualificationSubjectResponse();
                                        GradesModel.setSubjectName(item.getQualificationSubjectResponse().get(y).getSubjectName());
                                        GradesModel.setSubjectId(item.getQualificationSubjectResponse().get(y).getSubjectId());
                                        GradesModel.setMarksgrades(item.getQualificationSubjectResponse().get(y).getMarksgrades());
                                        gradesEntered.add(GradesModel);
                                    }
                                    addModel.setQualificationSubjectResponseList(gradesEntered);


                                    Country countryTemp = new Country();
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                        countryTemp = Global.detailsEquivalenceNewModel.getResult().getCountries().stream().filter(y -> y.getId().equals(item.getCountryId())).findFirst().get();
                                    }

                                    Qualification qualification = new Qualification();
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                        qualification = Global.detailsEquivalenceNewModel.getResult().getQualification().stream().filter(y -> y.getId().equals(item.getQualificationId())).findFirst().get();

                                    }

                                    addModel.setQualification(qualification);
                                    addModel.setGradingSystem(equivalenceGradingSystem);
                                    addModel.setPurposeOfEquivalence(item.getPurposeOfEquivalence());

                                    GroupEQNew equivalenceGroup = new GroupEQNew();
                                    equivalenceGroup.setName(item.getGroupName());
                                    equivalenceGroup.setId(Integer.parseInt(item.getGroupId()));
                                    addModel.setGroup(equivalenceGroup);


                                    addModel.setExaminationSystem(item.getExaminationSystem());
                                    ExaminingBody examiningBody = new ExaminingBody();
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                        examiningBody = Global.detailsEquivalenceNewModel.getResult().getExaminingBody().stream().filter(y -> y.getName().equals(item.getExaminingBody())).findFirst().get();

                                    }
                                    addModel.setExaminingBody(examiningBody);
                                    addModel.setCountry(countryTemp);
                                    addModel.setSession(item.getSession());

                                    List<QualificationSubjectResponse> gradeList = new ArrayList<>();
                                    Global.equivalenceQualificationList.add(addModel);

                                    EquivalenceInitiateCase equivalenceInitiateCase = new EquivalenceInitiateCase();
                                    equivalenceInitiateCase.setcCountryId(item.getCountryId());
                                    equivalenceInitiateCase.setEmail(item.getEmail());
                                    equivalenceInitiateCase.setPhone(item.getTelNo());
                                    equivalenceInitiateCase.setFatherCnic(item.getFatherCnic());
                                    equivalenceInitiateCase.setFatherAddress(item.getParentsPermanentAddress());
                                    equivalenceInitiateCase.setFatherName(item.getFatherName());
                                    equivalenceInitiateCase.setParentsEmployment(item.getPresentEmploymentOfParents());
                                    Global.equivalenceInitiateCase = equivalenceInitiateCase;

                                    DeleteParams deleteParams = new DeleteParams();
                                    deleteParams.setCaseId(item.getCaseId());
                                    deleteParams.setDocId(item.getDocId());
                                    Global.deleteParams.add(deleteParams);

                                }

                                BottomLayout.setVisibility(View.GONE);

                                Global.stepNumberEQ = "2";
                                Intent intent = new Intent(getActivity(), ApplyEquivalenceActivity.class);
                                startActivity(intent);
                            } else if (response.body().getResult().getStepNumber().equals("4")) {
                                Global.isIncompleteAppointmentEQ = true;
                                Global.equivalenceQualificationList.clear();
                                Global.isFromEquivalence = true;

                                for (int i = 0; i < response.body().getResult().getDocument().size(); i++) {
                                    Document_EQ item = response.body().getResult().getDocument().get(i);
                                    //Global.documentsTotalFee = Integer.parseInt(item.getDocumentDetailList().getAmount());

                                    AddQualificationModel addModel = new AddQualificationModel();
                                    EquivalenceGradingSystemEQNew equivalenceGradingSystem = new EquivalenceGradingSystemEQNew();
                                    equivalenceGradingSystem.setId(Integer.parseInt(item.getGradingSystemId()));
                                    equivalenceGradingSystem.setName(item.getGradingSystemName());
                                    //List<EquivalenceGrade> equivalenceGrades = new ArrayList<>();
                                    //equivalenceGradingSystem.setEquivalenceGrade(equivalenceGrades);

                                    List<EquivalenceFileModel> imagesModelList = new ArrayList<>();
                                    for (int y = 0; y < item.getCaseUploadedDocumentResponse().size(); y++) {
                                        EquivalenceFileModel imagesModel = new EquivalenceFileModel();
                                        imagesModel.setUri(Uri.parse(item.getCaseUploadedDocumentResponse().get(y).getImagename()));
                                        imagesModel.setFileName(item.getCaseUploadedDocumentResponse().get(y).getImagename());
                                        imagesModel.setFileType(String.valueOf(item.getCaseUploadedDocumentResponse().get(y).getIspdf()));
                                        imagesModelList.add(imagesModel);
                                    }
                                    addModel.setSelectedFilesList(imagesModelList);


                                    List<EquivalenceTravelFileModel> imagesTravellingModelList = new ArrayList<>();

                                    for (int y = 0; y < item.getCaseUploadedTravellingDocumentResponse().size(); y++) {
                                        EquivalenceTravelFileModel imagesModel = new EquivalenceTravelFileModel();
                                        imagesModel.setUri(Uri.parse(item.getCaseUploadedTravellingDocumentResponse().get(y).getImagename()));
                                        imagesModel.setFileName(item.getCaseUploadedTravellingDocumentResponse().get(y).getImagename());
                                        imagesModel.setFileType(String.valueOf(item.getCaseUploadedTravellingDocumentResponse().get(y).getIspdf()));
                                        imagesTravellingModelList.add(imagesModel);
                                    }
                                    addModel.setSelectedTravelFilesList(imagesTravellingModelList);

                                    //todo : adding gradesss of subjects in list
                                    List<QualificationSubjectResponse> gradesEntered = new ArrayList<>();
                                    for (int y = 0; y < item.getQualificationSubjectResponse().size(); y++) {
                                        QualificationSubjectResponse GradesModel = new QualificationSubjectResponse();
                                        GradesModel.setSubjectName(item.getQualificationSubjectResponse().get(y).getSubjectName());
                                        GradesModel.setSubjectId(item.getQualificationSubjectResponse().get(y).getSubjectId());
                                        GradesModel.setMarksgrades(item.getQualificationSubjectResponse().get(y).getMarksgrades());
                                        gradesEntered.add(GradesModel);
                                    }
                                    addModel.setQualificationSubjectResponseList(gradesEntered);

                                    Country countryTemp = new Country();
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                        countryTemp = Global.detailsEquivalenceNewModel.getResult().getCountries().stream().filter(y -> y.getId().equals(item.getCountryId())).findFirst().get();
                                    }

                                    Qualification qualification = new Qualification();
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                        qualification = Global.detailsEquivalenceNewModel.getResult().getQualification().stream().filter(y -> y.getId().equals(item.getQualificationId())).findFirst().get();

                                    }


                                    addModel.setQualification(qualification);
                                    addModel.setGradingSystem(equivalenceGradingSystem);
                                    addModel.setPurposeOfEquivalence(item.getPurposeOfEquivalence());


                                    GroupEQNew equivalenceGroup = new GroupEQNew();
                                    equivalenceGroup.setName(item.getGroupName());
                                    equivalenceGroup.setId(Integer.parseInt(item.getGroupId()));
                                    addModel.setGroup(equivalenceGroup);

                                    addModel.setExaminationSystem(item.getExaminationSystem());
                                    ExaminingBody examiningBody = new ExaminingBody();
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                        examiningBody = Global.detailsEquivalenceNewModel.getResult().getExaminingBody().stream().filter(y -> y.getName().equals(item.getExaminingBody())).findFirst().get();

                                    }

                                    String documenttype = item.getDocumentDetailList().getDocumentType();

                                    String isurgent = item.getDocumentDetailList().getUrgent();
                                    switch (documenttype) {
                                        case "New/First Time":
                                            if (isurgent == "true") {
                                                Global.documentsTotalFee += 6000;
                                                //    Global.documentAmountArray[i] = 6000;
                                            } else {
                                                Global.documentsTotalFee += 3000;
                                                //     Global.documentAmountArray[i] = 3000;
                                            }
                                            break;

                                        case "Duplicate":
                                            if (isurgent == "true") {
                                                Global.documentsTotalFee += 12000;
                                                //    Global.documentAmountArray[i] = 12000;
                                            } else {
                                                Global.documentsTotalFee += 6000;
                                                //    Global.documentAmountArray[i] = 6000;
                                            }
                                            break;

                                        case "1st Revision":
                                            if (isurgent == "true") {
                                                Global.documentsTotalFee += 12000;
                                                // Global.documentAmountArray[i] = 12000;
                                            } else {
                                                Global.documentsTotalFee += 6000;
                                                //  Global.documentAmountArray[i] = 6000;
                                            }
                                            break;

                                        case "2nd Revision":
                                            if (isurgent == "true") {
                                                Global.documentsTotalFee += 18000;
                                                //  Global.documentAmountArray[i] = 18000;
                                            } else {
                                                Global.documentsTotalFee += 9000;
                                                //    Global.documentAmountArray[i] = 9000;
                                            }
                                            break;

                                        case "3rd Revision":
                                            if (isurgent == "true") {
                                                Global.documentsTotalFee += 24000;
                                                //    Global.documentAmountArray[i] = 24000;
                                            } else {
                                                Global.documentsTotalFee += 12000;
                                                //     Global.documentAmountArray[i] = 12000;
                                            }
                                            break;
                                    }


                                    addModel.setExaminingBody(examiningBody);
                                    addModel.setCountry(countryTemp);
                                    addModel.setSession(item.getSession());

                                    List<QualificationSubjectResponse> gradeList = new ArrayList<>();
                                    Global.equivalenceQualificationList.add(addModel);

                                    EquivalenceInitiateCase equivalenceInitiateCase = new EquivalenceInitiateCase();
                                    equivalenceInitiateCase.setcCountryId(item.getCountryId());
                                    equivalenceInitiateCase.setEmail(item.getEmail());
                                    equivalenceInitiateCase.setPhone(item.getTelNo());
                                    equivalenceInitiateCase.setFatherCnic(item.getFatherCnic());
                                    equivalenceInitiateCase.setFatherAddress(item.getParentsPermanentAddress());
                                    equivalenceInitiateCase.setFatherName(item.getFatherName());
                                    equivalenceInitiateCase.setParentsEmployment(item.getPresentEmploymentOfParents());
                                    Global.equivalenceInitiateCase = equivalenceInitiateCase;

                                    DeleteParams deleteParams = new DeleteParams();
                                    deleteParams.setCaseId(item.getCaseId());
                                    deleteParams.setDocId(item.getDocId());
                                    Global.deleteParams.add(deleteParams);

                                }


                                BottomLayout.setVisibility(View.GONE);
                                Global.stepNumberEQ = "4";
                                Intent intent = new Intent(getActivity(), ApplyEquivalenceActivity.class);
                                startActivity(intent);
                            } else if (response.body().getResult().getStepNumber().equals("5")) {
                                Global.isIncompleteAppointmentEQ = true;
                                Global.equivalenceQualificationList.clear();
                                Global.isFromEquivalence = true;

                                for (int i = 0; i < response.body().getResult().getDocument().size(); i++) {
                                    Document_EQ item = response.body().getResult().getDocument().get(i);
                                    Global.documentsTotalFee = Integer.parseInt(item.getDocumentDetailList().getAmount());

                                    //Global.allDocumentsFeeEQ= item.getDocumentDetailList().getAmount();

                                    AddQualificationModel addModel = new AddQualificationModel();
                                    EquivalenceGradingSystemEQNew equivalenceGradingSystem = new EquivalenceGradingSystemEQNew();
                                    equivalenceGradingSystem.setId(Integer.parseInt(item.getGradingSystemId()));
                                    equivalenceGradingSystem.setName(item.getGradingSystemName());
                                    //List<EquivalenceGrade> equivalenceGrades = new ArrayList<>();

                                    //equivalenceGradingSystem.setEquivalenceGrade(equivalenceGrades);
                                    List<EquivalenceFileModel> imagesModelList = new ArrayList<>();
                                    for (int y = 0; y < item.getCaseUploadedDocumentResponse().size(); y++) {
                                        EquivalenceFileModel imagesModel = new EquivalenceFileModel();
                                        imagesModel.setUri(Uri.parse(item.getCaseUploadedDocumentResponse().get(y).getImagename()));
                                        imagesModel.setFileName(item.getCaseUploadedDocumentResponse().get(y).getImagename());
                                        imagesModel.setFileType(String.valueOf(item.getCaseUploadedDocumentResponse().get(y).getIspdf()));
                                        imagesModelList.add(imagesModel);
                                    }
                                    addModel.setSelectedFilesList(imagesModelList);


                                    List<EquivalenceTravelFileModel> imagesTravellingModelList = new ArrayList<>();
                                    for (int y = 0; y < item.getCaseUploadedTravellingDocumentResponse().size(); y++) {
                                        EquivalenceTravelFileModel imagesModel = new EquivalenceTravelFileModel();
                                        imagesModel.setUri(Uri.parse(item.getCaseUploadedTravellingDocumentResponse().get(y).getImagename()));
                                        imagesModel.setFileName(item.getCaseUploadedTravellingDocumentResponse().get(y).getImagename());
                                        imagesModel.setFileType(String.valueOf(item.getCaseUploadedTravellingDocumentResponse().get(y).getIspdf()));
                                        imagesTravellingModelList.add(imagesModel);
                                    }
                                    addModel.setSelectedTravelFilesList(imagesTravellingModelList);

                                    //todo : adding gradesss of subjects in list
                                    List<QualificationSubjectResponse> gradesEnteredlist = new ArrayList<>();
                                    for (int y = 0; y < item.getQualificationSubjectResponse().size(); y++) {
                                        QualificationSubjectResponse GradesModel = new QualificationSubjectResponse();
                                        GradesModel.setSubjectName(item.getQualificationSubjectResponse().get(y).getSubjectName());
                                        GradesModel.setSubjectId(item.getQualificationSubjectResponse().get(y).getSubjectId());
                                        GradesModel.setMarksgrades(item.getQualificationSubjectResponse().get(y).getMarksgrades());
                                        gradesEnteredlist.add(GradesModel);
                                    }
                                    addModel.setQualificationSubjectResponseList(gradesEnteredlist);


                                    Country countryTemp = new Country();
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                        countryTemp = Global.detailsEquivalenceNewModel.getResult().getCountries().stream().filter(y -> y.getId().equals(item.getCountryId())).findFirst().get();
                                    }

                                    Qualification qualification = new Qualification();
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                        qualification = Global.detailsEquivalenceNewModel.getResult().getQualification().stream().filter(y -> y.getId().equals(item.getQualificationId())).findFirst().get();

                                    }

                                    addModel.setQualification(qualification);
                                    addModel.setGradingSystem(equivalenceGradingSystem);
                                    addModel.setPurposeOfEquivalence(item.getPurposeOfEquivalence());

                                    GroupEQNew equivalenceGroup = new GroupEQNew();
                                    equivalenceGroup.setName(item.getGroupName());
                                    equivalenceGroup.setId(Integer.parseInt(item.getGroupId()));
                                    addModel.setGroup(equivalenceGroup);

                                    addModel.setExaminationSystem(item.getExaminationSystem());
                                    ExaminingBody examiningBody = new ExaminingBody();
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                        examiningBody = Global.detailsEquivalenceNewModel.getResult().getExaminingBody().stream().filter(y -> y.getName().equals(item.getExaminingBody())).findFirst().get();

                                    }
                                    addModel.setExaminingBody(examiningBody);
                                    addModel.setCountry(countryTemp);
                                    addModel.setSession(item.getSession());

                                    List<QualificationSubjectResponse> gradeList = new ArrayList<>();
                                    Global.equivalenceQualificationList.add(addModel);

                                    //Global.caseIdQualificationEQ = item.getCaseId().toString();
                                    EquivalenceInitiateCase equivalenceInitiateCase = new EquivalenceInitiateCase();
                                    equivalenceInitiateCase.setcCountryId(item.getCountryId());
                                    equivalenceInitiateCase.setEmail(item.getEmail());
                                    equivalenceInitiateCase.setPhone(item.getTelNo());
                                    equivalenceInitiateCase.setFatherCnic(item.getFatherCnic());
                                    equivalenceInitiateCase.setFatherAddress(item.getParentsPermanentAddress());
                                    equivalenceInitiateCase.setFatherName(item.getFatherName());
                                    equivalenceInitiateCase.setParentsEmployment(item.getPresentEmploymentOfParents());
                                    Global.equivalenceInitiateCase = equivalenceInitiateCase;

                                    DeleteParams deleteParams = new DeleteParams();
                                    deleteParams.setCaseId(item.getCaseId());
                                    deleteParams.setDocId(item.getDocId());
                                    Global.deleteParams.add(deleteParams);

                                }

                                BottomLayout.setVisibility(View.GONE);
                                Global.stepNumberEQ = "5";
                                Intent intent = new Intent(getActivity(), ApplyEquivalenceActivity.class);
                                startActivity(intent);
                            }

                        } else {
                            Global.utils.hideCustomLoadingDialog();
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<IncompleteDetailsEQ> call, Throwable t) {
                    //Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view, int pos, String str) {
        IncompleteResultDetail item = Global.viewIncompleteAppointmentEQ.getResult()
                .getIncompleteResultDetails().get(pos);
        final String caseId = item.getCaseId();
        final String caseStatus = item.getCaseStatus();
        final String centerId = item.getCenterId();

        if (str.equals("mEdit")) {
            Global.caseIdQualificationEQ = caseId;
            Global.caseId = caseId;
            Global.selEqCenter = centerId;

            Global.equivalenceGenerateAppCenter = new Center();
            for (int i = 0; i < Global.pdfResponse.getResult().getCenters().size(); i++) {
                Global.equivalenceGenerateAppCenter.setCallCourierId(Global.pdfResponse.getResult().getCenters().get(i).getCallCourierId());
                Global.equivalenceGenerateAppCenter.setPhoneNumber(Global.pdfResponse.getResult().getCenters().get(i).getPhoneNumber());
                Global.equivalenceGenerateAppCenter.setAddress(Global.pdfResponse.getResult().getCenters().get(i).getAddress());
                break;
            }

            //callViewIncompleteDetailsEQApi(getActivity(), caseId);
            viewModel.callViewIncompleteDetailsEQApi(getActivity(), caseId);

        }

    }
}