package com.webdoc.ibcc.ServerManager;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;

import org.json.JSONException;
import org.json.JSONObject;

import xyz.hasnat.sweettoast.SweetToast;

public class GetApiResultManager {
    public static Context ctx;
    public static RequestQueue requestQueue;
    VolleyListener volleyListener;

    public GetApiResultManager(Context ctx) {
        this.ctx = ctx;
        this.volleyListener = (VolleyListener) this.ctx;
    }

    public void jsonParse(final String requestType, JSONObject getParams) {
        SSLVerification sslVerification = new SSLVerification(ctx);
        requestQueue = VolleySingleton.getInstance(ctx, sslVerification.Certificate()).getRequestQueue();

        String url = Constants.BASE_URL + requestType;

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        try {
                            /* Calling VolleyListener interface method and returning responseCode */
                            volleyListener.getRequestResponse(response, requestType);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String message = null; // error message, show it in toast or dialog, whatever you want
                if (error instanceof NetworkError || error instanceof AuthFailureError || error instanceof NoConnectionError || error instanceof TimeoutError) {
                    message = "Cannot connect to Internet.";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found.";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again later.";
                }

                try {
                    SweetToast.error(ctx, message);
                    Global.utils.hideCustomLoadingDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        getRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequest.setTag(requestType);

        // Adding request to request queue
        requestQueue.add(getRequest);
    }

}



