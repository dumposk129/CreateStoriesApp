package com.dumposk129.create.stories.app.create_stories;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.sql.DatabaseHelper;

/**
 * Created by DumpOSK129.
 */
public class MoveImageFromGallery extends ActionBarActivity implements View.OnClickListener, View.OnTouchListener{
    private Bitmap bitmap;
    private ImageView imgFullSize, imgTicker;
    private Button btnOK;
    private int frame_id;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touch_image_gallery);

        imgFullSize = (ImageView) findViewById(R.id.full_image_view);
        imgTicker = (ImageView) findViewById(R.id.imageSticker);
        btnOK = (Button) findViewById(R.id.btnOk);

        btnOK.setOnClickListener(this);
        imgTicker.setOnTouchListener(this);

        frame_id = getIntent().getExtras().getInt("frame_id");

        showImage();

        Intent i = getIntent();

        // Selected image id
        if (i.getExtras() != null) {
            byte[] byteArr = i.getExtras().getByteArray("imagePath");
            bitmap = BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length);
            imgTicker.setImageBitmap(bitmap);
        }
    }

    public void showImage() {
        String path_pic = null;
        db = new DatabaseHelper(getApplicationContext());
        path_pic = db.getPath(2);

        imgFullSize.setImageBitmap(BitmapFactory.decodeFile(path_pic));
        bitmap = BitmapFactory.decodeFile(path_pic);
    }

    @Override
    public void onClick(View v) {
        Bitmap bmpCombined = CombineImage.getImageDrawer(imgFullSize, imgTicker);
        String path = PhotoHelper.writeImagePath(bmpCombined);
        PhotoHelper.updatePath(getApplicationContext(), frame_id, path);

        Intent intent = new Intent(MoveImageFromGallery.this, SelectCharacter.class);
        intent.putExtra("frame_id", frame_id);
        startActivity(intent);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {}
            break;
            case MotionEvent.ACTION_MOVE: {
                FrameLayout.LayoutParams mParams = (FrameLayout.LayoutParams) imgTicker.getLayoutParams();
                mParams.leftMargin = x;
                mParams.topMargin = y;
                imgTicker.setLayoutParams(mParams);
            }
            break;
            case MotionEvent.ACTION_UP: {}
            break;
        }
        return true;
    }
}
