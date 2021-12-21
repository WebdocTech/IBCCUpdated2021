
package com.webdoc.ibcc.ResponseModels.ViewIncompleteDetailsResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Paymentinfo {

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
