package com.example.administrator.finalworks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/12/2.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(id integer primary key autoincrement,name text, password text);");
        db.execSQL("create table food(id integer primary key autoincrement,idname text, apply text, cost real);");
        db.execSQL("create table car(id integer primary key autoincrement,idname text, apply text, cost real);");
        db.execSQL("create table shopping(id integer primary key autoincrement,idname text, apply text, cost real);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
