package com.webdoc.ibcc.DashBoard.Home.ApplyAttestation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.layer_net.stepindicator.StepIndicator;
import com.webdoc.ibcc.Adapter.EducationDetailsAdapter;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.DocumentSelection.DocumentSelectionFragment;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.EducationDetails.EducationDetailsFragment;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.PersonalInfo.PersonalInfoFragment;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.GenerateApp.GenerateAppFragment;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.AddDocumentDetailsResult.AddDocumentDetailsResult;
import com.webdoc.ibcc.ResponseModels.AddEducationResult.AddEducationResult;
import com.webdoc.ibcc.ResponseModels.DeleteEducationResult.DeleteEducationResult;
import com.webdoc.ibcc.ResponseModels.EditEducationResult.EditEducationResult;
import com.webdoc.ibcc.ResponseModels.PdfResult.Cerftificate;
import com.webdoc.ibcc.ResponseModels.Step1Result.Step1Result;
import com.webdoc.ibcc.ServerManager.VolleyListener;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.databinding.ActivityAttestationApplyBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AttestationApplyActivity extends AppCompatActivity /*implements VolleyListener*/ {
    public static StepIndicator stepIndicator;
    VolleyRequestController volleyRequestController;
    private ActivityAttestationApplyBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityAttestationApplyBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        //volleyRequestController = new VolleyRequestController(this);

        stepIndicator = layoutBinding.stepIndicator;
        stepIndicator.setClickable(false);

        if (Global.isIncompleteAppointment) {
            List<Cerftificate> cerftificateList = new ArrayList<>();
            cerftificateList.addAll(Global.pdfResponse.getResult().getCerftificates());
            Global.selectedCerti = cerftificateList;

            switch (Global.stepNumber) {
                case "2":
                    AttestationApplyActivity.stepIndicator.setCurrentStepPosition(1);
                    Fragment educationDetailsFragment = new EducationDetailsFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container,
                            educationDetailsFragment).addToBackStack(null).commit();
                    break;

                case "4":
                    AttestationApplyActivity.stepIndicator.setCurrentStepPosition(3);
                    Fragment fragment = new GenerateAppFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container,
                            fragment).addToBackStack(null).commit();
                    break;
            }
        } else {
            Fragment fragment = new PersonalInfoFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    fragment).addToBackStack(null).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) != null) {
            String fragment_container = getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_container).getClass().getSimpleName();

            switch (fragment_container) {
                case "PersonalInfoFragment":

                case "EducationDetailsFragment":
                    finish();
                    break;

                case "DocumentSelectionFragment":
                    stepIndicator.setCurrentStepPosition(1);
                    if (Global.isIncompleteAppointment) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new EducationDetailsFragment())
                                .addToBackStack(null).commit();
                    } else {
                        super.onBackPressed();
                    }
                    break;

                case "GenerateAppFragment":
                    stepIndicator.setCurrentStepPosition(2);
                    if (Global.isIncompleteAppointment) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new DocumentSelectionFragment())
                                .addToBackStack(null).commit();
                    } else {
                        super.onBackPressed();
                    }
                    break;

                case "PaymentFragment":
                    //stepIndicator.setCurrentStepPosition(3);
                    //super.onBackPressed();
                    Global.utils.showErrorSnakeBar(this, "Proceed to Payment to complete your appointment.");
                    break;

                default:
                    super.onBackPressed();
                    break;
            }
        }
    }

    /*@Override
    public void getRequestResponse(JSONObject response, String requestType) throws JSONException {
        Gson gson = new Gson();

        //done
        if (requestType.equals(Constants.STEP1)) {
            Step1Result step1Result = gson.fromJson(response.toString(), Step1Result.class);

            if (step1Result.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                Global.step1Response = step1Result;
                Global.utils.hideCustomLoadingDialog();

                Fragment educationDetailsFragment = new EducationDetailsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, educationDetailsFragment).addToBackStack(null).commit();

                // "id": "10" against values for essa
                Global.caseId = step1Result.getResult().getId();

                //Toast.makeText(this, step1Result.getResult().getResponseMessage(), Toast.LENGTH_SHORT).show();
            } else {
                // Toast.makeText(this, step1Result.getResult().getResponseMessage(), Toast.LENGTH_SHORT).show();
                Global.utils.hideCustomLoadingDialog();
                Global.utils.showErrorSnakeBar(this, step1Result.getResult().getResponseMessage());
            }
        }//step1

        //done
        if (requestType.equals(Constants.ADDEDUCATION)) {
            AddEducationResult addEducationResult = gson.fromJson(response.toString(), AddEducationResult.class);

            if (addEducationResult.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                Global.addEducationResponse = addEducationResult;

                for (int i = 0; i < addEducationResult.getResult().getEducationDetails().size(); i++) {
                    Global.selDocument.get(i).setDocId(addEducationResult.getResult().getEducationDetails().get(i).getDocId());
                }

                Global.utils.hideCustomLoadingDialog();

                EducationDetailsFragment.No_EducationRecord.setVisibility(View.GONE);
                EducationDetailsFragment.addEducationAlertDialog.dismiss();

                EducationDetailsFragment.educationDetailsAdapter = new EducationDetailsAdapter(this);
                EducationDetailsFragment.rv_educationDetails.setAdapter(EducationDetailsFragment.educationDetailsAdapter);

                Global.utils.showSuccessSnakeBar(this, "Added Successfully");

            } else {
                Global.utils.hideCustomLoadingDialog();
                EducationDetailsFragment.addEducationAlertDialog.dismiss();
                Global.utils.showErrorSnakeBar(this, addEducationResult.getResult().getResponseMessage());
            }
        }//add


        //done
        if (requestType.equals(Constants.DELETEEDUCATION)) {
            DeleteEducationResult deleteEducationResult = gson.fromJson(response.toString(), DeleteEducationResult.class);

            if (deleteEducationResult.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                Global.deleteEducationResponse = deleteEducationResult;
                Global.utils.hideCustomLoadingDialog();

                Global.addEducationResponse.getResult().getEducationDetails().remove(Global.deleteEduPosition);
                Global.selDocument.remove(Global.deleteEduPosition);

                EducationDetailsFragment.educationDetailsAdapter.notifyDataSetChanged();
                if (Global.addEducationResponse.getResult().getEducationDetails().size() == 0) {
                    EducationDetailsFragment.No_EducationRecord.setVisibility(View.VISIBLE);
                }

                Global.utils.showSuccessSnakeBar(this, "Deleted Successfully");
            } else {
                Global.utils.hideCustomLoadingDialog();
                Global.utils.showErrorSnakeBar(this, deleteEducationResult.getResult().getResponseMessage());
            }
        }//delete


        //done
        if (requestType.equals(Constants.EDITEDUCATION)) {
            EditEducationResult editEducationResult = gson.fromJson(response.toString(), EditEducationResult.class);

            if (editEducationResult.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                Global.editEducationResponse = editEducationResult;
                Global.utils.hideCustomLoadingDialog();

                //Global.addEducationResponse.getResult().getEducationDetails().clear();
                //volleyRequestController.addEducation(Global.UserEducDetail);

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

                EducationDetailsFragment.educationDetailsAdapter.notifyItemChanged(Global.editEduPosition);
                EducationDetailsAdapter.updateEducationAlertDialog.dismiss();

                Global.utils.showSuccessSnakeBar(this, "Edited Successfully");

            } else {
                // Toast.makeText(this, editEducationResult.getResult().getResponseMessage(), Toast.LENGTH_SHORT).show();
                Global.utils.hideCustomLoadingDialog();
                EducationDetailsAdapter.updateEducationAlertDialog.dismiss();
                Global.utils.showErrorSnakeBar(this, editEducationResult.getResult().getResponseMessage());
            }
        }//edit

        //done
        if (requestType.equals(Constants.ADDDOCUMENTDETAILS)) {
            AddDocumentDetailsResult addDocumentDetailsResult = gson.fromJson(response.toString(), AddDocumentDetailsResult.class);

            if (addDocumentDetailsResult.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                Global.addDocumentDetailsResponse = addDocumentDetailsResult;
                Global.utils.hideCustomLoadingDialog();

                //Global.enableAddDocument = false;
                Global.enableAddDocument = true;

                Fragment educationDetailsFragment = new GenerateAppFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, educationDetailsFragment).addToBackStack(null).commit();

            } else {
                Global.utils.hideCustomLoadingDialog();
                Global.utils.showErrorSnakeBar(this, "Please add document details properly.");
                // Global.utils.showErrorSnakeBar(this, addDocumentDetailsResult.getResult().getResponseMessage());
            }
        }
    }*/


}