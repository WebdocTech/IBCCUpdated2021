
package com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ResponseModel.JSPeymentRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JSPaymentRequestSuccessResponse {

    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("ResponseDescription")
    @Expose
    private String responseDescription;
    @SerializedName("TransactionCode")
    @Expose
    private String transactionCode;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

}
