
package com.webdoc.ibcc.ResponseModels.PdfResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pdf {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("pdf_url")
    @Expose
    private String pdfUrl;
    @SerializedName("name")
    @Expose
    private String name;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
