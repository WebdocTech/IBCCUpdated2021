package com.webdoc.ibcc.Model;

public class DocumentReceiptModel {
    String title,board,certificate,documentType, amount;

    public DocumentReceiptModel(String title, String board, String certificate, String documentType, String amount) {
        this.title = title;
        this.board = board;
        this.certificate = certificate;
        this.documentType = documentType;
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
