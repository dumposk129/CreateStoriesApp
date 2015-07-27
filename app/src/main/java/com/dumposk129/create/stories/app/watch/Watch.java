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
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.api.ApiConfig;
import com.dumposk129.create.stories.app.api.Globals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
    private Toolbar mToolbar;
    private boolean doubleBackToExitPressedOnce = false;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_audio);

        // Casting.
        imgBg = (ImageView) findViewById(R.id.imgBg);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPrev = (Button) findViewById(R.id.btnPrev);
        mSeekbar = (SeekBar) findViewById(R.id.seekBar);
        imgBtnPlay = (ImageButton) findViewById(R.id.imgBtnPlay);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);

        // Toolbar and Navigation Drawer.
     /*   setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
*/
        // Set seekbar is 99%
        mSeekbar.setMax(99); //0-100

        // Get Data
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

        /* If index equal size change text to Done. */
        if (index == size) {
            btnNext.setText("Done");
        }

        /* If index more than zero then show btnPrev. */
        if (index > 0) {
            btnPrev.setVisibility(View.VISIBLE);
        }

        /* Set PicPath and AudioPath*/
        if (Globals.frames.get(index).getPathPic() != "") {
            picPath = ApiConfig.apiUrl + Globals.frames.get(index).getPathPic();
            new LoadImageTask().execute();
        }

        if (Globals.frames.get(index).getPathAudio() != "" || Globals.frames.get(index).getPathAudio() == null) {
            audioPath = ApiConfig.apiUrl + Globals.frames.get(index).getPathAudio();
        }else {
            imgBtnPlay.setVisibility(View.INVISIBLE);
            mSeekbar.setVisibility(View.INVISIBLE);
        }

        // Set Listener.
        mPlayer.setOnBufferingUpdateListener(this);
        mPlayer.setOnCompletionListener(this);
        mSeekbar.setOnTouchListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        imgBtnPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnNext) { // Click btnNext.
            if (index == size) { // if index equal size, go to Show Stories
                intent = new Intent(Watch.this, ShowStories.class);
                Runtime.getRuntime().freeMemory();
                startActivity(intent);
            } else { // if not do-while index equal size.
                intent = new Intent(Watch.this, Watch.class);
                Runtime.getRuntime().freeMemory();
                intent.putExtra("index", index + 1);
                intent.putExtra("size", size);
                startActivity(intent);
            }
        } else if (v.getId() == R.id.btnPrev) { // Click prev. and index - 1.
            intent = new Intent(Watch.this, Watch.class);
            Runtime.getRuntime().freeMemory();
            intent.putExtra("index", index - 1);
            intent.putExtra("size", size);
            startActivity(intent);
        } else { // Click play button(imgBtn)
            try {
                mPlayer.setDataSource(audioPath); // Set audioPath and preparing.
                mPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }

            mediaFileLengthInMilliseconds = mPlayer.getDuration(); // Set mediaFileLength.

            if (!mPlayer.isPlaying()) { // playing and set icon pause.
                mPlayer.start();
                imgBtnPlay.setImageResource(R.drawable.ic_action_pause_circle_outline);
            } else { // pause and set icon play.
                mPlayer.pause();
                imgBtnPlay.setImageResource(R.drawable.ic_action_play_circle_outline);
            }

            primarySeekBarProgressUpdater(); // Call method primarySeekBarProgressUpdater
        }
    }

    /* Seekbar update */
    private void primarySeekBarProgressUpdater() {
        // seekbar update follow player.
        mSeekbar.setProgress((int) (((float) mPlayer.getCurrentPosition() / mediaFileLengthInMilliseconds) * 100));
        if (mPlayer.isPlaying()) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    primarySeekBarProgressUpdater(); // Update seekbar until finish.
                }
            };
            handler.postDelayed(runnable, 1000); // Set delay.
        }
    }

    /* Seekbar slide from current position to another position */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.seekBar) {
            if (mPlayer.isPlaying()) {
                SeekBar sb = (SeekBar) v;
                int playPosition = (mediaFileLengthInMilliseconds / 100) * sb.getProgress(); // Calculate position.
                mPlayer.seekTo(playPosition); // seekTo position.
            }
        }
        return false;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        mSeekbar.setSecondaryProgress(percent); // Update SecondaryProgress of Seekbar.
    }

    /* When play finish change logo to play*/
    @Override
    public void onCompletion(MediaPlayer mp) {
        imgBtnPlay.setImageResource(R.drawable.ic_action_play_circle_outline);
    }

    /* Load Image from url */
    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        // Preparing load image.
        private ProgressDialog progressDialog = new ProgressDialog(Watch.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading Image...");
            progressDialog.show();
        }

        /* Loading image from URL(picPath) */
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

        /* Show image */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (progressDialog.isShowing()) progressDialog.dismiss();
            imgBg.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}