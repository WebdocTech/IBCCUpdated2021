
package com.webdoc.ibcc.ResponseModels.IncompleteDetailsEQ;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Paymentinfo {

    @SerializedName("ibcc_amount")
    @Expose
    private String ibccAmount;
    @SerializedName("chalan_id")
    @Expose
    private String chalanId;
    @SerializedName("webdoc_amount")
    @Expose
    private String webdocAmount;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("is_security_check")
    @Expose
    private Boolean isSecurityCheck;
    @SerializedName("bankname")
    @Expose
    private String bankname;
    @SerializedName("paymentstatus")
    @Expose
    private String paymentstatus;
    @SerializedName("transactionid")
    @Expose
    private String transactionid;
    @SerializedName("webviewid")
    @Expose
    private String webviewid;
    @SerializedName("appointment_method")
    @Expose
    private String appointmentMethod;

    public String getIbccAmount() {
        return ibccAmount;
    }

    public void setIbccAmount(String ibccAmount) {
        this.ibccAmount = ibccAmount;
    }

    public String getChalanId() {
        return chalanId;
    }

    public void setChalanId(String chalanId) {
        this.chalanId = chalanId;
    }

    public String getWebdocAmount() {
        return webdocAmount;
    }

    public void setWebdocAmount(String webdocAmount) {
        this.webdocAmount = webdocAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getIsSecurityCheck() {
        return isSecurityCheck;
    }

    public void setIsSecurityCheck(Boolean isSecurityCheck) {
        this.isSecurityCheck = isSecurityCheck;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public String getWebviewid() {
        return webviewid;
    }

    public void setWebviewid(String webviewid) {
        this.webviewid = webviewid;
    }

    public String getAppointmentMethod() {
        return appointmentMethod;
    }

    public void setAppointmentMethod(String appointmentMethod) {
        this.appointmentMethod = appointmentMethod;
    }

}
