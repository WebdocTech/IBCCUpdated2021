
package com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EquivalenceGradingSystem {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("equivalenceGrade")
    @Expose
    private List<EquivalenceGrade> equivalenceGrade = null;

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

    public List<EquivalenceGrade> getEquivalenceGrade() {
        return equivalenceGrade;
    }

    public void setEquivalenceGrade(List<EquivalenceGrade> equivalenceGrade) {
        this.equivalenceGrade = equivalenceGrade;
    }

}
