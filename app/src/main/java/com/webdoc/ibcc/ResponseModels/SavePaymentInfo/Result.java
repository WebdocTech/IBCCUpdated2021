
package com.webdoc.ibcc.ResponseModels.SavePaymentInfo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("responseCode")
    @Expose
    private String responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("challan_id")
    @Expose
    private String challan_id;

    public String getChallan_id() {
        return challan_id;
    }

    public void setChallan_id(String challan_id) {
        this.challan_id = challan_id;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
