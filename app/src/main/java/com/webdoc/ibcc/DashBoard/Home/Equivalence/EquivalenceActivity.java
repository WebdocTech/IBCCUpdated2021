package com.webdoc.ibcc.DashBoard.Home.Equivalence;

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
import com.webdoc.ibcc.DashBoard.Home.Attestation.AttestationActivity;
import com.webdoc.ibcc.DashBoard.Home.Verification.PdfView;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.PdfResult.Pdf;
import com.webdoc.ibcc.ResponseModels.PdfResult.PdfResult;
import com.webdoc.ibcc.ServerManager.VolleyListener;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.ActivityEquivalenceBinding;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquivalenceActivity extends AppCompatActivity /*implements VolleyListener*/ {
    VolleyRequestController volleyRequestController;
    String form;
    public final String METHODS_FOR_EQUIVALENCE = "Methods for Equivalence";
    public final String CONVERSION_OF_EQUIVALENCE = "Conversion of Equivalence";
    public final String EQUIVALENCE_FORM = "equivalence Form";
    public final String RULES_OF_EQUIVALENCE = "Rules of Equivalence";
    private ActivityEquivalenceBinding layoutBinding;
    private DashboardSharedViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityEquivalenceBinding.inflate(getLayoutInflater());
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
                    Global.utils.hideCustomLoadingDialog();

                    for (int i = 0; i < Global.pdfResponse.getResult().getPdf().size(); i++) {
                        final Pdf item = Global.pdfResponse.getResult().getPdf().get(i);
                        final String title = item.getName();
                        final String date = item.getDate();
                        final String pdf = item.getPdfUrl();

                        switch (form) {
                            case RULES_OF_EQUIVALENCE:
                                if (title.equalsIgnoreCase("Rules of Equivalence")) {
                                    // Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(EquivalenceActivity.this, PdfView.class);
                                    intent.putExtra("pdfUrl", pdf);
                                    intent.putExtra("pdfName", title);
                                    startActivity(intent);
                                }
                                break;
                            case CONVERSION_OF_EQUIVALENCE:
                                if (title.equalsIgnoreCase("Conversion of Equivalence")) {
                                    Intent intent = new Intent(EquivalenceActivity.this, PdfView.class);
                                    intent.putExtra("pdfUrl", pdf);
                                    intent.putExtra("pdfName", title);
                                    startActivity(intent);
                                }
                                break;
                            case METHODS_FOR_EQUIVALENCE:
                                if (title.equalsIgnoreCase("Methods for Equivalence")) {
                                    Intent intent = new Intent(EquivalenceActivity.this, PdfView.class);
                                    intent.putExtra("pdfUrl", pdf);
                                    intent.putExtra("pdfName", title);
                                    startActivity(intent);
                                }
                                break;
                            case EQUIVALENCE_FORM:
                                if (title.equalsIgnoreCase("equivalence Form - 2020")) {
                                    Intent intent = new Intent(EquivalenceActivity.this, PdfView.class);
                                    intent.putExtra("pdfUrl", pdf);
                                    intent.putExtra("pdfName", title);
                                    startActivity(intent);
                                }
                                break;
                        }
                    }
                } else {
                    Global.utils.hideCustomLoadingDialog();
                    Toast.makeText(EquivalenceActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void clickListeners() {
        layoutBinding.tvRulesForVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                form = RULES_OF_EQUIVALENCE;
                //callPDFApi(EquivalenceActivity.this);
                viewModel.getPdfResultLiveData();
            }
        });

        layoutBinding.tvConversionForVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                form = CONVERSION_OF_EQUIVALENCE;
                //callPDFApi(EquivalenceActivity.this);
                viewModel.getPdfResultLiveData();
            }
        });

        layoutBinding.tvMethodsForEquivalence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                form = METHODS_FOR_EQUIVALENCE;
                //callPDFApi(EquivalenceActivity.this);
                viewModel.getPdfResultLiveData();
            }
        });

        layoutBinding.tvEquivalenceForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                form = EQUIVALENCE_FORM;
                //callPDFApi(EquivalenceActivity.this);
                viewModel.getPdfResultLiveData();
            }
        });
    }

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
                                    case RULES_OF_EQUIVALENCE:
                                        if (title.equalsIgnoreCase("Rules of Equivalence")) {
                                            // Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(EquivalenceActivity.this, PdfView.class);
                                            intent.putExtra("pdfUrl", pdf);
                                            intent.putExtra("pdfName", title);
                                            startActivity(intent);
                                        }
                                        break;
                                    case CONVERSION_OF_EQUIVALENCE:
                                        if (title.equalsIgnoreCase("Conversion of Equivalence")) {
                                            Intent intent = new Intent(EquivalenceActivity.this, PdfView.class);
                                            intent.putExtra("pdfUrl", pdf);
                                            intent.putExtra("pdfName", title);
                                            startActivity(intent);
                                        }
                                        break;
                                    case METHODS_FOR_EQUIVALENCE:
                                        if (title.equalsIgnoreCase("Methods for Equivalence")) {
                                            Intent intent = new Intent(EquivalenceActivity.this, PdfView.class);
                                            intent.putExtra("pdfUrl", pdf);
                                            intent.putExtra("pdfName", title);
                                            startActivity(intent);
                                        }
                                        break;
                                    case EQUIVALENCE_FORM:
                                        if (title.equalsIgnoreCase("equivalence Form - 2020")) {
                                            Intent intent = new Intent(EquivalenceActivity.this, PdfView.class);
                                            intent.putExtra("pdfUrl", pdf);
                                            intent.putExtra("pdfName", title);
                                            startActivity(intent);
                                        }
                                        break;
                                }
                            }
                        } else {
                            Global.utils.hideCustomLoadingDialog();
                            Toast.makeText(EquivalenceActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
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