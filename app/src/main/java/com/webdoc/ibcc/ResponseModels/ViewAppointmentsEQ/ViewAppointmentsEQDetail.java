
package com.webdoc.ibcc.ResponseModels.ViewAppointmentsEQ;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewAppointmentsEQDetail {

    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("equivalenceofQualification")
    @Expose
    private String equivalenceofQualification;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("examiningBody")
    @Expose
    private String examiningBody;

    @SerializedName("subjects")
    @Expose
    private String Subjects;

    public String getSubjects() {
        return Subjects;
    }

    public void setSubjects(String subjects) {
        Subjects = subjects;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEquivalenceofQualification() {
        return equivalenceofQualification;
    }

    public void setEquivalenceofQualification(String equivalenceofQualification) {
        this.equivalenceofQualification = equivalenceofQualification;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getExaminingBody() {
        return examiningBody;
    }

    public void setExaminingBody(String examiningBody) {
        this.examiningBody = examiningBody;
    }

}
