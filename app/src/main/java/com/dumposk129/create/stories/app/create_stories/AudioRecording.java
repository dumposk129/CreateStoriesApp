package com.dumposk129.create.stories.app.create_stories;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.api.ApiConfig;
import com.dumposk129.create.stories.app.model.Audio;
import com.dumposk129.create.stories.app.sql.DatabaseHelper;
import com.dumposk129.create.stories.app.watch.ShowStories;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by DumpOSK129.
 */
public class AudioRecording extends ActionBarActivity implements MediaPlayer.OnCompletionListener, View.OnClickListener {
    private ImageView imgView;
    private MediaRecorder recorder = null;
    private MediaPlayer mPlayer;
    private Button btnStartRecording,btnPlayRecording, btnNext;
    private Chronometer chronometer;
    private Bitmap bitmap;
    private long frame_id, frame_order;
    private double recordingDuration = 0;
    private String duration;
    private String path_pic = null;
    private int sId;
    private int stateRec = 0, statePlay = 0;

    private static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");
    private static final MediaType MEDIA_TYPE_MP4 = MediaType.parse("audio/mp4");

    private static final String path_audio = "StoryApp/StoryName/Audio";
    private java.util.Date date;

    private String fName;
    private File dir;

    DatabaseHelper db;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio);

        // Casting
        btnStartRecording = (Button) findViewById(R.id.btnStartRecord);
        btnPlayRecording = (Button) findViewById(R.id.btnPlay);
        imgView = (ImageView) findViewById(R.id.imgAudioRec);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        btnNext = (Button) findViewById(R.id.btnNext);

        // Set Listener.
        btnStartRecording.setOnClickListener(this);
        btnPlayRecording.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        // Set Enable.
        btnPlayRecording.setEnabled(false);
        btnNext.setEnabled(true);

        // Set audio name.
        date = new java.util.Date();
        fName = "audio_" + date.getTime() + ".mp4";
        dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + path_audio + "/" + fName);

        // Get data.
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("sId")) {
                sId = getIntent().getExtras().getInt("sId");
            }

            if (bundle.containsKey("frame_id")) {
                frame_id = (int) getIntent().getExtras().getLong("frame_id");
            }

            if (bundle.containsKey("frame_order")) {
                frame_order = (int) getIntent().getExtras().getLong("frame_order");
            }
        }

        // Call class ShowImage.
        bitmap = ShowImage.showImage(AudioRecording.this, sId, imgView);
        db = new DatabaseHelper(getApplicationContext());
        path_pic = db.getPath(sId);
    }

    /* This method is when play finish, show and set data to default. */
    public void onCompletion(MediaPlayer mp) {
        btnStartRecording.setEnabled(true);
        btnPlayRecording.setEnabled(true);
        btnNext.setEnabled(true);
        chronometer.stop();

        btnPlayRecording.setText("PLAY AUDIO");
        btnPlayRecording.setBackgroundColor(getResources().getColor(R.color.tealA400));
    }

    public void onClick(View v) {
        if (v == btnStartRecording && stateRec == 0) { // Check btnStartRec and stateRec = 0
            chronometer.start();
            // Set type recorder.
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            chronometer.setBase(SystemClock.elapsedRealtime());

            dir.mkdirs(); // Create folder.

            if (dir.exists()) { // Check file is exist and then delete.
                dir.delete();
            }

            try {
                dir.createNewFile(); // Create file.
            } catch (IOException e) {
                throw new IllegalStateException("Failed to create " + dir.toString());
            }

            recorder.setOutputFile(dir.getPath()); // Set output file follow dir.

            try {
                recorder.prepare(); // Preparation before start.
            } catch (IllegalStateException e) {
                throw new RuntimeException("IllegalStateException on MediaRecorder.prepare", e);
            } catch (IOException e) {
                throw new RuntimeException("IOException on MediaRecorder.prepare", e);
            }

            recorder.start(); // Start record.
            btnPlayRecording.setEnabled(false); // Set btnPlay enable false.

            // Change text and background color.
            btnStartRecording.setText("STOP RECORD");
            btnStartRecording.setBackgroundColor(getResources().getColor(R.color.red));

            stateRec++;
        } else if (v == btnStartRecording && stateRec == 1) { // Check btnStartRec and stateRec = 1
            chronometer.stop();
            recorder.stop();
            recorder.release();

            recordingDuration = (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000.0; // Calculate record duration.
            mPlayer = new MediaPlayer();
            mPlayer.setOnCompletionListener(this);

            try {
                mPlayer.setDataSource(dir.getAbsolutePath()); // Set audio path from dir.
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Illegal Argument to MediaPlayer.setDataSource", e);
            } catch (IllegalStateException e) {
                throw new RuntimeException("Illegal State in MediaPlayer.setDataSource", e);
            } catch (IOException e) {
                throw new RuntimeException("IOException in MediaPalyer.setDataSource", e);
            }

            try {
                mPlayer.prepare();
            } catch (IllegalStateException e) {
                throw new RuntimeException("IllegalStateException in MediaPlayer.prepare", e);
            } catch (IOException e) {
                throw new RuntimeException("IOException in MediaPlayer.prepare", e);
            }

            // Convert time to string.
            DecimalFormat df = new DecimalFormat("#.#");
            duration = df.format(recordingDuration);
            duration += getString(R.string.duration);

            // Set Enable.
            btnStartRecording.setEnabled(true);
            btnPlayRecording.setEnabled(true);
            btnNext.setEnabled(true);

            // Set Text and background color.
            btnStartRecording.setBackgroundColor(getResources().getColor(R.color.amber400));
            btnStartRecording.setText("START RECORD");

            stateRec = 0;
        } else if (v == btnPlayRecording && statePlay == 0) {
            mPlayer.start();
            chronometer.setBase(SystemClock.elapsedRealtime()); // Set Timer is 0.
            chronometer.start();

            // Set Enable.
            btnStartRecording.setEnabled(false);
            btnNext.setEnabled(false);

            // Change text and background.
            btnPlayRecording.setText("STOP AUDIO");
            btnPlayRecording.setBackgroundColor(getResources().getColor(R.color.red));

            statePlay++;
        } else if (v == btnPlayRecording && statePlay == 1) {
            mPlayer.stop();
            chronometer.stop();

            // Set Enable.
            btnStartRecording.setEnabled(true);
            btnPlayRecording.setEnabled(true);
            btnNext.setEnabled(true);

            // Change text and background.
            btnPlayRecording.setBackgroundColor(getResources().getColor(R.color.cyan400));
            btnPlayRecording.setText("PLAY AUDIO");

            statePlay = 0;
        } else {
            createAudioInSQLiteDB(); // Save To SQLiteDB

            // Call AsyncTask
            new UploadToServerTask().execute();
        }
    }

    /* Show Dialog "Do you want to create frame continue." */
    private void showDialog() {
        AlertDialog dialog = new AlertDialog.Builder(AudioRecording.this)
                .setTitle("Please select")
                .setMessage("Do you want to continue create frame?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // Yes
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        intent = new Intent(AudioRecording.this, SelectBackground.class);
                        Runtime.getRuntime().freeMemory();
                        intent.putExtra("sId", sId);
                        intent.putExtra("frame_id", frame_id);
                        intent.putExtra("frame_order", frame_order);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { //No
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Go to Main Page
                        intent = new Intent(AudioRecording.this, ShowStories.class);
                        Runtime.getRuntime().freeMemory();
                        startActivity(intent);
                    }
                })
                .show();
    }

    private long createAudioInSQLiteDB() {
        db = new DatabaseHelper(getApplicationContext());
        Audio audio = new Audio();
        if (path_audio != "") { // Check path_audio isn't empty.
            audio.setFrameID((int) frame_id);
            audio.setPathAudio(path_audio);
            audio.setDuration(duration);
        }
        return db.createNewAudio(audio);
    }

    private class UploadToServerTask extends AsyncTask<Void, Integer, Void> {
        private ProgressDialog progressDialog = new ProgressDialog(AudioRecording.this);

        // Preparing before upload.
        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Uploading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(1000);
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressPercentFormat(NumberFormat.getPercentInstance());
            progressDialog.show();

            // Set dialog when runnable.
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (progressDialog.getProgress() <= progressDialog.getMax()) {
                            Thread.sleep(200);
                            handler.sendMessage(handler.obtainMessage());
                            if (progressDialog.getProgress() == progressDialog.getMax()) {
                                progressDialog.dismiss();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        // Increase each 1 percent.
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progressDialog.incrementProgressBy(1);
            }
        };

        @Override
        protected Void doInBackground(Void... params) {
            // Upload file using httpOk.
            OkHttpClient client = new OkHttpClient();
            File imgFile = new File(path_pic);
            // requestBody file.
            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("sId", Integer.toString(sId))
                    .addFormDataPart("image", imgFile.getName(), RequestBody.create(MEDIA_TYPE_JPG, imgFile))
                    .addFormDataPart("audio", dir.getName(), RequestBody.create(MEDIA_TYPE_MP4, dir))
                    .build();

            // Uploading
            Request request = new Request.Builder()
                    .url(ApiConfig.hostname("create_frame"))
                    .post(requestBody)
                    .build();

            // Response.
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!response.isSuccessful()) {
                new IOException("Unexpected Code " + response);
            } else {
                Log.d("Upload Success", response.body().toString());
            }
            return null;
        }

        // Update percent while uploading.
        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            progressDialog.incrementProgressBy(1);
        }

        // show dialog when upload has been finish.
        @Override
        protected void onPostExecute(Void aVoid) {
            if (progressDialog.isShowing()) progressDialog.dismiss();
            showDialog();
        }
    }
}