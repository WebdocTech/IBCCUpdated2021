package com.webdoc.ibcc.ServerManager;

import android.content.Context;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;

import xyz.hasnat.sweettoast.SweetToast;

public class PostApiResultManager {
    public static Context ctx;
    public static RequestQueue requestQueue;
    VolleyListener volleyListener;

    public PostApiResultManager(Context ctx) {
        this.ctx = ctx;
        this.volleyListener = (VolleyListener) this.ctx;
    }

    public void jsonParse(final String requestType, JSONObject postParams) {
        /* SSL Verification Certificate */
        SSLVerification sslVerification = new SSLVerification(ctx);
        //Volley.newRequestQueue(ctx, sslVerification.Certificate());
        requestQueue = VolleySingleton.getInstance(ctx, sslVerification.Certificate()).getRequestQueue();

        String url = Constants.BASE_URL + requestType;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, postParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
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
                /*Log.e("ErrorResponse", error.getMessage());
                Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_LONG).show();*/
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
        }) {
            /* Passing some request headers */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjReq.setTag(requestType);

        // Adding request to request queue
        requestQueue.add(jsonObjReq);
    }
}
