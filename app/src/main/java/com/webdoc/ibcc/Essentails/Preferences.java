package com.webdoc.ibcc.Essentails;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    private static String KEY_USER_PIN = "userPin";
    private static String KEY_USER_FNAME = "userFName";
    private static String KEY_USER_LNAME = "userLName";
    private static String KEY_USER_PHONE = "userPhone";
    private static String KEY_USER_EMAIL = "userEmail";
    private static String KEY_USER_CNIC = "userCnic";
    private static String KEY_USER_LOGIN_TYPE = "userLoginType";
    public static Preferences sPreferences;

    public static Preferences getInstance(Context context) {
        if (sPreferences == null) {
            sPreferences = new Preferences(context);
        }
        return sPreferences;
    }

    public Preferences(Context ctx) {
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("IBCC", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void clearSharedPreferences() {
        editor.clear();
        editor.commit();
    }

    public Boolean getKeyIsLogin() {
        return prefs.getBoolean("isLogin", false);
    }

    public void setKeyIsLogin(Boolean keyIsLogin) {
        editor.putBoolean("isLogin", keyIsLogin);
        editor.commit();
    }

    public void setKeyUserFName(String FName) {
        editor.putString(KEY_USER_FNAME, FName);
        editor.commit();
    }

    public void setKeyUserLName(String LName) {
        editor.putString(KEY_USER_LNAME, LName);
        editor.commit();
    }

    public String getKeyUserEmail() {
        return prefs.getString(KEY_USER_EMAIL, null);
    }

    public void setKeyUserEmail(String keyEmail) {
        editor.putString(KEY_USER_EMAIL, keyEmail);
        editor.commit();
    }

    public String getKeyUserPin() {
        return prefs.getString(KEY_USER_PIN, null);
    }

    public void setKeyUserPin(String keyPin) {
        editor.putString(KEY_USER_PIN, keyPin);
        editor.commit();
    }

    public String getKeyUserPhone() {
        return prefs.getString(KEY_USER_PHONE, null);
    }

    public void setKeyUserPhone(String keyUserPhone) {
        editor.putString(KEY_USER_PHONE, keyUserPhone);
        editor.commit();
    }

    public String getKeyUserCnic() {
        return prefs.getString(KEY_USER_CNIC, null);
    }

    public void setKeyUserCnic(String keyUserCnic) {
        editor.putString(KEY_USER_CNIC, keyUserCnic);
        editor.commit();
    }

    public String getKeyUserLoginType() {
        return prefs.getString(KEY_USER_LOGIN_TYPE, null);
    }

    public void setKeyUserLoginType(String keyUserLoginType) {
        editor.putString(KEY_USER_LOGIN_TYPE, keyUserLoginType);
        editor.commit();
    }


}
