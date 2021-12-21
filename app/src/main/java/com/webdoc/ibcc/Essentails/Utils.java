package com.webdoc.ibcc.Essentails;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.snackbar.Snackbar;
import com.wajahatkarim3.easyflipview.EasyFlipView;
import com.webdoc.ibcc.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Utils {
    public Handler handler;
    public EasyFlipView easyFlipView;
    public static AlertDialog alertDialog;
    public static boolean isShowingCustomProgress = false;
    public MediaPlayer mediaPlayer;
    public Animation animation;
    // Define the code block to be executed
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Insert custom code here
            easyFlipView.flipTheView();
            // Repeat every 2 seconds
            handler.postDelayed(runnable, 500);  /*1000*/
        }
    };


    public void showCustomLoadingDialog(Activity activity) {
        handler = new Handler();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        View v = activity.getLayoutInflater().inflate(R.layout.loading_screen, null);
        dialogBuilder.setView(v);

        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        easyFlipView = (EasyFlipView) v.findViewById(R.id.easyFlipView);
        alertDialog.setCancelable(false);
        alertDialog.show();
        isShowingCustomProgress = true;
        // Start the Runnable immediately
        handler.post(runnable);
    }//alert

    public void hideCustomLoadingDialog() {
        handler.removeCallbacks(runnable);
        alertDialog.dismiss();
        isShowingCustomProgress = false;
    }


    public static boolean HaveNetwork(Context context) {
        boolean Have_WIFI = false;
        boolean Have_Mobile_Data = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for (NetworkInfo info : networkInfos) {
            if (info.getTypeName().equalsIgnoreCase("WIFI")) {
                if (info.isConnected())
                    Have_WIFI = true;
            }
            if (info.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (info.isConnected())
                    Have_Mobile_Data = true;
            }
        }
        return Have_Mobile_Data || Have_WIFI;
    }

    public void showSuccessSnakeBar(Activity activity, String msg) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setActionTextColor(activity.getResources().getColor(R.color.white));
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(activity.getResources().getColor(R.color.myAppColor));
        snackbar.show();
    }

    public void showErrorSnakeBar(Activity activity, String msg) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setActionTextColor(activity.getResources().getColor(R.color.white));
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(activity.getResources().getColor(R.color.red));
        snackbar.show();
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public String encodeFileToBase64Binary(File yourFile) {
        int size = (int) yourFile.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(yourFile));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String encoded = Base64.encodeToString(bytes, Base64.NO_WRAP);
        return encoded;
    }

    private static byte[] readBytes(Uri uri, ContentResolver resolver, boolean thumbnail)
            throws IOException {
        // this dynamically extends to take the bytes you read
        InputStream inputStream = resolver.openInputStream(uri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        if (!thumbnail) {
            // this is storage overwritten on each iteration with bytes
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            // we need to know how may bytes were read to write them to the
            // byteBuffer
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
        }
        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }


    public Bitmap getDecodedImage(String imageString) {
        byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String enccriptData(String strToEncrypt) {
        String encoded = "";
        byte[] encrypted = null;
        try {
            byte[] publicBytes = Base64.decode("\"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiO1lWgkTZeDWQgXlDF8t92YLYZm/ENvCvKPJNuj9WZfGCF5RIUFaYolb/HAhoAHKxgYRUS81WFfHuMROT+B/d0cW+Ii/sqLzTfFjepExonCj1I8m4WLdBAdZCRlWLo+bdO39OpxfK14XaPmRMdb8+uTpZ0hZBhDzZDnXChCm4fgsn63ZT2VEHdHX8PgmKTViR4VXsvyZCkT60FiEix2JdLCuSGF+tPr9GQnlSDJK4vRCZl+/TD/IaIbeAFWcx0Y6kdLpUBBUHbxY8cXcsr/HfJ6/WMEBSlUCOvbZhrx41yC/182WMPppaqCDeDamDV2T+ufzrQkT1nU40gm9h7uoXwIDAQAB\"", Base64.DEFAULT);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(keySpec);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING"); //or try with "RSA"
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            encrypted = cipher.doFinal(strToEncrypt.getBytes());
            encoded = Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encoded;
    }

    public Animation startBlinkAnimation(Activity activity) {
        animation = AnimationUtils.loadAnimation(activity,
                R.anim.blink_repeat);
        return animation;
    }

    public boolean isInternerConnected(Context cntx) {
        ConnectivityManager cm = (ConnectivityManager) cntx.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


}
