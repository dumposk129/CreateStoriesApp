package com.dumposk129.create.stories.app.create;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dumposk129.create.stories.app.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class EditStory extends ActionBarActivity {
    int windowWidth;
    int windowHeight;
    RelativeLayout relativeLayout;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_story);
        //load save image
        ImageView imageView_Background = new ImageView(this);

        //โหลดจากฐานข้อมูล สมมุติว่าได้ชื่อ background01.jpg
        //imageView_Background.setBackgroundResource(R.mipmap.bg_night);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        relativeLayout.addView(imageView_Background);


        ImageView imageView = null;//= (ImageView)findViewById(R.id.imageView);

        //background image
        if(this.getIntent().getExtras() != null && this.getIntent().getExtras().containsKey("backgroundId")){
            String background_message = this.getIntent().getStringExtra("backgroundId");
            imageView = imageIntend(Integer.parseInt(background_message), "background");
            relativeLayout.addView(imageView);
        }

        //character image
        if(this.getIntent().getExtras() != null && this.getIntent().getExtras().containsKey("characterId")){
            String character_message = this.getIntent().getStringExtra("characterId");
            imageView = imageIntend(Integer.parseInt(character_message), "character");
            relativeLayout.addView(imageView);
        }

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        windowWidth = size.x;
        windowHeight = size.y;
        View.OnTouchListener backListener = new TouchImage(imageView);
        //สร้าง innerclass แทน
//        View.OnTouchListener backListener=new View.OnTouchListener(){
//            public boolean onTouch(View v, MotionEvent event){
//                layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
//                switch(event.getAction())
//                {
//                    case MotionEvent.ACTION_DOWN:  break;
//                    case MotionEvent.ACTION_MOVE:
//                        int x_cord = (int)event.getRawX();
//                        int y_cord = (int)event.getRawY();
//                        if(x_cord>windowWidth){x_cord=windowWidth;}
//                        if(y_cord>windowHeight){y_cord=windowHeight;}
//                        layoutParams.leftMargin = x_cord -25;
//                        layoutParams.topMargin = y_cord - 75;
//                        imageView.setLayoutParams(layoutParams);
//                        //savePoint(String.valueOf(x_cord), String.valueOf(y_cord));
//                        break;
//                    default: break;
//                }
//                return true;
//            }
//        };
        imageView.setOnTouchListener(backListener);
    }
    //public static LinearLayout.LayoutParams layoutParams;
    public RelativeLayout.LayoutParams layoutParams;
    class TouchImage implements  View.OnTouchListener{
        ImageView imageView;
        TouchImage(ImageView imageView){
            this.imageView = imageView;
        }
        public boolean onTouch(View v, MotionEvent event){
            layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:  break;
                case MotionEvent.ACTION_MOVE:
                    int x_cord = (int)event.getRawX();
                    int y_cord = (int)event.getRawY();
                    if(x_cord>windowWidth){x_cord = windowWidth;}
                    if(y_cord>windowHeight){y_cord = windowHeight;}
                    layoutParams.leftMargin = x_cord - 25;
                    layoutParams.topMargin = y_cord - 75;
                    imageView.setLayoutParams(layoutParams);
                    //ปัญทึกลงฐานตำแหน่งข้อมูล หรือจะสร้างปุ่มคำสั่งให้บันทึกก๊ได้
                    break;
                default: break;
            }
            return true;
        }
    }
    private ImageView imageIntend(int imageIndex, String from){
        //final ImageView imageView = (ImageView)findViewById(R.id.imageView);
        ImageView imageView = new ImageView(this);
        if(from == "background") {
            switch (imageIndex) {
                case 0:
                 //   imageView.setBackgroundResource(R.mipmap.bg_night); break;
                /*case 1:
                    imageView.setBackgroundResource(R.drawable.background02); break;*/
            }
        }
        if(from == "character") {
            switch (imageIndex) {
                case 0: imageView.setBackgroundResource(R.mipmap.Cat); break;
                case 1: imageView.setBackgroundResource(R.mipmap.CatSit); break;
                case 2: imageView.setBackgroundResource(R.mipmap.boysit); break;
                case 3: imageView.setBackgroundResource(R.mipmap.boystand); break;
                case 4: imageView.setBackgroundResource(R.mipmap.boywalk); break;
                /*case 5: imageView.setBackgroundResource(R.drawable.sample_5); break;
                case 6: imageView.setBackgroundResource(R.drawable.sample_6); break;
                case 7: imageView.setBackgroundResource(R.drawable.sample_7); break;*/
            }
        }
        return imageView;
    }
    private void savePoint(String x, String y){
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard,"tmp/testFile.txt");

        FileOutputStream fos;
        try {
            String msg = x + "," + y;
            fos = new FileOutputStream(file);
            fos.write(msg.getBytes());
            fos.close();
        } catch (IOException e) {
            Log.e("EditStory", e.getMessage());
        }

    }
}
