
package com.webdoc.ibcc.ResponseModels.PdfResult;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("pdf")
    @Expose
    private List<Pdf> pdf = null;
    @SerializedName("boards")
    @Expose
    private List<Board> boards = null;
    @SerializedName("countries")
    @Expose
    private List<Country> countries = null;
    @SerializedName("provinces")
    @Expose
    private List<Province> provinces = null;
    @SerializedName("cerftificates")
    @Expose
    private List<Cerftificate> cerftificates = null;
    @SerializedName("centers")
    @Expose
    private List<Center> centers = null;

    public List<Pdf> getPdf() {
        return pdf;
    }

    public void setPdf(List<Pdf> pdf) {
        this.pdf = pdf;
    }

    public List<Board> getBoards() {
        return boards;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public List<Cerftificate> getCerftificates() {
        return cerftificates;
    }

    public void setCerftificates(List<Cerftificate> cerftificates) {
        this.cerftificates = cerftificates;
    }

    public List<Center> getCenters() {
        return centers;
    }

    public void setCenters(List<Center> centers) {
        this.centers = centers;
    }

}
