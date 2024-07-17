package com.zyh.ZyhG1.ui;

import android.os.Bundle;
import android.util.Log;

import com.google.androidgamesdk.GameActivity;
import com.zyh.ZyhG1.singleton.ActivityCollector;

public class BaseActivity extends GameActivity {
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        Log.d("BaseActivity", this.getClass().getName());
        ActivityCollector.addActivity(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
