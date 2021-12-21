
package com.webdoc.ibcc.ResponseModels.IntiateCase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("responseCode")
    @Expose
    private String responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("intiateCaseResponseDetails")
    @Expose
    private IntiateCaseResponseDetails intiateCaseResponseDetails;

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

    public IntiateCaseResponseDetails getIntiateCaseResponseDetails() {
        return intiateCaseResponseDetails;
    }

    public void setIntiateCaseResponseDetails(IntiateCaseResponseDetails intiateCaseResponseDetails) {
        this.intiateCaseResponseDetails = intiateCaseResponseDetails;
    }

}
