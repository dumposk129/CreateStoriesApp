package com.dumposk129.create.stories.app.create_stories;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dumposk129.create.stories.app.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by DumpOSK129.
 */
public class PhotoBackground extends ActionBarActivity {
    private ImageView imgOldSelected, imgFullSize, img, imgSelected;
    private int[] imgsId = {R.drawable.bg1, R.drawable.bg2, R.drawable.bg3, R.drawable.bg4 ,R.drawable.bg5 ,R.drawable.bg6,
            R.drawable.bg7, R.drawable.bg8 ,R.drawable.bg9, R.drawable.bg10,};
    private Button btnOk;
    private Bitmap bitmap;
    private int position = 0;
    int selectedResourcePosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_gallery);

        final LinearLayout gallery = (LinearLayout) findViewById(R.id.images_gallery);
        imgFullSize = (ImageView) findViewById(R.id.full_image_view);
        btnOk = (Button) findViewById(R.id.btnOk);
       // btnOk.setOnClickListener(this);

        for (final int id : imgsId) {

            img = new ImageView(this);  // Minimal Image
            img.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
            img.setPadding(8, 8, 8, 8);
            img.setImageResource(id);
            img.setTag(position);
            position++;
            // When Click
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tmp = (Integer) v.getTag();
                    selectedResourcePosition = imgsId[tmp];

                    imgSelected = (ImageView) v;
                    //Toast.makeText(getApplicationContext(), "select : "+position, Toast.LENGTH_SHORT).show();
                    bitmap = ((BitmapDrawable) imgSelected.getDrawable()).getBitmap();
                    imgFullSize.setImageBitmap(bitmap); // Show Image in full size;

                    if (imgOldSelected != null) {
                        imgOldSelected.setBackgroundColor(Color.TRANSPARENT);
                    }

                    imgSelected.setBackgroundColor(Color.YELLOW);
                    imgOldSelected = imgSelected;
                }
            });
            //final int selected = selectedResourcePosition;
            gallery.addView(img);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        bitmap = BitmapFactory.decodeResource(getResources(), id);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] byteArr = stream.toByteArray();

                        Intent intent = new Intent(PhotoBackground.this,SelectBackground.class);
                        intent.putExtra("bg", byteArr);
                        startActivity(intent);
                    } catch (Exception e) {
                        Log.e("Photo Streaming Error:", e.getMessage());
                    }
                }
            });
        }


    }
}