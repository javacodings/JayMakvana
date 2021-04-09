package com.example.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHolder extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "register.db";
    public static final String TABLE_NAME = "register_table";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String NUMBER = "number";
    public static final String EMAIL = "email";
    public static final String DATE1 = "date";
    public DatabaseHolder(@Nullable Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,NUMBER TEXT,EMAIL TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(db);
    }
    public boolean insertData(String name, String number, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(NUMBER,number);
        contentValues.put(EMAIL,email);
        //contentValues.put(DATE1,date);
        Long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == 0)
            return false;
        else
            return true;
    }
    public boolean updatedata(String id, String name, String number, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,id);
        contentValues.put(NAME,name);
        contentValues.put(NUMBER,number);
        contentValues.put(EMAIL,email);
        db.update(TABLE_NAME,contentValues,"ID = ?",new String[] { id });
        return true;
    }
    public Integer deletedata(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?",new String[]{String.valueOf(id)});
    }
    public Cursor Viewalldata(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = "email=? and password = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        close();

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkIfRecordExist(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = "email=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        close();

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
}
