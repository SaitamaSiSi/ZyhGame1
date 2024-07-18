package com.zyh.ZyhG1.ui.AndroidStudy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.zyh.ZyhG1.MainActivity;
import com.zyh.ZyhG1.R;
import com.zyh.ZyhG1.ui.BaseActivity;

import java.util.ArrayList;

public class RunningPermissionActivity extends BaseActivity {
    private ArrayList<String> _contactsList = new ArrayList<>();
    private ArrayAdapter<String> _adapter;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.running_permission);
        boolean extraData = getIntent().getBooleanExtra("HiddenActionBar", false);
        // 隐藏ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (extraData && actionBar != null) {
            actionBar.hide();
        }

        init();
    }

    public void init() {
        /* 返回上一层 **/
        Button quitBtn = findViewById(R.id.rp_btn_quit);
        quitBtn.setOnClickListener((v) -> {
            Intent intent = new Intent(RunningPermissionActivity.this, MainActivity.class);
            intent.putExtra("return", "运行时权限返回的数据");
            setResult(RESULT_OK, intent);
            RunningPermissionActivity.this.finish();
        });

        /* 拨打电话 **/
        Button callBtn = findViewById(R.id.rp_btn_call);
        callBtn.setOnClickListener((v) -> {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{ Manifest.permission.CALL_PHONE }, 1);
            } else {
                call();
            }
        });

        /* 获取联系人信息 **/
        _adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, _contactsList);
        ListView listView = findViewById(R.id.rp_contacts_view);
        listView.setAdapter(_adapter);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.READ_CONTACTS }, 2);
        } else {
            readContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                call();
            } else {
                Toast.makeText(this, "你拒绝了权限", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readContacts();
            } else {
                Toast.makeText(this, "你拒绝了权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void call() {
        try {
            // ACTION_DIAL只打开界面，不需要权限。ACTION_CALL直接打电话，需要权限
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:10086"));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readContacts() {
        ContentResolver contentResolver = getContentResolver();
        try (Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String displayName = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                    ));
                    @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    ));
                    _contactsList.add(displayName + "\n" + number);
                } while (cursor.moveToNext());
                _adapter.notifyDataSetChanged();
                cursor.close();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
