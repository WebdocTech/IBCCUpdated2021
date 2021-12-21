package com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.DocumentSelection;

import android.app.Activity;
import android.os.Bundle;

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
import com.webdoc.ibcc.Adapter.DocumentSelectionAdapter;
import com.webdoc.ibcc.Adapter.EquivalenceDocumentSelectionAdapter;
import com.webdoc.ibcc.Adapter.SubjectsAdapter;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.ApplyEquivalenceActivity;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.DocumentSelection.apimodels.DocumentsModel;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.DocumentSelection.apimodels.SaveDocumentDetails;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.GenerateApp.EquivalenceGenerateAppFragment;
import com.webdoc.ibcc.DashBoard.Home.HomeSharedViewModel.HomeSharedViewModel;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.SaveDocumentDetailsEQ.SaveDocumentDetailsEQ;
import com.webdoc.ibcc.ResponseModels.ViewAppointmentsEQ.ViewAppointmentsEQ;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.FragmentEquivalenceDocumentSelectionBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquivalenceDocumentSelectionFragment extends Fragment {
    private EquivalenceDocumentSelectionAdapter equivalenceDocumentSelectionAdapter;
    private VolleyRequestController volleyRequestController;
    public static TextView tvTotalAmount;
    private FragmentEquivalenceDocumentSelectionBinding layoutBinding;
    private HomeSharedViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutBinding = FragmentEquivalenceDocumentSelectionBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(HomeSharedViewModel.class);

        //volleyRequestController = new VolleyRequestController(getActivity());
        /*DOCUMENT SELECTION RECYCLERVIEW*/
        tvTotalAmount = layoutBinding.tvTotalAmountValue;
        setAdapter();
        observers();
        clickListeners();

        return layoutBinding.getRoot();
    }

    private void observers() {
        viewModel.getGetSaveDocumetnDetails().observe(getActivity(), response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Global.saveDocumentDetailsEQResponse = response;

                    ApplyEquivalenceActivity.stepIndicator.setCurrentStepPosition(4);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.equivalence_fragment_container, new EquivalenceGenerateAppFragment())
                            .addToBackStack(null).commit();

                } else {
                    Global.utils.showErrorSnakeBar(getActivity(), response.getResult()
                            .getResponseMessage());
                }
            }
        });
    }

    private void clickListeners() {
        layoutBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.documentsTotalFee = Integer.parseInt(layoutBinding.tvTotalAmountValue.getText().toString());

                //Global.utils.showCustomLoadingDialog(getActivity());
                //volleyRequestController.saveDocumentDetails(Global.eqModel);
                //callsaveDocumentDetailsApi(getActivity(), Global.eqModel);
                viewModel.callsaveDocumentDetailsApi(getActivity(), Global.eqModel);
            }
        });
    }

    private void setAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvEquivalenceDocumentSelection.setLayoutManager(layoutManager);
        layoutBinding.rvEquivalenceDocumentSelection.setHasFixedSize(true);
        equivalenceDocumentSelectionAdapter = new EquivalenceDocumentSelectionAdapter(getActivity());
        layoutBinding.rvEquivalenceDocumentSelection.setAdapter(equivalenceDocumentSelectionAdapter);
    }

    public void callsaveDocumentDetailsApi(Activity activity, List<DocDetailEQ_Model> dataModel) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            DocumentsModel documentsModel = new DocumentsModel();
            documentsModel.setCaseId(dataModel.get(0).getCaseID());

            //JSONObject params = new JSONObject();
            //try {
            //params.put("caseId", dataModel.get(0).getCaseID());
            //JSONArray docArray = new JSONArray();
            SaveDocumentDetails saveDocumentDetails = new SaveDocumentDetails();
            List<SaveDocumentDetails> list = new ArrayList<>();
            ;
            for (int j = 0; j < Global.equivalenceQualificationList.size(); j++) {

                saveDocumentDetails.setDocId(dataModel.get(j).getDocId());
                saveDocumentDetails.setAmount(dataModel.get(j).getAmount());
                saveDocumentDetails.setAverageMarks(0);
                saveDocumentDetails.setDocumentType(dataModel.get(j).getDocumentType());
                saveDocumentDetails.setEquivalenceOfCertificates(dataModel.get(j).getQofCert());
                saveDocumentDetails.setEquivalenceOfCertificatesType(dataModel.get(j).getqOfCertType());

                saveDocumentDetails.setFinalObtainedMarks(dataModel.get(j).getfObtMarks());
                saveDocumentDetails.setFinalTotalMarks(dataModel.get(j).getfTotalMarks());
                saveDocumentDetails.setObtainedMarks(dataModel.get(j).getObtainedMarks());
                saveDocumentDetails.setTotalMarks(dataModel.get(j).getTotalMarks());
                saveDocumentDetails.setPercentage(dataModel.get(j).getPercentage());

                saveDocumentDetails.setPreviousTicketNumber("");
                saveDocumentDetails.setTicketDate("2021-06-19T06:43:05.343Z");
                saveDocumentDetails.setTicketNumber("");
                saveDocumentDetails.setUrgent(dataModel.get(j).getUrgent());

                list.add(saveDocumentDetails);

                    /*JSONObject docItem = new JSONObject();
                    docItem.put("docId", dataModel.get(j).getDocId());
                    docItem.put("amount", dataModel.get(j).getAmount());
                    docItem.put("averageMarks", 0);
                    docItem.put("documentType", dataModel.get(j).getDocumentType());
                    docItem.put("equivalenceOfCertificates", dataModel.get(j).getQofCert());
                    docItem.put("equivalenceOfCertificatesType", dataModel.get(j).getqOfCertType());

                    docItem.put("finalObtainedMarks", dataModel.get(j).getfObtMarks());
                    docItem.put("finalTotalMarks", dataModel.get(j).getfTotalMarks());
                    docItem.put("obtainedMarks", dataModel.get(j).getObtainedMarks());
                    docItem.put("totalMarks", dataModel.get(j).getTotalMarks());
                    docItem.put("percentage", dataModel.get(j).getPercentage());

                    docItem.put("previousTicketNumber", "");
                    docItem.put("ticketDate", "2021-06-19T06:43:05.343Z");
                    docItem.put("ticketNumber", "");
                    docItem.put("urgent", dataModel.get(j).getUrgent());

                    docArray.put(docItem);*/
            }

            documentsModel.setSaveDocumentDetailsList(list);
            //params.put("saveDocumentDetailsList", docArray);
            Log.d("testparams", "callsaveDocumentDetailsApi: " + documentsModel);

            /*} catch (JSONException e) {
                System.out.println("Error : Save Document Details  " + e.toString());
            }*/

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<SaveDocumentDetailsEQ> call = apiInterface.callSaveDocumentsDetails(documentsModel);

            call.enqueue(new Callback<SaveDocumentDetailsEQ>() {
                @Override
                public void onResponse(Call<SaveDocumentDetailsEQ> call, Response<SaveDocumentDetailsEQ> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        String str = response.body().getResult().getResponseCode();
                        SaveDocumentDetailsEQ tst = response.body();
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            Global.saveDocumentDetailsEQResponse = response.body();

                            ApplyEquivalenceActivity.stepIndicator.setCurrentStepPosition(4);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.equivalence_fragment_container, new EquivalenceGenerateAppFragment()).addToBackStack(null).commit();

                        } else {
                            Global.utils.showErrorSnakeBar(getActivity(), response.body().getResult().getResponseMessage());
                        }
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SaveDocumentDetailsEQ> call, Throwable t) {
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