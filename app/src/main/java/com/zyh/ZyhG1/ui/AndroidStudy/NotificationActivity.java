package com.zyh.ZyhG1.ui.AndroidStudy;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.core.app.NotificationCompat;

import com.zyh.ZyhG1.R;
import com.zyh.ZyhG1.ui.BaseActivity;

public class NotificationActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.notification_activity);

        init();
    }

    public void init() {
        /* 返回上一层 **/
        Button quitBtn = findViewById(R.id.ntf_btn_quit);
        quitBtn.setOnClickListener((v) -> {
            NotificationActivity.this.finish();
        });

        /* 百度 **/
        Button baiduBtn = findViewById(R.id.ntf_btn_baidu);
        baiduBtn.setOnClickListener((v) -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.baidu.com"));
                startActivity(intent);
        });

        /* 发送通知 **/
        Button ntfBtn = findViewById(R.id.ntf_btn_notification);
        ntfBtn.setOnClickListener((v) -> {
            // 渠道ID，渠道名称，重要等级
            String channelId = "0124";
            String channelName = "碳酸教父";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            // 对通知进行管理
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // 构建通知渠道
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
                manager.createNotificationChannel(channel);
            }
            Intent intent = new Intent(NotificationActivity.this, AndroidStudyActivity.class);
            intent.putExtra("HiddenActionBar", true);
            PendingIntent pi = PendingIntent.getActivity(NotificationActivity.this, 0, intent, 0);
            // 构建通知内容
            Notification notification = new NotificationCompat.Builder(this, channelId)
                    .setContentTitle("这是标题内容")
                    .setContentText("这是正文内容")
                    .setSmallIcon(R.drawable.person)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.pic1))
                    .setContentIntent(pi)
                    .build();
            // 通知，每个通知id都不同
            manager.notify(1, notification);
        });
    }
}
