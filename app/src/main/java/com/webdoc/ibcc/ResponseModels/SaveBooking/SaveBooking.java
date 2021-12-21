
package com.webdoc.ibcc.ResponseModels.SaveBooking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveBooking {

    @SerializedName("NetAmount")
    @Expose
    private String netAmount;
    @SerializedName("Amount")
    @Expose
    private String amount;
    @SerializedName("GstPer")
    @Expose
    private String gstPer;
    @SerializedName("CNNO")
    @Expose
    private String cnno;
    @SerializedName("SpecialHandling")
    @Expose
    private String specialHandling;
    @SerializedName("Response")
    @Expose
    private String response;
    @SerializedName("Count")
    @Expose
    private Integer count;

    public String getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(String netAmount) {
        this.netAmount = netAmount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getGstPer() {
        return gstPer;
    }

    public void setGstPer(String gstPer) {
        this.gstPer = gstPer;
    }

    public String getCnno() {
        return cnno;
    }

    public void setCnno(String cnno) {
        this.cnno = cnno;
    }

    public String getSpecialHandling() {
        return specialHandling;
    }

    public void setSpecialHandling(String specialHandling) {
        this.specialHandling = specialHandling;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
