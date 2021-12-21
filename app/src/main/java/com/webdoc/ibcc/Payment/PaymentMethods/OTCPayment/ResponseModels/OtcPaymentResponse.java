package com.webdoc.ibcc.Payment.PaymentMethods.OTCPayment.ResponseModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtcPaymentResponse {

    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("storeId")
    @Expose
    private String storeId;
    @SerializedName("paymentToken")
    @Expose
    private String paymentToken;
    @SerializedName("transactionId")
    @Expose
    private String transactionId;
    @SerializedName("transactionDateTime")
    @Expose
    private String transactionDateTime;
    @SerializedName("paymentTokenExpiryDateTime")
    @Expose
    private String paymentTokenExpiryDateTime;
    @SerializedName("responseCode")
    @Expose
    private String responseCode;
    @SerializedName("responseDesc")
    @Expose
    private String responseDesc;
    @SerializedName("shopifyRedirectURL")
    @Expose
    private String shopifyRedirectURL;

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

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
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

    public String getPaymentTokenExpiryDateTime() {
        return paymentTokenExpiryDateTime;
    }

    public void setPaymentTokenExpiryDateTime(String paymentTokenExpiryDateTime) {
        this.paymentTokenExpiryDateTime = paymentTokenExpiryDateTime;
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

    public String getShopifyRedirectURL() {
        return shopifyRedirectURL;
    }

    public void setShopifyRedirectURL(String shopifyRedirectURL) {
        this.shopifyRedirectURL = shopifyRedirectURL;
    }

}
