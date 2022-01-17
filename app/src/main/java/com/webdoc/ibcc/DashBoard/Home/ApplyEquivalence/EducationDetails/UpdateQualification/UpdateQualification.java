package com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.UpdateQualification;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.gson.Gson;
import com.webdoc.ibcc.Adapter.EditSubjectsAdapter;
import com.webdoc.ibcc.Adapter.SelectedFilesAdapter;
import com.webdoc.ibcc.Adapter.SelectedTravellingDocumentAdapter;
import com.webdoc.ibcc.Adapter.Spinner.Equivalence.CountriesAdapter;
import com.webdoc.ibcc.Adapter.Spinner.Equivalence.EquivalenceGradingSystemAdapter;
import com.webdoc.ibcc.Adapter.Spinner.Equivalence.ExaminingBodyAdapter;
import com.webdoc.ibcc.Adapter.Spinner.Equivalence.GroupAdapter;
import com.webdoc.ibcc.Adapter.Spinner.Equivalence.QualificationAdapter;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.EquivalenceEducationDetailsFragment;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.Country;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.EquivalenceGradingSystemEQNew;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.EquivalenceSubjectEQNew;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.ExaminingBody;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.GradesEQNew;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.GroupEQNew;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.Qualification;
import com.webdoc.ibcc.DashBoard.Home.HomeSharedViewModel.HomeSharedViewModel;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.AddQualificationModel;
import com.webdoc.ibcc.Model.EquivalenceFileModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.EditQualificationEQ.EditQualificationEQ;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.EquivalenceGrade;
import com.webdoc.ibcc.ServerManager.VolleyListener;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.databinding.ActivityUpdateQualificationBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UpdateQualification extends AppCompatActivity implements VolleyListener {
    public static RecyclerView rv_subjects, rv_files, rv_files_trans;
    public static TextView tv_uploadHint;

    EditSubjectsAdapter editSubjectsAdapter;
    SelectedFilesAdapter selectedFilesAdapter;
    SelectedTravellingDocumentAdapter travellingDocumentAdapter;
    ArrayAdapter<CharSequence> spinner_purpose_of_equivalence_adapter,
            spinner_examination_system_adapter;

    Country country;
    ExaminingBody examiningBody;
    Qualification qualification;
    GroupEQNew equivalenceGroup;
    EquivalenceGradingSystemEQNew equivalenceGradingSystem;

    List<Country> countriesList;
    List<ExaminingBody> examiningBodyList;
    List<Qualification> qualificationList;
    List<EquivalenceGradingSystemEQNew> equivalenceGradingSystemList;
    List<GroupEQNew> equivalenceGroupList;
    List<EquivalenceSubjectEQNew> equivalenceSubjectList;

    AlertDialog fileChooserAlertDialog;
    String purpose_of_equivalence, examination_system;
    public static final int PDF_REQUEST_CODE = 100;
    public static final int GALLERY_REQUEST_CODE = 200;
    VolleyRequestController volleyRequestController;
    private ActivityUpdateQualificationBinding layoutBinding;
    private HomeSharedViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_qualification);
        layoutBinding = ActivityUpdateQualificationBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        viewModel = ViewModelProviders.of(this).get(HomeSharedViewModel.class);

        //statics:
        rv_subjects = layoutBinding.rvSubjects;
        rv_files = layoutBinding.rvFiles;
        rv_files_trans = layoutBinding.rvFilesTrans;

        Global.imagesEducationlList.clear();
        volleyRequestController = new VolleyRequestController(this);
        layoutBinding.etSession.setText(Global.equivalenceQualificationList.get(Global.selectedQualificationIndex).getSession());
        Global.selectedFilesList.clear();

        for (int i = 0; i < Global.equivalenceQualificationList.get(Global.selectedQualificationIndex).getSelectedFilesList().size(); i++) {
            tv_uploadHint.setVisibility(View.GONE);
            Global.selectedFilesList.add(Global.equivalenceQualificationList.get(Global.selectedQualificationIndex).getSelectedFilesList().get(i));
        }

        setSubjectsAdapter();
        setFilesAdapter();
        setFileTransAdapter();

        countriesList = new ArrayList<>();
        for (int i = 0; i < Global.detailsEquivalenceNewModel.getResult().getCountries().size(); i++) {
            countriesList.add(Global.detailsEquivalenceNewModel.getResult().getCountries().get(i));
        }

        observers();
        clickListeners();

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
                    viewModel.callImageTravellingApi(UpdateQualification.this);
                } else {
                    if (Global.isFromEditQualitifcation == true) {
                        Global.utils.showCustomLoadingDialog(Global.applicationContext);
                        volleyRequestController.equivalenceEditQualification();
                    } else {
                        Global.utils.showCustomLoadingDialog(Global.applicationContext);
                        volleyRequestController.equivalenceAddQualification();
                    }
                }
            }
        });

        viewModel.getGetImageTravelling().observe(this, response -> {
            if (response != null) {
                for (int i = 0; i < response.getImageUploadResult().getData().size(); i++) {

                    Global.imagesTravellinglList.add(response.getImageUploadResult().getData().get(i).getImagename());
                    Global.timestamp = response.getImageUploadResult().getData().get(i).getImagename();
                }
                Global.equivalenceAddQualification.setImagesTravellingList(Global.imagesTravellinglList);
                if (Global.isFromEditQualitifcation == true) {
                    Global.utils.showCustomLoadingDialog(Global.applicationContext);
                    volleyRequestController.equivalenceEditQualification();

                } else {
                    Global.utils.showCustomLoadingDialog(Global.applicationContext);
                    volleyRequestController.equivalenceAddQualification();
                }
            }
        });
    }

    private void clickListeners() {
        //COUNTRIES SPINNER
        CountriesAdapter spinnerCountriesAdapter = new CountriesAdapter(this,
                R.layout.spinner_item, countriesList);
        layoutBinding.spinnerCountry.setAdapter(spinnerCountriesAdapter);
        int spinner_countryPosition = spinnerCountriesAdapter.getPosition(Global.equivalenceQualificationList.get(Global.selectedQualificationIndex).getCountry());
        layoutBinding.spinnerCountry.setSelection(spinner_countryPosition);

        layoutBinding.spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                //country = countriesList.get(position);

                //EXAMINING BODY
                examiningBodyList = new ArrayList<>();
                for (int i = 0; i < Global.detailsEquivalenceNewModel.getResult().getExaminingBody().size(); i++) {
                    examiningBodyList.add(Global.detailsEquivalenceNewModel.getResult().getExaminingBody().get(i));
                }
                ExaminingBodyAdapter examiningBodyAdapter = new ExaminingBodyAdapter(UpdateQualification.this, R.layout.spinner_item, examiningBodyList);
                layoutBinding.spinnerExaminingBody.setAdapter(examiningBodyAdapter);

                int spinner_examiningBodyPosition = examiningBodyAdapter.getPosition(Global.equivalenceQualificationList.get(Global.selectedQualificationIndex).getExaminingBody());
                layoutBinding.spinnerExaminingBody.setSelection(spinner_examiningBodyPosition);


                //QUALIFICATION
                qualificationList = new ArrayList<>();
                for (int i = 0; i < Global.detailsEquivalenceNewModel.getResult().getQualification().size(); i++) {
                    qualificationList.add(Global.detailsEquivalenceNewModel.getResult().getQualification().get(i));
                }
                QualificationAdapter qualificationAdapter = new QualificationAdapter(UpdateQualification.this,
                        R.layout.spinner_item, qualificationList);
                layoutBinding.spinnerQualification.setAdapter(qualificationAdapter);

                int spinner_qualificationPosition = qualificationAdapter.getPosition(Global.equivalenceQualificationList.get(Global.selectedQualificationIndex).getQualification());
                layoutBinding.spinnerQualification.setSelection(spinner_qualificationPosition);
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
                for (int i = 0; i < Global.detailsEquivalenceNewModel.getResult().getEquivalenceGradingSystemEQNew().size(); i++) {
                    equivalenceGradingSystemList.add(Global.detailsEquivalenceNewModel.getResult().getEquivalenceGradingSystemEQNew().get(i));
                }
                EquivalenceGradingSystemAdapter equivalenceGradingSystemAdapter = new EquivalenceGradingSystemAdapter(UpdateQualification.this, R.layout.spinner_item, equivalenceGradingSystemList);
                layoutBinding.spinnerGradingSystem.setAdapter(equivalenceGradingSystemAdapter);

                int spinner_grading_systemPosition = equivalenceGradingSystemAdapter.getPosition(Global.equivalenceQualificationList.get(Global.selectedQualificationIndex).getGradingSystem());
                layoutBinding.spinnerGradingSystem.setSelection(spinner_grading_systemPosition);


                //EQUIVALENCE GROUP
                equivalenceGroupList = new ArrayList<>();
                for (int i = 0; i < Global.detailsEquivalenceNewModel.getResult().getGroupEQNew().size(); i++) {
                    equivalenceGroupList.add(Global.detailsEquivalenceNewModel.getResult().getGroupEQNew().get(i));
                }
                GroupAdapter groupAdapter = new GroupAdapter(UpdateQualification.this, R.layout.spinner_item, equivalenceGroupList);
                layoutBinding.spinnerGroup.setAdapter(groupAdapter);

                /*Examination System Spinner Visibility*/
                if (qualification.getName().equalsIgnoreCase("High School Diploma")) {
                    layoutBinding.textView16.setVisibility(View.VISIBLE);
                    layoutBinding.ExaminationSystemLayout.setVisibility(View.VISIBLE);

                    //SPINNER EXAMINATION SYSTEM
                    spinner_examination_system_adapter = ArrayAdapter.createFromResource(UpdateQualification.this, R.array.examination_system_array, R.layout.spinner_item);
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

                Global.utils.hideKeyboard(UpdateQualification.this);

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
                    Global.utils.showErrorSnakeBar(UpdateQualification.this, "Please Enter all requirments");
                }
            }
        });


        //GRADING SYSTEM SPINNER
        layoutBinding.spinnerGradingSystem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                equivalenceGradingSystem = equivalenceGradingSystemList.get(position);

                Global.equivalenceGradeList.clear();
                for (int i = 0; i < Global.detailsEquivalenceNewModel.getResult().getGradesEQNew().size(); i++) {
                    Global.equivalenceGradeList.add(Global.detailsEquivalenceNewModel.getResult().getGradesEQNew().get(i));
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
                for (int i = 0; i < Global.detailsEquivalenceNewModel.getResult().getEquivalenceSubjectEQNew().size(); i++) {
                    equivalenceSubjectList.add(Global.detailsEquivalenceNewModel.getResult().getEquivalenceSubjectEQNew().get(i));
                    // Global.selectedGradeList.add(Global.equivalenceQualificationList.get(Global.selectedQualificationIndex).getGradeList().get(i));

                    GradesEQNew equivalenceGrade = new GradesEQNew();
                    Global.selectedGradeList.add(equivalenceGrade);

                }

                editSubjectsAdapter = new EditSubjectsAdapter(UpdateQualification.this,
                        equivalenceSubjectList);
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

        int spinner_purpose_of_equivalencePosition = spinner_purpose_of_equivalence_adapter.getPosition(Global.equivalenceQualificationList.get(Global.selectedQualificationIndex).getPurposeOfEquivalence());
        layoutBinding.spinnerPurposeOfEquivalence.setSelection(spinner_purpose_of_equivalencePosition);

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
                if (TextUtils.isEmpty(country.getName()) || TextUtils.isEmpty(examiningBody.getName()) || TextUtils.isEmpty(qualification.getName())
                        || TextUtils.isEmpty(equivalenceGroup.getName()) || TextUtils.isEmpty(layoutBinding.etSession.getText().toString()) || TextUtils.isEmpty(purpose_of_equivalence)
                        || TextUtils.isEmpty(equivalenceGradingSystem.getName()) || equivalenceSubjectList.size() == 0 || Global.selectedGradeList.size() == 0) {
                    Toast.makeText(UpdateQualification.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Global.equivalenceOnline) {
                    if (Global.selectedFilesList.size() == 0) {
                        Toast.makeText(UpdateQualification.this, "Please select files to upload", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (!Global.isIncompleteAppointmentEQ) {
                    Global.equivalenceAddQualification.setCaseId(Global.equivalenceInitiateCaseResponse.getResult().getIntiateCaseResponseDetails().getCaseId().toString());
                } else {
                    Global.equivalenceAddQualification.setCaseId(Global.caseIdQualificationEQ);
                }

                new SweetAlertDialog(UpdateQualification.this, SweetAlertDialog.WARNING_TYPE)
                        .setContentText("Do you want to update this record?")
                        .setConfirmText("Yes")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                Global.isFromEditQualitifcation = true;
                                Global.equivalenceAddQualification.setCountryId(Global.equivalenceInitiateCase.getcCountryId());
                                Global.equivalenceAddQualification.setEmail(Global.equivalenceInitiateCase.getEmail());
                                Global.equivalenceAddQualification.setExaminationSystem(examination_system);
                                Global.equivalenceAddQualification.setExaminingBody(examiningBody.getName());
                                Global.equivalenceAddQualification.setPurposeOfEquivalence(purpose_of_equivalence);
                                Global.equivalenceAddQualification.setNameOfTheInstitution("");
                                Global.equivalenceAddQualification.setMailingAddress(Global.equivalenceInitiateCase.getEmail());
                                Global.equivalenceAddQualification.setTelNo(Global.equivalenceInitiateCase.getPhone());
                                Global.equivalenceAddQualification.setOtherExaminingBody("");
                                Global.equivalenceAddQualification.setGradingSystemId(String.valueOf(equivalenceGradingSystem.getId()));
                                Global.equivalenceAddQualification.setGroupId(String.valueOf(equivalenceGroup.getId()));
                                Global.equivalenceAddQualification.setSession(layoutBinding.etSession.getText().toString());
                                Global.equivalenceAddQualification.setQualificationId(String.valueOf(qualification.getId()));
                                Global.equivalenceAddQualification.setTitleOfQualification(qualification.getName());
                                Global.equivalenceAddQualification.setFatherCnic(Global.equivalenceInitiateCase.getFatherCnic());
                                Global.equivalenceAddQualification.setPresentEmploymentOfParents(Global.equivalenceInitiateCase.getParentsEmployment());
                                Global.equivalenceAddQualification.setFatherName(Global.equivalenceInitiateCase.getFatherName());
                                Global.equivalenceAddQualification.setParentsPermanentAddress(Global.equivalenceInitiateCase.getFatherAddress());
                                Global.equivalenceAddQualification.setParentsNameOfTheOrganization(Global.equivalenceInitiateCase.getNameOfOrganization());
                                Global.equivalenceAddQualification.setImagesTravellingList(Global.imagesTravellinglList);

                                //Todo: SubjectList
                                List<GradesEQNew> subjectEducationList = new ArrayList<>();
                                subjectEducationList.addAll(Global.selectedGradeList);
                                Global.equivalenceAddQualification.setSubjectEducationList(subjectEducationList);

                                Global.utils.showCustomLoadingDialog(UpdateQualification.this);
                                //Todo: imageList for document
                                Global.Count = 1;

                                Global.equivalenceAddQualification.setImagesEductaionList(Global.imagesEducationlList);
                                Global.equivalenceAddQualification.setImagesTravellingList(Global.imagesTravellinglList);

                                //php.callImageDocumentApi(UpdateQualification.this);
                                //php.callImageTravellingApi(UpdateQualification.this);
                                viewModel.callImageDocumentApi(UpdateQualification.this);
                                viewModel.callImageTravellingApi(UpdateQualification.this);

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

    private void setFileTransAdapter() {
        final LinearLayoutManager filesLayoutManager2 = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        layoutBinding.rvFilesTrans.setLayoutManager(filesLayoutManager2);
        layoutBinding.rvFilesTrans.setHasFixedSize(true);
        SnapHelper snapHelperFiles2 = new PagerSnapHelper();
        snapHelperFiles2.attachToRecyclerView(layoutBinding.rvFilesTrans);
        travellingDocumentAdapter = new SelectedTravellingDocumentAdapter(this);
        layoutBinding.rvFilesTrans.setAdapter(travellingDocumentAdapter);
    }

    private void setFilesAdapter() {
        final LinearLayoutManager filesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutBinding.rvFiles.setLayoutManager(filesLayoutManager);
        layoutBinding.rvFiles.setHasFixedSize(true);
        SnapHelper snapHelperFiles = new PagerSnapHelper();
        snapHelperFiles.attachToRecyclerView(layoutBinding.rvFiles);
        Global.widthFromUpdateQualification = true;
        selectedFilesAdapter = new SelectedFilesAdapter(this);
        layoutBinding.rvFiles.setAdapter(selectedFilesAdapter);
    }

    private void setSubjectsAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvSubjects.setLayoutManager(layoutManager);
        layoutBinding.rvSubjects.setHasFixedSize(true);
        SnapHelper snapHelperSubjects = new PagerSnapHelper();
        snapHelperSubjects.attachToRecyclerView(layoutBinding.rvSubjects);
        equivalenceSubjectList = new ArrayList<>();
        editSubjectsAdapter = new EditSubjectsAdapter(this, equivalenceSubjectList);
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

        if (Global.isFromDocument) {
            if (requestCode == PDF_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
                tv_uploadHint.setVisibility(View.GONE);

                Uri pdfUri = data.getData();
                String uriString = pdfUri.toString();
                File myFile = new File(uriString);
                String path = myFile.getAbsolutePath();
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
                Global.selectedFilesList.add(equivalenceFileModel);

                selectedFilesAdapter.notifyDataSetChanged();

            } else if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
                tv_uploadHint.setVisibility(View.GONE);

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
                    equivalenceFileModel.setFileName("");                //Global.timestamp + ".png"
                    Global.selectedFilesList.add(equivalenceFileModel);
                }

                selectedFilesAdapter.notifyDataSetChanged();

                return;
            }
        } else {
            Toast.makeText(this, "traveling", Toast.LENGTH_SHORT).show();
            if (requestCode == PDF_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
                layoutBinding.tvUploadHintTrans.setVisibility(View.GONE);

                Uri pdfUri = data.getData();
                String uriString = pdfUri.toString();
                File myFile = new File(uriString);
                String path = myFile.getAbsolutePath();
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
                Global.selectedFilesListTraveling.add(equivalenceFileModel);

                travellingDocumentAdapter.notifyDataSetChanged();

            } else if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
                layoutBinding.tvUploadHintTrans.setVisibility(View.GONE);

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
                    equivalenceFileModel.setFileName("");                //Global.timestamp + ".png"
                    Global.selectedFilesListTraveling.add(equivalenceFileModel);
                }

                travellingDocumentAdapter.notifyDataSetChanged();

                return;
            }
        }
    }

    @Override
    public void getRequestResponse(JSONObject response, String requestType) throws JSONException {
        Gson gson = new Gson();

        if (requestType.equals(Constants.EDITQUALIFICATIONEQ)) {
            EditQualificationEQ editQualificationEQ = gson.fromJson(response.toString(), EditQualificationEQ.class);

            if (editQualificationEQ.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                Global.editQualificationEQResponse = editQualificationEQ;
                Global.utils.hideCustomLoadingDialog();

                /*TODO: For adding items in recyclerview list*/
                AddQualificationModel addQualificationModel = new AddQualificationModel();
                addQualificationModel.setCountry(country);
                addQualificationModel.setExaminationSystem("");
                addQualificationModel.setExaminingBody(examiningBody);
                addQualificationModel.setQualification(qualification);
                addQualificationModel.setGroup(equivalenceGroup);
                addQualificationModel.setSession(layoutBinding.etSession.getText().toString());
                addQualificationModel.setPurposeOfEquivalence(purpose_of_equivalence);
                addQualificationModel.setGradingSystem(equivalenceGradingSystem);
                addQualificationModel.setSubjectList(equivalenceSubjectList);

                //TODO: subjects and grade list
                List<GradesEQNew> equivalenceGradeList = new ArrayList<>();
                equivalenceGradeList.addAll(Global.selectedGradeList);
                addQualificationModel.setGradeList(equivalenceGradeList);

                //TODO; images and file
                List<EquivalenceFileModel> equivalenceFileModelList = new ArrayList<>();
                equivalenceFileModelList.addAll(Global.selectedFilesList);
                addQualificationModel.setSelectedFilesList(equivalenceFileModelList);

               /* Global.equivalenceQualificationList.add(addQualificationModel);
                EquivalenceEducationDetailsFragment.equivalenceEducationDetailsAdapter.notifyDataSetChanged();*/

                Global.equivalenceQualificationList.set(Global.selectedQualificationIndex, addQualificationModel);
                EquivalenceEducationDetailsFragment.equivalenceEducationDetailsAdapter.notifyDataSetChanged();
                finish();


            } else {
                Global.utils.hideCustomLoadingDialog();
                Global.utils.showErrorSnakeBar(this, editQualificationEQ.getResult().getResponseMessage());
            }
        }
    }
}