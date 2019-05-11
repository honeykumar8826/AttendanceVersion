package com.attendnce.cloudanalogy.attendancev1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PhotoUpload extends AppCompatActivity {
    public static int RESULT_LOAD_IMAGE = 1;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_upload);
/*
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);*/


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            dialog = new ProgressDialog(PhotoUpload.this);
            dialog.setMessage("Please wait......");
            dialog.setCancelable(false);
            dialog.show();

            Uri selectedImage = data.getData();

            SharedPreferences sharedPreferences = getSharedPreferences("SavedPhoto", Context.MODE_PRIVATE);

            Bitmap photo = null;
            try {
                photo = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);

            byte[] b = baos.toByteArray();

            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

            SharedPreferences.Editor photoEdit= sharedPreferences.edit();

            photoEdit.putString("image", encodedImage);

            photoEdit.commit();

            dialog.dismiss();

            Intent userActivity=new Intent(PhotoUpload.this,UserActivity.class);
            startActivity(userActivity);
            finish();
        }else {
            Intent userActivity=new Intent(PhotoUpload.this,UserActivity.class);
            startActivity(userActivity);
            finish();
        }
    }
}
