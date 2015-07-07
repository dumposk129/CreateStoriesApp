package com.dumposk129.create.stories.app.watch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.api.ApiConfig;
import com.dumposk129.create.stories.app.api.Globals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


/*import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;*/

/**
 * Created by DumpOSK129.
 */
public class Watch extends ActionBarActivity implements View.OnClickListener, View.OnTouchListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener {
    private ImageView imgBg;
    private MediaPlayer mPlayer = new MediaPlayer();
    private ProgressDialog progressDialog;
    private Button btnNext, btnPrev;
    private int index, size, sId;
    private String picPath, audioPath = "";
    private SeekBar mSeekbar;
    private int mediaFileLengthInMilliseconds;
    private Handler handler = new Handler();
    private ImageButton imgBtnPlay;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_audio);

        imgBg = (ImageView) findViewById(R.id.imgBg);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPrev = (Button) findViewById(R.id.btnPrev);
        mSeekbar = (SeekBar) findViewById(R.id.seekBar);
        imgBtnPlay = (ImageButton) findViewById(R.id.imgBtnPlay);

        mSeekbar.setMax(99); //0-100

        /* Get Extra */
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("sId")) {
                sId = getIntent().getExtras().getInt("sId");
            }

            if (bundle.containsKey("size")) {
                size = getIntent().getExtras().getInt("size");
            }

            if (bundle.containsKey("index")) {
                index = getIntent().getExtras().getInt("index");
            }
        }

        /* If index equal size change text to Done */
        if (index == size) {
            btnNext.setText("Done");
        }

        if (index > 0){
            btnPrev.setVisibility(View.VISIBLE);
        }

        /* Set PicPath and AudioPath*/
        if (Globals.frames.get(index).getPathPic() == "") {
            imgBg.setImageResource(R.drawable.bg1);
        } else {
            picPath = ApiConfig.apiUrl + Globals.frames.get(index).getPathPic();
            new LoadImageTask().execute();
        }

        if (Globals.frames.get(index).getPathAudio() != "" || Globals.frames.get(index).getPathAudio() == null) {
            audioPath = ApiConfig.apiUrl + Globals.frames.get(index).getPathAudio();
        }

        mPlayer.setOnBufferingUpdateListener(this);
        mPlayer.setOnCompletionListener(this);
        mSeekbar.setOnTouchListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        imgBtnPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnNext){
            if (index == size) {
                intent = new Intent(Watch.this, ShowStories.class);
                startActivity(intent);
            } else {
                intent = new Intent(Watch.this, Watch.class);
                intent.putExtra("index", index + 1);
                intent.putExtra("size", size);
                startActivity(intent);
            }
        } else if (v.getId() == R.id.btnPrev){
                intent = new Intent(Watch.this, Watch.class);
                intent.putExtra("index", index - 1);
                intent.putExtra("size", size);
                startActivity(intent);
        } else {
            try {
                mPlayer.setDataSource(audioPath);
                mPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }

            mediaFileLengthInMilliseconds = mPlayer.getDuration();

            if (!mPlayer.isPlaying()){
                mPlayer.start();
                imgBtnPlay.setImageResource(R.drawable.ic_action_pause);
            }else {
                mPlayer.pause();
                imgBtnPlay.setImageResource(R.drawable.ic_action_play_circle_outline);
            }

            primarySeekBarProgressUpdater();
        }
    }

    private void primarySeekBarProgressUpdater() {
        mSeekbar.setProgress((int)(((float) mPlayer.getCurrentPosition() / mediaFileLengthInMilliseconds) * 100));
        if (mPlayer.isPlaying()){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    primarySeekBarProgressUpdater();
                }
            };
            handler.postDelayed(runnable, 1000);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.seekBar) {
            if (mPlayer.isPlaying()) {
                SeekBar sb = (SeekBar) v;
                int playPosition = (mediaFileLengthInMilliseconds / 100) * sb.getProgress();
                mPlayer.seekTo(playPosition);
            }
        }
        return false;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        mSeekbar.setSecondaryProgress(percent);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        imgBtnPlay.setImageResource(R.drawable.ic_action_play_circle_outline);
    }

    /* Load Image from url */
    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        // Preparing load questions and answer.
        private ProgressDialog progressDialog = new ProgressDialog(Watch.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading Image...");
            progressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bmp = null;

            try {
                URL url = new URL(picPath);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (progressDialog.isShowing()) progressDialog.dismiss();
            imgBg.setImageBitmap(bitmap);
        }
    }
}