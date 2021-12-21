
package com.webdoc.ibcc.ResponseModels.UpdateProfileResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProfileResult {

    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("isError")
    @Expose
    private Boolean isError;
    @SerializedName("result")
    @Expose
    private ProfileResult result;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getIsError() {
        return isError;
    }

    public void setIsError(Boolean isError) {
        this.isError = isError;
    }

    public ProfileResult getResult() {
        return result;
    }

    public void setResult(ProfileResult result) {
        this.result = result;
    }

}
