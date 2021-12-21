
package com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ResponseModel.JsPaymentFinal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsPaymentFinal {

    @SerializedName("PaymentResponse")
    @Expose
    private PaymentResponse paymentResponse;

    public PaymentResponse getPaymentResponse() {
        return paymentResponse;
    }

    public void setPaymentResponse(PaymentResponse paymentResponse) {
        this.paymentResponse = paymentResponse;
    }

}
