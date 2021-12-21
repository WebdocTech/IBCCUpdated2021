package com.webdoc.ibcc.DashBoard.Home.Payment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.webdoc.ibcc.Adapter.PaymentAdapter;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.PaymentModel;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.ActivityPaymentBinding;

public class PaymentActivity extends AppCompatActivity {
    PaymentAdapter paymentAdapter;
    private ActivityPaymentBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        clickListeners();
        setAdapter();
    }

    private void clickListeners() {
        layoutBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        layoutBinding.btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PaymentActivity.this, "Paid", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        layoutBinding.rvPaymentMethods.setLayoutManager(layoutManager);
        layoutBinding.rvPaymentMethods.setHasFixedSize(true);
        paymentAdapter = new PaymentAdapter(this);
        layoutBinding.rvPaymentMethods.setAdapter(paymentAdapter);
    }
}