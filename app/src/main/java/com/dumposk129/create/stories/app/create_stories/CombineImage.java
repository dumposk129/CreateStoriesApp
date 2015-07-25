package com.dumposk129.create.stories.app.create_stories;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by DumpOSK129.
 */
public class CombineImage {
    public static Bitmap getImageDrawer(ImageView imgFullSize, ImageView imgTicker){
        Bitmap bmpBg = ((BitmapDrawable) imgFullSize.getDrawable()).getBitmap();
        Bitmap bmpSticker = ((BitmapDrawable) imgTicker.getDrawable()).getBitmap();

        // Set position main and sticker
        int mainLeft = getRelativeLeft(imgFullSize);
        int mainTop = getRelativeTop(imgFullSize);
//        int stickerLeft = getRelativeLeft(imgTicker);
//        int stickerTop = getRelativeTop(imgTicker);

        float stickerLeft = imgTicker.getTranslationX();
        float stickerTop = imgTicker.getTranslationY();

        Bitmap combine = combineImage(bmpBg, bmpSticker, stickerLeft, stickerTop); // Call method combine.

        imgFullSize.destroyDrawingCache(); // Destroy old imgFullSize.
        imgFullSize.setImageBitmap(combine); // Set imgFullSize after combined.

        return combine; // Return combine.
    }

    /* Combine Image */
    public static Bitmap combineImage(Bitmap bmpFullSize,Bitmap bmpSticker, float left, float top) {
        Bitmap bmpOverlay = Bitmap.createBitmap(bmpFullSize.getWidth(), bmpFullSize.getHeight(), bmpFullSize.getConfig());
        // Draw Image
        Canvas canvas = new Canvas(bmpOverlay);
        canvas.drawBitmap(bmpFullSize, new Matrix(), null);
        canvas.drawBitmap(bmpSticker, left, top, null);

        return bmpOverlay;
    }

    /* Get position view of LEFT */
    public static int getRelativeLeft(View v){
        if (v.getParent() == v.getRootView())
            return v.getLeft();
        else
            return v.getLeft() + getRelativeTop((View) v.getParent());
    }

    /* Get position view of TOP*/
    public static int getRelativeTop(View v){
        if (v.getParent() == v.getRootView())
            return v.getTop();
        else
            return v.getTop() + getRelativeTop((View) v.getParent());
    }
}