package com.dumposk129.create.stories.app.create_stories;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private ImageView imageView;
    private Bitmap bitmap, resizeBm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);

        bitmap = getIntent().getParcelableExtra("id");
        btnOK = (Button) findViewById(R.id.btnOk);

        imageView = (ImageView) findViewById(R.id.full_image_view);

        // Compress
        Bitmap resizeBm = shrinkBitmap(bitmap, 300, 300);
        imageView.setImageBitmap(resizeBm);
        /*Bitmap bmp = BitmapFactory.decodeFile(String.valueOf(bitmap));
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        InputStream in = new ByteArrayInputStream(stream.toByteArray());
       // ContentBody foto = new InputStreamBody(in, "image/jpeg"bitmap);
        final byte[] bytes = stream.toByteArray();
*/
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SelectBackground.class);
                i.putExtra("id", bitmap);
                startActivity(i);
            }
        });
    }

    Bitmap shrinkBitmap(Bitmap file, int width, int height){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(String.valueOf(file), options);

        int heightRatio = (int)Math.ceil(options.outHeight/(float)height);
        int widthRatio = (int)Math.ceil(options.outWidth/(float)width);

        if (heightRatio > 1 || widthRatio > 1){
            if (heightRatio > widthRatio){
                options.inSampleSize = heightRatio;
            }else {
                options.inSampleSize = widthRatio;
            }
        }

        options.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeFile(String.valueOf(file), options);

        return bmp;
    }
}