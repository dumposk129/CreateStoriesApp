package com.dumposk129.create.stories.app.create_stories;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.model.ImageItem;

import java.util.ArrayList;

/**
 * Created by DumpOSK129.
 */
public class GridPhoto extends ActionBarActivity {
    private GridView gridView;
    private GridViewAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_layout);

        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);

        gridView.setAdapter(new GridViewAdapter(this, R.layout.grid_item_layout, getData()));

        //On Click event for Single GridView Item
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(), "image " + item.getImage(), Toast.LENGTH_SHORT).show();

                // Sending image id to FullScreenActivity
                Intent i = new Intent(GridPhoto.this, FullImageActivity.class);
                i.putExtra("id", item.getImage());
                startActivity(i);
            }
        });
    }

    // Prepare array data
    private ArrayList<ImageItem> getData(){
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.background);
        for (int i = 0; i < imgs.length(); i++){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap));
        }
        return imageItems;
    }
}
