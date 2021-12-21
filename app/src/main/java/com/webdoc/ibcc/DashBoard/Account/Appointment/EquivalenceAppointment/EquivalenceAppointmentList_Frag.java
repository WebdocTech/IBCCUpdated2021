package com.webdoc.ibcc.DashBoard.Account.Appointment.EquivalenceAppointment;

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
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.webdoc.ibcc.Adapter.CompletedAppointmentAdapter;
import com.webdoc.ibcc.Adapter.CompletedApptEQtAdapter;
import com.webdoc.ibcc.DashBoard.Account.Appointment.AppointmentViewModel.AppointmentViewModel;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.ViewAppointmentsEQ.ViewAppointmentsEQ;
import com.webdoc.ibcc.ResponseModels.ViewDetailsResult.ViewDetailsResult;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.FragEquivalenceAppointmentListBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquivalenceAppointmentList_Frag extends Fragment {
    private CompletedApptEQtAdapter completedApptEQtAdapter;
    private FragEquivalenceAppointmentListBinding layoutBinding;
    private AppointmentViewModel viewModel;

    public EquivalenceAppointmentList_Frag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutBinding = FragEquivalenceAppointmentListBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(AppointmentViewModel.class);

        //calling appointment Equivalence api:
        //callViewAppointmentEQApi(getActivity());
        viewModel.callViewAppointmentEQApi(getActivity());

        observers();
        return layoutBinding.getRoot();
    }

    private void observers() {
        viewModel.getViewAppointmentInfoEQ().observe(getActivity(), response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Global.viewAppointmentsEQResponse = response;

                    setAdapter();

                    if (Global.viewAppointmentsEQResponse.getResult().getViewAppointmentsEQD().size() > 0) {
                        layoutBinding.LayoutAppointmentsEQ.setVisibility(View.VISIBLE);
                        layoutBinding.LayoutNoAppointmentsEQ.setVisibility(View.GONE);
                    } else {
                        layoutBinding.LayoutNoAppointmentsEQ.setVisibility(View.VISIBLE);
                        layoutBinding.LayoutAppointmentsEQ.setVisibility(View.GONE);
                    }
                } else {
                    layoutBinding.LayoutNoAppointmentsEQ.setVisibility(View.VISIBLE);
                    layoutBinding.LayoutAppointmentsEQ.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvAppointments.setLayoutManager(layoutManager);
        layoutBinding.rvAppointments.setHasFixedSize(true);
        completedApptEQtAdapter = new CompletedApptEQtAdapter(getActivity());
        layoutBinding.rvAppointments.setAdapter(completedApptEQtAdapter);
    }

    public void callViewAppointmentEQApi(Activity activity) {
        if (Constants.isInternetConnected(activity)) {
            //Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("email", Global.userLoginResponse.getResult().getCustomerProfile().getEmail());
            params.addProperty("cnic", Global.userLoginResponse.getResult().getCustomerProfile().getCnic());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<ViewAppointmentsEQ> call = apiInterface.callViewAppointmentEQ(params);

            call.enqueue(new Callback<ViewAppointmentsEQ>() {
                @Override
                public void onResponse(Call<ViewAppointmentsEQ> call, Response<ViewAppointmentsEQ> response) {
                    //Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            Global.viewAppointmentsEQResponse = response.body();

                            setAdapter();

                            if (Global.viewAppointmentsEQResponse.getResult().getViewAppointmentsEQD().size() > 0) {
                                layoutBinding.LayoutAppointmentsEQ.setVisibility(View.VISIBLE);
                                layoutBinding.LayoutNoAppointmentsEQ.setVisibility(View.GONE);
                            } else {
                                layoutBinding.LayoutNoAppointmentsEQ.setVisibility(View.VISIBLE);
                                layoutBinding.LayoutAppointmentsEQ.setVisibility(View.GONE);
                            }

                        } else {
                            layoutBinding.LayoutNoAppointmentsEQ.setVisibility(View.VISIBLE);
                            layoutBinding.LayoutAppointmentsEQ.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ViewAppointmentsEQ> call, Throwable t) {
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