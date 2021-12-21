package com.webdoc.ibcc.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.DocumentSelection.DocDetailEQ_Model;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.DocumentSelection.EquivalenceDocumentSelectionFragment;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.AddQualificationModel;
import com.webdoc.ibcc.R;

import java.util.ArrayList;
import java.util.Arrays;

public class EquivalenceDocumentSelectionAdapter extends RecyclerView.Adapter<EquivalenceDocumentSelectionAdapter.ViewHolder> {
    Activity context;
    boolean[] urgentCheckedArray;
    String[] documentTypeArray;
    String docType;

    public EquivalenceDocumentSelectionAdapter(Activity context) {
        this.context = context;
        urgentCheckedArray = new boolean[Global.equivalenceQualificationList.size()];
        documentTypeArray = new String[Global.equivalenceQualificationList.size()];
        Global.documentAmountArray = new int[Global.equivalenceQualificationList.size()];
        Arrays.fill(Global.documentAmountArray, 3000);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.equivalence_document_selection_item, parent, false);
        return new EquivalenceDocumentSelectionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EquivalenceDocumentSelectionAdapter.ViewHolder holder, final int position) {
        AddQualificationModel qualificationModelItem = Global.equivalenceQualificationList.get(position);

        holder.tv_title.setText(qualificationModelItem.getQualification().getName());
        //holder.tv_eoq.setText();
        holder.tv_group.setText("Group: " + qualificationModelItem.getGroup().getName());
        holder.tv_examiningBody.setText("Examining Body: " + qualificationModelItem.getExaminingBody().getName());
        holder.tv_eoc_diplomas.setText("Certificates/Diplomas: " + "Foreign");
        // holder.tv_eoc_diplomas_type.setText("Certificates/Diplomas Type: "+"With Marks");

        if (holder.cb_urgent.isChecked()) {
            Global.documentUrgentCheck = 1; //true
        } else {
            Global.documentUrgentCheck = 0; //false
        }

        if (!Global.checkMarks) {
            docType = "Without Marks";
            holder.tv_eoc_diplomas_type.setText("Certificates/Diplomas Type: " + "Without Marks");
        } else {
            docType = "With marks";
            holder.tv_eoc_diplomas_type.setText("Certificates/Diplomas Type: " + "With Marks");
        }

        //setting MARKS from previous screen
        int finalObtainedMarks = 0;
        int finalTotalMarks = 0;
        int obtainedMarks = 0;
        int totalMarks = 0;
        int percent = 0;
        //   int percent= obtainedMarks / totalMarks;
        ArrayAdapter<CharSequence> spinner_document_type_adapter = ArrayAdapter.createFromResource(context, R.array.document_type_array, R.layout.spinner_item);
        holder.spinner_document_type.setAdapter(spinner_document_type_adapter);
        // saving in model class

        if (Global.isIncompleteAppointmentEQ) {
            DocDetailEQ_Model docDetailEQ_model = new DocDetailEQ_Model();
            docDetailEQ_model.setCaseID(Global.deleteParams.get(position).getCaseId());

            docDetailEQ_model.setDocId(Global.deleteParams.get(position).getDocId());
            docDetailEQ_model.setAmount(Integer.parseInt(String.valueOf(holder.et_amount.getText())));
            docDetailEQ_model.setDocumentType(docType);
            docDetailEQ_model.setDocumentType(holder.spinner_document_type.getItemAtPosition(0).toString());

            docDetailEQ_model.setQofCert(holder.tv_eoc_diplomas.getText().toString());
            docDetailEQ_model.setqOfCertType(holder.tv_eoc_diplomas_type.getText().toString());
            docDetailEQ_model.setUrgent(Global.documentUrgentCheck);

            docDetailEQ_model.setfObtMarks(finalObtainedMarks);
            docDetailEQ_model.setfTotalMarks(finalTotalMarks);
            docDetailEQ_model.setObtainedMarks(obtainedMarks);
            docDetailEQ_model.setTotalMarks(totalMarks);
            docDetailEQ_model.setPercentage(percent);
            Global.eqModel.add(docDetailEQ_model);
        } else {
            DocDetailEQ_Model docDetailEQ_model = new DocDetailEQ_Model();
            docDetailEQ_model.setCaseID(Global.deleteParams.get(position).getCaseId());

            docDetailEQ_model.setDocId(Global.deleteParams.get(position).getDocId());
            docDetailEQ_model.setAmount(Integer.parseInt(String.valueOf(holder.et_amount.getText())));
            // Global.eqModel.setAmount(Global.documentsTotalFee);
            docDetailEQ_model.setDocumentType(docType);
            docDetailEQ_model.setDocumentType(holder.spinner_document_type.getItemAtPosition(0).toString());

            docDetailEQ_model.setQofCert(holder.tv_eoc_diplomas.getText().toString());
            docDetailEQ_model.setqOfCertType(holder.tv_eoc_diplomas_type.getText().toString());
            docDetailEQ_model.setUrgent(Global.documentUrgentCheck);

            docDetailEQ_model.setfObtMarks(finalObtainedMarks);
            docDetailEQ_model.setfTotalMarks(finalTotalMarks);
            docDetailEQ_model.setObtainedMarks(obtainedMarks);
            docDetailEQ_model.setTotalMarks(totalMarks);
            docDetailEQ_model.setPercentage(percent);
            Global.eqModel.add(docDetailEQ_model);
        }


        //SPINNER DOCUMENT TYPE


        holder.spinner_document_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                documentTypeArray[position] = (String) parent.getItemAtPosition(pos);
                Global.eqModel.get(position).setDocumentType(holder.spinner_document_type.getItemAtPosition(pos).toString());
                switch (documentTypeArray[position]) {
                    case "New/First Time":
                        if (urgentCheckedArray[position]) {
                            holder.et_amount.setText("6000");
                            Global.eqModel.get(position).setAmount(6000);
                            Global.documentAmountArray[position] = 6000;
                        } else {
                            holder.et_amount.setText("3000");
                            Global.eqModel.get(position).setAmount(3000);
                            Global.documentAmountArray[position] = 3000;
                        }
                        break;

                    case "Duplicate":
                        if (urgentCheckedArray[position]) {
                            holder.et_amount.setText("12000");
                            Global.eqModel.get(position).setAmount(12000);
                            Global.documentAmountArray[position] = 12000;
                        } else {
                            holder.et_amount.setText("6000");
                            Global.eqModel.get(position).setAmount(6000);
                            Global.documentAmountArray[position] = 6000;
                        }
                        break;

                    case "1st Revision":
                        if (urgentCheckedArray[position]) {
                            holder.et_amount.setText("12000");
                            Global.documentAmountArray[position] = 12000;
                            Global.eqModel.get(position).setAmount(12000);
                        } else {
                            holder.et_amount.setText("6000");
                            Global.documentAmountArray[position] = 6000;
                            Global.eqModel.get(position).setAmount(6000);
                        }
                        break;

                    case "2nd Revision":
                        if (urgentCheckedArray[position]) {
                            holder.et_amount.setText("18000");
                            Global.documentAmountArray[position] = 18000;
                            Global.eqModel.get(position).setAmount(18000);
                        } else {
                            holder.et_amount.setText("9000");
                            Global.documentAmountArray[position] = 9000;
                            Global.eqModel.get(position).setAmount(9000);
                        }
                        break;

                    case "3rd Revision":
                        if (urgentCheckedArray[position]) {
                            holder.et_amount.setText("24000");
                            Global.documentAmountArray[position] = 24000;
                            Global.eqModel.get(position).setAmount(24000);
                        } else {
                            holder.et_amount.setText("12000");
                            Global.documentAmountArray[position] = 12000;
                            Global.eqModel.get(position).setAmount(12000);
                        }
                        break;
                }

                /*EquivalenceDocumentSelectionFragment equivalenceDocumentSelectionFragment = new EquivalenceDocumentSelectionFragment();
                equivalenceDocumentSelectionFragment.totalAmount();*/
                totalAmount();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        holder.cb_urgent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                urgentCheckedArray[position] = b;

                if (b) {
                    switch (documentTypeArray[position]) {
                        case "New/First Time":
                            holder.et_amount.setText("6000");
                            Global.documentAmountArray[position] = 6000;
                            Global.eqModel.get(position).setAmount(6000);
                            break;

                        case "Duplicate":

                        case "1st Revision":
                            holder.et_amount.setText("12000");
                            Global.documentAmountArray[position] = 12000;
                            Global.eqModel.get(position).setAmount(12000);
                            break;

                        case "2nd Revision":
                            holder.et_amount.setText("18000");
                            Global.documentAmountArray[position] = 18000;
                            Global.eqModel.get(position).setAmount(18000);
                            break;

                        case "3rd Revision":
                            holder.et_amount.setText("24000");
                            Global.documentAmountArray[position] = 24000;
                            Global.eqModel.get(position).setAmount(24000);
                            break;
                    }

                } else {
                    switch (documentTypeArray[position]) {
                        case "New/First Time":
                            holder.et_amount.setText("3000");
                            Global.documentAmountArray[position] = 3000;
                            Global.eqModel.get(position).setAmount(3000);
                            break;

                        case "Duplicate":

                        case "1st Revision":
                            holder.et_amount.setText("6000");
                            Global.documentAmountArray[position] = 6000;
                            Global.eqModel.get(position).setAmount(6000);
                            break;

                        case "2nd Revision":
                            holder.et_amount.setText("9000");
                            Global.documentAmountArray[position] = 9000;
                            Global.eqModel.get(position).setAmount(9000);
                            break;

                        case "3rd Revision":
                            holder.et_amount.setText("12000");
                            Global.documentAmountArray[position] = 12000;
                            Global.eqModel.get(position).setAmount(12000);
                            break;
                    }
                }

                /*EquivalenceDocumentSelectionFragment equivalenceDocumentSelectionFragment = new EquivalenceDocumentSelectionFragment();
                equivalenceDocumentSelectionFragment.totalAmount();*/
                totalAmount();
            }
        });
    }

    @Override
    public int getItemCount() {
        return Global.equivalenceQualificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title, tv_eoq, tv_group, tv_examiningBody, tv_eoc_diplomas, tv_eoc_diplomas_type;
        Spinner spinner_document_type;
        CheckBox cb_urgent;
        EditText et_amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_eoq = itemView.findViewById(R.id.tv_eoq);
            tv_group = itemView.findViewById(R.id.tv_group);
            tv_examiningBody = itemView.findViewById(R.id.tv_examiningBody);
            tv_eoc_diplomas = itemView.findViewById(R.id.tv_eoc_diplomas);
            tv_eoc_diplomas_type = itemView.findViewById(R.id.tv_eoc_diplomas_type);
            spinner_document_type = itemView.findViewById(R.id.spinner_document_type);
            cb_urgent = itemView.findViewById(R.id.cb_urgent);
            et_amount = itemView.findViewById(R.id.et_amount);
        }
    }

    public void totalAmount() {
        int amount = 0;
        for (int i = 0; i < Global.documentAmountArray.length; i++) {
            amount = amount + Global.documentAmountArray[i];
            Log.e("AMOUNT", String.valueOf(Global.documentAmountArray[i]));
            EquivalenceDocumentSelectionFragment.tvTotalAmount.setText(String.valueOf(amount));
        }
    }
}
