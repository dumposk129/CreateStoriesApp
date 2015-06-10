package com.dumposk129.create.stories.app.create_stories;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.GridView;

import com.dumposk129.create.stories.app.R;

/**
 * Created by DumpOSK129.
 */
public class SelectImage extends ActionBarActivity {
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);

        gridView = (GridView) findViewById(R.id.gridView);
      //  gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item, getData());
    }

   /* private ArrayList<ImageItem> getData(){
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainAttributes(R.array.image_ids);

    }*/
}
