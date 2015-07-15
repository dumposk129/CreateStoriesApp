package com.dumposk129.create.stories.app.create_stories;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.sql.DatabaseHelper;

/**
 * Created by DumpOSK129.
 */
public class PhotoCharacter extends ActionBarActivity implements View.OnClickListener, View.OnTouchListener {
    private ImageView imgOldSelected, imgFullSize, img, imgSelected, imgTicker;
    private int[] imgsId = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6, R.drawable.a7, R.drawable.a8, R.drawable.a9, R.drawable.a10, R.drawable.a11, R.drawable.a12, R.drawable.a13, R.drawable.a14, R.drawable.a15,
            R.drawable.a16, R.drawable.a17, R.drawable.a18, R.drawable.a19, R.drawable.a20, R.drawable.a21, R.drawable.a22, R.drawable.a23, R.drawable.a24, R.drawable.a25, R.drawable.a26, R.drawable.a27, R.drawable.a28, R.drawable.a29, R.drawable.a30, R.drawable.a31,
            R.drawable.a32, R.drawable.a33, R.drawable.a34, R.drawable.a35, R.drawable.a36, R.drawable.a37, R.drawable.a38, R.drawable.a39, R.drawable.a40, R.drawable.a41, R.drawable.a42, R.drawable.a43, R.drawable.a44, R.drawable.a45, R.drawable.a46, R.drawable.a47,
            R.drawable.a48, R.drawable.a49, R.drawable.a50, R.drawable.a51, R.drawable.a52, R.drawable.a53, R.drawable.a54, R.drawable.a55, R.drawable.a56, R.drawable.a57, R.drawable.a58, R.drawable.a59, R.drawable.a60, R.drawable.a61, R.drawable.a62, R.drawable.a63,
            R.drawable.a64, R.drawable.a65, R.drawable.a66,

            R.drawable.h1, R.drawable.h2, R.drawable.h3, R.drawable.h4, R.drawable.h5, R.drawable.h6, R.drawable.h7, R.drawable.h8, R.drawable.h9, R.drawable.h10, R.drawable.h11, R.drawable.h12, R.drawable.h13, R.drawable.h14, R.drawable.h15, R.drawable.h16,
            R.drawable.h17, R.drawable.h18, R.drawable.h19, R.drawable.h20, R.drawable.h21, R.drawable.h22, R.drawable.h23, R.drawable.h24, R.drawable.h25};

    private Button btnOk;
    private Bitmap bitmap;
    private long frame_id, frame_order;
    private int sId;
    private String path_pic;
    private float x, y;

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

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("sId")) {
                sId = getIntent().getExtras().getInt("sId");
            }

            if (bundle.containsKey("frame_id")){
                frame_id = (int)getIntent().getExtras().getLong("frame_id");
            }

            if (bundle.containsKey("frame_order")){
                frame_order = (int)getIntent().getExtras().getLong("frame_order");
            }
        }

        // Call class ShowImage.
        ShowImage.showImage(PhotoCharacter.this, sId, imgFullSize);

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

    /* Ticker Set On Touch */
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

     /* Resize Image */
    /*private Bitmap resizeBitmap(Bitmap bitmap){
        float resizedPercent = 0.82f;
        return Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*resizedPercent), (int)(bitmap.getHeight()*resizedPercent), true);
    }*/

    /* Button OK */
    @Override
    public void onClick(View v) {

        Bitmap bmpCombined = CombineImage.getImageDrawer(imgFullSize, imgTicker);

        String path = PhotoHelper.writeImagePath(bmpCombined);
        PhotoHelper.updatePath(getApplicationContext(), (int)frame_id, path);

        Intent intent = new Intent(PhotoCharacter.this, SelectCharacter.class);
        intent.putExtra("sId", sId);
        intent.putExtra("frame_id", frame_id);
        intent.putExtra("frame_order", frame_order);
        //Toast.makeText(getApplicationContext(), "frame_id: "+frame_id, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}