
package com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ResponseModel.JSAccountVerification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JSAccountVerificationSuccessResponse {

    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("ResponseDescription")
    @Expose
    private String responseDescription;
    @SerializedName("Rrn")
    @Expose
    private String rrn;
    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("Cnic")
    @Expose
    private String cnic;

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

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

}
