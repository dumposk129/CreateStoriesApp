package com.dumposk129.create.stories.app.watch;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.api.ApiConfig;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by DumpOSK129.
 */
public class Watch extends ActionBarActivity implements MediaPlayer.OnPreparedListener {
    OkHttpClient client = new OkHttpClient();

    private VideoView myVideoView;
    private MediaController mediaController;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_audio);

        myVideoView = (VideoView) findViewById(R.id.video_view);

        mediaController = new MediaController(Watch.this);
        myVideoView.setMediaController(mediaController);
        myVideoView.requestFocus();
        myVideoView.setOnPreparedListener(this);

        if (mediaController == null) {
            mediaController = new MediaController(Watch.this);
        }

        /* Download Image and Audio */
        try {
            downloadImageWithAudio(ApiConfig.hostname("create_frame"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String downloadImageWithAudio(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        myVideoView.seekTo(position);
        if (position == 0) {
            myVideoView.start();
        } else {
            // if we come from a resumed activity, video playback will be paused
            myVideoView.pause();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // onSaveInstanceState in order to store the video playback position for orientation change
        savedInstanceState.putInt("Position", myVideoView.getCurrentPosition());
        myVideoView.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // onRestoreInstanceState in order to play the video playback from the stored position
        position = savedInstanceState.getInt("Position");
        myVideoView.seekTo(position);
    }
}