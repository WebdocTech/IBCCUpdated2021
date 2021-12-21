package com.webdoc.ibcc.ServerManager;

import org.json.JSONException;
import org.json.JSONObject;

public interface VolleyListener {
    /* These methods will be called when you implement interface on activity at top */

    public void getRequestResponse(JSONObject response, String requestType) throws JSONException;
}
