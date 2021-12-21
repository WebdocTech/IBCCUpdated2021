package com.webdoc.ibcc.DashBoard.reAssignedCasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.webdoc.ItemClickListeners;
import com.webdoc.ibcc.Adapter.EquivalenceEducationDetailsAdapter;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.ApplyEquivalenceActivity;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.DocumentSelection.EquivalenceDocumentSelectionFragment;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.AddQualification.AddQualification;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.EquivalenceEducationDetailsFragment;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.Instructions.Instructions;
import com.webdoc.ibcc.DashBoard.Home.HomeSharedViewModel.HomeSharedViewModel;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.adapter.CaseEducationDetailsAdapter;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.ReassignedCaseDetailsModels.Document;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.ReassignedCaseDetailsModels.ReassignedCaseDetailsModel;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.RemoveQualificationEQ.RemoveQualificationEQ;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.ActivityCaseEducationDetailsBinding;
import com.webdoc.ibcc.databinding.FragmentEquivalenceEducationDetailsBinding;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaseEducationDetailsActivity extends AppCompatActivity implements ItemClickListeners {
    public CaseEducationDetailsAdapter caseEducationDetailsAdapter;
    public static ConstraintLayout No_EducationRecord;
    private ActivityCaseEducationDetailsBinding layoutBinding;
    private HomeSharedViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityCaseEducationDetailsBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        viewModel = ViewModelProviders.of(this).get(HomeSharedViewModel.class);

        //statics:
        No_EducationRecord = layoutBinding.NoEducationRecord;

        Intent intent = getIntent();
        String caseID = intent.getStringExtra("mCaseID");
        callReAssignedCaseDetailsApi(this, caseID);

        observers();
        clickListeners();
    }

    public void callReAssignedCaseDetailsApi(Activity activity, String caseID) {
        No_EducationRecord.setVisibility(View.VISIBLE);
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

                            ArrayList<Document> arrayList = new ArrayList<>();
                            arrayList = response.body().getResult().getDocument();
                            setAdapter(arrayList);

                            No_EducationRecord.setVisibility(View.GONE);

                        } else {
                            Global.utils.showErrorSnakeBar(CaseEducationDetailsActivity.this,
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
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void observers() {
        viewModel.getGetRemoveQualificationEQ().observe(this, response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Global.removeQualificationEQResponse = response;

                    Global.addQualificationEQResponse.getResult().getDocumentDetails().remove(Global.deleteEquQualificationPosition);
                    Global.equivalenceQualificationList.remove(Global.deleteEquQualificationPosition);
                    caseEducationDetailsAdapter.notifyDataSetChanged();

                    if (Global.equivalenceQualificationList.size() > 0) {
                        No_EducationRecord.setVisibility(View.GONE);
                    } else {
                        No_EducationRecord.setVisibility(View.VISIBLE);
                    }

                    Global.utils.showSuccessSnakeBar(this, "Deleted Successfully");
                } else {
                    Global.utils.showErrorSnakeBar(this, response.getResult().getResponseMessage());
                }
            }
        });
    }

    private void clickListeners() {
        layoutBinding.btnAddQualification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CaseEducationDetailsActivity.this, AddQualification.class);
                startActivity(intent);
            }
        });

        layoutBinding.btnInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CaseEducationDetailsActivity.this, Instructions.class);
                startActivity(intent);
            }
        });

        layoutBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.equivalenceQualificationList.size() > 0) {
                    ApplyEquivalenceActivity.stepIndicator.setCurrentStepPosition(3);

                    CaseEducationDetailsActivity.this.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.equivalence_fragment_container, new EquivalenceDocumentSelectionFragment()).addToBackStack(null).commit();

                } else {
                    Global.utils.showErrorSnakeBar(CaseEducationDetailsActivity.this, "Please add qualification to proceed");
                }

            }
        });

    }

    private void setAdapter(ArrayList<Document> arrayList) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(CaseEducationDetailsActivity.this,
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvEquivalenceEducationDetails.setLayoutManager(layoutManager);
        layoutBinding.rvEquivalenceEducationDetails.setHasFixedSize(true);
        caseEducationDetailsAdapter = new CaseEducationDetailsAdapter(CaseEducationDetailsActivity.this, arrayList);
        layoutBinding.rvEquivalenceEducationDetails.setAdapter(caseEducationDetailsAdapter);
        caseEducationDetailsAdapter.setItemClickListeners(this);
    }

    @Override
    public void onClick(View view, int pos, String str) {
        if (str.equals("mRemove")) {
            new SweetAlertDialog(CaseEducationDetailsActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setContentText("Do you want to remove this record?")
                    .setConfirmText("Yes")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            Global.deleteEquQualificationPosition = pos;
                            sDialog.dismissWithAnimation();

                            /*callRemoveQualificationApi(getActivity(),
                                    Global.deleteParams.get(pos).getDocId(),
                                    Global.deleteParams.get(pos).getCaseId());*/

                            viewModel.callRemoveQualificationApi(CaseEducationDetailsActivity.this,
                                    Global.deleteParams.get(pos).getDocId(),
                                    Global.deleteParams.get(pos).getCaseId());

                            if (Global.equivalenceQualificationList.size() > 0) {
                                EquivalenceEducationDetailsFragment.No_EducationRecord.setVisibility(View.GONE);
                            } else {
                                EquivalenceEducationDetailsFragment.No_EducationRecord.setVisibility(View.VISIBLE);
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
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            Global.removeQualificationEQResponse = response.body();

                            Global.addQualificationEQResponse.getResult().getDocumentDetails().remove(Global.deleteEquQualificationPosition);
                            Global.equivalenceQualificationList.remove(Global.deleteEquQualificationPosition);
                            caseEducationDetailsAdapter.notifyDataSetChanged();

                            if (Global.equivalenceQualificationList.size() > 0) {
                                No_EducationRecord.setVisibility(View.GONE);
                            } else {
                                No_EducationRecord.setVisibility(View.VISIBLE);
                            }

                            Global.utils.showSuccessSnakeBar(CaseEducationDetailsActivity.this, "Deleted Successfully");
                        } else {
                            Global.utils.showErrorSnakeBar(CaseEducationDetailsActivity.this, response.body().getResult().getResponseMessage());
                        }
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RemoveQualificationEQ> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Oopps something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }
}