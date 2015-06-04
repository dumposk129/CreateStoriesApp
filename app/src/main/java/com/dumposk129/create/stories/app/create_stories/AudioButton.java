package com.dumposk129.create.stories.app.create_stories;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.dumposk129.create.stories.app.R;

/**
 * Created by DumpOSK129.
 */
public class AudioButton extends Activity{
    private Button btnAudioRecording, btnShowDataImages, btnCreateStories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_button);

        btnAudioRecording = (Button) findViewById(R.id.btnAudRec);
        btnAudioRecording.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent i = new Intent(getApplicationContext(), AudioRecording.class);
                startActivity(i);
            }
        });

        btnShowDataImages = (Button) findViewById(R.id.btnShowDataImgs);
        btnShowDataImages.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent i = new Intent(getApplicationContext(), ShowDataImages.class);
                startActivity(i);
            }
        });

        btnCreateStories = (Button) findViewById(R.id.btnGetPth);
        btnCreateStories.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                // Show Dialog and user choose it.
                final AlertDialog.Builder builder = new AlertDialog.Builder(AudioButton.this);
                builder.setTitle(R.string.choose_item).setItems(R.array.audio_record_choose_image, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        Intent intent;
                        switch (position) {
                            case 0:
                                intent = new Intent(AudioButton.this, AudioRecording.class);
                                startActivity(intent);
                                break;
                            case 1:
                                intent = new Intent(AudioButton.this, ChooseImage.class);
                                startActivity(intent);
                                break;
                            case 2:
                                dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
    }
}