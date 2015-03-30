package com.dumposk129.create.stories.app.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dumposk129.create.stories.app.create.StoryTable;


public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "the_story.db";
    private static final int DATABASE_VERSION = 1;
    public MySQLiteOpenHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + StoryTable.TABLE
                + "("
                + StoryTable.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + StoryTable.NAME + " VARCHAR(255)"
                + ")" );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + StoryTable.TABLE);
        onCreate(db);
    }
}
