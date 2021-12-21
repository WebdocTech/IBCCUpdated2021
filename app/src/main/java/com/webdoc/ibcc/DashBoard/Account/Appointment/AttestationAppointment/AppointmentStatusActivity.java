package com.webdoc.ibcc.DashBoard.Account.Appointment.AttestationAppointment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.google.gson.Gson;
import com.webdoc.ibcc.Adapter.AppointmentStatusAdapter;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.CallCourierGetTackingHistory.CallCourierGetTackingHistory;
import com.webdoc.ibcc.ServerManager.CallCourier.CallCourierVolleyListener;
import com.webdoc.ibcc.ServerManager.CallCourier.CallCourierVolleyRequestController;
import com.webdoc.ibcc.databinding.ActivityAppointmentStatusBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AppointmentStatusActivity extends AppCompatActivity implements CallCourierVolleyListener {
    private AppointmentStatusAdapter documentReceiptAdapter;
    private static final int REQUEST_CAMERA = 0;
    private Bitmap bitmap;
    public TextView tv_dialog_status;
    CallCourierVolleyRequestController callCourierVolleyRequestController;
    private ActivityAppointmentStatusBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityAppointmentStatusBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        callCourierVolleyRequestController = new CallCourierVolleyRequestController(this);

        if (Global.userLoginResponse.getResult() != null) {
            layoutBinding.tvStudentName.setText(Global.userLoginResponse.getResult().getCustomerProfile().getFirstName());
            layoutBinding.tvFatherName.setText(Global.userLoginResponse.getResult().getCustomerProfile().getLastName());
            layoutBinding.tvCnic.setText(Global.userLoginResponse.getResult().getCustomerProfile().getCnic());
        }

        layoutBinding.tvDate.setText(Global.selectedAppointAtt.getTransactionDatetime());  //11-03-2021 at 11:03AM
        layoutBinding.tvCaseId.setText(Global.selectedAppointAtt.getCaseId());         //759723
        layoutBinding.tvConsignment.setText(Global.selectedAppointAtt.getConsignmentNo());      //749723
        layoutBinding.tvAppointmentModel.setText("Courier");
        layoutBinding.tvTransaction.setText(Global.selectedAppointAtt.getTransactionId());  //3423
        layoutBinding.tvPaymentModel.setText(Global.selectedAppointAtt.getBank());  //easy paisa
        layoutBinding.tvPaymentStatus.setText(Global.selectedAppointAtt.getPaymentStatus());    //paid
        layoutBinding.tvAppDate.setText(Global.selectedAppointAtt.getTransactionDatetime());
        layoutBinding.tvFormID.setText(Global.selectedAppointAtt.getCaseId());
        layoutBinding.tvChallanID.setText(Global.selectedAppointAtt.getChallanId());
        layoutBinding.tvAmountPaid.setText(Global.selectedAppointAtt.getAmount());

        layoutBinding.tvIbccCharges.setText("Rs. " + Global.selectedAppointAtt.getIbccAmount());
        layoutBinding.tvCourierCharges.setText("Rs.200");
        layoutBinding.tvSecurityFee.setText("Rs.50");
        //tv_bank_charges.setText(0);

        calculateBankCharges();
        clickListeners();
        setAdapter();
    }

    private void clickListeners() {
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
                String paymentId = Global.selectedAppointAtt.getSaveFormId();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url + paymentId)));
            }
        });

        layoutBinding.ivTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.utils.showCustomLoadingDialog(AppointmentStatusActivity.this);
                callCourierVolleyRequestController.getTackingHistory(Global.selectedAppointAtt.getConsignmentNo());
            }
        });

        layoutBinding.ivCourier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://cod.callcourier.com.pk/Booking/AfterSavePublic/";
                String consignmentNo = Global.selectedAppointAtt.getConsignmentNo();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url + consignmentNo)));
            }
        });


        layoutBinding.Layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://cod.callcourier.com.pk/Booking/AfterSavePublic/";
                String consignmentNo = Global.selectedAppointAtt.getConsignmentNo();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url + consignmentNo)));
            }
        });

        layoutBinding.Layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://attest.ibcc.edu.pk/home/my_form/";
                String paymentId = Global.selectedAppointAtt.getSaveFormId();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url + paymentId)));
            }
        });
    }

    private void setAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvDocuments.setLayoutManager(layoutManager);
        layoutBinding.rvDocuments.setHasFixedSize(true);
        documentReceiptAdapter = new AppointmentStatusAdapter(this);
        layoutBinding.rvDocuments.setAdapter(documentReceiptAdapter);
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

    public void calculateBankCharges() {
        int courierFee = 200;
        int securityFee = 50;
        int totalAmount = Integer.parseInt(Global.selectedAppointAtt.getAmount());
        int ibccTotalAmount = Integer.parseInt(Global.selectedAppointAtt.getIbccAmount());

        int bankCharges = totalAmount - courierFee - ibccTotalAmount;

        int bankChargesIfTrue = bankCharges - securityFee;   // true
        int bankChargesIfFalse = bankCharges;              // false

        if (!Global.selectedAppointAtt.getIsSecurityCheck()) {
            layoutBinding.tvBankCharges.setText("Rs. " + String.valueOf(bankChargesIfFalse));
        } else {
            layoutBinding.tvBankCharges.setText("Rs. " + String.valueOf(bankChargesIfTrue));
        }
    }

    public void TrackingDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AppointmentStatusActivity.this);
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
                    //Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
                    tv_dialog_status.setText(status);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}