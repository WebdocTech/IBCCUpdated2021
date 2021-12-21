
package com.webdoc.ibcc.ResponseModels.IntiateCase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IntiateCaseResponseDetails {

    @SerializedName("customerId")
    @Expose
    private Integer customerId;
    @SerializedName("caseId")
    @Expose
    private Integer caseId;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

}
