
package com.webdoc.ibcc.Testing.phpResp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhpResp {

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
