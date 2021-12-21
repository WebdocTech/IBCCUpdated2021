package com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.ReassignedCaseDetailsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocumentDetailList {
    @SerializedName("equivalenceOfCertificates")
    @Expose
    private String equivalenceOfCertificates;
    @SerializedName("documentType")
    @Expose
    private String documentType;
    @SerializedName("equivalenceOfCertificatesType")
    @Expose
    private String equivalenceOfCertificatesType;
    @SerializedName("urgent")
    @Expose
    private String urgent;
    @SerializedName("dd_id")
    @Expose
    private String ddId;
    @SerializedName("amount")
    @Expose
    private String amount;

    public String getEquivalenceOfCertificates() {
        return equivalenceOfCertificates;
    }

    public void setEquivalenceOfCertificates(String equivalenceOfCertificates) {
        this.equivalenceOfCertificates = equivalenceOfCertificates;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getEquivalenceOfCertificatesType() {
        return equivalenceOfCertificatesType;
    }

    public void setEquivalenceOfCertificatesType(String equivalenceOfCertificatesType) {
        this.equivalenceOfCertificatesType = equivalenceOfCertificatesType;
    }

    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }

    public String getDdId() {
        return ddId;
    }

    public void setDdId(String ddId) {
        this.ddId = ddId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
