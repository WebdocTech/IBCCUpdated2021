
package com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.jsDebitPaymentModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsDebitPaymentResponseModel {

    @SerializedName("DebitPaymentResponse")
    @Expose
    private DebitPaymentResponse debitPaymentResponse;

    public DebitPaymentResponse getDebitPaymentResponse() {
        return debitPaymentResponse;
    }

    public void setDebitPaymentResponse(DebitPaymentResponse debitPaymentResponse) {
        this.debitPaymentResponse = debitPaymentResponse;
    }

}
