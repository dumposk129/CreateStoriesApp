package com.dumposk129.create.stories.app.create_stories;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.sql.DatabaseHelper;

/**
 * Created by DumpOSK129
 */
public class SelectCharacter extends ActionBarActivity implements View.OnClickListener {
    private Button btnImage, btnGallery, btnNext, btnText;
    private static int RESULT_LOAD_IMG = 1;
    private String picturePath, path_pic;
    private String pathBg;
    private ImageView imgView;
    private Bitmap bitmap, bmp;
    private long frame_id, frame_order;
    private int sId;

    Intent intent;
    DatabaseHelper db;

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
        btnGallery.setVisibility(View.GONE);

        btnImage.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnText.setOnClickListener(this);

        frame_id = (int)getIntent().getExtras().getLong("frame_id");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("sId")) {
                sId = bundle.getInt("sId");
            }

            if (bundle.containsKey("frame_id")){
                frame_id = (int)getIntent().getExtras().getLong("frame_id");
            }

            if (bundle.containsKey("frame_order")){
                frame_order = (int)getIntent().getExtras().getLong("frame_order");
            }
        }

        /*Toast.makeText(getApplicationContext(), "sId: "+sId, Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "frame_id: "+frame_id, Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "frame_order: "+frame_order, Toast.LENGTH_SHORT).show();*/

        /* This method is show image */
        showImage();
    }

    /* Show Image from database */
    private void showImage() {
        String path_pic = null;
        db = new DatabaseHelper(getApplicationContext());
        path_pic = db.getPath(2);

        imgView.setImageBitmap(BitmapFactory.decodeFile(path_pic));
        bitmap = BitmapFactory.decodeFile(path_pic);
    }

    @Override
    public void onClick(View v) {
        if (v == btnImage) {
            intent = new Intent(SelectCharacter.this, PhotoCharacter.class);
            intent.putExtra("sId", sId);
            intent.putExtra("frame_id", frame_id);
            intent.putExtra("frame_order", frame_order);
            startActivity(intent);
        } /*else if (v == btnGallery) {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image*//*");
            startActivityForResult(intent, RESULT_LOAD_IMG);
        } */else if (v == btnText) {
            intent = new Intent(SelectCharacter.this, AddText.class);
            intent.putExtra("sId", sId);
            intent.putExtra("frame_id", frame_id);
            intent.putExtra("frame_order", frame_order);
            startActivity(intent);
        } else if (v == btnNext) {
            /* Save photo path */
            pathBg = PhotoHelper.writeImagePath(bitmap);

            // TODO: Update Path
            PhotoHelper.updatePath(getApplicationContext(), (int)frame_id, pathBg); // Update Path in db.

            intent = new Intent(SelectCharacter.this, AudioRecording.class);
            intent.putExtra("sId", sId);
            intent.putExtra("frame_id", frame_id);
            intent.putExtra("frame_order", frame_order);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(),"Please select an action", Toast.LENGTH_LONG).show();
        }
    }

  /*  *//* Show Image from Gallery *//*
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
                picturePath = cursor.getString(colIndex);
                cursor.close();

                *//*Convert to bitmap*//*
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp = resizeBitmap(bitmap);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArr = stream.toByteArray();

                intent = new Intent(SelectCharacter.this.getApplicationContext(), MoveImageFromGallery.class);
                intent.putExtra("imagePath", byteArr);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap resizeBitmap(Bitmap bitmap){
        float resizedPercent = 0.82f;
        return Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*resizedPercent), (int)(bitmap.getHeight()*resizedPercent), true);
    }*/
}