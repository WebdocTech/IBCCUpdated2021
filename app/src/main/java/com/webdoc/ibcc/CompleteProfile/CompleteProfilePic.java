package com.webdoc.ibcc.CompleteProfile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.google.firebase.database.DatabaseReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Permissions.ActivityPermissions;
import com.webdoc.ibcc.PictureUpload.PictureUpload;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.ActivityCompleteProfilePicBinding;

public class CompleteProfilePic extends AppCompatActivity {
    AlertDialog imageChooserAlertDialog;
    int imageChooserSelector = 0;
    Uri imageUri;
    public static final int CAMERA_REQUEST_CODE = 200;
    public static final int GALLERY_REQUEST_CODE = 300;
    public static final int READ_WRITE_REQUEST_CODE = 400;
    DatabaseReference reference;
    Boolean imageCheck;
    private ActivityCompleteProfilePicBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile_pic);
        layoutBinding = ActivityCompleteProfilePicBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        clickListeners();

    }

    private void clickListeners() {
        layoutBinding.btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChooser();
            }
        });

        layoutBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompleteProfilePic.this, SuccessRegistration.class);
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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
                if (ActivityPermissions.camera(CompleteProfilePic.this, CAMERA_REQUEST_CODE)) {
                    if (ActivityPermissions.readAndWriteExternalStorage(CompleteProfilePic.this, READ_WRITE_REQUEST_CODE)) {
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
                if (ActivityPermissions.camera(CompleteProfilePic.this, CAMERA_REQUEST_CODE)) {
                    if (ActivityPermissions.readAndWriteExternalStorage(CompleteProfilePic.this, READ_WRITE_REQUEST_CODE)) {
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
                if (ActivityPermissions.camera(CompleteProfilePic.this, CAMERA_REQUEST_CODE)) {
                    if (ActivityPermissions.readAndWriteExternalStorage(CompleteProfilePic.this, READ_WRITE_REQUEST_CODE)) {
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
                if (ActivityPermissions.camera(CompleteProfilePic.this, CAMERA_REQUEST_CODE)) {
                    if (ActivityPermissions.readAndWriteExternalStorage(CompleteProfilePic.this, READ_WRITE_REQUEST_CODE)) {
                        //Your read write code.
                        openGallery();
                    }
                }
            }
        });

        imageChooserAlertDialog.show();
    }//alert

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

                layoutBinding.userImage.setImageURI(imageUri);

                Long tsLong = System.currentTimeMillis() / 1000;
                String ts = tsLong.toString();

                PictureUpload pictureUpload = new PictureUpload();
                pictureUpload.UploadPictureToServer(((BitmapDrawable) layoutBinding.userImage.getDrawable()).getBitmap(),
                        ts, this);

                Global.utils.showCustomLoadingDialog(this);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
            }
            return;
        }
    }

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
}