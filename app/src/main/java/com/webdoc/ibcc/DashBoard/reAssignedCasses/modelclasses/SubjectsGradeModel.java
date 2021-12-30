package com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectsGradeModel {
    private String subjectId;
    private String subjectName;
    private String marksgrades;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getMarksgrades() {
        return marksgrades;
    }

    public void setMarksgrades(String marksgrades) {
        this.marksgrades = marksgrades;
    }
}
