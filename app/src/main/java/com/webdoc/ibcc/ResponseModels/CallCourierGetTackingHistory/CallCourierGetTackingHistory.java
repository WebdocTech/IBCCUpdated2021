
package com.webdoc.ibcc.ResponseModels.CallCourierGetTackingHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallCourierGetTackingHistory {

    @SerializedName("ConsignmentNo")
    @Expose
    private String consignmentNo;
    @SerializedName("ConsigneeName")
    @Expose
    private String consigneeName;
    @SerializedName("ConsigneeAddress")
    @Expose
    private String consigneeAddress;
    @SerializedName("ConsigneeCity")
    @Expose
    private String consigneeCity;
    @SerializedName("ContactNo")
    @Expose
    private String contactNo;
    @SerializedName("ShipperName")
    @Expose
    private String shipperName;
    @SerializedName("ShipperAddress")
    @Expose
    private String shipperAddress;
    @SerializedName("TransactionDate")
    @Expose
    private String transactionDate;
    @SerializedName("OperationDesc")
    @Expose
    private String operationDesc;
    @SerializedName("ProcessDescForPortal")
    @Expose
    private String processDescForPortal;
    @SerializedName("ReceiverName")
    @Expose
    private String receiverName;
    @SerializedName("Relation")
    @Expose
    private String relation;
    @SerializedName("ReasonDesc")
    @Expose
    private String reasonDesc;
    @SerializedName("IsPortalBooking")
    @Expose
    private String isPortalBooking;
    @SerializedName("HomeBranch")
    @Expose
    private String homeBranch;
    @SerializedName("DestBranch")
    @Expose
    private String destBranch;
    @SerializedName("codAmount")
    @Expose
    private Double codAmount;
    @SerializedName("Weight")
    @Expose
    private Double weight;
    @SerializedName("Pcs")
    @Expose
    private Double pcs;
    @SerializedName("ServiceType")
    @Expose
    private String serviceType;
    @SerializedName("OriginCity")
    @Expose
    private String originCity;
    @SerializedName("MDNNo")
    @Expose
    private String mDNNo;
    @SerializedName("CallDate")
    @Expose
    private String callDate;
    @SerializedName("CallTime")
    @Expose
    private String callTime;
    @SerializedName("CallStatus")
    @Expose
    private Object callStatus;
    @SerializedName("CallRemarks")
    @Expose
    private Object callRemarks;

    public String getConsignmentNo() {
        return consignmentNo;
    }

    public void setConsignmentNo(String consignmentNo) {
        this.consignmentNo = consignmentNo;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public String getConsigneeCity() {
        return consigneeCity;
    }

    public void setConsigneeCity(String consigneeCity) {
        this.consigneeCity = consigneeCity;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getShipperAddress() {
        return shipperAddress;
    }

    public void setShipperAddress(String shipperAddress) {
        this.shipperAddress = shipperAddress;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getOperationDesc() {
        return operationDesc;
    }

    public void setOperationDesc(String operationDesc) {
        this.operationDesc = operationDesc;
    }

    public String getProcessDescForPortal() {
        return processDescForPortal;
    }

    public void setProcessDescForPortal(String processDescForPortal) {
        this.processDescForPortal = processDescForPortal;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getReasonDesc() {
        return reasonDesc;
    }

    public void setReasonDesc(String reasonDesc) {
        this.reasonDesc = reasonDesc;
    }

    public String getIsPortalBooking() {
        return isPortalBooking;
    }

    public void setIsPortalBooking(String isPortalBooking) {
        this.isPortalBooking = isPortalBooking;
    }

    public String getHomeBranch() {
        return homeBranch;
    }

    public void setHomeBranch(String homeBranch) {
        this.homeBranch = homeBranch;
    }

    public String getDestBranch() {
        return destBranch;
    }

    public void setDestBranch(String destBranch) {
        this.destBranch = destBranch;
    }

    public Double getCodAmount() {
        return codAmount;
    }

    public void setCodAmount(Double codAmount) {
        this.codAmount = codAmount;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getPcs() {
        return pcs;
    }

    public void setPcs(Double pcs) {
        this.pcs = pcs;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getOriginCity() {
        return originCity;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public String getMDNNo() {
        return mDNNo;
    }

    public void setMDNNo(String mDNNo) {
        this.mDNNo = mDNNo;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public Object getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(Object callStatus) {
        this.callStatus = callStatus;
    }

    public Object getCallRemarks() {
        return callRemarks;
    }

    public void setCallRemarks(Object callRemarks) {
        this.callRemarks = callRemarks;
    }

}
