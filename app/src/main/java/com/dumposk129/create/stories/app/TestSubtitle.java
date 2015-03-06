package com.dumposk129.create.stories.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by DumpOSK129 on 3/6/2015.
 */
public class TestSubtitle extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_subtitle);
    }


    public void ClickEdit(View v){
        EditText text = (EditText) findViewById(R.id.editText);
        String subtitle = text.getText().toString();

        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard,"tmp/testFile.txt");

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            fos.write(subtitle.getBytes());
            fos.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void showSubtitle(){
        final TextView textView = (TextView) findViewById(R.id.showText);
        File sdcard = Environment.getExternalStorageDirectory();
        final File file = new File(sdcard,"tmp/testFile.txt");


        Button btnShow = (Button) findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    StringBuilder text = new StringBuilder();
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = br.readLine()) != null) {
                        text.append(line);
                        text.append('\n');
                    }
                    textView.setText(text.toString());
                }
                catch(IOException e){
                    //textView.setText(e.getMessage());
                }

            }
        });
    }
}
