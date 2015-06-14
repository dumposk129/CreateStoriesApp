package com.dumposk129.create.stories.app.create_stories;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dumposk129.create.stories.app.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by DumpOSK129.
 */
public class SelectBackground extends ActionBarActivity implements View.OnClickListener {
    private Button btnImage, btnGallery, btnNext;
    private static int RESULT_LOAD_IMG = 1;
    private static int RESULT_LOAD_FROM_BG = 2;
    private String imgDecodableString;
    private ImageView imgView;
    private Bitmap bitmap;
    Intent intent;
    private final String PATH = "StoryApp/Test/Frame";
    private String NAME = "Background";
    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + PATH + "/" + NAME);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_bg_char);

        btnImage = (Button) findViewById(R.id.btnImage);
        btnGallery = (Button) findViewById(R.id.btnGallery);
        btnNext = (Button) findViewById(R.id.btnNext);
        imgView = (ImageView) findViewById(R.id.full_image_view);

        btnImage.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        // get intent data
        Intent i = getIntent();

        // Selected image id
        if (i.getExtras() != null){
            byte[] byteArr = i.getByteArrayExtra("bg");
            bitmap = BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length);
            imgView.setImageBitmap(bitmap);
            Toast.makeText(getApplicationContext(), "path in app : " + bitmap, Toast.LENGTH_SHORT).show();
        }
    }

/*
    @Override
    protected void onStart() {
        super.onStart();
    }*/

    @Override
    public void onClick(View v) {
        if (v == btnImage) {
          //  Toast.makeText(getApplicationContext(), "Image clicked", Toast.LENGTH_SHORT).show();
            intent = new Intent(SelectBackground.this, PhotoBackground.class);
            startActivity(intent);
        } else if (v == btnGallery) {
           // Toast.makeText(getApplicationContext(), "Gallery clicked", Toast.LENGTH_SHORT).show();
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, RESULT_LOAD_IMG);
        } else {
          //  Toast.makeText(getApplicationContext(), "Next clicked", Toast.LENGTH_SHORT).show();
            createDirectory();
          //  Toast.makeText(getApplicationContext(), "Path " + bitmap , Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), "Path Gallery: " + imgDecodableString , Toast.LENGTH_SHORT).show();
            writeImageFromApp(bitmap);
           // writeImageFromGallery(imgDecodableString);
           /* intent = new Intent(SelectBackground.this, SelectCharacter.class);
            startActivity(intent);*/
        }
    }

    /* Show Image from Gallery */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && data != null) {
                Uri selectImage = data.getData();
                String[] filePathCol = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectImage, filePathCol, null, null, null);
                cursor.moveToFirst();

                int colIndex = cursor.getColumnIndex(filePathCol[0]);
                imgDecodableString = cursor.getString(colIndex);
                cursor.close();
                imgView = (ImageView) findViewById(R.id.full_image_view);
                imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                bitmap = BitmapFactory.decodeFile(imgDecodableString);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] bitmapData = baos.toByteArray();
                bitmap = BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length);
                Toast.makeText(getApplicationContext(), "path in gallery : " +imgDecodableString, Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "bitmapData : " +bitmapData, Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "bitmap : " +bitmap, Toast.LENGTH_LONG).show();
            } /*else if(requestCode == RESULT_LOAD_FROM_BG && resultCode == RESULT_OK && data != null) {
                byte[] byteArr = data.getByteArrayExtra("bg");
                bitmap = BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length);
                imgView.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(), "path in app : " + bitmap, Toast.LENGTH_SHORT).show();
            }*/
            else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();
        }
    }

    /* Create Path */
    private void createDirectory() {
        dir.mkdirs();
    }

    /* Write image path from exist in app*/
    private void writeImageFromApp(Bitmap bitmap){
        FileOutputStream fos;
        boolean success = false;
        File file = new File(dir, "" + bitmap);
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), ""+e,Toast.LENGTH_SHORT).show();
        }

        if (success == true){
            Toast.makeText(getApplicationContext(), "Save path success", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "Error during path image", Toast.LENGTH_SHORT).show();
        }
    }
}