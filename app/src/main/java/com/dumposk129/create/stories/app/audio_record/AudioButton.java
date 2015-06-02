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
    private Button btnAudioRecording, btnShowDataImages, btnGetPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_button);

        btnAudioRecording = (Button) findViewById(R.id.mBtn1);
        btnAudioRecording.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), AudioRecording.class);
                startActivity(i);
            }
        });

        btnShowDataImages = (Button) findViewById(R.id.mBtn2);
        // View products click event
        btnShowDataImages.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), ShowDataImages.class);
                startActivity(i);
            }
        });

        btnGetPath = (Button) findViewById(R.id.mBtn3);
        // View products click event
        btnGetPath.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), GetPath.class);
                startActivity(i);
            }
        });
    }
}