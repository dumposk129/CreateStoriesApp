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
    private static ImageView imgView;
    private static DatabaseHelper db;
    private static Context context;

    public static void showImage(Context context, String path_pic) {
        path_pic = null;
        db = new DatabaseHelper(context);
        path_pic = db.getPath(2);

        imgView.setImageBitmap(BitmapFactory.decodeFile(path_pic));
        bitmap = BitmapFactory.decodeFile(path_pic);
    }
}