package com.dumposk129.create.stories.app.model;

import android.graphics.Bitmap;

/**
 * Created by DumpOSK129.
 */
public class ImageItem {
    private Bitmap image;

    public ImageItem(Bitmap image){
        super();
        this.image = image;
    }

    public void setImage(Bitmap image){
        this.image = image;
    }

    public Bitmap getImage(){
        return image;
    }
}
