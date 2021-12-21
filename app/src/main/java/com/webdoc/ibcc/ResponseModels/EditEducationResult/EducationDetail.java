
package com.webdoc.ibcc.ResponseModels.EditEducationResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EducationDetail {

    @SerializedName("certificate")
    @Expose
    private String certificate;
    @SerializedName("program")
    @Expose
    private String program;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("board")
    @Expose
    private String board;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("marks")
    @Expose
    private String marks;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("session")
    @Expose
    private String session;
    @SerializedName("roll_number")
    @Expose
    private String rollNumber;
    @SerializedName("case_id")
    @Expose
    private String caseId;
    @SerializedName("doc_id")
    @Expose
    private String docId;

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

}
