
package com.webdoc.ibcc.ResponseModels.ViewIncompleteDetailsResult;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("responseCode")
    @Expose
    private String responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("stepNumber")
    @Expose
    private String stepNumber;
    @SerializedName("document")
    @Expose
    private List<Document> document = null;
    @SerializedName("paymentinfo")
    @Expose
    private Paymentinfo paymentinfo;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(String stepNumber) {
        this.stepNumber = stepNumber;
    }

    public List<Document> getDocument() {
        return document;
    }

    public void setDocument(List<Document> document) {
        this.document = document;
    }

    public Paymentinfo getPaymentinfo() {
        return paymentinfo;
    }

    public void setPaymentinfo(Paymentinfo paymentinfo) {
        this.paymentinfo = paymentinfo;
    }

}
