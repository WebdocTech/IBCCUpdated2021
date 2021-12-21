
package com.webdoc.ibcc.ResponseModels.CancelAppointmentResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocumentDetail {

    @SerializedName("certificate_type")
    @Expose
    private String certificateType;
    @SerializedName("original_included")
    @Expose
    private String originalIncluded;
    @SerializedName("dd_id")
    @Expose
    private String ddId;
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

    public String getDdId() {
        return ddId;
    }

    public void setDdId(String ddId) {
        this.ddId = ddId;
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
