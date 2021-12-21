
package com.webdoc.ibcc.ResponseModels.ViewDetailsResult;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {

    @SerializedName("appointmentType")
    @Expose
    private String appointmentType;
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("consigment_id")
    @Expose
    private String consigmentId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("save_form_id")
    @Expose
    private String saveFormId;
    @SerializedName("center_id")
    @Expose
    private String centerId;
    @SerializedName("challan_id")
    @Expose
    private String challanId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("case_id")
    @Expose
    private String caseId;
    @SerializedName("consignment_no")
    @Expose
    private String consignmentNo;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("transaction_type")
    @Expose
    private String transactionType;
    @SerializedName("transaction_datetime")
    @Expose
    private String transactionDatetime;
    @SerializedName("bank")
    @Expose
    private String bank;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("ibcc_amount")
    @Expose
    private String ibccAmount;
    @SerializedName("webdoc_amount")
    @Expose
    private String webdocAmount;
    @SerializedName("cal_amount")
    @Expose
    private String calAmount;
    @SerializedName("is_security_check")
    @Expose
    private Boolean isSecurityCheck;

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

    @SerializedName("document")
    @Expose

    private List<Document> document = null;

    public String getConsigmentId() {
        return consigmentId;
    }

    public void setConsigmentId(String consigmentId) {
        this.consigmentId = consigmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getSaveFormId() {
        return saveFormId;
    }

    public void setSaveFormId(String saveFormId) {
        this.saveFormId = saveFormId;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public String getChallanId() {
        return challanId;
    }

    public void setChallanId(String challanId) {
        this.challanId = challanId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getConsignmentNo() {
        return consignmentNo;
    }

    public void setConsignmentNo(String consignmentNo) {
        this.consignmentNo = consignmentNo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionDatetime() {
        return transactionDatetime;
    }

    public void setTransactionDatetime(String transactionDatetime) {
        this.transactionDatetime = transactionDatetime;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIbccAmount() {
        return ibccAmount;
    }

    public void setIbccAmount(String ibccAmount) {
        this.ibccAmount = ibccAmount;
    }

    public String getWebdocAmount() {
        return webdocAmount;
    }

    public void setWebdocAmount(String webdocAmount) {
        this.webdocAmount = webdocAmount;
    }

    public String getCalAmount() {
        return calAmount;
    }

    public void setCalAmount(String calAmount) {
        this.calAmount = calAmount;
    }

    public Boolean getIsSecurityCheck() {
        return isSecurityCheck;
    }

    public void setIsSecurityCheck(Boolean isSecurityCheck) {
        this.isSecurityCheck = isSecurityCheck;
    }

    public List<Document> getDocument() {
        return document;
    }

    public void setDocument(List<Document> document) {
        this.document = document;
    }

}
