package com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.DocumentSelection.apimodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveDocumentDetails {

    @SerializedName("docId")
    @Expose
    private Integer docId;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("averageMarks")
    @Expose
    private Integer averageMarks;
    @SerializedName("documentType")
    @Expose
    private String documentType;
    @SerializedName("equivalenceOfCertificates")
    @Expose
    private String equivalenceOfCertificates;
    @SerializedName("equivalenceOfCertificatesType")
    @Expose
    private String equivalenceOfCertificatesType;
    @SerializedName("finalObtainedMarks")
    @Expose
    private Integer finalObtainedMarks;
    @SerializedName("finalTotalMarks")
    @Expose
    private Integer finalTotalMarks;
    @SerializedName("obtainedMarks")
    @Expose
    private Integer obtainedMarks;
    @SerializedName("totalMarks")
    @Expose
    private Integer totalMarks;
    @SerializedName("percentage")
    @Expose
    private Integer percentage;
    @SerializedName("previousTicketNumber")
    @Expose
    private String previousTicketNumber;
    @SerializedName("ticketDate")
    @Expose
    private String ticketDate;
    @SerializedName("ticketNumber")
    @Expose
    private String ticketNumber;
    @SerializedName("urgent")
    @Expose
    private Integer urgent;

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAverageMarks() {
        return averageMarks;
    }

    public void setAverageMarks(Integer averageMarks) {
        this.averageMarks = averageMarks;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getEquivalenceOfCertificates() {
        return equivalenceOfCertificates;
    }

    public void setEquivalenceOfCertificates(String equivalenceOfCertificates) {
        this.equivalenceOfCertificates = equivalenceOfCertificates;
    }

    public String getEquivalenceOfCertificatesType() {
        return equivalenceOfCertificatesType;
    }

    public void setEquivalenceOfCertificatesType(String equivalenceOfCertificatesType) {
        this.equivalenceOfCertificatesType = equivalenceOfCertificatesType;
    }

    public Integer getFinalObtainedMarks() {
        return finalObtainedMarks;
    }

    public void setFinalObtainedMarks(Integer finalObtainedMarks) {
        this.finalObtainedMarks = finalObtainedMarks;
    }

    public Integer getFinalTotalMarks() {
        return finalTotalMarks;
    }

    public void setFinalTotalMarks(Integer finalTotalMarks) {
        this.finalTotalMarks = finalTotalMarks;
    }

    public Integer getObtainedMarks() {
        return obtainedMarks;
    }

    public void setObtainedMarks(Integer obtainedMarks) {
        this.obtainedMarks = obtainedMarks;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public String getPreviousTicketNumber() {
        return previousTicketNumber;
    }

    public void setPreviousTicketNumber(String previousTicketNumber) {
        this.previousTicketNumber = previousTicketNumber;
    }

    public String getTicketDate() {
        return ticketDate;
    }

    public void setTicketDate(String ticketDate) {
        this.ticketDate = ticketDate;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Integer getUrgent() {
        return urgent;
    }

    public void setUrgent(Integer urgent) {
        this.urgent = urgent;
    }

}
