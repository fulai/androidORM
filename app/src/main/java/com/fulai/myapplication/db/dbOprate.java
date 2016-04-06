package com.fulai.myapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by fulai on 2016/3/20.
 */
public class dbOprate {
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public dbOprate(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
}
