package com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReassignedCaseDetail {
    @SerializedName("caseId")
    @Expose
    private String caseId;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("challanNumber")
    @Expose
    private String challanNumber;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("city")
    @Expose
    private String city;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getChallanNumber() {
        return challanNumber;
    }

    public void setChallanNumber(String challanNumber) {
        this.challanNumber = challanNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
