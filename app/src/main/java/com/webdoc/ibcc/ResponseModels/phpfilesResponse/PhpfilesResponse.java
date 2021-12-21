
package com.webdoc.ibcc.ResponseModels.phpfilesResponse;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhpfilesResponse {

    @SerializedName("ImageUploadResult")
    @Expose
    private ImageUploadResult imageUploadResult;

    public ImageUploadResult getImageUploadResult() {
        return imageUploadResult;
    }

    public void setImageUploadResult(ImageUploadResult imageUploadResult) {
        this.imageUploadResult = imageUploadResult;
    }

}
