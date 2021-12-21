
package com.webdoc.ibcc.ResponseModels.ViewIncompleteResult;

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
    @SerializedName("incompleteResultDetails")
    @Expose
    private List<IncompleteResultDetail> incompleteResultDetails = null;

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

    public List<IncompleteResultDetail> getIncompleteResultDetails() {
        return incompleteResultDetails;
    }

    public void setIncompleteResultDetails(List<IncompleteResultDetail> incompleteResultDetails) {
        this.incompleteResultDetails = incompleteResultDetails;
    }

}
