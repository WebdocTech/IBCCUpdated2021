package com.webdoc.ibcc.Model;

public class DocumentDetailModel {
    String id,certificateType, documentType,totalCopies, ticketNo, date, originalIncluded, titleCert,titleProg,titleBoard,certificateTypeID;
    int amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateTypeID() {
        return certificateTypeID;
    }

    public void setCertificateTypeID(String certificateTypeID) {
        this.certificateTypeID = certificateTypeID;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(String totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOriginalIncluded() {
        return originalIncluded;
    }

    public void setOriginalIncluded(String originalIncluded) {
        this.originalIncluded = originalIncluded;
    }

    public String getTitleCert() {
        return titleCert;
    }

    public void setTitleCert(String titleCert) {
        this.titleCert = titleCert;
    }

    public String getTitleProg() {
        return titleProg;
    }

    public void setTitleProg(String titleProg) {
        this.titleProg = titleProg;
    }

    public String getTitleBoard() {
        return titleBoard;
    }

    public void setTitleBoard(String titleBoard) {
        this.titleBoard = titleBoard;
    }


}
