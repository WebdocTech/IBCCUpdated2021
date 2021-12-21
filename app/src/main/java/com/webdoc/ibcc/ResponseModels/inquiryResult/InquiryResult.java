
package com.webdoc.ibcc.ResponseModels.inquiryResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class InquiryResult {

    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("accountNum")
    @Expose
    private String accountNum;
    @SerializedName("storeId")
    @Expose
    private Integer storeId;
    @SerializedName("storeName")
    @Expose
    private String storeName;
    @SerializedName("paymentToken")
    @Expose
    private String paymentToken;
    @SerializedName("transactionStatus")
    @Expose
    private String transactionStatus;
    @SerializedName("transactionAmount")
    @Expose
    private Double transactionAmount;
    @SerializedName("transactionDateTime")
    @Expose
    private String transactionDateTime;
    @SerializedName("paymentTokenExpiryDateTime")
    @Expose
    private String paymentTokenExpiryDateTime;
    @SerializedName("msisdn")
    @Expose
    private String msisdn;
    @SerializedName("paymentMode")
    @Expose
    private String paymentMode;
    @SerializedName("responseCode")
    @Expose
    private String responseCode;
    @SerializedName("responseDesc")
    @Expose
    private String responseDesc;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(String transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public String getPaymentTokenExpiryDateTime() {
        return paymentTokenExpiryDateTime;
    }

    public void setPaymentTokenExpiryDateTime(String paymentTokenExpiryDateTime) {
        this.paymentTokenExpiryDateTime = paymentTokenExpiryDateTime;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
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
