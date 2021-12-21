package com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.DocumentSelection;

public class DocDetailEQ_Model {
    int caseID;
    int docId;
    int amount;
    String documentType;
    String  qofCert;
    String qOfCertType;
    int fObtMarks;
    int fTotalMarks;
    int obtainedMarks;
    int totalMarks;
    int percentage;
    int urgent;

    public int getCaseID() {
        return caseID;
    }

    public void setCaseID(int caseID) {
        this.caseID = caseID;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getQofCert() {
        return qofCert;
    }

    public void setQofCert(String qofCert) {
        this.qofCert = qofCert;
    }

    public String getqOfCertType() {
        return qOfCertType;
    }

    public void setqOfCertType(String qOfCertType) {
        this.qOfCertType = qOfCertType;
    }

    public int getfObtMarks() {
        return fObtMarks;
    }

    public void setfObtMarks(int fObtMarks) {
        this.fObtMarks = fObtMarks;
    }

    public int getfTotalMarks() {
        return fTotalMarks;
    }

    public void setfTotalMarks(int fTotalMarks) {
        this.fTotalMarks = fTotalMarks;
    }

    public int getObtainedMarks() {
        return obtainedMarks;
    }

    public void setObtainedMarks(int obtainedMarks) {
        this.obtainedMarks = obtainedMarks;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getUrgent() {
        return urgent;
    }

    public void setUrgent(int urgent) {
        this.urgent = urgent;
    }
}
