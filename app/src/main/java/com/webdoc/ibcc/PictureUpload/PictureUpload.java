package com.webdoc.ibcc.PictureUpload;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;

import java.util.HashMap;
import java.util.Map;

public class PictureUpload {

    public void UploadPictureToServer(final Bitmap bitmap, final String picName, final Context ctx) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.PICTURE_UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Global.utils.hideCustomLoadingDialog();
                        // Global.registerUser.setUrl(Global.utils.getStringImage(bitmap));
                        Global.timestamp = picName;

                        Toast.makeText(ctx, "Success", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Global.utils.hideCustomLoadingDialog();
                Toast.makeText(ctx, error.getMessage() + "00", Toast.LENGTH_SHORT).show();
            }}) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("name", picName);
                String uploadImage = Global.utils.getStringImage(bitmap);
                hashMap.put("image", uploadImage);
                //bitmap.recycle();
                System.gc();
                return hashMap;
            }
        };
        RequestQueue requestQueue = new Volley().newRequestQueue(ctx);
        requestQueue.add(stringRequest);
    }

}
