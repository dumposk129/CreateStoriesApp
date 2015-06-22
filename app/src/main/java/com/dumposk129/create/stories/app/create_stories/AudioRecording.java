package com.dumposk129.create.stories.app.create_stories;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.sql.DatabaseHelper;

import java.io.File;
import java.io.IOException;

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
    File audioFile;

    private static final String PATH = "StoryApp/StoryName/Audio Record";
    private static File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + PATH );

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
    }

    /* Show Image from database */
    private void showImage() {
        String path_pic = null;
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
            Toast.makeText(AudioRecording.this, "Start Recording", Toast.LENGTH_SHORT).show();
            chronometer.start();
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            dir.mkdirs();

            try {
                audioFile = File.createTempFile("recording", ".3gp", dir);
            } catch (IOException e) {
                throw new RuntimeException("Couldn't create recording audio file", e);
            }

            recorder.setOutputFile(audioFile.getAbsolutePath());

            try {
                recorder.prepare();
            } catch (IllegalStateException e) {
                throw new RuntimeException(
                        "IllegalStateException on MediaRecorder.prepare", e);
            } catch (IOException e) {
                throw new RuntimeException(
                        "IOException on MediaRecorder.prepare", e);
            }

            recorder.start();
            btnPlayRecording.setEnabled(false);
            btnStartRecording.setEnabled(false);
            btnStopRecording.setEnabled(true);
            btnStop.setEnabled(false);
            btnNext.setEnabled(false);
        } else if (v == btnStopRecording) {
            Toast.makeText(AudioRecording.this, "Stop Recording", Toast.LENGTH_SHORT).show();
            chronometer.stop();
            recorder.stop();
            recorder.release();

            player = new MediaPlayer();
            player.setOnCompletionListener(this);

            try {
                player.setDataSource(audioFile.getAbsolutePath());
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

            btnPlayRecording.setEnabled(true);
            btnStartRecording.setEnabled(true);
            btnStopRecording.setEnabled(false);
            btnStop.setEnabled(false);
            btnNext.setEnabled(true);
        } else if (v == btnPlayRecording) {
            player.start();
            btnPlayRecording.setEnabled(false);
            btnStopRecording.setEnabled(false);
            btnStartRecording.setEnabled(false);
            btnStop.setEnabled(true);
            btnNext.setEnabled(true);
        } else if (v == btnStop) {
            Toast.makeText(AudioRecording.this, "Stop", Toast.LENGTH_SHORT).show();
            player.stop();

            btnStartRecording.setEnabled(true);
            btnStopRecording.setEnabled(false);
            btnPlayRecording.setEnabled(true);
            btnStop.setEnabled(true);
            btnNext.setEnabled(true);
        } else {
            /*Intent i = new Intent(getApplicationContext(), SoundPath.class);
            startActivity(i);*/
        }
    }
}