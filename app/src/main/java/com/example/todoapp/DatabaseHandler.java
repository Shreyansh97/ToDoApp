package com.example.todoapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shreyansh on 20/5/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tasksManager";
    private static final String TABLE_NAME = "tasks";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESC = "description";
    private static final String KEY_DATE = "date";
    private static final String KEY_COMPLETED = "completed";

    DatabaseHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+TABLE_NAME+"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +KEY_TITLE+" TEXT, "+KEY_DESC+" TEXT, "+KEY_DATE+" TEXT, "+KEY_COMPLETED+" INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE,task.getTitle());
        values.put(KEY_DESC,task.getDescription());
        values.put(KEY_DATE,task.getDate());
        values.put(KEY_COMPLETED,task.isCompleted());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public Task getTaskFromId(int id){
        Task task = new Task();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID+"="+id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setTitle(cursor.getString(1));
                task.setDescription(cursor.getString(2));
                task.setDate(cursor.getString(3));
                task.setCompleted(Integer.parseInt(cursor.getString(4)) == 1);
            }while (cursor.moveToNext());
        }
        return task;
    }

    public List<Task> getTasks(int completed){
        List<Task> tasks = new ArrayList<Task>();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_COMPLETED+"="+completed;

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setTitle(cursor.getString(1));
                task.setDescription(cursor.getString(2));
                task.setDate(cursor.getString(3));
                task.setCompleted(Integer.parseInt(cursor.getString(4))==1);

                tasks.add(task);
            }while (cursor.moveToNext());
        }
        return tasks;
    }

    public void updateTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE,task.getTitle());
        contentValues.put(KEY_DESC,task.getDescription());
        contentValues.put(KEY_DATE,task.getDate());
        contentValues.put(KEY_COMPLETED,task.isCompleted());

        db.update(TABLE_NAME,contentValues, KEY_ID +" =? ",
                new String[] {String.valueOf(task.getId())});
        db.close();
    }

    public void deleteTask(Task task){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ID + " =?",
                new String[] {String.valueOf(task.getId())});
        db.close();
    }
}
