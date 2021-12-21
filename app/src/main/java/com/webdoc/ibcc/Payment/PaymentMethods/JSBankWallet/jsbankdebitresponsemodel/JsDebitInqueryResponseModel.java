
package com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.jsbankdebitresponsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsDebitInqueryResponseModel {

    @SerializedName("DebitInqResponse")
    @Expose
    private DebitInqResponse debitInqResponse;

    public DebitInqResponse getDebitInqResponse() {
        return debitInqResponse;
    }

    public void setDebitInqResponse(DebitInqResponse debitInqResponse) {
        this.debitInqResponse = debitInqResponse;
    }

}
