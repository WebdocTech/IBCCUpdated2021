package com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Qualification {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("instructions")
    @Expose
    private String instructions;
    @SerializedName("is_required_documentary_evidence")
    @Expose
    private Integer isRequiredDocumentaryEvidence;
    @SerializedName("countryId")
    @Expose
    private Integer countryId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Integer getIsRequiredDocumentaryEvidence() {
        return isRequiredDocumentaryEvidence;
    }

    public void setIsRequiredDocumentaryEvidence(Integer isRequiredDocumentaryEvidence) {
        this.isRequiredDocumentaryEvidence = isRequiredDocumentaryEvidence;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

}