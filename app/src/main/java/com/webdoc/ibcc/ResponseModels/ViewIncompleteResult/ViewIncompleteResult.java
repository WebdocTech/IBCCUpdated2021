
package com.webdoc.ibcc.ResponseModels.ViewIncompleteResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewIncompleteResult {

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
    private Result result;

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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
