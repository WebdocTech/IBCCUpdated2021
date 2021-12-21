
package com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ResponseModel.PaymentInquiry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AccountInquiryResponse {

    @SerializedName("MerchantType")
    @Expose
    private String merchantType;
    @SerializedName("TraceNo")
    @Expose
    private String traceNo;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("DateTime")
    @Expose
    private String dateTime;
    @SerializedName("Charges")
    @Expose
    private String charges;
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("ResponseDetails")
    @Expose
    private List<String> responseDetails = new ArrayList<String>();

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public List<String> getResponseDetails() {
        return responseDetails;
    }

    public void setResponseDetails(List<String> responseDetails) {
        this.responseDetails = responseDetails;
    }

}
