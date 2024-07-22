package com.zyh.ZyhG1.thread;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.zyh.ZyhG1.R;
import com.zyh.ZyhG1.ui.AndroidStudy.ThreadActivity;

public class MyService extends Service {
    public static class DownloadBinder extends Binder {
        public void startDownload() {
            Log.d("Myservice", "startDownload executed");
        }
        public int getProgress() {
            Log.d("Myservice", "getProgress executed");
            return 0;
        }
    }

    private DownloadBinder mBinder = new DownloadBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Myservice", "onCreate executed");

        /* 前台Service **/
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("zyh_service", "前台Service通知",
                NotificationManager.IMPORTANCE_DEFAULT);
        manager.createNotificationChannel(channel);
        Intent intent = new Intent(this, ThreadActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder(this, "zyh_service")
                .setContentTitle("标题")
                .setContentText("内容")
                .setSmallIcon(R.drawable.person)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.star))
                .setContentIntent(pi)
                .build();
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Myservice", "onStartCommand executed");
        new Thread(() -> {
            // 具体停止代码
            Log.d("Myservice", "onStartCommand->stopSelf executed");
            stopSelf();
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("Myservice", "onDestroy executed");
        super.onDestroy();
    }
}
