package com.dumposk129.create.stories.app.create_stories;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.dumposk129.create.stories.app.sql.DatabaseHelper;

/**
 * Created by DumpOSK129.
 */
public class ShowImage {
    private static Bitmap bitmap;
    private static DatabaseHelper db;
    private static String path_pic;

    public static Bitmap showImage(Context context, int sId, ImageView imgFullSize){
        db = new DatabaseHelper(context);
        path_pic = db.getPath(sId); // Read image from sId

        imgFullSize.setImageBitmap(BitmapFactory.decodeFile(path_pic)); // set ImageBitmap
        return bitmap = BitmapFactory.decodeFile(path_pic); // Return bitmap.
    }
}
