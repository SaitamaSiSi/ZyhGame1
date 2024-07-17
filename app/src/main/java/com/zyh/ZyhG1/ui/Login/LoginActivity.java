package com.zyh.ZyhG1.ui.Login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import com.zyh.ZyhG1.MainActivity;
import com.zyh.ZyhG1.R;
import com.zyh.ZyhG1.singleton.ActivityCollector;
import com.zyh.ZyhG1.ui.BaseActivity;

public class LoginActivity extends BaseActivity {
    private EditText _accountEdit;
    private EditText _passwordEdit;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.login_activity);
        // 隐藏ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        _accountEdit = findViewById(R.id.login_account);
        _passwordEdit = findViewById(R.id.login_password);
    }

    public void onLoginClick(View view) {
        String account = _accountEdit.getText().toString();
        String password = _passwordEdit.getText().toString();
        if (account.equals("admin") && password.equals("123456")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "账号/密码不正确", Toast.LENGTH_SHORT).show();
        }
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
