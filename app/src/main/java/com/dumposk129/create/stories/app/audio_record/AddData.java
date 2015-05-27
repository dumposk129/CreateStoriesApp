package com.dumposk129.create.stories.app.audio_record;

import android.app.Activity;
import android.content.Context;
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
    private EditText fname;
    private EditText lname;
    private Button submit;
    private TextView result;

    private MyHttpPoster poster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        context = this;

        // View matching
        fname = (EditText) findViewById(R.id.main_first_name);
        lname = (EditText) findViewById(R.id.main_last_name);
        //dname = (EditText) findViewById(R.id.main_d_name);
        submit = (Button) findViewById(R.id.main_submit);
        result = (TextView) findViewById(R.id.main_result);

        // Event for submit button
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String first_name = fname.getText().toString().trim();
                String last_name = lname.getText().toString().trim();
                //String d_name = dname.getText().toString().trim();

                if (first_name.length() == 0 || last_name.length() == 0) {
                    Toast.makeText(context, "ssss", Toast.LENGTH_LONG);
                } else {
                    //ready to sent
                    //poster = new MyHttpPoster ("http://www.kusrcxcode.com/an70/addDataMember.php");
                    poster = new MyHttpPoster("http://www.myxcode.com/an202/upfile3.php");

                    //Data to sent
                    ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
                    data.add(new BasicNameValuePair("fname", first_name));
                    data.add(new BasicNameValuePair("lname", last_name));
                    // data.add(new BasicNameValuePair("dname", d_name));

                    poster.doPost(data, new Handler() {
                        public void handleMessage(android.os.Message msg) {
                            switch (msg.what) {
                                case MyHttpPoster.HTTP_POST_OK:
                                    //ok
                                    String resultValue = (String) msg.obj;
                                    result.setText(resultValue);
                                    Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();

                                    //Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
                                    //startActivity(i);

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