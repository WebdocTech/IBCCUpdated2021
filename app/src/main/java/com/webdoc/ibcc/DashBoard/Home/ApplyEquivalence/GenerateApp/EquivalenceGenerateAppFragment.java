package com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.GenerateApp;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.ApplyEquivalenceActivity;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.Pyament.PaymentFragment_EQ;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.PdfResult.Center;
import com.webdoc.ibcc.databinding.FragmentEquivalenceGenerateAppBinding;

public class EquivalenceGenerateAppFragment extends Fragment {
    //Spinner spinner_center;
    public static EditText edt_Amount;
    String center_name = " ";
    boolean checkBoxState = true;
    LottieAnimationView logo;
    int courierFee = 200;
    int securityFee = 50;
    int bankCharges;
    private FragmentEquivalenceGenerateAppBinding layoutBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       layoutBinding = FragmentEquivalenceGenerateAppBinding.inflate(inflater, container, false);

       //Statics
        edt_Amount = layoutBinding.edtAmount;

        calculateBankCharges(50);
        Global.webdocAmount = 50;
        int totalAmount = Global.documentsTotalFee + Global.webdocAmount
                + courierFee
                + Global.bankChargeEQ;
        layoutBinding.edtAmount.setText(String.valueOf(totalAmount));
        Global.amount = totalAmount;
        Global.ibccAmount = Global.documentsTotalFee;

        if (Global.isOnline) {
            layoutBinding.SecurityFeeLayout.setVisibility(View.GONE);
        }

        layoutBinding.tvDocumentFee.setText("Rs." + String.valueOf(Global.documentsTotalFee));
        layoutBinding.tvProcessingFee.setText("Rs." + String.valueOf(Global.bankChargeEQ));
        layoutBinding.tvCourierFee.setText("Rs." + String.valueOf(courierFee));
        layoutBinding.tvSecurityFee.setText("Rs." + securityFee);
        Global.bankChargesForReceiptEQ = String.valueOf(Global.bankChargeEQ);
        Global.courierFeeForReceiptEQ = String.valueOf(courierFee);
        Global.secuirtyFeeForReceiptEQ = String.valueOf(securityFee);
        Global.ibbcChargesForReceiptEQ = String.valueOf(Global.documentsTotalFee);

        Global.center = center_name;
        if (!Global.isIncompleteAppointmentEQ) {
            Global.caseId = String.valueOf(Global.equivalenceInitiateCaseResponse.getResult().getIntiateCaseResponseDetails().getCaseId());
        } else {
            Global.caseId = Global.caseIdQualificationEQ;
        }

        Global.equivalenceGenerateAppCenter = Global.pdfResponse.getResult().getCenters().get(0);

        clickListeners();

        return layoutBinding.getRoot();
    }

    private void clickListeners() {
        layoutBinding.tvDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //documentSecureDialog(50);
            }
        });

        layoutBinding.switchSecurityFee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == false) {   //false
                    No_documentSecureDialog(0);
                    securityFee = 0;
                    Global.webdocAmount = 0;
                    layoutBinding.tvSecurityFee.setText("Rs." + securityFee);
                    int totalAmount = Global.documentsTotalFee
                            + Global.webdocAmount + courierFee
                            + Global.bankChargeEQ;
                    layoutBinding.edtAmount.setText(String.valueOf(totalAmount));
                } else {
                    documentSecureDialog(50);
                    securityFee = 50;
                    Global.webdocAmount = 50;
                    layoutBinding.tvSecurityFee.setText("Rs." + securityFee);
                    int totalAmount = Global.documentsTotalFee + Global.webdocAmount
                            + courierFee + Global.bankChargeEQ;
                    layoutBinding.edtAmount.setText(String.valueOf(totalAmount));
                }
            }
        });

        layoutBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.center = center_name;
                ApplyEquivalenceActivity.stepIndicator.setCurrentStepPosition(5);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.equivalence_fragment_container,
                        new PaymentFragment_EQ()).commit();
            }
        });
    }

    public void No_documentSecureDialog(int webAmount) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        View v = getLayoutInflater().inflate(R.layout.alert_document_secure, null);
        dialogBuilder.setView(v);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Global.webdocAmount = ;
        calculateBankCharges(webAmount);

        logo = v.findViewById(R.id.iv_User);
        final ImageView iv_cancel = v.findViewById(R.id.iv_cancel);
        TextView tv_details = v.findViewById(R.id.tv_details);

        tv_details.setText("If you want your documents transfer securely to IBCC then avail document security first");
        logo.setAnimation("no-protection-shield.json");
        logo.loop(true);
        logo.playAnimation();
        checkBoxState = false;

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void documentSecureDialog(int webAmount) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        View v = getLayoutInflater().inflate(R.layout.alert_document_secure, null);
        dialogBuilder.setView(v);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Global.webdocAmount = 50;
        calculateBankCharges(webAmount);

        logo = v.findViewById(R.id.iv_User);
        final ImageView iv_cancel = v.findViewById(R.id.iv_cancel);
        TextView tv_details = v.findViewById(R.id.tv_details);
        tv_details.setText("Your Document is securely transfer to ibcc and after attestation it will send back to you securily. if you don't want to use this facility then please turn off switch.");

        logo.setAnimation("9943-protection-shield.json");
        logo.loop(true);
        logo.playAnimation();
        checkBoxState = true;

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setCancelable(false);
        alertDialog.show();
    }//alert

    public void calculateBankCharges(int wdAmount) {
        int Amount = Global.documentsTotalFee + wdAmount + courierFee;
        bankCharges = Amount * 3 / 100;
        layoutBinding.tvProcessingFee.setText("Rs." + String.valueOf(bankCharges));
        Global.bankChargeEQ = bankCharges;
    }

}