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
    private long frame_id, frame_order;
    private int sId;
    private String path_pic;
    private float x, y;

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

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("sId")) {
                sId = getIntent().getExtras().getInt("sId");
            }

            if (bundle.containsKey("frame_id")) {
                frame_id = (int) getIntent().getExtras().getLong("frame_id");
            }

            if (bundle.containsKey("frame_order")) {
                frame_order = (int) getIntent().getExtras().getLong("frame_order");
            }

            if (bundle.containsKey("imagePath")) {
                byte[] byteArr = bundle.getByteArray("imagePath");
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length);
                imgTicker.setImageBitmap(bmp);
            }
        }

        showImage();
    }

    private void showImage() {
        path_pic = null;
        db = new DatabaseHelper(getApplicationContext());
        path_pic = db.getPath(sId);

        imgFullSize.setImageBitmap(BitmapFactory.decodeFile(path_pic));
        bitmap = BitmapFactory.decodeFile(path_pic);
    }

    @Override
    public void onClick(View v) {
        Bitmap bmpCombined = CombineImage.getImageDrawer(imgFullSize, imgTicker);
        String path = PhotoHelper.writeImagePath(bmpCombined);
        PhotoHelper.updatePath(getApplicationContext(), (int) frame_id, path);

        Intent intent = new Intent(MoveImageFromGallery.this, SelectCharacter.class);
        intent.putExtra("sId", sId);
        intent.putExtra("frame_id", frame_id);
        intent.putExtra("frame_order", frame_order);
        startActivity(intent);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                x = event.getX();
                y = event.getY();
            }
            break;
            case MotionEvent.ACTION_UP: {
                x = event.getX();
                y = event.getY();
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                FrameLayout.LayoutParams mParams = (FrameLayout.LayoutParams) imgTicker.getLayoutParams();
                x = event.getX();
                y = event.getY();
                mParams.leftMargin = Math.round(x);
                mParams.topMargin = Math.round(y);
                imgTicker.setLayoutParams(mParams);
            }
            break;
        }
        return true;
    }
}
