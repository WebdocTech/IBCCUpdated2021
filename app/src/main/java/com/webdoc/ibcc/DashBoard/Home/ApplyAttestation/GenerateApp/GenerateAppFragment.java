package com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.GenerateApp;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.webdoc.ibcc.Adapter.SelectedDocumentAdapter;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.AttestationApplyActivity;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.Pyament.PaymentFragment;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.FragmentGenerateAppBinding;

public class GenerateAppFragment extends Fragment {
    //Spinner spinner_center;
    public static EditText edt_Amount;
    SelectedDocumentAdapter selectedDocumentAdapter;
    int Amount, count, allDocumentsFee;
    int courierFee = 200;
    int securityFee = 50;
    boolean checkBoxState = true;
    private FragmentGenerateAppBinding layoutBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generate_app, container, false);
        layoutBinding = FragmentGenerateAppBinding.inflate(inflater, container, false);
        edt_Amount = layoutBinding.edtAmount;

        layoutBinding.tvCourierFee.setText("Rs." + courierFee);
        layoutBinding.tvSecurityFee.setText("Rs." + securityFee);

        clickListeners();


       /* secureAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                documentSecureDialog();
            }
        });
        tv_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                documentSecureDialog();
            }
        });*/
        ;


        Global.attestationGenerateAppCenter = Global.pdfResponse.getResult().getCenters().get(0);
        //SPINNER CENTER
       /* SpinnerCenterAdapter arrayAdapter3 = new SpinnerCenterAdapter(getActivity(), R.layout.spinner_item, Global.pdfResponse.getResult().getCenters());
        spinner_center.setAdapter(arrayAdapter3);
        spinner_center.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                center_name = Global.pdfResponse.getResult().getCenters().get(position).getName();
                center_Id = Global.pdfResponse.getResult().getCenters().get(position).getId().toString();
                Global.attestationGenerateAppCenter = Global.pdfResponse.getResult().getCenters().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });*/

        //setting document adapter:
        setAdapter();

        /*: TODO  PAYMENT*/
        count = 0;
        allDocumentsFee = 0;
        if (selectedDocumentAdapter != null) {
            count = selectedDocumentAdapter.getItemCount();
        }

        if (Global.selDocument != null) {
            for (int i = 0; i < Global.selDocument.get(Global.selectedDoc).getDetailModelList().size(); i++) {
                allDocumentsFee += Global.selDocument.get(Global.selectedDoc).getDetailModelList().get(i).getAmount();
            }
        }

        DocumentPayment(allDocumentsFee + courierFee + securityFee, 3);  //900 for 3 documents: (900,3)

        return view;
    }

    private void setAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvDocumentList.setLayoutManager(layoutManager);
        layoutBinding.rvDocumentList.setHasFixedSize(true);
        selectedDocumentAdapter = new SelectedDocumentAdapter(getActivity());
        layoutBinding.rvDocumentList.setAdapter(selectedDocumentAdapter);
    }

    private void clickListeners() {
        layoutBinding.switchSecurityFee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    documentSecureDialog();

                    securityFee = 50;
                    layoutBinding.tvSecurityFee.setText("Rs." + securityFee);
                    DocumentPayment(allDocumentsFee + courierFee + securityFee, 3);  //900 for 3 documents: (900,3)
                } else {
                    No_documentSecureDialog();

                    securityFee = 0;
                    layoutBinding.tvSecurityFee.setText("Rs." + securityFee);
                    DocumentPayment(allDocumentsFee + courierFee + securityFee, 3);  //900 for 3 documents: (900,3)
                }
            }
        });

        layoutBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AttestationApplyActivity.stepIndicator.setCurrentStepPosition(4);
                } catch (Exception e) {
                }

                Global.center = " ";

                Global.courierFeeForReceipt = layoutBinding.tvCourierFee.getText().toString();
                Global.secuirtyFeeForReceipt = layoutBinding.tvSecurityFee.getText().toString();
                Global.bankChargesForReceipt = layoutBinding.tvProcessingFee.getText().toString();


                Fragment paymentFragment = new PaymentFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, paymentFragment).addToBackStack(null).commit();
            }
        });
    }

    public int DocumentPayment(int amount, int percent) {
        //Amount = amount * percent / 100;
        Amount = (amount * percent) / 100;
        //Amount = Amount + 50;

        int TotalAmount = amount + Amount;
        int documentSecurityFee = Amount;

        layoutBinding.tvProcessingFee.setText("Rs." + String.valueOf(documentSecurityFee));
        edt_Amount.setText(String.valueOf(TotalAmount));

        Global.ibccAmount = amount;    //document fee
        Global.webdocAmount = Amount;   //security fee

        return Amount;
    }

    public void No_documentSecureDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        View v = getLayoutInflater().inflate(R.layout.alert_document_secure, null);
        dialogBuilder.setView(v);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LottieAnimationView iv_User = v.findViewById(R.id.iv_User);
        final ImageView iv_cancel = v.findViewById(R.id.iv_cancel);
        TextView tv_details = v.findViewById(R.id.tv_details);

        courierFee = 200; //220
        layoutBinding.tvCourierFee.setText("Rs." + courierFee);
        tv_details.setText("If you want your documents transfer securely to IBCC then avail document security first");
        DocumentPayment(allDocumentsFee + courierFee, 3);
        iv_User.setAnimation("no-protection-shield.json");
        iv_User.loop(true);
        iv_User.playAnimation();
        layoutBinding.secureAnimation.setAnimation("no-protection-shield.json");
        layoutBinding.secureAnimation.loop(true);
        layoutBinding.secureAnimation.playAnimation();
        checkBoxState = false;

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setCancelable(false);
        alertDialog.show();
    }//no_alert

    public void documentSecureDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        View v = getLayoutInflater().inflate(R.layout.alert_document_secure, null);
        dialogBuilder.setView(v);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LottieAnimationView iv_User = v.findViewById(R.id.iv_User);
        final ImageView iv_cancel = v.findViewById(R.id.iv_cancel);
        TextView tv_details = v.findViewById(R.id.tv_details);
        tv_details.setText("Your Document is securely transfer to ibcc and after attestation it will send back to you securily. if you don't want to use this facility then please turn off switch.");

        courierFee = 200;
        layoutBinding.tvCourierFee.setText("Rs." + courierFee);
        DocumentPayment(allDocumentsFee + courierFee, 3);
        iv_User.setAnimation("9943-protection-shield.json");
        iv_User.loop(true);
        iv_User.playAnimation();
        layoutBinding.secureAnimation.setAnimation("9943-protection-shield.json");
        layoutBinding.secureAnimation.loop(true);
        layoutBinding.secureAnimation.playAnimation();
        checkBoxState = true;

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setCancelable(false);
        alertDialog.show();
    }

}