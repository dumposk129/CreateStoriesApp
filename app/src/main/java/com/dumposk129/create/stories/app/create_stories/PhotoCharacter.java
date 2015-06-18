package com.dumposk129.create.stories.app.create_stories;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.sql.DatabaseHelper;

/**
 * Created by DumpOSK129.
 */
public class PhotoCharacter extends ActionBarActivity implements View.OnClickListener, View.OnTouchListener{
    private ImageView imgOldSelected, imgFullSize, img, imgSelected, imgTicker;
    private int[] imgsId = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6, R.drawable.a7, R.drawable.a8, R.drawable.a9, R.drawable.a10, R.drawable.a11, R.drawable.a12, R.drawable.a13, R.drawable.a14, R.drawable.a15,
            R.drawable.a16, R.drawable.a17, R.drawable.a18, R.drawable.a19, R.drawable.a20, R.drawable.a21, R.drawable.a22, R.drawable.a23, R.drawable.a24, R.drawable.a25, R.drawable.a26, R.drawable.a27, R.drawable.a28, R.drawable.a29, R.drawable.a30, R.drawable.a31,
            R.drawable.a32, R.drawable.a33, R.drawable.a34, R.drawable.a35, R.drawable.h1, R.drawable.h2, R.drawable.h3, R.drawable.h4, R.drawable.h5, R.drawable.h6, R.drawable.h7, R.drawable.h8, R.drawable.h9, R.drawable.h10, R.drawable.h11, R.drawable.a12,
            R.drawable.h13, R.drawable.h14, R.drawable.h15, R.drawable.h16, R.drawable.h17, R.drawable.h18, R.drawable.h19, R.drawable.h20, R.drawable.h21, R.drawable.h22, R.drawable.h23, R.drawable.h24, R.drawable.h25};
    private Button btnOk;
    private Bitmap bitmap;
    private int frame_id;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_gallery);

        final LinearLayout gallery = (LinearLayout) findViewById(R.id.images_gallery);
        int position = 0;

        imgFullSize = (ImageView) findViewById(R.id.full_image_view);
        imgTicker = (ImageView) findViewById(R.id.imageSticker);
        imgTicker.bringToFront();
        btnOk = (Button) findViewById(R.id.btnOk);

        /* Set Listener */
        btnOk.setOnClickListener(this);
        imgTicker.setOnTouchListener(this);

        frame_id = getIntent().getExtras().getInt("frame_id");

        /* This method is show image */
        showImage();

        /* Set Image */
        for (final int id : imgsId) {
            img = new ImageView(PhotoCharacter.this);  // Minimal Image
            img.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
            img.setPadding(8, 8, 8, 8);
            img.setImageResource(id);
            img.setTag(position);
            img.setId(id);
            position++;

            gallery.addView(img);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgSelected = (ImageView) v;
                    bitmap = ((BitmapDrawable) imgSelected.getDrawable()).getBitmap();
                    imgTicker.setImageBitmap(bitmap); // Show Image in full size;

                    if (imgOldSelected != null) {
                        imgOldSelected.setBackgroundColor(Color.TRANSPARENT);
                    }

                    imgSelected.setBackgroundColor(Color.YELLOW);
                    imgOldSelected = imgSelected;

                }
            });
        }
    }

    /* Show Image from database */
    private void showImage() {
        String path_pic = null;
        db = new DatabaseHelper(getApplicationContext());
        path_pic = db.getPath(2);

        imgFullSize.setImageBitmap(BitmapFactory.decodeFile(path_pic));
        bitmap = BitmapFactory.decodeFile(path_pic);

        Toast.makeText(getApplicationContext(), "Read Image form database successfully", Toast.LENGTH_SHORT).show();
    }

    /* Ticker Set On Touch */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{}
                break;
            case MotionEvent.ACTION_MOVE: {
                FrameLayout.LayoutParams mParams = (FrameLayout.LayoutParams) imgTicker.getLayoutParams();
                mParams.leftMargin = x - 5;
                mParams.topMargin = y - 5;
                imgTicker.setLayoutParams(mParams);
            }
                break;
            case MotionEvent.ACTION_UP: {}
                break;
        }

        return true;
    }

    /* Resize Image */
    private Bitmap resizeBitmap(Bitmap bitmap){
        float resizedPercent = 0.82f;
        return Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*resizedPercent), (int)(bitmap.getHeight()*resizedPercent), true);
    }

    /* Button OK */
    @Override
    public void onClick(View v) {

        Bitmap bmpCombined = getImageDrawer();

        String path = PhotoHelper.writeImagePath(bmpCombined);
        PhotoHelper.updatePath(getApplicationContext(), frame_id, path);

        Intent intent = new Intent(PhotoCharacter.this, SelectCharacter.class);
        intent.putExtra("frame_id", frame_id);
        startActivity(intent);
    }

    private Bitmap getImageDrawer(){
        Bitmap bmpBg = ((BitmapDrawable) imgFullSize.getDrawable()).getBitmap();
        Bitmap bmpSticker = ((BitmapDrawable) imgTicker.getDrawable()).getBitmap();

        int mainLeft = getRelativeLeft(imgFullSize);
        int mainTop = getRelativeTop(imgFullSize);
        int stickerLeft = getRelativeLeft(imgTicker);
        int stickerTop = getRelativeTop(imgTicker);

        Bitmap combine = combineImage(bmpBg, bmpSticker, stickerLeft-mainLeft, stickerTop-mainTop);

        imgFullSize.destroyDrawingCache();
        imgFullSize.setImageBitmap(combine);

        return combine;
    }

    /* Combine Image */
    private Bitmap combineImage(Bitmap bmpFullSize,Bitmap bmpSticker, int left, int top) {
        Bitmap bmpOverlay = Bitmap.createBitmap(bmpFullSize.getWidth(), bmpFullSize.getHeight(), bmpFullSize.getConfig());
        Canvas canvas = new Canvas(bmpOverlay);
        canvas.drawBitmap(bmpFullSize, new Matrix(), null);
        canvas.drawBitmap(bmpSticker, left, top, null);

        return bmpOverlay;
    }

    /* Get position view of LEFT */
    private int getRelativeLeft(View v){
        if (v.getParent() == v.getRootView())
            return v.getLeft();
        else
            return v.getLeft() + getRelativeTop((View) v.getParent());
    }

    /* Get postion view of TOP*/
    private int getRelativeTop(View v){
        if (v.getParent() == v.getRootView())
            return v.getTop();
        else
            return v.getTop() + getRelativeTop((View) v.getParent());
    }
}