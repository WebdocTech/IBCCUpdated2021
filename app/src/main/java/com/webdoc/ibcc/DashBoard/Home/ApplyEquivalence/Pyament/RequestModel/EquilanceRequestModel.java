package com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.Pyament.RequestModel;

public class EquilanceRequestModel {

    String caseId, amountPaid, bank,
            accountnumber, mobilenumber, transactionType,
            transactionReferenceNumber,
            transactionDateTime,
            userId, status, ibccAmount, webdocAmount, bank_charges, courier_amount,order_id,platform;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getBank_charges() {
        return bank_charges;
    }

    public void setBank_charges(String bank_charges) {
        this.bank_charges = bank_charges;
    }

    public String getCourier_amount() {
        return courier_amount;
    }

    public void setCourier_amount(String courier_amount) {
        this.courier_amount = courier_amount;
    }

    public String getCase_id() {
        return caseId;
    }

    public void setCase_id(String case_id) {
        this.caseId = case_id;
    }

    public String getAmount_paid() {
        return amountPaid;
    }

    public void setAmount_paid(String amount_paid) {
        this.amountPaid = amount_paid;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccount_number() {
        return accountnumber;
    }

    public void setAccount_number(String account_number) {
        this.accountnumber = account_number;
    }

    public String getMobile_number() {
        return mobilenumber;
    }

    public void setMobile_number(String mobile_number) {
        this.mobilenumber = mobile_number;
    }

    public String getTransection_type() {
        return transactionType;
    }

    public void setTransection_type(String transection_type) {
        this.transactionType = transection_type;
    }

    public String getTransaction_reference_number() {
        return transactionReferenceNumber;
    }

    public void setTransaction_reference_number(String transaction_reference_number) {
        this.transactionReferenceNumber = transaction_reference_number;
    }

    public String getTransaction_datetime() {
        return transactionDateTime;
    }

    public void setTransaction_datetime(String transaction_datetime) {
        this.transactionDateTime = transaction_datetime;
    }

    public String getUser_id() {
        return userId;
    }

    public void setUser_id(String user_id) {
        this.userId = user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIbcC_amount() {
        return ibccAmount;
    }

    public void setIbcC_amount(String ibcC_amount) {
        this.ibccAmount = ibcC_amount;
    }

    public String getWebdoc_amount() {
        return webdocAmount;
    }

    public void setWebdoc_amount(String webdoc_amount) {
        this.webdocAmount = webdoc_amount;
    }
}
