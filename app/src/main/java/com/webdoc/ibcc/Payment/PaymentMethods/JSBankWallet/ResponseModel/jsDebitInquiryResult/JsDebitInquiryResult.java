
package com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ResponseModel.jsDebitInquiryResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsDebitInquiryResult {

    @SerializedName("processingCode")
    @Expose
    private String processingCode;
    @SerializedName("merchantType")
    @Expose
    private String merchantType;
    @SerializedName("traceNo")
    @Expose
    private String traceNo;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("dateTime")
    @Expose
    private String dateTime;
    @SerializedName("responseCode")
    @Expose
    private String responseCode;
    @SerializedName("responseDetails")
    @Expose
    private List<String> responseDetails = null;
    @SerializedName("rrn")
    @Expose
    private String rrn;
    @SerializedName("responseDescription")
    @Expose
    private String responseDescription;

    public String getProcessingCode() {
        return processingCode;
    }

    public void setProcessingCode(String processingCode) {
        this.processingCode = processingCode;
    }

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

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

}
