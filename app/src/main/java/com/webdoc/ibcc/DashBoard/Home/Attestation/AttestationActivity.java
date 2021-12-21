package com.webdoc.ibcc.DashBoard.Home.Attestation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.webdoc.ibcc.DashBoard.DashboardSharedViewModel.DashboardSharedViewModel;
import com.webdoc.ibcc.DashBoard.Home.Verification.PdfView;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.PdfResult.Pdf;
import com.webdoc.ibcc.ResponseModels.PdfResult.PdfResult;
import com.webdoc.ibcc.ServerManager.VolleyListener;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.SplashScreen;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.ActivityAttestationBinding;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttestationActivity extends AppCompatActivity /*implements VolleyListener */ {
    VolleyRequestController volleyRequestController;
    String form;
    public final String RULES_OF_ATTESTATION = "Rules of Attestation";
    public final String REQUIREMENT_OF_ATTESTATION = "Requirement of Attestation";
    public final String ATTESTATION_FORM = "Attestation Form";
    private ActivityAttestationBinding layoutBinding;
    private DashboardSharedViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityAttestationBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        viewModel = ViewModelProviders.of(this).get(DashboardSharedViewModel.class);

        observers();
        clickListeners();
    }

    private void observers() {
        viewModel.getPdfResultLiveData().observe(this, response -> {
            if (response != null) {
                if (response.getMessage().equalsIgnoreCase(Constants.IBCC_SUCCESS_MESSAGE)) {
                    Global.pdfResponse = response;

                    for (int i = 0; i < Global.pdfResponse.getResult().getPdf().size(); i++) {
                        final Pdf item = Global.pdfResponse.getResult().getPdf().get(i);
                        final String title = item.getName();
                        final String date = item.getDate();
                        final String pdf = item.getPdfUrl();

                        switch (form) {
                            case RULES_OF_ATTESTATION:
                                if (title.equalsIgnoreCase("Rules of Attestation")) {
                                    Intent intent = new Intent(AttestationActivity.this, PdfView.class);
                                    intent.putExtra("pdfUrl", pdf);
                                    intent.putExtra("pdfName", title);
                                    startActivity(intent);
                                }
                                break;
                            case REQUIREMENT_OF_ATTESTATION:
                                if (title.equalsIgnoreCase("Requirement of Attestation")) {
                                    Intent intent = new Intent(AttestationActivity.this, PdfView.class);
                                    intent.putExtra("pdfUrl", pdf);
                                    intent.putExtra("pdfName", title);
                                    startActivity(intent);
                                }
                                break;
                            case ATTESTATION_FORM:
                                if (title.equalsIgnoreCase("Attestation Form")) {
                                    Intent intent = new Intent(AttestationActivity.this, PdfView.class);
                                    intent.putExtra("pdfUrl", pdf);
                                    intent.putExtra("pdfName", title);
                                    startActivity(intent);

                               /* String pdf_url = pdf;
                                 String pdf_url = "https://portal.webdoc.com.pk/Documents/Attestation/Attestation_Form.pdf";
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(Uri.parse("http://docs.google.com/viewer?url=" + pdf_url), "text/html");
                                startActivity(intent);*/
                                }
                                break;
                        }
                    }
                } else {
                    Global.utils.hideCustomLoadingDialog();
                    Toast.makeText(AttestationActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void clickListeners() {
        layoutBinding.tvRulesForAttestation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                form = RULES_OF_ATTESTATION;
                //Global.utils.showCustomLoadingDialog(AttestationActivity.this);
                //volleyRequestController.Pdf();
                //callPDFApi(AttestationActivity.this);
                viewModel.callPDFApi(AttestationActivity.this);
            }
        });

        layoutBinding.tvRequirementsForAttestation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                form = REQUIREMENT_OF_ATTESTATION;
                //Global.utils.showCustomLoadingDialog(AttestationActivity.this);
                //volleyRequestController.Pdf();
                //callPDFApi(AttestationActivity.this);
                viewModel.callPDFApi(AttestationActivity.this);
            }
        });

        layoutBinding.tvAttestationForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                form = ATTESTATION_FORM;
                //Global.utils.showCustomLoadingDialog(AttestationActivity.this);
                //volleyRequestController.Pdf();
                //callPDFApi(AttestationActivity.this);
                viewModel.callPDFApi(AttestationActivity.this);
            }
        });
    }

    /*@Override
    public void getRequestResponse(JSONObject response, String requestType) throws JSONException {
        Gson gson = new Gson();

        if (requestType.equals(Constants.PDF)) {
            PdfResult pdfResult = gson.fromJson(response.toString(), PdfResult.class);

            if (pdfResult.getMessage().equalsIgnoreCase(Constants.IBCC_SUCCESS_MESSAGE)) {
                Global.pdfResponse = pdfResult;
                Global.utils.hideCustomLoadingDialog();

                for (int i = 0; i < Global.pdfResponse.getResult().getPdf().size(); i++) {
                    final Pdf item = Global.pdfResponse.getResult().getPdf().get(i);
                    final String title = item.getName();
                    final String date = item.getDate();
                    final String pdf = item.getPdfUrl();

                    switch (form) {
                        case RULES_OF_ATTESTATION:
                            if (title.equalsIgnoreCase("Rules of Attestation")) {
                                Intent intent = new Intent(this, PdfView.class);
                                intent.putExtra("pdfUrl", pdf);
                                intent.putExtra("pdfName", title);
                                startActivity(intent);
                            }
                            break;
                        case REQUIREMENT_OF_ATTESTATION:
                            if (title.equalsIgnoreCase("Requirement of Attestation")) {
                                Intent intent = new Intent(this, PdfView.class);
                                intent.putExtra("pdfUrl", pdf);
                                intent.putExtra("pdfName", title);
                                startActivity(intent);
                            }
                            break;
                        case ATTESTATION_FORM:
                            if (title.equalsIgnoreCase("Attestation Form")) {
                                Intent intent = new Intent(this, PdfView.class);
                                intent.putExtra("pdfUrl", pdf);
                                intent.putExtra("pdfName", title);
                                startActivity(intent);

                               *//* String pdf_url = pdf;
                                 String pdf_url = "https://portal.webdoc.com.pk/Documents/Attestation/Attestation_Form.pdf";
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(Uri.parse("http://docs.google.com/viewer?url=" + pdf_url), "text/html");
                                startActivity(intent);*//*
                            }
                            break;
                    }
                }//for
            } else {
                Global.utils.hideCustomLoadingDialog();
                Toast.makeText(this, pdfResult.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }*/

    public void callPDFApi(Activity activity) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<PdfResult> call = apiInterface.callPDF();

            call.enqueue(new Callback<PdfResult>() {
                @Override
                public void onResponse(Call<PdfResult> call, Response<PdfResult> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getMessage().equalsIgnoreCase(Constants.IBCC_SUCCESS_MESSAGE)) {
                            Global.pdfResponse = response.body();
                            Global.utils.hideCustomLoadingDialog();

                            for (int i = 0; i < Global.pdfResponse.getResult().getPdf().size(); i++) {
                                final Pdf item = Global.pdfResponse.getResult().getPdf().get(i);
                                final String title = item.getName();
                                final String date = item.getDate();
                                final String pdf = item.getPdfUrl();

                                switch (form) {
                                    case RULES_OF_ATTESTATION:
                                        if (title.equalsIgnoreCase("Rules of Attestation")) {
                                            Intent intent = new Intent(AttestationActivity.this, PdfView.class);
                                            intent.putExtra("pdfUrl", pdf);
                                            intent.putExtra("pdfName", title);
                                            startActivity(intent);
                                        }
                                        break;
                                    case REQUIREMENT_OF_ATTESTATION:
                                        if (title.equalsIgnoreCase("Requirement of Attestation")) {
                                            Intent intent = new Intent(AttestationActivity.this, PdfView.class);
                                            intent.putExtra("pdfUrl", pdf);
                                            intent.putExtra("pdfName", title);
                                            startActivity(intent);
                                        }
                                        break;
                                    case ATTESTATION_FORM:
                                        if (title.equalsIgnoreCase("Attestation Form")) {
                                            Intent intent = new Intent(AttestationActivity.this, PdfView.class);
                                            intent.putExtra("pdfUrl", pdf);
                                            intent.putExtra("pdfName", title);
                                            startActivity(intent);

                               /* String pdf_url = pdf;
                                 String pdf_url = "https://portal.webdoc.com.pk/Documents/Attestation/Attestation_Form.pdf";
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(Uri.parse("http://docs.google.com/viewer?url=" + pdf_url), "text/html");
                                startActivity(intent);*/
                                        }
                                        break;
                                }
                            }//for
                        } else {
                            Toast.makeText(AttestationActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PdfResult> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

}