package com.webdoc.ibcc.ServerManager.CallCourier;

import android.app.Activity;

import com.webdoc.ibcc.DataModel.EducationDetailModel;
import com.webdoc.ibcc.DataModel.LoginUser;
import com.webdoc.ibcc.DataModel.RegisterUser;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.DocumentDetailModel;
import com.webdoc.ibcc.Model.DocumentSelectionModel;
import com.webdoc.ibcc.ServerManager.GetApiResultManager;
import com.webdoc.ibcc.ServerManager.PostApiResultManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CallCourierVolleyRequestController {
    Activity context;
    CallCourierApiResultManager callCourierApiResultManager;

    public CallCourierVolleyRequestController(Activity context) {
        this.context = context;
        callCourierApiResultManager = new CallCourierApiResultManager(context);
    }

    /*TODO: CALL COURIER*/
    public void getCityListByService() {
        callCourierApiResultManager.jsonParse(Constants.GETCITYLISTBYSERVICE, "");
    }

    public void getAreasByCity(String cityID) {
        callCourierApiResultManager.jsonParse(Constants.GETAREASBYCITY, cityID);
    }

    public void saveBooking(String params) {
        callCourierApiResultManager.jsonParse(Constants.SAVEBOOKING, params);
    }

    public void getTackingHistory(String consignNo) {
        callCourierApiResultManager.jsonParse(Constants.GETTACKINGHISTORY  , consignNo);
    }

}
