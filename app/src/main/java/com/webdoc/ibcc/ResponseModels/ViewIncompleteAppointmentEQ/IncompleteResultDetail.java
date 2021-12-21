
package com.webdoc.ibcc.ResponseModels.ViewIncompleteAppointmentEQ;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IncompleteResultDetail {

    @SerializedName("center_id")
    @Expose
    private String centerId;
    @SerializedName("case_id")
    @Expose
    private String caseId;
    @SerializedName("case_status")
    @Expose
    private String caseStatus;

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

}
