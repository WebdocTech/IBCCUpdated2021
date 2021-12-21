package com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.Pyament;

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
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.GenerateApp.EquivalenceGenerateAppFragment;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.PaymentModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.FragmentPaymentBinding;

public class PaymentFragment_EQ extends Fragment {
    PaymentAdapter paymentAdapter;
    private FragmentPaymentBinding layoutBinding;

    public PaymentFragment_EQ() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutBinding = FragmentPaymentBinding.inflate(inflater, container, false);

        if (EquivalenceGenerateAppFragment.edt_Amount.getText().toString() != null) {
            String amount = EquivalenceGenerateAppFragment.edt_Amount.getText().toString();
            layoutBinding.tvPrice.setText(amount);
            Global.price = amount;
        }


        //RECYCLER VIEW
        setAdapter();

        Global.paymentTitle.clear();

        final int[] logo = {R.drawable.easypaisa_icon, R.drawable.easypaisa_icon, R.drawable.credit_card, R.drawable.js_bank_logo};
        final String[] title = {"EasyPaisa Wallet", "OTC Through EasyPaisa", "Credit/Debit", "Js Wallet"};


        for (int i = 0; i < logo.length; i++) {
            PaymentModel model = new PaymentModel(logo[i], title[i]);
            Global.paymentTitle.add(model);
        }

        return layoutBinding.getRoot();
    }

    private void setAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvPaymentMethods.setLayoutManager(layoutManager);
        layoutBinding.rvPaymentMethods.setHasFixedSize(true);
        paymentAdapter = new PaymentAdapter(getActivity());
        layoutBinding.rvPaymentMethods.setAdapter(paymentAdapter);
    }

}