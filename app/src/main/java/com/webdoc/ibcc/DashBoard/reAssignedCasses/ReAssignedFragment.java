package com.webdoc.ibcc.DashBoard.reAssignedCasses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.webdoc.ItemClickListeners;
import com.webdoc.ibcc.Adapter.AppointmentStatusAdapter;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.EducationDetails.EducationDetailsFragment;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.adapter.ReassignCassesAdapter;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.ReassignedCaseDetail;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.ReassignedCaseModel;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.Step1Result.Step1Result;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.FragmentReAssignedBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReAssignedFragment extends Fragment implements ItemClickListeners {
    private FragmentReAssignedBinding layoutBinding;
    private ReassignCassesAdapter reassignCassesAdapter;
    private ArrayList<ReassignedCaseDetail> arrayList = new ArrayList<>();

    public ReAssignedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layoutBinding = FragmentReAssignedBinding.inflate(getLayoutInflater(), container, false);

        callReAssignedCassesApi(getActivity(),
                Global.equivalenceInitiateCase.getEmail(),
                Global.equivalenceInitiateCase.getCnic());

        /*Intent intent = new Intent(getActivity(), CaseEducationDetailsActivity.class);
        intent.putExtra("mCaseID", "12978");
        startActivity(intent);*/

        return layoutBinding.getRoot();
    }

    private void setAdapter(ArrayList<ReassignedCaseDetail> arrayList) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvCasses.setLayoutManager(layoutManager);
        layoutBinding.rvCasses.setHasFixedSize(true);
        reassignCassesAdapter = new ReassignCassesAdapter(getContext(), arrayList);
        layoutBinding.rvCasses.setAdapter(reassignCassesAdapter);
        reassignCassesAdapter.setItemClickListeners(this);
    }

    public void callReAssignedCassesApi(Activity activity, String mEmail, String mCnic) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("email", mEmail);
            params.addProperty("cnic", mCnic);

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<ReassignedCaseModel> call = apiInterface.callReassignedCassesApi(params);

            call.enqueue(new Callback<ReassignedCaseModel>() {
                @Override
                public void onResponse(Call<ReassignedCaseModel> call, Response<ReassignedCaseModel> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            layoutBinding.llNoCaseFound.setVisibility(View.GONE);
                            arrayList = response.body().getResult().getReassignedCaseDetails();
                            setAdapter(arrayList);
                        } else {
                            layoutBinding.llNoCaseFound.setVisibility(View.VISIBLE);
                            //Global.utils.showErrorSnakeBar(getActivity(), response.body().getResult().getResponseMessage());
                        }
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ReassignedCaseModel> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
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
        Intent intent = new Intent(getActivity(), CaseEducationDetailsActivity.class);
        intent.putExtra("mCaseID", arrayList.get(pos).getCaseId());
        startActivity(intent);
    }
}