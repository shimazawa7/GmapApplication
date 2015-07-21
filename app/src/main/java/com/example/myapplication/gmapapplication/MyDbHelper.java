package com.example.myapplication.gmapapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "myDatabase.db";
    static final int DATABASE_VERSION = 1;
    static final String TABLE_NAME = "myData";
    static final String ID = "_id";
    static final String COMPANY_NAME = "company_name";
    static final String ADDRESS = "addr";
    static final String TEL = "tel";
    static final String DAY = "day";
    static final String ETC = "etc";

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
}
    @Override
    public void onCreate(SQLiteDatabase db) {
        //重複を防ぐ処理
        String query = "create table " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COMPANY_NAME + " TEXT,"
                + ADDRESS + " TEXT,"
                + TEL + " TEXT,"
                + DAY + " TEXT,"
                + ETC + " TEXT);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DATABASE_NAME);
        onCreate(db);
    }
}
