package com.webdoc.ibcc.Model;

import com.webdoc.ibcc.ResponseModels.AddQualificationEQ.CaseUploadedDocumentResponse;
import com.webdoc.ibcc.ResponseModels.AddQualificationEQ.QualificationSubjectResponse;

import java.util.List;

public class AddQualificationEQModel {
    String docId, caseId, countryId, countryName, email, examinationSystem, examiningBody, purposeOfEquivalence, nameOfTheInstitution,
            mailingAddress, telNo, otherExaminingBody, gradingSystemId, gradingSystemName, groupId, groupName, session, qualificationId,
            qualificationName, titleOfQualification, fatherCnic, presentEmploymentOfParents, fatherName, parentsPermanentAddress, parentsNameOfTheOrganization;
    List<CaseUploadedDocumentResponse> caseUploadedDocumentResponseList;
    List<QualificationSubjectResponse> qualificationSubjectResponseList;

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
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

    public List<CaseUploadedDocumentResponse> getCaseUploadedDocumentResponseList() {
        return caseUploadedDocumentResponseList;
    }

    public void setCaseUploadedDocumentResponseList(List<CaseUploadedDocumentResponse> caseUploadedDocumentResponseList) {
        this.caseUploadedDocumentResponseList = caseUploadedDocumentResponseList;
    }

    public List<QualificationSubjectResponse> getQualificationSubjectResponseList() {
        return qualificationSubjectResponseList;
    }

    public void setQualificationSubjectResponseList(List<QualificationSubjectResponse> qualificationSubjectResponseList) {
        this.qualificationSubjectResponseList = qualificationSubjectResponseList;
    }
}
