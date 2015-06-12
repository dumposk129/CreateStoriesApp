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
public class Photo extends ActionBarActivity {
    private ImageView imgOldSelected, imgFullSize, img, imgSelected;
    private int[] imgsId = {R.drawable.bg1, R.drawable.bg2, R.drawable.bg3, R.drawable.bg4 ,R.drawable.bg5 ,R.drawable.bg6,
            R.drawable.bg7, R.drawable.bg8 ,R.drawable.bg9, R.drawable.bg10,};
    private Button btnOk;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_gallery);

        final LinearLayout gallery = (LinearLayout) findViewById(R.id.images_gallery);
        imgFullSize = (ImageView) findViewById(R.id.full_image_view);
        btnOk = (Button) findViewById(R.id.btnOk);

        for (final int id : imgsId){
            img = new ImageView(this);  // Minimal Image
            img.setLayoutParams(new LinearLayout.LayoutParams(150,150));
            img.setPadding(8, 8, 8, 8);
            img.setImageResource(id);

            //When Click
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgSelected = (ImageView) v;
                    Bitmap bitmap = ((BitmapDrawable) imgSelected.getDrawable()).getBitmap();
                    imgFullSize.setImageBitmap(bitmap);

                    if (imgOldSelected != null){
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
                    Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), id);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArr = stream.toByteArray();

                    Intent intent = new Intent(Photo.this, SelectBackground.class);
                    intent.putExtra("id", byteArr);
                    startActivity(intent);
                }
            });
        }

        //imageViewAdapter = new ImageViewAdapter(this, R.layout.grid_item, getData());

        //gridView = (GridView) findViewById(R.id.gridView);

//        gridView.setAdapter(imageViewAdapter);

  //      gridView.setAdapter(new ImageViewAdapter(this, R.layout.grid_item, getData()));

        //On Click event for Single GridView Item
/*
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(), "image " + item.getImage(), Toast.LENGTH_SHORT).show();

                // Sending image id to FullScreenActivity
                Intent i = new Intent(Photo.this, FullImageActivity.class);
                i.putExtra("id", item.getImage());
                startActivity(i);
            }
        });
*/
    }

/*    // Prepare array data
    private ArrayList<ImageItem> getData(){
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.background);
        for (int i = 0; i < imgs.length(); i++){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap));
        }
        return imageItems;
    }*/
}