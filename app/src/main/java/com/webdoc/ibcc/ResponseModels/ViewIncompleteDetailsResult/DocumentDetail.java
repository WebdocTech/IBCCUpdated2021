
package com.webdoc.ibcc.ResponseModels.ViewIncompleteDetailsResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocumentDetail {

    @SerializedName("certificate_type")
    @Expose
    private String certificateType;

    @SerializedName("urgent")
    @Expose
    private String urgent;
    @SerializedName("original_included")
    @Expose
    private String originalIncluded;
    @SerializedName("dd_id")
    @Expose
    private String ddId;
    @SerializedName("document_type")
    @Expose
    private String documentType;
    @SerializedName("ticket_number")
    @Expose
    private String ticketNumber;
    @SerializedName("ticket_date")
    @Expose
    private String ticketDate;
    @SerializedName("no_of_copies")
    @Expose
    private String noOfCopies;
    @SerializedName("amount")
    @Expose
    private String amount;

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getOriginalIncluded() {
        return originalIncluded;
    }

    public void setOriginalIncluded(String originalIncluded) {
        this.originalIncluded = originalIncluded;
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

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getTicketDate() {
        return ticketDate;
    }

    public void setTicketDate(String ticketDate) {
        this.ticketDate = ticketDate;
    }

    public String getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(String noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
