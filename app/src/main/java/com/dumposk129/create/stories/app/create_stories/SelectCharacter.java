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
import java.io.FileNotFoundException;
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
    private int position;
    Intent intent;
    private Uri imagePath;
    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "StoryApp/Test/Frame/");

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
            //  Toast.makeText(getApplicationContext(), "Next clicked", Toast.LENGTH_SHORT).show();
            createDirectory();
            Toast.makeText(getApplicationContext(), "Path " + bitmap , Toast.LENGTH_SHORT).show();
            //findPath(bitmap);
          //  writeFile(bitmap);

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

            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private String findPath(Uri uri){
        String imgPath;

        String[] column = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, column, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            imgPath = cursor.getString(columnIndex);
        }else {
            imgPath = uri.getPath();
        }
        return imgPath;
    }

    /* Create Path */
    private void createDirectory() {
        //File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "StoryApp/Test/Frame/");
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

    /* Copy Path to Directory*/
    private void writeFile(/*String data,*/ Bitmap bitmap){
        File file = new File(dir, "" + bitmap);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
