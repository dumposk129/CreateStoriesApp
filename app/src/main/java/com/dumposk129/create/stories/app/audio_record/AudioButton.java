package com.dumposk129.create.stories.app.audio_record;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.dumposk129.create.stories.app.R;

/**
 * Created by DumpOSK129.
 */
public class AudioButton extends Activity{
    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private Button mBtn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_button);

        mBtn1 = (Button) findViewById(R.id.mBtn1);
        // View products click event
        mBtn1.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), AudioRecording.class);
                startActivity(i);
            }
        });

        mBtn2 = (Button) findViewById(R.id.mBtn2);
        // View products click event
        mBtn2.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), GetPath.class);
                startActivity(i);
            }


        });

        mBtn3 = (Button) findViewById(R.id.mBtn3);
        // View products click event
        mBtn3.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), ChoosePicture.class);
                startActivity(i);
            }
        });

        mBtn4 = (Button) findViewById(R.id.mBtn4);
        // View products click event
        mBtn4.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), VideoViewActivity.class);
                startActivity(i);
            }
        });
    }
}