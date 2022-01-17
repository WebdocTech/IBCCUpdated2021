package com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.AddQualification;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.webdoc.ibcc.Adapter.SelectedFilesAdapter;
import com.webdoc.ibcc.Adapter.SelectedTravellingDocumentAdapter;
import com.webdoc.ibcc.Adapter.Spinner.Equivalence.CountriesAdapter;
import com.webdoc.ibcc.Adapter.Spinner.Equivalence.EquivalenceGradingSystemAdapter;
import com.webdoc.ibcc.Adapter.Spinner.Equivalence.ExaminingBodyAdapter;
import com.webdoc.ibcc.Adapter.Spinner.Equivalence.GroupAdapter;
import com.webdoc.ibcc.Adapter.Spinner.Equivalence.QualificationAdapter;
import com.webdoc.ibcc.Adapter.SubjectsAdapter;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.EquivalenceEducationDetailsFragment;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.UpdateQualification.UpdateQualification;
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
import com.webdoc.ibcc.Model.DeleteParams;
import com.webdoc.ibcc.Model.EquivalenceFileModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.AddQualificationEQ.AddQualificationEQ;
import com.webdoc.ibcc.ServerManager.VolleyListener;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.databinding.ActivityAddQualificationBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

public class AddQualification extends AppCompatActivity implements VolleyListener {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static RecyclerView rv_subjects, rv_files, rv_files_trans;
    private SubjectsAdapter subjectsAdapter;
    private SelectedFilesAdapter selectedFilesAdapter;
    private SelectedTravellingDocumentAdapter travellingDocumentAdapter;
    public static TextView tv_uploadHint;
    private ArrayAdapter<CharSequence> spinner_purpose_of_equivalence_adapter,
            spinner_examination_system_adapter;
    private String purpose_of_equivalence, examination_system;
    private Country country;
    private ExaminingBody examiningBody;
    private Qualification qualification;
    private GroupEQNew equivalenceGroup;
    private EquivalenceGradingSystemEQNew equivalenceGradingSystem;
    private List<Country> countriesList;
    private List<ExaminingBody> examiningBodyList;
    private List<Qualification> qualificationList;
    private List<EquivalenceGradingSystemEQNew> equivalenceGradingSystemList;
    private List<GroupEQNew> equivalenceGroupList;
    private List<EquivalenceSubjectEQNew> equivalenceSubjectList;
    private AlertDialog fileChooserAlertDialog;
    public static final int PDF_REQUEST_CODE = 100;
    public static final int GALLERY_REQUEST_CODE = 200;
    private VolleyRequestController volleyRequestController;
    private ActivityAddQualificationBinding layoutBinding;
    private Spinner spinnerQualification;
    private boolean isMarksAdded = false;
    private HomeSharedViewModel viewModel;

    private static final int RESULT_LOAD_DOCUMENT = 1;
    private static final int WRITE_PERMISSION = 2;
    private static final int RESULT_LOAD_IMAGE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityAddQualificationBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        viewModel = ViewModelProviders.of(this).get(HomeSharedViewModel.class);

        Global.phptravellingFiles.clear();
        Global.phpfiles.clear();
        volleyRequestController = new VolleyRequestController(this);
        Global.selectedFilesList.clear();
        Global.selectedFilesListTraveling.clear();
        Global.applicationContext = AddQualification.this;

        //statics:
        rv_subjects = layoutBinding.rvSubjects;
        rv_files = layoutBinding.rvFiles;
        rv_files_trans = layoutBinding.rvFilesTrans;
        tv_uploadHint = layoutBinding.tvUploadHint;

        //setUp Adapters:
        setSubjectsAdapter();
        setFilesAdapter();
        setFilesTransAdapter();

        clickListeners();
        setUpSpinners();
        observers();
    }

    private void clickListeners() {
        layoutBinding.btnSelectSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutBinding.ccAddMarks.setVisibility(View.VISIBLE);
                layoutBinding.ccAllitems.setVisibility(View.GONE);
            }
        });

        layoutBinding.btnAddMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.utils.hideKeyboard(AddQualification.this);
                Boolean flag = true;
                for (int i = 0; i < Global.selectedGradeList.size(); i++) {
                    // id -1 for "Please Select*"
                    if (Global.selectedGradeList.get(i).getId() == -1) {
                        flag = false;
                    }
                }
                if (flag) {
                    isMarksAdded = true;
                    layoutBinding.ccAddMarks.setVisibility(View.GONE);
                    layoutBinding.ccAllitems.setVisibility(View.VISIBLE);
                    layoutBinding.btnSelectSubject.setBackground(getDrawable(R.drawable.green_button_background));
                    layoutBinding.btnSelectSubject.setText("Added Successfully");
                } else {
                    isMarksAdded = false;
                    Global.utils.showErrorSnakeBar(AddQualification.this, "Please Fill all Fields");
                }
            }
        });

        layoutBinding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.isFromDocument = true;
                if (ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, WRITE_PERMISSION);
                } else {
                    showFileChooser();
                }
            }
        });

        layoutBinding.btnUploadTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.isFromDocument = false;
                //verifyStoragePermissions(AddQualification.this);
                if (ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, WRITE_PERMISSION);

                } else {
                    showFileChooser();
                }
            }
        });

        layoutBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isExist = false;
                for (int i = 0; i < Global.qualificationIdArray.size(); i++) {
                    isExist = false;
                    if (Global.qualificationIdArray.get(i).equals(qualification.getId())) {
                        isExist = true;
                        break;
                    } else {
                        isExist = false;
                    }
                }

                if (isExist) {
                    layoutBinding.spinnerQualification.requestFocus();
                    TextView errorText = (TextView) spinnerQualification.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);
                    errorText.setText("* Select another qualification !");
                    Global.utils.showErrorSnakeBar(AddQualification.this, "Please select another qualification !");

                } else {
                    if (isValid() && isMarksAdded) {

                        /*TODO: For API calling*/
                        if (!Global.isIncompleteAppointmentEQ) {
                            Global.equivalenceAddQualification.setEmail(Global.equivalenceInitiateCase.getEmail());
                            Global.equivalenceAddQualification.setMailingAddress(Global.equivalenceInitiateCase.getEmail());
                            Global.equivalenceAddQualification.setTelNo(Global.equivalenceInitiateCase.getPhone());
                            Global.equivalenceAddQualification.setFatherCnic(Global.equivalenceInitiateCase.getFatherCnic());
                            Global.equivalenceAddQualification.setPresentEmploymentOfParents(Global.equivalenceInitiateCase.getParentsEmployment());
                            Global.equivalenceAddQualification.setFatherName(Global.equivalenceInitiateCase.getFatherName());
                            Global.equivalenceAddQualification.setParentsPermanentAddress(Global.equivalenceInitiateCase.getFatherAddress());
                            Global.equivalenceAddQualification.setParentsNameOfTheOrganization(Global.equivalenceInitiateCase.getNameOfOrganization());

                            Global.equivalenceAddQualification.setCaseId(Global.equivalenceInitiateCaseResponse.getResult().getIntiateCaseResponseDetails().getCaseId().toString());
                        } else {
                            Global.equivalenceAddQualification.setEmail("");
                            Global.equivalenceAddQualification.setMailingAddress("");
                            Global.equivalenceAddQualification.setTelNo("");
                            Global.equivalenceAddQualification.setFatherCnic("");
                            Global.equivalenceAddQualification.setPresentEmploymentOfParents("");
                            Global.equivalenceAddQualification.setFatherName("");
                            Global.equivalenceAddQualification.setParentsPermanentAddress("");
                            Global.equivalenceAddQualification.setParentsNameOfTheOrganization("");
                            Global.equivalenceAddQualification.setCaseId(Global.caseIdQualificationEQ);
                        }

                        Global.equivalenceAddQualification.setCountryId(String.valueOf(country.getId()));

                        Global.equivalenceAddQualification.setExaminationSystem(examination_system);
                        Global.equivalenceAddQualification.setExaminingBody(examiningBody.getName());
                        Global.equivalenceAddQualification.setPurposeOfEquivalence(purpose_of_equivalence);
                        Global.equivalenceAddQualification.setNameOfTheInstitution("");
                        Global.equivalenceAddQualification.setOtherExaminingBody("");
                        Global.equivalenceAddQualification.setGradingSystemId(String.valueOf(equivalenceGradingSystem.getId()));
                        Global.equivalenceAddQualification.setGroupId(String.valueOf(equivalenceGroup.getId()));
                        Global.equivalenceAddQualification.setSession(layoutBinding.etSession.getText().toString());
                        Global.equivalenceAddQualification.setQualificationId(String.valueOf(qualification.getId()));
                        Global.equivalenceAddQualification.setTitleOfQualification(qualification.getName());
                        Global.equivalenceAddQualification.setImagesTravellingList(Global.imagesTravellinglList);

                        Global.qualificationId = String.valueOf(qualification.getId());
                        Global.qualificationIdArray.add(String.valueOf(qualification.getId()));

                        //Todo: SubjectList
                        List<GradesEQNew> subjectEducationList = new ArrayList<>();
                        subjectEducationList.addAll(Global.selectedGradeList);
                        Global.equivalenceAddQualification.setSubjectEducationList(subjectEducationList);

                        //Todo: imageList for document
                        Global.Count = 1;

                        Global.equivalenceAddQualification.setImagesEductaionList(Global.imagesEducationlList);
                        Global.equivalenceAddQualification.setImagesTravellingList(Global.imagesTravellinglList);

                        Global.utils.showCustomLoadingDialog(AddQualification.this);
                        viewModel.callImageDocumentApi(AddQualification.this);
                    } else {
                        if (!isMarksAdded) {
                            Global.utils.showErrorSnakeBar(AddQualification.this, "Please add subjects Marks !");
                        }
                    }

                }
            }
        });
    }

    public boolean isValid() {
        if (TextUtils.isEmpty(country.getName()) || TextUtils.isEmpty(examiningBody.getName()) || TextUtils.isEmpty(qualification.getName())
                || TextUtils.isEmpty(equivalenceGroup.getName()) || TextUtils.isEmpty(layoutBinding.etSession.getText().toString()) || TextUtils.isEmpty(purpose_of_equivalence)
                || TextUtils.isEmpty(equivalenceGradingSystem.getName()) || equivalenceSubjectList.size() == 0 || Global.selectedGradeList.size() == 0) {
            Global.utils.showErrorSnakeBar(AddQualification.this, "Please fill all fields");
            return false;
        } else if (Global.equivalenceOnline) {
            if (Global.selectedFilesList.size() == 0) {
                Global.utils.showErrorSnakeBar(AddQualification.this, "Please select files to upload");
                return false;
            }
        }

        return true;
    }

    private void setUpSpinners() {
        countriesList = new ArrayList<>();
        for (int i = 0; i < Global.detailsEquivalenceNewModel.getResult().getCountries().size(); i++) {
            countriesList.add(Global.detailsEquivalenceNewModel.getResult().getCountries().get(i));
        }

        /*country = countriesList.get(0);
        for (int i = 0; i < countriesList.size(); i++) {
            qualificationList.add(countriesList.get(i));
        }*/

        qualificationList = new ArrayList<>();
        for (int i = 0; i < Global.detailsEquivalenceNewModel.getResult().getQualification().size(); i++) {
            int str1 = Global.detailsEquivalenceNewModel.getResult().getQualification().get(i).getCountryId();
            int str2 = countriesList.get(0).getId();
            if (str1 == str2) {
                qualificationList.add(Global.detailsEquivalenceNewModel.getResult().getQualification().get(i));
            }
        }
        //qualificationList = Global.detailsEquivalenceNewModel.getResult().getQualification();

        //COUNTRIES SPINNER
        CountriesAdapter spinnerCountriesAdapter = new CountriesAdapter(this, R.layout.spinner_item, countriesList);
        layoutBinding.spinnerCountry.setAdapter(spinnerCountriesAdapter);

        layoutBinding.spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                country = countriesList.get(position);

                //EXAMINING BODY
                /*examiningBodyList = new ArrayList<>();
                for (int i = 0; i < country.getExaminingBody().size(); i++) {
                    examiningBodyList.add(country.getExaminingBody().get(i));
                }*/

                examiningBodyList = new ArrayList<>();
                for (int i = 0; i < Global.detailsEquivalenceNewModel.getResult().getExaminingBody().size(); i++) {
                    int str1 = Global.detailsEquivalenceNewModel.getResult().getExaminingBody().get(i).getCountryId();
                    int str2 = country.getId();
                    if (str1 == str2) {
                        examiningBodyList.add(Global.detailsEquivalenceNewModel.getResult().getExaminingBody().get(i));
                    }
                }

                ExaminingBodyAdapter examiningBodyAdapter = new ExaminingBodyAdapter(
                        AddQualification.this, R.layout.spinner_item, examiningBodyList);
                layoutBinding.spinnerExaminingBody.setAdapter(examiningBodyAdapter);

                //QUALIFICATION
                /*qualificationList = new ArrayList<>();
                for (int i = 0; i < country.getQualification().size(); i++) {
                    qualificationList.add(country.getQualification().get(i));
                }*/

                qualificationList = new ArrayList<>();
                for (int i = 0; i < Global.detailsEquivalenceNewModel.getResult().getQualification().size(); i++) {
                    int str1 = Global.detailsEquivalenceNewModel.getResult().getQualification().get(i).getCountryId();
                    int str2 = country.getId();
                    if (str1 == str2) {
                        qualificationList.add(Global.detailsEquivalenceNewModel.getResult().getQualification().get(i));
                    }
                }

                QualificationAdapter qualificationAdapter = new QualificationAdapter(AddQualification.this,
                        R.layout.spinner_item, qualificationList);
                layoutBinding.spinnerQualification.setAdapter(qualificationAdapter);

                /*For Examination System Spinner*/
                if (country.getName().equalsIgnoreCase("Pakistan")) {
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


        /*qualificationList = new ArrayList<>();
        for (int i = 0; i < country.getQualification().size(); i++) {
            qualificationList.add(country.getQualification().get(i));
        }*/

        QualificationAdapter qualificationAdapter = new QualificationAdapter(AddQualification.this,
                R.layout.spinner_item,
                Global.detailsEquivalenceNewModel.getResult().getQualification());
        layoutBinding.spinnerQualification.setAdapter(qualificationAdapter);


        if (Global.equivalenceAddQualification.getQualificationId() != null) {
            spinnerQualification = findViewById(R.id.spinner_qualification);
            String str = Global.equivalenceAddQualification.getQualificationId();
            if (Global.equivalenceAddQualification.getQualificationId().equals(Global.qualificationId)) {
                 /* new Handler().post(() ->
                        ((TextView)spinnerQualification.getSelectedView()).setError("ddfdd"));*/
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TextView errorText = (TextView) spinnerQualification.getSelectedView();
                        errorText.setError("");
                        errorText.setTextColor(Color.RED);
                        errorText.setText("* Select another qualification !");
                    }
                }, 2000);
            }
        }

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
                    int gradingSystemEQID = Global.detailsEquivalenceNewModel.getResult().getEquivalenceGradingSystemEQNew().get(i).getQualificationId();
                    int qualificationID = qualificationList.get(position).getId();
                    if (gradingSystemEQID == qualificationID) {
                        equivalenceGradingSystemList.add(Global.detailsEquivalenceNewModel.getResult().getEquivalenceGradingSystemEQNew().get(i));
                    }
                }

                EquivalenceGradingSystemEQNew temp = new EquivalenceGradingSystemEQNew();
                temp.setId(0);
                temp.setName("Marks");
                temp.setQualificationId(qualificationList.get(position).getId());
                // Global.detailsEquivalenceNewModel.getResult().getEquivalenceGradingSystemEQNew().add(temp);
                equivalenceGradingSystemList.add(temp);


                EquivalenceGradingSystemAdapter equivalenceGradingSystemAdapter = new EquivalenceGradingSystemAdapter(AddQualification.this,
                        R.layout.spinner_item, equivalenceGradingSystemList);
                layoutBinding.spinnerGradingSystem.setAdapter(equivalenceGradingSystemAdapter);

                //change subject Button:
                isMarksAdded = false;
                layoutBinding.btnSelectSubject.setBackground(getDrawable(R.drawable.red_button_background));
                layoutBinding.btnSelectSubject.setText("Select Subject Grades/Marks");


                //EQUIVALENCE GROUP
                /*equivalenceGroupList = new ArrayList<>();
                for (int i = 0; i < qualification.getEquivalenceGroup().size(); i++) {
                    equivalenceGroupList.add(qualification.getEquivalenceGroup().get(i));
                }*/

                //List<GroupEQNew> groupList = new ArrayList<>();
                equivalenceGroupList = new ArrayList<>();
                for (int i = 0; i < Global.detailsEquivalenceNewModel.getResult().getGroupEQNew().size(); i++) {
                    int gradingSystemEQID = Global.detailsEquivalenceNewModel.getResult().getGroupEQNew().get(i).getQualificationId();
                    int qualificationID = qualificationList.get(position).getId();
                    if (gradingSystemEQID == qualificationID) {
                        equivalenceGroupList.add(Global.detailsEquivalenceNewModel.getResult().getGroupEQNew().get(i));
                    }
                }

                GroupAdapter groupAdapter = new GroupAdapter(AddQualification.this,
                        R.layout.spinner_item, equivalenceGroupList);
                layoutBinding.spinnerGroup.setAdapter(groupAdapter);

                /*Examination System Spinner Visibility*/
                if (qualification.getName().equalsIgnoreCase("High School Diploma")) {
                    layoutBinding.textView16.setVisibility(View.VISIBLE);
                    layoutBinding.ExaminationSystemLayout.setVisibility(View.VISIBLE);

                    //SPINNER EXAMINATION SYSTEM
                    spinner_examination_system_adapter = ArrayAdapter.createFromResource(AddQualification.this,
                            R.array.examination_system_array, R.layout.spinner_item);
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

        //GRADING SYSTEM SPINNER
        layoutBinding.spinnerGradingSystem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                equivalenceGradingSystem = equivalenceGradingSystemList.get(position);

               /* if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    Global.equivalenceGradeList.add(Global.detailsEquivalenceNewModel.getResult().getGradesEQNew().stream().filter(y -> y.getId().equals(String.valueOf(position))).findFirst().get());
                }*/

                Global.equivalenceGradeList.clear();
                GradesEQNew gradesEQNew = new GradesEQNew();
                gradesEQNew.setEquivalenceGradingSystemId(-1);
                gradesEQNew.setId(-1);
                gradesEQNew.setName("Please Select *");
                Global.equivalenceGradeList.add(gradesEQNew);
                for (int i = 0; i < Global.detailsEquivalenceNewModel.getResult().getGradesEQNew().size(); i++) {
                    int strGradingSystemID = equivalenceGradingSystemList.get(position).getId();
                    int GradeID = Global.detailsEquivalenceNewModel.getResult().getGradesEQNew().get(i).getEquivalenceGradingSystemId();
                    if (GradeID == strGradingSystemID) {
                        Global.equivalenceGradeList.add(Global.detailsEquivalenceNewModel.getResult().getGradesEQNew().get(i));
                    }
                }

                Global.equivalenceGradingSystemName = equivalenceGradingSystem.getName();

                /*if(equivalenceGradingSystem.getName().equalsIgnoreCase("Marks")) {
                    Toast.makeText(AddQualification.this, "done", Toast.LENGTH_SHORT).show();

                } else{
                }*/

                subjectsAdapter.notifyDataSetChanged();

                //change subject Button:
                isMarksAdded = false;
                layoutBinding.btnSelectSubject.setBackground(getDrawable(R.drawable.red_button_background));
                layoutBinding.btnSelectSubject.setText("Select Subject Grades/Marks");
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
                    int str2 = Global.detailsEquivalenceNewModel.getResult().getEquivalenceSubjectEQNew().get(i).getGroupId();
                    int str1 = equivalenceGroupList.get(position).getId();
                    if (str1 == str2) {
                        equivalenceSubjectList.add(Global.detailsEquivalenceNewModel.getResult().getEquivalenceSubjectEQNew().get(i));
                        GradesEQNew equivalenceGrade = new GradesEQNew();
                        Global.selectedGradeList.add(equivalenceGrade);
                    }
                }

                subjectsAdapter = new SubjectsAdapter(AddQualification.this, equivalenceSubjectList);
                layoutBinding.rvSubjects.setAdapter(subjectsAdapter);
                Global.equivalenceSubjectList = equivalenceSubjectList;

                //change subject Button:
                isMarksAdded = false;
                layoutBinding.btnSelectSubject.setBackground(getDrawable(R.drawable.red_button_background));
                layoutBinding.btnSelectSubject.setText("Select Subject Grades/Marks");
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        //SPINNER PURPOSE OF EQUIVALENCE
        spinner_purpose_of_equivalence_adapter = ArrayAdapter.createFromResource(this, R.array.poe_array, R.layout.spinner_item);
        layoutBinding.spinnerPurposeOfEquivalence.setAdapter(spinner_purpose_of_equivalence_adapter);
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
                    viewModel.callImageTravellingApi(AddQualification.this);
                } else {
                    if (Global.isFromEditQualitifcation) {
                        Global.utils.showCustomLoadingDialog(Global.applicationContext);
                        equivalenceAddQualificationApi();

                    } else {
                        Global.utils.showCustomLoadingDialog(Global.applicationContext);
                        equivalenceAddQualificationApi();
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
                if (Global.isFromEditQualitifcation) {
                    Global.utils.showCustomLoadingDialog(Global.applicationContext);
                    equivalenceAddQualificationApi();
                } else {
                    Global.utils.showCustomLoadingDialog(Global.applicationContext);
                    equivalenceAddQualificationApi();
                }
            }
        });

        viewModel.getEquivalenveAddQualification().observe(this, response -> {
            if (response != null) {

                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {

                    Global.addQualificationEQResponse = response;
                    /*TODO: For adding items in recyclerview list*/
                    AddQualificationModel addQualificationModel = new AddQualificationModel();
                    addQualificationModel.setCountry(country);
                    addQualificationModel.setExaminationSystem(examination_system);
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

                    Global.equivalenceQualificationList.add(addQualificationModel);
                    EquivalenceEducationDetailsFragment.equivalenceEducationDetailsAdapter.notifyDataSetChanged();

                    Global.deleteParams.clear();
                    for (int i = 0; i < response.getResult().getDocumentDetails().size(); i++) {
                        Global.caseId_Equ = response.getResult().getDocumentDetails().get(i).getCaseId();
                        Global.docId_Equ = response.getResult().getDocumentDetails().get(i).getDocId();
                        DeleteParams deleteParams = new DeleteParams();
                        deleteParams.setCaseId(response.getResult().getDocumentDetails().get(i).getCaseId());
                        deleteParams.setDocId(response.getResult().getDocumentDetails().get(i).getDocId());
                        Global.deleteParams.add(deleteParams);
                    }

                    //Clear Data:
                    Global.imagesEducationlList.clear();
                    Global.imagesTravellinglList.clear();
                    Global.selectedGradeList.clear();

                    finish();
                } else {
                    Global.utils.showErrorSnakeBar(this, response.getResult().getResponseMessage());
                }
            }
        });
    }

    private void setSubjectsAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvSubjects.setLayoutManager(layoutManager);
        layoutBinding.rvSubjects.setHasFixedSize(true);
        SnapHelper snapHelperSubjects = new PagerSnapHelper();
        snapHelperSubjects.attachToRecyclerView(layoutBinding.rvSubjects);
        equivalenceSubjectList = new ArrayList<>();
        subjectsAdapter = new SubjectsAdapter(this, equivalenceSubjectList);
        layoutBinding.rvSubjects.setAdapter(subjectsAdapter);
    }

    private void setFilesAdapter() {
        final LinearLayoutManager filesLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        layoutBinding.rvFiles.setLayoutManager(filesLayoutManager);
        layoutBinding.rvFiles.setHasFixedSize(true);
        SnapHelper snapHelperFiles = new PagerSnapHelper();
        snapHelperFiles.attachToRecyclerView(layoutBinding.rvFiles);
        selectedFilesAdapter = new SelectedFilesAdapter(this);
        layoutBinding.rvFiles.setAdapter(selectedFilesAdapter);
    }

    private void setFilesTransAdapter() {
        final LinearLayoutManager filesLayoutManager2 = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        layoutBinding.rvFilesTrans.setLayoutManager(filesLayoutManager2);
        layoutBinding.rvFilesTrans.setHasFixedSize(true);
        SnapHelper snapHelperFiles2 = new PagerSnapHelper();
        snapHelperFiles2.attachToRecyclerView(layoutBinding.rvFilesTrans);
        Global.applicationContext = AddQualification.this;
        travellingDocumentAdapter = new SelectedTravellingDocumentAdapter(this);
        layoutBinding.rvFilesTrans.setAdapter(travellingDocumentAdapter);
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
                //android 11 permissions:
                /*if (SDK_INT >= Build.VERSION_CODES.R) {
                    if (checkPermission()) {
                        selectPDF();
                        fileChooserAlertDialog.dismiss();
                    } else {
                        requestPermission();
                    }
                } else {*/
                selectPDF();
                fileChooserAlertDialog.dismiss();
                //}
            }
        });

        tv_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (SDK_INT >= 30) {
                    //only api 30 above
                    Toast.makeText(AddQualification.this, "Unable to select files in Android version 11", Toast.LENGTH_SHORT).show();
                } else {*/
                selectPDF();
                fileChooserAlertDialog.dismiss();
                //}
            }
        });

        iv_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //android 11 permissions:
                /*if (SDK_INT >= Build.VERSION_CODES.R) {
                    if (checkPermission()) {*/
                selectGalleryImages();
                fileChooserAlertDialog.dismiss();
                    /*} else {
                        requestPermission();
                    }
                } else {
                    selectGalleryImages();
                    fileChooserAlertDialog.dismiss();
                }*/
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
    }

    // for android version 11 or above:
    private boolean checkPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int result = ContextCompat.checkSelfPermission(AddQualification.this, READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(AddQualification.this, WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        }
    }

    // for android version 11 or above:
    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                startActivityForResult(intent, 2296);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2296);
            }
        } else {
            //below android 11
            //ActivityCompat.requestPermissions(AddQualification.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    public void selectPDF() {
        /*Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(Intent.createChooser(intent, "Select PDF(s)"), PDF_REQUEST_CODE);*/

        // scope storage work for PDF file
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, WRITE_PERMISSION);
            } else {
                Intent i = new Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                i.setType("application/pdf");
                i.setAction(Intent.ACTION_OPEN_DOCUMENT);
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(i, PDF_REQUEST_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectGalleryImages() {
        /*Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Image(s)"), GALLERY_REQUEST_CODE);*/

        // scope storage permission for gallery...
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, WRITE_PERMISSION);
            } else {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(i, GALLERY_REQUEST_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (Global.isFromDocument) {
            if (requestCode == PDF_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
                layoutBinding.tvUploadHint.setVisibility(View.GONE);
                Global.ispdf = true;
                Uri pdfUri = data.getData();
                String uriString = pdfUri.toString();
                File myFile = new File(uriString);
                int filesize = getfilesize(pdfUri);
                if (filesize > 2) {
                    Global.utils.showErrorSnakeBar(AddQualification.this,
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
                    Global.phpfiles.add(photos.get(i)); //Global.timestamp + ".png"
                    Global.selectedFilesList.add(equivalenceFileModel);
                }
                selectedFilesAdapter.notifyDataSetChanged();
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
                    Global.utils.showErrorSnakeBar(AddQualification.this,
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
                    equivalenceFileModel.setFileName("");                //Global.timestamp + ".png"
                    Global.phptravellingFiles.add(photos.get(i));
                    Global.selectedFilesListTraveling.add(equivalenceFileModel);
                }
                travellingDocumentAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showFileChooser();
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
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

    @Override
    public void getRequestResponse(JSONObject response, String requestType) throws JSONException {
        Gson gson = new Gson();

        if (requestType.equals(Constants.ADDQUALIFICATIONEQ)) {
            AddQualificationEQ addQualificationEQ = gson.fromJson(response.toString(), AddQualificationEQ.class);

            if (addQualificationEQ.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                Global.utils.hideCustomLoadingDialog();
                Global.addQualificationEQResponse = addQualificationEQ;
                /*TODO: For adding items in recyclerview list*/
                AddQualificationModel addQualificationModel = new AddQualificationModel();
                addQualificationModel.setCountry(country);
                addQualificationModel.setExaminationSystem(examination_system);
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

                Global.equivalenceQualificationList.add(addQualificationModel);
                EquivalenceEducationDetailsFragment.equivalenceEducationDetailsAdapter.notifyDataSetChanged();

                for (int i = 0; i < addQualificationEQ.getResult().getDocumentDetails().size(); i++) {
                    Global.caseId_Equ = addQualificationEQ.getResult().getDocumentDetails().get(i).getCaseId();
                    Global.docId_Equ = addQualificationEQ.getResult().getDocumentDetails().get(i).getDocId();
                    DeleteParams deleteParams = new DeleteParams();
                    deleteParams.setCaseId(addQualificationEQ.getResult().getDocumentDetails().get(i).getCaseId());
                    deleteParams.setDocId(addQualificationEQ.getResult().getDocumentDetails().get(i).getDocId());
                    Global.deleteParams.add(deleteParams);
                }

                finish();
            } else {
                Global.utils.hideCustomLoadingDialog();
                Global.utils.showErrorSnakeBar(this, addQualificationEQ.getResult().getResponseMessage());
            }
        }
    }

    private static String[] PERMISSIONS_STORAGE = {
            READ_EXTERNAL_STORAGE,
            WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void setSpinnerError(Spinner spinner, String error) {
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.
        }
    }

    public void equivalenceAddQualificationApi() {
        JsonObject params = new JsonObject();

        params.addProperty("countryId", Integer.parseInt(Global.equivalenceAddQualification.getCountryId()));
        params.addProperty("session", Global.equivalenceAddQualification.getSession());
        params.addProperty("fatherCnic", Global.equivalenceAddQualification.getFatherCnic());
        params.addProperty("fatherName", Global.equivalenceAddQualification.getFatherName());
        params.addProperty("email", Global.equivalenceAddQualification.getEmail());
        params.addProperty("titleOfQualification", Global.equivalenceAddQualification.getTitleOfQualification());
        params.addProperty("mailingAddress", Global.equivalenceAddQualification.getMailingAddress());
        params.addProperty("gradingSystemId", Integer.parseInt(Global.equivalenceAddQualification.getGradingSystemId()));
        params.addProperty("groupId", Integer.parseInt(Global.equivalenceAddQualification.getGroupId()));
        params.addProperty("presentEmploymentOfParents", Global.equivalenceAddQualification.getPresentEmploymentOfParents());
        params.addProperty("purposeOfEquivalence", Global.equivalenceAddQualification.getPurposeOfEquivalence());
        params.addProperty("parentsNameOfTheOrganization", Global.equivalenceAddQualification.getParentsNameOfTheOrganization());
        params.addProperty("qualificationId", Integer.parseInt(Global.equivalenceAddQualification.getQualificationId()));
        params.addProperty("otherExaminingBody", Global.equivalenceAddQualification.getOtherExaminingBody());
        params.addProperty("examinationSystem", Global.equivalenceAddQualification.getExaminationSystem());
        params.addProperty("caseId", Integer.parseInt(Global.equivalenceAddQualification.getCaseId()));
        params.addProperty("parentsPermanentAddress", Global.equivalenceAddQualification.getPresentEmploymentOfParents());
        params.addProperty("nameOfTheInstitution", Global.equivalenceAddQualification.getNameOfTheInstitution());
        params.addProperty("telNo", Global.equivalenceAddQualification.getTelNo());
        params.addProperty("examiningBody", Global.equivalenceAddQualification.getExaminingBody());

        JsonArray documentArray = new JsonArray();
        for (int i = 0; i < Global.equivalenceAddQualification.getImagesEductaionList().size(); i++) {
            JsonObject imgItem = new JsonObject();
            imgItem.addProperty("imagename", Global.imagesEducationlList.get(i));
            documentArray.add(imgItem);
        }
        params.add("imagesEductaion", documentArray);

        //working on this travelling part....
        JsonArray documentTravellingArray = new JsonArray();
        for (int k = 0; k < Global.equivalenceAddQualification.getImagesTravellingList().size(); k++) {
            JsonObject imgItem = new JsonObject();
            //20210702065122.png
            imgItem.addProperty("imagename", Global.imagesTravellinglList.get(k));
            documentTravellingArray.add(imgItem);
        }
        params.add("imagestravelling", documentTravellingArray);

        JsonArray subjectArray = new JsonArray();
        for (int j = 0; j < Global.equivalenceAddQualification.getSubjectEducationList().size(); j++) {
            JsonObject subjItem = new JsonObject();
            subjItem.addProperty("subjectId", String.valueOf(Global.selectedGradeList.get(j).getId()));
            subjItem.addProperty("marksgrades", Global.selectedGradeList.get(j).getName());
            subjectArray.add(subjItem);
        }

        params.add("subjectEductaion", subjectArray);

        System.out.println("AddQualification Params: " + params);

        viewModel.callEquivalenceAddQualificationApi(this, params);
        //postApiResultManager.jsonParse(Constants.ADDQUALIFICATIONEQ, params);
    }

    @Override
    public void onBackPressed() {
        if (layoutBinding.ccAddMarks.getVisibility() == View.VISIBLE) {
            layoutBinding.ccAddMarks.setVisibility(View.GONE);
            layoutBinding.ccAllitems.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }
}