package com.dumposk129.create.stories.app.create;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dumposk129.create.stories.app.R;

import java.io.File;
import java.io.FileOutputStream;


public class CreateStory extends ActionBarActivity {
    int windowWidth;
    int windowHeight;
    public static LinearLayout.LayoutParams layoutParams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_story);
        Intent intent = getIntent();
        String message = intent.getStringExtra(Charactor_Show.CHARACTOR_INDEX);
        int charactor_index = Integer.parseInt(message);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        switch (charactor_index){
            case 0: imageView.setBackgroundResource(R.mipmap.boysit); break;
            case 1: imageView.setBackgroundResource(R.mipmap.boysleep); break;
            case 2: imageView.setBackgroundResource(R.mipmap.boystand); break;
            case 3: imageView.setBackgroundResource(R.mipmap.boywalk); break;
            case 4: imageView.setBackgroundResource(R.mipmap.boywalkfull); break;
        }
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        windowWidth = size.x;
        windowHeight = size.y;
        View.OnTouchListener backListener = new View.OnTouchListener(){
            public boolean onTouch(View view,  MotionEvent event){
                layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x_cord = (int)event.getRawX();
                        int y_cord = (int)event.getRawY();
                        if (x_cord > windowWidth){
                            x_cord = windowWidth;
                        }
                        if (y_cord > windowHeight){
                            y_cord = windowHeight;
                        }
                        layoutParams.leftMargin = x_cord - 25;
                        layoutParams.topMargin = y_cord -  75;
                        imageView.setLayoutParams(layoutParams);

                        WriteToFile(String.valueOf(x_cord),String.valueOf(y_cord));
                        break;

                    default: break;
                }
                return true;
            }
        };
        imageView.setOnTouchListener(backListener);
    }

    private void WriteToFile(String x, String y) {
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "tmp/testFile.txt");

        FileOutputStream fos;
        try {
            String msg = x + "," + y;
            fos = new FileOutputStream(file);
            fos.write(msg.getBytes());
            fos.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
        }


        /*TextView tv = (TextView)findViewById(R.id.textView1);
        try{
            StringBuilder text = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            tv.setText(text.toString());
        }
        catch(IOException e){
            tv.setText(e.getMessage());
        }*/


    }
}
