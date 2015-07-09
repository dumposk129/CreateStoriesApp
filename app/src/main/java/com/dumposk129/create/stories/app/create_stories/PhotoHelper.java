package com.dumposk129.create.stories.app.create_stories;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.dumposk129.create.stories.app.sql.DatabaseHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by DumpOSK129.
 */
public class PhotoHelper {
    private static String pathPhoto;
    private static final String PATH = "StoryApp/StoryName/Frame";
    private static File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + PATH );

    public static String writeImagePath(Bitmap bitmap,boolean isFinal){
        FileOutputStream fos;
        boolean success = false;
        String fName = "";
        if (isFinal) {
            java.util.Date date = new java.util.Date();
            fName = "bg_" +  date.getTime() + ".jpg";
        }else{
            fName = "bg.jpg";
        }
        File file = new File(dir, fName); // TODO: change bg.jpg to frame name.
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("save photo", e.getMessage());
        }

        if (success == true){
            pathPhoto = file.getPath();
            Log.d("Save path", "Complete! path is "+ pathPhoto);
        }else {
            pathPhoto = "";
            Log.e("Save path", "Error can't save");
        }
        return pathPhoto;
    }

    public static String writeImagePath(Bitmap bitmap){
        return writeImagePath(bitmap, false);
    }

    public static void updatePath(Context context, int frame_id , String path){
        if(path != ""){
            DatabaseHelper db = new DatabaseHelper(context);
            db.updatePath(frame_id, path);
        }
    }
}