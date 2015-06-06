package com.dumposk129.create.stories.app.create_stories;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.watch.MainActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by DumpOSK129
 */
public class SaveTitle extends Activity {
    private Context context;
    private EditText txtName/*, txtDetail*/;
    private Button submit;
    private MyHttpPoster poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_name);
        context = this;

        // View matching
        txtName = (EditText) findViewById(R.id.name);
        //txtDetail = (EditText) findViewById(R.id.des);
        submit = (Button) findViewById(R.id.btnsubmit);

        // Event for submit button
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = txtName.getText().toString().trim();
             //   String detail = txtDetail.getText().toString().trim();
                if (name.length() == 0) {
                    Toast.makeText(context, "Please Enter", Toast.LENGTH_LONG);
                } else {
                    // Ready to sent
                    poster = new MyHttpPoster("http://dump.geozigzag.com/api/text.php");
                    // Data to sent
                    ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
                    data.add(new BasicNameValuePair("title_name", name));
                 //   data.add(new BasicNameValuePair("description", detail));

                    poster.doPost(data, new Handler() {
                        public void handleMessage(android.os.Message msg) {
                            switch (msg.what) {
                                case MyHttpPoster.HTTP_POST_OK:
                                    // OK
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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}