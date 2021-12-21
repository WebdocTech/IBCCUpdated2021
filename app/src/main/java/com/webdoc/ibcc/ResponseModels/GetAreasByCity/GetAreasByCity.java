
package com.webdoc.ibcc.ResponseModels.GetAreasByCity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAreasByCity {

    @SerializedName("AreaID")
    @Expose
    private Integer areaID;
    @SerializedName("AreaName")
    @Expose
    private String areaName;
    @SerializedName("RouteNo")
    @Expose
    private String routeNo;
    @SerializedName("CityID")
    @Expose
    private Integer cityID;
    @SerializedName("EnteredBy")
    @Expose
    private String enteredBy;
    @SerializedName("EnteredOn")
    @Expose
    private String enteredOn;
    @SerializedName("EditedBy")
    @Expose
    private Object editedBy;
    @SerializedName("EditedOn")
    @Expose
    private Object editedOn;
    @SerializedName("Locked")
    @Expose
    private Boolean locked;

    public Integer getAreaID() {
        return areaID;
    }

    public void setAreaID(Integer areaID) {
        this.areaID = areaID;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }

    public Integer getCityID() {
        return cityID;
    }

    public void setCityID(Integer cityID) {
        this.cityID = cityID;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getEnteredOn() {
        return enteredOn;
    }

    public void setEnteredOn(String enteredOn) {
        this.enteredOn = enteredOn;
    }

    public Object getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(Object editedBy) {
        this.editedBy = editedBy;
    }

    public Object getEditedOn() {
        return editedOn;
    }

    public void setEditedOn(Object editedOn) {
        this.editedOn = editedOn;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

}
