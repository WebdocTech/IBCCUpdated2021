
package com.webdoc.ibcc.Payment.PaymentMethods.JsBankAccount.ResponseModels.JsGenerateOtpResponse;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsGenerateOtpResponse {

    @SerializedName("MTI")
    @Expose
    private String mti;
    @SerializedName("ProcessingCode")
    @Expose
    private String processingCode;
    @SerializedName("TransmissionDatetime")
    @Expose
    private String transmissionDatetime;
    @SerializedName("SystemsTraceAuditNumber")
    @Expose
    private String systemsTraceAuditNumber;
    @SerializedName("TimeLocalTransaction")
    @Expose
    private String timeLocalTransaction;
    @SerializedName("DateLocalTransaction")
    @Expose
    private String dateLocalTransaction;
    @SerializedName("MerchantType")
    @Expose
    private String merchantType;
    @SerializedName("CNIC")
    @Expose
    private String cnic;
    @SerializedName("AccountIdentification1")
    @Expose
    private String accountIdentification1;
    @SerializedName("TransactionType")
    @Expose
    private String transactionType;
    @SerializedName("TransactionDescription")
    @Expose
    private String transactionDescription;
    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("ResponseDescription")
    @Expose
    private String responseDescription;

    public String getMti() {
        return mti;
    }

    public void setMti(String mti) {
        this.mti = mti;
    }

    public String getProcessingCode() {
        return processingCode;
    }

    public void setProcessingCode(String processingCode) {
        this.processingCode = processingCode;
    }

    public String getTransmissionDatetime() {
        return transmissionDatetime;
    }

    public void setTransmissionDatetime(String transmissionDatetime) {
        this.transmissionDatetime = transmissionDatetime;
    }

    public String getSystemsTraceAuditNumber() {
        return systemsTraceAuditNumber;
    }

    public void setSystemsTraceAuditNumber(String systemsTraceAuditNumber) {
        this.systemsTraceAuditNumber = systemsTraceAuditNumber;
    }

    public String getTimeLocalTransaction() {
        return timeLocalTransaction;
    }

    public void setTimeLocalTransaction(String timeLocalTransaction) {
        this.timeLocalTransaction = timeLocalTransaction;
    }

    public String getDateLocalTransaction() {
        return dateLocalTransaction;
    }

    public void setDateLocalTransaction(String dateLocalTransaction) {
        this.dateLocalTransaction = dateLocalTransaction;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getAccountIdentification1() {
        return accountIdentification1;
    }

    public void setAccountIdentification1(String accountIdentification1) {
        this.accountIdentification1 = accountIdentification1;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

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

}
