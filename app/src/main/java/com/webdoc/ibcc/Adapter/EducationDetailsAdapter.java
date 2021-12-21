package com.webdoc.ibcc.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.webdoc.ItemClickListeners;
import com.webdoc.ibcc.Adapter.Spinner.SpinnerBoardAdapter;
import com.webdoc.ibcc.Adapter.Spinner.SpinnerCertificateAdapter;
import com.webdoc.ibcc.Adapter.Spinner.Spinner_Cert_GroupAdapter;
import com.webdoc.ibcc.Adapter.Spinner.Spinner_Cert_ProgramAdapter;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.AddEducationResult.EducationDetail;
import com.webdoc.ibcc.ResponseModels.PdfResult.Board;
import com.webdoc.ibcc.ResponseModels.PdfResult.Cerftificate;
import com.webdoc.ibcc.ResponseModels.PdfResult.Program;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;

import java.util.ArrayList;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EducationDetailsAdapter extends RecyclerView.Adapter<EducationDetailsAdapter.ViewHolder> {
    Activity context;
    String board_name, board_Id, group_name, group_Id, program_name, program_Id, cert_name, cert_Id, passing_year, session,
            marks_obtained, total_marks, roll_no;
    VolleyRequestController volleyRequestController;
    private ItemClickListeners itemClickListeners;

    public EducationDetailsAdapter(Activity context) {
        this.context = context;
        volleyRequestController = new VolleyRequestController(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.education_details_item, parent, false);
        return new EducationDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EducationDetailsAdapter.ViewHolder holder, final int position) {
        EducationDetail myListData = Global.addEducationResponse.getResult().getEducationDetails().get(position);
        holder.tv_certificate.setText(myListData.getCertificate());
        holder.tv_program.setText(myListData.getProgram());
        holder.tv_group.setText(myListData.getGroup());
        holder.tv_board.setText(myListData.getBoard());
        holder.tv_passingYear.setText(myListData.getYear());
        holder.tv_marksObtained.setText(myListData.getMarks());
        holder.tv_totalMarks.setText(myListData.getTotal());

        if (Global.isIncompleteAppointment) {
            holder.iv_edit.setVisibility(View.INVISIBLE);
        } else {
            holder.iv_edit.setVisibility(View.VISIBLE);
        }

        holder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListeners != null) {
                    itemClickListeners.onClick(view, position, "mEdit");
                }
            }
        });

        holder.iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListeners != null) {
                    itemClickListeners.onClick(view, position, "mRemove");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //return Global.educationDetailsList.size();
        if (Global.addEducationResponse.getResult() != null) {
            if (Global.addEducationResponse.getResult().getEducationDetails().size() > 0) {
                return Global.addEducationResponse.getResult().getEducationDetails().size();
            } else {
                return 0;
            }
        }
        return 0;
    }

    public void setItemClickListeners(ItemClickListeners itemClickListeners) {
        this.itemClickListeners = itemClickListeners;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_certificate, tv_program, tv_group, tv_board, tv_passingYear, tv_marksObtained, tv_totalMarks;
        ImageView iv_edit, iv_remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_certificate = itemView.findViewById(R.id.tv_certificateValue);
            tv_program = itemView.findViewById(R.id.tv_programValue);
            tv_group = itemView.findViewById(R.id.tv_groupValue);
            tv_board = itemView.findViewById(R.id.tv_boardValue);
            tv_passingYear = itemView.findViewById(R.id.tv_passingYearValue);
            tv_marksObtained = itemView.findViewById(R.id.tv_marksObtainedValue);
            tv_totalMarks = itemView.findViewById(R.id.tv_totalMarksValue);
            iv_edit = itemView.findViewById(R.id.iv_edit);
            iv_remove = itemView.findViewById(R.id.iv_remove);
        }
    }

}
