package com.dumposk129.create.stories.app.create_stories;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

/**
 * Created by DumpOSK129.
 */
public class DemuxerVideo {
    private static Bitmap bitmap;
    private static String duration;

    public DemuxerVideo(Bitmap bitmap, String duration) {
        this.bitmap = bitmap;
        this.duration = duration;
    }

    public void initialFFmpeg(Context context) {
        FFmpeg fFmpeg = FFmpeg.getInstance(context);
        try {
            fFmpeg.loadBinary(new LoadBinaryResponseHandler(){
                @Override
                public void onStart() {}

                @Override
                public void onFailure() {}

                @Override
                public void onSuccess() {}

                @Override
                public void onFinish() {}
            });
        } catch (FFmpegNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void executeFFmpeg(Context context, String cmd){
        FFmpeg fFmpeg = FFmpeg.getInstance(context);
        try {
            fFmpeg.execute(cmd, new ExecuteBinaryResponseHandler(){
                @Override
                public void onStart() {}

                @Override
                public void onProgress(String message) {
                    Log.d("Creating Frame", message);
                }

                @Override
                public void onFailure(String message) {
                    Log.e("Creating Frame", message);
                }

                @Override
                public void onSuccess(String message) {
                    Log.d("Created frame success", message);
                }

                @Override
                public void onFinish() {}
            });
        }catch (FFmpegCommandAlreadyRunningException e){
            Log.e("Error!", e.getMessage());
        }
    }
}