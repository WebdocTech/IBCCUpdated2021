
package com.webdoc.ibcc.ResponseModels.ViewIncompleteResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IncompleteResultDetail {

    @SerializedName("case_id")
    @Expose
    private String caseId;
    @SerializedName("case_status")
    @Expose
    private String caseStatus;
    @SerializedName("center_id")
    @Expose
    private String center_id;

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

    public String getCenter_id() {
        return center_id;
    }

    public void setCenter_id(String center_id) {
        this.center_id = center_id;
    }
}
