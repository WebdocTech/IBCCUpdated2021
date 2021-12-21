package com.webdoc.ibcc.DashBoard.Account.Appointment.AttestationAppointment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.webdoc.ItemClickListeners;
import com.webdoc.ibcc.Adapter.CompletedAppointmentAdapter;
import com.webdoc.ibcc.DashBoard.Account.Appointment.AppointmentViewModel.AppointmentViewModel;
import com.webdoc.ibcc.DashBoard.Appointment.IncompleteAppt_Attest;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.CancelAppointmentResult.CancelAppointmentResult;
import com.webdoc.ibcc.ResponseModels.ViewDetailsResult.ViewDetailsResult;
import com.webdoc.ibcc.ServerManager.VolleyListener;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.ActivityCompletedAppointmentBinding;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompletedAppointment extends AppCompatActivity implements ItemClickListeners {
    private CompletedAppointmentAdapter completedAppointmentAdapter;
    private ActivityCompletedAppointmentBinding layoutBinding;
    private AppointmentViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityCompletedAppointmentBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        viewModel = ViewModelProviders.of(this).get(AppointmentViewModel.class);

        //callViewDetailsApi(this);
        viewModel.callViewDetailsApi(this);

        observers();
        clickListeners();

    }

    private void observers() {
        viewModel.getDetatilsResult().observe(this, response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Global.viewDetailsResponse = response;

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
                    Global.utils.showErrorSnakeBar(CompletedAppointment.this, response.getResult().getResponseMessage());
                }
            }
        });

        viewModel.getCancelAppointment().observe(this, response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Global.cancelAppointmentResponse = response;

                    Global.userLoginResponse.getResult().getCustomerProfile().setIsAppointmentAvailabe(true);
                    Global.viewDetailsResponse.getResult().getDetails().remove(Global.cancelAppointmentPosition);

                    if (Global.viewDetailsResponse.getResult().getDetails().size() > 0) {
                        layoutBinding.AppointmentLayout.setVisibility(View.VISIBLE);
                        layoutBinding.LayoutNoAppointments.setVisibility(View.GONE);
                    } else {
                        layoutBinding.LayoutNoAppointments.setVisibility(View.VISIBLE);
                        layoutBinding.AppointmentLayout.setVisibility(View.GONE);
                    }

                    Toast.makeText(CompletedAppointment.this, response.getResult().getResponseMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    layoutBinding.LayoutNoAppointments.setVisibility(View.VISIBLE);
                    layoutBinding.AppointmentLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void clickListeners() {
        layoutBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvCompleted.setLayoutManager(layoutManager);
        layoutBinding.rvCompleted.setHasFixedSize(true);
        completedAppointmentAdapter = new CompletedAppointmentAdapter(CompletedAppointment.this);
        layoutBinding.rvCompleted.setAdapter(completedAppointmentAdapter);
        completedAppointmentAdapter.setItemClickListeners(this);
    }

    public void callViewDetailsApi(Activity activity) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("user_id", Global.userLoginResponse.getResult().getCustomerProfile().getId());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<ViewDetailsResult> call = apiInterface.callViewDetails(params);

            call.enqueue(new Callback<ViewDetailsResult>() {
                @Override
                public void onResponse(Call<ViewDetailsResult> call, Response<ViewDetailsResult> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            Global.viewDetailsResponse = response.body();

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
                            Global.utils.showErrorSnakeBar(CompletedAppointment.this, response.body().getResult().getResponseMessage());
                        }
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ViewDetailsResult> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callCancelAppointmentApi(Activity activity, String caseId) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("case_id", caseId);
            params.addProperty("user_id", Global.userLoginResponse.getResult().getCustomerProfile().getId());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<CancelAppointmentResult> call = apiInterface.callCancelAppointment(params);

            call.enqueue(new Callback<CancelAppointmentResult>() {
                @Override
                public void onResponse(Call<CancelAppointmentResult> call, Response<CancelAppointmentResult> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            Global.cancelAppointmentResponse = response.body();

                            Global.userLoginResponse.getResult().getCustomerProfile().setIsAppointmentAvailabe(true);
                            Global.viewDetailsResponse.getResult().getDetails().remove(Global.cancelAppointmentPosition);

                            if (Global.viewDetailsResponse.getResult().getDetails().size() > 0) {
                                layoutBinding.AppointmentLayout.setVisibility(View.VISIBLE);
                                layoutBinding.LayoutNoAppointments.setVisibility(View.GONE);
                            } else {
                                layoutBinding.LayoutNoAppointments.setVisibility(View.VISIBLE);
                                layoutBinding.AppointmentLayout.setVisibility(View.GONE);
                            }

                            Toast.makeText(CompletedAppointment.this, response.body().getResult().getResponseMessage(), Toast.LENGTH_SHORT).show();

                        } else {
                            layoutBinding.LayoutNoAppointments.setVisibility(View.VISIBLE);
                            layoutBinding.AppointmentLayout.setVisibility(View.GONE);
                            //Global.utils.showErrorSnakeBar(this, cancelAppointmentResult.getResult().getResponseMessage());
                        }
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CancelAppointmentResult> call, Throwable t) {
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
        Global.cancelAppointmentPosition = pos;
        String caseID = Global.viewDetailsResponse.getResult().getDetails().get(pos).getCaseId();

        if (str.equals("mCancel")) {
            //callCancelAppointmentApi(this, caseID);
            viewModel.callCancelAppointmentApi(this, caseID);
        }
    }
}