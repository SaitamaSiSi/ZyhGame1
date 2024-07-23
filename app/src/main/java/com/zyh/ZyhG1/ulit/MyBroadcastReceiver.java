package com.zyh.ZyhG1.ulit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String extraData = intent.getStringExtra("BroadcastInfo");
        Toast.makeText(context, "Received 【" + extraData + "】 in MyBroadcastReceiver", Toast.LENGTH_SHORT).show();
        // 通过android:priority="100"优先获取广播后，终止后续广播
        abortBroadcast();
    }
}
