
package com.webdoc.ibcc.ResponseModels.DeleteEducationResult;

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
    @SerializedName("educationDetails")
    @Expose
    private List<Object> educationDetails = null;

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

    public List<Object> getEducationDetails() {
        return educationDetails;
    }

    public void setEducationDetails(List<Object> educationDetails) {
        this.educationDetails = educationDetails;
    }

}
