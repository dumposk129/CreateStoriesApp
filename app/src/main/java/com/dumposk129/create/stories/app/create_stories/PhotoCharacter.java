package com.dumposk129.create.stories.app.create_stories;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dumposk129.create.stories.app.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by DumpOSK129.
 */
public class PhotoCharacter extends ActionBarActivity {
    private ImageView imgOldSelected, imgFullSize, img, imgSelected;
    private int[] imgsId = {R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6,R.drawable.a7,R.drawable.a8,R.drawable.a9,R.drawable.a10,R.drawable.a11,R.drawable.a12,R.drawable.a13,R.drawable.a14,R.drawable.a15,
            R.drawable.a16,R.drawable.a17,R.drawable.a18,R.drawable.a19,R.drawable.a20,R.drawable.a21,R.drawable.a22,R.drawable.a23,R.drawable.a24,R.drawable.a25,R.drawable.a26,R.drawable.a27,R.drawable.a28,R.drawable.a29,R.drawable.a30,R.drawable.a31,
            R.drawable.a32,R.drawable.a33,R.drawable.a34, R.drawable.a35,R.drawable.h1,R.drawable.h2,R.drawable.h3,R.drawable.h4,R.drawable.h5,R.drawable.h6, R.drawable.h7,R.drawable.h8,R.drawable.h9,R.drawable.h10,R.drawable.h11,R.drawable.a12,
            R.drawable.h13,R.drawable.h14,R.drawable.h15,R.drawable.h16,R.drawable.h17,R.drawable.h18,R.drawable.h19,R.drawable.h20,R.drawable.h21,R.drawable.h22,R.drawable.h23,R.drawable.h24,R.drawable.h25};
    private Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_gallery);

        final LinearLayout gallery = (LinearLayout) findViewById(R.id.images_gallery);
        imgFullSize = (ImageView) findViewById(R.id.full_image_view);
        btnOk = (Button) findViewById(R.id.btnOk);

        for (final int id : imgsId) {
            img = new ImageView(this);  // Minimal Image
            img.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
            img.setPadding(8, 8, 8, 8);
            img.setImageResource(id);

            //When Click
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgSelected = (ImageView) v;
                    Bitmap bitmap = ((BitmapDrawable) imgSelected.getDrawable()).getBitmap();
                    imgFullSize.setImageBitmap(bitmap);

                    if (imgOldSelected != null) {
                        imgOldSelected.setBackgroundColor(Color.TRANSPARENT);
                    }

                    imgSelected.setBackgroundColor(Color.YELLOW);
                    imgOldSelected = imgSelected;
                }
            });
            gallery.addView(img);

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArr = stream.toByteArray();

                    Intent intent = new Intent(PhotoCharacter.this, SelectCharacter.class);
                    intent.putExtra("id", byteArr);
                    startActivity(intent);
                }
            });
        }
    }
}