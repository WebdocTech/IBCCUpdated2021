package com.webdoc.ibcc.Payment.RequestModel;

public class savePaymentInfoRequestModel {

    String case_id, amount_paid, bank,
            account_number, mobile_number, transection_type,
            transaction_reference_number,
            transaction_datetime,
            user_id, status, order_id,bank_charges,courier_amount,platform;

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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getIbcC_amount() {
        return ibcC_amount;
    }

    public void setIbcC_amount(String ibcC_amount) {
        this.ibcC_amount = ibcC_amount;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    String ibcC_amount, webdoc_amount;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getIBCC_amount() {
        return ibcC_amount;
    }

    public void setIBCC_amount(String ibcC_amount) {
        this.ibcC_amount = ibcC_amount;
    }

    public String getWebdoc_amount() {
        return webdoc_amount;
    }

    public void setWebdoc_amount(String webdoc_amount) {
        this.webdoc_amount = webdoc_amount;
    }

    public String getCase_id() {
        return case_id;
    }

    public void setCase_id(String case_id) {
        this.case_id = case_id;
    }

    public String getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(String amount_paid) {
        this.amount_paid = amount_paid;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getTransection_type() {
        return transection_type;
    }

    public void setTransection_type(String transection_type) {
        this.transection_type = transection_type;
    }

    public String getTransaction_reference_number() {
        return transaction_reference_number;
    }

    public void setTransaction_reference_number(String transaction_reference_number) {
        this.transaction_reference_number = transaction_reference_number;
    }

    public String getTransaction_datetime() {
        return transaction_datetime;
    }

    public void setTransaction_datetime(String transaction_datetime) {
        this.transaction_datetime = transaction_datetime;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
