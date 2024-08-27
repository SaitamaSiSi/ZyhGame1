package com.zyh.ZyhG1.ui.Login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.zyh.ZyhG1.MainActivity;
import com.zyh.ZyhG1.R;
import com.zyh.ZyhG1.singleton.ActivityCollector;
import com.zyh.ZyhG1.ui.BaseActivity;
import com.zyh.ZyhG1.ui.Canvas.CanvasActivity;

public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.login_activity);

        init();
    }

    public void init() {
        EditText accountEdit = findViewById(R.id.login_account);
        EditText passwordEdit = findViewById(R.id.login_password);
        CheckBox rememberPass = findViewById(R.id.login_remember_pwd);
        Button loginBtn = findViewById(R.id.login_account_login);

        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        boolean isRemember = prefs.getBoolean("remember_pwd", false);
        if (isRemember) {
            // 将账号和密码设置到文本框中
            String account = prefs.getString("account", "");
            String password = prefs.getString("password", "");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }

        loginBtn.setOnClickListener((v) -> {
            String account = accountEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            if (account.equals("admin") && password.equals("123456")) {
                SharedPreferences.Editor editor = prefs.edit();
                if (rememberPass.isChecked()) {
                    editor.putBoolean("remember_pwd", true);
                    editor.putString("account", account);
                    editor.putString("password", password);
                } else {
                    editor.clear();
                }
                editor.apply();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "账号/密码不正确", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onExitClick(View view) {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("是否确认退出APP")
                .setCancelable(false)
                .setNegativeButton("否", (dialogInterface, i) -> Toast.makeText(this, "已取消", Toast.LENGTH_SHORT).show())
                .setPositiveButton("是", (dialogInterface, i) -> ActivityCollector.finishAll())
                .show();
    }
}
