package com.dumposk129.create.stories.app.create;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import com.dumposk129.create.stories.app.sql.MySQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class StoryTable {
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String TABLE = "story";

    MySQLiteOpenHelper dbHelper;
    SQLiteDatabase db;

    List<Story> storys;

    public static String error;
    public StoryTable(Context context){
        dbHelper = new MySQLiteOpenHelper (context);
        db = dbHelper.getWritableDatabase();
        storys = new ArrayList<>();
    }
    public List<Story> getAll(){
        String sql = "SELECT  * FROM " + TABLE;
        db = dbHelper.getReadableDatabase();
        storys.clear();
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id =       cursor.getInt(cursor.getColumnIndex(ID));
            String name = cursor.getString(cursor.getColumnIndex(NAME));

            Story story = new Story().setId(id).setName(name);
            storys.add(story);
        }
        cursor.close();
        db.close();
        return  storys;
    }

    public boolean insert(Story story){
        boolean success = false;
        try{
            db = dbHelper.getWritableDatabase();
            String sql = "INSERT INTO " + TABLE +
                    "(" + NAME + ") VALUES(?)";
            SQLiteStatement stm =  db.compileStatement(sql);
            stm.bindString(1, story.getName());
            stm.executeInsert();
            db.close();
            success = true;
        }
        catch (SQLiteException e){
            error = "Insert Error: " + e.getMessage();
        }
        finally {
            db.close();
        }
        return success;
    }

    public  int delete(int id){
        int effect = 0;
        try{
            db = dbHelper.getWritableDatabase();
            String sql = "DELETE FROM " + TABLE + " WHERE "+ ID +" =?";
            SQLiteStatement stm =  db.compileStatement(sql);
            stm.bindLong(1, id);
            effect = stm.executeUpdateDelete();
        }
        catch (SQLiteException e){
            error = "Delete Error: " + e.getMessage();
        }
        finally {
            db.close();
        }
        return effect;
    }

    public  int update(Story story){
        int effect = 0;
        try{
                db = dbHelper.getWritableDatabase();
            String sql = " UPDATE " + TABLE +
                    " SET " + NAME + "=?" +
                    " WHERE "+ ID +" =?";
            SQLiteStatement stm =  db.compileStatement(sql);
            stm.bindString(1, story.getName());
            stm.bindLong(2, story.getId());
            effect = stm.executeUpdateDelete();
        }
        catch (SQLiteException e){
            error = "Update Error: " + e.getMessage();
        }
        finally {
            db.close();
        }
        return effect;
    }

    public Story getById(int id){
        String sql = "SELECT  * FROM  " + TABLE + " WHERE " + ID + " =?";
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
        Story story = null;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(NAME));
            story = new Story(id, name);
        }
        cursor.close();
        db.close();
        return  story;
    }

}
