
package com.webdoc.ibcc.ResponseModels.UserLoginResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerProfile {

    @SerializedName("isAppointmentAvailabe")
    @Expose
    private Boolean isAppointmentAvailabe;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("father_name")
    @Expose
    private String fatherName;
    @SerializedName("cnic")
    @Expose
    private String cnic;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("domicile")
    @Expose
    private String domicile;
    @SerializedName("birth_place")
    @Expose
    private String birthPlace;
    @SerializedName("p_add")
    @Expose
    private String pAdd;
    @SerializedName("p_city")
    @Expose
    private String pCity;
    @SerializedName("p_province_id")
    @Expose
    private String pProvinceId;
    @SerializedName("p_country_id")
    @Expose
    private String pCountryId;
    @SerializedName("c_add")
    @Expose
    private String cAdd;
    @SerializedName("c_city")
    @Expose
    private String cCity;
    @SerializedName("c_province_id")
    @Expose
    private String cProvinceId;
    @SerializedName("c_country_id")
    @Expose
    private String cCountryId;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("passport_siz_image")
    @Expose
    private String passportSizImage;
    @SerializedName("nationality")
    @Expose
    private String nationality;

    public Boolean getIsAppointmentAvailabe() {
        return isAppointmentAvailabe;
    }

    public void setIsAppointmentAvailabe(Boolean isAppointmentAvailabe) {
        this.isAppointmentAvailabe = isAppointmentAvailabe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getpAdd() {
        return pAdd;
    }

    public void setpAdd(String pAdd) {
        this.pAdd = pAdd;
    }

    public String getpCity() {
        return pCity;
    }

    public void setpCity(String pCity) {
        this.pCity = pCity;
    }

    public String getpProvinceId() {
        return pProvinceId;
    }

    public void setpProvinceId(String pProvinceId) {
        this.pProvinceId = pProvinceId;
    }

    public String getpCountryId() {
        return pCountryId;
    }

    public void setpCountryId(String pCountryId) {
        this.pCountryId = pCountryId;
    }

    public String getcAdd() {
        return cAdd;
    }

    public void setcAdd(String cAdd) {
        this.cAdd = cAdd;
    }

    public String getcCity() {
        return cCity;
    }

    public void setcCity(String cCity) {
        this.cCity = cCity;
    }

    public String getcProvinceId() {
        return cProvinceId;
    }

    public void setcProvinceId(String cProvinceId) {
        this.cProvinceId = cProvinceId;
    }

    public String getcCountryId() {
        return cCountryId;
    }

    public void setcCountryId(String cCountryId) {
        this.cCountryId = cCountryId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassportSizImage() {
        return passportSizImage;
    }

    public void setPassportSizImage(String passportSizImage) {
        this.passportSizImage = passportSizImage;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

}
