package com.dumposk129.create.stories.app;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.dumposk129.create.stories.app.sql.DatabaseHelper;

/**
 * Created by DumpOSK129.
 */
public class MainActivity extends Activity{
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.getWritableDatabase();
    }
}