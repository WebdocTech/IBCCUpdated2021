package com.webdoc.ibcc.Payment.PaymentMethods.EasyPaisa.ResoponseModel.easypaisaPAymentResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EasypaisaPAymentResponse {

    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("storeId")
    @Expose
    private String storeId;
    @SerializedName("transactionId")
    @Expose
    private String transactionId;
    @SerializedName("transactionDateTime")
    @Expose
    private String transactionDateTime;
    @SerializedName("responseCode")
    @Expose
    private String responseCode;
    @SerializedName("responseDesc")
    @Expose
    private String responseDesc;

    public EasypaisaPAymentResponse(String orderId, String storeId, String transactionAmount, String transactionType, String mobileAccountNo, String emailAddress) {
    }

    public EasypaisaPAymentResponse() {

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(String transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }

}
