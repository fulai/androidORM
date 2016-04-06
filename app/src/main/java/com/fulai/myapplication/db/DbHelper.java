package com.fulai.myapplication.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fulai on 2016/3/20.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static String databaseName = "contacts.db";
    private static int version = 1;
    private static String tableName = "persion";

    public DbHelper(Context context) {
        super(context, databaseName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "create table if not exists " + tableName + "( id Integer primary key autoincrement,name varchar(50),age Integer )";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + tableName);
        onCreate(db);
    }
}
