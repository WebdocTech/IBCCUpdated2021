
package com.webdoc.ibcc.ResponseModels.phpfilesResponse;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Datum {

    @SerializedName("imagename")
    @Expose
    private String imagename;

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

}
