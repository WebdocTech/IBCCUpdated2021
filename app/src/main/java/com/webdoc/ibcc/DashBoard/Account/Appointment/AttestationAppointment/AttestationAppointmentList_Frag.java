package com.webdoc.ibcc.DashBoard.Account.Appointment.AttestationAppointment;

import android.app.Activity;
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
import com.webdoc.ibcc.Adapter.CompletedAppointmentAdapter;
import com.webdoc.ibcc.DashBoard.Account.Appointment.AppointmentViewModel.AppointmentViewModel;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.ViewAppointmentsEQ.ViewAppointmentsEQ;
import com.webdoc.ibcc.ResponseModels.ViewDetailsResult.ViewDetailsResult;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.FragAttestationAppointmentListBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttestationAppointmentList_Frag extends Fragment {
    public CompletedAppointmentAdapter completedAppointmentAdapter;
    private FragAttestationAppointmentListBinding layoutBinding;
    private AppointmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutBinding = FragAttestationAppointmentListBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(AppointmentViewModel.class);

        //callViewDetailsApi(getActivity());
        viewModel.callViewDetailsApi(getActivity());

        observers();
        return layoutBinding.getRoot();
    }

    private void observers() {
        viewModel.getDetatilsResult().observe(getActivity(), response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Global.viewDetailsResponse = response;

                    //setting adapter:
                    setAdapter();

                    if (Global.viewDetailsResponse.getResult().getDetails().size() > 0) {
                        layoutBinding.AppointmentLayout.setVisibility(View.VISIBLE);
                        layoutBinding.LayoutNoAppointments.setVisibility(View.GONE);
                    } else {
                        layoutBinding.LayoutNoAppointments.setVisibility(View.VISIBLE);
                        layoutBinding.AppointmentLayout.setVisibility(View.GONE);
                    }

                } else {
                    layoutBinding.LayoutNoAppointments.setVisibility(View.VISIBLE);
                    layoutBinding.AppointmentLayout.setVisibility(View.GONE);
                    // Global.utils.showErrorSnakeBar(this, viewDetailsResult.getResult().getResponseMessage());
                }
            }
        });
    }

    private void setAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvCompleted.setLayoutManager(layoutManager);
        layoutBinding.rvCompleted.setHasFixedSize(true);
        completedAppointmentAdapter = new CompletedAppointmentAdapter(getActivity());
        layoutBinding.rvCompleted.setAdapter(completedAppointmentAdapter);
    }

    public void callViewDetailsApi(Activity activity) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("user_id", Global.userLoginResponse.getResult()
                    .getCustomerProfile().getId());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<ViewDetailsResult> call = apiInterface.callViewDetails(params);

            call.enqueue(new Callback<ViewDetailsResult>() {
                @Override
                public void onResponse(Call<ViewDetailsResult> call, Response<ViewDetailsResult> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            Global.viewDetailsResponse = response.body();

                            //setting adapter:
                            setAdapter();

                            if (Global.viewDetailsResponse.getResult().getDetails().size() > 0) {
                                layoutBinding.AppointmentLayout.setVisibility(View.VISIBLE);
                                layoutBinding.LayoutNoAppointments.setVisibility(View.GONE);
                            } else {
                                layoutBinding.LayoutNoAppointments.setVisibility(View.VISIBLE);
                                layoutBinding.AppointmentLayout.setVisibility(View.GONE);
                            }

                        } else {
                            layoutBinding.LayoutNoAppointments.setVisibility(View.VISIBLE);
                            layoutBinding.AppointmentLayout.setVisibility(View.GONE);
                            // Global.utils.showErrorSnakeBar(this, viewDetailsResult.getResult().getResponseMessage());
                        }
                    } else {
                        Toast.makeText(activity, "Something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ViewDetailsResult> call, Throwable t) {
                    //Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

}