package com.zyh.ZyhG1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.core.util.Pair;

import java.util.List;

public class SqliteDBHelper extends SQLiteOpenHelper {
    private final Context _context;
    //定义数据库名称和版本号
    public static final String name = "db_store.db";
    public static final int DB_VERSION = 1;
    //构造函数
    public SqliteDBHelper(Context context) {
        super(context, name, null, DB_VERSION);
        _context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        CreateDb(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 当前版本比当前文件中的数据库版本高时，会触发更新操作
        /*db.execSQL("drop table if exists food_category");
        db.execSQL("drop table if exists food");
        CreateDb(db);*/
    }

    private void CreateDb(SQLiteDatabase db) {
        String createFoodCategorySql = "create table food_category(" +
                "id integer primary key autoincrement," +
                "name text)";
        db.execSQL(createFoodCategorySql);
        Toast.makeText(_context, "创建food_category表成功", Toast.LENGTH_SHORT).show();
        String createFoodSql = "create table food(" +
                "id integer primary key autoincrement," +
                "category_id integer," +
                "name text," +
                "price integer," +
                "store_number integer)";
        db.execSQL(createFoodSql);
        // Toast.makeText(_context, "创建food表成功", Toast.LENGTH_SHORT).show();
    }

    private ContentValues cvOf(List<Pair<String, Object>> pairs) {
        ContentValues cv = new ContentValues();
        for (Pair<String, Object>pair : pairs) {
            String key = pair.first;
            Object value = pair.second;
            if (value instanceof Integer) {
                cv.put(key, (Integer) value);
            }
        }
        return cv;
    }
}
