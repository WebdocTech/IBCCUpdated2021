package com.webdoc.ibcc.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.webdoc.ibcc.Adapter.Spinner.Spinner_DocumentDetails_CertificateTypeAdapter;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.DocumentSelection.DocumentSelectionFragment;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.CertificateType;
import com.webdoc.ibcc.Model.DocumentDetailModel;
import com.webdoc.ibcc.Model.DocumentSelectionModel;
import com.webdoc.ibcc.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DocumentSelectionAdapter extends RecyclerView.Adapter<DocumentSelectionAdapter.ViewHolder> {
    Activity context;
    AlertDialog addDocumentAlertDialog;
    String document = "Please Select";
    EditText edt_Calender;
    String copies, date, cert_name, cert_Id,ticketNo,ticketDate;
    // int amount;
    final Calendar myCalendar = Calendar.getInstance();
    DocumentDetailsAdapter documentDetailsAdapter;
    //List<DocumentDetailModel> DMList = new ArrayList<>();
    EditText edt_noOfCopies, edt_Amount;
    ImageView iv_ok;

    int TotalAmount;
    int copy = 0;
    // boolean docCheck,copycheck;
    String payment;
    public final String DOCUMENT_PAYMENT = "document";
    public final String COPIES_PAYMENT = "copies";

    ArrayAdapter<CharSequence> spinner_certificate_adapter;
    int selectedIndex;
    String count;
    int countforlist;


    public DocumentSelectionAdapter(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DocumentSelectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.document_selection_item, parent, false);
        return new DocumentSelectionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DocumentSelectionAdapter.ViewHolder holder, final int position) {
        DocumentSelectionModel item = Global.selDocument.get(position);
        String certificate = item.getCertificate();
        String board = item.getBoard();
        String program = item.getProgram();
        String docId= item.getDocId();

        holder.tv_title.setText(certificate);
        holder.tv_board.setText(board);
        holder.tv_degree.setText(program);

        holder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // AddDocumentDialog(position, certificate, program,board);

                if(Global.enableAddDocument) {
                    AddDocumentDialog(position, certificate, program,board, docId, holder);
                }
                else {
                    Global.utils.showErrorSnakeBar(context, "You are not allowed to add document");
                }

            }
        });

    }//onBindView


    public void AddDocumentDialog(int myID, String certificate, String program, String board, String docId, ViewHolder holder) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        View v = context.getLayoutInflater().inflate(R.layout.alert_document_selection, null);
        dialogBuilder.setView(v);

        addDocumentAlertDialog = dialogBuilder.create();
        addDocumentAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // myID= Global.documentPosition;

        Button btn_add = v.findViewById(R.id.btn_add);
        ImageView iv_cancel = v.findViewById(R.id.iv_cancel);

        Spinner spinner_certificate = v.findViewById(R.id.spinner_certificate);
        Spinner spinner_document = v.findViewById(R.id.spinner_document);
        edt_noOfCopies = v.findViewById(R.id.edt_noOfCopies);
        edt_Amount = v.findViewById(R.id.edt_Amount);
        CheckBox checkboxCopies = v.findViewById(R.id.checkboxCopies);
        ConstraintLayout onlyCopiesLayout = v.findViewById(R.id.onlyCopiesLayout);
        edt_Calender = v.findViewById(R.id.edt_Calender);
        EditText edt_TicketNo=v.findViewById(R.id.edt_TicketNo);
        iv_ok = v.findViewById(R.id.iv_ok);

        edt_Calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        checkboxCopies.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("This only copies option is only for those applicants who have already attested their original certificates, and now want photo copies of that certificate to be attested.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                    payment = COPIES_PAYMENT;
                                    int onlyCopiesFee = 400;
                                    edt_Amount.setText(String.valueOf(onlyCopiesFee));
                                    DocSelectionPayment(onlyCopiesFee);
                                    //copycheck=true;
                                }
                            });
                    alertDialog.show();


                    onlyCopiesLayout.setVisibility(View.VISIBLE);
                    spinner_document.setEnabled(false);
                    spinner_document.setSelection(0);

                } else {
                  /*  AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("You have added copies of the certificate, but donot selected the certificate itself, which is not allowed.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();*/

                    onlyCopiesLayout.setVisibility(View.GONE);
                    spinner_document.setEnabled(true);

                    edt_Amount.getText().clear();
                    edt_noOfCopies.getText().clear();
                }
            }
        });


    //count for no.of copies
        String title = Global.educationDetail.getProgram();
        for (int i = 0; i < Global.pdfResponse.getResult().getCerftificates().size(); i++) {
            for (int j = 0; j < Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().size(); j++) {
                if (program.equalsIgnoreCase(Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().get(j).getName())) {
                    count = Global.pdfResponse.getResult().getCerftificates().get(i).getPrograms().get(j).getNoOfCopies();

                    //Toast.makeText(context, count, Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }

        //CERTIFICATE SPINNER
        List<CertificateType> listCertificate = new ArrayList<CertificateType>();
        CertificateType ct=new CertificateType();
        ct.setId("Certificate");
        ct.setName("Certificate / Sanad");
        listCertificate.add(ct);
        countforlist=Integer.parseInt(count);

        for (int i = 0; i < countforlist; i++) {
            ct=new CertificateType();
            ct.setId("Transcript"+(i+1));
            ct.setName("Result DMC "+(i+1));
            listCertificate.add(ct);
        }

        Spinner_DocumentDetails_CertificateTypeAdapter dataAdapter = new Spinner_DocumentDetails_CertificateTypeAdapter(context,R.layout.spinner_item,  listCertificate);
        spinner_certificate.setAdapter(dataAdapter);
        spinner_certificate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CertificateType ct=new CertificateType();
                ct = (CertificateType) parent.getItemAtPosition(position);
                cert_name=ct.getName();
                cert_Id=ct.getId();
                // selectedIndex= position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });


        //DOCUMENT ADAPTER
        ArrayAdapter<CharSequence> spinner_document_adapter = ArrayAdapter.createFromResource(context, R.array.title_document, R.layout.spinner_item);
        // spinner_document_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_document.setAdapter(spinner_document_adapter);

        spinner_document.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                document = (String) parent.getItemAtPosition(position);

                if (document.matches("Please Select")) {
                    edt_Amount.getText().clear();
                    edt_noOfCopies.getText().clear();
                } else {

                    payment = DOCUMENT_PAYMENT;
                    int docCopiesFee = 800;
                    edt_Amount.setText(String.valueOf(docCopiesFee));
                    DocSelectionPayment(docCopiesFee);

                    //docCheck=true;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //List<DocumentDetailModel> dd=Global.selDocument.get(myID).getDetailModelList();
                for (int i = 0; i < Global.selDocument.get(myID).getDetailModelList().size(); i++) {
                    if(Global.selDocument.get(myID).getDetailModelList().get(i).getCertificateType().equals(cert_name)) {
                        //Toast.makeText(context, "Already Selected this Document", Toast.LENGTH_LONG).show();
                        Global.utils.showErrorSnakeBar(context,"Already Selected this Document");
                        return;
                    }
                }

                if (TextUtils.isEmpty(edt_noOfCopies.getText())) {
                    edt_noOfCopies.setError("Please enter");
                    edt_noOfCopies.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(edt_Amount.getText())) {
                    edt_Amount.setError("Please enter");
                    edt_Amount.requestFocus();
                    return;
                }

               // TICKET NO & TICKET DATE
                if(TextUtils.isEmpty(edt_TicketNo.getText())){
                    ticketNo="00";
                } else{
                    ticketNo= edt_TicketNo.getText().toString();
                }

                //TICKET DATE
                if(TextUtils.isEmpty(edt_Calender.getText())){
                    ticketDate="1970-01-01";
                } else{
                    ticketDate= edt_Calender.getText().toString();
                }

                //NO. OF COPIES
                copies = edt_noOfCopies.getText().toString();

                Global.selectedDoc = myID;
                //DMList.clear();     //document Detail Model
                DocumentDetailModel documentDetailModel = new DocumentDetailModel();
                documentDetailModel.setCertificateType(cert_name);
                documentDetailModel.setCertificateTypeID(cert_Id);
                documentDetailModel.setDocumentType(document);
                documentDetailModel.setDate(ticketDate);
                documentDetailModel.setTicketNo(ticketNo);
                documentDetailModel.setTotalCopies(copies);
                documentDetailModel.setAmount(Global.amount); //GLOBAL.AMOUNT

                if(checkboxCopies.isChecked()) {
                    documentDetailModel.setOriginalIncluded("0");    //selected
                } else {
                    documentDetailModel.setOriginalIncluded("1");   //not selected
                }

                Global.DMList.add(documentDetailModel);


               /* listCertificate.remove(selectedIndex);
                dataAdapter.notifyDataSetChanged();*/

                Global.selDocument.get(myID).getDetailModelList().add(documentDetailModel);
                Global.selDocument.get(myID).getDetailModelList().get(Global.selDocument.get(myID).getDetailModelList().size()-1).setTitleCert(certificate);
                Global.selDocument.get(myID).getDetailModelList().get(Global.selDocument.get(myID).getDetailModelList().size()-1).setTitleProg(program);
                Global.selDocument.get(myID).getDetailModelList().get(Global.selDocument.get(myID).getDetailModelList().size()-1).setTitleBoard(board);
                Global.selDocument.get(myID).setTotalAmount(String.valueOf(Global.amount));
                Global.selDocument.get(myID).setDocId(docId);

                notifyDataSetChanged();

                addDocumentAlertDialog.dismiss();
            }
        });

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDocumentAlertDialog.dismiss();
            }
        });

        edt_noOfCopies.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (checkboxCopies.isChecked()) {
                    if (edt_noOfCopies.getText().toString().equalsIgnoreCase("0") || TextUtils.isEmpty(edt_noOfCopies.getText().toString())) {
                        edt_Amount.setText("Rs.400");
                    } else {
                       // edt_Amount.setText("Rs." + Integer.parseInt(edt_noOfCopies.getText().toString()) * 200);

                        int copies = Integer.parseInt(edt_noOfCopies.getText().toString()) * 400;

                        edt_Amount.setText("Rs." + copies);
                        Global.amount = copies;

                    }
                } else if (!checkboxCopies.isChecked() && !document.matches("Please Select")) {
                    if (edt_noOfCopies.getText().toString().equalsIgnoreCase("0") || TextUtils.isEmpty(edt_noOfCopies.getText().toString())) {
                        edt_Amount.setText("Rs.800");
                    } else {
                        int amount = Integer.parseInt(edt_Amount.getText().toString().replace("Rs.", ""));
                        int copies = Integer.parseInt(edt_noOfCopies.getText().toString()) * 400;
                        int total_amount = amount + copies;

                        edt_Amount.setText("Rs." + total_amount);
                        Global.amount = total_amount;

                    }
                } else if (document.matches("Please Select")) {
                    edt_Amount.setText("Rs.0");
                }
            }
        });

        addDocumentAlertDialog.show();
        addDocumentAlertDialog.setCancelable(false);
    }//alert

    public void showDatePicker() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date = sdf.format(myCalendar.getTime());
        edt_Calender.setText(date);
    }

    @Override
    public int getItemCount() {
        //return Global.documentTitle.size();
        if (Global.selDocument != null) {
            return Global.selDocument.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_board, tv_degree;
        ImageView iv_add;
        RecyclerView rv_subDocument;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_board = itemView.findViewById(R.id.tv_board);
            tv_degree = itemView.findViewById(R.id.tv_degree);
            iv_add = itemView.findViewById(R.id.iv_add);
            rv_subDocument = itemView.findViewById(R.id.rv_subDocument);
        }
    }

    public int DocSelectionPayment(int Amount) {
        switch (payment) {
            case COPIES_PAYMENT:
                // copy = Integer.parseInt(edt_noOfCopies.getText().toString());
                copy = 1;
                edt_noOfCopies.setText(String.valueOf(copy));

                TotalAmount = (copy * 400);                 //(1*200)=200
                edt_Amount.setText("Rs." + String.valueOf(TotalAmount));


                Global.amount = TotalAmount;
                break;

            case DOCUMENT_PAYMENT:
                // dCopy = Integer.parseInt(edt_noOfCopies.getText().toString());
                copy = 0;
                edt_noOfCopies.setText(String.valueOf(copy));

                TotalAmount = Amount + (copy * 400);
                edt_Amount.setText("Rs." + String.valueOf(TotalAmount));

                Global.amount = TotalAmount;
                break;
        }

        return TotalAmount;
    }

}
