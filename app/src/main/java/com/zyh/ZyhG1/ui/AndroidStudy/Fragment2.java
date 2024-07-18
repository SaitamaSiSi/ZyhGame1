package com.zyh.ZyhG1.ui.AndroidStudy;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zyh.ZyhG1.R;
import com.zyh.ZyhG1.database.SqliteDBHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class Fragment2 extends Fragment {
    private Context _context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        _context = view.getContext();

        EditText editText = view.findViewById(R.id.f2_edit_text);

        // 文件读写 /data/data/<package name>/files/
        Button fileSaver = view.findViewById(R.id.f2_btn_file_save);
        fileSaver.setOnClickListener(v -> {
            String inputText = editText.getText().toString();
            save(inputText);
        });
        Button fileLoader = view.findViewById(R.id.f2_btn_file_load);
        fileLoader.setOnClickListener(v -> {
            String outputText = load();
            editText.setText(outputText);
        });

        // 键值对文件读写 /data/data/<package name>/shared_prefs/
        Button sharedFileSaver = view.findViewById(R.id.f2_btn_share_save);
        sharedFileSaver.setOnClickListener(v -> {
            String inputText = editText.getText().toString();
            sharedSave(inputText);
        });
        Button sharedFileLoader = view.findViewById(R.id.f2_btn_share_load);
        sharedFileLoader.setOnClickListener(v -> {
            String outputText = sharedLoad();
            editText.setText(outputText);
        });

        // 创建数据库
        Button testDbBtn = view.findViewById(R.id.f2_btn_sqlite_test);
        try (SqliteDBHelper dbHelper = new SqliteDBHelper(_context)) {
            testDbBtn.setOnClickListener((v) -> {
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // 开启事务
                db.beginTransaction();
                try {
                    db.delete("food_category", null, null);

                    /* 新增 **/
                    ContentValues category1 = new ContentValues();
                    category1.put("name", "牛肉");
                    ContentValues category2 = new ContentValues();
                    category2.put("name", "羊肉");
                    db.insert("food_category", null, category1);
                    db.insert("food_category", null, category2);

                    /* 修改 **/
                    ContentValues newCategory = new ContentValues();
                    newCategory.put("name", "猪肉");
                    db.update("food_category", newCategory, "name = ?", new String[] { "羊肉" });

                    /* 删除 **/
                    db.delete("food_category", "name = ?", new String[] { "猪肉" });

                    db.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    db.endTransaction();
                }

                /* 查询 **/
                Cursor cursor = db.query("food_category", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                        Log.d("Fragment2", "food category id is " + id);
                        Log.d("Fragment2", "food category name is " + name);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    public void save(String inputText) {
        try (FileOutputStream output = _context.openFileOutput("data", Context.MODE_PRIVATE)) {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
            writer.write(inputText);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String load() {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileInputStream input = _context.openFileInput("data")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
            String line;
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public void sharedSave(String inputText) {
        SharedPreferences.Editor editor = _context.getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        editor.putString("editText", inputText);
        editor.apply();
    }

    public String sharedLoad() {
        SharedPreferences prefs = _context.getSharedPreferences("data", Context.MODE_PRIVATE);
        return prefs.getString("editText", "");
    }
}
