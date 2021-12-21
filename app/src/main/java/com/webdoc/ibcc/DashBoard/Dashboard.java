package com.webdoc.ibcc.DashBoard;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.JsonObject;
import com.webdoc.ibcc.DashBoard.Account.AccountFrag;
import com.webdoc.ibcc.DashBoard.Appointment.IncompleteAppointmentFrag;
import com.webdoc.ibcc.DashBoard.DashboardSharedViewModel.DashboardSharedViewModel;
import com.webdoc.ibcc.DashBoard.Faq.FaqsFrag;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.DocumentSelection.DocumentSelectionFragment;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.EducationDetails.EducationDetailsFragment;
import com.webdoc.ibcc.DashBoard.Home.HomeFrag;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.CaseEducationDetailsActivity;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.ReAssignedFragment;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Essentails.Preferences;
import com.webdoc.ibcc.MainActivity;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.GetDetailsEquivalence;
import com.webdoc.ibcc.ResponseModels.PdfResult.PdfResult;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.SplashScreen;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.ActivityDashboardBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity /*implements VolleyListener*/ {
    private VolleyRequestController volleyRequestController;
    public static ConstraintLayout BottomLayout;
    private ActivityDashboardBinding layoutBinding;
    private DashboardSharedViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        viewModel = ViewModelProviders.of(this).get(DashboardSharedViewModel.class);

        //volleyRequestController = new VolleyRequestController(this);
        //statics:
        BottomLayout = layoutBinding.BottomLayout;
        layoutBinding.BottomLayout.setVisibility(View.VISIBLE);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.dashboard_fragment_container, new HomeFrag()).commit();
        }

        //callPDFApi(this);
        viewModel.callPDFApi(this);
        clickListeners();
        observers();

    }

    private void observers() {
        viewModel.getPdfResultLiveData().observe(this, response -> {
            if (response != null) {
                if (response.getMessage().equalsIgnoreCase(Constants.IBCC_SUCCESS_MESSAGE)) {
                    Global.pdfResponse = response;
                    viewModel.callDetailsEquivalenceApi(this);
                } else {
                    Global.utils.showErrorSnakeBar(this, response.getMessage());
                }
            }
        });

        viewModel.getDetailsEquivalenceLiveData().observe(this, response -> {
            if (response != null) {
                if (response.getMessage().equalsIgnoreCase(Constants.IBCC_SUCCESS_MESSAGE)) {
                    Global.getDetailsEquivalence = response;
                } else {
                    Global.utils.showErrorSnakeBar(this, response.getMessage());
                }
            }
        });
    }

    private void clickListeners() {
        layoutBinding.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.dashboard_fragment_container, new HomeFrag()).commit();
            }
        });

        layoutBinding.ivHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.dashboard_fragment_container,
                                new IncompleteAppointmentFrag()).commit();

                //Global.utils.showCustomLoadingDialog(Dashboard.this);
                //volleyRequestController.ViewIncomplete();
            }
        });

        layoutBinding.ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:051 111114222"));
                startActivity(intent);
            }
        });

        layoutBinding.ivFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.dashboard_fragment_container,
                        new ReAssignedFragment()).commit();
            }
        });

        layoutBinding.ivAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.dashboard_fragment_container,
                        new AccountFrag()).commit();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentById(R.id.dashboard_fragment_container) != null) {
            String dashboard_fragment_container = getSupportFragmentManager()
                    .findFragmentById(R.id.dashboard_fragment_container)
                    .getClass().getSimpleName();

            if (dashboard_fragment_container.equals("HomeFrag")) {
                finishAffinity();
            } else if (dashboard_fragment_container.equals("incompleteAppointmentFrag")) {
                super.onBackPressed();
            } else if (dashboard_fragment_container.equals("faqsFrag")) {
                super.onBackPressed();
            } else if (dashboard_fragment_container.equals("accountFrag")) {
                super.onBackPressed();
            } else if (dashboard_fragment_container.equals("EducationDetailsFragment")) {
                getSupportFragmentManager()
                        .beginTransaction().replace(R.id.fragment_container, new IncompleteAppointmentFrag()).commit();
            } else if (dashboard_fragment_container.equals("DocumentSelectionFragment")) {
                getSupportFragmentManager()
                        .beginTransaction().replace(R.id.fragment_container, new EducationDetailsFragment()).commit();
            } else if (dashboard_fragment_container.equals("GenerateAppFragment")) {
                getSupportFragmentManager()
                        .beginTransaction().replace(R.id.fragment_container, new DocumentSelectionFragment()).commit();
            } else if (dashboard_fragment_container.equals("PaymentFragment")) {
                Global.utils.showErrorSnakeBar(this, "Proceed to Payment to complete your appointment.");
            } else {
                getSupportFragmentManager()
                        .beginTransaction().replace(R.id.dashboard_fragment_container, new HomeFrag()).commit();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomLayout.setVisibility(View.VISIBLE);
    }


}