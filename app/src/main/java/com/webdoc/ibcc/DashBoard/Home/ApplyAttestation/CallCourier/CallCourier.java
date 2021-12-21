package com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.CallCourier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.webdoc.ibcc.Adapter.Spinner.CallCourier.SenderAreaAdapter;
import com.webdoc.ibcc.Adapter.Spinner.CallCourier.SenderCityAdapter;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.AttestationReceipt;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.CallCourier_EQ.CallCourier_EQ;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.Equivalence_Receipt;
import com.webdoc.ibcc.DashBoard.Home.HomeSharedViewModel.HomeSharedViewModel;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.AddEducationResult.AddEducationResult;
import com.webdoc.ibcc.ResponseModels.GetAreasByCity.GetAreasByCity;
import com.webdoc.ibcc.ResponseModels.GetCityListByService.GetCityListByService;
import com.webdoc.ibcc.ResponseModels.SaveBooking.SaveBooking;
import com.webdoc.ibcc.ResponseModels.SaveCourierDetials.SaveCourierDetials;
import com.webdoc.ibcc.ResponseModels.SaveCourierDetialsEQ.SaveCourierDetialsEQ;
import com.webdoc.ibcc.ServerManager.CallCourier.CallCourierVolleyListener;
import com.webdoc.ibcc.ServerManager.CallCourier.CallCourierVolleyRequestController;
import com.webdoc.ibcc.ServerManager.VolleyListener;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.ActivityCallCourierBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallCourier extends AppCompatActivity implements CallCourierVolleyListener, VolleyListener {
    private String pAddress;
    private String saveBookingParameter;
    private String description = "";
    private GetCityListByService getCityListByServiceSender;
    private GetAreasByCity getAreasByCity;
    private CallCourierVolleyRequestController callCourierVolleyRequestController;
    private VolleyRequestController volleyRequestController;
    private ActivityCallCourierBinding layoutBinding;
    private HomeSharedViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityCallCourierBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        //ViewModel:
        viewModel = ViewModelProviders.of(this).get(HomeSharedViewModel.class);

        callCourierVolleyRequestController = new CallCourierVolleyRequestController(this);
        volleyRequestController = new VolleyRequestController(this);

        if (Global.userLoginResponse != null) {
            layoutBinding.edtAddress.setText(Global.userLoginResponse.getResult().getCustomerProfile().getcAdd());
        }

        layoutBinding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pAddress = Global.userLoginResponse.getResult().getCustomerProfile().getcAdd();

                for (int i = 0; i < Global.selDocument.size(); i++) {
                    description = Global.selDocument.get(i).getCertificate() + "," + Global.selDocument.get(i).getProgram() + "," + description;
                    for (int j = 0; j < Global.selDocument.get(i).getDetailModelList().size(); j++) {
                        description = Global.selDocument.get(i).getDetailModelList().get(j).getCertificateType() + "," + Global.selDocument.get(i).getDetailModelList().get(j).getTotalCopies() + "," + description;
                    }
                }

                saveBookingParameter = "loginId=test-0001&ConsigneeName=IBCC&ConsigneeRefNo=abc&ConsigneeCellNo=" + Global.attestationGenerateAppCenter.getPhoneNumber()
                        + "&Address=" + Global.attestationGenerateAppCenter.getAddress()
                        + "&Origin=CallCourier Sale Branch&DestCityId=" + Global.attestationGenerateAppCenter.getCallCourierId()
                        + "&ServiceTypeId=1&Pcs=" + Global.attestationTotalDocuments
                        + "&Weight=" + Global.attestationTotalDocuments
                        + "&Description=" + description + "&SelOrigin=Domestic&CodAmount=1" +
                        "&SpecialHandling=false&MyBoxId=1&Holiday=false&remarks=abc&ShipperName=" +
                        Global.userLoginResponse.getResult().getCustomerProfile().getFirstName() + " " +
                        Global.userLoginResponse.getResult().getCustomerProfile().getLastName()
                        + "&ShipperCellNo=" + Global.userLoginResponse.getResult().getCustomerProfile().getPhone()
                        + "&ShipperArea=" + getAreasByCity.getAreaID()
                        + "&ShipperCity=" + getCityListByServiceSender.getCityID()
                        + "&ShipperAddress=" + pAddress
                        + "&ShipperLandLineNo=" + Global.userLoginResponse.getResult().getCustomerProfile().getPhone()
                        + "&ShipperEmail=" + Global.userLoginResponse.getResult().getCustomerProfile().getEmail();

                System.out.println("Save Booking: " + saveBookingParameter);

                Global.utils.showCustomLoadingDialog(CallCourier.this);
                callCourierVolleyRequestController.saveBooking(saveBookingParameter);
                //callSaveBookingApi(CallCourier.this, saveBookingParameter);
            }
        });

        Global.utils.showCustomLoadingDialog(this);
        callCourierVolleyRequestController.getCityListByService();
        //callCityListByServiceApi(this);

        observers();
    }

    private void observers() {
        viewModel.getSaveCourierDetails().observe(this, response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Global.saveCourierDetials = response;

                    Intent intent = new Intent(CallCourier.this, AttestationReceipt.class);
                    finish();
                    startActivity(intent);
                } else {
                    Global.utils.showErrorSnakeBar(CallCourier.this, response.getResult().getResponseMessage());
                }
            }
        });
    }

    @Override
    public void getCallCourierRequestResponse(String response, String requestType) {
        Gson gson = new Gson();

        if (requestType.equals(Constants.GETCITYLISTBYSERVICE)) {
            List<GetCityListByService> getCityListByServiceList = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(response);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    GetCityListByService getCityListByService = gson.fromJson(obj.toString(), GetCityListByService.class);
                    getCityListByServiceList.add(getCityListByService);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //SENDER CITY SPINNER
            SenderCityAdapter spinnerSenderCityAdapter = new SenderCityAdapter(this, R.layout.spinner_item, getCityListByServiceList);
            layoutBinding.spinnerSenderCity.setAdapter(spinnerSenderCityAdapter);

            layoutBinding.spinnerSenderCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    getCityListByServiceSender = getCityListByServiceList.get(position);
                    if (!Global.utils.alertDialog.isShowing())
                        Global.utils.showCustomLoadingDialog(CallCourier.this);
                    callCourierVolleyRequestController.getAreasByCity(getCityListByServiceSender.getCityID().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub;
                }
            });
        } else if (requestType.equals(Constants.GETAREASBYCITY)) {
            Global.utils.hideCustomLoadingDialog();
            List<GetAreasByCity> getAreasByCityList = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(response);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    GetAreasByCity getAreasByCity = gson.fromJson(obj.toString(), GetAreasByCity.class);
                    getAreasByCityList.add(getAreasByCity);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //AREAS SPINNER
            SenderAreaAdapter spinnerAreaAdapter = new SenderAreaAdapter(this, R.layout.spinner_item, getAreasByCityList);
            layoutBinding.spinnerSenderArea.setAdapter(spinnerAreaAdapter);

            layoutBinding.spinnerSenderArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    getAreasByCity = getAreasByCityList.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub;
                }
            });
        } else if (requestType.equals(Constants.SAVEBOOKING)) {
            SaveBooking saveBooking = gson.fromJson(response, SaveBooking.class);

            if (saveBooking.getResponse().contains("false")) {
                Global.utils.hideCustomLoadingDialog();
                Toast.makeText(this, saveBooking.getResponse(), Toast.LENGTH_SHORT).show();
                return;
            }

            Global.saveBookingResponse = saveBooking;

            //volleyRequestController.saveCourierDetails();
            //callSaveCourierDetailsApi(this);
            viewModel.callSaveCourierDetailsApi(this);

        }
    }

    public void callSaveCourierDetailsApi(Activity activity) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("center", String.valueOf(Global.center));
            params.addProperty("case_id", Global.caseId);
            params.addProperty("user_id", Global.userLoginResponse.getResult().getCustomerProfile().getId());
            params.addProperty("consignment_id", Global.saveBookingResponse.getCnno());

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<SaveCourierDetials> call = apiInterface.callSaveCourierDetails(params);

            call.enqueue(new Callback<SaveCourierDetials>() {
                @Override
                public void onResponse(Call<SaveCourierDetials> call, Response<SaveCourierDetials> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            Global.saveCourierDetials = response.body();

                            Intent intent = new Intent(CallCourier.this, AttestationReceipt.class);
                            finish();
                            startActivity(intent);
                        } else {
                            Global.utils.showErrorSnakeBar(CallCourier.this, response.body().getResult().getResponseMessage());
                        }
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SaveCourierDetials> call, Throwable t) {
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
    public void getRequestResponse(JSONObject response, String requestType) throws JSONException {
        Gson gson = new Gson();

        if (requestType.equals(Constants.SAVECOURIERDETIALS)) {
            Global.utils.hideCustomLoadingDialog();
            SaveCourierDetials saveCourierDetials = gson.fromJson(response.toString(), SaveCourierDetials.class);

            if (saveCourierDetials.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                Global.saveCourierDetials = saveCourierDetials;

                Intent intent = new Intent(CallCourier.this, AttestationReceipt.class);
                finish();
                startActivity(intent);
            } else {
                Global.utils.showErrorSnakeBar(this, saveCourierDetials.getResult().getResponseMessage());
            }
        }
    }

    public void callSaveBookingApi(Activity activity, String params) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            APIInterface apiInterface = APIClient.getClient(Constants.CALLCOURIER_BASEURL);
            Call<SaveBooking> call = apiInterface.callSaveBooking(params);

            call.enqueue(new Callback<SaveBooking>() {
                @Override
                public void onResponse(Call<SaveBooking> call, Response<SaveBooking> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getResponse().contains("false")) {
                            Global.utils.hideCustomLoadingDialog();
                            Toast.makeText(CallCourier.this, response.body().getResponse(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Global.saveBookingResponse = response.body();

                        //volleyRequestController.saveCourierDetails();
                        //callSaveCourierDetailsApi(CallCourier.this);
                        viewModel.callSaveCourierDetailsApi(CallCourier.this);
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SaveBooking> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callCityListByServiceApi(Activity activity) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            APIInterface apiInterface = APIClient.getClient(Constants.CALLCOURIER_BASEURL);
            Call<GetCityListByService> call = apiInterface.callCityListByService();

            call.enqueue(new Callback<GetCityListByService>() {
                @Override
                public void onResponse(Call<GetCityListByService> call, Response<GetCityListByService> response) {
                    Global.utils.hideCustomLoadingDialog();

                    Gson gson = new Gson();
                    if (response.isSuccessful()) {
                        List<GetCityListByService> getCityListByServiceList = new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            /*for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                GetCityListByService getCityListByService = gson.fromJson(obj.toString(), GetCityListByService.class);*/
                            getCityListByServiceList.add(response.body());
                            //}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //SENDER CITY SPINNER
                        SenderCityAdapter spinnerSenderCityAdapter = new SenderCityAdapter(CallCourier.this, R.layout.spinner_item, getCityListByServiceList);
                        layoutBinding.spinnerSenderCity.setAdapter(spinnerSenderCityAdapter);

                        layoutBinding.spinnerSenderCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                                getCityListByServiceSender = getCityListByServiceList.get(position);
                                if (!Global.utils.alertDialog.isShowing())
                                    //Global.utils.showCustomLoadingDialog(CallCourier.this);
                                    //callCourierVolleyRequestController.getAreasByCity(getCityListByServiceSender.getCityID().toString());
                                    callAreasByCityApi(CallCourier.this, getCityListByServiceSender.getCityID().toString());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub;
                            }
                        });
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GetCityListByService> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void callAreasByCityApi(Activity activity, String cityID) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            APIInterface apiInterface = APIClient.getClient(Constants.CALLCOURIER_BASEURL);
            Call<GetAreasByCity> call = apiInterface.callGetAreasByCity(cityID);

            call.enqueue(new Callback<GetAreasByCity>() {
                @Override
                public void onResponse(Call<GetAreasByCity> call, Response<GetAreasByCity> response) {
                    Global.utils.hideCustomLoadingDialog();

                    Gson gson = new Gson();
                    if (response.isSuccessful()) {
                        List<GetAreasByCity> getAreasByCityList = new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            /*for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                GetAreasByCity getAreasByCity = gson.fromJson(obj.toString(), GetAreasByCity.class);*/
                            getAreasByCityList.add(response.body());
                            //}

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        //AREAS SPINNER
                        SenderAreaAdapter spinnerAreaAdapter = new SenderAreaAdapter(CallCourier.this, R.layout.spinner_item, getAreasByCityList);
                        layoutBinding.spinnerSenderArea.setAdapter(spinnerAreaAdapter);

                        layoutBinding.spinnerSenderArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                                getAreasByCity = getAreasByCityList.get(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub;
                            }
                        });
                    } else {
                        Toast.makeText(activity, "Ooops! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GetAreasByCity> call, Throwable t) {
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