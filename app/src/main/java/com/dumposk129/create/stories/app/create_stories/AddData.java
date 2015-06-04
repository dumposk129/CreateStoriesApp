package com.dumposk129.create.stories.app.create_stories;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.watch.MainActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by DumpOSK129
 */
public class AddData extends Activity {
    //context
    private Context context;

    //View
    private EditText txtName;
    private EditText txtDetail;
    private Button submit2;
    private TextView result2;

    private MyHttpPoster poster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);
        context = this;

        // view matching
        txtName = (EditText) findViewById(R.id.name1);
        txtDetail = (EditText) findViewById(R.id.name2);
        submit2 = (Button) findViewById(R.id.main_submit2);
        result2 = (TextView) findViewById(R.id.main_result2);

        // event for submit button
        submit2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String new_name1 = txtName.getText().toString().trim();
                String new_name2 = txtDetail.getText().toString().trim();
                if (new_name1.length() == 0 || new_name2.length() == 0) {
                    Toast.makeText(context, "Please Enter", Toast.LENGTH_LONG);
                } else {
                    //ready to sent
                    poster = new MyHttpPoster("http://dump.geozigzag.com/api/");
                    //Data to sent
                    ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
                    data.add(new BasicNameValuePair("name", new_name1));
                    data.add(new BasicNameValuePair("detail", new_name2));

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
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("title", new_name1);
                startActivity(i);
            }

        });

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        hideSoftKeyboard(AddData.this);

        return false;
    }

    public static void hideSoftKeyboard(Activity activity) {

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}