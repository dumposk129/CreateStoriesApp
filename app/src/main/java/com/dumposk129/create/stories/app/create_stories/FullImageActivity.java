package com.dumposk129.create.stories.app.create_stories;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dumposk129.create.stories.app.R;

/**
 * Created by DumpOSK129.
 */
public class FullImageActivity extends ActionBarActivity{
    private Button btnOK;
    private Boolean isImageFitToScreen;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);

        btnOK = (Button) findViewById(R.id.btnOk);

        // get intent data
        Intent i = getIntent();

        // Selected image id
        position = i.getExtras().getInt("id");
        ImageAdapter imageAdapter = new ImageAdapter(this);

        final ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        imageView.setImageResource(imageAdapter.mThumbIds[position]);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SelectBackground.class);
                i.putExtra("photo", position);
                startActivity(i);
            }
        });
    }
}
