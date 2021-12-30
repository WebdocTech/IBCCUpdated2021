package com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GradesEQNew {
    @SerializedName("equivalenceGradingSystemId")
    @Expose
    private Integer equivalenceGradingSystemId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getEquivalenceGradingSystemId() {
        return equivalenceGradingSystemId;
    }

    public void setEquivalenceGradingSystemId(Integer equivalenceGradingSystemId) {
        this.equivalenceGradingSystemId = equivalenceGradingSystemId;
    }

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

}