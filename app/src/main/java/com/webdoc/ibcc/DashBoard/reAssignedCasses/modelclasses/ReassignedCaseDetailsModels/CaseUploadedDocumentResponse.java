package com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.ReassignedCaseDetailsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CaseUploadedDocumentResponse {
    @SerializedName("imagename")
    @Expose
    private String imagename;
    @SerializedName("ispdf")
    @Expose
    private Boolean ispdf;

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public Boolean getIspdf() {
        return ispdf;
    }

    public void setIspdf(Boolean ispdf) {
        this.ispdf = ispdf;
    }
}
