package com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.DocumentSelection.apimodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocumentsModel {

    @SerializedName("caseId")
    @Expose
    private Integer caseId;
    @SerializedName("saveDocumentDetailsList")
    @Expose
    private List<SaveDocumentDetails> saveDocumentDetailsList = null;

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public List<SaveDocumentDetails> getSaveDocumentDetailsList() {
        return saveDocumentDetailsList;
    }

    public void setSaveDocumentDetailsList(List<SaveDocumentDetails> saveDocumentDetailsList) {
        this.saveDocumentDetailsList = saveDocumentDetailsList;
    }

}