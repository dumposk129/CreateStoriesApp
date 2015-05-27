package com.dumposk129.create.stories.app.audio_record;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.dumposk129.create.stories.app.R;

/**
 * Created by DumpOSK129.
 */
public class Main2 extends Activity {
    TextView tvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);

        tvView = (TextView) findViewById(R.id.tvView);

        Intent intent = getIntent();
        String fName = intent.getStringExtra("fname");
        // String lName = intent.getStringExtra("lname");
        //tvView.setText("Your name is: " + fName + " " + lName);
        tvView.setText("Your name is: " + fName);


    }
}