package com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Result {

    @SerializedName("responseCode")
    @Expose
    private String responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("countries")
    @Expose
    private List<Country> countries = null;
    @SerializedName("province")
    @Expose
    private List<Province> province = null;
    @SerializedName("examiningBody")
    @Expose
    private ArrayList<ExaminingBody> examiningBody = null;
    @SerializedName("qualification")
    @Expose
    private List<Qualification> qualification = null;
    @SerializedName("groupEQNew")
    @Expose
    private ArrayList<GroupEQNew> groupEQNew = null;
    @SerializedName("equivalenceGradingSystemEQNew")
    @Expose
    private ArrayList<EquivalenceGradingSystemEQNew> equivalenceGradingSystemEQNew = null;
    @SerializedName("equivalenceSubjectEQNew")
    @Expose
    private ArrayList<EquivalenceSubjectEQNew> equivalenceSubjectEQNew = null;
    @SerializedName("gradesEQNew")
    @Expose
    private ArrayList<GradesEQNew> gradesEQNew = null;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    public List<Province> getProvince() {
        return province;
    }

    public void setProvince(ArrayList<Province> province) {
        this.province = province;
    }

    public ArrayList<ExaminingBody> getExaminingBody() {
        return examiningBody;
    }

    public void setExaminingBody(ArrayList<ExaminingBody> examiningBody) {
        this.examiningBody = examiningBody;
    }

    public List<Qualification> getQualification() {
        return qualification;
    }

    public void setQualification(List<Qualification> qualification) {
        this.qualification = qualification;
    }

    public ArrayList<GroupEQNew> getGroupEQNew() {
        return groupEQNew;
    }

    public void setGroupEQNew(ArrayList<GroupEQNew> groupEQNew) {
        this.groupEQNew = groupEQNew;
    }

    public ArrayList<EquivalenceGradingSystemEQNew> getEquivalenceGradingSystemEQNew() {
        return equivalenceGradingSystemEQNew;
    }

    public void setEquivalenceGradingSystemEQNew(ArrayList<EquivalenceGradingSystemEQNew> equivalenceGradingSystemEQNew) {
        this.equivalenceGradingSystemEQNew = equivalenceGradingSystemEQNew;
    }

    public ArrayList<EquivalenceSubjectEQNew> getEquivalenceSubjectEQNew() {
        return equivalenceSubjectEQNew;
    }

    public void setEquivalenceSubjectEQNew(ArrayList<EquivalenceSubjectEQNew> equivalenceSubjectEQNew) {
        this.equivalenceSubjectEQNew = equivalenceSubjectEQNew;
    }

    public ArrayList<GradesEQNew> getGradesEQNew() {
        return gradesEQNew;
    }

    public void setGradesEQNew(ArrayList<GradesEQNew> gradesEQNew) {
        this.gradesEQNew = gradesEQNew;
    }

}