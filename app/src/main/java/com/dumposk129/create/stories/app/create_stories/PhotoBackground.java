package com.dumposk129.create.stories.app.create_stories;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dumposk129.create.stories.app.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by DumpOSK129.
 */
public class PhotoBackground extends ActionBarActivity implements View.OnClickListener{
    private ImageView imgOldSelected, imgFullSize, img, imgSelected;
    private int[] imgsId = {R.drawable.bg1, R.drawable.bg2, R.drawable.bg3, R.drawable.bg4 ,R.drawable.bg5 ,R.drawable.bg6,
            R.drawable.bg8, R.drawable.bg9, R.drawable.bg10};
    private Button btnOk;
    private Bitmap bitmap;
    private int sId;
    private long frame_id, frame_order;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_gallery);

        final LinearLayout gallery = (LinearLayout) findViewById(R.id.images_gallery);
        int position = 0;

        imgFullSize = (ImageView) findViewById(R.id.full_image_view);
        btnOk = (Button) findViewById(R.id.btnOk);

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

        btnOk.setOnClickListener(this);

        /* Set Image */
        for (final int id : imgsId) {
            img = new ImageView(PhotoBackground.this);  // Minimal Image
            img.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
            img.setPadding(8, 8, 8, 8);
            img.setImageResource(id);
            img.setTag(position);
            img.setId(id);
            position++;

            gallery.addView(img); // Show minimal image.
            img.setOnClickListener(new View.OnClickListener() {  // Click minimal image.
                @Override
                public void onClick(View v) {
                    imgSelected = (ImageView) v;
                    bitmap = ((BitmapDrawable) imgSelected.getDrawable()).getBitmap();
                    imgFullSize.setImageBitmap(bitmap); // Show Image in full size;

                    if (imgOldSelected != null) {
                        imgOldSelected.setBackgroundColor(Color.TRANSPARENT); // When change image bg changed transparent.
                    }

                    imgSelected.setBackgroundColor(Color.YELLOW); // When select image and bg change yellow.
                    imgOldSelected = imgSelected;
                }
            });
        }
    }

    /* Resize Image and return bitmap after resized. */
    private Bitmap resizeBitmap(Bitmap bitmap){
        float resizedPercent = 0.82f;
        return Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*resizedPercent), (int)(bitmap.getHeight()*resizedPercent), true);
    }

    @Override
    public void onClick(View v) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap = resizeBitmap(bitmap);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream); // Compress bitmap.
        byte[] byteArr = stream.toByteArray();

        // Intent and putExtra.
        Intent intent = new Intent(PhotoBackground.this.getApplicationContext(),SelectBackground.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Runtime.getRuntime().freeMemory();
        intent.putExtra("sId", sId);
        intent.putExtra("frame_order", frame_order);
        intent.putExtra("bg", byteArr);
        v.getContext().startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}