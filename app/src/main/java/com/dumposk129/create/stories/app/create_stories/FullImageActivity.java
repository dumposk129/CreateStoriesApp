package com.dumposk129.create.stories.app.create_stories;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dumposk129.create.stories.app.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by DumpOSK129.
 */
public class FullImageActivity extends ActionBarActivity{
    private Button btnOK;
    private ImageView imageView;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);

        bitmap = getIntent().getParcelableExtra("id");
        btnOK = (Button) findViewById(R.id.btnOk);

        imageView = (ImageView) findViewById(R.id.full_image_view);
        imageView.setImageBitmap(bitmap);

        // Compress
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        final byte[] bytes = stream.toByteArray();

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SelectBackground.class);
                i.putExtra("id", bytes);
                startActivity(i);
            }
        });
    }
}