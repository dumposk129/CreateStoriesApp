package com.dumposk129.create.stories.app.create_stories;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.model.Audio;
import com.dumposk129.create.stories.app.sql.DatabaseHelper;
import com.squareup.okhttp.MediaType;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by DumpOSK129.
 */
public class AudioRecording extends ActionBarActivity implements MediaPlayer.OnCompletionListener, View.OnClickListener {
    private ImageView imgView;
    private MediaRecorder recorder = null;
    private MediaPlayer player;
    private Button btnStartRecording, btnStopRecording, btnPlayRecording, btnStop, btnNext;
    private Chronometer chronometer;
    private Bitmap bitmap;
    private long frame_id;
    private double recordingDuration = 0;
    private String duration;
    private String path_pic = null;

    private static final String path_audio = "StoryApp/StoryName/Audio";
    private static File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + path_audio + "/audio.mp4");

  /*  private static final String path_output = "StoryApp/StoryName/Video";
    private static File dirOutput = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + path_output);
*/
    DatabaseHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio);

        btnStartRecording = (Button) findViewById(R.id.btnStartRecord);
        btnStopRecording = (Button) findViewById(R.id.btnStopRecord);
        btnPlayRecording = (Button) findViewById(R.id.btnPlay);
        btnStop = (Button) findViewById(R.id.btnStop);
        imgView = (ImageView) findViewById(R.id.imgAudioRec);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        btnNext = (Button) findViewById(R.id.btnNext);

        btnStartRecording.setOnClickListener(this);
        btnStopRecording.setOnClickListener(this);
        btnPlayRecording.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        btnPlayRecording.setEnabled(false);
        btnStopRecording.setEnabled(false);
        btnStop.setEnabled(false);
        btnNext.setEnabled(false);

        showImage();

        frame_id = (int)getIntent().getExtras().getLong("frame_id");
    }

    /* Show Image from database */
    private void showImage() {
        db = new DatabaseHelper(getApplicationContext());
        path_pic = db.getPath(2);

        imgView.setImageBitmap(BitmapFactory.decodeFile(path_pic));
        bitmap = BitmapFactory.decodeFile(path_pic);
    }

    public void onCompletion(MediaPlayer mp) {
        btnStartRecording.setEnabled(true);
        btnStopRecording.setEnabled(false);
        btnPlayRecording.setEnabled(true);
        btnStop.setEnabled(false);
        btnNext.setEnabled(true);
    }

    public void onClick(View v) {
        if (v == btnStartRecording) {
            chronometer.start();
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            chronometer.setBase(SystemClock.elapsedRealtime());
            dir.mkdirs();

            if (dir.exists()){
                dir.delete();
            }

            try {
                dir.createNewFile();
            }catch (IOException e) {
                throw new IllegalStateException("Failed to create " + dir.toString());
            }

            recorder.setOutputFile(dir.getAbsolutePath());

            try {
                recorder.prepare();
            } catch (IllegalStateException e) {
                throw new RuntimeException("IllegalStateException on MediaRecorder.prepare", e);
            } catch (IOException e) {
                throw new RuntimeException("IOException on MediaRecorder.prepare", e);
            }

            recorder.start();
            btnPlayRecording.setEnabled(false);
            btnStartRecording.setEnabled(false);
            btnStopRecording.setEnabled(true);
            btnStop.setEnabled(false);
            btnNext.setEnabled(false);
        } else if (v == btnStopRecording) {
            chronometer.stop();
            recorder.stop();
            recorder.release();
            recordingDuration = (SystemClock.elapsedRealtime() - chronometer.getBase())/1000.0;

            player = new MediaPlayer();
            player.setOnCompletionListener(this);

            try {
                player.setDataSource(dir.getAbsolutePath());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Illegal Argument to MediaPlayer.setDataSource", e);
            } catch (IllegalStateException e) {
                throw new RuntimeException("Illegal State in MediaPlayer.setDataSource", e);
            } catch (IOException e) {
                throw new RuntimeException("IOException in MediaPalyer.setDataSource", e);
            }

            try {
                player.prepare();
            } catch (IllegalStateException e) {
                throw new RuntimeException("IllegalStateException in MediaPlayer.prepare", e);
            } catch (IOException e) {
                throw new RuntimeException("IOException in MediaPlayer.prepare", e);
            }

            DecimalFormat df = new DecimalFormat("#.#");
            duration = df.format(recordingDuration);
            duration += getString(R.string.s);

            btnPlayRecording.setEnabled(true);
            btnStartRecording.setEnabled(true);
            btnStopRecording.setEnabled(false);
            btnStop.setEnabled(false);
            btnNext.setEnabled(true);
        } else if (v == btnPlayRecording) {
            player.start();
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            btnPlayRecording.setEnabled(false);
            btnStopRecording.setEnabled(false);
            btnStartRecording.setEnabled(false);
            btnStop.setEnabled(true);
            btnNext.setEnabled(true);
        } else if (v == btnStop) {
            player.stop();
            chronometer.stop();
            btnStartRecording.setEnabled(true);
            btnStopRecording.setEnabled(false);
            btnPlayRecording.setEnabled(true);
            btnStop.setEnabled(true);
            btnNext.setEnabled(true);
        } else {
            createAudioInSQLiteDB();

            SavePhotoToServerAsyncTask savePhotoToServer = new SavePhotoToServerAsyncTask();
            savePhotoToServer.execute();
        }
    }

    private long createAudioInSQLiteDB() {
        db = new DatabaseHelper(getApplicationContext());
        Audio audio = new Audio();
        if (path_audio != ""){
            audio.setFrameID((int)frame_id);
            audio.setPathAudio(path_audio);
            audio.setDuration(duration);
        }
        return db.createNewAudio(audio);
    }

    private class SavePhotoToServerAsyncTask extends AsyncTask {
        final MediaType JSON = MediaType.parse("application/json; charset=urf-8");

        @Override
        protected Object doInBackground(Object[] params) {
            return null;
        }
    }
}