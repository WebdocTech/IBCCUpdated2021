package com.webdoc.ibcc.Model;

import com.webdoc.ibcc.ResponseModels.AddQualificationEQ.QualificationSubjectResponse;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.EquivalenceGrade;

import java.util.List;

public class EquivalenceAddQualification {

    String caseId;
    String countryId;
    String email;
    String examinationSystem;
    String examiningBody;
    String purposeOfEquivalence;
    String nameOfTheInstitution;
    String mailingAddress;
    String telNo;
    String otherExaminingBody;
    String gradingSystemId;
    String groupId;
    String session;
    String qualificationId;
    String titleOfQualification;
    String fatherCnic;
    String presentEmploymentOfParents;
    String fatherName;
    String parentsPermanentAddress;
    String parentsNameOfTheOrganization;
    List<String> imagesEductaionList;
    List<EquivalenceGrade> subjectEducationList;
    List<String> imagesTravellingList;
    List<QualificationSubjectResponse> qualificationSubjectResponseList;

    public List<QualificationSubjectResponse> getQualificationSubjectResponseList() {
        return qualificationSubjectResponseList;
    }

    public void setQualificationSubjectResponseList(List<QualificationSubjectResponse> qualificationSubjectResponseList) {
        this.qualificationSubjectResponseList = qualificationSubjectResponseList;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    public List<String> getImagesEductaionList() {
        return imagesEductaionList;
    }

    public void setImagesEductaionList(List<String> imagesEductaionList) {
        this.imagesEductaionList = imagesEductaionList;
    }

    public List<EquivalenceGrade> getSubjectEducationList() {
        return subjectEducationList;
    }

    public void setSubjectEducationList(List<EquivalenceGrade> subjectEducationList) {
        this.subjectEducationList = subjectEducationList;
    }

    public List<String> getImagesTravellingList() {
        return imagesTravellingList;
    }

    public void setImagesTravellingList(List<String> imagesTravellingList) {
        this.imagesTravellingList = imagesTravellingList;
    }
}
