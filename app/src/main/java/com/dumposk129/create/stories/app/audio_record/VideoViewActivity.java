package com.dumposk129.create.stories.app.audio_record;

import android.app.Activity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.dumposk129.create.stories.app.R;

/**
 * Created by DumpOSK129
 */
public class VideoViewActivity extends Activity{
    String path = "myfile/";
    private VideoView myVideoView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.video_view);
        myVideoView = (VideoView) findViewById(R.id.myVideoView);
        myVideoView.setVideoPath(path);
        myVideoView.setMediaController(new MediaController(this));
        myVideoView.requestFocus();
        myVideoView.start();
    }
}