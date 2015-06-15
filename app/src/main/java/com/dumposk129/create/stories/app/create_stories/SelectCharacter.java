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
 * Created by DumpOSK129
 */
public class SelectCharacter  extends ActionBarActivity implements View.OnClickListener {
    private Button btnImage, btnGallery, btnText, btnNext;
    private static int RESULT_LOAD_IMG = 1;
    private String imgDecodableString;
    private ImageView imgView;
    private Bitmap bitmap;
    Intent intent;
    private final String PATH = "StoryApp/Test/Frame";
    private final String BACKGROUND = "Background";
    private final String CHARACTER = "Character";
    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + PATH + "/" + BACKGROUND);
    File dir1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + PATH + "/" + CHARACTER);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_bg_char);

        btnImage = (Button) findViewById(R.id.btnImage);
        btnGallery = (Button) findViewById(R.id.btnGallery);
        btnText = (Button) findViewById(R.id.btnText);
        btnNext = (Button) findViewById(R.id.btnNext);
        imgView = (ImageView) findViewById(R.id.full_image_view);

        btnImage.setText("Character");
        btnText.setVisibility(View.VISIBLE);

        btnImage.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
        btnText.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        // Show Image in folder
        ShowImageInFolder();

        /*// get intent data
        Intent i = getIntent();

        // Selected image id
        if (i.getExtras() != null){
            byte[] byteArr = i.getByteArrayExtra("id");
            bitmap = BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length);
            imgView.setImageBitmap(bitmap);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] bitmapData = baos.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length);
            //Toast.makeText(getApplicationContext(), "path in app : " + bitmap, Toast.LENGTH_SHORT).show();
        }*/

    }

    @Override
    public void onClick(View v) {
        if (v == btnImage) {
            //  Toast.makeText(getApplicationContext(), "Image clicked", Toast.LENGTH_SHORT).show();
            intent = new Intent(SelectCharacter.this, PhotoCharacter.class);
            startActivity(intent);
        } else if (v == btnGallery) {
            // Toast.makeText(getApplicationContext(), "Gallery clicked", Toast.LENGTH_SHORT).show();
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, RESULT_LOAD_IMG);
        } else if (v == btnText){
            Toast.makeText(getApplicationContext(), "Text clicked", Toast.LENGTH_SHORT).show();
        }
        else {
            createDirectory();
            Toast.makeText(getApplicationContext(), "Path " + bitmap , Toast.LENGTH_SHORT).show();
            writeImagePath(bitmap);
        }
    }
    /* Show image from directory */
    private void ShowImageInFolder(){
        File imgFile = dir;
        if (imgFile.exists()){
            bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgView.setImageBitmap(bitmap);
        }
    }

    /* Show image from Gallery */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
                Uri selectImage = data.getData();
                String[] filePathCol = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectImage, filePathCol, null, null, null);
                cursor.moveToFirst();

                int colIndex = cursor.getColumnIndex(filePathCol[0]);
                imgDecodableString = cursor.getString(colIndex);
                cursor.close();
                imgView = (ImageView) findViewById(R.id.full_image_view);
                imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

                 /* Convert to bitmap */
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] bitmapData = baos.toByteArray();
                bitmap = BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length);

                //Toast.makeText(getApplicationContext(), "path in gallery : " +imgDecodableString, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();
        }
    }

    /* Create Path */
    private void createDirectory() {
        try {
            if (dir.mkdirs()){
                System.out.println("Directory created");
            }else {
                System.out.println("Directory is not created");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /* Write image path */
    private void writeImagePath(Bitmap bitmap){
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