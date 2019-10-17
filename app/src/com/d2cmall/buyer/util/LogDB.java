package com.d2cmall.buyer.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Fixme
 * Author: hrb
 * Date: 2017/03/03 15:23
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class LogDB extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "LOG.db";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "log_table";
    public final static String LOG_ID = "log_id";
    public final static String LOG_CONTENT = "log_content";
    private static LogDB instance;

    public static LogDB getInstance(Context context) {
        if (instance == null) {
            synchronized (LogDB.class) {
                instance = new LogDB(context.getApplicationContext());
            }
        }
        return instance;
    }

    public LogDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //创建table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + LOG_ID
                + " INTEGER primary key autoincrement, " + LOG_CONTENT + " text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public Cursor select() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db
                .query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    public List<String> query() {
        List<String> results = new ArrayList<>();
        Cursor cursor = select();
        int count = 0;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (count >= 100) {
                    break;
                }
                results.add(cursor.getString(cursor.getColumnIndex(LOG_CONTENT)));
                count++;
            }
        }
        cursor.close();
        return results;
    }

    //增加操作
    public long insert(String log) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LOG_CONTENT, log);
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }

    //删除操作
    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = LOG_ID + " = ?";
        String[] whereValue = {Integer.toString(id)};
        db.delete(TABLE_NAME, where, whereValue);
    }

    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = LOG_ID + " between ? and ?";
        String[] whereValue = {Integer.toString(0), Integer.toString(99)};
        db.delete(TABLE_NAME, where, whereValue);
    }
}