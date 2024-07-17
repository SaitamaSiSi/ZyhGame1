package com.zyh.ZyhG1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteDBHelper extends SQLiteOpenHelper {
    //定义数据库名称和版本号
    public static final String name = "db_store.db";
    public static final int DB_VERSION = 1;
    //创建数据表的语句
    // public static final String CREATE_USERDATA = "create table tb_Students(studentid char(20)primary key,studentname varchar(20),majoy varchar(20),studentclass varchar(20))";
    public static final String CREATE_OBJECT_DATA = "create table tb_Objects(objectid char(20)primary key,studentname varchar(20),majoy varchar(20),studentclass varchar(20))";
    //构造函数
    public SqliteDBHelper(Context context) {
        super(context, name, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // db.execSQL(CREATE_USERDATA);
        db.execSQL(CREATE_OBJECT_DATA);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
