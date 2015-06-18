package com.dumposk129.create.stories.app.create_stories;

import android.content.Intent;
import android.graphics.Bitmap;
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
public class PhotoBackground extends ActionBarActivity implements View.OnClickListener{
    private ImageView imgOldSelected, imgFullSize, img, imgSelected;
    private int[] imgsId = {R.drawable.bg1, R.drawable.bg2, R.drawable.bg3, R.drawable.bg4 ,R.drawable.bg5 ,R.drawable.bg6,
            R.drawable.bg7,R.drawable.bg8, R.drawable.bg9,};
    private Button btnOk;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_gallery);

        final LinearLayout gallery = (LinearLayout) findViewById(R.id.images_gallery);
        int position = 0;

        imgFullSize = (ImageView) findViewById(R.id.full_image_view);
        btnOk = (Button) findViewById(R.id.btnOk);

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

            gallery.addView(img);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgSelected = (ImageView) v;
                    bitmap = ((BitmapDrawable) imgSelected.getDrawable()).getBitmap();
                    imgFullSize.setImageBitmap(bitmap); // Show Image in full size;

                    if (imgOldSelected != null) {
                        imgOldSelected.setBackgroundColor(Color.TRANSPARENT);
                    }

                    imgSelected.setBackgroundColor(Color.YELLOW);
                    imgOldSelected = imgSelected;

                }
            });
        }
    }

    /* Resize Image */
    private Bitmap resizeBitmap(Bitmap bitmap){
        float resizedPercent = 0.82f;
        return Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*resizedPercent), (int)(bitmap.getHeight()*resizedPercent), true);
    }

    /* Button OK */
    @Override
    public void onClick(View v) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap = resizeBitmap(bitmap);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArr = stream.toByteArray();

        Intent intent = new Intent(PhotoBackground.this.getApplicationContext(),SelectBackground.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("bg", byteArr);
        v.getContext().startActivity(intent);
    }
}