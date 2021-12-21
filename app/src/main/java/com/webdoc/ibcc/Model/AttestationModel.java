package com.webdoc.ibcc.Model;

public class AttestationModel {
    String title;
    int description;

    public AttestationModel(String title, int description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }


}
