package com.webdoc.ibcc.Model;

public class UpdatePaymentInfoRequestModel {

    String case_id,transection_id,type;

    public String getCase_id() {
        return case_id;
    }

    public void setCase_id(String case_id) {
        this.case_id = case_id;
    }

    public String getTransection_id() {
        return transection_id;
    }

    public void setTransection_id(String transection_id) {
        this.transection_id = transection_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
