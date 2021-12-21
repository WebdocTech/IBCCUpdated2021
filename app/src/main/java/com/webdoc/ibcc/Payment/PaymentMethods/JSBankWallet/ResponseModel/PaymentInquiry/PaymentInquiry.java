
package com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ResponseModel.PaymentInquiry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentInquiry {

    @SerializedName("AccountInquiryResponse")
    @Expose
    private AccountInquiryResponse accountInquiryResponse;

    public AccountInquiryResponse getAccountInquiryResponse() {
        return accountInquiryResponse;
    }

    public void setAccountInquiryResponse(AccountInquiryResponse accountInquiryResponse) {
        this.accountInquiryResponse = accountInquiryResponse;
    }

}
