
package com.webdoc.ibcc.ResponseModels.EditQualificationEQ;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QualificationSubjectResponse {

    @SerializedName("subjectName")
    @Expose
    private String subjectName;
    @SerializedName("subjectId")
    @Expose
    private String subjectId;
    @SerializedName("marksgrades")
    @Expose
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
