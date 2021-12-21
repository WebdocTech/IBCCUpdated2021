
package com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Country {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("examiningBody")
    @Expose
    private List<ExaminingBody> examiningBody = null;
    @SerializedName("qualification")
    @Expose
    private List<Qualification> qualification = null;

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

    public List<ExaminingBody> getExaminingBody() {
        return examiningBody;
    }

    public void setExaminingBody(List<ExaminingBody> examiningBody) {
        this.examiningBody = examiningBody;
    }

    public List<Qualification> getQualification() {
        return qualification;
    }

    public void setQualification(List<Qualification> qualification) {
        this.qualification = qualification;
    }

}
