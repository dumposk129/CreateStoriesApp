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

/**
 * Created by DumpOSK129.
 */
public class SelectBackground extends ActionBarActivity implements View.OnClickListener {
    private Button btnImage, btnGallery, btnNext;
    private static int RESULT_LOAD_IMG = 1;
    private String imgDecodableString;
    private ImageView imgView;
    private int position;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_bg);

        btnImage = (Button) findViewById(R.id.btnImage);
        btnGallery = (Button) findViewById(R.id.btnGallery);
        btnNext = (Button) findViewById(R.id.btnNext);
        imgView = (ImageView) findViewById(R.id.showImg);

        btnImage.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        // get intent data
        Intent i = getIntent();

        // Selected image id
        if (i.getExtras() != null){
            final Bitmap bitmap = getIntent().getParcelableExtra("id");
            imgView.setImageBitmap(bitmap);
            /*position = i.getExtras().getInt("id");
            GridViewAdapter gridViewAdapter = new GridViewAdapter(this, R.layout.grid_view_row, getData());
            imgView.setImageResource((Integer) gridViewAdapter.getItem(position));
            Toast.makeText(getApplicationContext(), "path in app : ", Toast.LENGTH_SHORT).show();*/
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnImage) {
            Toast.makeText(getApplicationContext(), "Image clicked", Toast.LENGTH_SHORT).show();
            intent = new Intent(SelectBackground.this, GridPhoto.class);
            startActivity(intent);
        } else if (v == btnGallery) {
            Toast.makeText(getApplicationContext(), "Gallery clicked", Toast.LENGTH_SHORT).show();
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, RESULT_LOAD_IMG);
        } else {
            Toast.makeText(getApplicationContext(), "Next clicked", Toast.LENGTH_SHORT).show();
            CreateDirectory();
            CopyFile();
        }
    }

    /* Show Image from Gallery */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
                Uri selectImage = data.getData();
                String[] filePathCol = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectImage, filePathCol, null, null, null);
                cursor.moveToFirst();

                int colIndex = cursor.getColumnIndex(filePathCol[0]);
                imgDecodableString = cursor.getString(colIndex);
                cursor.close();
                imgView = (ImageView) findViewById(R.id.showImg);
                imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

                Toast.makeText(getApplicationContext(), "path in gallery : " +imgDecodableString, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();
        }
    }

    /* Create Path */
    private void CreateDirectory() {
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "StoryApp/Test/Frame/");
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
    private void CopyFile(){

    }
}