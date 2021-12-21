package com.webdoc.ibcc.DashBoard.Appointment;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.webdoc.ibcc.Adapter.IncompleteAppointmentAdapter;
import com.webdoc.ibcc.Adapter.TabLayoutAdapter;
import com.webdoc.ibcc.DashBoard.Account.Appointment.AttestationAppointment.AttestationAppointmentList_Frag;
import com.webdoc.ibcc.DashBoard.Account.Appointment.EquivalenceAppointment.EquivalenceAppointmentList_Frag;
import com.webdoc.ibcc.DashBoard.Dashboard;
import com.webdoc.ibcc.DashBoard.Faq.FaqsFrag;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.IncompleteAppointmentModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.FragmentIncompleteAppointmentBinding;

public class IncompleteAppointmentFrag extends Fragment {
    private FragmentIncompleteAppointmentBinding layoutBinding;

    public IncompleteAppointmentFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutBinding = FragmentIncompleteAppointmentBinding.inflate(inflater, container, false);

        //setUp viewpager adapter:
        setAdapter();

        return layoutBinding.getRoot();
    }

    private void setAdapter() {
        layoutBinding.tablayout.setupWithViewPager(layoutBinding.pager);
        TabLayoutAdapter adapter = new TabLayoutAdapter(getChildFragmentManager());
        layoutBinding.tablayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.gray));
        adapter.addFrag(new IncompleteAppt_Attest(), "Attestation");
        adapter.addFrag(new IncompleteAppt_Eq(), "Equivalence");
        layoutBinding.pager.setAdapter(adapter);
    }
}