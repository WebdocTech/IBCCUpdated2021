package com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.DocumentCheckList;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webdoc.ibcc.Adapter.DocumentCheckListAdapter;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.CallCourier.CallCourier;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.ActivityDocumentChecklisBinding;

public class DocumentChecklisActivity extends AppCompatActivity {
    DocumentCheckListAdapter documentCheckListAdapter;
    String appointment_method, trx_id, bank_name, payment_status;
    AlertDialog selectProcessAlertDialog;
    private ActivityDocumentChecklisBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityDocumentChecklisBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        Intent intent = getIntent();
        appointment_method = intent.getStringExtra("appointment_method");
        trx_id = intent.getStringExtra("trx_id");
        bank_name = intent.getStringExtra("bank_name");
        payment_status = intent.getStringExtra("payment_status");

        clickListeners();
        setAdapter();
    }

    private void clickListeners() {
        layoutBinding.ivSealedEnvelop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DocumentChecklisActivity.this);
                View v = getLayoutInflater().inflate(R.layout.alert_sealed_envelop, null);
                dialogBuilder.setView(v);
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                final ImageView iv_cancel = v.findViewById(R.id.iv_cancel);

                iv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

        layoutBinding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.userLoginResponse.getResult().getCustomerProfile().setIsAppointmentAvailabe(false);
                Global.appointment_method = appointment_method;
                Global.trx_id = trx_id;
                Global.bank_name = bank_name;
                Global.payment_status = payment_status;

                for (int i = 0; i < Global.selDocument.get(Global.selectedDoc).getDetailModelList().size(); i++) {
                    Global.attestationTotalDocuments = Global.attestationTotalDocuments + Integer.parseInt(Global.selDocument.get(Global.selectedDoc).getDetailModelList().get(i).getTotalCopies()) + 1;
                }

                dialogChooser();
            }
        });
    }

    private void setAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvDocumentCheckList.setLayoutManager(layoutManager);
        layoutBinding.rvDocumentCheckList.setHasFixedSize(true);
        documentCheckListAdapter = new DocumentCheckListAdapter(this);
        layoutBinding.rvDocumentCheckList.setAdapter(documentCheckListAdapter);
    }

    public void dialogChooser() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View v = getLayoutInflater().inflate(R.layout.alert_select_courier, null);
        dialogBuilder.setView(v);

        selectProcessAlertDialog = dialogBuilder.create();
        selectProcessAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView iv_callCourier = v.findViewById(R.id.iv_callCourier);
        ImageView iv_tcs = v.findViewById(R.id.iv_tcs);

        iv_callCourier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DocumentChecklisActivity.this, CallCourier.class);
                finish();
                startActivity(intent);

                selectProcessAlertDialog.dismiss();
            }
        });

        iv_tcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DocumentChecklisActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
            }
        });

        selectProcessAlertDialog.show();
    }

    @Override
    public void onBackPressed() {
    }
}