package com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.ChooseEqu_Method;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.ApplyEquivalenceActivity;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.PersonalInfo.EquivalencePersonalInfoFragment;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.FragmentEquivalenceMethodBinding;

public class EquivalenceMethodFragment extends Fragment {
    private FragmentEquivalenceMethodBinding layoutBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutBinding = FragmentEquivalenceMethodBinding.inflate(inflater, container, false);

        clickListeners();

        return layoutBinding.getRoot();
    }

    private void clickListeners() {
        layoutBinding.applyOnlineCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.equivalenceOnline = true;
                Global.isOnline = true;
                Global.eqType = "2";
                layoutBinding.tvDetails.setText(getActivity().getResources().getString(R.string.online_details_text));
                layoutBinding.ivOnline.setBackgroundColor(getResources().getColor(R.color.myAppSecondColor));
                layoutBinding.ivCourier.setBackgroundColor(getResources().getColor(R.color.gray));
            }
        });

        layoutBinding.courierCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.equivalenceOnline = false;
                Global.isOnline = false;
                Global.eqType = "1";
                layoutBinding.tvDetails.setText(getActivity().getResources().getString(R.string.courier_details_text));
                layoutBinding.ivCourier.setBackgroundColor(getResources().getColor(R.color.myAppSecondColor));
                layoutBinding.ivOnline.setBackgroundColor(getResources().getColor(R.color.gray));
            }
        });

        layoutBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplyEquivalenceActivity.stepIndicator.setCurrentStepPosition(1);

                Fragment fragment = new EquivalencePersonalInfoFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.equivalence_fragment_container,
                        fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (Global.equivalenceOnline) {
            layoutBinding.ivOnline.setBackgroundColor(getResources().getColor(R.color.myAppSecondColor));
            layoutBinding.ivCourier.setBackgroundColor(getResources().getColor(R.color.gray));
        } else {
            layoutBinding.ivCourier.setBackgroundColor(getResources().getColor(R.color.myAppSecondColor));
            layoutBinding.ivOnline.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }
}