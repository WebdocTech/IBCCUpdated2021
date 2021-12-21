
package com.webdoc.ibcc.ResponseModels.GetCityListByService;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCityListByService {

    @SerializedName("CityID")
    @Expose
    private Integer cityID;
    @SerializedName("CityName")
    @Expose
    private String cityName;
    @SerializedName("CityShortDesc")
    @Expose
    private String cityShortDesc;
    @SerializedName("StationID")
    @Expose
    private Integer stationID;
    @SerializedName("StationDesc")
    @Expose
    private String stationDesc;
    @SerializedName("StationShortDesc")
    @Expose
    private String stationShortDesc;
    @SerializedName("BranchID")
    @Expose
    private Integer branchID;
    @SerializedName("BranchName")
    @Expose
    private String branchName;
    @SerializedName("HubID")
    @Expose
    private Integer hubID;
    @SerializedName("HubDesc")
    @Expose
    private String hubDesc;
    @SerializedName("ZoneID")
    @Expose
    private Integer zoneID;
    @SerializedName("ZoneDesc")
    @Expose
    private String zoneDesc;
    @SerializedName("RegionID")
    @Expose
    private Integer regionID;
    @SerializedName("RegionDesc")
    @Expose
    private String regionDesc;
    @SerializedName("ProvinceID")
    @Expose
    private Integer provinceID;
    @SerializedName("ProvinceName")
    @Expose
    private String provinceName;
    @SerializedName("ServiceAreaTypeID")
    @Expose
    private Integer serviceAreaTypeID;
    @SerializedName("ServiceAreaTypeDesc")
    @Expose
    private Object serviceAreaTypeDesc;
    @SerializedName("EmployeeID")
    @Expose
    private Integer employeeID;
    @SerializedName("EmployeeName")
    @Expose
    private Object employeeName;

    public Integer getCityID() {
        return cityID;
    }

    public void setCityID(Integer cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityShortDesc() {
        return cityShortDesc;
    }

    public void setCityShortDesc(String cityShortDesc) {
        this.cityShortDesc = cityShortDesc;
    }

    public Integer getStationID() {
        return stationID;
    }

    public void setStationID(Integer stationID) {
        this.stationID = stationID;
    }

    public String getStationDesc() {
        return stationDesc;
    }

    public void setStationDesc(String stationDesc) {
        this.stationDesc = stationDesc;
    }

    public String getStationShortDesc() {
        return stationShortDesc;
    }

    public void setStationShortDesc(String stationShortDesc) {
        this.stationShortDesc = stationShortDesc;
    }

    public Integer getBranchID() {
        return branchID;
    }

    public void setBranchID(Integer branchID) {
        this.branchID = branchID;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Integer getHubID() {
        return hubID;
    }

    public void setHubID(Integer hubID) {
        this.hubID = hubID;
    }

    public String getHubDesc() {
        return hubDesc;
    }

    public void setHubDesc(String hubDesc) {
        this.hubDesc = hubDesc;
    }

    public Integer getZoneID() {
        return zoneID;
    }

    public void setZoneID(Integer zoneID) {
        this.zoneID = zoneID;
    }

    public String getZoneDesc() {
        return zoneDesc;
    }

    public void setZoneDesc(String zoneDesc) {
        this.zoneDesc = zoneDesc;
    }

    public Integer getRegionID() {
        return regionID;
    }

    public void setRegionID(Integer regionID) {
        this.regionID = regionID;
    }

    public String getRegionDesc() {
        return regionDesc;
    }

    public void setRegionDesc(String regionDesc) {
        this.regionDesc = regionDesc;
    }

    public Integer getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(Integer provinceID) {
        this.provinceID = provinceID;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getServiceAreaTypeID() {
        return serviceAreaTypeID;
    }

    public void setServiceAreaTypeID(Integer serviceAreaTypeID) {
        this.serviceAreaTypeID = serviceAreaTypeID;
    }

    public Object getServiceAreaTypeDesc() {
        return serviceAreaTypeDesc;
    }

    public void setServiceAreaTypeDesc(Object serviceAreaTypeDesc) {
        this.serviceAreaTypeDesc = serviceAreaTypeDesc;
    }

    public Integer getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }

    public Object getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(Object employeeName) {
        this.employeeName = employeeName;
    }

}
