
package com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ResponseModel.JSAuthentication.SuccessResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JSAuthenticationSuccessResponse {

    @SerializedName("Token")
    @Expose
    private String token;
    @SerializedName("expires_in")
    @Expose
    private String expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

}
