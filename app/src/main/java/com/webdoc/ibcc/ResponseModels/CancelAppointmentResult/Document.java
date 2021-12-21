
package com.webdoc.ibcc.ResponseModels.CancelAppointmentResult;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Document {

    @SerializedName("case_id")
    @Expose
    private String caseId;
    @SerializedName("doc_id")
    @Expose
    private String docId;
    @SerializedName("certificate_id")
    @Expose
    private String certificateId;
    @SerializedName("program_id")
    @Expose
    private String programId;
    @SerializedName("group_id")
    @Expose
    private String groupId;
    @SerializedName("board_id")
    @Expose
    private String boardId;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("marks")
    @Expose
    private String marks;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("roll_number")
    @Expose
    private String rollNumber;
    @SerializedName("stepNumber")
    @Expose
    private String stepNumber;
    @SerializedName("document_details")
    @Expose
    private List<DocumentDetail> documentDetails = null;

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

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
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

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(String stepNumber) {
        this.stepNumber = stepNumber;
    }

    public List<DocumentDetail> getDocumentDetails() {
        return documentDetails;
    }

    public void setDocumentDetails(List<DocumentDetail> documentDetails) {
        this.documentDetails = documentDetails;
    }

}
