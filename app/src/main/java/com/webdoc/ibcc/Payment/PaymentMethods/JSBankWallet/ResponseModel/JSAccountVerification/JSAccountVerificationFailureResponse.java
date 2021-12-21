
package com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ResponseModel.JSAccountVerification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JSAccountVerificationFailureResponse {

    @SerializedName("requestId")
    @Expose
    private String requestId;
    @SerializedName("errorCode")
    @Expose
    private String errorCode;
    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
