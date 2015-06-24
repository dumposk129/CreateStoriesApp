package com.dumposk129.create.stories.app.sql;

/**
 * Created by DumpOSK129.
 */
public class Schema {

    // constraint declaration
    /** Main **/
    public static String KEY_ID = "id";

    /** Story **/
    public static String TABLE_STORY = "story";
    public static String KEY_TITLE_NAME = "title_name";
    public static String KEY_IS_COMPLETE = "is_complete";

    /** Frame **/
    public static String TABLE_FRAME = "frame";
    //public static String KEY_FRAME_ORDER = "frame_order";
    public static String KEY_PATH_VIDEO = "path_video";
    public static String KEY_PATH_PIC = "path_pic";
    public static String KEY_STORY_ID = "story_id";
    //public static String KEY_STEP = "step";

    /** Audio **/
    public static String TABLE_AUDIO = "audio";
    public static String KEY_AUDIO_PATH = "path_audio";
    public static String KEY_DURATION = "duration";
    public static String KEY_FRAME_ID = "frame_id";

    // CREATE TABLE table
    public static final String CREATE_TABLE_STORY = "CREATE TABLE " + TABLE_STORY +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_TITLE_NAME + " TEXT NOT NULL,"
                + KEY_IS_COMPLETE + " NUMERIC NOT NULL" +
            ")";

    // CREATE FRAME table
    public static final String CREATE_TABLE_FRAME =
            "CREATE TABLE " + TABLE_FRAME +
                    "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                      //  + KEY_FRAME_ORDER + " INTEGER NOT NULL,"
                        + KEY_PATH_VIDEO +  " TEXT NULL,"
                        + KEY_PATH_PIC + " TEXT NULL,"
                        + KEY_STORY_ID + " INTEGER,"
                       // + KEY_STEP + " INTEGER NOT NULL,"
                        + "FOREIGN KEY("+KEY_STORY_ID+") REFERENCES "+TABLE_STORY+" ("+KEY_ID+")" +
                    ")";

    // Create AUDIO Table
    public static final String CREATE_TABLE_AUDIO =
            "CREATE TABLE " + TABLE_AUDIO +
                    "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                        + KEY_AUDIO_PATH + " TEXT NULL,"
                        + KEY_DURATION + " TEXT NULL,"
                        + KEY_FRAME_ID + " INTEGER,"
                        + "FOREIGN KEY("+KEY_FRAME_ID+") REFERENCES "+TABLE_FRAME+" ("+KEY_ID+")"+
                    ")";

    // Drop STORY Table
    public static final String DROP_TABLE_STORY = "DROP TABLE IF EXISTS " + TABLE_STORY;

    // Drop FRAME Table
    public static final String DROP_TABLE_FRAME = "DROP TABLE IF EXISTS"  + TABLE_FRAME;

    // Drop AUDIO Table
    public static final String DROP_TABLE_AUDIO = "DROP TABLE IF EXISTS" + TABLE_AUDIO;
}