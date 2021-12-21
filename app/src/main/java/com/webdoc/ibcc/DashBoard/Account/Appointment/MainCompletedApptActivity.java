package com.webdoc.ibcc.DashBoard.Account.Appointment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.webdoc.ibcc.Adapter.CompletedAppointmentAdapter;
import com.webdoc.ibcc.Adapter.CompletedApptEQtAdapter;
import com.webdoc.ibcc.Adapter.TabLayoutAdapter;
import com.webdoc.ibcc.DashBoard.Account.Appointment.AttestationAppointment.AttestationAppointmentList_Frag;
import com.webdoc.ibcc.DashBoard.Account.Appointment.AttestationAppointment.CompletedAppointment;
import com.webdoc.ibcc.DashBoard.Account.Appointment.EquivalenceAppointment.EquivalenceAppointmentList_Frag;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.CancelAppointmentResult.CancelAppointmentResult;
import com.webdoc.ibcc.ResponseModels.ViewAppointmentsEQ.ViewAppointmentsEQ;
import com.webdoc.ibcc.ResponseModels.ViewDetailsResult.ViewDetailsResult;
import com.webdoc.ibcc.ServerManager.VolleyListener;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.ActivityMainCompletedApptBinding;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainCompletedApptActivity extends AppCompatActivity /*implements VolleyListener*/ {
    private ActivityMainCompletedApptBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityMainCompletedApptBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        layoutBinding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setUpTabLayout();
    }

    private void setUpTabLayout() {
        layoutBinding.tablayout.setupWithViewPager(layoutBinding.pager);
        TabLayoutAdapter adapter = new TabLayoutAdapter(getSupportFragmentManager());
        layoutBinding.tablayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.gray));
        adapter.addFrag(new AttestationAppointmentList_Frag(), "Attestation");
        adapter.addFrag(new EquivalenceAppointmentList_Frag(), "Equivalence");
        layoutBinding.pager.setAdapter(adapter);
    }
}