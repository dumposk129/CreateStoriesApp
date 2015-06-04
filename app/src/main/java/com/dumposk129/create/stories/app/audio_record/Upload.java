package com.dumposk129.create.stories.app.audio_record;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dumposk129.create.stories.app.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DumpOSK129
 */
public class Upload extends Activity implements View.OnClickListener {
    private ImageView chosenImageView;
    private Button choosePicture, btn_upload;
    protected String _path_pic = null;
    private Bitmap bmp = null;
    private String up_name;
    private String lat = null, lon = null;
    private ProgressBar progressBar;
    private String FILE_UPLOAD_URL, filePath, fileName = null;
    private TextView txtPercentage, tvView;
    private Button btnUpload;
    long totalSize = 0;
    String folder = "aa"; //

    final String PHP_URL = "http://victorymonumentmap.com/an105/uppic.php"; // Change to Your Host.

    @SuppressLint("SdCardPath")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);

        //tvView = (EditText) findViewById(R.id.tvView);
        tvView = (TextView) findViewById(R.id.tvView);
        Intent intent = getIntent();
        String fName = intent.getStringExtra("fname");
        tvView.setText(fName);
        txtPercentage = (TextView) findViewById(R.id.txtPercentage);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final TextView txtSDCard = (TextView) findViewById(R.id.tvView);

		/*Server*/
        FILE_UPLOAD_URL = "http://victorymonumentmap.com/an105/upmp4.php";

        chosenImageView = (ImageView) this.findViewById(R.id.ChosenImageView);
        choosePicture = (Button) this.findViewById(R.id.ChoosePictureButton);

        choosePicture.setOnClickListener(this);

        btn_upload = (Button) findViewById(R.id.button2);
        btn_upload.setOnClickListener(this);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fileName = txtSDCard.getText().toString();
                filePath = "/mnt/sdcard/DCIM/Camera/" + folder + "/" + fileName; // ¿…Æ◊∏ÙÆ|¶Ï∏m
                new UploadFileToServer().execute();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String picTime = sdf.format(new Date());
                _path_pic = Environment.getExternalStorageDirectory() + "/myfile/" + picTime + ".jpg";
                up_name = picTime + ".jpg";
                new ImageUploadTask().execute(bmp);

                Intent i = new Intent(getApplicationContext(), AddData.class);
                startActivity(i);
            }
        });
    }

    /* Waiting for upload to server */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // ProgressBar
            progressBar.setProgress(0);
            super.onPreExecute();
        }

        /* Uploading */
        @Override
        protected void onProgressUpdate(Integer... progress) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(progress[0]);
            txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        /* Done */
        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(FILE_UPLOAD_URL);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(new AndroidMultiPartEntity.ProgressListener() {
                    public void transferred(long num) {
                        publishProgress((int) ((num / (float) totalSize) * 100));
                    }
                });

                File sourceFile = new File(filePath);

                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));
                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }
            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }
            return responseString;
        }

        /* Show Message*/
        @Override
        protected void onPostExecute(String result) {
            Log.e("END", "Response from server: " + result);
            showAlert(result);
            super.onPostExecute(result);
        }
    }

    /* Show Alert */
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle("Response from Servers").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Do nothing
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.ChoosePictureButton) {
            Intent choosePictureIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(choosePictureIntent, 0);
        }

        if (v.getId() == R.id.button2) {
            if (bmp != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String picTime = sdf.format(new Date());
                _path_pic = Environment.getExternalStorageDirectory() + "/myfile/" + picTime + ".jpg";
                up_name = picTime + ".jpg";
                new ImageUploadTask().execute(bmp);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_OK) {
            Uri imageFileUri = intent.getData();

            Display currentDisplay = getWindowManager().getDefaultDisplay();
            int dw = currentDisplay.getWidth();
            int dh = currentDisplay.getHeight() / 2 - 100;

            try {
                // Load up the image's dimensions not the image itself
                BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                bmpFactoryOptions.inJustDecodeBounds = true;
                bmp = BitmapFactory
                        .decodeStream(getContentResolver().openInputStream(
                                imageFileUri), null, bmpFactoryOptions);

                int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
                        / (float) dh);
                int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
                        / (float) dw);

                if (heightRatio > 1 && widthRatio > 1) {
                    if (heightRatio > widthRatio) {
                        bmpFactoryOptions.inSampleSize = heightRatio;
                    } else {
                        bmpFactoryOptions.inSampleSize = widthRatio;
                    }
                }

                bmpFactoryOptions.inJustDecodeBounds = false;
                bmp = BitmapFactory
                        .decodeStream(getContentResolver().openInputStream(
                                imageFileUri), null, bmpFactoryOptions);

                chosenImageView.setImageBitmap(bmp);

            } catch (FileNotFoundException e) {
                Log.v("ERROR", e.toString());
            }
        }
    }//end method onActivityResult

    ///  AsyncTask  Upload Image
    class ImageUploadTask extends AsyncTask<Bitmap, Integer, String> {
        private ProgressDialog progressDialog = new ProgressDialog(Upload.this);
        String err = null;

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Uploading...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    ImageUploadTask.this.cancel(true);
                }
            });
        }

        @Override
        protected String doInBackground(Bitmap... arg) {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                arg[0].compress(Bitmap.CompressFormat.JPEG, 75, bos);
                byte[] data = bos.toByteArray();
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost postRequest = new HttpPost(PHP_URL);
                ByteArrayBody bab = new ByteArrayBody(data, up_name);
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                reqEntity.addPart("uploadedfile", bab);

                if (lat != null && lon != null) {
                    reqEntity.addPart("lat", new StringBody(lat));
                    reqEntity.addPart("lon", new StringBody(lon));
                }
                postRequest.setEntity(reqEntity);
                HttpResponse response = httpClient.execute(postRequest);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                String sResponse;
                StringBuilder s = new StringBuilder();
                while ((sResponse = reader.readLine()) != null) {
                    s = s.append(sResponse);
                }
                return s.toString().trim();

            } catch (Exception e) {

                err = "error" + e.getMessage();
                Log.e(e.getClass().getName(), e.getMessage());

                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String res) {
            if (progressDialog.isShowing()) progressDialog.dismiss();
            AlertDialog.Builder alertBox = new AlertDialog.Builder(Upload.this);
            alertBox.setTitle("Information");
            alertBox.setNeutralButton("Ok", null);
            if (err != null) {
                alertBox.setMessage("Success \n" + res);
            } else {
                alertBox.setMessage(res);
            }
            alertBox.show();
        }
    }
}