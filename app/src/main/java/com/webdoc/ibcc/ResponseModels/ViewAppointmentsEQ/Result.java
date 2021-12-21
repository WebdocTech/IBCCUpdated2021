
package com.webdoc.ibcc.ResponseModels.ViewAppointmentsEQ;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("responseCode")
    @Expose
    private String responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("viewAppointmentsEQD")
    @Expose
    private List<ViewAppointmentsEQD> viewAppointmentsEQD = null;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<ViewAppointmentsEQD> getViewAppointmentsEQD() {
        return viewAppointmentsEQD;
    }

    public void setViewAppointmentsEQD(List<ViewAppointmentsEQD> viewAppointmentsEQD) {
        this.viewAppointmentsEQD = viewAppointmentsEQD;
    }

}
