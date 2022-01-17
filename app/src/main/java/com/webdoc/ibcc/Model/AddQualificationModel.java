package com.webdoc.ibcc.Model;

import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.Country;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.EquivalenceGradingSystemEQNew;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.EquivalenceSubjectEQNew;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.ExaminingBody;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.GradesEQNew;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.GroupEQNew;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.Qualification;
import com.webdoc.ibcc.ResponseModels.AddQualificationEQ.QualificationSubjectResponse;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.EquivalenceGrade;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.EquivalenceGradingSystem;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.EquivalenceSubject;

import java.util.List;

public class AddQualificationModel {
    Country country;
    String examinationSystem;
    ExaminingBody examiningBody;
    Qualification qualification;
    GroupEQNew group;
    String session;
    String purposeOfEquivalence;
    EquivalenceGradingSystemEQNew gradingSystem;
    List<EquivalenceSubjectEQNew> subjectList;
    List<GradesEQNew> gradeList;
    List<EquivalenceFileModel> selectedFilesList;
    List<EquivalenceTravelFileModel> selectedTravelFilesList;
    List<QualificationSubjectResponse> qualificationSubjectResponseList;

    public List<QualificationSubjectResponse> getQualificationSubjectResponseList() {
        return qualificationSubjectResponseList;
    }

    public void setQualificationSubjectResponseList(List<QualificationSubjectResponse> qualificationSubjectResponseList) {
        this.qualificationSubjectResponseList = qualificationSubjectResponseList;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getExaminationSystem() {
        return examinationSystem;
    }

    public void setExaminationSystem(String examinationSystem) {
        this.examinationSystem = examinationSystem;
    }

    public ExaminingBody getExaminingBody() {
        return examiningBody;
    }

    public void setExaminingBody(ExaminingBody examiningBody) {
        this.examiningBody = examiningBody;
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

    public GroupEQNew getGroup() {
        return group;
    }

    public void setGroup(GroupEQNew group) {
        this.group = group;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getPurposeOfEquivalence() {
        return purposeOfEquivalence;
    }

    public void setPurposeOfEquivalence(String purposeOfEquivalence) {
        this.purposeOfEquivalence = purposeOfEquivalence;
    }

    public EquivalenceGradingSystemEQNew getGradingSystem() {
        return gradingSystem;
    }

    public void setGradingSystem(EquivalenceGradingSystemEQNew gradingSystem) {
        this.gradingSystem = gradingSystem;
    }

    public List<EquivalenceSubjectEQNew> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<EquivalenceSubjectEQNew> subjectList) {
        this.subjectList = subjectList;
    }

    public List<GradesEQNew> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<GradesEQNew> gradeList) {
        this.gradeList = gradeList;
    }

    public List<EquivalenceFileModel> getSelectedFilesList() {
        return selectedFilesList;
    }

    public void setSelectedFilesList(List<EquivalenceFileModel> selectedFilesList) {
        this.selectedFilesList = selectedFilesList;
    }

    public List<EquivalenceTravelFileModel> getSelectedTravelFilesList() {
        return selectedTravelFilesList;
    }

    public void setSelectedTravelFilesList(List<EquivalenceTravelFileModel> selectedTravelFilesList) {
        this.selectedTravelFilesList = selectedTravelFilesList;
    }
}
