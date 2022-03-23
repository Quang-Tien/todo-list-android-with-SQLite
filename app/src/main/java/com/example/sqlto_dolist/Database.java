package com.example.sqlto_dolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    // not return data
    public void QueryData(String sqlCommand){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sqlCommand);
    }

    public Cursor GetData(String sqlCommand){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sqlCommand, null);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
