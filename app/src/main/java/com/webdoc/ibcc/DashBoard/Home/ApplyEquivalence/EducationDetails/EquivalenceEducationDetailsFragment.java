package com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.webdoc.ItemClickListeners;
import com.webdoc.ibcc.Adapter.EquivalenceEducationDetailsAdapter;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.ApplyEquivalenceActivity;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.DocumentSelection.DocDetailEQ_Model;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.DocumentSelection.EquivalenceDocumentSelectionFragment;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.AddQualification.AddQualification;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.Instructions.Instructions;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.GenerateApp.EquivalenceGenerateAppFragment;
import com.webdoc.ibcc.DashBoard.Home.HomeSharedViewModel.HomeSharedViewModel;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.RemoveQualificationEQ.RemoveQualificationEQ;
import com.webdoc.ibcc.ResponseModels.SaveDocumentDetailsEQ.SaveDocumentDetailsEQ;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.FragmentEquivalenceEducationDetailsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquivalenceEducationDetailsFragment extends Fragment implements ItemClickListeners {
    public static EquivalenceEducationDetailsAdapter equivalenceEducationDetailsAdapter;
    public static ConstraintLayout No_EducationRecord;
    private FragmentEquivalenceEducationDetailsBinding layoutBinding;
    private HomeSharedViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutBinding = FragmentEquivalenceEducationDetailsBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(HomeSharedViewModel.class);

        //statics:
        No_EducationRecord = layoutBinding.NoEducationRecord;

        setAdapter();
        observers();
        clickListeners();

        return layoutBinding.getRoot();
    }

    private void observers() {
        viewModel.getGetRemoveQualificationEQ().observe(getActivity(), response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Global.removeQualificationEQResponse = response;

                    Global.addQualificationEQResponse.getResult().getDocumentDetails().remove(Global.deleteEquQualificationPosition);
                    Global.equivalenceQualificationList.remove(Global.deleteEquQualificationPosition);
                    equivalenceEducationDetailsAdapter.notifyDataSetChanged();

                    if (Global.equivalenceQualificationList.size() > 0) {
                        No_EducationRecord.setVisibility(View.GONE);
                    } else {
                        No_EducationRecord.setVisibility(View.VISIBLE);
                    }

                    Global.utils.showSuccessSnakeBar(getActivity(), "Deleted Successfully");
                } else {
                    Global.utils.showErrorSnakeBar(getActivity(), response.getResult().getResponseMessage());
                }
            }
        });
    }

    private void clickListeners() {
        layoutBinding.btnAddQualification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddQualification.class);
                startActivity(intent);
            }
        });

        layoutBinding.btnInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Instructions.class);
                startActivity(intent);
            }
        });

        layoutBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.equivalenceQualificationList.size() > 0) {
                    ApplyEquivalenceActivity.stepIndicator.setCurrentStepPosition(3);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.equivalence_fragment_container,
                                    new EquivalenceDocumentSelectionFragment())
                            .addToBackStack(null).commit();

                } else {
                    Global.utils.showErrorSnakeBar(getActivity(), "Please add qualification to proceed");
                }
            }
        });
    }

    private void setAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvEquivalenceEducationDetails.setLayoutManager(layoutManager);
        layoutBinding.rvEquivalenceEducationDetails.setHasFixedSize(true);
        equivalenceEducationDetailsAdapter = new EquivalenceEducationDetailsAdapter(getActivity());
        layoutBinding.rvEquivalenceEducationDetails.setAdapter(equivalenceEducationDetailsAdapter);
        equivalenceEducationDetailsAdapter.setItemClickListeners(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Global.equivalenceQualificationList.size() > 0) {
            No_EducationRecord.setVisibility(View.GONE);
        } else {
            No_EducationRecord.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view, int pos, String str) {
        if (str.equals("mRemove")) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
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

                            viewModel.callRemoveQualificationApi(getActivity(),
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
                            equivalenceEducationDetailsAdapter.notifyDataSetChanged();

                            if (Global.equivalenceQualificationList.size() > 0) {
                                No_EducationRecord.setVisibility(View.GONE);
                            } else {
                                No_EducationRecord.setVisibility(View.VISIBLE);
                            }

                            Global.utils.showSuccessSnakeBar(getActivity(), "Deleted Successfully");
                        } else {
                            Global.utils.showErrorSnakeBar(getActivity(), response.body().getResult().getResponseMessage());
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