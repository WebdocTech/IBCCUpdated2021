package com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.Instructions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.webdoc.ibcc.Adapter.InstructionsAdapter;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.AttestationApplyActivity;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.InstructionsModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.ActivityFormInstructionBinding;

public class FormInstructionActivity extends AppCompatActivity {
    private InstructionsAdapter instructionsAdapter;
    private ActivityFormInstructionBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityFormInstructionBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        //RecyclerView for instructions
        Global.instructionsModels.clear();
        final String[] count = {"1.", "2.", "3.", "4.", "5.", "6.", "7.", "8.", "9.", "10.", "11.", "12.", "13.", "14.", "15.", " "};
        final String[] detail = {"All certificates and diplomas along with its copies must be got verified from concerned Exam Board/ authority before submitting to IBCC for attestation. Verification should be submitted in sealed envelope of Concerned Board along with application for attestation of Certificates. Verification forwarded by every Board shall be valid for a period of three months from the date of issuance.",
                "All SSC/HSSCs/Diplomas etc. attested by IBCC before 31-12-2010 requires fresh verification.",
                "DMC/Marks sheet will be treated as valid for two years only.",
                "Original SSC (Matric) Certificate must be submitted for attestation of HSSC Certificate/Diploma.",
                "In case photo copies are required to be attested then extra copies may be provided however attestation of Original Certificate/Diploma/DMC is must.",
                "Only candidate itself and its blood relatives are authorized to deposit and collect documents.",
                "Verified copies of the Certificates /Diplomas issued by the Exam Boards will be retained by IBCC for record and will not be returned to the applicant.",
                "Attestation fee for each original certificate is Rs.400/- and for each of the copy is Rs.200/.",
                "Provide back to back photo copy of each document(s) for office record.",
                "Applications for attestation will be accepted & received from 09:00 AM to 05:00 PM for all week working days, except lunch/prayer break from 01:00 PM to 02:00 PM & on Friday from 12:30 PM to 02:00 PM.",
                "Doubtful cases will be retained for verification/ confirmation from the concerned Boards.",
                "Certificates may be collected within seven days from the date of deposit (for clear cases only), IBCC shall have no responsibility in case of misplacement, if any, after the prescribed period.",
                "Documents already attested from Regional offices of IBCC will not be attested by IBCC Islamabad.",
                "Foreign Nationals / Afghan students are required to submit copies of their passports and visa or copies of refugee card as the case may be.",
                "Get delivery of attested certificates on next working day (only clear cases) from 09:00 AM to 05:00 PM daily except lunch /prayer break 01:00 PM to 02:00 PM and on Friday except juma prayer 12:30 PM to 02:00 PM.",
                "IMPORTANT.Certificate found bogus will be forfeited be the IBCC and legal action will be taken against those who will submit bogus Certificates."};

        for (int i = 0; i < count.length; i++) {
            InstructionsModel model = new InstructionsModel(count[i], detail[i]);
            Global.instructionsModels.add(model);
        }

        setAdapter();
        clickListeners();

    }

    private void clickListeners() {
        layoutBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        layoutBinding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FormInstructionActivity.this, AttestationApplyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                finish();
                startActivity(intent);
            }
        });
    }

    private void setAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvInstructions.setLayoutManager(layoutManager);
        layoutBinding.rvInstructions.setHasFixedSize(true);
        instructionsAdapter = new InstructionsAdapter(this);
        layoutBinding.rvInstructions.setAdapter(instructionsAdapter);
    }

}