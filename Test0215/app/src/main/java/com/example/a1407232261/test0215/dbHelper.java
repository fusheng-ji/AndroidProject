package com.example.a1407232261.test0215;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 1407232261 on 2020/3/26.
 */

public class dbHelper extends SQLiteOpenHelper{

    String TB_Name = "userinfo";

    public dbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists "+TB_Name
                +"(uid integer primary key autoincrement,"
                +"uname varchar,"
                +"pwd varchar,"
                +"age integer"
                +")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+TB_Name);
        onCreate(sqLiteDatabase);
    }
}
