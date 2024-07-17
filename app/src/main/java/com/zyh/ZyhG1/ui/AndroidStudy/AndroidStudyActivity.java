package com.zyh.ZyhG1.ui.AndroidStudy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import com.zyh.ZyhG1.MainActivity;
import com.zyh.ZyhG1.R;
import com.zyh.ZyhG1.ui.BaseActivity;

public class AndroidStudyActivity extends BaseActivity {
    String msg = "Android AndroidStudyActivity: ";

    /** 当活动第一次被创建时调用 */
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        Log.d(msg, "The onCreate() event");
        setContentView(R.layout.android_stuty);
        boolean extraData = getIntent().getBooleanExtra("HiddenActionBar", false);
        // 隐藏ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (extraData && actionBar != null) {
            actionBar.hide();
        }
    }
    
    public void onBtnMsgClick(View view) {
        Toast.makeText(this, "You clicked Button 1", Toast.LENGTH_SHORT).show();
    }

    public void onBtnDialogClick(View view) {
        Intent intent = new Intent(AndroidStudyActivity.this, DialogActivity.class);
        startActivity(intent);
    }

    /* 退出按钮的点击事件*/
    public void quit(View view) {
        Log.d(msg, "The quit() event");
        Intent intent = new Intent(AndroidStudyActivity.this, MainActivity.class);
        intent.putExtra("return", "活动二返回的数据");
        setResult(RESULT_OK, intent);
        AndroidStudyActivity.this.finish();
    }
}
