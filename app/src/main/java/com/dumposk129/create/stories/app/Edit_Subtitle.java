package com.dumposk129.create.stories.app;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;


public class Edit_Subtitle extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_subtitle);

    }
    public void writeToFile(View v){
        EditText editText = (EditText) findViewById(R.id.editTextSubtitle);
        String subtitle = editText.getText().toString();

        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard,"tmp/testSubTitle.txt");

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            fos.write(subtitle.getBytes());
            fos.close();
        } catch (IOException e) {
            Log.e("Edit_Subtitle", e.getMessage());
        }
    }
    public void showSubtitle(View v){
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard,"tmp/testSubTitle.txt");

        TextView textView = (TextView) findViewById(R.id.textViewSubtitle);
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
            textView.setText(e.getMessage());
        }
    }
}
