
package com.webdoc.ibcc.ResponseModels.ViewAppointmentsEQ;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewAppointmentsEQD {


    @SerializedName("appointmentType")
    @Expose
    private String appointmentType;

    @SerializedName("orderId")
    @Expose
    private String orderId;

    @SerializedName("bankcharges")
    @Expose
    private String bankcharges;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("appointmentStatus")
    @Expose
    private String appointmentStatus;
    @SerializedName("center")
    @Expose
    private String center;
    @SerializedName("webdoccharges")
    @Expose
    private String webdoccharges;
    @SerializedName("ibccamount")
    @Expose
    private String ibccamount;
    @SerializedName("caseid")
    @Expose
    private String caseid;
    @SerializedName("transactionid")
    @Expose
    private String transactionid;
    @SerializedName("paidthrough")
    @Expose
    private String paidthrough;
    @SerializedName("consignmentno")
    @Expose
    private String consignmentno;
    @SerializedName("paymentstatus")
    @Expose
    private String paymentstatus;
    @SerializedName("paidamount")
    @Expose
    private String paidamount;
    @SerializedName("challanid")
    @Expose
    private String challanid;
    @SerializedName("webview_id")
    @Expose
    private String webviewId;

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @SerializedName("viewAppointmentsEQDetails")
    @Expose

    private List<ViewAppointmentsEQDetail> viewAppointmentsEQDetails = null;

    public String getBankcharges() {
        return bankcharges;
    }

    public void setBankcharges(String bankcharges) {
        this.bankcharges = bankcharges;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getWebdoccharges() {
        return webdoccharges;
    }

    public void setWebdoccharges(String webdoccharges) {
        this.webdoccharges = webdoccharges;
    }

    public String getIbccamount() {
        return ibccamount;
    }

    public void setIbccamount(String ibccamount) {
        this.ibccamount = ibccamount;
    }

    public String getCaseid() {
        return caseid;
    }

    public void setCaseid(String caseid) {
        this.caseid = caseid;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public String getPaidthrough() {
        return paidthrough;
    }

    public void setPaidthrough(String paidthrough) {
        this.paidthrough = paidthrough;
    }

    public String getConsignmentno() {
        return consignmentno;
    }

    public void setConsignmentno(String consignmentno) {
        this.consignmentno = consignmentno;
    }

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
    }

    public String getPaidamount() {
        return paidamount;
    }

    public void setPaidamount(String paidamount) {
        this.paidamount = paidamount;
    }

    public String getChallanid() {
        return challanid;
    }

    public void setChallanid(String challanid) {
        this.challanid = challanid;
    }

    public String getWebviewId() {
        return webviewId;
    }

    public void setWebviewId(String webviewId) {
        this.webviewId = webviewId;
    }

    public List<ViewAppointmentsEQDetail> getViewAppointmentsEQDetails() {
        return viewAppointmentsEQDetails;
    }

    public void setViewAppointmentsEQDetails(List<ViewAppointmentsEQDetail> viewAppointmentsEQDetails) {
        this.viewAppointmentsEQDetails = viewAppointmentsEQDetails;
    }

}
