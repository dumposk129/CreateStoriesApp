package com.dumposk129.create.stories.app.create_stories;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dumposk129.create.stories.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DumpOSK129.
 */
public class SoundPath extends Activity {
    private ListView lstView;
    private Handler handler = new Handler();

    List<String> ImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soundpath);
        /*** Get Images from SDCard ***/
        ImageList = getSD();

        // ListView and imageAdapter
        lstView = (ListView) findViewById(R.id.listView1);
        lstView.setAdapter(new ImageAdapter(this));
    }

    private List<String> getSD() {
        List<String> it = new ArrayList<String>();
        File f = new File("/mnt/sdcard/DCIM/Camera/aa/");
        File[] files = f.listFiles();

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            Log.d("Count", file.getPath());
            it.add(file.getPath());
        }
        return it;
    }

    public class ImageAdapter extends BaseAdapter {
        private Context context;

        public ImageAdapter(Context c) {
            context = c;
        }

        public int getCount() {
            return ImageList.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.activity_column, null);
            }

            // ColImgName
            TextView txtName = (TextView) convertView.findViewById(R.id.ColImgName);
            String strPath = ImageList.get(position).toString();

            // Get File Name
            final String fileName = strPath.substring(strPath.lastIndexOf('/') + 1, strPath.length());
            File file = new File(strPath);
            long length = file.length();
            txtName.setPadding(3, 0, 0, 0);
            txtName.setText(fileName + " (" + length / 1024 + " KB.)");

            // Image Resource
            ImageView imageView = (ImageView) convertView.findViewById(R.id.ColImgPath);
            imageView.getLayoutParams().height = 110;
            imageView.getLayoutParams().width = 110;
            imageView.setPadding(10, 10, 2, 10);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Bitmap bm = BitmapFactory.decodeFile(strPath);
            imageView.setImageBitmap(bm);

            // ColStatus
            final TextView txtStatus = (TextView) convertView.findViewById(R.id.ColStatus);
            txtStatus.setPadding(3, 0, 0, 0);
            txtStatus.setText("...");

            // progressBar
            final ProgressBar progress = (ProgressBar) convertView.findViewById(R.id.progressBar);
            progress.setVisibility(View.GONE);
            progress.setPadding(0, 0, 0, 0);

            //btnUpload
            final Button btnUpload = (Button) convertView.findViewById(R.id.btnUpload);
            btnUpload.setTextColor(Color.BLACK);
            btnUpload.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Upload
                    btnUpload.setEnabled(false);
                    btnUpload.setTextColor(Color.GRAY);

                    startUpload(position);
                }
            });
            final Button submitBtn = (Button) convertView.findViewById(R.id.btnSubmit);
            submitBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Upload
                    Intent intent = new Intent(inflater.getContext(), Upload.class);
                    intent.putExtra("fname", fileName);
                    //intent.putExtra("lname", etLName.getText().toString());
                    startActivity(intent);
                }
            });
            return convertView;

        }
    }

    //Upload
    public void startUpload(final int position) {

        Runnable runnable = new Runnable() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        View v = lstView.getChildAt(position - lstView.getFirstVisiblePosition());

                        // Show ProgressBar
                        ProgressBar progress = (ProgressBar) v.findViewById(R.id.progressBar);
                        progress.setVisibility(View.VISIBLE);

                        // Status
                        TextView status = (TextView) v.findViewById(R.id.ColStatus);
                        status.setText("Uploading..");

                        new UploadFileAsync().execute(String.valueOf(position));
                    }
                });
            }
        };
        new Thread(runnable).start();
    }

    // Async Upload
    public class UploadFileAsync extends AsyncTask<String, Void, Void> {

        String resServer;
        int position;

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            position = Integer.parseInt(params[0]);

            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            int resCode = 0;
            String resMessage = "";

            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";

            // File Path
            String strSDPath = ImageList.get(position).toString();

            // Upload to PHP Script
            String strUrlServer = "http://dump.geozigzag.com/api/";

            try {
                /** Check file on SD Card ***/
                File file = new File(strSDPath);
                if (!file.exists()) {
                    resServer = "{\"StatusID\":\"0\",\"Error\":\"Please check path on SD Card\"}";
                    return null;
                }

                FileInputStream fileInputStream = new FileInputStream(new File(strSDPath));

                URL url = new URL(strUrlServer);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");

                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Content-Type",
                        "multipart/form-data;boundary=" + boundary);

                DataOutputStream outputStream = new DataOutputStream(conn
                        .getOutputStream());
                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                outputStream
                        .writeBytes("Content-Disposition: form-data; name=\"fileUpload\";filename=\""
                                + strSDPath + "\"" + lineEnd);
                outputStream.writeBytes(lineEnd);

                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // Read file
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {
                    outputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                outputStream.writeBytes(lineEnd);
                outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Response Code and  Message
                resCode = conn.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    int read = 0;
                    while ((read = is.read()) != -1) {
                        bos.write(read);
                    }
                    byte[] result = bos.toByteArray();
                    bos.close();

                    resMessage = new String(result);

                }

                Log.d("resCode=", Integer.toString(resCode));
                Log.d("resMessage=", resMessage.toString());

                fileInputStream.close();
                outputStream.flush();
                outputStream.close();

                resServer = resMessage.toString();


            } catch (Exception ex) {
                // Exception handling
                return null;
            }

            return null;
        }

        protected void onPostExecute(Void unused) {
            statusWhenFinish(position, resServer);
        }
    }

    // When UPload Finish
    protected void statusWhenFinish(int position, String resServer) {

        View v = lstView.getChildAt(position - lstView.getFirstVisiblePosition());

        // Show ProgressBar
        ProgressBar progress = (ProgressBar) v.findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);


        // Status
        TextView status = (TextView) v.findViewById(R.id.ColStatus);

        /** Get result from Server (Return the JSON Code)
         * StatusID = ? [0=Failed,1=Complete]
         * Error	= ?	[On case error return custom error message]
         *
         * Eg Upload Failed = {"StatusID":"0","Error":"Cannot Upload file!"}
         * Eg Upload Complete = {"StatusID":"1","Error":""}
         */

        /*** Default Value ***/
        String strStatusID = "0";
        String strError = "Unknow Status!";

        try {
            JSONObject c = new JSONObject(resServer);
            strStatusID = c.getString("StatusID");
            strError = c.getString("Error");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Prepare Status
        if (strStatusID.equals("0")) {
            // When update Failed
            status.setText("Upload Failed. (" + strError + ")");
            status.setTextColor(Color.RED);

            // Enabled Button again
            Button btnUpload = (Button) v.findViewById(R.id.btnUpload);
            btnUpload.setText("Re-try");
            btnUpload.setTextColor(Color.RED);
            btnUpload.setEnabled(true);
        } else {
            status.setText("Upload Completed.");
            status.setTextColor(Color.GREEN);
        }
    }
}