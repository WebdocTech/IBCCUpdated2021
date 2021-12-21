
package com.webdoc.ibcc.ResponseModels.PdfResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Center {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("callCourierId")
    @Expose
    private String callCourierId;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCallCourierId() {
        return callCourierId;
    }

    public void setCallCourierId(String callCourierId) {
        this.callCourierId = callCourierId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
