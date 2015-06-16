package com.dumposk129.create.stories.app.create_stories;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.dumposk129.create.stories.app.model.Frame;
import com.dumposk129.create.stories.app.sql.DatabaseHelper;
import com.dumposk129.create.stories.app.sql.Schema;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by DumpOSK129
 */
public class SelectCharacter  extends ActionBarActivity implements View.OnClickListener {
    private Button btnImage, btnGallery, btnNext;
    private static int RESULT_LOAD_IMG = 1;
    private String imgDecodableString;
    private ImageView imgView;
    private Bitmap bitmap;
    Intent intent;
    private boolean hasBg = false;
    private String pathBg;
    private long frame_id;
    private long story_id;
    private long frame_order;
    private final String PATH = "StoryApp/StoryName/Frame";

    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + PATH );

    SQLiteDatabase db;

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

        showImage();
    }

    /* Show Image from database */
    private void showImage() {
        byte[] bytes = null;
        db = SQLiteDatabase.openDatabase(DatabaseHelper.DATABASE_NAME, null, Context.MODE_PRIVATE);
        Cursor c = db.query(Schema.TABLE_FRAME, new String[]{Schema.KEY_PATH_PIC}, Schema.KEY_FRAME_ORDER, new String[]{" = " + story_id},
                null, null, Schema.KEY_FRAME_ORDER, null);
        while (c.isAfterLast() == false) {
            c.moveToNext();
        }

        c.moveToFirst();
        bytes = c.getBlob(c.getColumnIndex(Schema.KEY_PATH_PIC));
        c.close();
        db.close();

        imgView.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        Toast.makeText(getApplicationContext(), "Read Image form database successfully", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        if (v == btnImage) {
            intent = new Intent(SelectCharacter.this, PhotoCharacter.class);
            startActivity(intent);
        } else if (v == btnGallery) {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, RESULT_LOAD_IMG);
        } else if (v == btnNext) {
            if(hasBg){
                createDirectory();
                frame_id = createFrame();
                writeImagePath(bitmap);

                // TODO: Update Path
                updatePath();
                intent = new Intent(SelectCharacter.this, AudioRecording.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(getApplicationContext(),"Please select an action", Toast.LENGTH_LONG).show();
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

                /* Convert to bitmap */
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] bitmapData = baos.toByteArray();
                bitmap = BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length);
            }
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

    /* Write image path */
    private void writeImagePath(Bitmap bitmap){
        FileOutputStream fos;
        boolean success = false;
        File file = new File(dir, "bg.jpg"); // TODO: change bg.jpg to framename.
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
            pathBg = file.getPath();
            Toast.makeText(getApplicationContext(), "Save path success : "+file.getPath(), Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(), "Error during path image", Toast.LENGTH_LONG).show();
        }
    }

    /* Create Frame */
    private long createFrame() {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        Frame frame = new Frame();
        frame.setFrameOrder((int)frame_order);
        frame.setStepId(0);
        frame.setStoryId(2);
        return db.createNewFrame(frame);
    }

    /* Update Path*/
    private void updatePath() {
        if(pathBg != ""){
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            db.updatePath((int) frame_id, 1, pathBg);
        }
    }
}