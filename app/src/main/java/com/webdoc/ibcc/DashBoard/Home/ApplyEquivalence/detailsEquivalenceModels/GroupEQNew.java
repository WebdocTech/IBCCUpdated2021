package com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupEQNew {

    @SerializedName("qualificationId")
    @Expose
    private Integer qualificationId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getQualificationId() {
        return qualificationId;
    }

    public void setQualificationId(Integer qualificationId) {
        this.qualificationId = qualificationId;
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