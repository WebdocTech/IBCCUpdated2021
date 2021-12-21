package com.webdoc.ibcc.Payment.PaymentMethods.paymentMethodsList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.webdoc.ibcc.Payment.PaymentMethods.EasyPaisa.EasyPaisaActivity;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.ActivityPaymentMethodsListBinding;

public class PaymentMethodsListActivity extends AppCompatActivity {
    private ActivityPaymentMethodsListBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityPaymentMethodsListBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        clickListeners();
    }

    private void clickListeners() {
        layoutBinding.ccEasyPaisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentMethodsListActivity.this, EasyPaisaActivity.class);
                startActivity(intent);

            }
        });

    }
}