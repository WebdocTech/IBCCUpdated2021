
package com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ExaminingBody {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("countryId")
    @Expose
    private Integer countryId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("status")
    @Expose
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
