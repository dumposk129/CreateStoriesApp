package com.dumposk129.create.stories.app.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dumposk129.create.stories.app.model.Frame;
import com.dumposk129.create.stories.app.model.Story;

/**
 * Created by DumpOSK129.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    SQLiteDatabase db;

    // LOG
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Story";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create story
        db.execSQL(Schema.CREATE_TABLE_STORY);
        Log.d("CREATE TABLE: "+ Schema.TABLE_STORY, "Create Table Successfully.");

        // Create Frame
        db.execSQL(Schema.CREATE_TABLE_FRAME);
        Log.d("CREATE TABLE: "+ Schema.TABLE_FRAME, "Create Table Successfully.");

        // Create audio
        db.execSQL(Schema.CREATE_TABLE_AUDIO);
        Log.d("CREATE TABLE: "+ Schema.TABLE_AUDIO, "Create Table Successfully.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop old tables.
        db.execSQL(Schema.DROP_TABLE_STORY);
        db.execSQL(Schema.DROP_TABLE_FRAME);
        db.execSQL(Schema.DROP_TABLE_AUDIO);
        onCreate(db);
    }

    public long createNewStory(Story story){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Schema.KEY_TITLE_NAME, story.getTitle());
        values.put(Schema.KEY_IS_COMPLETE, story.getIsCompleteInt());

        long story_id = db.insert(Schema.TABLE_STORY, null, values);

        return story_id;
    }

    public Story getStory(int id){
        db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Schema.TABLE_STORY + " WHERE " + Schema.KEY_ID + " = " + id;

        Log.e(LOG, query);

        Cursor c = db.rawQuery(query, null);

        if (c != null){
            c.moveToFirst();
        }

        boolean isComplete = c.getInt(c.getColumnIndex(Schema.KEY_IS_COMPLETE)) == 1 ? true : false;

        Story story = new Story(c.getInt(c.getColumnIndex(Schema.KEY_ID)), c.getString(c.getColumnIndex(Schema.KEY_TITLE_NAME)), isComplete);

        return story;
    }

    public long createNewFrame(Frame frame){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Schema.KEY_FRAME_ORDER, frame.getFrameOrder());
        values.put(Schema.KEY_STEP, frame.getStep());
        values.put(Schema.KEY_STORY_ID, frame.getStoryId());

        long frame_id = db.insert(Schema.TABLE_FRAME, null, values);

        return frame_id;
    }

    public int updatePath(int id, int step, String path_pic){
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Schema.KEY_STEP , step);
        values.put(Schema.KEY_PATH_PIC, path_pic);

        // Update
        return db.update(Schema.TABLE_FRAME, values, Schema.KEY_FRAME_ID + " = " + id, new String[]{String.valueOf(id)});
      //  String query = "UPDATE " + Schema.TABLE_FRAME + " SET " + Schema.KEY_PATH_PIC + " = " + path_pic + " WHERE " + Schema.KEY_FRAME_ID + " = " + id;
//
      //  Log.e(LOG, query);

      /*  Cursor c = db.rawQuery(query, null);

        if (c != null){
            c.moveToFirst();
        }
        Frame frame = new Frame(c.getInt(c.getColumnIndex(Schema.KEY_STEP)), c.getString(c.getColumnIndex(Schema.KEY_PATH_PIC)));

        return frame;*/
    }
}