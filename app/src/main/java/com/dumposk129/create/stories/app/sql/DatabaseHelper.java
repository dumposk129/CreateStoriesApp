package com.dumposk129.create.stories.app.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by DumpOSK129.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    // LOG
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Story";

    // Table Names
    private static String TABLE_STORY = "story";
    private static String TABLE_FRAME = "frame";
    private static String TABLE_AUDIO = "audio";

    // Common column names
    private static int KEY_ID = 1;

    // CREATE Table - column names
    private static String KEY_TITLE_NAME = "title_name";
    private static int KEY_COMPLETE = 1;

    // FRAME Table - column names
    private static int KEY_FRAME_ORDER = 1;
    private static String KEY_PATH_VIDEO = "path_video";
    private static String KEY_PATH_PIC = "path_pic";
    private static int KEY_STORY_ID = 1;
    private static int KEY_STEP = 0;

    // AUDIO Table - column names
    private static String KEY_AUDIO = "path_audio";
    private static String KEY_DURATION = "duration";
    private static int KEY_FRAME_ID = 1;

    // Table Create Statements
    // Story table create statement
    private static final String CREATE_TABLE_STORY =
            "CREATE TABLE " + TABLE_STORY +
                    "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                        + KEY_TITLE_NAME + " TEXT NOT NULL,"
                        + KEY_COMPLETE + " NUMERIC NOT NULL," +
                    ")";

    // Frame table create statement
    private static final String CREATE_TABLE_FRAME =
            "CREATE TABLE " + TABLE_FRAME +
                    "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                        + KEY_FRAME_ORDER + " INTEGER NOT NULL,"
                        + KEY_PATH_VIDEO +  " TEXT NULL,"
                        + KEY_PATH_PIC + " TEXT NULL,"
                        + KEY_STORY_ID + " INTEGER,"
                        + KEY_STEP + " INTEGER NOT NULL,"
                        + "FOREIGN KEY("+KEY_STORY_ID+") REFERENCES "+TABLE_STORY+" ("+KEY_ID+")," +
                    ")";

    // Audio table create statement
    private static final String CREATE_TABLE_AUDIO =
            "CREATE TABLE " + TABLE_AUDIO +
                    "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                        + KEY_AUDIO + " TEXT NULL,"
                        + KEY_DURATION + " TEXT NULL,"
                        + KEY_FRAME_ID + " INTEGER,"
                        + "FOREIGN KEY("+KEY_FRAME_ID+") REFERENCES "+TABLE_FRAME+" ("+KEY_ID+"),"+
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STORY);
        /*"CREATE TABLE " + TABLE_STORY +
                        "( " +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                            " title_name TEXT(100) NOT NULL," +
                            " is_complete NUMERIC NOT NULL" +
                        ");"
        );*/
        db.execSQL(CREATE_TABLE_FRAME);
                /*"CREATE TABLE " + TABLE_FRAME +
                        "( " +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                            " frame_order INTEGER NOT NULL," +
                            " path_video TEXT," +
                            " path_pic TEXT," +
                            " step INTEGER NOT NULL," +
                            " story_id INTEGER," +
                            " FOREIGN KEY(story_id) REFERENCES " + TABLE_STORY + "id" +
                        ");"
        );*/
        db.execSQL(CREATE_TABLE_AUDIO);
        /*"CREATE TABLE " + TABLE_AUDIO +
                        "( " +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                            " audio_path TEXT," +
                            " duration NUMERIC," +
                            " frame_id INTEGER," +
                            " FOREIGN KEY(frame_id) REFERENCES " + TABLE_FRAME + "id" +
                        ");"
        );*/

        Log.d("CREATE TABLE", "Create Table Successfully.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop old tables.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUDIO);
        onCreate(db);
    }
}
