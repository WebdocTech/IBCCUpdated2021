package com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.ReassignedCaseDetailsModels;

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
    @SerializedName("document")
    @Expose
    private ArrayList<Document> document = null;

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

    public ArrayList<Document> getDocument() {
        return document;
    }

    public void setDocument(ArrayList<Document> document) {
        this.document = document;
    }
}
