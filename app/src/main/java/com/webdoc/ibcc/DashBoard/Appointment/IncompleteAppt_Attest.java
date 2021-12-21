package com.webdoc.ibcc.DashBoard.Appointment;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.webdoc.ItemClickListeners;
import com.webdoc.ibcc.Adapter.IncompleteAppointmentAdapter;
import com.webdoc.ibcc.DashBoard.Account.Appointment.AppointmentViewModel.AppointmentViewModel;
import com.webdoc.ibcc.DashBoard.Dashboard;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.AttestationApplyActivity;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.DocumentCheckList.DocumentChecklisActivity;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.DocumentDetailModel;
import com.webdoc.ibcc.Model.DocumentSelectionModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.AddEducationResult.AddEducationResult;
import com.webdoc.ibcc.ResponseModels.AddEducationResult.EducationDetail;
import com.webdoc.ibcc.ResponseModels.AddEducationResult.Result;
import com.webdoc.ibcc.ResponseModels.CancelAppointmentResult.CancelAppointmentResult;
import com.webdoc.ibcc.ResponseModels.PdfResult.Center;
import com.webdoc.ibcc.ResponseModels.SavePaymentInfo.SavePaymentInfo;
import com.webdoc.ibcc.ResponseModels.ViewIncompleteDetailsResult.Document;
import com.webdoc.ibcc.ResponseModels.ViewIncompleteDetailsResult.ViewIncompleteDetails;
import com.webdoc.ibcc.ResponseModels.ViewIncompleteResult.IncompleteResultDetail;
import com.webdoc.ibcc.ResponseModels.ViewIncompleteResult.ViewIncompleteResult;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.FragIncompleteApptAttestBinding;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.webdoc.ibcc.DashBoard.Dashboard.BottomLayout;

public class IncompleteAppt_Attest extends Fragment implements ItemClickListeners {
    private IncompleteAppointmentAdapter upcomingAppointmentAdapter;
    private FragIncompleteApptAttestBinding layoutBinding;
    private AppointmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutBinding = FragIncompleteApptAttestBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(AppointmentViewModel.class);

        //bottomlayout imported from dashboard:
        BottomLayout.setVisibility(View.VISIBLE);
        //callViewIncompleteApi(getActivity());
        viewModel.callViewIncompleteApi(getActivity());

        observers();
        return layoutBinding.getRoot();
    }

    private void observers() {
        viewModel.getViewIncompleteResult().observe(this, response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Global.viewIncompleteResponse = response;

                    if (Global.viewIncompleteResponse.getResult().getIncompleteResultDetails().size() > 0) {
                        layoutBinding.LayoutAppointments.setVisibility(View.VISIBLE);
                        layoutBinding.LayoutNoAppointments.setVisibility(View.GONE);
                    } else {
                        layoutBinding.LayoutNoAppointments.setVisibility(View.VISIBLE);
                        layoutBinding.LayoutAppointments.setVisibility(View.GONE);
                    }

                    setAdapter();

                } else {
                    layoutBinding.LayoutNoAppointments.setVisibility(View.VISIBLE);
                    layoutBinding.LayoutAppointments.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getCancelAppointment().observe(this, response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Global.cancelAppointmentResponse = response;
                    Global.utils.hideCustomLoadingDialog();

                    Global.viewIncompleteResponse.getResult().getIncompleteResultDetails()
                            .remove(Global.cancelIncompleteApptPosition);
                    upcomingAppointmentAdapter.notifyDataSetChanged();

                    Global.utils.showSuccessSnakeBar(getActivity(), "Success");
                }
            }
        });

        viewModel.getViewIncompleteDetails().observe(this, response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Global.viewIncompleteDetailsResponse = response;
                    Global.utils.hideCustomLoadingDialog();

                    if (response.getResult().getStepNumber().equals("2")) {
                        Global.isIncompleteAppointment = true;
                        Result r = new Result();
                        List<EducationDetail> tempList = new ArrayList<>();
                        Global.selDocument.clear();

                        for (int i = 0; i < response.getResult().getDocument().size(); i++) {
                            Document item = response.getResult().getDocument().get(i);

                            String cert_name = null;
                            for (int k = 0; k < Global.pdfResponse.getResult().getCerftificates().size(); k++) {
                                if (item.getCertificateId().equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(k).getId().toString())) {
                                    cert_name = Global.pdfResponse.getResult().getCerftificates().get(k).getName();
                                    Log.e("R", cert_name);
                                    break;
                                }
                            }

                            String prog_name = null;
                            for (int l = 0; l < Global.pdfResponse.getResult().getCerftificates().size(); l++) {
                                if (item.getProgramId().equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().get(l).getId().toString())) {
                                    prog_name = Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().get(l).getName();
                                    break;
                                }
                            }

                            String board_name = null;
                            for (int m = 0; m < Global.pdfResponse.getResult().getBoards().size(); m++) {
                                if (item.getBoardId().equalsIgnoreCase(Global.pdfResponse.getResult().getBoards().get(m).getId().toString())) {
                                    board_name = Global.pdfResponse.getResult().getBoards().get(m).getName();
                                    break;
                                }
                            }

                            String group_name = null;
                            for (int m = 0; m < Global.pdfResponse.getResult().getCerftificates().size(); m++) {
                                if (item.getGroupId().equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(i).getGroups().get(m).getId().toString())) {
                                    group_name = Global.pdfResponse.getResult().getCerftificates().get(i).getGroups().get(m).getName();
                                    break;
                                }
                            }

                            EducationDetail educationDetail = new EducationDetail();
                            educationDetail.setCertificate(cert_name);
                            educationDetail.setProgram(prog_name);
                            educationDetail.setGroup(group_name);
                            educationDetail.setBoard(board_name);
                            educationDetail.setYear(item.getYear());
                            educationDetail.setSession("");
                            educationDetail.setCaseId(Global.incompleteCaseId);
                            educationDetail.setMarks(item.getMarks());
                            educationDetail.setTotal(item.getTotal());
                            educationDetail.setRollNumber(item.getRollNumber());
                            educationDetail.setDocId(item.getDocId());

                            DocumentSelectionModel documentSelectionModel = new DocumentSelectionModel();
                            documentSelectionModel.setBoard(board_name);
                            documentSelectionModel.setProgram(prog_name);
                            documentSelectionModel.setCertificate(cert_name);
                            documentSelectionModel.setDocId(item.getDocId());
                            documentSelectionModel.setCaseId(Global.incompleteCaseId);

                            List<DocumentDetailModel> docList = new ArrayList<>();
                            documentSelectionModel.setDetailModelList(docList);
                            Global.selDocument.add(documentSelectionModel);

                            tempList.add(educationDetail);
                        }

                        r.setEducationDetails(tempList);
                        AddEducationResult aer = new AddEducationResult();
                        aer.setResult(r);
                        Global.addEducationResponse = aer;
                        Global.enableEditEducation = true;
                        Global.enableAddDocument = true;
                        BottomLayout.setVisibility(View.GONE);

                        Global.stepNumber = "2";
                        Intent intent = new Intent(getActivity(), AttestationApplyActivity.class);
                        startActivity(intent);
                    } else if (response.getResult().getStepNumber().equals("4")) {
                        Global.isIncompleteAppointment = true;
                        Result r = new Result();
                        List<EducationDetail> tempList = new ArrayList<>();
                        Global.DMList.clear();
                        Global.selDocument.clear();

                        for (int i = 0; i < response.getResult().getDocument().size(); i++) {
                            Document item = response.getResult().getDocument().get(i);

                            String cert_name = null;
                            for (int k = 0; k < Global.pdfResponse.getResult().getCerftificates().size(); k++) {
                                if (item.getCertificateId().equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(k).getId().toString())) {
                                    cert_name = Global.pdfResponse.getResult().getCerftificates().get(k).getName();
                                    Log.e("R", cert_name);
                                    break;
                                }
                            }

                            String prog_name = null;
                            for (int l = 0; l < Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().size(); l++) {
                                if (item.getProgramId().equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().get(l).getId().toString())) {
                                    prog_name = Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().get(l).getName();
                                    break;
                                }
                            }

                            String group_name = null;
                            for (int m = 0; m < Global.pdfResponse.getResult().getCerftificates().get(i).getGroups().size(); m++) {
                                if (item.getGroupId().equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(i).getGroups().get(m).getId().toString())) {
                                    group_name = Global.pdfResponse.getResult().getCerftificates().get(i).getGroups().get(m).getName();
                                    break;
                                }
                            }

                            String board_name = null;
                            for (int m = 0; m < Global.pdfResponse.getResult().getBoards().size(); m++) {
                                if (item.getBoardId().equalsIgnoreCase(Global.pdfResponse.getResult().getBoards().get(m).getId().toString())) {
                                    board_name = Global.pdfResponse.getResult().getBoards().get(m).getName();
                                    break;
                                }
                            }

                            EducationDetail educationDetail = new EducationDetail();
                            educationDetail.setCertificate(cert_name);
                            educationDetail.setProgram(prog_name);
                            educationDetail.setGroup(group_name);
                            educationDetail.setBoard(board_name);
                            educationDetail.setYear(item.getYear());
                            educationDetail.setSession("");
                            educationDetail.setCaseId(Global.incompleteCaseId);
                            educationDetail.setMarks(item.getMarks());
                            educationDetail.setTotal(item.getTotal());
                            educationDetail.setRollNumber(item.getRollNumber());
                            educationDetail.setDocId(item.getDocId());

                            DocumentSelectionModel documentSelectionModel = new DocumentSelectionModel();
                            documentSelectionModel.setBoard(board_name);
                            documentSelectionModel.setProgram(prog_name);
                            documentSelectionModel.setCertificate(cert_name);
                            documentSelectionModel.setDocId(item.getDocId());
                            documentSelectionModel.setCaseId(Global.incompleteCaseId);
                            documentSelectionModel.setTotalAmount(item.getTotal());
                            List<DocumentDetailModel> docList = new ArrayList<>();
                            documentSelectionModel.setDetailModelList(docList);
                            Global.selDocument.add(documentSelectionModel);

                            for (int j = 0; j < item.getDocumentDetails().size(); j++) {
                                DocumentDetailModel documentDetailModel = new DocumentDetailModel();
                                documentDetailModel.setTitleCert(cert_name);
                                documentDetailModel.setDocumentType(item.getDocumentDetails().get(j).getDocumentType());
                                documentDetailModel.setDate(item.getDocumentDetails().get(j).getTicketDate());
                                documentDetailModel.setTicketNo(item.getDocumentDetails().get(j).getTicketNumber());
                                documentDetailModel.setTotalCopies(item.getDocumentDetails().get(j).getNoOfCopies());
                                documentDetailModel.setAmount(Integer.parseInt(item.getDocumentDetails().get(j).getAmount()));
                                documentDetailModel.setCertificateType(item.getDocumentDetails().get(j).getCertificateType());
                                documentDetailModel.setCertificateTypeID(item.getDocumentDetails().get(j).getDdId());
                                documentDetailModel.setTitleProg(prog_name);
                                documentDetailModel.setOriginalIncluded(item.getDocumentDetails().get(j).getOriginalIncluded());

                                Global.DMList.add(documentDetailModel);
                            }

                            Global.selDocument.get(i).setDetailModelList(Global.DMList);
                            tempList.add(educationDetail);
                        }

                        r.setEducationDetails(tempList);
                        AddEducationResult aer = new AddEducationResult();
                        aer.setResult(r);
                        Global.addEducationResponse = aer;
                        Global.enableEditEducation = true;
                        Global.enableAddDocument = true;
                        BottomLayout.setVisibility(View.GONE);

                        Global.stepNumber = "4";
                        Intent intent = new Intent(getActivity(), AttestationApplyActivity.class);
                        startActivity(intent);
                    } else if (response.getResult().getStepNumber().equals("5")) {
                        Global.isIncompleteAppointment = true;
                        Result r = new Result();
                        List<EducationDetail> tempList = new ArrayList<>();
                        Global.DMList.clear();
                        Global.selDocument.clear();

                        for (int i = 0; i < response.getResult().getDocument().size(); i++) {
                            Document item = response.getResult().getDocument().get(i);

                            String cert_name = null;
                            for (int k = 0; k < Global.pdfResponse.getResult().getCerftificates().size(); k++) {
                                if (item.getCertificateId().equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(k).getId().toString())) {
                                    cert_name = Global.pdfResponse.getResult().getCerftificates().get(k).getName();
                                    Log.e("R", cert_name);
                                    break;
                                }
                            }

                            String prog_name = null;
                            for (int l = 0; l < Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().size(); l++) {
                                if (item.getProgramId().equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().get(l).getId().toString())) {
                                    prog_name = Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().get(l).getName();
                                    break;
                                }
                            }

                            String group_name = null;
                            for (int m = 0; m < Global.pdfResponse.getResult().getCerftificates().get(i).getGroups().size(); m++) {
                                if (item.getGroupId().equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(i).getGroups().get(m).getId().toString())) {
                                    group_name = Global.pdfResponse.getResult().getCerftificates().get(i).getGroups().get(m).getName();
                                    break;
                                }
                            }

                            String board_name = null;
                            for (int m = 0; m < Global.pdfResponse.getResult().getBoards().size(); m++) {
                                if (item.getBoardId().equalsIgnoreCase(Global.pdfResponse.getResult().getBoards().get(m).getId().toString())) {
                                    board_name = Global.pdfResponse.getResult().getBoards().get(m).getName();
                                    break;
                                }
                            }

                            EducationDetail educationDetail = new EducationDetail();
                            educationDetail.setCertificate(cert_name);
                            educationDetail.setProgram(prog_name);
                            educationDetail.setGroup(group_name);
                            educationDetail.setBoard(board_name);
                            educationDetail.setYear(item.getYear());
                            educationDetail.setSession("");
                            educationDetail.setCaseId(Global.incompleteCaseId);
                            educationDetail.setMarks(item.getMarks());
                            educationDetail.setTotal(item.getTotal());
                            educationDetail.setRollNumber(item.getRollNumber());
                            educationDetail.setDocId(item.getDocId());

                            DocumentSelectionModel documentSelectionModel = new DocumentSelectionModel();
                            documentSelectionModel.setBoard(board_name);
                            documentSelectionModel.setProgram(prog_name);
                            documentSelectionModel.setCertificate(cert_name);
                            documentSelectionModel.setDocId(item.getDocId());
                            documentSelectionModel.setCaseId(Global.incompleteCaseId);
                            documentSelectionModel.setTotalAmount(item.getTotal());

                            List<DocumentDetailModel> docList = new ArrayList<>();
                            documentSelectionModel.setDetailModelList(docList);
                            Global.selDocument.add(documentSelectionModel);

                            for (int j = 0; j < item.getDocumentDetails().size(); j++) {
                                DocumentDetailModel documentDetailModel = new DocumentDetailModel();
                                documentDetailModel.setTitleCert(cert_name);
                                documentDetailModel.setDocumentType(item.getDocumentDetails().get(j).getDocumentType());
                                documentDetailModel.setDate(item.getDocumentDetails().get(j).getTicketDate());
                                documentDetailModel.setTicketNo(item.getDocumentDetails().get(j).getTicketNumber());
                                documentDetailModel.setTotalCopies(item.getDocumentDetails().get(j).getNoOfCopies());
                                documentDetailModel.setAmount(Integer.parseInt(item.getDocumentDetails().get(j).getAmount()));
                                documentDetailModel.setCertificateType(item.getDocumentDetails().get(j).getCertificateType());
                                documentDetailModel.setCertificateTypeID(item.getDocumentDetails().get(j).getDdId());
                                documentDetailModel.setTitleProg(prog_name);
                                documentDetailModel.setOriginalIncluded(item.getDocumentDetails().get(j).getOriginalIncluded());
                                /*documentDetailModel.setCertificateTypeID("1");*/

                                Global.DMList.add(documentDetailModel);
                            }

                            Global.selDocument.get(i).setDetailModelList(Global.DMList);

                            // Toast.makeText(this, Global.incompleteCaseId, Toast.LENGTH_SHORT).show();
                            tempList.add(educationDetail);
                        }

                        r.setEducationDetails(tempList);
                        AddEducationResult aer = new AddEducationResult();
                        aer.setResult(r);
                        Global.addEducationResponse = aer;

                        Global.enableEditEducation = true;
                        Global.enableAddDocument = true;
                        BottomLayout.setVisibility(View.GONE);

                        Intent intent = new Intent(getActivity(), DocumentChecklisActivity.class);
                        intent.putExtra("appointment_method", response.getResult().getPaymentinfo().getAppointmentMethod());
                        intent.putExtra("trx_id", response.getResult().getPaymentinfo().getTransactionid());
                        intent.putExtra("bank_name", response.getResult().getPaymentinfo().getBankname());
                        intent.putExtra("payment_status", response.getResult().getPaymentinfo().getPaymentstatus());

                        Global.savePaymentInfo = new SavePaymentInfo();
                        com.webdoc.ibcc.ResponseModels.SavePaymentInfo.Result result = new com.webdoc.ibcc.ResponseModels.SavePaymentInfo.Result();
                        Global.savePaymentInfo.setResult(result);
                        Global.savePaymentInfo.getResult().setId(response.getResult().getPaymentinfo().getWebviewid());

                        startActivity(intent);
                    }

                } else {
                    Global.utils.showErrorSnakeBar(getActivity(), response.getResult().getResponseMessage());
                }
            }
        });
    }

    private void setAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvIncompleteAppointments.setLayoutManager(layoutManager);
        layoutBinding.rvIncompleteAppointments.setHasFixedSize(true);
        upcomingAppointmentAdapter = new IncompleteAppointmentAdapter(getActivity());
        layoutBinding.rvIncompleteAppointments.setAdapter(upcomingAppointmentAdapter);
        upcomingAppointmentAdapter.setItemClickListeners(this);
    }

    @Override
    public void onClick(View view, int pos, String str) {
        IncompleteResultDetail item = Global.viewIncompleteResponse.getResult().getIncompleteResultDetails().get(pos);
        final String caseId = item.getCaseId();
        final String caseStatus = item.getCaseStatus();
        switch (str) {
            case "mEdit": {
                Global.incompleteCaseId = caseId;
                Global.caseId = caseId;
                Global.utils.showCustomLoadingDialog(getActivity());

                Global.attestationGenerateAppCenter = new Center();
                for (int i = 0; i < Global.pdfResponse.getResult().getCenters().size(); i++) {
                    if (Global.pdfResponse.getResult().getCenters().get(i).getName().equalsIgnoreCase(item.getCenter_id())) {
                        Global.attestationGenerateAppCenter.setCallCourierId(Global.pdfResponse.getResult().getCenters().get(i).getCallCourierId());
                        Global.attestationGenerateAppCenter.setPhoneNumber(Global.pdfResponse.getResult().getCenters().get(i).getPhoneNumber());
                        Global.attestationGenerateAppCenter.setAddress(Global.pdfResponse.getResult().getCenters().get(i).getAddress());
                        break;
                    }
                }

                //volleyRequestController.ViewIncompleteDetails(caseId);
                //callViewIncompleteDetailsApi(getActivity(), caseId);
                viewModel.callViewIncompleteDetailsApi(getActivity(), caseId);
            }
            break;

            case "mCancel": {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setContentText("Do you want to remove your record?")
                        .setConfirmText("Yes")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                Global.cancelIncompleteApptPosition = pos;
                                sDialog.dismissWithAnimation();

                                Global.utils.showCustomLoadingDialog(getActivity());

                                //volleyRequestController.CancelAppointment(caseId);
                                //callCancelAppointmentApi(getActivity(), caseId);
                                viewModel.callCancelAppointmentApi(getActivity(), caseId);
                            }
                        })
                        .setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        }).show();
            }
        }
    }

    public void callViewIncompleteApi(Activity activity) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("user_id", Global.userLoginResponse.getResult().getCustomerProfile().getId());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<ViewIncompleteResult> call = apiInterface.callViewIncomplete(params);

            call.enqueue(new Callback<ViewIncompleteResult>() {
                @Override
                public void onResponse(Call<ViewIncompleteResult> call, Response<ViewIncompleteResult> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            Global.viewIncompleteResponse = response.body();

                            if (Global.viewIncompleteResponse.getResult().getIncompleteResultDetails().size() > 0) {
                                layoutBinding.LayoutAppointments.setVisibility(View.VISIBLE);
                                layoutBinding.LayoutNoAppointments.setVisibility(View.GONE);
                            } else {
                                layoutBinding.LayoutNoAppointments.setVisibility(View.VISIBLE);
                                layoutBinding.LayoutAppointments.setVisibility(View.GONE);
                            }

                            /*upcomingAppointmentAdapter = new IncompleteAppointmentAdapter(getActivity());
                            rv_incompleteAppointments.setAdapter(IncompleteAppt_Attest.upcomingAppointmentAdapter);*/
                            setAdapter();

                        } else {
                            layoutBinding.LayoutNoAppointments.setVisibility(View.VISIBLE);
                            layoutBinding.LayoutAppointments.setVisibility(View.GONE);
                            // Global.utils.showErrorSnakeBar(this, "No record Found.");
                        }
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ViewIncompleteResult> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Oopps something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callCancelAppointmentApi(Activity activity, String caseId) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("case_id", caseId);
            params.addProperty("user_id", Global.userLoginResponse.getResult()
                    .getCustomerProfile().getId());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<CancelAppointmentResult> call = apiInterface.callCancelAppointment(params);

            call.enqueue(new Callback<CancelAppointmentResult>() {
                @Override
                public void onResponse(Call<CancelAppointmentResult> call, Response<CancelAppointmentResult> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            Global.cancelAppointmentResponse = response.body();
                            Global.utils.hideCustomLoadingDialog();

                            Global.viewIncompleteResponse.getResult().getIncompleteResultDetails()
                                    .remove(Global.cancelIncompleteApptPosition);
                            upcomingAppointmentAdapter.notifyDataSetChanged();

                            Global.utils.showSuccessSnakeBar(getActivity(), "Success");
                        }
                    } else {
                        Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CancelAppointmentResult> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Oopps something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callViewIncompleteDetailsApi(Activity activity, String caseId) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("case_id", caseId);

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<ViewIncompleteDetails> call = apiInterface.callViewIncompleteDetails(params);

            call.enqueue(new Callback<ViewIncompleteDetails>() {
                @Override
                public void onResponse(Call<ViewIncompleteDetails> call, Response<ViewIncompleteDetails> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            Global.viewIncompleteDetailsResponse = response.body();
                            Global.utils.hideCustomLoadingDialog();

                            if (response.body().getResult().getStepNumber().equals("2")) {
                                Global.isIncompleteAppointment = true;
                                Result r = new Result();
                                List<EducationDetail> tempList = new ArrayList<>();
                                Global.selDocument.clear();

                                for (int i = 0; i < response.body().getResult().getDocument().size(); i++) {
                                    Document item = response.body().getResult().getDocument().get(i);

                                    String cert_name = null;
                                    for (int k = 0; k < Global.pdfResponse.getResult().getCerftificates().size(); k++) {
                                        if (item.getCertificateId().equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(k).getId().toString())) {
                                            cert_name = Global.pdfResponse.getResult().getCerftificates().get(k).getName();
                                            Log.e("R", cert_name);
                                            break;
                                        }
                                    }

                                    String prog_name = null;
                                    for (int l = 0; l < Global.pdfResponse.getResult().getCerftificates().size(); l++) {
                                        if (item.getProgramId().equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().get(l).getId().toString())) {
                                            prog_name = Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().get(l).getName();
                                            break;
                                        }
                                    }

                                    String board_name = null;
                                    for (int m = 0; m < Global.pdfResponse.getResult().getBoards().size(); m++) {
                                        if (item.getBoardId().equalsIgnoreCase(Global.pdfResponse.getResult().getBoards().get(m).getId().toString())) {
                                            board_name = Global.pdfResponse.getResult().getBoards().get(m).getName();
                                            break;
                                        }
                                    }

                                    String group_name = null;
                                    for (int m = 0; m < Global.pdfResponse.getResult().getCerftificates().size(); m++) {
                                        if (item.getGroupId().equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(i).getGroups().get(m).getId().toString())) {
                                            group_name = Global.pdfResponse.getResult().getCerftificates().get(i).getGroups().get(m).getName();
                                            break;
                                        }
                                    }

                                    EducationDetail educationDetail = new EducationDetail();
                                    educationDetail.setCertificate(cert_name);
                                    educationDetail.setProgram(prog_name);
                                    educationDetail.setGroup(group_name);
                                    educationDetail.setBoard(board_name);
                                    educationDetail.setYear(item.getYear());
                                    educationDetail.setSession("");
                                    educationDetail.setCaseId(Global.incompleteCaseId);
                                    educationDetail.setMarks(item.getMarks());
                                    educationDetail.setTotal(item.getTotal());
                                    educationDetail.setRollNumber(item.getRollNumber());
                                    educationDetail.setDocId(item.getDocId());
                  /*    Global.educationDetail.setCertificate_id(item.getCertificateId());
                        Global.educationDetail.setProgram_id(item.getProgramId());
                        Global.educationDetail.setBoard_id(item.getBoardId());
                        Global.educationDetail.setGroup_id(item.getGroupId());*/

                                    DocumentSelectionModel documentSelectionModel = new DocumentSelectionModel();
                                    documentSelectionModel.setBoard(board_name);
                                    documentSelectionModel.setProgram(prog_name);
                                    documentSelectionModel.setCertificate(cert_name);
                                    documentSelectionModel.setDocId(item.getDocId());
                                    documentSelectionModel.setCaseId(Global.incompleteCaseId);

                                    List<DocumentDetailModel> docList = new ArrayList<>();
                                    documentSelectionModel.setDetailModelList(docList);
                                    Global.selDocument.add(documentSelectionModel);


                                    // Toast.makeText(this, Global.incompleteCaseId, Toast.LENGTH_SHORT).show();
                                    tempList.add(educationDetail);
                                }

                                r.setEducationDetails(tempList);
                                AddEducationResult aer = new AddEducationResult();
                                aer.setResult(r);
                                Global.addEducationResponse = aer;

                                //Global.enableEditEducation = false;
                                Global.enableEditEducation = true;

                                Global.enableAddDocument = true;
                                BottomLayout.setVisibility(View.GONE);

                                Global.stepNumber = "2";
                                Intent intent = new Intent(getActivity(), AttestationApplyActivity.class);
                                startActivity(intent);
                            } else if (response.body().getResult().getStepNumber().equals("4")) {
                                Global.isIncompleteAppointment = true;
                                Result r = new Result();
                                List<EducationDetail> tempList = new ArrayList<>();
                                Global.DMList.clear();
                                Global.selDocument.clear();

                                for (int i = 0; i < response.body().getResult().getDocument().size(); i++) {
                                    Document item = response.body().getResult().getDocument().get(i);

                                    String cert_name = null;
                                    for (int k = 0; k < Global.pdfResponse.getResult().getCerftificates().size(); k++) {
                                        if (item.getCertificateId().equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(k).getId().toString())) {
                                            cert_name = Global.pdfResponse.getResult().getCerftificates().get(k).getName();
                                            Log.e("R", cert_name);
                                            break;
                                        }
                                    }

                                    String prog_name = null;
                                    for (int l = 0; l < Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().size(); l++) {
                                        if (item.getProgramId().equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().get(l).getId().toString())) {
                                            prog_name = Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().get(l).getName();
                                            break;
                                        }
                                    }

                                    String group_name = null;
                                    for (int m = 0; m < Global.pdfResponse.getResult().getCerftificates().get(i).getGroups().size(); m++) {
                                        if (item.getGroupId().equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(i).getGroups().get(m).getId().toString())) {
                                            group_name = Global.pdfResponse.getResult().getCerftificates().get(i).getGroups().get(m).getName();
                                            break;
                                        }
                                    }

                                    String board_name = null;
                                    for (int m = 0; m < Global.pdfResponse.getResult().getBoards().size(); m++) {
                                        if (item.getBoardId().equalsIgnoreCase(Global.pdfResponse.getResult().getBoards().get(m).getId().toString())) {
                                            board_name = Global.pdfResponse.getResult().getBoards().get(m).getName();
                                            break;
                                        }
                                    }

                                    EducationDetail educationDetail = new EducationDetail();
                                    educationDetail.setCertificate(cert_name);
                                    educationDetail.setProgram(prog_name);
                                    educationDetail.setGroup(group_name);
                                    educationDetail.setBoard(board_name);
                                    educationDetail.setYear(item.getYear());
                                    educationDetail.setSession("");
                                    educationDetail.setCaseId(Global.incompleteCaseId);
                                    educationDetail.setMarks(item.getMarks());
                                    educationDetail.setTotal(item.getTotal());
                                    educationDetail.setRollNumber(item.getRollNumber());
                                    educationDetail.setDocId(item.getDocId());

                                    DocumentSelectionModel documentSelectionModel = new DocumentSelectionModel();
                                    documentSelectionModel.setBoard(board_name);
                                    documentSelectionModel.setProgram(prog_name);
                                    documentSelectionModel.setCertificate(cert_name);
                                    documentSelectionModel.setDocId(item.getDocId());
                                    documentSelectionModel.setCaseId(Global.incompleteCaseId);
                                    documentSelectionModel.setTotalAmount(item.getTotal());
                                    List<DocumentDetailModel> docList = new ArrayList<>();
                                    documentSelectionModel.setDetailModelList(docList);
                                    Global.selDocument.add(documentSelectionModel);

                                    for (int j = 0; j < item.getDocumentDetails().size(); j++) {
                                        DocumentDetailModel documentDetailModel = new DocumentDetailModel();
                                        documentDetailModel.setTitleCert(cert_name);
                                        documentDetailModel.setDocumentType(item.getDocumentDetails().get(j).getDocumentType());
                                        documentDetailModel.setDate(item.getDocumentDetails().get(j).getTicketDate());
                                        documentDetailModel.setTicketNo(item.getDocumentDetails().get(j).getTicketNumber());
                                        documentDetailModel.setTotalCopies(item.getDocumentDetails().get(j).getNoOfCopies());
                                        documentDetailModel.setAmount(Integer.parseInt(item.getDocumentDetails().get(j).getAmount()));
                                        documentDetailModel.setCertificateType(item.getDocumentDetails().get(j).getCertificateType());
                                        documentDetailModel.setCertificateTypeID(item.getDocumentDetails().get(j).getDdId());
                                        documentDetailModel.setTitleProg(prog_name);
                                        documentDetailModel.setOriginalIncluded(item.getDocumentDetails().get(j).getOriginalIncluded());

                                        /*documentDetailModel.setCertificateTypeID("1");*/

                                        Global.DMList.add(documentDetailModel);
                                    }

                                    Global.selDocument.get(i).setDetailModelList(Global.DMList);

                                    // Toast.makeText(this, Global.incompleteCaseId, Toast.LENGTH_SHORT).show();
                                    tempList.add(educationDetail);
                                }

                                r.setEducationDetails(tempList);
                                AddEducationResult aer = new AddEducationResult();
                                aer.setResult(r);
                                Global.addEducationResponse = aer;

                                //Global.enableEditEducation = false;
                                Global.enableEditEducation = true;
                                Global.enableAddDocument = true;
                                BottomLayout.setVisibility(View.GONE);

                                Global.stepNumber = "4";
                                Intent intent = new Intent(getActivity(), AttestationApplyActivity.class);
                                startActivity(intent);
                            } else if (response.body().getResult().getStepNumber().equals("5")) {
                                Global.isIncompleteAppointment = true;
                                Result r = new Result();
                                List<EducationDetail> tempList = new ArrayList<>();
                                Global.DMList.clear();
                                Global.selDocument.clear();

                                for (int i = 0; i < response.body().getResult().getDocument().size(); i++) {
                                    Document item = response.body().getResult().getDocument().get(i);

                                    String cert_name = null;
                                    for (int k = 0; k < Global.pdfResponse.getResult().getCerftificates().size(); k++) {
                                        if (item.getCertificateId().equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(k).getId().toString())) {
                                            cert_name = Global.pdfResponse.getResult().getCerftificates().get(k).getName();
                                            Log.e("R", cert_name);
                                            break;
                                        }
                                    }

                                    String prog_name = null;
                                    for (int l = 0; l < Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().size(); l++) {
                                        if (item.getProgramId().equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().get(l).getId().toString())) {
                                            prog_name = Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().get(l).getName();
                                            break;
                                        }
                                    }

                                    String group_name = null;
                                    for (int m = 0; m < Global.pdfResponse.getResult().getCerftificates().get(i).getGroups().size(); m++) {
                                        if (item.getGroupId().equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(i).getGroups().get(m).getId().toString())) {
                                            group_name = Global.pdfResponse.getResult().getCerftificates().get(i).getGroups().get(m).getName();
                                            break;
                                        }
                                    }

                                    String board_name = null;
                                    for (int m = 0; m < Global.pdfResponse.getResult().getBoards().size(); m++) {
                                        if (item.getBoardId().equalsIgnoreCase(Global.pdfResponse.getResult().getBoards().get(m).getId().toString())) {
                                            board_name = Global.pdfResponse.getResult().getBoards().get(m).getName();
                                            break;
                                        }
                                    }

                                    EducationDetail educationDetail = new EducationDetail();
                                    educationDetail.setCertificate(cert_name);
                                    educationDetail.setProgram(prog_name);
                                    educationDetail.setGroup(group_name);
                                    educationDetail.setBoard(board_name);
                                    educationDetail.setYear(item.getYear());
                                    educationDetail.setSession("");
                                    educationDetail.setCaseId(Global.incompleteCaseId);
                                    educationDetail.setMarks(item.getMarks());
                                    educationDetail.setTotal(item.getTotal());
                                    educationDetail.setRollNumber(item.getRollNumber());
                                    educationDetail.setDocId(item.getDocId());

                                    DocumentSelectionModel documentSelectionModel = new DocumentSelectionModel();
                                    documentSelectionModel.setBoard(board_name);
                                    documentSelectionModel.setProgram(prog_name);
                                    documentSelectionModel.setCertificate(cert_name);
                                    documentSelectionModel.setDocId(item.getDocId());
                                    documentSelectionModel.setCaseId(Global.incompleteCaseId);
                                    documentSelectionModel.setTotalAmount(item.getTotal());

                                    List<DocumentDetailModel> docList = new ArrayList<>();
                                    documentSelectionModel.setDetailModelList(docList);
                                    Global.selDocument.add(documentSelectionModel);


                                    //List<DocumentDetailModel> DMList = new ArrayList<>();

                                    for (int j = 0; j < item.getDocumentDetails().size(); j++) {
                                        DocumentDetailModel documentDetailModel = new DocumentDetailModel();
                                        documentDetailModel.setTitleCert(cert_name);
                                        documentDetailModel.setDocumentType(item.getDocumentDetails().get(j).getDocumentType());
                                        documentDetailModel.setDate(item.getDocumentDetails().get(j).getTicketDate());
                                        documentDetailModel.setTicketNo(item.getDocumentDetails().get(j).getTicketNumber());
                                        documentDetailModel.setTotalCopies(item.getDocumentDetails().get(j).getNoOfCopies());
                                        documentDetailModel.setAmount(Integer.parseInt(item.getDocumentDetails().get(j).getAmount()));
                                        documentDetailModel.setCertificateType(item.getDocumentDetails().get(j).getCertificateType());
                                        documentDetailModel.setCertificateTypeID(item.getDocumentDetails().get(j).getDdId());
                                        documentDetailModel.setTitleProg(prog_name);
                                        documentDetailModel.setOriginalIncluded(item.getDocumentDetails().get(j).getOriginalIncluded());
                                        /*documentDetailModel.setCertificateTypeID("1");*/

                                        Global.DMList.add(documentDetailModel);
                                    }

                                    Global.selDocument.get(i).setDetailModelList(Global.DMList);

                                    // Toast.makeText(this, Global.incompleteCaseId, Toast.LENGTH_SHORT).show();
                                    tempList.add(educationDetail);
                                }

                                r.setEducationDetails(tempList);
                                AddEducationResult aer = new AddEducationResult();
                                aer.setResult(r);
                                Global.addEducationResponse = aer;

                                Global.enableEditEducation = true;
                                Global.enableAddDocument = true;
                                BottomLayout.setVisibility(View.GONE);

                                Intent intent = new Intent(getActivity(), DocumentChecklisActivity.class);
                                intent.putExtra("appointment_method", response.body().getResult().getPaymentinfo().getAppointmentMethod());
                                intent.putExtra("trx_id", response.body().getResult().getPaymentinfo().getTransactionid());
                                intent.putExtra("bank_name", response.body().getResult().getPaymentinfo().getBankname());
                                intent.putExtra("payment_status", response.body().getResult().getPaymentinfo().getPaymentstatus());

                                Global.savePaymentInfo = new SavePaymentInfo();
                                com.webdoc.ibcc.ResponseModels.SavePaymentInfo.Result result = new com.webdoc.ibcc.ResponseModels.SavePaymentInfo.Result();
                                Global.savePaymentInfo.setResult(result);
                                Global.savePaymentInfo.getResult().setId(response.body().getResult().getPaymentinfo().getWebviewid());

                                startActivity(intent);
                            }

                        } else {
                            Global.utils.hideCustomLoadingDialog();
                            Global.utils.showErrorSnakeBar(getActivity(), response.body().getResult().getResponseMessage());
                        }
                    } else {
                        Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ViewIncompleteDetails> call, Throwable t) {
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