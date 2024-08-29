package com.zyh.ZyhG1.ui;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.androidgamesdk.GameActivity;
import com.zyh.ZyhG1.singleton.ActivityCollector;
import com.zyh.ZyhG1.ui.Login.LoginActivity;

public class BaseActivity extends GameActivity {
    private ForceOfflineReceiver _receiver;
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        // Log.d("BaseActivity", this.getClass().getName());
        /* 隐藏ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        **/

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.zyh.ZyhG1.FORCE_OFFLINE");
        _receiver = new ForceOfflineReceiver();
        registerReceiver(_receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(_receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    public static class ForceOfflineReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            new AlertDialog.Builder(context)
                    .setTitle("警告")
                    .setMessage("当前用户已被强制下线，请重新登录")
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialogInterface, pos) -> {
                        ActivityCollector.finishAll();
                        Intent i = new Intent(context, LoginActivity.class);
                        context.startActivity(i);
                    })
                    .show();
        }
    }
}
