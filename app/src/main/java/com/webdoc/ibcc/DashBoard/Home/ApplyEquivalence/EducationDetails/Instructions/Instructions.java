package com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.Instructions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.webdoc.ibcc.Adapter.InstructionsAdapter;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.InstructionsModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.ActivityInstructionsBinding;

public class Instructions extends AppCompatActivity {
    private InstructionsAdapter instructionsAdapter;
    private ActivityInstructionsBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityInstructionsBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        //RecyclerView for instructions
        Global.instructionsModels.clear();
        final String[] count = {"1.", "2.", "3.", "4.", "5.", "6.", "7.", "8."};
        final String[] detail = {"Provide: Original ''O'' and ''A\" level certificate issued by Pearson/ Cambridge University along with two attested photocopy of each certificate.(Copies attached with application must be signed by candidate/applicant himself/herself).",
                "Attested copy of C.N.I.C or Form 'B' of student.",
                "Attested photo copies of passport/visa of the student if studied from abroad.",
                "Downloaded result is not acceptable for equivalence, even if results is attested/verified by school.",
                "For equivalence of A level, provide original certificates of IGCSE/GCE ‘O’ & 'A' level issued by Pearson/ CIE /other UK exams boards, along with attested photo copies of all certificates and photo copy of equivalence certificate of O Level if available.",
                "Provisional equivalence will be issued on the basis of Statement of Results. Statement of Result is valid for 6 months only. Final Equivalence Certificate will be issued after receipt of original final certificate and submission of an application for proper equivalence certificate along with provisional equivalence and all original Certificates and photo copies of O and A level.",
                "If a candidate passes ''O'' level from abroad and appears for ''A'' level from Pakistan or vice versa, he/She has to pass Urdu , Islamiyat and Pakistan Studies at SSC or ''O'' level for obtaining Equivalence certificate.",
                "The candidate must have passed seven subjects before June 2006 & Eight subjects from June 2006 & onward of GCE ‘O’-Level, if he/she appears from Pakistan (including Mathematics, English , Urdu , Islamiyat, Pakistan Studies and Three Elective subjects) and Five Subjects(including Mathematics, English and three elective subjects) if he appears from abroad."};

        for (int i = 0; i < count.length; i++) {
            InstructionsModel model = new InstructionsModel(count[i], detail[i]);
            Global.instructionsModels.add(model);
        }

        setAdapter();
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