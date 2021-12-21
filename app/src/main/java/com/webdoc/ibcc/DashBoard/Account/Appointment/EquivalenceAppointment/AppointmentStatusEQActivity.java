package com.webdoc.ibcc.DashBoard.Account.Appointment.EquivalenceAppointment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.webdoc.ibcc.Adapter.AppointmentStatusEQAdapter;
import com.webdoc.ibcc.DashBoard.Account.Appointment.AppointmentViewModel.AppointmentViewModel;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.UpdatePaymentInfoRequestModel;
import com.webdoc.ibcc.Model.inquiryRequestModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.CallCourierGetTackingHistory.CallCourierGetTackingHistory;
import com.webdoc.ibcc.ResponseModels.inquiryResult.InquiryResult;
import com.webdoc.ibcc.ResponseModels.updatePAymentInfoResult.UpdatePAymentInfoResult;
import com.webdoc.ibcc.Retrofit.jsonPlaceHolderApi;
import com.webdoc.ibcc.ServerManager.CallCourier.CallCourierVolleyListener;
import com.webdoc.ibcc.ServerManager.CallCourier.CallCourierVolleyRequestController;
import com.webdoc.ibcc.databinding.ActivityAppointmentStatusBinding;
import com.webdoc.ibcc.databinding.ActivityAppointmentStatusEqBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppointmentStatusEQActivity extends AppCompatActivity implements CallCourierVolleyListener {
    private static final int REQUEST_CAMERA = 0;
    private Bitmap bitmap;
    public TextView tv_dialog_status;
    private CallCourierVolleyRequestController callCourierVolleyRequestController;
    private ActivityAppointmentStatusEqBinding layoutBinding;
    private AppointmentViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityAppointmentStatusEqBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        viewModel = ViewModelProviders.of(this).get(AppointmentViewModel.class);

        callCourierVolleyRequestController = new CallCourierVolleyRequestController(this);
        Intent intent = getIntent();

        String orderIDD = intent.getStringExtra("orderId");
        String storeId = intent.getStringExtra("storeId");
        String accountNum = intent.getStringExtra("accountNum");

        //callingPaymentInquiryApi(orderIDD, storeId, accountNum);
        viewModel.callingPaymentInquiryApi(orderIDD, storeId, accountNum);

        if (Global.userLoginResponse.getResult() != null) {
            layoutBinding.tvStudentName.setText(Global.userLoginResponse.getResult().getCustomerProfile().getFirstName());
            layoutBinding.tvFatherName.setText(Global.userLoginResponse.getResult().getCustomerProfile().getLastName());
            layoutBinding.tvCnic.setText(Global.userLoginResponse.getResult().getCustomerProfile().getCnic());
        }

        layoutBinding.tvDate.setText(Global.selectedAppointEQ.getDatetime());  //11-03-2021 at 11:03AM
        layoutBinding.tvCaseId.setText(Global.selectedAppointEQ.getCaseid());              //759723
        layoutBinding.tvConsignment.setText(Global.selectedAppointEQ.getConsignmentno());  //749723
        layoutBinding.tvAppointmentModel.setText("Courier");
        layoutBinding.tvTransaction.setText(Global.selectedAppointEQ.getTransactionid());  //3423
        layoutBinding.tvPaymentModel.setText(Global.selectedAppointEQ.getPaidthrough());   //easy paisa
        layoutBinding.tvPaymentStatus.setText(Global.selectedAppointEQ.getPaymentstatus()); //paid
        layoutBinding.tvAppDate.setText(Global.selectedAppointEQ.getDatetime());
        layoutBinding.tvFormID.setText(Global.selectedAppointEQ.getCaseid());
        layoutBinding.tvChallanID.setText(Global.selectedAppointEQ.getChallanid());
        layoutBinding.tvAmountPaid.setText(Global.selectedAppointEQ.getPaidamount());

        layoutBinding.tvIbccCharges.setText("Rs. " + Global.selectedAppointEQ.getIbccamount());
        layoutBinding.tvCourierCharges.setText("Rs.200");
        layoutBinding.tvSecurityFee.setText("Rs.50");
        layoutBinding.tvBankCharges.setText("Rs. " + Global.selectedAppointEQ.getBankcharges());

        layoutBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        layoutBinding.ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBitmapFromView(layoutBinding.sv, layoutBinding.sv.getChildAt(0).getHeight(),
                        layoutBinding.sv.getChildAt(0).getWidth());
                bitmap = getBitmapFromView(layoutBinding.sv, layoutBinding.sv.getChildAt(0).getHeight(),
                        layoutBinding.sv.getChildAt(0).getWidth());
                Log.i("dsd", String.valueOf(bitmap));

                if (isStoragePermissionGranted()) {
                    SaveImage(bitmap);
                }
            }
        });

        layoutBinding.ivPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://attest.ibcc.edu.pk/home/my_form/";
                String paymentId = Global.selectedAppointEQ.getWebviewId();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url + paymentId)));
            }
        });

        layoutBinding.ivTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.utils.showCustomLoadingDialog(AppointmentStatusEQActivity.this);
                callCourierVolleyRequestController.getTackingHistory(Global.selectedAppointEQ.getConsignmentno());
            }
        });

        layoutBinding.ivCourier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://cod.callcourier.com.pk/Booking/AfterSavePublic/";
                String consignmentNo = Global.selectedAppointEQ.getConsignmentno();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url + consignmentNo)));
            }
        });

        layoutBinding.Layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://cod.callcourier.com.pk/Booking/AfterSavePublic/";
                String consignmentNo = Global.selectedAppointEQ.getConsignmentno();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url + consignmentNo)));
            }
        });

        layoutBinding.Layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String url = "http://equivalence.ibcc.edu.pk/home/my_form/";
                String paymentId = Global.selectedAppointEQ.getWebviewId();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url + paymentId)));
            }
        });

        observers();
        setAdapter();
    }

    private void observers() {
        viewModel.getPaymentInquiry().observe(this, response -> {
            if (response != null) {
                if (response.getResponseDesc().equals("SUCCESS")) {
                    Global.inquiryResult = response;
                    layoutBinding.tvPaymentStatus.setText(response.getTransactionStatus());

                    if (response.getTransactionStatus().equals("SUCCESS")) {

                        String case_id = Global.selectedAppointEQ.getCaseid();
                        String transection_id = Global.selectedAppointEQ.getTransactionid();
                        String type = "Paid";

                        //callingUpdatePaymentInfoEQApi(case_id, transection_id, type);
                        viewModel.callingUpdatePaymentInfoEQApi(case_id, transection_id, type);

                    } else if (response.getTransactionStatus().equals("EXPIRED")) {
                        String case_id = Global.selectedAppointEQ.getCaseid();
                        String transection_id = Global.selectedAppointEQ.getTransactionid();
                        String type = "Fail";

                        //callingUpdatePaymentInfoEQApi(case_id, transection_id, type);
                        viewModel.callingUpdatePaymentInfoEQApi(case_id, transection_id, type);
                    }
                }
            }
        });

        viewModel.getUpdatePaymentInfoEQ().observe(this, response -> {
            if (response != null) {
                Toast.makeText(AppointmentStatusEQActivity.this, response.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvDocuments.setLayoutManager(layoutManager);
        layoutBinding.rvDocuments.setHasFixedSize(true);
        AppointmentStatusEQAdapter appointmentStatusEQAdapter = new AppointmentStatusEQAdapter(this);
        layoutBinding.rvDocuments.setAdapter(appointmentStatusEQAdapter);
    }

    private void callingPaymentInquiryApi(String orderId, String storeId, String accountNum) {

        inquiryRequestModel requestModel = new inquiryRequestModel();
        requestModel.setAccountNum(accountNum);
        requestModel.setOrderId(orderId);
        requestModel.setStoreId(storeId);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient();
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" https://easypay.easypaisa.com.pk/easypay-service/rest/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) // Set HttpClient to be used by Retrofit
                .build();

        jsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);
        Call<InquiryResult> call1 = jsonPlaceHolderApi.inquiryresult(requestModel);

        call1.enqueue(new Callback<InquiryResult>() {
            @Override
            public void onResponse(Call<InquiryResult> call, Response<InquiryResult> response) {

                InquiryResult result = response.body();
                Global.inquiryResult = result;
                if (response.isSuccessful()) {
                    if (result.getResponseDesc().equals("SUCCESS")) {
                        layoutBinding.tvPaymentStatus.setText(response.body().getTransactionStatus());

                        if (result.getTransactionStatus().equals("SUCCESS")) {

                            String case_id = Global.selectedAppointEQ.getCaseid();
                            String transection_id = Global.selectedAppointEQ.getTransactionid();
                            String type = "Paid";
                            callingUpdatePaymentInfoEQApi(case_id, transection_id, type);

                        } else if (result.getTransactionStatus().equals("EXPIRED")) {
                            String case_id = Global.selectedAppointEQ.getCaseid();
                            String transection_id = Global.selectedAppointEQ.getTransactionid();
                            String type = "Fail";
                            callingUpdatePaymentInfoEQApi(case_id, transection_id, type);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<InquiryResult> call, Throwable t) {
                Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    public void callingUpdatePaymentInfoEQApi(String caseId, String transectionId, String type) {
        UpdatePaymentInfoRequestModel requestModel = new UpdatePaymentInfoRequestModel();
        requestModel.setCase_id(caseId);
        requestModel.setTransection_id(transectionId);
        requestModel.setType(type);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient();
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ibcc.webddocsystems.com/api/Equivalence/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) // Set HttpClient to be used by Retrofit
                .build();

        jsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);
        Call<UpdatePAymentInfoResult> call1 = jsonPlaceHolderApi.UpdatePaymentInfoEQ(requestModel);

        call1.enqueue(new Callback<UpdatePAymentInfoResult>() {
            @Override
            public void onResponse(Call<UpdatePAymentInfoResult> call, Response<UpdatePAymentInfoResult> response) {

                UpdatePAymentInfoResult result = response.body();
                Global.updatePAymentInfoResult = result;
                if (!response.isSuccessful()) {
                    Toast.makeText(AppointmentStatusEQActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<UpdatePAymentInfoResult> call, Throwable t) {
                Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // Toast.makeText(this, "Permission is granted", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                // Toast.makeText(this, "Permission is revoked", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            //Toast.makeText(this, "Permission is granted", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //  Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                    SaveImage(bitmap);
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void SaveImage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        //File myDir = new File(root + "/IBCC_saved_images");

        File myDir = new File(root + "/DCIM" + "/Camera");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getBitmapFromView(View view, int height, int width) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }


    public void TrackingDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AppointmentStatusEQActivity.this);
        View v = getLayoutInflater().inflate(R.layout.alert_receipt_track, null);
        dialogBuilder.setView(v);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final ImageView iv_cancel = v.findViewById(R.id.iv_cancel);
        tv_dialog_status = v.findViewById(R.id.tv_status);

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    @Override
    public void getCallCourierRequestResponse(String response, String requestType) {
        Gson gson = new Gson();

        if (requestType.equals(Constants.GETTACKINGHISTORY)) {
            Global.utils.hideCustomLoadingDialog();

            List<CallCourierGetTackingHistory> getTackingHistories = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(response);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    CallCourierGetTackingHistory getTacking = gson.fromJson(obj.toString(), CallCourierGetTackingHistory.class);
                    getTackingHistories.add(getTacking);

                    TrackingDialog();

                    String status = getTacking.getProcessDescForPortal();
                    tv_dialog_status.setText(status);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}