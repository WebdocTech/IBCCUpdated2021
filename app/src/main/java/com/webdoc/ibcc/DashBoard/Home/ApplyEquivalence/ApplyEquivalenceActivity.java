package com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.layer_net.stepindicator.StepIndicator;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.CallCourier_EQ.CallCourier_EQ;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.DocumentSelection.EquivalenceDocumentSelectionFragment;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.EquivalenceEducationDetailsFragment;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.ChooseEqu_Method.EquivalenceMethodFragment;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.GenerateApp.EquivalenceGenerateAppFragment;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.IntiateCase.IntiateCase;
import com.webdoc.ibcc.ResponseModels.RemoveQualificationEQ.RemoveQualificationEQ;
import com.webdoc.ibcc.ResponseModels.SaveDocumentDetailsEQ.SaveDocumentDetailsEQ;
import com.webdoc.ibcc.ServerManager.VolleyListener;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.databinding.ActivityApplyEquivalenceBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class ApplyEquivalenceActivity extends AppCompatActivity implements VolleyListener {
    public static StepIndicator stepIndicator;
    VolleyRequestController volleyRequestController;
    private ActivityApplyEquivalenceBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityApplyEquivalenceBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        volleyRequestController = new VolleyRequestController(this);

        stepIndicator = findViewById(R.id.stepIndicator);
        stepIndicator.setClickable(false);

        if (!Global.isIncompleteAppointmentEQ) {
            Fragment fragment = new EquivalenceMethodFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.equivalence_fragment_container, fragment).addToBackStack(null).commit();

        } else {
            switch (Global.stepNumberEQ) {
                case "2":
                    ApplyEquivalenceActivity.stepIndicator.setCurrentStepPosition(1);
                    Fragment fragment0 = new EquivalenceEducationDetailsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.equivalence_fragment_container, fragment0).addToBackStack(null).commit();
                    break;

                case "4":
                    ApplyEquivalenceActivity.stepIndicator.setCurrentStepPosition(3);
                    Fragment fragment1 = new EquivalenceGenerateAppFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.equivalence_fragment_container, fragment1).addToBackStack(null).commit();
                    break;

                case "5":
                    Intent intent = new Intent(this, CallCourier_EQ.class);
                    finish();
                    startActivity(intent);
                    break;
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentById(R.id.equivalence_fragment_container) != null) {
            String fragment_container = getSupportFragmentManager().findFragmentById(R.id.equivalence_fragment_container).getClass().getSimpleName();

            switch (fragment_container) {
                case "EquivalenceMethodFragment":
                    finish();
                    break;

                case "EquivalencePersonalInfoFragment":
                    stepIndicator.setCurrentStepPosition(0);
                    super.onBackPressed();
                    break;

                case "EquivalenceEducationDetailsFragment":
                    stepIndicator.setCurrentStepPosition(1);
                    // super.onBackPressed();
                    finish();
                    break;

                case "EquivalenceDocumentSelectionFragment":
                    stepIndicator.setCurrentStepPosition(2);
                    if (!Global.isIncompleteAppointmentEQ) {
                        super.onBackPressed();
                    } else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.equivalence_fragment_container, new EquivalenceEducationDetailsFragment()).addToBackStack(null).commit();
                    }
                    break;

                case "EquivalenceGenerateAppFragment":
                    stepIndicator.setCurrentStepPosition(3);
                    if (!Global.isIncompleteAppointmentEQ) {
                        super.onBackPressed();
                    } else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.equivalence_fragment_container, new EquivalenceDocumentSelectionFragment()).addToBackStack(null).commit();
                    }
                    break;

                case "PaymentFragment_EQ":
                    stepIndicator.setCurrentStepPosition(4);
                    Global.utils.showErrorSnakeBar(this, "Proceed to Payment to complete your appointment.");
                    break;

                default:
                    super.onBackPressed();
                    break;
            }
        }
    }

    @Override
    public void getRequestResponse(JSONObject response, String requestType) throws JSONException {
        Gson gson = new Gson();

        //done
        if (requestType.equals(Constants.INTIATECASE)) {
            IntiateCase intiateCase = gson.fromJson(response.toString(), IntiateCase.class);

            if (intiateCase.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                Global.equivalenceInitiateCaseResponse = intiateCase;
                Global.caseIdQualificationEQ = intiateCase.getResult().getIntiateCaseResponseDetails().getCaseId().toString();
                Global.utils.hideCustomLoadingDialog();

                ApplyEquivalenceActivity.stepIndicator.setCurrentStepPosition(2);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.equivalence_fragment_container, new EquivalenceEducationDetailsFragment()).addToBackStack(null).commit();

            } else {
                Global.utils.hideCustomLoadingDialog();
                Global.utils.showErrorSnakeBar(this, intiateCase.getResult().getResponseMessage());
            }
        }//step1

        //done
        else if (requestType.equals(Constants.REMOVEQUALIFICATION)) {
            RemoveQualificationEQ removeQualificationEQ = gson.fromJson(response.toString(), RemoveQualificationEQ.class);
            if (removeQualificationEQ.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                Global.removeQualificationEQResponse = removeQualificationEQ;
                Global.utils.hideCustomLoadingDialog();

                Global.addQualificationEQResponse.getResult().getDocumentDetails().remove(Global.deleteEquQualificationPosition);
                Global.equivalenceQualificationList.remove(Global.deleteEquQualificationPosition);
                EquivalenceEducationDetailsFragment.equivalenceEducationDetailsAdapter.notifyDataSetChanged();

                if (Global.equivalenceQualificationList.size() > 0) {
                    EquivalenceEducationDetailsFragment.No_EducationRecord.setVisibility(View.GONE);
                } else {
                    EquivalenceEducationDetailsFragment.No_EducationRecord.setVisibility(View.VISIBLE);
                }

                Global.utils.showSuccessSnakeBar(this, "Deleted Successfully");
            } else {
                Global.utils.hideCustomLoadingDialog();
                Global.utils.showErrorSnakeBar(this, removeQualificationEQ.getResult().getResponseMessage());
            }
        }//delete

        //done
        if (requestType.equals(Constants.SAVEDOCUMENTDETAILS)) {
            SaveDocumentDetailsEQ saveDocumentDetailsEQ = gson.fromJson(response.toString(), SaveDocumentDetailsEQ.class);

            if (saveDocumentDetailsEQ.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                Global.saveDocumentDetailsEQResponse = saveDocumentDetailsEQ;
                Global.utils.hideCustomLoadingDialog();

                ApplyEquivalenceActivity.stepIndicator.setCurrentStepPosition(4);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.equivalence_fragment_container, new EquivalenceGenerateAppFragment()).addToBackStack(null).commit();

            } else {
                Global.utils.hideCustomLoadingDialog();
                Global.utils.showErrorSnakeBar(this, saveDocumentDetailsEQ.getResult().getResponseMessage());
            }
        }//saveDocument

    }
}