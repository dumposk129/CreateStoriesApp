package com.dumposk129.create.stories.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.dumposk129.create.stories.app.model.Frame;
import com.dumposk129.create.stories.app.model.Story;
import com.dumposk129.create.stories.app.sql.DatabaseHelper;

/**
 * Created by DumpOSK129.
 */
public class MainActivity extends ActionBarActivity{

    private Story story;
    private Frame frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Mock DATA
        this.story = new Story("TEST",false);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        long story_id = dbHelper.createNewStory(this.story);
        Toast.makeText(getApplicationContext(),"Story_ID : " +story_id,Toast.LENGTH_SHORT).show();

        Story sr = dbHelper.getStory((int)story_id);
        Toast.makeText(getApplicationContext(),"Story_Name : "+sr.getTitle(),Toast.LENGTH_SHORT).show();


        long frame_id = dbHelper.createNewFrame(this.frame);
        Toast.makeText(getApplicationContext(), "Frame_ID : "+frame_id, Toast.LENGTH_SHORT).show();
        Frame fr = dbHelper.updatePath((int)frame_id);
        Toast.makeText(getApplicationContext(), "Path : "+fr.getPathPic(), Toast.LENGTH_SHORT).show();
    }
}
