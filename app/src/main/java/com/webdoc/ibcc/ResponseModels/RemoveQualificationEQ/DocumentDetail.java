
package com.webdoc.ibcc.ResponseModels.RemoveQualificationEQ;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocumentDetail {

    @SerializedName("docId")
    @Expose
    private Integer docId;
    @SerializedName("caseId")
    @Expose
    private Integer caseId;
    @SerializedName("countryId")
    @Expose
    private String countryId;
    @SerializedName("countryName")
    @Expose
    private String countryName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("examinationSystem")
    @Expose
    private String examinationSystem;
    @SerializedName("examiningBody")
    @Expose
    private String examiningBody;
    @SerializedName("purposeOfEquivalence")
    @Expose
    private String purposeOfEquivalence;
    @SerializedName("nameOfTheInstitution")
    @Expose
    private String nameOfTheInstitution;
    @SerializedName("mailingAddress")
    @Expose
    private String mailingAddress;
    @SerializedName("telNo")
    @Expose
    private String telNo;
    @SerializedName("otherExaminingBody")
    @Expose
    private String otherExaminingBody;
    @SerializedName("gradingSystemId")
    @Expose
    private String gradingSystemId;
    @SerializedName("gradingSystemName")
    @Expose
    private String gradingSystemName;
    @SerializedName("groupId")
    @Expose
    private String groupId;
    @SerializedName("groupName")
    @Expose
    private String groupName;
    @SerializedName("session")
    @Expose
    private String session;
    @SerializedName("qualificationId")
    @Expose
    private String qualificationId;
    @SerializedName("qualificationName")
    @Expose
    private String qualificationName;
    @SerializedName("titleOfQualification")
    @Expose
    private String titleOfQualification;
    @SerializedName("fatherCnic")
    @Expose
    private String fatherCnic;
    @SerializedName("presentEmploymentOfParents")
    @Expose
    private String presentEmploymentOfParents;
    @SerializedName("fatherName")
    @Expose
    private String fatherName;
    @SerializedName("parentsPermanentAddress")
    @Expose
    private String parentsPermanentAddress;
    @SerializedName("parentsNameOfTheOrganization")
    @Expose
    private String parentsNameOfTheOrganization;
    @SerializedName("caseUploadedDocumentResponse")
    @Expose
    private List<CaseUploadedDocumentResponse> caseUploadedDocumentResponse = null;
    @SerializedName("qualificationSubjectResponse")
    @Expose
    private List<QualificationSubjectResponse> qualificationSubjectResponse = null;

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExaminationSystem() {
        return examinationSystem;
    }

    public void setExaminationSystem(String examinationSystem) {
        this.examinationSystem = examinationSystem;
    }

    public String getExaminingBody() {
        return examiningBody;
    }

    public void setExaminingBody(String examiningBody) {
        this.examiningBody = examiningBody;
    }

    public String getPurposeOfEquivalence() {
        return purposeOfEquivalence;
    }

    public void setPurposeOfEquivalence(String purposeOfEquivalence) {
        this.purposeOfEquivalence = purposeOfEquivalence;
    }

    public String getNameOfTheInstitution() {
        return nameOfTheInstitution;
    }

    public void setNameOfTheInstitution(String nameOfTheInstitution) {
        this.nameOfTheInstitution = nameOfTheInstitution;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getOtherExaminingBody() {
        return otherExaminingBody;
    }

    public void setOtherExaminingBody(String otherExaminingBody) {
        this.otherExaminingBody = otherExaminingBody;
    }

    public String getGradingSystemId() {
        return gradingSystemId;
    }

    public void setGradingSystemId(String gradingSystemId) {
        this.gradingSystemId = gradingSystemId;
    }

    public String getGradingSystemName() {
        return gradingSystemName;
    }

    public void setGradingSystemName(String gradingSystemName) {
        this.gradingSystemName = gradingSystemName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getQualificationId() {
        return qualificationId;
    }

    public void setQualificationId(String qualificationId) {
        this.qualificationId = qualificationId;
    }

    public String getQualificationName() {
        return qualificationName;
    }

    public void setQualificationName(String qualificationName) {
        this.qualificationName = qualificationName;
    }

    public String getTitleOfQualification() {
        return titleOfQualification;
    }

    public void setTitleOfQualification(String titleOfQualification) {
        this.titleOfQualification = titleOfQualification;
    }

    public String getFatherCnic() {
        return fatherCnic;
    }

    public void setFatherCnic(String fatherCnic) {
        this.fatherCnic = fatherCnic;
    }

    public String getPresentEmploymentOfParents() {
        return presentEmploymentOfParents;
    }

    public void setPresentEmploymentOfParents(String presentEmploymentOfParents) {
        this.presentEmploymentOfParents = presentEmploymentOfParents;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getParentsPermanentAddress() {
        return parentsPermanentAddress;
    }

    public void setParentsPermanentAddress(String parentsPermanentAddress) {
        this.parentsPermanentAddress = parentsPermanentAddress;
    }

    public String getParentsNameOfTheOrganization() {
        return parentsNameOfTheOrganization;
    }

    public void setParentsNameOfTheOrganization(String parentsNameOfTheOrganization) {
        this.parentsNameOfTheOrganization = parentsNameOfTheOrganization;
    }

    public List<CaseUploadedDocumentResponse> getCaseUploadedDocumentResponse() {
        return caseUploadedDocumentResponse;
    }

    public void setCaseUploadedDocumentResponse(List<CaseUploadedDocumentResponse> caseUploadedDocumentResponse) {
        this.caseUploadedDocumentResponse = caseUploadedDocumentResponse;
    }

    public List<QualificationSubjectResponse> getQualificationSubjectResponse() {
        return qualificationSubjectResponse;
    }

    public void setQualificationSubjectResponse(List<QualificationSubjectResponse> qualificationSubjectResponse) {
        this.qualificationSubjectResponse = qualificationSubjectResponse;
    }

}
