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
public class SaveTitle extends Activity {
    // Context
    private Context context;

    // View
    private EditText title_name;
    private EditText description;
    private Button submit;
    private TextView result;
    private MyHttpPoster poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_name);

        context = this;
        title_name = (EditText) findViewById(R.id.txtName);
     //   description = (EditText) findViewById(R.id.txtDes);
        submit = (Button) findViewById(R.id.btnSubmit);
        result = (TextView) findViewById(R.id.tvResult);

        // event for submit button
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String new_name1 = title_name.getText().toString().trim();
             //   String new_name2 = description.getText().toString().trim();

                if (new_name1.length() == 0/* || new_name2.length() == 0*/) {
                    Toast.makeText(context, "Please Enter", Toast.LENGTH_LONG);
                } else {
                    // Ready to sent
                    poster = new MyHttpPoster("http://dump.geozigzag.com/api/text.php");

                    //Data to sent
                    ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
                    data.add(new BasicNameValuePair("title_name", new_name1));
                //    data.add(new BasicNameValuePair("description", new_name2));

                    poster.doPost(data, new Handler() {
                        public void handleMessage(android.os.Message msg) {
                            switch (msg.what) {
                                case MyHttpPoster.HTTP_POST_OK:
                                    // OK
                                    String resultValue = (String) msg.obj;
                                    result.setText(resultValue);
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
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    // Keyboard
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideSoftKeyboard(SaveTitle.this);
        return false;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}