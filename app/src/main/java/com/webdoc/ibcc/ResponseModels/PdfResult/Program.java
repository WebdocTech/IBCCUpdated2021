
package com.webdoc.ibcc.ResponseModels.PdfResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Program {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("no_of_Copies")
    @Expose
    private String noOfCopies;

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

    public String getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(String noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

}
