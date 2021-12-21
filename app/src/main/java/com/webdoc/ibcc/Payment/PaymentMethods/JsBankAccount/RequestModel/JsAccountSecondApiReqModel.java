package com.webdoc.ibcc.Payment.PaymentMethods.JsBankAccount.RequestModel;

public class JsAccountSecondApiReqModel {

    String MTI,
            ProcessingCode,
            TransmissionDatetime,
            SystemsTraceAuditNumber,
            TimeLocalTransaction,
            DateLocalTransaction,
            MerchantType,
            TransactionType,
            CNIC,
            AccountIdentification1,
            TransactionDescription,
            PersonalIdentificationNumberData;

    public String getMti() {
        return MTI;
    }

    public void setMti(String MTI) {
        this.MTI = MTI;
    }

    public String getProcessingCode() {
        return ProcessingCode;
    }

    public void setProcessingCode(String processingCode) {
        ProcessingCode = processingCode;
    }

    public String getTransmissionDatetime() {
        return TransmissionDatetime;
    }

    public void setTransmissionDatetime(String transmissionDatetime) {
        TransmissionDatetime = transmissionDatetime;
    }

    public String getSystemsTraceAuditNumber() {
        return SystemsTraceAuditNumber;
    }

    public void setSystemsTraceAuditNumber(String systemsTraceAuditNumber) {
        SystemsTraceAuditNumber = systemsTraceAuditNumber;
    }

    public String getTimeLocalTransaction() {
        return TimeLocalTransaction;
    }

    public void setTimeLocalTransaction(String timeLocalTransaction) {
        TimeLocalTransaction = timeLocalTransaction;
    }

    public String getDateLocalTransaction() {
        return DateLocalTransaction;
    }

    public void setDateLocalTransaction(String dateLocalTransaction) {
        DateLocalTransaction = dateLocalTransaction;
    }

    public String getMerchantType() {
        return MerchantType;
    }

    public void setMerchantType(String merchantType) {
        MerchantType = merchantType;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String transactionType) {
        TransactionType = transactionType;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public String getAccountIdentification1() {
        return AccountIdentification1;
    }

    public void setAccountIdentification1(String accountIdentification1) {
        AccountIdentification1 = accountIdentification1;
    }

    public String getTransactionDescription() {
        return TransactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        TransactionDescription = transactionDescription;
    }

    public String getPersonalIdentificationNumberData() {
        return PersonalIdentificationNumberData;
    }

    public void setPersonalIdentificationNumberData(String personalIdentificationNumberData) {
        PersonalIdentificationNumberData = personalIdentificationNumberData;
    }
}
