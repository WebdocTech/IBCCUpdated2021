package com.webdoc.ibcc.Model;

import java.util.List;

public class DocumentSelectionModel {
    String certificate, board, program;
    List<DocumentDetailModel> detailModelList;
    int id;
    String docId, caseId,TotalAmount;

    /*public DocumentSelectionModel(String title, String board, String degree, List<DocumentDetailModel> detailModelList) {
        this.title = title;
        this.board = board;
        this.degree = degree;
        this.detailModelList = detailModelList;
    }*/

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<DocumentDetailModel> getDetailModelList() {
        return detailModelList;
    }

    public void setDetailModelList(List<DocumentDetailModel> detailModelList) {
        this.detailModelList = detailModelList;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }
}
