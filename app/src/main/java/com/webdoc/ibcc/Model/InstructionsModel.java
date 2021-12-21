package com.webdoc.ibcc.Model;

public class InstructionsModel {
    String count, details;

    public InstructionsModel(String count, String details) {
        this.count = count;
        this.details = details;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }


}
