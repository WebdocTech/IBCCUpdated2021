package com.webdoc.ibcc.DashBoard.reAssignedCasses;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.webdoc.ibcc.Adapter.EditSubjectsAdapter;
import com.webdoc.ibcc.Adapter.SelectedFilesAdapter;
import com.webdoc.ibcc.Adapter.SelectedTravellingDocumentAdapter;
import com.webdoc.ibcc.Adapter.Spinner.Equivalence.CountriesAdapter;
import com.webdoc.ibcc.Adapter.Spinner.Equivalence.EquivalenceGradingSystemAdapter;
import com.webdoc.ibcc.Adapter.Spinner.Equivalence.ExaminingBodyAdapter;
import com.webdoc.ibcc.Adapter.Spinner.Equivalence.GroupAdapter;
import com.webdoc.ibcc.Adapter.Spinner.Equivalence.QualificationAdapter;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.AddQualification.AddQualification;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.UpdateQualification.UpdateQualification;
import com.webdoc.ibcc.DashBoard.Home.HomeSharedViewModel.HomeSharedViewModel;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.adapter.CaseEditSubjectsAdapter;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.adapter.CaseSelectedFilesAdapter;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.adapter.CaseSelectedTravellingDocumentAdapter;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.ReassignedCaseDetail;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.ReassignedCaseDetailsModels.CaseUploadedDocumentResponse;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.ReassignedCaseDetailsModels.CaseUploadedTravDocumentResponse;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.ReassignedCaseDetailsModels.QualificationSubjectResponse;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.ReassignedCaseDetailsModels.ReassignedCaseDetailsModel;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.ReassignedCaseModel;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.editReassignCaseModels.EditReassignCaseModel;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.EquivalenceFileModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.Country;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.EquivalenceGrade;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.EquivalenceGradingSystem;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.EquivalenceGroup;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.EquivalenceSubject;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.ExaminingBody;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.Qualification;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.ActivityReassignedCaseDetailsBinding;
import com.webdoc.ibcc.databinding.ActivityUpdateQualificationBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Build.VERSION.SDK_INT;

public class ReassignedCaseDetailsActivity extends AppCompatActivity {
    private ActivityReassignedCaseDetailsBinding layoutBinding;
    private Context mContext;

    public static RecyclerView rv_subjects, rv_files, rv_files_trans;
    public static TextView tv_uploadHint;
    CaseEditSubjectsAdapter editSubjectsAdapter;
    CaseSelectedFilesAdapter selectedFilesAdapter;
    CaseSelectedTravellingDocumentAdapter travellingDocumentAdapter;
    ArrayAdapter<CharSequence> spinner_purpose_of_equivalence_adapter,
            spinner_examination_system_adapter;

    Country country;
    ExaminingBody examiningBody;
    Qualification qualification;
    EquivalenceGroup equivalenceGroup;
    EquivalenceGradingSystem equivalenceGradingSystem;

    List<Country> countriesList;
    List<ExaminingBody> examiningBodyList;
    List<Qualification> qualificationList;
    List<EquivalenceGradingSystem> equivalenceGradingSystemList;
    List<EquivalenceGroup> equivalenceGroupList;
    List<EquivalenceSubject> equivalenceSubjectList;

    AlertDialog fileChooserAlertDialog;
    String purpose_of_equivalence, examination_system;
    public static final int PDF_REQUEST_CODE = 100;
    public static final int GALLERY_REQUEST_CODE = 200;
    private HomeSharedViewModel viewModel;

    //Details Api Data:
    private String mExaminingBody;
    private String mQualification;
    private String mGroup;
    private String mPurposeOfEQ;
    private String mGradingSystem;
    private int mPosition;
    private int mDocID;
    private String caseID;
    private String mEmail;
    private ArrayList<QualificationSubjectResponse> arrayListSubjects;
    private List<CaseUploadedTravDocumentResponse> listTrav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityReassignedCaseDetailsBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        mContext = this;

        viewModel = ViewModelProviders.of(ReassignedCaseDetailsActivity.this).get(HomeSharedViewModel.class);

        Intent intent = getIntent();
        caseID = intent.getStringExtra("mCaseID");
        mPosition = intent.getIntExtra("mPos", 0);
        mDocID = intent.getIntExtra("docID", 0);
        callReAssignedCaseDetailsApi(this, caseID);

        //statics:
        rv_subjects = layoutBinding.rvSubjects;
        rv_files = layoutBinding.rvFiles;
        rv_files_trans = layoutBinding.rvFilesTrans;

        /*Global.imagesEducationlList.clear();
        layoutBinding.etSession.setText(Global.equivalenceQualificationList.get(Global.selectedQualificationIndex).getSession());
        Global.selectedFilesList.clear();

        for (int i = 0; i < Global.equivalenceQualificationList.get(Global.selectedQualificationIndex).getSelectedFilesList().size(); i++) {
            tv_uploadHint.setVisibility(View.GONE);
            Global.selectedFilesList.add(Global.equivalenceQualificationList.get(Global.selectedQualificationIndex).getSelectedFilesList().get(i));
        }

        setSubjectsAdapter();
        setFilesAdapter();
        setFileTransAdapter();*/

        countriesList = new ArrayList<>();
        for (int i = 0; i < Global.getDetailsEquivalence.getResult().getCountries().size(); i++) {
            countriesList.add(Global.getDetailsEquivalence.getResult().getCountries().get(i));
        }

        observers();
        //clickListeners();

    }

    private void observers() {
        viewModel.getGetImageDocuments().observe(this, response -> {
            if (response != null) {
                for (int i = 0; i < response.getImageUploadResult().getData().size(); i++) {
                    Global.imagesEducationlList.add(response.getImageUploadResult().getData().get(i).getImagename());
                    Global.timestamp = response.getImageUploadResult().getData().get(i).getImagename();
                }
                Global.equivalenceAddQualification.setImagesEductaionList(Global.imagesEducationlList);
                if (Global.phptravellingFiles.size() > 0) {
                    //callImageTravellingApi(context);
                    viewModel.callImageTravellingApi(ReassignedCaseDetailsActivity.this);
                } /*else {
                    if (Global.isFromEditQualitifcation == true) {
                        Global.utils.showCustomLoadingDialog(Global.applicationContext);
                        volleyRequestController.equivalenceEditQualification();
                    } else {
                        Global.utils.showCustomLoadingDialog(Global.applicationContext);
                        volleyRequestController.equivalenceAddQualification();
                    }
                }*/
            }
        });

        viewModel.getGetImageTravelling().observe(this, response -> {
            if (response != null) {
                for (int i = 0; i < response.getImageUploadResult().getData().size(); i++) {
                    Global.imagesTravellinglList.add(response.getImageUploadResult().getData().get(i).getImagename());
                    Global.timestamp = response.getImageUploadResult().getData().get(i).getImagename();

                    callEquivalenceUpdateQualificationApi();
                }
                /*Global.equivalenceAddQualification.setImagesTravellingList(Global.imagesTravellinglList);
                if (Global.isFromEditQualitifcation == true) {
                    Global.utils.showCustomLoadingDialog(Global.applicationContext);
                    volleyRequestController.equivalenceEditQualification();

                } else {
                    Global.utils.showCustomLoadingDialog(Global.applicationContext);
                    volleyRequestController.equivalenceAddQualification();
                }*/
            }
        });
    }

    public void callReAssignedCaseDetailsApi(Activity activity, String caseID) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("case_id", caseID);

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<ReassignedCaseDetailsModel> call = apiInterface.callReassignedCaseDetailsApi(params);

            call.enqueue(new Callback<ReassignedCaseDetailsModel>() {
                @Override
                public void onResponse(Call<ReassignedCaseDetailsModel> call, Response<ReassignedCaseDetailsModel> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().getResult().getResponseCode()
                                .equals(Constants.IBCC_SUCCESS_CODE)) {

                            mEmail = response.body().getResult().getDocument().get(mPosition).getEmail();
                            mExaminingBody = response.body().getResult().getDocument().get(mPosition).getExaminingBody();
                            mQualification = response.body().getResult().getDocument().get(mPosition).getQualificationId();
                            mGroup = response.body().getResult().getDocument().get(mPosition).getGroupName();
                            mPurposeOfEQ = response.body().getResult().getDocument().get(mPosition).getPurposeOfEquivalence();
                            mGradingSystem = response.body().getResult().getDocument().get(mPosition).getGradingSystemId();

                            layoutBinding.etSession.setText(response.body().getResult().getDocument().get(mPosition).getSession());

                            Global.imagesEducationlList.clear();
                            //layoutBinding.etSession.setText(Global.equivalenceQualificationList.get(Global.selectedQualificationIndex).getSession());
                            Global.caseSelectedFilesList.clear();

                            for (int i = 0; i < response.body().getResult().getDocument().get(mPosition).getCaseUploadedDocumentResponse().size(); i++) {
                                layoutBinding.tvUploadHint.setVisibility(View.GONE);
                                Global.caseSelectedFilesList.add(response.body().getResult().getDocument().get(mPosition).getCaseUploadedDocumentResponse().get(i));
                            }

                            arrayListSubjects = response.body().getResult().getDocument().get(mPosition).getQualificationSubjectResponse();
                            //setSubjectsAdapter(response.body().getResult().getDocument().get(mPosition).getQualificationSubjectResponse());
                            setFilesAdapter(Global.caseSelectedFilesList, null);

                            listTrav = new ArrayList<>();
                            for (int i = 0; i < response.body().getResult().getDocument().get(mPosition).getCaseUploadedTravellingDocumentResponse().size(); i++) {
                                layoutBinding.tvUploadHint.setVisibility(View.GONE);
                                listTrav.add(response.body().getResult().getDocument().get(mPosition).getCaseUploadedTravellingDocumentResponse().get(i));
                            }
                            setFileTransAdapter(listTrav);

                            clickListeners();

                            setSubjectsAdapter(response.body().getResult().getDocument().get(mPosition).getQualificationSubjectResponse());

                        } else {
                            Global.utils.showErrorSnakeBar(ReassignedCaseDetailsActivity.this,
                                    response.body().getResult().getResponseMessage());
                        }
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ReassignedCaseDetailsModel> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Oopps something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void clickListeners() {
        //COUNTRIES SPINNER
        CountriesAdapter spinnerCountriesAdapter = new CountriesAdapter(this,
                R.layout.spinner_item, countriesList);
        layoutBinding.spinnerCountry.setAdapter(spinnerCountriesAdapter);
        //int spinner_countryPosition = spinnerCountriesAdapter.getPosition(Global.equivalenceQualificationList.get(Global.selectedQualificationIndex).getCountry());
        //layoutBinding.spinnerCountry.setSelection(spinner_countryPosition);
        layoutBinding.spinnerCountry.setSelection(0);

        layoutBinding.spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                country = countriesList.get(position);

                int mExaminingBodyPos = 0;
                //EXAMINING BODY
                examiningBodyList = new ArrayList<>();
                for (int i = 0; i < country.getExaminingBody().size(); i++) {
                    examiningBodyList.add(country.getExaminingBody().get(i));
                    if (country.getExaminingBody().get(i).getName().equalsIgnoreCase(mExaminingBody)) {
                        mExaminingBodyPos = i;
                    }
                }

                ExaminingBodyAdapter examiningBodyAdapter = new ExaminingBodyAdapter(ReassignedCaseDetailsActivity.this, R.layout.spinner_item, examiningBodyList);
                layoutBinding.spinnerExaminingBody.setAdapter(examiningBodyAdapter);

                //int spinner_examiningBodyPosition = examiningBodyAdapter.getPosition(Global.equivalenceQualificationList.get(Global.selectedQualificationIndex).getExaminingBody());
                //layoutBinding.spinnerExaminingBody.setSelection(spinner_examiningBodyPosition);
                layoutBinding.spinnerExaminingBody.setSelection(mExaminingBodyPos);

                //int mQualificationID = 0;
                //QUALIFICATION
                qualificationList = new ArrayList<>();
                for (int i = 0; i < country.getQualification().size(); i++) {
                    qualificationList.add(country.getQualification().get(i));
                }
                QualificationAdapter qualificationAdapter = new QualificationAdapter(ReassignedCaseDetailsActivity.this,
                        R.layout.spinner_item, qualificationList);
                layoutBinding.spinnerQualification.setAdapter(qualificationAdapter);

                //int spinner_qualificationPosition = qualificationAdapter.getPosition(Global.equivalenceQualificationList.get(Global.selectedQualificationIndex).getQualification());
                layoutBinding.spinnerQualification.setSelection(Integer.parseInt(mQualification) - 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });


        //EXAMINING BODY SPINNER
        layoutBinding.spinnerExaminingBody.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                examiningBody = examiningBodyList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        layoutBinding.btnSelectSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutBinding.ccAddMarks.setVisibility(View.VISIBLE);
                layoutBinding.ccAllitems.setVisibility(View.GONE);
            }
        });

        layoutBinding.btnUploadTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.isFromDocument = false;
                showFileChooser();
            }
        });

        //QUALIFICATION SPINNER
        layoutBinding.spinnerQualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                qualification = qualificationList.get(position);
                if (qualification.getIsRequiredDocumentaryEvidence().equals(1)) {
                    layoutBinding.btnUploadTransport.setVisibility(View.VISIBLE);
                    layoutBinding.selectedFilesLayoutTrans.setVisibility(View.VISIBLE);
                    Global.isRequired = true;
                } else {
                    layoutBinding.btnUploadTransport.setVisibility(View.GONE);
                    layoutBinding.selectedFilesLayoutTrans.setVisibility(View.GONE);
                    Global.isRequired = false;
                }

                //GRADING SYSTEM
                equivalenceGradingSystemList = new ArrayList<>();
                for (int i = 0; i < qualification.getEquivalenceGradingSystem().size(); i++) {
                    equivalenceGradingSystemList.add(qualification.getEquivalenceGradingSystem().get(i));
                }
                EquivalenceGradingSystemAdapter equivalenceGradingSystemAdapter = new EquivalenceGradingSystemAdapter(ReassignedCaseDetailsActivity.this, R.layout.spinner_item, equivalenceGradingSystemList);
                layoutBinding.spinnerGradingSystem.setAdapter(equivalenceGradingSystemAdapter);

                //int spinner_grading_systemPosition = equivalenceGradingSystemAdapter.getPosition(Global.equivalenceQualificationList.get(Global.selectedQualificationIndex).getGradingSystem());
                //layoutBinding.spinnerGradingSystem.setSelection(Integer.parseInt(mGradingSystem));


                //EQUIVALENCE GROUP
                equivalenceGroupList = new ArrayList<>();
                for (int i = 0; i < qualification.getEquivalenceGroup().size(); i++) {
                    equivalenceGroupList.add(qualification.getEquivalenceGroup().get(i));
                }
                GroupAdapter groupAdapter = new GroupAdapter(ReassignedCaseDetailsActivity.this, R.layout.spinner_item, equivalenceGroupList);
                layoutBinding.spinnerGroup.setAdapter(groupAdapter);

                /*Examination System Spinner Visibility*/
                if (qualification.getName().equalsIgnoreCase("High School Diploma")) {
                    layoutBinding.textView16.setVisibility(View.VISIBLE);
                    layoutBinding.ExaminationSystemLayout.setVisibility(View.VISIBLE);

                    //SPINNER EXAMINATION SYSTEM
                    spinner_examination_system_adapter = ArrayAdapter.createFromResource(ReassignedCaseDetailsActivity.this, R.array.examination_system_array, R.layout.spinner_item);
                    layoutBinding.spinnerExaminationSystem.setAdapter(spinner_examination_system_adapter);

                    layoutBinding.spinnerExaminationSystem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            examination_system = (String) parent.getItemAtPosition(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub;
                        }
                    });
                } else {
                    layoutBinding.textView16.setVisibility(View.GONE);
                    layoutBinding.ExaminationSystemLayout.setVisibility(View.GONE);
                    examination_system = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        layoutBinding.btnAddMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Global.utils.hideKeyboard(ReassignedCaseDetailsActivity.this);

                Boolean flag = true;
                for (int i = 0; i < Global.selectedGradeList.size(); i++) {

                    if (Global.selectedGradeList.get(i).getId() == null) {
                        flag = false;
                    }
                }
                if (flag) {
                    layoutBinding.ccAddMarks.setVisibility(View.GONE);
                    layoutBinding.ccAllitems.setVisibility(View.VISIBLE);
                    layoutBinding.btnSelectSubject.setBackground(getDrawable(R.drawable.green_button_background));
                    layoutBinding.btnSelectSubject.setText("Added Successfully");
                } else {
                    Global.utils.showErrorSnakeBar(ReassignedCaseDetailsActivity.this, "Please Enter all requirments");
                }
            }
        });


        //GRADING SYSTEM SPINNER
        layoutBinding.spinnerGradingSystem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                equivalenceGradingSystem = equivalenceGradingSystemList.get(position);

                Global.equivalenceGradeList.clear();
                for (int i = 0; i < equivalenceGradingSystem.getEquivalenceGrade().size(); i++) {
                    Global.equivalenceGradeList.add(equivalenceGradingSystem.getEquivalenceGrade().get(i));
                }
                Global.equivalenceGradingSystemName = equivalenceGradingSystem.getName();
                editSubjectsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });


        //EQUIVALENCE GROUP SPINNER
        layoutBinding.spinnerGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                equivalenceGroup = equivalenceGroupList.get(position);

                equivalenceSubjectList = new ArrayList<>();

                Global.selectedGradeList.clear();
                for (int i = 0; i < equivalenceGroup.getEquivalenceSubject().size(); i++) {
                    equivalenceSubjectList.add(equivalenceGroup.getEquivalenceSubject().get(i));

                    EquivalenceGrade equivalenceGrade = new EquivalenceGrade();
                    Global.selectedGradeList.add(equivalenceGrade);
                }

                editSubjectsAdapter = new CaseEditSubjectsAdapter(ReassignedCaseDetailsActivity.this,
                        equivalenceSubjectList, arrayListSubjects);
                layoutBinding.rvSubjects.setAdapter(editSubjectsAdapter);
                Global.equivalenceSubjectList = equivalenceSubjectList;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });


        //SPINNER PURPOSE OF EQUIVALENCE
        spinner_purpose_of_equivalence_adapter = ArrayAdapter.createFromResource(this, R.array.poe_array, R.layout.spinner_item);
        layoutBinding.spinnerPurposeOfEquivalence.setAdapter(spinner_purpose_of_equivalence_adapter);

        //int spinner_purpose_of_equivalencePosition = spinner_purpose_of_equivalence_adapter.getPosition(Global.equivalenceQualificationList.get(Global.selectedQualificationIndex).getPurposeOfEquivalence());
        //layoutBinding.spinnerPurposeOfEquivalence.setSelection(spinner_purpose_of_equivalencePosition);

        layoutBinding.spinnerPurposeOfEquivalence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                purpose_of_equivalence = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });


        layoutBinding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        layoutBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SweetAlertDialog(ReassignedCaseDetailsActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setContentText("Do you want to update this record?")
                        .setConfirmText("Yes")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();

                                callEquivalenceUpdateQualificationApi();

                                //viewModel.callImageDocumentApi(ReassignedCaseDetailsActivity.this);
                                // viewModel.callImageTravellingApi(ReassignedCaseDetailsActivity.this);

                            }

                        }).setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                }).show();
            }
        });
    }

    private void setFileTransAdapter(List<CaseUploadedTravDocumentResponse> arraylist) {
        final LinearLayoutManager filesLayoutManager2 = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        layoutBinding.rvFilesTrans.setLayoutManager(filesLayoutManager2);
        layoutBinding.rvFilesTrans.setHasFixedSize(true);
        SnapHelper snapHelperFiles2 = new PagerSnapHelper();
        snapHelperFiles2.attachToRecyclerView(layoutBinding.rvFilesTrans);
        travellingDocumentAdapter = new CaseSelectedTravellingDocumentAdapter(this, arraylist);
        layoutBinding.rvFilesTrans.setAdapter(travellingDocumentAdapter);
    }

    private void setFilesAdapter(List<CaseUploadedDocumentResponse> caseSelectedFilesList,
                                 List<EquivalenceFileModel> selectedFilesList) {
        final LinearLayoutManager filesLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        layoutBinding.rvFiles.setLayoutManager(filesLayoutManager);
        layoutBinding.rvFiles.setHasFixedSize(true);
       /* SnapHelper snapHelperFiles = new PagerSnapHelper();
        snapHelperFiles.attachToRecyclerView(layoutBinding.rvFiles);*/
        Global.widthFromUpdateQualification = true;
        selectedFilesAdapter = new CaseSelectedFilesAdapter(this, caseSelectedFilesList, selectedFilesList);
        layoutBinding.rvFiles.setAdapter(selectedFilesAdapter);
    }

    private void setSubjectsAdapter(ArrayList<QualificationSubjectResponse> arrayList) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvSubjects.setLayoutManager(layoutManager);
        layoutBinding.rvSubjects.setHasFixedSize(true);
        SnapHelper snapHelperSubjects = new PagerSnapHelper();
        snapHelperSubjects.attachToRecyclerView(layoutBinding.rvSubjects);
        equivalenceSubjectList = new ArrayList<>();
        editSubjectsAdapter = new CaseEditSubjectsAdapter(this, equivalenceSubjectList, arrayList);
        layoutBinding.rvSubjects.setAdapter(editSubjectsAdapter);
    }

    public void showFileChooser() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View v = getLayoutInflater().inflate(R.layout.alert_select_file, null);
        dialogBuilder.setView(v);

        fileChooserAlertDialog = dialogBuilder.create();
        fileChooserAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView iv_pdf = v.findViewById(R.id.iv_pdf);
        ImageView iv_gallery = v.findViewById(R.id.iv_gallery);
        TextView tv_pdf = v.findViewById(R.id.tv_pdf);
        TextView tv_gallery = v.findViewById(R.id.tv_gallery);

        iv_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPDF();
                fileChooserAlertDialog.dismiss();
            }
        });

        tv_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPDF();
                fileChooserAlertDialog.dismiss();
            }
        });

        iv_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectGalleryImages();
                fileChooserAlertDialog.dismiss();
            }
        });

        tv_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectGalleryImages();
                fileChooserAlertDialog.dismiss();
            }
        });

        fileChooserAlertDialog.show();
    }//alert

    public void selectPDF() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF(s)"), PDF_REQUEST_CODE);
    }

    public void selectGalleryImages() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Image(s)"), GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2296) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // perform action when allow permission success
                } else {
                    Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (Global.isFromDocument) {
            if (requestCode == PDF_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
                layoutBinding.tvUploadHint.setVisibility(View.GONE);
                Global.ispdf = true;
                Uri pdfUri = data.getData();
                String uriString = pdfUri.toString();
                File myFile = new File(uriString);
                int filesize = getfilesize(pdfUri);
                if (filesize > 2) {
                    Global.utils.showErrorSnakeBar(ReassignedCaseDetailsActivity.this,
                            "you cannot select file more than 2 MB");
                } else {
                    String displayName = null;
                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getContentResolver().query(pdfUri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                    }

                    EquivalenceFileModel equivalenceFileModel = new EquivalenceFileModel();
                    equivalenceFileModel.setUri(pdfUri);
                    equivalenceFileModel.setFileType("pdf");
                    equivalenceFileModel.setFileName(displayName);

                    Global.phpfiles.add(pdfUri);
                    Global.selectedFilesList.add(equivalenceFileModel);

                    selectedFilesAdapter.notifyDataSetChanged();
                    setFilesAdapter(Global.caseSelectedFilesList, Global.selectedFilesList);
                }
            } else if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
                layoutBinding.tvUploadHint.setVisibility(View.GONE);
                Global.ispdf = false;
                List<Uri> photos = new ArrayList<>();
                if (data.getClipData() != null) {
                    //multiple images
                    int count = data.getClipData().getItemCount();

                    for (int i = 0; i < count; i++) {
                        photos.add(data.getClipData().getItemAt(i).getUri());
                    }
                } else {
                    //single image
                    photos.add(data.getData());
                }

                for (int i = 0; i < photos.size(); i++) {
                    EquivalenceFileModel equivalenceFileModel = new EquivalenceFileModel();
                    equivalenceFileModel.setUri(photos.get(i));
                    equivalenceFileModel.setFileType("png");
                    equivalenceFileModel.setFileName("");
                    Global.phpfiles.add(photos.get(i));//Global.timestamp + ".png"
                    Global.selectedFilesList.add(equivalenceFileModel);
                }
                //selectedFilesAdapter.notifyDataSetChanged();
                setFilesAdapter(Global.caseSelectedFilesList, Global.selectedFilesList);
            }
        } else {
            if (requestCode == PDF_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
                layoutBinding.tvUploadHintTrans.setVisibility(View.GONE);
                Global.ispdf = true;
                Uri pdfUri = data.getData();
                String uriString = pdfUri.toString();
                File myFile = new File(uriString);
                int filesize = getfilesize(pdfUri);

                if (filesize > 2) {
                    Global.utils.showErrorSnakeBar(ReassignedCaseDetailsActivity.this,
                            "you cannot upload file more than 2 MB");
                } else {
                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getContentResolver().query(pdfUri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                    }

                    EquivalenceFileModel equivalenceFileModel = new EquivalenceFileModel();
                    equivalenceFileModel.setUri(pdfUri);
                    equivalenceFileModel.setFileType("pdf");
                    equivalenceFileModel.setFileName(displayName);
                    Global.phptravellingFiles.add(pdfUri);
                    Global.selectedFilesListTraveling.add(equivalenceFileModel);
                    travellingDocumentAdapter.notifyDataSetChanged();
                }
            } else if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
                layoutBinding.tvUploadHintTrans.setVisibility(View.GONE);

                Global.ispdf = false;
                List<Uri> photos = new ArrayList<>();
                if (data.getClipData() != null) {
                    //multiple images
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        photos.add(data.getClipData().getItemAt(i).getUri());
                    }
                } else {
                    //single image
                    photos.add(data.getData());
                }

                for (int i = 0; i < photos.size(); i++) {
                    EquivalenceFileModel equivalenceFileModel = new EquivalenceFileModel();
                    equivalenceFileModel.setUri(photos.get(i));
                    equivalenceFileModel.setFileType("png");
                    equivalenceFileModel.setFileName("");
                    Global.phptravellingFiles.add(photos.get(i));
                    Global.selectedFilesListTraveling.add(equivalenceFileModel);
                }
                travellingDocumentAdapter.notifyDataSetChanged();
            }
        }
    }

    private int getfilesize(Uri pdfUri) {
        Cursor returnCursor =
                getContentResolver().query(pdfUri, null, null, null, null);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        int size = (int) returnCursor.getLong(sizeIndex);
        int sizeKB = size / 1024;
        int sizeMb = size / (1024 * 1024);
        return sizeMb;
    }

    public void callEquivalenceUpdateQualificationApi() {
        if (Constants.isInternetConnected(ReassignedCaseDetailsActivity.this)) {
            Global.utils.showCustomLoadingDialog(ReassignedCaseDetailsActivity.this);

            JsonObject params = new JsonObject();
            try {
                params.addProperty("docId", mDocID);
                params.addProperty("countryId", Integer.parseInt(country.getId()));
                params.addProperty("session", layoutBinding.etSession.getText().toString());

                params.addProperty("fatherCnic", "");
                params.addProperty("fatherName", "");
                //
                params.addProperty("email", mEmail);
                //
                params.addProperty("titleOfQualification", country.getQualification().get(mPosition).getName());
                params.addProperty("mailingAddress", "");
                //
                params.addProperty("gradingSystemId", Integer.parseInt(equivalenceGradingSystem.getId()));
                //
                params.addProperty("groupId", Integer.parseInt(equivalenceGroup.getId()));

                params.addProperty("presentEmploymentOfParents", "");
                //
                params.addProperty("purposeOfEquivalence", purpose_of_equivalence);

                params.addProperty("parentsnameoftheorganization", "");
                //
                params.addProperty("qualificationId", Integer.parseInt(qualification.getId()));

                params.addProperty("otherexaminingbody", "");
                params.addProperty("examinationsystem", "");
                //
                params.addProperty("caseId", Integer.parseInt(caseID));

                params.addProperty("parentspermanentaddress", "");
                params.addProperty("nameoftheinstitution", "");
                params.addProperty("telno", "");

                params.addProperty("examiningbody", examiningBody.getName());

                //Education files image....
                JsonArray documentArray = new JsonArray();
                for (int i = 0; i < Global.caseSelectedFilesList.size(); i++) {
                    JsonObject imgItem = new JsonObject();
                    imgItem.addProperty("imagename", Global.caseSelectedFilesList.get(i).getImagename());

                    documentArray.add(imgItem);
                }
                params.add("imageseductaion", documentArray);

                //travelling/other files image....
                JsonArray documentTravellingArray = new JsonArray();
                for (int k = 0; k < listTrav.size(); k++) {
                    JsonObject imgItem = new JsonObject();
                    //20210702065122.png
                    imgItem.addProperty("imagename", listTrav.get(k).getImagename());
                    documentTravellingArray.add(imgItem);
                }
                params.add("imagestravelling", documentTravellingArray);

                //Subjects Grade:
                JsonArray subjectArray = new JsonArray();
                for (int j = 0; j < arrayListSubjects.size(); j++) {
                    JsonObject subjItem = new JsonObject();
                    subjItem.addProperty("subjectId", arrayListSubjects.get(j).getSubjectId());
                    subjItem.addProperty("marksgrades", arrayListSubjects.get(j).getMarksgrades());

                    subjectArray.add(subjItem);
                }
                params.add("subjecteductaion", subjectArray);

                System.out.println("AddQualification Params: " + params);

            } catch (Exception e) {
                System.out.println("Error : Equivalence Add Qualification " + e.toString());
            }

            //postApiResultManager.jsonParse(Constants.EDITQUALIFICATIONEQ, params);

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<EditReassignCaseModel> call = apiInterface.callReassignedCaseEditApi(params);

            call.enqueue(new Callback<EditReassignCaseModel>() {
                @Override
                public void onResponse(Call<EditReassignCaseModel> call, Response<EditReassignCaseModel> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().getResult().getResponseCode()
                                .equals(Constants.IBCC_SUCCESS_CODE)) {
                            Toast.makeText(ReassignedCaseDetailsActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Global.utils.showErrorSnakeBar(ReassignedCaseDetailsActivity.this,
                                    response.body().getResult().getResponseMessage());
                        }
                    } else {
                        Toast.makeText(ReassignedCaseDetailsActivity.this, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<EditReassignCaseModel> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(ReassignedCaseDetailsActivity.this, "Oopps something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(ReassignedCaseDetailsActivity.this, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }

    }
}