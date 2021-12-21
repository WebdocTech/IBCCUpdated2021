package com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.Pyament;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webdoc.ibcc.Adapter.PaymentAdapter;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.GenerateApp.GenerateAppFragment;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.PaymentModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.FragmentPaymentBinding;

public class PaymentFragment extends Fragment {
    private PaymentAdapter paymentAdapter;
    private FragmentPaymentBinding layoutBinding;

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_payment, container, false);
        layoutBinding = FragmentPaymentBinding.inflate(inflater, container, false);

       /* btn_next = view.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttestationApplyActivity.stepIndicator.setCurrentStepPosition(5);

                Fragment documentCheckListFragment = new DocumentCheckListFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, documentCheckListFragment).addToBackStack(null).commit();
            }
        });*/

        layoutBinding.tvPrice.setText(GenerateAppFragment.edt_Amount.getText().toString());
        Global.price = GenerateAppFragment.edt_Amount.getText().toString();
        String price = GenerateAppFragment.edt_Amount.getText().toString();

        setAdapter();

        Global.paymentTitle.clear();
       /* final int[] logo = {R.drawable.bank_alfalah_logo, R.drawable.bank_alfalah_logo, R.drawable.jazzcash, R.drawable.easypaisa_icon, R.drawable.js_bank_logo, R.drawable.credit, R.drawable.easypaisa_icon, R.drawable.easypaisa_icon};
        final String[] title = {"Bank Alfalah Account", "Bank Alfalah Wallet", "Jazz Cash Wallet", "Easy Paisa Wallet", "Js Wallet", "Credit/Debit Card", "Easy Paisa OTC payment", "Easy Paisa Credit Debit"};*/

        final int[] logo = {R.drawable.easypaisa_icon, R.drawable.easypaisa_icon, R.drawable.credit_card, R.drawable.js_bank_logo};
        final String[] title = {"EasyPaisa Wallet", "OTC Through EasyPaisa", "Credit/Debit", "Js Wallet"};

        for (int i = 0; i < logo.length; i++) {
            PaymentModel model = new PaymentModel(logo[i], title[i]);
            Global.paymentTitle.add(model);
        }

        return layoutBinding.getRoot();
    }

    private void setAdapter() {
        //RECYCLER VIEW
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvPaymentMethods.setLayoutManager(layoutManager);
        layoutBinding.rvPaymentMethods.setHasFixedSize(true);
        paymentAdapter = new PaymentAdapter(getActivity());
        layoutBinding.rvPaymentMethods.setAdapter(paymentAdapter);
    }

}