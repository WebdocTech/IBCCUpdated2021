package com.webdoc.ibcc.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.CallCourier_EQ.CallCourier_EQ;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.PaymentModel;
import com.webdoc.ibcc.Payment.PaymentMethods.BankAlfalah.BankAlfalahAccount.BankAlfalahAccountActivity;
import com.webdoc.ibcc.Payment.PaymentMethods.BankAlfalah.BankAlfalahWallet.BankAlfalahWalletActivity;
import com.webdoc.ibcc.Payment.PaymentMethods.BankAlfalah.BankAlfalhCreditDebit.BankAlfalahCreditDebitActivity;
import com.webdoc.ibcc.Payment.PaymentMethods.EasyPaisa.EasyPaisaActivity;
import com.webdoc.ibcc.Payment.PaymentMethods.EasyPaisaCreditDebit.EasyPaisaCreditDebitActivity;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.JsMainActivities.JsBankActivity;
import com.webdoc.ibcc.Payment.PaymentMethods.JazzCash.JazzCashPaymentActivity;
import com.webdoc.ibcc.Payment.PaymentMethods.OTCPayment.OtcPaymentActivity;
import com.webdoc.ibcc.Payment.PaymentMethods.StripePayment.StripeActivity;
import com.webdoc.ibcc.R;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {
    Activity context;
    boolean checked;

    public PaymentAdapter(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_item, parent, false);
        return new PaymentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PaymentAdapter.ViewHolder holder, final int position) {
        final PaymentModel myListData = Global.paymentTitle.get(position);
        final int image = myListData.getImage();
        final String title = myListData.getTitle();

        holder.iv_payment.setImageResource(image);
        holder.tv_title.setText(title);

        if (position % 2 == 1) {
            holder.MainLayout.setBackgroundResource(R.drawable.border_rectangle_white);
        } else {
            holder.MainLayout.setBackgroundResource(R.drawable.border_rectangle_gray);
        }

        holder.MainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title.equals("Bank Alfalah Account")) {
                    Intent intent = new Intent(context, BankAlfalahAccountActivity.class);
                    context.startActivity(intent);
                } else if (title.equals("Bank Alfalah Wallet")) {
                    Intent intent = new Intent(context, BankAlfalahWalletActivity.class);
                    context.startActivity(intent);
                } else if (title.equals("Jazz Cash Wallet")) {
                    Intent intent = new Intent(context, JazzCashPaymentActivity.class);
                    context.startActivity(intent);
                } else if (title.equals("EasyPaisa Wallet")) {
                    Intent intent = new Intent(context, EasyPaisaActivity.class);
                    context.startActivity(intent);
                } else if (title.equals("Js Wallet")) {
                    Intent intent = new Intent(context, JsBankActivity.class); //webview
                    context.startActivity(intent);
                } else if (title.equals("Credit/Debit Card")) {
                    Intent intent = new Intent(context, BankAlfalahCreditDebitActivity.class);
                    context.startActivity(intent);
                } else if (title.equals("OTC Through EasyPaisa")) {
                    Intent intent = new Intent(context, OtcPaymentActivity.class);
                    context.startActivity(intent);
                } else if (title.equals("Credit/Debit")) {
                    Intent intent = new Intent(context, EasyPaisaCreditDebitActivity.class); //webview
                    context.startActivity(intent);
                } else if (title.equals("InternationalPayment")) {
                    Intent intent = new Intent(context, StripeActivity.class);
                    context.startActivity(intent);
                } else if (title.equals("temp")) {
                    Intent intent = new Intent(context, CallCourier_EQ.class);
                    intent.putExtra("appointment_method", "");
                    intent.putExtra("trx_id", "");
                    intent.putExtra("bank_name", "");
                    intent.putExtra("payment_status", "Pending");
                    context.finish();
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return Global.paymentTitle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_payment;
        public ConstraintLayout MainLayout;
        public TextView tv_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_payment = itemView.findViewById(R.id.iv_payment);
            MainLayout = itemView.findViewById(R.id.MainLayout);
            tv_title = itemView.findViewById(R.id.tv_title);
        }
    }
}
