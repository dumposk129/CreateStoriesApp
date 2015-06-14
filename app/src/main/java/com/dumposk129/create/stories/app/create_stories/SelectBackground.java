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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by DumpOSK129.
 */
public class SelectBackground extends ActionBarActivity implements View.OnClickListener {
    private Button btnImage, btnGallery, btnNext;
    private static int RESULT_LOAD_IMG = 1;
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
            byte[] byteArr = i.getByteArrayExtra("id");
            bitmap = BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length);
            imgView.setImageBitmap(bitmap);
            Toast.makeText(getApplicationContext(), "path in app : " + bitmap, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnImage) {
          //  Toast.makeText(getApplicationContext(), "Image clicked", Toast.LENGTH_SHORT).show();
            intent = new Intent(SelectBackground.this, PhotoBackground.class);
            startActivity(intent);
        } else if (v == btnGallery) {
           // Toast.makeText(getApplicationContext(), "Gallery clicked", Toast.LENGTH_SHORT).show();
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
                Toast.makeText(getApplicationContext(), "path in gallery : " +imgDecodableString, Toast.LENGTH_LONG).show();

                //StringToBitmap(imgDecodableString);
                /*byte[] encodeByte = Base64.decode(imgDecodableString, Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);*/

//                Toast.makeText(getApplicationContext(), "path in gallery : " +imgDecodableString, Toast.LENGTH_LONG).show();

            } else {
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

    /*private Bitmap StringToBitmap(String imgDecodableString){
        try{
            byte[] encodeByte = Base64.decode(imgDecodableString, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            Toast.makeText(getApplicationContext(),"bmp "+bitmap, Toast.LENGTH_SHORT).show();
            return bitmap;
        }catch (Exception e){
            e.getMessage();
            Toast.makeText(getApplicationContext(), "err "+e, Toast.LENGTH_SHORT).show();
            return null;
        }
    }*/

    /* Write image path from gallery */
     /*  private void writeImageFromGallery(String imgDecodableString) {
     FileOutputStream fos = null;
        File file = new File(dir, "" + imgDecodableString);
        try {
            fos = openFileOutput(imgDecodableString, Context.MODE_PRIVATE);
            byte[] decodeString
            osw.write(imgDecodableString);
            osw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fos;
        boolean success = false;
        File file = new File(dir, "" + imgDecodableString);
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
            Toast.makeText(getApplicationContext(), "Save image success", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "Error during save image", Toast.LENGTH_SHORT).show();
        }
    }*/
}