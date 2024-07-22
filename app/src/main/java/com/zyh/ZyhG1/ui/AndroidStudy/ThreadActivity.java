package com.zyh.ZyhG1.ui.AndroidStudy;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zyh.ZyhG1.R;
import com.zyh.ZyhG1.thread.MyService;
import com.zyh.ZyhG1.ui.BaseActivity;

import java.lang.ref.WeakReference;

public class ThreadActivity extends BaseActivity {
    private static class MyHandler extends Handler {
        private final WeakReference<ThreadActivity> myActivityWeakReference;

        public MyHandler(ThreadActivity activity) {
            myActivityWeakReference = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            ThreadActivity activity = myActivityWeakReference.get();
            if (activity != null) {
                if (msg.what == 1) {
                    activity._textView.setText("Nice to meet you");
                }
            }
        }
    }

    private class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBinder = (MyService.DownloadBinder) iBinder;
            mBinder.startDownload();
            mBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    }

    MyHandler _handler = new MyHandler(this);
    TextView _textView;
    MyService.DownloadBinder mBinder = new MyService.DownloadBinder();
    MyServiceConnection connection = new MyServiceConnection();

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.thread_activity);

        Button changeBtn = findViewById(R.id.thread_btn_changeText);
        _textView = findViewById(R.id.thread_text_view);
        changeBtn.setOnClickListener((v) -> {
            _handler.sendEmptyMessage(1);
            // runOnUiThread(() -> { _textView.setText("Nice to meet you");});
        });

        /* 启动/停止服务 **/
        Button startBtn = findViewById(R.id.thread_btn_start);
        Button stopBtn = findViewById(R.id.thread_btn_stop);
        startBtn.setOnClickListener((v) -> {
            Intent intent = new Intent(ThreadActivity.this, MyService.class);
            startService(intent);
        });
        stopBtn.setOnClickListener((v) -> {
            Intent intent = new Intent(ThreadActivity.this, MyService.class);
            stopService(intent);
        });

        /* 绑定/解绑服务 **/
        Button bindBtn = findViewById(R.id.thread_btn_bind);
        Button unbindBtn = findViewById(R.id.thread_btn_unbind);
        bindBtn.setOnClickListener((v) -> {
            Intent intent = new Intent(ThreadActivity.this, MyService.class);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
        });
        unbindBtn.setOnClickListener((v) -> {
            unbindService(connection);
        });
    }

    /** 当活动将被销毁时调用 */
    @Override
    public void onDestroy() {
        _handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
