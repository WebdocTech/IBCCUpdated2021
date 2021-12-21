package com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence;

import android.Manifest;
import android.app.Activity;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.webdoc.ibcc.Adapter.DocumentReceiptAdapterEQ;
import com.webdoc.ibcc.Adapter.EducationDetailsAdapter;
import com.webdoc.ibcc.Adapter.SubjectsAdapter;
import com.webdoc.ibcc.DashBoard.Dashboard;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.EducationDetails.EducationDetailsFragment;
import com.webdoc.ibcc.DataModel.EducationDetailModel;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.AddEducationResult.AddEducationResult;
import com.webdoc.ibcc.ResponseModels.CallCourierGetTackingHistory.CallCourierGetTackingHistory;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.EquivalenceSubject;
import com.webdoc.ibcc.ServerManager.CallCourier.CallCourierVolleyListener;
import com.webdoc.ibcc.ServerManager.CallCourier.CallCourierVolleyRequestController;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.ActivityEquivalenceReceiptBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Equivalence_Receipt extends AppCompatActivity implements CallCourierVolleyListener {
    private static final int REQUEST_CAMERA = 0;
    private String appointment_method, trx_id, bank_name, payment_status;
    public TextView tv_dialog_status;
    private List<EquivalenceSubject> equivalenceSubjectList;
    private CallCourierVolleyRequestController callCourierVolleyRequestController;
    private Bitmap bitmap;
    private DocumentReceiptAdapterEQ documentReceiptAdapterEQ;
    private ActivityEquivalenceReceiptBinding layoutBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityEquivalenceReceiptBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        callCourierVolleyRequestController = new CallCourierVolleyRequestController(this);
        appointment_method = Global.appointment_method;
        trx_id = Global.trx_id;
        bank_name = Global.bank_name;
        payment_status = Global.payment_status;

        if (Global.isOnline) {
            layoutBinding.Layout1.setVisibility(View.GONE);
            layoutBinding.Layout2.setVisibility(View.GONE);
            layoutBinding.Layout3.setVisibility(View.GONE);
            //tv_appointmentModel.setText("Online");
        } else {
            layoutBinding.Layout1.setVisibility(View.VISIBLE);
            layoutBinding.Layout2.setVisibility(View.VISIBLE);
            layoutBinding.Layout3.setVisibility(View.VISIBLE);
            //tv_appointmentModel.setText("Courier");//courier
        }


        if (Global.userLoginResponse.getResult() != null) {
            layoutBinding.tvStudentName.setText(Global.userLoginResponse.getResult().getCustomerProfile().getFirstName());
            layoutBinding.tvFatherName.setText(Global.userLoginResponse.getResult().getCustomerProfile().getLastName());
            layoutBinding.tvCnic.setText(Global.userLoginResponse.getResult().getCustomerProfile().getCnic());
        }

        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        layoutBinding.tvDate.setText(currentDate + " at " + currentTime);  //11-03-2021 at 11:03AM
        layoutBinding.tvAppDate.setText(currentDate);
        layoutBinding.tvAppTime.setText(currentTime);

        if (Global.isIncompleteAppointmentEQ) {
            layoutBinding.tvChallanID.setText(Global.incompleteDetailsEQResponse.getResult().getPaymentinfo().getChalanId());
            layoutBinding.tvConsignment.setText(Global.saveBookingResponse.getCnno());
            layoutBinding.tvPaymentStatus.setText(Global.incompleteDetailsEQResponse.getResult().getPaymentinfo().getPaymentstatus());
            layoutBinding.tvCaseId.setText(Global.caseIdQualificationEQ);
            layoutBinding.tvFormID.setText(Global.caseIdQualificationEQ);
            layoutBinding.tvTransaction.setText(Global.incompleteDetailsEQResponse.getResult().getPaymentinfo().getTransactionid());
            layoutBinding.tvPaymentModel.setText(Global.incompleteDetailsEQResponse.getResult().getPaymentinfo().getAppointmentMethod());
            layoutBinding.tvCourierCharges.setText("Rs. " + "200");
            layoutBinding.tvAppointmentStatus.setText("Staff");
            layoutBinding.tvAppointmentModel.setText("Courier");//courier
            // tv_amountPaid.setText("Rs. " + Global.price);
            layoutBinding.tvIbccCharges.setText("Rs. " + Global.incompleteDetailsEQResponse.getResult().getPaymentinfo().getIbccAmount());
            if (!Global.incompleteDetailsEQResponse.getResult().getPaymentinfo().getIsSecurityCheck()) {
                layoutBinding.tvSecurityFee.setText("Rs. " + "0");
            } else {
                layoutBinding.tvSecurityFee.setText("Rs. " + "50");
            }

            calculateBankCharges();

        } else {
            layoutBinding.tvChallanID.setText(Global.savePaymentInfo.getResult().getId());
            layoutBinding.tvConsignment.setText(Global.saveBookingResponse.getCnno());
            layoutBinding.tvPaymentStatus.setText(payment_status);
            layoutBinding.tvCaseId.setText(Global.caseId);
            layoutBinding.tvFormID.setText(Global.caseId);
            layoutBinding.tvTransaction.setText(trx_id);
            layoutBinding.tvAmountPaid.setText("Rs. " + Global.price);
            layoutBinding.tvPaymentModel.setText(bank_name);     //easy paisa
            layoutBinding.tvIbccCharges.setText("Rs. " + Global.ibbcChargesForReceiptEQ);
            layoutBinding.tvCourierCharges.setText("Rs. " + Global.courierFeeForReceiptEQ);
            layoutBinding.tvSecurityFee.setText("Rs. " + Global.secuirtyFeeForReceiptEQ);
            layoutBinding.tvBankCharges.setText("Rs. " + Global.bankChargesForReceiptEQ);
            layoutBinding.tvAppointmentStatus.setText("Staff");
            layoutBinding.tvAppointmentModel.setText("Courier");//courier
        }

        layoutBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Equivalence_Receipt.this, Dashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
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
                //SaveImage(bitmap);

                if (isStoragePermissionGranted()) {
                    SaveImage(bitmap);
                }
            }
        });

        layoutBinding.ivPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://equivalence.ibcc.edu.pk/home/my_form/";
                String paymentId;
                if (Global.isIncompleteAppointmentEQ) {
                    paymentId = Global.caseIdQualificationEQ;
                } else {
                    paymentId = Global.caseId;
                }

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url + paymentId)));
            }
        });

        layoutBinding.ivTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.utils.showCustomLoadingDialog(Equivalence_Receipt.this);
                callCourierVolleyRequestController.getTackingHistory(Global.saveBookingResponse.getCnno());
            }
        });

        layoutBinding.ivCourier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://cod.callcourier.com.pk/Booking/AfterSavePublic/";
                String consignmentNo = Global.saveBookingResponse.getCnno();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url + consignmentNo)));
            }
        });

        layoutBinding.Layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://cod.callcourier.com.pk/Booking/AfterSavePublic/";
                String consignmentNo = Global.saveBookingResponse.getCnno();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url + consignmentNo)));
            }
        });

        layoutBinding.Layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String url = "http://equivalence.ibcc.edu.pk/home/my_form/";
                String paymentId;
                if (Global.isIncompleteAppointmentEQ) {
                    paymentId = Global.caseIdQualificationEQ;
                } else {
                    paymentId = Global.caseId;
                }

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url + paymentId)));

            }
        });


        setAdapter();


   /*     final LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_subjects.setLayoutManager(layoutManager2);
        rv_subjects.setHasFixedSize(true);

        subAdapter = new NewSubjAdapterForEqReciept(Equivalence_Receipt.this, Global.equivalenceSubjectList);
        rv_subjects.setAdapter(subAdapter);*/

    }//onCreate

    private void setAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvDocuments.setLayoutManager(layoutManager);
        layoutBinding.rvDocuments.setHasFixedSize(true);
        documentReceiptAdapterEQ = new DocumentReceiptAdapterEQ(this);
        layoutBinding.rvDocuments.setAdapter(documentReceiptAdapterEQ);
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

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // Toast.makeText(this, "Permission is granted", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(this, "Permission is revoked", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        } else {
            //permission is automatically granted on sdk<23 upon installation
            //Toast.makeText(this, "Permission is granted", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    @Override
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Equivalence_Receipt.this, Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void TrackingDialog() {
        // Global.utils.hideCustomLoadingDialog();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Equivalence_Receipt.this);
        View v = getLayoutInflater().inflate(R.layout.alert_receipt_track, null);
        dialogBuilder.setView(v);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final ImageView iv_cancel = v.findViewById(R.id.iv_cancel);
        tv_dialog_status = v.findViewById(R.id.tv_status);

        //String status = getTackingHistory.getProcessDescForPortal();
        //tv_dialog_status.setText("status");

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
    }//getResponse


    public void calculateBankCharges() {
        int ibccAmount = Integer.parseInt(Global.incompleteDetailsEQResponse.getResult().getPaymentinfo().getIbccAmount());
        int webdocAmount = Integer.parseInt(Global.incompleteDetailsEQResponse.getResult().getPaymentinfo().getWebdocAmount());

        int totalAmount = ibccAmount + webdocAmount + 200;

        float bankcharges = (totalAmount * 3) / 100;

        totalAmount += bankcharges;


        layoutBinding.tvAmountPaid.setText("Rs. " + String.valueOf(totalAmount));
        layoutBinding.tvBankCharges.setText("Rs. " + String.valueOf(bankcharges));
//        if (!Global.incompleteDetailsEQResponse.getResult().getPaymentinfo().getIsSecurityCheck()) {
//            tv_bank_charges.setText("Rs. " + String.valueOf(bankcharges));
//        } else {
//            tv_bank_charges.setText("Rs. " + String.valueOf(bankChargesIfTrue));
//        }
    }

}