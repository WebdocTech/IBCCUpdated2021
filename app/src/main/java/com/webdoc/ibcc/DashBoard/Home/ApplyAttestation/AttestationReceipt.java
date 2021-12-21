package com.webdoc.ibcc.DashBoard.Home.ApplyAttestation;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.webdoc.ibcc.Adapter.DocumentReceiptAdapter;
import com.webdoc.ibcc.DashBoard.Dashboard;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.CallCourierGetTackingHistory.CallCourierGetTackingHistory;
import com.webdoc.ibcc.ServerManager.CallCourier.CallCourierVolleyListener;
import com.webdoc.ibcc.ServerManager.CallCourier.CallCourierVolleyRequestController;
import com.webdoc.ibcc.databinding.ActivityAttestationReceiptBinding;

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

public class AttestationReceipt extends AppCompatActivity implements CallCourierVolleyListener {
    private static final int REQUEST_CAMERA = 0;
    String appointment_method, trx_id, bank_name, payment_status;
    DocumentReceiptAdapter documentReceiptAdapter;
    public TextView tv_dialog_status;
    CallCourierVolleyRequestController callCourierVolleyRequestController;
    CallCourierGetTackingHistory getTackingHistory;
    Bitmap bitmap;
    private ActivityAttestationReceiptBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityAttestationReceiptBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        callCourierVolleyRequestController = new CallCourierVolleyRequestController(this);
        appointment_method = Global.appointment_method;
        trx_id = Global.trx_id;
        bank_name = Global.bank_name;
        payment_status = Global.payment_status;

        if (Global.userLoginResponse.getResult() != null) {
            layoutBinding.tvStudentName.setText(Global.userLoginResponse.getResult().getCustomerProfile().getFirstName());
            layoutBinding.tvFatherName.setText(Global.userLoginResponse.getResult().getCustomerProfile().getLastName());
            layoutBinding.tvCnic.setText(Global.userLoginResponse.getResult().getCustomerProfile().getCnic());
        }

        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        layoutBinding.tvDate.setText(currentDate + " at " + currentTime);  //11-03-2021 at 11:03AM
        layoutBinding.tvCaseId.setText(Global.caseId);         //759723
        layoutBinding.tvFormID.setText(Global.caseId);
        layoutBinding.tvChallanID.setText(Global.savePaymentInfo.getResult().getChallan_id());
        layoutBinding.tvConsignment.setText(Global.saveBookingResponse.getCnno());      //749723
        layoutBinding.tvAppointmentModel.setText("Courier");  //courier
        layoutBinding.tvTransaction.setText(trx_id);  //3423
        layoutBinding.tvPaymentModel.setText(bank_name);  //easy paisa
        layoutBinding.tvPaymentStatus.setText(payment_status);    //paid
        layoutBinding.tvAmountPaid.setText(Global.price);
        layoutBinding.tvAppDate.setText(currentDate);
        layoutBinding.tvAppTime.setText(currentTime);
        layoutBinding.tvAppointmentStatus.setText("Staff");

        try {
            layoutBinding.tvIbccCharges.setText(Global.ibccAmount);
        } catch (Exception e) {
            layoutBinding.tvIbccCharges.setText("");
        }
        layoutBinding.tvCourierCharges.setText(Global.courierFeeForReceipt);
        layoutBinding.tvSecurityFee.setText(Global.secuirtyFeeForReceipt);
        layoutBinding.tvBankCharges.setText(Global.bankChargesForReceipt);

        layoutBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // AttestationReceipt.super.onBackPressed();
                //finish();
                Intent intent = new Intent(AttestationReceipt.this, Dashboard.class);
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
                String url = "http://attest.ibcc.edu.pk/home/my_form/";
                String paymentId = Global.savePaymentInfo.getResult().getChallan_id();
                //String paymentId = trx_id;
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url + paymentId)));
            }
        });

        layoutBinding.ivTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.utils.showCustomLoadingDialog(AttestationReceipt.this);
                callCourierVolleyRequestController.getTackingHistory(Global.saveBookingResponse.getCnno());
            }
        });

        layoutBinding.ivCourier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://cod.callcourier.com.pk/Booking/AfterSavePublic/";
                String consignmentNo= Global.saveBookingResponse.getCnno();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url + consignmentNo)));
            }
        });

        layoutBinding.Layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://cod.callcourier.com.pk/Booking/AfterSavePublic/";
                String consignmentNo= Global.saveBookingResponse.getCnno();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url + consignmentNo)));
            }
        });

        layoutBinding.Layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://attest.ibcc.edu.pk/home/my_form/";
                String paymentId = Global.savePaymentInfo.getResult().getId();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url + paymentId)));
            }
        });

        setAdapter();

    }//onCreate

    private void setAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvDocuments.setLayoutManager(layoutManager);
        layoutBinding.rvDocuments.setHasFixedSize(true);
        documentReceiptAdapter = new DocumentReceiptAdapter(this);
        layoutBinding.rvDocuments.setAdapter(documentReceiptAdapter);
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

   /*   private void captureScreen() {
      View v = getWindow().getDecorView().getRootView();
        v.setDrawingCacheEnabled(true);
        Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        try {
            FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory().toString(),
                    "SCREEN" + System.currentTimeMillis() + ".png"));
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

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
        } else { //permission is automatically granted on sdk<23 upon installation
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

    /* public void onActivityResult() {
       View v = getWindow().getDecorView().getRootView();
      // View v= Full_Layout.getRootView();
       v.setDrawingCacheEnabled(true);
       bitmap = Bitmap.createBitmap(v.getDrawingCache());
       v.setDrawingCacheEnabled(false);

        if(isStoragePermissionGranted()){
            SaveImage(bitmap);
        }
    }*/

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
        Intent intent = new Intent(AttestationReceipt.this, Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void TrackingDialog() {
        // Global.utils.hideCustomLoadingDialog();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AttestationReceipt.this);
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
          //  CallCourierGetTackingHistory trackingResult = gson.fromJson(response.toString(), CallCourierGetTackingHistory.class);
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


}