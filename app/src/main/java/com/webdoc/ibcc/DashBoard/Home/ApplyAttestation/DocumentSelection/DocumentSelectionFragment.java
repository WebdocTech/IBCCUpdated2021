package com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.DocumentSelection;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.webdoc.ibcc.Adapter.DocumentDetailsAdapter;
import com.webdoc.ibcc.Adapter.DocumentSelectionAdapter;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.AttestationApplyActivity;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.AttestationReceipt;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.CallCourier.CallCourier;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.GenerateApp.GenerateAppFragment;
import com.webdoc.ibcc.DashBoard.Home.HomeSharedViewModel.HomeSharedViewModel;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.DocumentDetailModel;
import com.webdoc.ibcc.Model.DocumentSelectionModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.AddDocumentDetailsResult.AddDocumentDetailsResult;
import com.webdoc.ibcc.ResponseModels.SaveCourierDetials.SaveCourierDetials;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.FragmentDocumentSelectionBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentSelectionFragment extends Fragment {
    private DocumentSelectionAdapter documentSelectionAdapter;
    private DocumentDetailsAdapter documentDetailsAdapter;
    private FragmentDocumentSelectionBinding layoutBinding;
    private HomeSharedViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutBinding = FragmentDocumentSelectionBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(HomeSharedViewModel.class);

        layoutBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttestationApplyActivity.stepIndicator.setCurrentStepPosition(3);

                //callAddDocumentDetailsApi(getActivity(), Global.selDocument);
                viewModel.callAddDocumentDetailsApi(getActivity(), Global.selDocument);
            }
        });

        //RECYCLER VIEW
        setSelectionAdapter();
        setDetailsAdapter();

        if (Global.selDocument != null) {
            if (Global.selDocument.get(Global.selectedDoc).getDetailModelList().size() > 0) {
                documentDetailsAdapter = new DocumentDetailsAdapter(getActivity());
                layoutBinding.rvChildDocumentSelection.setAdapter(documentDetailsAdapter);
            }
        }


        if (Global.isIncompleteAppointment) {
            documentSelectionAdapter.notifyDataSetChanged();
            documentDetailsAdapter.notifyDataSetChanged();
        }

        observers();
        return layoutBinding.getRoot();
    }

    private void observers() {
        viewModel.getAddDocumentDetails().observe(this, response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Global.addDocumentDetailsResponse = response;
                    Global.enableAddDocument = true;
                    //set Fragment:
                    Fragment educationDetailsFragment = new GenerateAppFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, educationDetailsFragment).addToBackStack(null).commit();

                } else {
                    Global.utils.showErrorSnakeBar(getActivity(), "Please add document details properly.");
                }
            }
        });
    }

    private void setSelectionAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        layoutBinding.rvDocumentSelection.setLayoutManager(layoutManager);
        layoutBinding.rvDocumentSelection.setHasFixedSize(true);
        documentSelectionAdapter = new DocumentSelectionAdapter(getActivity());
        layoutBinding.rvDocumentSelection.setAdapter(documentSelectionAdapter);
    }

    private void setDetailsAdapter() {
        final LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvChildDocumentSelection.setLayoutManager(layoutManager1);
        layoutBinding.rvChildDocumentSelection.setHasFixedSize(true);
        layoutManager1.setAutoMeasureEnabled(true);
        documentDetailsAdapter = new DocumentDetailsAdapter(getActivity());
        layoutBinding.rvChildDocumentSelection.setAdapter(documentDetailsAdapter);
    }

    public void callAddDocumentDetailsApi(Activity activity, List<DocumentSelectionModel> documentSelectionModelList) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JSONObject params = new JSONObject();
            try {
                JSONArray documentArray = new JSONArray();
                for (int i = 0; i < documentSelectionModelList.size(); i++) {
                    JSONObject docItem = new JSONObject();

                    //docItem.put("document_id", documentSelectionModelList.get(i).getDocId());
                    docItem.put("document_id", documentSelectionModelList.get(i).getDocId());
                    docItem.put("case_id", documentSelectionModelList.get(i).getCaseId());
                    docItem.put("totalAmount", documentSelectionModelList.get(i).getTotalAmount());   //all document total amount

                    JSONArray document_detailsArray = new JSONArray();
                    for (DocumentDetailModel item : documentSelectionModelList.get(i).getDetailModelList()) {
                        JSONObject docDetailItem = new JSONObject();
                        docDetailItem.put("certificate_type", item.getCertificateType());
                        docDetailItem.put("original_included", item.getOriginalIncluded());
                        docDetailItem.put("document_type", item.getDocumentType());

                        docDetailItem.put("ticket_number", item.getTicketNo());
                        docDetailItem.put("ticket_date", item.getDate());
                    /*docDetailItem.put("ticket_number", "dfg");
                    docDetailItem.put("ticket_date", "0000-00-0");*/

                        docDetailItem.put("no_of_copies", item.getTotalCopies());
                        docDetailItem.put("amount", String.valueOf(item.getAmount()));     //only item ki amount

                        document_detailsArray.put(docDetailItem);
                    }

                    //params.put("document_details", document_detailsArray);
                    docItem.put("document_details", document_detailsArray);
                    documentArray.put(docItem);
                }

                params.put("document", documentArray);
                System.out.println("my selected documents" + params);

            } catch (JSONException e) {
                System.out.println("Error : Add Document Details " + e.toString());
            }


            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<AddDocumentDetailsResult> call = apiInterface.callAddDocumentDetails(params);

            call.enqueue(new Callback<AddDocumentDetailsResult>() {
                @Override
                public void onResponse(Call<AddDocumentDetailsResult> call, Response<AddDocumentDetailsResult> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            Global.addDocumentDetailsResponse = response.body();
                            Global.enableAddDocument = true;

                            //set Fragment:
                            Fragment educationDetailsFragment = new GenerateAppFragment();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, educationDetailsFragment).addToBackStack(null).commit();

                        } else {
                            Global.utils.showErrorSnakeBar(getActivity(), "Please add document details properly.");
                        }
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AddDocumentDetailsResult> call, Throwable t) {
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