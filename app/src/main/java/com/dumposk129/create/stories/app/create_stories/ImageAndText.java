package com.dumposk129.create.stories.app.create_stories;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dumposk129.create.stories.app.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by DumpOSK129.
 */
public class ImageAndText extends Activity implements View.OnClickListener {
    // Context
    private Context context;
    private EditText senda;
    //private EditText sendb;
    private Button submit2;
    private TextView result2;
    private MyHttpPoster poster;
    private ImageView chosenImageView;
    private Button choosePicture, btn_upload;
    protected String _path_pic = null;
    private Bitmap bmp = null;
    private String up_name;
    private String lat = null, lon = null;

    final String PHP_URL = "http://dump.geozigzag.com/api"; // Change to Your Host.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageandtext);

        context = this;

        // View matching
        senda = (EditText) findViewById(R.id.name1);
        submit2 = (Button) findViewById(R.id.main_submit2);
        result2 = (TextView) findViewById(R.id.main_result2);

        // event for submit button
        submit2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String new_name1 = senda.getText().toString().trim();
                //String new_name2 = sendb.getText().toString().trim();

                if (new_name1.length() == 0) {
                    Toast.makeText(context, "ssss", Toast.LENGTH_LONG);
                } else {
                    //ready to sent
                    poster = new MyHttpPoster("http://victorymonumentmap.com/an105/upimage.php");
                    //Data to sent
                    ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
                    data.add(new BasicNameValuePair("senda", new_name1));
                    poster.doPost(data, new Handler() {
                        public void handleMessage(android.os.Message msg) {
                            switch (msg.what) {
                                case MyHttpPoster.HTTP_POST_OK:
                                    //ok
                                    String resultValue = (String) msg.obj;
                                    result2.setText(resultValue);
                                    Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
                                    break;
                                case MyHttpPoster.HTTP_POST_ERROR:
                                    //Error
                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    });
                }
            }
        });

        chosenImageView = (ImageView) this.findViewById(R.id.ChosenImageView);
        choosePicture = (Button) this.findViewById(R.id.ChoosePictureButton);

        choosePicture.setOnClickListener(this);

        btn_upload = (Button) findViewById(R.id.button2);
        btn_upload.setOnClickListener(this);
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

        String new_name1 = senda.getText().toString().trim();

        if (new_name1.length() == 0) {
            Toast.makeText(context, "aaa", Toast.LENGTH_LONG);
        } else {
            //ready to sent
            poster = new MyHttpPoster("http://dump.geozigzag.com/api/");
            //Data to sent
            ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
            data.add(new BasicNameValuePair("title", new_name1));

            poster.doPost(data, new Handler() {
                public void handleMessage(android.os.Message msg) {
                    switch (msg.what) {
                        case MyHttpPoster.HTTP_POST_OK:
                            // OK
                            String resultValue = (String) msg.obj;
                            result2.setText(resultValue);
                            Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
                            break;
                        case MyHttpPoster.HTTP_POST_ERROR:
                            // Error
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
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


    }

    ///  AsyncTask  Upload Image
    class ImageUploadTask extends AsyncTask<Bitmap, Integer, String> {
        private ProgressDialog progressDialog = new ProgressDialog(ImageAndText.this);
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
            AlertDialog.Builder alertBox = new AlertDialog.Builder(ImageAndText.this);
            alertBox.setTitle("Information");
            alertBox.setNeutralButton("Ok", null);
            if (err != null) {
                alertBox.setMessage("Error!! \n" + res);
            } else {
                alertBox.setMessage(res);
            }
            alertBox.show();
        }
    }
}