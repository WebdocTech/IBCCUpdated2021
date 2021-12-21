package com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.EducationDetails;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.webdoc.ItemClickListeners;
import com.webdoc.ibcc.Adapter.EducationDetailsAdapter;
import com.webdoc.ibcc.Adapter.Spinner.SpinnerBoardAdapter;
import com.webdoc.ibcc.Adapter.Spinner.SpinnerCertificateAdapter;
import com.webdoc.ibcc.Adapter.Spinner.Spinner_Cert_GroupAdapter;
import com.webdoc.ibcc.Adapter.Spinner.Spinner_Cert_ProgramAdapter;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.AttestationApplyActivity;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.AttestationReceipt;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.CallCourier.CallCourier;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.DocumentSelection.DocumentSelectionFragment;
import com.webdoc.ibcc.DashBoard.Home.HomeSharedViewModel.HomeSharedViewModel;
import com.webdoc.ibcc.DataModel.EducationDetailModel;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.DocumentDetailModel;
import com.webdoc.ibcc.Model.DocumentSelectionModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.AddEducationResult.AddEducationResult;
import com.webdoc.ibcc.ResponseModels.AddEducationResult.EducationDetail;
import com.webdoc.ibcc.ResponseModels.DeleteEducationResult.DeleteEducationResult;
import com.webdoc.ibcc.ResponseModels.EditEducationResult.EditEducationResult;
import com.webdoc.ibcc.ResponseModels.PdfResult.Board;
import com.webdoc.ibcc.ResponseModels.PdfResult.Cerftificate;
import com.webdoc.ibcc.ResponseModels.PdfResult.Program;
import com.webdoc.ibcc.ResponseModels.SaveCourierDetials.SaveCourierDetials;
import com.webdoc.ibcc.ResponseModels.Step1Result.Step1Result;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.FragmentEducationDetailsBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EducationDetailsFragment extends Fragment implements ItemClickListeners {
    public static AlertDialog addEducationAlertDialog;
    private String marks_obtained, total_marks, roll_no;
    public EducationDetailsAdapter educationDetailsAdapter;
    private String board_Id, board_name, cert_name, cert_Id, program_name, program_Id,
            group_name, group_Id, passing_year, session;
    public Spinner spinner_program, spinner_group;
    private int selectedCertificateIndex;
    private AlertDialog updateEducationAlertDialog;
    private FragmentEducationDetailsBinding layoutBinding;
    private HomeSharedViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutBinding = FragmentEducationDetailsBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(HomeSharedViewModel.class);

        if (Global.selDocument != null) {
            if (Global.selDocument.size() > 0) {
                layoutBinding.NoEducationRecord.setVisibility(View.GONE);
            } else {
                layoutBinding.NoEducationRecord.setVisibility(View.VISIBLE);
            }
        } else {
            layoutBinding.NoEducationRecord.setVisibility(View.VISIBLE);
        }

        //RECYCLER VIEW
        setAdapter();
        clickListeners();
        observers();
        return layoutBinding.getRoot();
    }

    private void observers() {
        viewModel.getGetDeleteEduction().observe(getActivity(), response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Global.deleteEducationResponse = response;
                    Global.utils.hideCustomLoadingDialog();

                    Global.addEducationResponse.getResult().getEducationDetails().remove(Global.deleteEduPosition);
                    Global.selDocument.remove(Global.deleteEduPosition);

                    educationDetailsAdapter.notifyDataSetChanged();
                    if (Global.addEducationResponse.getResult().getEducationDetails().size() == 0) {
                        layoutBinding.NoEducationRecord.setVisibility(View.VISIBLE);
                    }

                    Global.utils.showSuccessSnakeBar(getActivity(), "Deleted Successfully");
                } else {
                    Global.utils.hideCustomLoadingDialog();
                    Global.utils.showErrorSnakeBar(getActivity(), response.getResult().getResponseMessage());
                }
            }
        });

        viewModel.getGetEditEduction().observe(getActivity(), response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Global.editEducationResponse = response;

                    Global.educationDetail.setCertificate(Global.userEduDetail.getCertificate());
                    Global.educationDetail.setProgram(Global.userEduDetail.getProgram());
                    Global.educationDetail.setGroup(Global.userEduDetail.getGroup());
                    Global.educationDetail.setBoard(Global.userEduDetail.getBoard());
                    Global.educationDetail.setYear(Global.userEduDetail.getPassing_year());
                    Global.educationDetail.setMarks(Global.userEduDetail.getMarks_obtained());
                    Global.educationDetail.setTotal(Global.userEduDetail.getTotal_marks());
                    Global.educationDetail.setSession(Global.userEduDetail.getSession());
                    Global.educationDetail.setRollNumber(Global.userEduDetail.getRoll_number());
                    Global.educationDetail.setCaseId(Global.userEduDetail.getCase_id());
                    Global.educationDetail.setDocId(Global.userEduDetail.getDocId());

                    Global.addEducationResponse.getResult().getEducationDetails().set(Global.editEduPosition, Global.educationDetail);

                    educationDetailsAdapter.notifyItemChanged(Global.editEduPosition);
                    updateEducationAlertDialog.dismiss();

                    Global.utils.showSuccessSnakeBar(getActivity(), "Edited Successfully");

                } else {
                    updateEducationAlertDialog.dismiss();
                    Global.utils.showErrorSnakeBar(getActivity(), response.getResult().getResponseMessage());
                }
            }
        });

        viewModel.getGetAddEduction().observe(getActivity(), response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Global.addEducationResponse = response;

                    for (int i = 0; i < response.getResult().getEducationDetails().size(); i++) {
                        Global.selDocument.get(i).setDocId(response.getResult().getEducationDetails().get(i).getDocId());
                    }

                    layoutBinding.NoEducationRecord.setVisibility(View.GONE);
                    addEducationAlertDialog.dismiss();

                    educationDetailsAdapter = new EducationDetailsAdapter(getActivity());
                    layoutBinding.rvEducationDetails.setAdapter(educationDetailsAdapter);

                    Global.utils.showSuccessSnakeBar(getActivity(), "Added Successfully");

                } else {
                    EducationDetailsFragment.addEducationAlertDialog.dismiss();
                    Global.utils.showErrorSnakeBar(getActivity(), response.getResult().getResponseMessage());
                }
            }
        });
    }

    private void clickListeners() {
        layoutBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AttestationApplyActivity.stepIndicator.setCurrentStepPosition(2);
                } catch (Exception e) {
                }

                if (Global.selDocument.size() > 0) {
                    Global.enableEditEducation = true;

                    Fragment documentSelectionFragment = new DocumentSelectionFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, documentSelectionFragment).addToBackStack(null).commit();
                } else {
                    Global.utils.showErrorSnakeBar(getActivity(), "Education Not Added");
                }
            }
        });

        layoutBinding.btnAddEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.enableEditEducation) {
                    showAddEducationDialog();

                } else {
                    Global.utils.showErrorSnakeBar(getActivity(), "You are not allowed to add education");
                }
            }
        });
    }

    private void setAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvEducationDetails.setLayoutManager(layoutManager);
        layoutBinding.rvEducationDetails.setHasFixedSize(true);
        educationDetailsAdapter = new EducationDetailsAdapter(getActivity());
        layoutBinding.rvEducationDetails.setAdapter(educationDetailsAdapter);
        educationDetailsAdapter.setItemClickListeners(this);
    }

    @Override
    public void onClick(View view, int pos, String str) {
        EducationDetail myListData = Global.addEducationResponse.getResult().getEducationDetails().get(pos);

        switch (str) {
            case "mRemove": {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setContentText("Do you want to remove this record?")
                        .setConfirmText("Yes")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                Global.deleteEduPosition = pos;
                                sDialog.dismissWithAnimation();

                                //Global.utils.showCustomLoadingDialog(getActivity());
                                //volleyRequestController.DeleteEducation(myListData.getDocId(), Global.caseId);
                                //callDeleteEducationApi(getActivity(), myListData.getDocId(), Global.caseId);
                                viewModel.callDeleteEducationApi(getActivity(), myListData.getDocId(), Global.caseId);

                                for (int i = 0; i < Global.pdfResponse.getResult().getCerftificates().size(); i++) {
                                    if (myListData.getCertificate().equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(i).getName())) {
                                        Global.selectedCerti.add(Global.pdfResponse.getResult().getCerftificates().get(i));
                                        break;
                                    }
                                }

                            }
                        })
                        .setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
            break;

            case "mEdit": {
                if (Global.enableEditEducation) {
                    showUpdateEducationDialog(pos, myListData.getYear(), myListData.getSession(), myListData.getMarks(),
                            myListData.getTotal(), myListData.getRollNumber(), myListData.getDocId());
                    Global.editEduPosition = pos;
                } else {
                    Global.utils.showErrorSnakeBar(getActivity(), "You are not allowed to edit education");
                }
            }
        }
    }

    public void showUpdateEducationDialog(int pos, String e_passing_year, String e_session,
                                          String e_marks_obtained, String e_total_marks, String e_roll_no,
                                          String docId) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        View v = getActivity().getLayoutInflater().inflate(R.layout.alert_add_education, null);
        dialogBuilder.setView(v);

        updateEducationAlertDialog = dialogBuilder.create();
        updateEducationAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tv_educationDetails = v.findViewById(R.id.tv_educationDetails);
        Spinner spinner_certificate = v.findViewById(R.id.spinner_certificate);
        Spinner spinner_program = v.findViewById(R.id.spinner_program);
        Spinner spinner_group = v.findViewById(R.id.spinner_group);
        Spinner spinner_board = v.findViewById(R.id.spinner_board);
        Spinner spinner_passing_year = v.findViewById(R.id.spinner_passing_year);
        Spinner spinner_session = v.findViewById(R.id.spinner_session);
        EditText et_marks_obtained = v.findViewById(R.id.et_marks_obtained);
        EditText et_total_marks = v.findViewById(R.id.et_total_marks);
        EditText et_roll_no = v.findViewById(R.id.et_roll_no);
        Button btn_add = v.findViewById(R.id.btn_add);
        ImageView iv_cancel = v.findViewById(R.id.iv_cancel);
        TextView tv_program = v.findViewById(R.id.tv_program);
        TextView tv_group = v.findViewById(R.id.tv_group);
        TextView tv_certificate = v.findViewById(R.id.tv_certificate);
        ConstraintLayout ProgramLayout = v.findViewById(R.id.ProgramLayout);
        ConstraintLayout GroupLayout = v.findViewById(R.id.GroupLayout);

        spinner_program.setEnabled(false);
        spinner_group.setEnabled(false);
        spinner_certificate.setEnabled(false);

        spinner_certificate.setVisibility(View.GONE);
        spinner_program.setVisibility(View.GONE);
        spinner_group.setVisibility(View.GONE);

        tv_program.setVisibility(View.VISIBLE);
        tv_group.setVisibility(View.VISIBLE);
        tv_certificate.setVisibility(View.VISIBLE);

        tv_certificate.setText(Global.addEducationResponse.getResult().getEducationDetails().get(pos).getCertificate());
        tv_program.setText(Global.addEducationResponse.getResult().getEducationDetails().get(pos).getProgram());
        tv_group.setText(Global.addEducationResponse.getResult().getEducationDetails().get(pos).getGroup());


        tv_educationDetails.setText("Edit Education Details");
        btn_add.setText("Update");
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEducationAlertDialog.dismiss();
            }
        });

        //CERTIFICATE SPINNER
        SpinnerCertificateAdapter arrayAdapter4 = new SpinnerCertificateAdapter(getActivity(), R.layout.spinner_item, Global.pdfResponse.getResult().getCerftificates());
        spinner_certificate.setAdapter(arrayAdapter4);

        Cerftificate selectedCertificate = null;
        for (int i = 0; i < Global.pdfResponse.getResult().getCerftificates().size(); i++) {
            if (Global.userEduDetail.getCertificate_id().equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(i).getId().toString())) {
                selectedCertificate = Global.pdfResponse.getResult().getCerftificates().get(i);

                Toast.makeText(getActivity(), selectedCertificate + "--" + Global.addEducationResponse.getResult().getEducationDetails().get(pos) + "--" + Global.userEduDetail.getCertificate_id(), Toast.LENGTH_SHORT).show();
                break;
            }
        }
        int spinner_certificatePosition = arrayAdapter4.getPosition(selectedCertificate);
        spinner_certificate.setSelection(spinner_certificatePosition);

        spinner_certificate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                cert_name = Global.pdfResponse.getResult().getCerftificates().get(position).getName();
                cert_Id = Global.pdfResponse.getResult().getCerftificates().get(position).getId().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        //PROGRAM SPINNER
        Spinner_Cert_ProgramAdapter arrayAdapter5 = new Spinner_Cert_ProgramAdapter(getActivity(), Global.selectedCertificate.getPrograms());
        spinner_program.setAdapter(arrayAdapter5);
        Program selectedProgram = null;
        for (int i = 0; i < Global.pdfResponse.getResult().getCerftificates().size(); i++) {
            for (int j = 0; j < Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().size(); j++) {
                if (Global.userEduDetail.getProgram_id().equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().get(j).getId().toString())) {
                    selectedProgram = Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().get(j);
                    break;
                }
            }
        }
        int spinner_programPosition = arrayAdapter5.getPosition(selectedProgram);
        spinner_program.setSelection(spinner_programPosition);


        spinner_program.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                program_name = Global.selectedCertificate.getPrograms().get(position).getName();
                program_Id = Global.selectedCertificate.getPrograms().get(position).getId().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        //GROUP ADAPTER
        Spinner_Cert_GroupAdapter arrayAdapter6 = new Spinner_Cert_GroupAdapter(getActivity(), Global.selectedCertificate.getGroups());
        spinner_group.setAdapter(arrayAdapter6);
        spinner_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                group_name = Global.selectedCertificate.getGroups().get(position).getName();
                group_Id = Global.selectedCertificate.getGroups().get(position).getId().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });


        //BOARD SPINNER
        SpinnerBoardAdapter arrayAdapter3 = new SpinnerBoardAdapter(getActivity(), R.layout.spinner_item, Global.pdfResponse.getResult().getBoards());
        spinner_board.setAdapter(arrayAdapter3);

        Board selectedBoard = null;
        for (int i = 0; i < Global.pdfResponse.getResult().getBoards().size(); i++) {
            if (Global.userEduDetail.getBoard_id().equalsIgnoreCase(Global.pdfResponse.getResult().getBoards().get(i).getId().toString())) {
                selectedBoard = Global.pdfResponse.getResult().getBoards().get(i);
                break;
            }
        }
        int spinner_boardPosition = arrayAdapter3.getPosition(selectedBoard);
        spinner_board.setSelection(spinner_boardPosition);

        spinner_board.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                board_name = Global.pdfResponse.getResult().getBoards().get(position).getName();
                board_Id = Global.pdfResponse.getResult().getBoards().get(position).getId().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        //PASSING YEAR
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, years);
        spinner_passing_year.setAdapter(adapter);

        int spinner_passing_yearPosition = adapter.getPosition(e_passing_year);
        spinner_passing_year.setSelection(spinner_passing_yearPosition);

        spinner_passing_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                passing_year = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        //SESSION
        ArrayAdapter<CharSequence> spinner_session_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.title_session, R.layout.spinner_item);
        //spinner_session_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_session.setAdapter(spinner_session_adapter);
        int spinner_sessionPosition = spinner_session_adapter.getPosition(e_session);
        spinner_session.setSelection(spinner_sessionPosition);

        spinner_session.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                session = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });


        et_marks_obtained.setText(e_marks_obtained);
        et_total_marks.setText(e_total_marks);
        et_roll_no.setText(e_roll_no);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_marks_obtained.getText())) {
                    et_marks_obtained.setError("Field cannot be empty");
                    et_marks_obtained.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(et_total_marks.getText())) {
                    et_total_marks.setError("Field cannot be empty");
                    et_total_marks.requestFocus();
                    return;
                }

                roll_no = et_roll_no.getText().toString();

                if (Integer.parseInt(et_marks_obtained.getText().toString()) > Integer.parseInt(et_total_marks.getText().toString())) {
                    et_marks_obtained.setError("Obtained marks is greater than total marks");
                    et_marks_obtained.requestFocus();
                    return;
                } else {
                    marks_obtained = et_marks_obtained.getText().toString();
                    total_marks = et_total_marks.getText().toString();
                }

                Global.userEduDetail.setCertificate(Global.addEducationResponse.getResult().getEducationDetails().get(pos).getCertificate());
                Global.userEduDetail.setProgram(Global.addEducationResponse.getResult().getEducationDetails().get(pos).getProgram());
                Global.userEduDetail.setGroup(Global.addEducationResponse.getResult().getEducationDetails().get(pos).getGroup());

                //3,7,12
                String certID = null;
                String progID = null;
                String groupID = null;
                for (int i = 0; i < Global.pdfResponse.getResult().getCerftificates().size(); i++) {
                    if (Global.pdfResponse.getResult().getCerftificates().get(i).getName().equalsIgnoreCase(Global.addEducationResponse.getResult().getEducationDetails().get(pos).getCertificate())) {
                        certID = Global.pdfResponse.getResult().getCerftificates().get(i).getId().toString();

                        for (int j = 0; j < Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().size(); j++) {
                            if (Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().get(j).getName().equalsIgnoreCase(Global.addEducationResponse.getResult().getEducationDetails().get(pos).getProgram())) {
                                progID = Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().get(j).getId().toString();
                            }
                        }

                        for (int k = 0; k < Global.pdfResponse.getResult().getCerftificates().get(i).getGroups().size(); k++) {
                            if (Global.pdfResponse.getResult().getCerftificates().get(i).getGroups().get(k).getName().equalsIgnoreCase(Global.addEducationResponse.getResult().getEducationDetails().get(pos).getGroup())) {
                                groupID = Global.pdfResponse.getResult().getCerftificates().get(i).getGroups().get(k).getId().toString();
                            }
                        }
                    }
                }

                //Toast.makeText(getActivity(), certID +","+ progID +","+ groupID, Toast.LENGTH_SHORT).show();

                Global.userEduDetail.setCertificate_id(certID);
                Global.userEduDetail.setProgram_id(progID);
                Global.userEduDetail.setGroup_id(groupID);
                Global.userEduDetail.setBoard_id(board_Id);
                Global.userEduDetail.setBoard(board_name);
                Global.userEduDetail.setPassing_year(passing_year);
                Global.userEduDetail.setMarks_obtained(marks_obtained);
                Global.userEduDetail.setTotal_marks(total_marks);
                Global.userEduDetail.setSession(session);
                Global.userEduDetail.setRoll_number(roll_no);
                Global.userEduDetail.setCase_id(Global.caseId);
                Global.userEduDetail.setDocId(docId);


                String DocId = docId;
                String certId = certID;
                String proId = progID;
                String groId = groupID;


                String broId = board_Id;
                String year = passing_year;
                String obtainedMark = marks_obtained;
                String totalMarks = total_marks;
                String eSession = session;
                String rollNo = roll_no;
                String eCaseId = Global.caseId;

                //Global.utils.showCustomLoadingDialog(getActivity());
                //volleyRequestController.EditEducation(DocId, certId, proId, groId, broId, year, obtainedMark, totalMarks,
                // eSession, rollNo, eCaseId);

                /*callEditEducationApi(getActivity(), DocId, certId, proId, groId, broId, year, obtainedMark,
                        totalMarks, eSession, rollNo, eCaseId);*/

                viewModel.callEditEducationApi(getActivity(), DocId, certId, proId, groId,
                        broId, year, obtainedMark, totalMarks, eSession, rollNo, eCaseId);
            }
        });

        updateEducationAlertDialog.show();
    }

    public void showAddEducationDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        View v = getLayoutInflater().inflate(R.layout.alert_add_education, null);
        dialogBuilder.setView(v);

        addEducationAlertDialog = dialogBuilder.create();
        addEducationAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Spinner spinner_certificate = v.findViewById(R.id.spinner_certificate);
        spinner_program = v.findViewById(R.id.spinner_program);
        spinner_group = v.findViewById(R.id.spinner_group);

        Spinner spinner_board = v.findViewById(R.id.spinner_board);
        Spinner spinner_passing_year = v.findViewById(R.id.spinner_passing_year);
        Spinner spinner_session = v.findViewById(R.id.spinner_session);
        EditText et_marks_obtained = v.findViewById(R.id.et_marks_obtained);
        EditText et_total_marks = v.findViewById(R.id.et_total_marks);
        EditText et_roll_no = v.findViewById(R.id.et_roll_no);
        Button btn_add = v.findViewById(R.id.btn_add);
        ImageView iv_cancel = v.findViewById(R.id.iv_cancel);

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEducationAlertDialog.dismiss();
            }
        });

        //CERTIFICATE SPINNER
        SpinnerCertificateAdapter arrayAdapter4 = new SpinnerCertificateAdapter(getActivity(), R.layout.spinner_item, Global.selectedCerti);
        spinner_certificate.setAdapter(arrayAdapter4);

        spinner_certificate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                cert_name = Global.selectedCerti.get(position).getName();
                cert_Id = Global.selectedCerti.get(position).getId().toString();

                selectedCertificateIndex = position;

                Global.selectedCertificate = Global.selectedCerti.get(position);

                Spinner_Cert_ProgramAdapter arrayAdapter5 = new Spinner_Cert_ProgramAdapter(getActivity(), Global.selectedCerti.get(position).getPrograms());
                spinner_program.setAdapter(arrayAdapter5);

                Spinner_Cert_GroupAdapter arrayAdapter6 = new Spinner_Cert_GroupAdapter(getActivity(), Global.selectedCerti.get(position).getGroups());
                spinner_group.setAdapter(arrayAdapter6);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });


        //PROGRAM SPINNER
        spinner_program.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                program_name = Global.selectedCertificate.getPrograms().get(position).getName();
                program_Id = Global.selectedCertificate.getPrograms().get(position).getId().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        //GROUP SPINNER
        spinner_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                group_name = Global.selectedCertificate.getGroups().get(position).getName();
                group_Id = Global.selectedCertificate.getGroups().get(position).getId().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        //BOARD SPINNER
        SpinnerBoardAdapter arrayAdapter3 = new SpinnerBoardAdapter(getActivity(), R.layout.spinner_item, Global.pdfResponse.getResult().getBoards());
        spinner_board.setAdapter(arrayAdapter3);
        spinner_board.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                board_name = Global.pdfResponse.getResult().getBoards().get(position).getName();
                board_Id = Global.pdfResponse.getResult().getBoards().get(position).getId().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        //PASSING YEAR SPINNER
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, years);
        spinner_passing_year.setAdapter(adapter);

        spinner_passing_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                passing_year = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        //SPINNER SESSION
        ArrayAdapter<CharSequence> spinner_session_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.title_session, R.layout.spinner_item);
        spinner_session.setAdapter(spinner_session_adapter);
        spinner_session.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                session = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*for (int i = 0; i < Global.addEducationResponse.getResult().getEducationDetails().size(); i++) {
                    if(Global.addEducationResponse.getResult().getEducationDetails().get(i).getCertificate().equals(cert_name)) {
                        //Toast.makeText(getActivity(), "Already Selected this Document", Toast.LENGTH_LONG).show();
                        Global.utils.showErrorSnakeBar(getActivity(),"Already Selected this Document");
                        return;
                    }
                }*/

                if (TextUtils.isEmpty(et_marks_obtained.getText())) {
                    et_marks_obtained.setError("Field cannot be empty");
                    et_marks_obtained.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(et_total_marks.getText())) {
                    et_total_marks.setError("Field cannot be empty");
                    et_total_marks.requestFocus();
                    return;
                }

                roll_no = et_roll_no.getText().toString();

                if (Integer.parseInt(et_marks_obtained.getText().toString()) > Integer.parseInt(et_total_marks.getText().toString())) {
                    et_marks_obtained.setError("Obtained marks is greater than total marks");
                    et_marks_obtained.requestFocus();
                    return;
                } else {
                    marks_obtained = et_marks_obtained.getText().toString();
                    total_marks = et_total_marks.getText().toString();
                }


                Global.userEduDetail.setCertificate(cert_name);
                Global.userEduDetail.setProgram(program_name);
                Global.userEduDetail.setGroup(group_name);
                Global.userEduDetail.setBoard(board_name);
                Global.userEduDetail.setPassing_year(passing_year);
                Global.userEduDetail.setSession(session);
                Global.userEduDetail.setMarks_obtained(marks_obtained);
                Global.userEduDetail.setTotal_marks(total_marks);
                Global.userEduDetail.setRoll_number(roll_no);
                Global.userEduDetail.setCertificate_id(cert_Id);
                Global.userEduDetail.setProgram_id(program_Id);
                Global.userEduDetail.setBoard_id(board_Id);
                Global.userEduDetail.setGroup_id(group_Id);
                Global.userEduDetail.setCase_id(Global.caseId);
                //Global.userEduDetail.setDocId();

                DocumentSelectionModel documentSelectionModel = new DocumentSelectionModel();
                documentSelectionModel.setBoard(board_name);
                documentSelectionModel.setProgram(program_name);
                documentSelectionModel.setCertificate(cert_name);
                //documentSelectionModel.setDocId(Global.docId);
                documentSelectionModel.setCaseId(Global.caseId);

                List<DocumentDetailModel> docList = new ArrayList<>();
                documentSelectionModel.setDetailModelList(docList);

                if (Global.selDocument != null) {
                    Global.selDocument.add(documentSelectionModel);
                }

                Global.selectedCerti.remove(selectedCertificateIndex);

                //Global.utils.showCustomLoadingDialog(getActivity());
                //volleyRequestController.addEducation(Global.userEduDetail);
                //callAddEducationApi(getActivity(), Global.userEduDetail);
                viewModel.callAddEducationApi(getActivity(), Global.userEduDetail);
            }
        });

        addEducationAlertDialog.setCancelable(true);
        addEducationAlertDialog.show();
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
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            Global.deleteEducationResponse = response.body();
                            Global.utils.hideCustomLoadingDialog();

                            Global.addEducationResponse.getResult().getEducationDetails().remove(Global.deleteEduPosition);
                            Global.selDocument.remove(Global.deleteEduPosition);

                            educationDetailsAdapter.notifyDataSetChanged();
                            if (Global.addEducationResponse.getResult().getEducationDetails().size() == 0) {
                                layoutBinding.NoEducationRecord.setVisibility(View.VISIBLE);
                            }

                            Global.utils.showSuccessSnakeBar(getActivity(), "Deleted Successfully");
                        } else {
                            Global.utils.hideCustomLoadingDialog();
                            Global.utils.showErrorSnakeBar(getActivity(), response.body().getResult().getResponseMessage());
                        }
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
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            Global.editEducationResponse = response.body();

                            Global.educationDetail.setCertificate(Global.userEduDetail.getCertificate());
                            Global.educationDetail.setProgram(Global.userEduDetail.getProgram());
                            Global.educationDetail.setGroup(Global.userEduDetail.getGroup());
                            Global.educationDetail.setBoard(Global.userEduDetail.getBoard());
                            Global.educationDetail.setYear(Global.userEduDetail.getPassing_year());
                            Global.educationDetail.setMarks(Global.userEduDetail.getMarks_obtained());
                            Global.educationDetail.setTotal(Global.userEduDetail.getTotal_marks());
                            Global.educationDetail.setSession(Global.userEduDetail.getSession());
                            Global.educationDetail.setRollNumber(Global.userEduDetail.getRoll_number());
                            Global.educationDetail.setCaseId(Global.userEduDetail.getCase_id());
                            Global.educationDetail.setDocId(Global.userEduDetail.getDocId());

                            Global.addEducationResponse.getResult().getEducationDetails().set(Global.editEduPosition, Global.educationDetail);

                            educationDetailsAdapter.notifyItemChanged(Global.editEduPosition);
                            updateEducationAlertDialog.dismiss();

                            Global.utils.showSuccessSnakeBar(getActivity(), "Edited Successfully");

                        } else {
                            updateEducationAlertDialog.dismiss();
                            Global.utils.showErrorSnakeBar(getActivity(), response.body().getResult().getResponseMessage());
                        }
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
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            Global.addEducationResponse = response.body();

                            for (int i = 0; i < response.body().getResult().getEducationDetails().size(); i++) {
                                Global.selDocument.get(i).setDocId(response.body().getResult().getEducationDetails().get(i).getDocId());
                            }

                            layoutBinding.NoEducationRecord.setVisibility(View.GONE);
                            addEducationAlertDialog.dismiss();

                            educationDetailsAdapter = new EducationDetailsAdapter(getActivity());
                            layoutBinding.rvEducationDetails.setAdapter(educationDetailsAdapter);

                            Global.utils.showSuccessSnakeBar(getActivity(), "Added Successfully");

                        } else {
                            Global.utils.hideCustomLoadingDialog();
                            EducationDetailsFragment.addEducationAlertDialog.dismiss();
                            Global.utils.showErrorSnakeBar(getActivity(), response.body().getResult().getResponseMessage());
                        }
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

}