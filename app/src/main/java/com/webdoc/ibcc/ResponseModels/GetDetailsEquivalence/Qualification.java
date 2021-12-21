
package com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Qualification {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("instructions")
    @Expose
    private String instructions;
    @SerializedName("is_required_documentary_evidence")
    @Expose
    private Integer isRequiredDocumentaryEvidence;
    @SerializedName("equivalenceGradingSystem")
    @Expose
    private List<EquivalenceGradingSystem> equivalenceGradingSystem = null;
    @SerializedName("equivalenceGroup")
    @Expose
    private List<EquivalenceGroup> equivalenceGroup = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<EquivalenceGradingSystem> getEquivalenceGradingSystem() {
        return equivalenceGradingSystem;
    }

    public void setEquivalenceGradingSystem(List<EquivalenceGradingSystem> equivalenceGradingSystem) {
        this.equivalenceGradingSystem = equivalenceGradingSystem;
    }

    public List<EquivalenceGroup> getEquivalenceGroup() {
        return equivalenceGroup;
    }

    public void setEquivalenceGroup(List<EquivalenceGroup> equivalenceGroup) {
        this.equivalenceGroup = equivalenceGroup;
    }

}
