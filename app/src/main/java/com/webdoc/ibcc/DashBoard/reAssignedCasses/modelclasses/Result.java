package com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Result {
    @SerializedName("responseCode")
    @Expose
    private String responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("reassignedCaseDetails")
    @Expose
    private ArrayList<ReassignedCaseDetail> reassignedCaseDetails = null;

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

    public ArrayList<ReassignedCaseDetail> getReassignedCaseDetails() {
        return reassignedCaseDetails;
    }

    public void setReassignedCaseDetails(ArrayList<ReassignedCaseDetail> reassignedCaseDetails) {
        this.reassignedCaseDetails = reassignedCaseDetails;
    }
}
