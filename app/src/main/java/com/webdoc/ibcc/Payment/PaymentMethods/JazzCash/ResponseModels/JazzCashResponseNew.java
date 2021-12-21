package com.webdoc.ibcc.Payment.PaymentMethods.JazzCash.ResponseModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JazzCashResponseNew {

    @SerializedName("pp_Amount")
    @Expose
    private String ppAmount;
    @SerializedName("pp_AuthCode")
    @Expose
    private String ppAuthCode;
    @SerializedName("pp_BillReference")
    @Expose
    private String ppBillReference;
    @SerializedName("pp_Language")
    @Expose
    private String ppLanguage;
    @SerializedName("pp_MerchantID")
    @Expose
    private String ppMerchantID;
    @SerializedName("pp_ResponseCode")
    @Expose
    private String ppResponseCode;
    @SerializedName("pp_ResponseMessage")
    @Expose
    private String ppResponseMessage;
    @SerializedName("pp_RetreivalReferenceNo")
    @Expose
    private String ppRetreivalReferenceNo;
    @SerializedName("pp_SubMerchantID")
    @Expose
    private String ppSubMerchantID;
    @SerializedName("pp_TxnCurrency")
    @Expose
    private String ppTxnCurrency;
    @SerializedName("pp_TxnDateTime")
    @Expose
    private String ppTxnDateTime;
    @SerializedName("pp_TxnRefNo")
    @Expose
    private String ppTxnRefNo;
    @SerializedName("pp_MobileNumber")
    @Expose
    private String ppMobileNumber;
    @SerializedName("pp_CNIC")
    @Expose
    private String ppCNIC;
    @SerializedName("pp_DiscountedAmount")
    @Expose
    private String ppDiscountedAmount;
    @SerializedName("ppmpf_1")
    @Expose
    private String ppmpf1;
    @SerializedName("ppmpf_2")
    @Expose
    private String ppmpf2;
    @SerializedName("ppmpf_3")
    @Expose
    private String ppmpf3;
    @SerializedName("ppmpf_4")
    @Expose
    private String ppmpf4;
    @SerializedName("ppmpf_5")
    @Expose
    private String ppmpf5;
    @SerializedName("pp_SecureHash")
    @Expose
    private String ppSecureHash;


    public String getPpAmount() {
        return ppAmount;
    }

    public void setPpAmount(String ppAmount) {
        this.ppAmount = ppAmount;
    }

    public String getPpAuthCode() {
        return ppAuthCode;
    }

    public void setPpAuthCode(String ppAuthCode) {
        this.ppAuthCode = ppAuthCode;
    }

    public String getPpBillReference() {
        return ppBillReference;
    }

    public void setPpBillReference(String ppBillReference) {
        this.ppBillReference = ppBillReference;
    }

    public String getPpLanguage() {
        return ppLanguage;
    }

    public void setPpLanguage(String ppLanguage) {
        this.ppLanguage = ppLanguage;
    }

    public String getPpMerchantID() {
        return ppMerchantID;
    }

    public void setPpMerchantID(String ppMerchantID) {
        this.ppMerchantID = ppMerchantID;
    }

    public String getPpResponseCode() {
        return ppResponseCode;
    }

    public void setPpResponseCode(String ppResponseCode) {
        this.ppResponseCode = ppResponseCode;
    }

    public String getPpResponseMessage() {
        return ppResponseMessage;
    }

    public void setPpResponseMessage(String ppResponseMessage) {
        this.ppResponseMessage = ppResponseMessage;
    }

    public String getPpRetreivalReferenceNo() {
        return ppRetreivalReferenceNo;
    }

    public void setPpRetreivalReferenceNo(String ppRetreivalReferenceNo) {
        this.ppRetreivalReferenceNo = ppRetreivalReferenceNo;
    }

    public String getPpSubMerchantID() {
        return ppSubMerchantID;
    }

    public void setPpSubMerchantID(String ppSubMerchantID) {
        this.ppSubMerchantID = ppSubMerchantID;
    }

    public String getPpTxnCurrency() {
        return ppTxnCurrency;
    }

    public void setPpTxnCurrency(String ppTxnCurrency) {
        this.ppTxnCurrency = ppTxnCurrency;
    }

    public String getPpTxnDateTime() {
        return ppTxnDateTime;
    }

    public void setPpTxnDateTime(String ppTxnDateTime) {
        this.ppTxnDateTime = ppTxnDateTime;
    }

    public String getPpTxnRefNo() {
        return ppTxnRefNo;
    }

    public void setPpTxnRefNo(String ppTxnRefNo) {
        this.ppTxnRefNo = ppTxnRefNo;
    }

    public String getPpMobileNumber() {
        return ppMobileNumber;
    }

    public void setPpMobileNumber(String ppMobileNumber) {
        this.ppMobileNumber = ppMobileNumber;
    }

    public String getPpCNIC() {
        return ppCNIC;
    }

    public void setPpCNIC(String ppCNIC) {
        this.ppCNIC = ppCNIC;
    }

    public String getPpDiscountedAmount() {
        return ppDiscountedAmount;
    }

    public void setPpDiscountedAmount(String ppDiscountedAmount) {
        this.ppDiscountedAmount = ppDiscountedAmount;
    }

    public String getPpmpf1() {
        return ppmpf1;
    }

    public void setPpmpf1(String ppmpf1) {
        this.ppmpf1 = ppmpf1;
    }

    public String getPpmpf2() {
        return ppmpf2;
    }

    public void setPpmpf2(String ppmpf2) {
        this.ppmpf2 = ppmpf2;
    }

    public String getPpmpf3() {
        return ppmpf3;
    }

    public void setPpmpf3(String ppmpf3) {
        this.ppmpf3 = ppmpf3;
    }

    public String getPpmpf4() {
        return ppmpf4;
    }

    public void setPpmpf4(String ppmpf4) {
        this.ppmpf4 = ppmpf4;
    }

    public String getPpmpf5() {
        return ppmpf5;
    }

    public void setPpmpf5(String ppmpf5) {
        this.ppmpf5 = ppmpf5;
    }

    public String getPpSecureHash() {
        return ppSecureHash;
    }

    public void setPpSecureHash(String ppSecureHash) {
        this.ppSecureHash = ppSecureHash;
    }

}
