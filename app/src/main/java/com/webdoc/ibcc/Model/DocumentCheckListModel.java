package com.webdoc.ibcc.Model;

public class DocumentCheckListModel {
    String title, documentType;

    public DocumentCheckListModel(String title, String documentType) {
        this.title = title;
        this.documentType = documentType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
}
