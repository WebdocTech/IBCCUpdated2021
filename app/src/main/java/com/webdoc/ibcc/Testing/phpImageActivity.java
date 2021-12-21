package com.webdoc.ibcc.Testing;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Permissions.ActivityPermissions;
import com.webdoc.ibcc.PictureUpload.PictureUpload;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.ActivityPhpImageBinding;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class phpImageActivity extends AppCompatActivity {
    private AlertDialog imageChooserAlertDialog;
    private int imageChooserSelector = 0;
    public static final int CAMERA_REQUEST_CODE = 200;
    public static final int GALLERY_REQUEST_CODE = 300;
    public static final int READ_WRITE_REQUEST_CODE = 400;
    private Uri imageUri;
    private ActivityPhpImageBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityPhpImageBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        layoutBinding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChooser();
            }
        });
    }

    public void showImageChooser() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View v = getLayoutInflater().inflate(R.layout.alert_profile_picture, null);
        dialogBuilder.setView(v);

        imageChooserAlertDialog = dialogBuilder.create();
        imageChooserAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView iv_camera = v.findViewById(R.id.iv_camera);
        ImageView iv_gallery = v.findViewById(R.id.iv_gallery);
        TextView tv_camera = v.findViewById(R.id.tv_camera);
        TextView tv_gallery = v.findViewById(R.id.tv_gallery);

        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooserSelector = 1;
                if (ActivityPermissions.camera(phpImageActivity.this, CAMERA_REQUEST_CODE)) {
                    if (ActivityPermissions.readAndWriteExternalStorage(phpImageActivity.this, READ_WRITE_REQUEST_CODE)) {
                        //Your read write code.
                        openCamera();
                    }
                }
            }
        });

        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooserSelector = 1;
                if (ActivityPermissions.camera(phpImageActivity.this, CAMERA_REQUEST_CODE)) {
                    if (ActivityPermissions.readAndWriteExternalStorage(phpImageActivity.this, READ_WRITE_REQUEST_CODE)) {
                        //Your read write code.
                        openCamera();
                    }
                }
            }
        });

        iv_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooserSelector = 2;
                if (ActivityPermissions.camera(phpImageActivity.this, CAMERA_REQUEST_CODE)) {
                    if (ActivityPermissions.readAndWriteExternalStorage(phpImageActivity.this, READ_WRITE_REQUEST_CODE)) {
                        //Your read write code.
                        openGallery();
                    }
                }
            }
        });

        tv_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooserSelector = 2;
                if (ActivityPermissions.camera(phpImageActivity.this, CAMERA_REQUEST_CODE)) {
                    if (ActivityPermissions.readAndWriteExternalStorage(phpImageActivity.this, READ_WRITE_REQUEST_CODE)) {
                        //Your read write code.
                        openGallery();
                    }
                }
            }
        });

        imageChooserAlertDialog.show();
    }//alert

    public void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    public void openGallery() {
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*RequestCode for Camera*/
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setFlipHorizontally(false)
                    .setFlipVertically(false)
                    .setActivityTitle("")
                    .start(this);
            return;
        }

        /*RequestCode for Gallery*/
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();

            CropImage.activity(selectedImage)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setFlipHorizontally(false)
                    .setFlipVertically(false)
                    .setActivityTitle("")
                    .start(this);

            return;
        }

        /*RequestCode for Image Cropper*/
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                Uri imageUri = result.getUri();
                imageChooserAlertDialog.dismiss();

                //Toast.makeText(this, "imageSet", Toast.LENGTH_SHORT).show();
                layoutBinding.userImage.setImageURI(imageUri);

                Long tsLong = System.currentTimeMillis() / 1000;
                String ts = tsLong.toString();

                PictureUpload pictureUpload = new PictureUpload();
                UploadPictureToServer(((BitmapDrawable) layoutBinding.userImage.getDrawable()).getBitmap(),
                        ts, this);

                Global.utils.showCustomLoadingDialog(this);
                //Global.registerUser.setUrl(imageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
            }

            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == CAMERA_REQUEST_CODE) {
            //you have the permission now.
            if (ActivityPermissions.readAndWriteExternalStorage(this, READ_WRITE_REQUEST_CODE)) {
                //Your read write code.
                switch (imageChooserSelector) {
                    case 1:
                        openCamera();
                        break;
                    case 2:
                        openGallery();
                        break;
                }
            }
        }

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == READ_WRITE_REQUEST_CODE) {
            //you have the permission now.
            switch (imageChooserSelector) {
                case 1:
                    openCamera();
                    break;
                case 2:
                    openGallery();
                    break;
            }
        }
    }

    public void UploadPictureToServer(final Bitmap bitmap, final String picName, final Context ctx) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DOUCMENT_UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Global.utils.hideCustomLoadingDialog();
                       // Global.phpResp = response;
                        // Global.registerUser.setUrl(Global.utils.getStringImage(bitmap));
                        Global.timestamp = picName;
                        Toast.makeText(ctx, response.toString() + "jadoon", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Global.utils.hideCustomLoadingDialog();
                Toast.makeText(ctx, error.getMessage() + "00", Toast.LENGTH_SHORT).show();
            }
        }) {
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