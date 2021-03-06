package com.dumposk129.create.stories.app.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dumposk129.create.stories.app.model.Audio;
import com.dumposk129.create.stories.app.model.Frame;
import com.dumposk129.create.stories.app.model.Story;

/**
 * Created by DumpOSK129.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;

    // LOG
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "Story";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create story
        db.execSQL(Schema.CREATE_TABLE_STORY);
        Log.d("CREATE TABLE: " + Schema.TABLE_STORY, "Create Table Successfully.");

        // Create Frame
        db.execSQL(Schema.CREATE_TABLE_FRAME);
        Log.d("CREATE TABLE: " + Schema.TABLE_FRAME, "Create Table Successfully.");

        // Create audio
        db.execSQL(Schema.CREATE_TABLE_AUDIO);
        Log.d("CREATE TABLE: " + Schema.TABLE_AUDIO, "Create Table Successfully.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop old tables.
        db.execSQL(Schema.DROP_TABLE_STORY);
        db.execSQL(Schema.DROP_TABLE_FRAME);
        db.execSQL(Schema.DROP_TABLE_AUDIO);
        onCreate(db);
    }

    public long createNewStory(Story story) {
        db = this.getWritableDatabase();

        // Add data into TABLE STORY.
        ContentValues values = new ContentValues();
        values.put(Schema.KEY_TITLE_NAME, story.getTitle());
        values.put(Schema.KEY_IS_COMPLETE, story.getIsCompleteInt());

        long story_id = db.insert(Schema.TABLE_STORY, null, values);

        db.close();

        return story_id; // return story_id.
    }

    public Story getStory(int id) {
        db = this.getReadableDatabase();

        // Select data from story
        String query = "SELECT * FROM " + Schema.TABLE_STORY + " WHERE " + Schema.KEY_ID + " = " + id;

        Log.e(LOG, query);

        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }

        boolean isComplete = c.getInt(c.getColumnIndex(Schema.KEY_IS_COMPLETE)) == 1 ? true : false;

        // Read data
        Story story = new Story(c.getInt(c.getColumnIndex(Schema.KEY_ID)), c.getString(c.getColumnIndex(Schema.KEY_TITLE_NAME)), isComplete);

        c.close();
        db.close();
        
        return story; // return story.
    }

    public long createNewFrame(Frame frame) {
        db = this.getWritableDatabase();

        // Add data into TABLE FRAME.
        ContentValues values = new ContentValues();
        values.put(Schema.KEY_FRAME_ORDER, frame.getFrameOrder());
        values.put(Schema.KEY_STORY_ID, frame.getStoryId());

        long frame_id = db.insert(Schema.TABLE_FRAME, null, values);

        db.close();

        return frame_id; // return frame_id.
    }

    public void updatePath(int frame_id, String path_pic) {
        db = this.getWritableDatabase();

        // Add path_pic
        ContentValues values = new ContentValues();
        values.put(Schema.KEY_PATH_PIC, path_pic);

        // Update path_pic from frame.
        db.update(Schema.TABLE_FRAME, values, Schema.KEY_ID + " = " + frame_id, null);

        db.close();
    }

    public String getPath(int story_id){
        db = this.getReadableDatabase();
        String  path_pic = null;

        // Read path_pic from frame.
        String query = "SELECT " +Schema.KEY_PATH_PIC  + " FROM "+ Schema.TABLE_FRAME
                + " WHERE " + Schema.KEY_STORY_ID + " = " + story_id + " ORDER BY " + Schema.KEY_FRAME_ORDER;

        /* move cursor */
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        // move until cursor is last.
        while (!c.isAfterLast()) {
            path_pic = c.getString(c.getColumnIndex(Schema.KEY_PATH_PIC));
            c.moveToNext();
        }

        c.moveToFirst();

        c.close();
        db.close();
        return path_pic;
    }

    public long createNewAudio(Audio audio){
        db = this.getWritableDatabase();

        // Add data into TABLE AUDIO.
        ContentValues values = new ContentValues();
        values.put(Schema.KEY_FRAME_ID, audio.getFrameID());
        values.put(Schema.KEY_AUDIO_PATH, audio.getPathAudio());
        values.put(Schema.KEY_DURATION, audio.getDuration());

        long audio_id = db.insert(Schema.TABLE_AUDIO, null, values);

        db.close();

        return audio_id; // return audio_id
    }
}